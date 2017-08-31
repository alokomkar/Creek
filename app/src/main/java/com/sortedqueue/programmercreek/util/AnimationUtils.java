package com.sortedqueue.programmercreek.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;

import com.sortedqueue.programmercreek.R;

/**
 * Created by Alok Omkar on 2017-02-04.
 */

public class AnimationUtils {

    public static void enterReveal( View myView ) {

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            try {
                if( myView != null ) {
                    // previously invisible view

                    // get the center for the clipping circle
                    int cx = myView.getMeasuredWidth() / 2;
                    int cy = myView.getMeasuredHeight() / 2;

                    // get the final radius for the clipping circle
                    int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

                    // create the animator for this view (the start radius is zero)
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

                    // make the view visible and start the animation
                    myView.setVisibility(View.VISIBLE);
                    anim.start();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
                myView.setVisibility(View.VISIBLE);
            }
        }
        else {
            myView.setVisibility(View.VISIBLE);
        }

    }

    public static void exitReveal(final View myView) {
        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ) {
            myView.setVisibility(View.INVISIBLE);
            return;
        }
        try {
            if( myView != null ) {
                // previously visible view

                // get the center for the clipping circle
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight() / 2;

                // get the initial radius for the clipping circle
                int initialRadius = myView.getWidth() / 2;

                // create the animation (the final radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myView.setVisibility(View.INVISIBLE);
                    }
                });

                // start the animation
                anim.start();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            myView.setVisibility(View.INVISIBLE);
        }


    }

    public static void exitRevealGone(final View myView) {
        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ) {
            myView.setVisibility(View.GONE);
            return;
        }
        try {
            if( myView != null ) {
                // previously visible view

                // get the center for the clipping circle
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight() / 2;

                // get the initial radius for the clipping circle
                int initialRadius = myView.getWidth() / 2;

                // create the animation (the final radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myView.setVisibility(View.GONE);
                    }
                });

                // start the animation
                anim.start();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            myView.setVisibility(View.GONE);
        }


    }

    public static void slideDown(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_down);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideDownGone(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_down);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideInToRight(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.anim_slide_in_right);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideInToLeft(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.anim_slide_in_left);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideOutToRight(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.anim_slide_out_right);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideOutToLeft(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.anim_slide_out_left);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideOut(final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.anim_slide_out_left);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideUpVisible( final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_up);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }

    public static void slideUp( final View view )
    {
        if( view != null ) {
            Animation viewAnimation = android.view.animation.AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_up);
            viewAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(viewAnimation);
        }

    }
}
