
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;


public class SwipeDismissListItemViewTouchListener implements View.OnTouchListener {

    private CardWithList.ListObject listObject;
    private LinearListView listView;

    // Cached ViewConfiguration and system-wide constant values
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;

    // Fixed properties
    private View itemView;
    //private DismissCallbacks mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

    // Transient properties
    private float mDownX;
    //private Card mToken;
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;
    private boolean mPaused;
    private float mTranslationX;


   public SwipeDismissListItemViewTouchListener(View itemView, LinearListView mListView, CardWithList.ListObject listObject){
       this.listObject = listObject;
       this.listView = mListView;
       this.itemView = itemView;
   }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // offset because the view is translated during swipe
        motionEvent.offsetLocation(mTranslationX, 0);

        if (mViewWidth < 2) {
            mViewWidth = itemView.getWidth();
        }

        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (mPaused) {
                    return false;
                }

                // TODO: ensure this is a finger, and set a flag

                mDownX = motionEvent.getRawX();
                if (true) {
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                view.onTouchEvent(motionEvent);
                //return true;
                return false;  //fixing swipe and click together
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

                    itemView.animate()
                            .translationX(dismissRight ? mViewWidth : -mViewWidth)
                            .alpha(0).setDuration(mAnimationTime)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                   // performDismiss()
                                    Log.d("TAG","Desmis....");

                                    //itemView.invalidate();
                                    listView.getAdapter().remove(listObject);
                                    ;
                                }
                            });
                } else {
                    // cancel
                    itemView.animate().translationX(0).alpha(1)
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
                    itemView.getParent().requestDisallowInterceptTouchEvent(true);

                    // Cancel ListView's touch (un-highlighting the item)
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent
                            .setAction(MotionEvent.ACTION_CANCEL
                                    | (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    itemView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }

                if (mSwiping) {
                    mTranslationX = deltaX;
                    itemView.setTranslationX(deltaX);
                   /* itemView.setAlpha(Math.max(0f,
                            Math.min(1f, 1f - 2f * Math.abs(deltaX) / mViewWidth)));*/
                    return true;
                }
                break;
            }
        }
        return false;
    }
}