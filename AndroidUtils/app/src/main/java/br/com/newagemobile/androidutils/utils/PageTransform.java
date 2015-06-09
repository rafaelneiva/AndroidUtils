package br.com.newagemobile.androidutils.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by rafaelneiva on 6/9/15.
 */
public class PageTransform implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        View backgroundView = page.findViewById(0);
        View object1 = page.findViewById(0);
        View object2 = page.findViewById(0);
        View object3 = page.findViewById(0);
        View object4 = page.findViewById(0);
        View object5 = page.findViewById(0);
        View object6 = page.findViewById(0);

        if (0 < position && position < 1) {
            ViewHelper.setTranslationX(page, pageWidth * -position);
        }
        if (-1 < position && position < 0) {
            ViewHelper.setTranslationX(page, pageWidth * -position);
        }

        if (position <= -1.0f || position >= 1.0f) {
        } else if (position == 0.0f) {
        } else {
            if (backgroundView != null) {
                ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));
            }

            if (object1 != null) {
                ViewHelper.setTranslationX(object1, pageWidth * position);
            }

            // parallax effect
            if (object2 != null) {
                ViewHelper.setTranslationX(object2, pageWidth * position);
            }

            if (object4 != null) {
                ViewHelper.setTranslationX(object4, pageWidth / 2 * position);
            }

            if (object5 != null) {
                ViewHelper.setTranslationX(object5, pageWidth / 2 * position);
            }

            if (object6 != null) {
                ViewHelper.setTranslationX(object6, pageWidth / 2 * position);
            }

            if (object3 != null) {
                ViewHelper.setTranslationY(object3, (float) (pageWidth / 1.2 * position));
            }
        }

    }
}
