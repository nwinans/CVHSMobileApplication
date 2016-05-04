package com.benrcarvergmail.cvhsmobileapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// ToDo: Thoroughly comment all of this code.

/**
 * Created by Benjamin on 4/6/2016.
 */
public class AnimatedRecyclerView extends RecyclerView {

    private boolean mScrollable;    // Used to prevent scrolling during initial animation
    private boolean mDoAnimate;     // Used to determine whether or not to animate its children

    public AnimatedRecyclerView(Context context) {
        super(context);
    }

    public AnimatedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScrollable = false;
        // ((SimpleItemAnimator) this.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return !mScrollable || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mDoAnimate) {
            for(int i = 0; i < getChildCount(); i++) {
                animate(getChildAt(i), i);

                if (i == getChildCount() - 1) {
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mScrollable = true;
                        }
                    }, i * 100);
                }
            }

            mDoAnimate = false;     // Since we just animated, we don't need to until further notice.
        } else {
            // We still have to set mScrollable to true, even if we don't want to animate anything.
            // If we neglect to do this, we will not be able to scroll through the various announcements.
            mScrollable = true;
        }
    }

    public boolean getDoAnimate() {
        return mDoAnimate;
    }

    public void setDoAnimate(boolean bool) {
        mDoAnimate = bool;
    }

    private void animate(View view, final int pos) {
        view.animate().cancel();
        view.setTranslationY(100);
        view.setAlpha(0);
        view.animate().alpha(1.0f).translationY(0).setDuration(750).setStartDelay(pos * 100);
    }
}
