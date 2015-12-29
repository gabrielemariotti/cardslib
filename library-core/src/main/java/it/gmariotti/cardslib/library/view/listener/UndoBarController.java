/*
 * ******************************************************************************
 *   Copyright (c) 2013 Roman Nurik, 2013-2014 Gabriele Mariotti.
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
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * It is based on Roman Nurik code.
 * See this link for original code:
 * https://code.google.com/p/romannurik-code/source/browse/#git%2Fmisc%2Fundobar
 *
 *
 */
public class UndoBarController {

    private View mBarView;
    private TextView mMessageView;
    private ViewPropertyAnimator mBarAnimator;
    private Handler mHideHandler = new Handler();

    private UndoListener mUndoListener;
    private UndoBarHideListener mUndoBarHideListener;

    // State objects
    private Parcelable mUndoToken;
    private CharSequence mUndoMessage;

    private UndoBarUIElements mUndoBarUIElements;

    /**
     * Interface to listen the undo controller actions
     */
    public interface UndoListener {
        /*
         *  Called when you undo the action
         */
        void onUndo(Parcelable undoToken);
    }

    /**
     * Interface to listen for when the Undo controller hides the Undo Bar,
     * after it times out from being shown for the currently dismissed mUndoToken.
     */
    public interface UndoBarHideListener {
        /**
         * Called when the UndoBar is hidden after being shown.
         * @param undoOccurred true if the user pressed the undo button
         *                     for the current mUndoToken.
         */
        void onUndoBarHide(boolean undoOccurred);
    }

    public UndoBarController(View undoBarView, UndoListener undoListener) {
        this (undoBarView,undoListener,null);
    }

    public UndoBarController(View undoBarView, UndoListener undoListener,UndoBarUIElements undoBarUIElements) {
        mBarView = undoBarView;
        mBarAnimator = mBarView.animate();
        mUndoListener = undoListener;

        if (undoBarUIElements==null)
            undoBarUIElements = new DefaultUndoBarUIElements();
        mUndoBarUIElements = undoBarUIElements;

        mMessageView = (TextView) mBarView.findViewById(mUndoBarUIElements.getUndoBarMessageId());
        mBarView.findViewById(mUndoBarUIElements.getUndoBarButtonId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideUndoBar(false);
                        mUndoListener.onUndo(mUndoToken);
                        // Remove the reference to the undo token, since the undo has occurred.
                        mUndoToken = null;
                    }
                });

        setupAnimation();

        if (mUndoBarUIElements.isEnabledUndoBarSwipeAction() != UndoBarUIElements.SwipeDirectionEnabled.NONE){
            setupSwipeActionOnUndoBar();
        }

        hideUndoBar(true);
    }



    public void showUndoBar(boolean immediate, CharSequence message,
                            Parcelable undoToken, UndoBarHideListener undoBarHideListener) {

        // We're replacing the existing UndoBarHideListener, meaning that
        // the original object removal was not undone. So, execute
        // onUndoBarHide for the previous listener.
        if (mUndoBarHideListener != null) {
            mUndoBarHideListener.onUndoBarHide(mUndoToken == null);
        }


        mUndoToken = undoToken;
        mUndoMessage = message;
        mUndoBarHideListener = undoBarHideListener;
        mMessageView.setText(mUndoMessage);

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable,
                mBarView.getResources().getInteger(R.integer.list_card_undobar_hide_delay));

        mBarView.setVisibility(View.VISIBLE);
        if (immediate) {
            mBarView.setAlpha(1);
        } else {

            if (mUndoBarUIElements.getAnimationType() == UndoBarUIElements.AnimationType.ALPHA) {

                mBarAnimator.cancel();
                mBarAnimator
                        .alpha(1)
                        .setDuration(
                                mBarView.getResources()
                                        .getInteger(android.R.integer.config_shortAnimTime))
                        .setListener(null);
            } else if (mUndoBarUIElements.getAnimationType() == UndoBarUIElements.AnimationType.TOPBOTTOM){

                mBarAnimator.cancel();
                mBarAnimator
                        .alpha(1)
                        .translationY(0)
                        .setDuration(
                                mBarView.getResources()
                                        .getInteger(android.R.integer.config_mediumAnimTime))
                        .setListener(null);

            }
        }
    }

    public void hideUndoBar(boolean immediate) {
        mHideHandler.removeCallbacks(mHideRunnable);
        if (immediate) {
            mBarView.setVisibility(View.GONE);
            mBarView.setAlpha(0);
            mUndoMessage = null;
            if (mUndoBarHideListener != null) {
                // The undo has occurred only if mUndoToken was set to null.
                mUndoBarHideListener.onUndoBarHide(mUndoToken == null);
            }
            mUndoBarHideListener = null;
            mUndoToken = null;
        } else {
            mBarAnimator.cancel();

            if (mUndoBarUIElements.getAnimationType() == UndoBarUIElements.AnimationType.ALPHA) {
                mBarAnimator
                        .alpha(0)
                        .setDuration(mBarView.getResources()
                                .getInteger(android.R.integer.config_shortAnimTime))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mBarView.setVisibility(View.GONE);
                                mUndoMessage = null;
                                if (mUndoBarHideListener != null) {
                                    // The undo has occurred only if mUndoToken was set to null.
                                    mUndoBarHideListener.onUndoBarHide(mUndoToken == null);
                                }
                                mUndoBarHideListener = null;
                                mUndoToken = null;
                            }
                        });
            } else if (mUndoBarUIElements.getAnimationType() == UndoBarUIElements.AnimationType.TOPBOTTOM){
                mBarAnimator
                        .alpha(0)
                        .translationY(+mBarView.getHeight())
                        .setDuration(mBarView.getResources()
                                .getInteger(android.R.integer.config_shortAnimTime))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mBarView.setVisibility(View.GONE);
                                mUndoMessage = null;
                                if (mUndoBarHideListener != null) {
                                    // The undo has occurred only if mUndoToken was set to null.
                                    mUndoBarHideListener.onUndoBarHide(mUndoToken == null);
                                }
                                mUndoBarHideListener = null;
                                mUndoToken = null;
                            }
                        });
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("undo_message", mUndoMessage);
        outState.putParcelable("undo_token", mUndoToken);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUndoMessage = savedInstanceState.getCharSequence("undo_message");
            mUndoToken = savedInstanceState.getParcelable("undo_token");

            if (mUndoToken != null || !TextUtils.isEmpty(mUndoMessage)) {
                showUndoBar(true, mUndoMessage, mUndoToken, mUndoBarHideListener);
            }
        }
    }

    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hideUndoBar(false);
        }
    };

    public Parcelable getUndoToken(){
        return mUndoToken;
    }

    private void setupAnimation(){
        if (mUndoBarUIElements.getAnimationType() == UndoBarUIElements.AnimationType.TOPBOTTOM) {
            mBarView.setTranslationY(mBarView.getHeight());
        }
    }

    private void setupSwipeActionOnUndoBar() {
        if (mBarView != null) {

            if (mUndoBarUIElements.isEnabledUndoBarSwipeAction() == UndoBarUIElements.SwipeDirectionEnabled.LEFTRIGHT) {

                mBarView.setOnTouchListener(new SwipeDismissTouchListener(mBarView, null,
                        new SwipeDismissTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(Object token) {
                                return mUndoBarUIElements.isEnabledUndoBarSwipeAction() != UndoBarUIElements.SwipeDirectionEnabled.NONE;
                            }

                            @Override
                            public void onDismiss(View view, Object token) {
                                hideUndoBar(true);
                                mUndoListener.onUndo(mUndoToken);
                                // Remove the reference to the undo token, since the undo has occurred.
                                mUndoToken = null;
                            }
                        }));
            } else if (mUndoBarUIElements.isEnabledUndoBarSwipeAction() == UndoBarUIElements.SwipeDirectionEnabled.TOPBOTTOM) {

                mBarView.setOnTouchListener(new SwipeDismissTopBottomTouchListener(mBarView, null,
                        new SwipeDismissTopBottomTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(Object token) {
                                return mUndoBarUIElements.isEnabledUndoBarSwipeAction() != UndoBarUIElements.SwipeDirectionEnabled.NONE;
                            }

                            @Override
                            public void onDismiss(View view, Object token) {
                                hideUndoBar(true);
                                mUndoListener.onUndo(mUndoToken);
                                // Remove the reference to the undo token, since the undo has occurred.
                                mUndoToken = null;
                            }
                        }));
            }
        }

    }

    // -------------------------------------------------------------
    //  Undo Custom Bar
    // -------------------------------------------------------------

    /**
     * Interface to set the ui elements in undo bar
     */
    public interface UndoBarUIElements{

        /**
         * UndoBar id
         * @return
         */
        public int getUndoBarId();

        /**
         * TextView Id which displays message
         *
         * @return
         */
        public int getUndoBarMessageId();

        /**
         * UndoButton Id
         *
         * @return
         */
        public int getUndoBarButtonId();

        /**
         * UndoMessage.
         * Implement this method to customize the undo message dynamically.
         * </p>
         * You can't find the cards with these positions in your arrayAdapter because the cards are removed.
         * You have to/can use your id itemIds, to identify your cards.
         *
         * @param cardArrayAdapter  array Adapter
         * @param itemIds           ids of items
         * @param itemPositions     position of removed items
         * @return
         */
        public String getMessageUndo(CardArrayAdapter cardArrayAdapter,String[] itemIds,int[] itemPositions);

        /**
         * Define the swipe action to remove the undobar.
         * @return
         */
        public SwipeDirectionEnabled isEnabledUndoBarSwipeAction();

        /**
         * Define the animation type for the undobar when it appears.
         * @return
         */
        public AnimationType getAnimationType();


        /**
         * Enum to define the animation type of the undobar.<p/>
         * Use the {@link AnimationType#ALPHA} for an alpha animation, or {@link AnimationType#TOPBOTTOM} for a translation from bottom to top.
         */
        public enum AnimationType {
            ALPHA(0),
            TOPBOTTOM(1);

            private final int mValue;

            private AnimationType(int value) {
                mValue = value;
            }

            public int getValue() {
                return mValue;
            }
        }

        /**
         *  Enum to define the direction of the swipe action.
         *  <p/>
         *  You can use {@link SwipeDirectionEnabled#NONE}  to disable the swipe action or  {@link SwipeDirectionEnabled#LEFTRIGHT} to enable an action in left-right direction
         *  or {@link SwipeDirectionEnabled#TOPBOTTOM} to define a swipe action from top to bottom.
         */
        public enum SwipeDirectionEnabled {
            NONE(0),
            LEFTRIGHT(1),
            TOPBOTTOM(2);

            private final int mValue;

            private SwipeDirectionEnabled(int value) {
                mValue = value;
            }

            public int getValue() {
                return mValue;
            }
        }

    }

    /**
     * Default UndoBar
     *
     * You can provide a custom UndoBar.
     * This UndoBar has to contains these elements:
     * <ul>
     *    <li>A TextView</li>
     *    <li>A Button</li>
     *    <li>A root element with an id attribute </li>
     * </ul>
     *
     *  You should use the same Ids provided in the default layout list_card_undo_message,
     *  but if you have to use different ids you can use the CardArrayAdapter.setUndoBarUIElements.
     *
     *  Example:
     *  <code>
     *      mCardArrayAdapter.setUndoBarUIElements(new UndoBarController.DefaultUndoBarUIElements(){
     *
     *          //Override methods to customize the elements
     *      }
     *  </code>
     * It is very important to set the UndoBarUIElements before to call the setEnableUndo(true);
     *
     */
    public static class DefaultUndoBarUIElements implements UndoBarUIElements {

        public DefaultUndoBarUIElements(){};

        @Override
        public int getUndoBarId() {
            return R.id.list_card_undobar;
        }

        @Override
        public int getUndoBarMessageId() {
            return R.id.list_card_undobar_message;
        }

        @Override
        public int getUndoBarButtonId() {
            return R.id.list_card_undobar_button;
        }

        @Override
        public String getMessageUndo(CardArrayAdapter cardArrayAdapter, String[] itemIds, int[] itemPositions) {
            if (cardArrayAdapter!=null && cardArrayAdapter.getContext()!=null) {
                Resources res = cardArrayAdapter.getContext().getResources();
                if (res!=null)
                    return res.getQuantityString(R.plurals.list_card_undo_items, itemPositions.length, itemPositions.length);
            }
            return null;
        }

        @Override
        public SwipeDirectionEnabled isEnabledUndoBarSwipeAction() {
            return SwipeDirectionEnabled.NONE;
        }

        @Override
        public AnimationType getAnimationType() {
            return AnimationType.ALPHA;
        }

    };


    /**
     * Sets UndoBar UI Elements
     *
     * @return
     */
    public UndoBarUIElements getUndoBarUIElements() {
        return mUndoBarUIElements;
    }

    /**
     * Returns UndoBar UI Elements
     * @param undoBarUIElements
     */
    public void setUndoBarUIElements(UndoBarUIElements undoBarUIElements) {
        this.mUndoBarUIElements = undoBarUIElements;
    }
}
