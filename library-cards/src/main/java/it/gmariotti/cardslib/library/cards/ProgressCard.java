/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
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

package it.gmariotti.cardslib.library.cards;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ProgressCard extends Card {

    /**
     * Progress View
     */
    private View mProgressView;

    /**
     * Resource Id used which identifies the progressBar
     */
    protected @LayoutRes int progressBarId = R.id.card_native_progressbar;

    /**
     * An identifier for the layout resource to inflate when the ViewStub becomes visible
     */
    protected @LayoutRes int progressBarViewStubLayoutId = R.layout.native_base_progress;

    /**
     * Indicates if the progressBar feature is enabled
     */
    protected boolean useProgressBar = false;

    /**
     * Internal flag to indicate if the list is shown
     */
    private boolean mCardShown;



    public ProgressCard(Context context) {
        super(context);
    }

    public ProgressCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent,view);
        internalSetupProgressBar(parent, view);
    }

    /**
     * Setup the Progress Bar view.
     *
     * @param parent mainContentLayout
     * @param view   innerView
     */
    @SuppressWarnings("UnusedParameters")
    private void internalSetupProgressBar(ViewGroup parent, View view) {
        if (useProgressBar && mProgressView == null) {
            mProgressView = ((View) getCardView()).findViewById(getProgressBarId());
            mCardShown=true;
            if (mProgressView != null) {
                if (mProgressView instanceof ViewStub)
                    ((ViewStub) mProgressView).setLayoutResource(getProgressBarViewStubLayoutId());
                setProgressView(mProgressView);
            }else{
               mProgressView = ((View) getCardView()).findViewById(R.id.card_native_progressbar_inflated);
               setProgressView(mProgressView);
            }
        }
    }

    // -------------------------------------------------------------
    // Progress bar
    // -------------------------------------------------------------

    /**
     * When the current adapter is loading data, the LinearListView can display a special
     * progress Bar.
     *
     * @return The view to show if the adapter is the progress bar is enabled.
     */
    public View getProgressView() {
        return mProgressView;
    }

    /**
     * Sets the view to show as progress bar
     */
    public void setProgressView(View progressView) {
        mProgressView = progressView;
        useProgressBar = progressView != null;
    }

    /**
     * Updates the status of the list and the progress bar.
     *
     * @param shownCard indicates if the list has to be shown
     * @param animate indicates to use an animation between view transition
     */
    public void updateProgressBar(boolean shownCard, boolean animate) {
        if (isUseProgressBar()) {
            if (mCardShown == shownCard) {
                return;
            }
            mCardShown = shownCard;

            if (shownCard) {
                if (animate) {
                    mProgressView.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_out));
                    getCardView().getInternalMainCardLayout().startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_in));

                }
                getCardView().getInternalMainCardLayout().setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);

            } else {
                if (animate) {
                    mProgressView.startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_in));
                    getCardView().getInternalMainCardLayout().startAnimation(AnimationUtils.loadAnimation(
                            getContext(), android.R.anim.fade_out));
                }
                mProgressView.setVisibility(View.VISIBLE);
                getCardView().getInternalMainCardLayout().setVisibility(View.INVISIBLE);
            }
        }
    }

    // -------------------------------------------------------------
    // Getter and setter
    // -------------------------------------------------------------

    private boolean isUseProgressBar() {
        if (mProgressView != null)
            return useProgressBar;
        else
            return false;
    }

    /**
     * Sets the flag to enable and disable the progress bar
     * @param useProgressBar
     */
    public void setUseProgressBar(boolean useProgressBar) {
        this.useProgressBar = useProgressBar;
    }

    /**
     * Returns the resource Id used which identifies the ProgressBar
     * @return the resource Id used which identifies the ProgressBar
     */
    public int getProgressBarId() {
        return progressBarId;
    }

    /**
     * Sets the resource Id used which identifies the ProgressBar
     * @param progressBarId  resource Id used which identifies the ProgressBar
     */
    public void setProgressBarId(int progressBarId) {
        this.progressBarId = progressBarId;
    }


    /**
     * Returns the identifier for the layout resource to inflate when the ViewStub used by the ProgressBar becomes visible
     * It is used only if the {@see useProgressBar) is setted to true and the {@see mProgressView} is a {@link android.view.ViewStub}.
     *
     * @return
     */
    public int getProgressBarViewStubLayoutId() {
        return progressBarViewStubLayoutId;
    }

    /**
     * Sets the identifier for the layout resource to inflate when the ViewStub used by the ProgressBar becomes visible
     *
     * @param progressBarViewStubLayoutId
     */
    public void setProgressBarViewStubLayoutId(@LayoutRes int progressBarViewStubLayoutId) {
        this.progressBarViewStubLayoutId = progressBarViewStubLayoutId;
    }
}
