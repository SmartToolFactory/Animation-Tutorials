# 🍭🚀💗 Animation Tutorials
Tutorials about animations in Android such as ObjectAnimators, ValueAnimators, translations,
gradient animations, AnimationDrawables, AnimatedVectorDrawables with states, physics animations,
fragment transitions and image to ViewPager transitions and more.

## Overview

* [Tutorial1-1Basics](https://github.com/SmartToolFactory/Animation-Tutorials/tree/master/Tutorial1-1Basics)
    * Tutorials about animators, animation basics and using coroutine based SurfaceView to create a counter up down motion

| Ch2-3 Circular Reveal      | Ch2-4 Rotate X/Y Flip   | Ch2-6 Zoom |
| ----------|-----------| -----------|
| <img src="./screenshots/chapter2_3circular_reveal.gif"/> | <img src="./screenshots/chapter2_4flip.gif"/> | <img src="./screenshots/chapter2_6zoom.gif"/> |

| Ch2-7 Gradient | Ch2-8 Counter TextViews   | Ch2-9 Counter SurfaceView |
| ----------|----------------| --------|
| <img src="./screenshots/chapter2_7gradient.gif"/> | <img src="./screenshots/chapter2_8counter_textview.gif"/> | <img src="./screenshots/chapter2_9_counter_surfaceview.gif"/> |

* [Tutorial2-1Animated Vector Drawables](https://github.com/SmartToolFactory/Animation-Tutorials/tree/master/Tutorial2-1AnimatedVectorDrawables)
   * Tutorials about Vector drawables, AnimatedVectorDrawables, and animation transitions for Animated Drawables
   
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

**M** represents move to a coordinate
**L** means draw a line from previous coordinate to coordinates after *L*
**C** is contour
**Z** closes/ends the drawing shape

**<group>**  tag is used for grouping sections of drawable to be able to be animated together. And some animations such as
rotation, and translation can only be applied to groups.

Animations can be performed on the animatable attributes in android.graphics.drawable.VectorDrawable. 
These attributes will be animated by android.animation.ObjectAnimator. 
The ObjectAnimator's target can be the root element, a group element or a path element.
 The targeted elements need to be named uniquely within the same VectorDrawable. 
 Elements without animation do not need to be named.


### XML for AnimatedVectorDrawable
An AnimatedVectorDrawable element has a VectorDrawable attribute, and one or more target element(s). 
The target element can specify its target by android:name attribute, 
and link the target with the proper ObjectAnimator or AnimatorSet by android:animation attribute.

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

### TODOs:
- [ ] Add fragment transitions, and image to ViewPager transitions
- [ ] Add RecyclerView, ViewPager animations
- [ ] Add custom Views with animations
