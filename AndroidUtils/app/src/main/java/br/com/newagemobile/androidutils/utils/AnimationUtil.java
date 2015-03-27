package br.com.newagemobile.androidutils.utils;

import android.animation.Animator;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * Created by rafaelneiva on 3/23/15.
 */
public class AnimationUtil {

    /**
     * @param v View that will be expanded with animation
     */
    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms + 100
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) + 100);
        v.startAnimation(a);
    }

    /**
     * @param v View that will be closed with animation and visibility will be changed to Gone
     */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms + 100
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) + 100);
        v.startAnimation(a);
    }

    /**
     * @param progressView View to be animated
     * @param context      Context/Activity
     */
    public static void animateIn(final View progressView, final Context context) {
        progressView.setVisibility(View.VISIBLE);
        progressView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int translationValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
                progressView.getViewTreeObserver().removeOnPreDrawListener(this);
                progressView.setAlpha(0);
                progressView.setScaleX(1.8f);
                progressView.setPivotX(progressView.getWidth() / 2);
                progressView.setPivotY(progressView.getHeight() / 2);
                progressView.setScaleY(1.8f);
                progressView.setTranslationY(translationValue);
                progressView.animate()
                        .alpha(1)
                        .translationY(0)
                        .scaleX(1)
                        .scaleY(1)
                        .setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .setListener(null);
                return true;
            }
        });
    }

    /**
     * @param progressView View to be animated
     * @param context      Context/Activity
     */
    public static void animateOut(final View progressView, final Context context) {
        progressView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int translationValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
                progressView.getViewTreeObserver().removeOnPreDrawListener(this);
                progressView.animate()
                        .alpha(0)
                        .translationY(-translationValue)
                        .scaleX(1.8f)
                        .scaleY(1.8f)
                        .setDuration(200)
                        .setInterpolator(new AccelerateInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                progressView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                return true;
            }
        });
    }

}
