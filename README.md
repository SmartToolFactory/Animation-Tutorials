# 🍭🚀💗 Animation Tutorials
Tutorials about animations in Android such as ObjectAnimators, ValueAnimators, translations,
gradient animations, AnimationDrawables, AnimatedVectorDrawables with states, physics animations,
fragment transitions and image to ViewPager transitions and more.

## Overview

* [Tutorial1-1Basics](https://github.com/SmartToolFactory/Animation-Tutorials/tree/master/Tutorial1-1Basics)
<br>Tutorials about animators, animation basics and using coroutine based SurfaceView to create a counter up down motion

| Ch2-3 Circular Reveal      | Ch2-4 Rotate X/Y Flip   | Ch2-6 Zoom |
| ----------|-----------| -----------|
| <img src="./screenshots/chapter2_3circular_reveal.gif"/> | <img src="./screenshots/chapter2_4flip.gif"/> | <img src="./screenshots/chapter2_6zoom.gif"/> |

| Ch2-7 Gradient | Ch2-8 Counter TextViews   | Ch2-9 Counter SurfaceView |
| ----------|----------------| --------|
| <img src="./screenshots/chapter2_7gradient.gif"/> | <img src="./screenshots/chapter2_8counter_textview.gif"/> | <img src="./screenshots/chapter2_9_counter_surfaceview.gif"/> |

| Ch3-1 Physics | Ch3-2 Scale and Chained   | Ch3-3 Fling |
| ----------|----------------| --------|
| <img src="./screenshots/chapter3_1physics_basics.gif"/> | <img src="./screenshots/chapter3_2scale_and_chain.gif"/> | <img src="./screenshots/chapter3_fling_animation.gif"/> |

<br>

* [Tutorial2-1Animated Vector Drawables](https://github.com/SmartToolFactory/Animation-Tutorials/tree/master/Tutorial2-1AnimatedVectorDrawables)
<br>Tutorials about Vector drawables, AnimatedVectorDrawables, and animation transitions for Animated Drawables

| Ch1-1 Animated VDs | Ch1-2 State Change   | Ch1-3 BNV Icons |
| ----------|----------------| --------|
| <img src="./screenshots/avd_chapter1_1.gif"/> | <img src="./screenshots/avd_chapter1_2.gif"/> | <img src="./screenshots/avd_chapter1_3.gif"/> |

* [Tutorial3-1Shared Transitions](https://github.com/SmartToolFactory/Animation-Tutorials/tree/master/Tutorial3-1Transitions)
Tutorials about Shared Transitions from Activity to Activity, and from Fragment to Fragment

| Ch1-2 RV Transition | Ch1-3 RV to VP2 Transition   | Ch1-4 Style |
| ----------|----------------| --------|
| <img src="./screenshots/transition_chapter1_2.gif"/> | <img src="./screenshots/transition_chapter1_3.gif"/> | <img src="./screenshots/transition_chapter1_4.gif"/> |


## Physics Based Animations
Physics-based motion is driven by force. Spring force is one such force that guides interactivity and motion. A spring force has the following properties: damping and stiffness. In a spring-based animation, the value and the velocity are calculated based on the spring force that are applied on each frame.

If you'd like your app's animations to slow down in only one direction, consider using a friction-based fling animation instead.

### Build a spring animation
The general steps for building a spring animation for your application are as follows:

* Add the support library You must add the support library to your project to use the spring animation classes.
* Create a spring animation: The primary step is to create an instance of the SpringAnimation class and set the motion behavior parameters.
* (Optional) Register listeners: Register listeners to watch for animation lifecycle changes and animation value updates.

**Note:** Update listener should be registered only if you need per-frame update on the animation value changes. An update listener prevents the animation from potentially running on a separate thread.

* (Optional) Remove listeners: Remove listeners that are no longer in use.
* (Optional) Set a start value: Customize the animation start value.
* (Optional) Set a value range: Set the animation value range to restrain values within the minimum and the maximum range.
* (Optional) Set start velocity: Set the start velocity for the animation.
* (Optional) Set spring properties: Set the damping ratio and the stiffness on the spring.
* (Optional) Create a custom spring: Create a custom spring in case you do not intend to use the default spring or want to use a common spring throughout the animation.
* Start animation: Start the spring animation.
* (Optional) Cancel animation: Cancel the animation in case the user abruptly exits the app or the view becomes invisble.

## Drawable Animations   

### XML for the VectorDrawable containing properties to be animated
A VectorDrawable can be represented in xml with

```
<vector xmlns:android="http://schemas.android.com/apk/res/android"
      android:height="64dp"
      android:width="64dp"
      android:viewportHeight="600"
      android:viewportWidth="600" >
      <group
          android:name="rotationGroup"
          android:pivotX="300.0"
          android:pivotY="300.0"
          android:rotation="45.0" >
          <path
              android:name="v"
              android:fillColor="#000000"
              android:pathData="M300,70 l 0,-70 70,70 0,0 -70,70z" />
      </group>
  </vector>
```

where width and height are the actual dimensions of while viewportWidth, and viewportHeight are used for drawing coordinates.

**M**(x,y) 	Begin a new subpath by moving to (x,y).

**L**(x,y) Draw a line to (x,y).

**C** (x1,y1 x2,y2 x,y) Draw a cubic bezier curve to (x,y) using control points (x1,y1) and (x2,y2).

**Z** Close the path by drawing a line back to the beginning of the current subpath

**'<group>'**  tag is used for grouping sections of drawable to be able to be animated together. And some animations such as
rotation, and translation can only be applied to groups.

Animations can be performed on the animatable attributes in android.graphics.drawable.VectorDrawable. 
These attributes will be animated by android.animation.ObjectAnimator. 
The ObjectAnimator's target can be the root element, a group element or a path element.
 The targeted elements need to be named uniquely within the same VectorDrawable. 
 Elements without animation do not need to be named.
 
 For more details you check out [here](https://www.androiddesignpatterns.com/2016/11/introduction-to-icon-animation-techniques.html)



### XML for AnimatedVectorDrawable

An AnimatedVectorDrawable element has a VectorDrawable attribute, and one or more target element(s). 
The target element can specify its target by android:name attribute, 
and link the target with the proper ObjectAnimator or AnimatorSet by android:animation attribute.

🔥😍 [ShapeShifter](https://shapeshifter.design) by **Alex Lockwood** makes it so easy to create animations for Vector Drawables.
Wonderful and very easy to use tool to create Animated Vector Drawables.

### XML for Animations defined using ObjectAnimator or AnimatorSet
```
<set xmlns:android="http://schemas.android.com/apk/res/android">
      <objectAnimator
          android:duration="3000"
          android:propertyName="pathData"
          android:valueFrom="M300,70 l 0,-70 70,70 0,0 -70,70z"
          android:valueTo="M300,70 l 0,-70 70,0  0,140 -70,0 z"
          android:valueType="pathType"/>
</set>
```
### Define an AnimatedVectorDrawable all in one XML file

Since the AAPT tool supports a new format that bundles several related XML files together, we can merge the XML files from the previous examples into one XML file:

```
<animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
                   xmlns:aapt=&quothttp://schemas.android.com/aapt" >
      <aapt:attr name="android:drawable">
          <vector
              android:height="64dp"
              android:width="64dp"
              android:viewportHeight="600"
              android:viewportWidth="600" >
              <group
                  android:name="rotationGroup"
                  android:pivotX="300.0"
                  android:pivotY="300.0"
                  android:rotation="45.0" >
                  <path
                      android:name="v"
                      android:fillColor="#000000"
                      android:pathData="M300,70 l 0,-70 70,70 0,0 -70,70z" />
              </group>
          </vector>
      </aapt:attr>
 
      <target android:name="rotationGroup"> *
          <aapt:attr name="android:animation">
              <objectAnimator
              android:duration="6000"
              android:propertyName="rotation"
              android:valueFrom="0"
              android:valueTo="360" />
          </aapt:attr>
      </target>
 
      <target android:name="v" >
          <aapt:attr name="android:animation">
              <set>
                  <objectAnimator
                      android:duration="3000"
                      android:propertyName="pathData"
                      android:valueFrom="M300,70 l 0,-70 70,70 0,0 -70,70z"
                      android:valueTo="M300,70 l 0,-70 70,0  0,140 -70,0 z"
                      android:valueType="pathType"/>
              </set>
          </aapt:attr>
       </target>
  </animated-vector>
```

## Shared Transitions

### Transitions

To create transition between views set transition name in xml with **android:transitionName**
```
 <androidx.appcompat.widget.AppCompatImageView
            // Rest of the imageView properties
            android:transitionName="ivAvatar"/>
```

or in Kotlin/Java with
```
iv.setTransitionName("SOME_TRANSITION_NAME")
```
these names should match for both Activities. To start transition after a click

```
 val intent = Intent(this, Activity1_1DetailActivity::class.java)
        intent.putExtra("imageRes", imageRes)

        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                this,
                ivAvatar,
                ViewCompat.getTransitionName(ivAvatar)
            )
        // start the new activity
        startActivity(intent, options.toBundle())
```

For custom transitions create **transition** folder inside **res** folder and add

```
<slide xmlns:android="http://schemas.android.com/apk/res/android"
    android:slideEdge="left"
    android:duration="1500">

    <targets>
        <!-- Specify the status bar ID if it needs to be excluded -->
        <target android:excludeId="@android:id/statusBarBackground"/>
        <!-- Specify the navigation bar ID if it needs to be excluded -->
        <target android:excludeId="@android:id/navigationBarBackground"/>
    </targets>

</slide>
```

default transition for Android system is

```
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- This is the systems default transition -->
    <changeBounds />
    <changeTransform />
    <changeClipBounds />
    <changeImageTransform />
</transitionSet>
```

Use ```<item name="android:windowContentTransitions">true</item>``` to enable Activity transitions
Use ```<item name="android:windowSharedElementsUseOverlay">false</item>``` for shared transition items not to be drawn
over **NavigationBar**, or **Toolbar**

### TODOs:
- [ ] Add fragment transitions, and image to ViewPager transitions
- [ ] Add RecyclerView, ViewPager animations
- [ ] Add custom Views with animations
