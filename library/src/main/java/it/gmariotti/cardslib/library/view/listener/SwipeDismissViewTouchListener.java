
/*
 * ******************************************************************************
 *   Copyright (c) 2013 Roman Nurik,2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package it.gmariotti.cardslib.library.view.listener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * It is based on Roman Nurik code.
 * See this link for original code https://github.com/romannurik/Android-SwipeToDismiss
 *
 * </p>
 * It provides a SwipeDismissViewTouchListener for a single Card.
 * </p>
 * If you are using a list, see {@link SwipeDismissListViewTouchListener}
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SwipeDismissViewTouchListener implements View.OnTouchListener {

    // Cached ViewConfiguration and system-wide constant values
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;

    // Fixed properties
    private CardView mCardView;
    private DismissCallbacks mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

    // Transient properties
    private float mDownX;
    private Card mToken;
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;
    private boolean mPaused;
    private float mTranslationX;

    /**
     * The callback interface used by {@link SwipeDismissViewTouchListener}
     * to inform its client about a successful dismissal of the view for which it was created.
     */
    public interface DismissCallbacks {
        /**
         * Called to determine whether the given position can be dismissed.
         */
        boolean canDismiss(Card card);

        /**
         * Called when the user has indicated they she would like to dismiss the view.
         *
         * @param cardView               The originating {@link it.gmariotti.cardslib.library.view.CardView}.
         * @parma card                   Card
         */
        void onDismiss(CardView cardView,Card card);
    }

    /**
     * Constructs a new swipe-to-dismiss touch listener for the given view.
     *
     * @param cardView  The card view which should be dismissable.
     * @param callbacks The callback to trigger when the user has indicated that she
     */
    public SwipeDismissViewTouchListener(CardView cardView,
                                         Card card,
                                         DismissCallbacks callbacks) {
        ViewConfiguration vc = ViewConfiguration.get(cardView.getContext());
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = cardView.getContext().getResources()
                .getInteger(android.R.integer.config_shortAnimTime);
        mCardView = cardView;
        mToken= card;
        mCallbacks = callbacks;
    }

    /**
     * Enables or disables (pauses or resumes) watching for swipe-to-dismiss
     * gestures.
     *
     * @param enabled Whether or not to watch for gestures.
     */
    public void setEnabled(boolean enabled) {
        mPaused = !enabled;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // offset because the view is translated during swipe
        motionEvent.offsetLocation(mTranslationX, 0);

        if (mViewWidth < 2) {
            mViewWidth = mCardView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (mPaused) {
                    return false;
                }

                // TODO: ensure this is a finger, and set a flag

                mDownX = motionEvent.getRawX();
                if (mCallbacks.canDismiss(mToken)) {
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                view.onTouchEvent(motionEvent);
                return true;
                //return false;  fixing swipe and click together
            }

            case MotionEvent.ACTION_UP: {
                if (mVelocityTracker == null) {
                    break;
                }

                float deltaX = motionEvent.getRawX() - mDownX;
                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = mVelocityTracker.getXVelocity();
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
                boolean dismiss = false;
                boolean dismissRight = false;
                if (Math.abs(deltaX) > mViewWidth / 2) {
                    dismiss = true;
                    dismissRight = deltaX > 0;
                } else if (mMinFlingVelocity <= absVelocityX
                        && absVelocityX <= mMaxFlingVelocity
                        && absVelocityY < absVelocityX) {
                    // dismiss only if flinging in the same direction as dragging
                    dismiss = (velocityX < 0) == (deltaX < 0);
                    dismissRight = mVelocityTracker.getXVelocity() > 0;
                }
                if (dismiss) {
                    // dismiss

                    mCardView.animate()
                            .translationX(dismissRight ? mViewWidth : -mViewWidth)
                            .alpha(0).setDuration(mAnimationTime)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    performDismiss();
                                }
                            });
                } else {
                    // cancel
                    mCardView.animate().translationX(0).alpha(1)
                            .setDuration(mAnimationTime).setListener(null);
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mDownX = 0;
                mSwiping = false;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (mVelocityTracker == null || mPaused) {
                    break;
                }

                mVelocityTracker.addMovement(motionEvent);
                float deltaX = motionEvent.getRawX() - mDownX;
                if (Math.abs(deltaX) > mSlop) {
                    mSwiping = true;
                    mCardView.getParent().requestDisallowInterceptTouchEvent(true);

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent
                            .setAction(MotionEvent.ACTION_CANCEL
                                    | (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mCardView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }

                if (mSwiping) {
                    mTranslationX = deltaX;
                    mCardView.setTranslationX(deltaX);
                    mCardView.setAlpha(Math.max(0f,
                            Math.min(1f, 1f - 2f * Math.abs(deltaX) / mViewWidth)));
                    return true;
                }
                break;
            }
        }
        return false;
    }


    private void performDismiss() {
        // Animate the dismissed view to zero-height and then fire the dismiss callback.
        // This triggers layout on each animation frame; in the future we may want to do something
        // smarter and more performant.

        final ViewGroup.LayoutParams lp = mCardView.getLayoutParams();
        final int originalHeight = mCardView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1)
                .setDuration(mAnimationTime);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                mCallbacks.onDismiss(mCardView,mToken);
                // Reset view presentation
                mCardView.setAlpha(1f);
                mCardView.setTranslationX(0);
                //ViewGroup.LayoutParams lp = mCardView.getLayoutParams();
                lp.height = originalHeight;
                mCardView.setLayoutParams(lp);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                mCardView.setLayoutParams(lp);
            }
        });

        animator.start();
    }
}