package com.vmloft.develop.app.vmnote.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by lzan13 on 2018/4/28.
 * 悬浮按钮相对于同布局控件关联实现
 */
public class FABScrollBehavior extends FloatingActionButton.Behavior {
    // 动画插值器
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean isAnimatingOut = false;

    public FABScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
            @NonNull FloatingActionButton child, @NonNull View directTargetChild,
            @NonNull View target, int axes, int type) {
        //        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
            @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed,
            int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        // 列表向上滚动，隐藏 fab
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else {
            if (dyConsumed < 0 && child.getVisibility() == View.INVISIBLE) {
                animateIn(child);
            }
        }
    }

    /**
     * Fab 退出动画，这里实现了动画监听，当动画结束时，隐藏按钮
     * PS: 这里有个坑，是 android 一个 bug，当 design 版本大于25.0.1时，
     * fab 设置为 GONE 后会显示不出来，所以设置为 INVISIBLE
     */
    private void animateOut(final FloatingActionButton button) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) button.getLayoutParams();
        int bottomMargin = layoutParams.bottomMargin;
        if (!isAnimatingOut) {
            ViewCompat.animate(button)
                    .translationY(button.getHeight() + bottomMargin)
                    .setInterpolator(INTERPOLATOR)
                    .withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        public void onAnimationStart(View view) {
                            isAnimatingOut = true;
                        }

                        public void onAnimationCancel(View view) {
                            isAnimatingOut = false;
                        }

                        public void onAnimationEnd(View view) {
                            isAnimatingOut = false;
                            view.setVisibility(View.INVISIBLE);
                        }
                    })
                    .start();
        }
    }

    /**
     * Fab 显示动画
     */
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(null)
                .start();
    }
}
