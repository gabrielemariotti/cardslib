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
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import it.gmariotti.cardslib.library.R;

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
                    }
                });

        hideUndoBar(true);
    }

    public void showUndoBar(boolean immediate, CharSequence message, Parcelable undoToken) {

        mUndoToken = undoToken;
        mUndoMessage = message;
        mMessageView.setText(mUndoMessage);

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable,
                mBarView.getResources().getInteger(R.integer.list_card_undobar_hide_delay));

        mBarView.setVisibility(View.VISIBLE);
        if (immediate) {
            mBarView.setAlpha(1);
        } else {
            mBarAnimator.cancel();
            mBarAnimator
                    .alpha(1)
                    .setDuration(
                            mBarView.getResources()
                                    .getInteger(android.R.integer.config_shortAnimTime))
                    .setListener(null);
        }
    }

    public void hideUndoBar(boolean immediate) {
        mHideHandler.removeCallbacks(mHideRunnable);
        if (immediate) {
            mBarView.setVisibility(View.GONE);
            mBarView.setAlpha(0);
            mUndoMessage = null;
            mUndoToken = null;

        } else {
            mBarAnimator.cancel();
            mBarAnimator
                    .alpha(0)
                    .setDuration(mBarView.getResources()
                            .getInteger(android.R.integer.config_shortAnimTime))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mBarView.setVisibility(View.GONE);
                            mUndoMessage = null;
                            mUndoToken = null;
                        }
                    });
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
                showUndoBar(true, mUndoMessage, mUndoToken);
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


    }

    /**
     * Default UndoBar
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
