package com.smarttoolfactory.tutorial3_1transitions.adapter.layoutmanager

import android.content.Context
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * A [RecyclerView.LayoutManager] which displays a regular grid (i.e. all cells are the same
 * size) and allows simultaneous row & column spanning.
 * Source: https://github.com/nickbutcher/plaid/blob/6a3cb17ede0ffeffb4ca16e4fd3c2ab60900d6b8/app/src/main/java/io/plaidapp/ui/recyclerview/SpannedGridLayoutManager.java
 */
class SpannedGridLayoutManager : RecyclerView.LayoutManager {

    private var spanLookup: GridSpanLookup? = null
    private var columns = 1
    private var cellAspectRatio = 1f

    private var cellHeight: Int = 0
    private var cellBorders: IntArray? = null
    var firstVisibleItemPosition: Int = 0
        private set
    private var lastVisiblePosition: Int = 0
    private var firstVisibleRow: Int = 0
    private var lastVisibleRow: Int = 0
    private var forceClearOffsets: Boolean = false
    private var cells: SparseArray<GridCell>? = null
    private var firstChildPositionForRow: MutableList<Int>? = null // key == row, val == first child position
    private var totalRows: Int = 0
    private val itemDecorationInsets = Rect()

    private val spannedRowCount: Int
        get() = firstChildPositionForRow!!.size

    private// adjust to spanned rows
    val minimumFirstVisibleRow: Int
        get() {
            val maxDisplayedRows = Math.ceil((height.toFloat() / cellHeight).toDouble()).toInt() + 1
            if (totalRows < maxDisplayedRows) return 0
            val minFirstRow = totalRows - maxDisplayedRows
            return getRowIndex(getFirstPositionInSpannedRow(minFirstRow))
        }

    constructor(columns: Int, cellAspectRatio: Float) {
        this.spanLookup = object : GridSpanLookup {
            override fun getSpanInfo(position: Int): SpanInfo {
                return if (position % 6 == 0 || position % 6 == 4) {
                    SpanInfo(2, 2)
                } else {
                    SpanInfo(1, 1)
                }
            }
        }
        this.columns = columns
        this.cellAspectRatio = cellAspectRatio
        isAutoMeasureEnabled = true
    }

    interface GridSpanLookup {
        fun getSpanInfo(position: Int): SpanInfo
    }

    fun setSpanLookup(spanLookup: GridSpanLookup) {
        this.spanLookup = spanLookup
    }

    class SpanInfo(var columnSpan: Int, var rowSpan: Int) {
        companion object {

            val SINGLE_CELL = SpanInfo(1, 1)
        }
    }

    class LayoutParams : RecyclerView.LayoutParams {

        internal var columnSpan: Int = 0
        internal var rowSpan: Int = 0

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {}

        constructor(width: Int, height: Int) : super(width, height) {}

        constructor(source: ViewGroup.MarginLayoutParams) : super(source) {}

        constructor(source: ViewGroup.LayoutParams) : super(source) {}

        constructor(source: RecyclerView.LayoutParams) : super(source) {}
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        calculateWindowSize()
        calculateCellPositions(recycler, state!!)

        if (state.itemCount == 0) {
            detachAndScrapAttachedViews(recycler!!)
            firstVisibleRow = 0
            resetVisibleItemTracking()
            return
        }

        // TODO use orientationHelper
        var startTop = paddingTop
        var scrollOffset = 0
        if (forceClearOffsets) { // see #scrollToPosition
            startTop = -(firstVisibleRow * cellHeight)
            forceClearOffsets = false
        } else if (childCount != 0) {
            scrollOffset = getDecoratedTop(getChildAt(0)!!)
            startTop = scrollOffset - firstVisibleRow * cellHeight
            resetVisibleItemTracking()
        }

        detachAndScrapAttachedViews(recycler!!)
        var row = firstVisibleRow
        var availableSpace = height - scrollOffset
        val lastItemPosition = state.itemCount - 1
        while (lastVisiblePosition < lastItemPosition) {
            availableSpace -= layoutRow(row, startTop, recycler, state)
            row = getNextSpannedRow(row)
        }

        layoutDisappearingViews(recycler, state, startTop)
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun generateLayoutParams(c: Context, attrs: AttributeSet): RecyclerView.LayoutParams {
        return LayoutParams(c, attrs)
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
        return (lp as? ViewGroup.MarginLayoutParams)?.let { LayoutParams(it) } ?: LayoutParams(lp)
    }

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        return lp is LayoutParams
    }

    override fun onAdapterChanged(oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?) {
        removeAllViews()
        reset()
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return true
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        if (childCount == 0 || dy == 0) return 0

        val scrolled: Int
        val top = getDecoratedTop(getChildAt(0)!!)

        if (dy < 0) { // scrolling content down
            if (firstVisibleRow == 0) { // at top of content
                val scrollRange = -(paddingTop - top)
                scrolled = Math.max(dy, scrollRange)
            } else {
                scrolled = dy
            }
            if (top - scrolled >= 0) { // new top row came on screen
                val newRow = firstVisibleRow - 1
                if (newRow >= 0) {
                    val startOffset = top - firstVisibleRow * cellHeight
                    layoutRow(newRow, startOffset, recycler, state)
                }
            }
            val firstPositionOfLastRow = getFirstPositionInSpannedRow(lastVisibleRow)
            val lastRowTop = getDecoratedTop(
                getChildAt(firstPositionOfLastRow - firstVisibleItemPosition)!!
            )
            if (lastRowTop - scrolled > height) { // last spanned row scrolled out
                recycleRow(lastVisibleRow, recycler, state)
            }
        } else { // scrolling content up
            val bottom = getDecoratedBottom(getChildAt(childCount - 1)!!)
            if (lastVisiblePosition == itemCount - 1) { // is at end of content
                val scrollRange = Math.max(bottom - height + paddingBottom, 0)
                scrolled = Math.min(dy, scrollRange)
            } else {
                scrolled = dy
            }
            if (bottom - scrolled < height) { // new row scrolled in
                val nextRow = lastVisibleRow + 1
                if (nextRow < spannedRowCount) {
                    val startOffset = top - firstVisibleRow * cellHeight
                    layoutRow(nextRow, startOffset, recycler, state)
                }
            }
            val lastPositionInRow = getLastPositionInSpannedRow(firstVisibleRow, state)
            val bottomOfFirstRow = getDecoratedBottom(getChildAt(lastPositionInRow - firstVisibleItemPosition)!!)
            if (bottomOfFirstRow - scrolled < 0) { // first spanned row scrolled out
                recycleRow(firstVisibleRow, recycler, state)
            }
        }
        offsetChildrenVertical(-scrolled)
        return scrolled
    }

    override fun scrollToPosition(position: Int) {
        var position = position
        if (position >= itemCount) position = itemCount - 1

        firstVisibleRow = getRowIndex(position)
        resetVisibleItemTracking()
        forceClearOffsets = true
        removeAllViews()
        requestLayout()
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int
    ) {
        var position = position
        if (position >= itemCount) position = itemCount - 1

        val scroller = object : LinearSmoothScroller(recyclerView!!.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF {
                val rowOffset = getRowIndex(targetPosition) - firstVisibleRow
                return PointF(0f, (rowOffset * cellHeight).toFloat())
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

    override fun computeVerticalScrollRange(state: RecyclerView.State): Int {
        // TODO update this to incrementally calculate
        return spannedRowCount * cellHeight + paddingTop + paddingBottom
    }

    override fun computeVerticalScrollExtent(state: RecyclerView.State): Int {
        return height
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        return if (childCount == 0) 0 else paddingTop + firstVisibleRow * cellHeight - getDecoratedTop(getChildAt(0)!!)
    }

    override fun findViewByPosition(position: Int): View? {
        return if (position < firstVisibleItemPosition || position > lastVisiblePosition) null else getChildAt(position - firstVisibleItemPosition)
    }

    private class GridCell internal constructor(
        internal val row: Int,
        internal val rowSpan: Int,
        internal val column: Int,
        internal val columnSpan: Int
    )

    /**
     * This is the main layout algorithm, iterates over all items and places them into [column, row]
     * cell positions. Stores this layout info for use later on. Also records the adapter position
     * that each row starts at.
     *
     *
     * Note that if a row is spanned, then the row start position is recorded as the first cell of
     * the row that the spanned cell starts in. This is to ensure that we have sufficient contiguous
     * views to layout/draw a spanned row.
     */
    private fun calculateCellPositions(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        val itemCount = state.itemCount
        cells = SparseArray(itemCount)
        firstChildPositionForRow = ArrayList()
        var row = 0
        var column = 0
        recordSpannedRowStartPosition(row, column)
        val rowHWM = IntArray(columns) // row high water mark (per column)
        for (position in 0 until itemCount) {

            val spanInfo: SpanInfo
            val adapterPosition = recycler!!.convertPreLayoutPositionToPostLayout(position)
            if (adapterPosition != RecyclerView.NO_POSITION) {
                spanInfo = spanLookup!!.getSpanInfo(adapterPosition)
            } else {
                // item removed from adapter, retrieve its previous span info
                // as we can't get from the lookup (adapter)
                spanInfo = getSpanInfoFromAttachedView(position)
            }

            if (spanInfo.columnSpan > columns) {
                spanInfo.columnSpan = columns // or should we throw?
            }

            // check horizontal space at current position else start a new row
            // note that this may leave gaps in the grid; we don't backtrack to try and fit
            // subsequent cells into gaps. We place the responsibility on the adapter to provide
            // continuous data i.e. that would not span column boundaries to avoid gaps.
            if (column + spanInfo.columnSpan > columns) {
                row++
                recordSpannedRowStartPosition(row, position)
                column = 0
            }

            // check if this cell is already filled (by previous spanning cell)
            while (rowHWM[column] > row) {
                column++
                if (column + spanInfo.columnSpan > columns) {
                    row++
                    recordSpannedRowStartPosition(row, position)
                    column = 0
                }
            }

            // by this point, cell should fit at [column, row]
            cells!!.put(position, GridCell(row, spanInfo.rowSpan, column, spanInfo.columnSpan))

            // update the high water mark book-keeping
            for (columnsSpanned in 0 until spanInfo.columnSpan) {
                rowHWM[column + columnsSpanned] = row + spanInfo.rowSpan
            }

            // if we're spanning rows then record the 'first child position' as the first item
            // *in the row the spanned item starts*. i.e. the position might not actually sit
            // within the row but it is the earliest position we need to render in order to fill
            // the requested row.
            if (spanInfo.rowSpan > 1) {
                val rowStartPosition = getFirstPositionInSpannedRow(row)
                for (rowsSpanned in 1 until spanInfo.rowSpan) {
                    val spannedRow = row + rowsSpanned
                    recordSpannedRowStartPosition(spannedRow, rowStartPosition)
                }
            }

            // increment the current position
            column += spanInfo.columnSpan
        }
        totalRows = rowHWM[0]
        for (i in 1 until rowHWM.size) {
            if (rowHWM[i] > totalRows) {
                totalRows = rowHWM[i]
            }
        }
    }

    private fun getSpanInfoFromAttachedView(position: Int): SpanInfo {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (position == getPosition(child!!)) {
                val lp = child.layoutParams as LayoutParams
                return SpanInfo(lp.columnSpan, lp.rowSpan)
            }
        }
        // errrrr?
        return SpanInfo.SINGLE_CELL
    }

    private fun recordSpannedRowStartPosition(rowIndex: Int, position: Int) {
        if (spannedRowCount < rowIndex + 1) {
            firstChildPositionForRow!!.add(position)
        }
    }

    private fun getRowIndex(position: Int): Int {
        return if (position < cells!!.size()) cells!!.get(position).row else -1
    }

    private fun getNextSpannedRow(rowIndex: Int): Int {
        val firstPositionInRow = getFirstPositionInSpannedRow(rowIndex)
        var nextRow = rowIndex + 1
        while (nextRow < spannedRowCount && getFirstPositionInSpannedRow(nextRow) == firstPositionInRow) {
            nextRow++
        }
        return nextRow
    }

    private fun getFirstPositionInSpannedRow(rowIndex: Int): Int {
        return firstChildPositionForRow!![rowIndex]
    }

    private fun getLastPositionInSpannedRow(rowIndex: Int, state: RecyclerView.State?): Int {
        val nextRow = getNextSpannedRow(rowIndex)
        return if (nextRow != spannedRowCount)
        // check if reached boundary
            getFirstPositionInSpannedRow(nextRow) - 1
        else
            state!!.itemCount - 1
    }

    /**
     * Lay out a given 'row'. We might actually add more that one row if the requested row contains
     * a row-spanning cell. Returns the pixel height of the rows laid out.
     *
     *
     * To simplify logic & book-keeping, views are attached in adapter order, that is child 0 will
     * always be the earliest position displayed etc.
     */
    private fun layoutRow(
        rowIndex: Int, startTop: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?
    ): Int {
        val firstPositionInRow = getFirstPositionInSpannedRow(rowIndex)
        val lastPositionInRow = getLastPositionInSpannedRow(rowIndex, state)
        var containsRemovedItems = false

        var insertPosition = if (rowIndex < firstVisibleRow) 0 else childCount
        var position = firstPositionInRow
        while (position <= lastPositionInRow) {

            val view = recycler!!.getViewForPosition(position)
            val lp = view.layoutParams as LayoutParams
            containsRemovedItems = containsRemovedItems or lp.isItemRemoved
            val cell = cells!!.get(position)
            addView(view, insertPosition)

            // TODO use orientation helper
            val wSpec = RecyclerView.LayoutManager.getChildMeasureSpec(
                cellBorders!![cell.column + cell.columnSpan] - cellBorders!![cell.column],
                View.MeasureSpec.EXACTLY, 0, lp.width, false
            )
            val hSpec = RecyclerView.LayoutManager.getChildMeasureSpec(
                cell.rowSpan * cellHeight,
                View.MeasureSpec.EXACTLY, 0, lp.height, true
            )
            measureChildWithDecorationsAndMargin(view, wSpec, hSpec)

            val left = cellBorders!![cell.column] + lp.leftMargin
            val top = startTop + cell.row * cellHeight + lp.topMargin
            val right = left + getDecoratedMeasuredWidth(view)
            val bottom = top + getDecoratedMeasuredHeight(view)
            layoutDecorated(view, left, top, right, bottom)
            lp.columnSpan = cell.columnSpan
            lp.rowSpan = cell.rowSpan
            position++
            insertPosition++
        }

        if (firstPositionInRow < firstVisibleItemPosition) {
            firstVisibleItemPosition = firstPositionInRow
            firstVisibleRow = getRowIndex(firstVisibleItemPosition)
        }
        if (lastPositionInRow > lastVisiblePosition) {
            lastVisiblePosition = lastPositionInRow
            lastVisibleRow = getRowIndex(lastVisiblePosition)
        }
        if (containsRemovedItems) return 0 // don't consume space for rows with disappearing items

        val first = cells!!.get(firstPositionInRow)
        val last = cells!!.get(lastPositionInRow)
        return (last.row + last.rowSpan - first.row) * cellHeight
    }

    /**
     * Remove and recycle all items in this 'row'. If the row includes a row-spanning cell then all
     * cells in the spanned rows will be removed.
     */
    private fun recycleRow(
        rowIndex: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?
    ) {
        val firstPositionInRow = getFirstPositionInSpannedRow(rowIndex)
        val lastPositionInRow = getLastPositionInSpannedRow(rowIndex, state)
        var toRemove = lastPositionInRow
        while (toRemove >= firstPositionInRow) {
            val index = toRemove - firstVisibleItemPosition
            removeAndRecycleViewAt(index, recycler!!)
            toRemove--
        }
        if (rowIndex == firstVisibleRow) {
            firstVisibleItemPosition = lastPositionInRow + 1
            firstVisibleRow = getRowIndex(firstVisibleItemPosition)
        }
        if (rowIndex == lastVisibleRow) {
            lastVisiblePosition = firstPositionInRow - 1
            lastVisibleRow = getRowIndex(lastVisiblePosition)
        }
    }

    private fun layoutDisappearingViews(
        recycler: RecyclerView.Recycler, state: RecyclerView.State, startTop: Int
    ) {
        // TODO
    }

    private fun calculateWindowSize() {
        // TODO use OrientationHelper#getTotalSpace
        val cellWidth = Math.floor(((width - paddingLeft - paddingRight) / columns).toDouble()).toInt()
        cellHeight = Math.floor((cellWidth * (1f / cellAspectRatio)).toDouble()).toInt()
        calculateCellBorders()
    }

    private fun reset() {
        cells = null
        firstChildPositionForRow = null
        firstVisibleItemPosition = 0
        firstVisibleRow = 0
        lastVisiblePosition = 0
        lastVisibleRow = 0
        cellHeight = 0
        forceClearOffsets = false
    }

    private fun resetVisibleItemTracking() {
        // maintain the firstVisibleRow but reset other state vars
        // TODO make orientation agnostic
        val minimumVisibleRow = minimumFirstVisibleRow
        if (firstVisibleRow > minimumVisibleRow) firstVisibleRow = minimumVisibleRow
        firstVisibleItemPosition = getFirstPositionInSpannedRow(firstVisibleRow)
        lastVisibleRow = firstVisibleRow
        lastVisiblePosition = firstVisibleItemPosition
    }

    /* Adapted from GridLayoutManager */

    private fun calculateCellBorders() {
        cellBorders = IntArray(columns + 1)
        val totalSpace = width - paddingLeft - paddingRight
        var consumedPixels = paddingLeft
        cellBorders!![0] = consumedPixels
        val sizePerSpan = totalSpace / columns
        val sizePerSpanRemainder = totalSpace % columns
        var additionalSize = 0
        for (i in 1..columns) {
            var itemSize = sizePerSpan
            additionalSize += sizePerSpanRemainder
            if (additionalSize > 0 && columns - additionalSize < sizePerSpanRemainder) {
                itemSize += 1
                additionalSize -= columns
            }
            consumedPixels += itemSize
            cellBorders!![i] = consumedPixels
        }
    }

    private fun measureChildWithDecorationsAndMargin(child: View, widthSpec: Int, heightSpec: Int) {
        var widthSpec = widthSpec
        var heightSpec = heightSpec
        calculateItemDecorationsForChild(child, itemDecorationInsets)
        val lp = child.getLayoutParams() as RecyclerView.LayoutParams
        widthSpec = updateSpecWithExtra(
            widthSpec, lp.leftMargin + itemDecorationInsets.left,
            lp.rightMargin + itemDecorationInsets.right
        )
        heightSpec = updateSpecWithExtra(
            heightSpec, lp.topMargin + itemDecorationInsets.top,
            lp.bottomMargin + itemDecorationInsets.bottom
        )
        child.measure(widthSpec, heightSpec)
    }

    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        if (startInset == 0 && endInset == 0) {
            return spec
        }
        val mode = View.MeasureSpec.getMode(spec)
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(spec) - startInset - endInset, mode
            )
        } else spec
    }

    /* Adapted from ConstraintLayout */

    private fun parseAspectRatio(aspect: String?) {
        if (aspect != null) {
            val colonIndex = aspect.indexOf(':')
            if (colonIndex >= 0 && colonIndex < aspect.length - 1) {
                val nominator = aspect.substring(0, colonIndex)
                val denominator = aspect.substring(colonIndex + 1)
                if (nominator.length > 0 && denominator.length > 0) {
                    try {
                        val nominatorValue = java.lang.Float.parseFloat(nominator)
                        val denominatorValue = java.lang.Float.parseFloat(denominator)
                        if (nominatorValue > 0 && denominatorValue > 0) {
                            cellAspectRatio = Math.abs(nominatorValue / denominatorValue)
                            return
                        }
                    } catch (e: NumberFormatException) {
                        // Ignore
                    }

                }
            }
        }
        throw IllegalArgumentException("Could not parse aspect ratio: '$aspect'")
    }

}