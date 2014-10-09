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
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.gmariotti.cardslib.library.cards.base.BaseMaterialCard;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class HalfColoredCard extends BaseMaterialCard {

    /**
     *  Resource Background Color ID
     */
    protected @ColorRes
    int mColorResourceId;

    /**
     * Title to use for the title over the color
     */
    protected CharSequence mTitleOverColor;

    /**
     * Resource Id to use for the title over the color
     */
    protected @StringRes
    int mTitleOverColorResId;

    /**
     * Title to use for the subtitle over the color
     */
    protected CharSequence mSubTitleOverColor;

    /**
     * Resource Id to use for the subtitle over the color
     */
    protected @StringRes
    int mSubTitleOverColorResId;

    /**
     * An identifier for the layout resource to inflate when the ViewStub becomes visible
     */
    private @LayoutRes int mSecondHalfViewStubLayoutId;


    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public HalfColoredCard(Context context) {
        this(context, R.layout.native_material_halfcolored_inner_base_main);
    }

    public HalfColoredCard(Context context, @LayoutRes int innerLayout) {
        super(context, innerLayout);
    }

    // -------------------------------------------------------------
    // Build
    // -------------------------------------------------------------

    @Override
    public void build(){


    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //Use the title in super method
        super.setupInnerViewElements(parent, view);

        //Add a simple subtitle
        if (view != null) {

            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.mLinearLayoutContainer = (LinearLayout) view.findViewById(R.id.card_main_inner_halfcolored_layout);
                holder.mTitleView = (TextView) view.findViewById(R.id.card_main_inner_halfcolored_title);
                holder.mSubTitleView = (TextView) view.findViewById(R.id.card_main_inner_halfcolored_subtitle);
                View viewStub = ((View)getCardView()).findViewById(R.id.card_halfcolored_secondhalf);
                if (viewStub != null) {
                    ((ViewStub)viewStub).setLayoutResource(mSecondHalfViewStubLayoutId);
                    ((ViewStub)viewStub).inflate();
                    holder.mSecondHalfView= ((View)getCardView()).findViewById(R.id.card_halfcolored_secondhalf_layout);
                }
                view.setTag(holder);
            }

            //Color the LinearLayout
            if (holder.mLinearLayoutContainer != null && mColorResourceId != 0) {
                holder.mLinearLayoutContainer.setBackgroundColor(mContext.getResources().getColor(mColorResourceId));
            }

            //Set the text elements
            buildTitleOverColor(holder);
            buildSubTitleOverColor(holder);

            //Set the second half area
            if (holder.mSecondHalfView!=null && mSecondHalfViewStubLayoutId!=0 ){
                setupInnerViewElementsSecondHalf(holder.mSecondHalfView);
            }

        }
    }

    static class ViewHolder {

        /**
         * LinearLayout
         */
        LinearLayout mLinearLayoutContainer;

        /**
         * Title
         */
        private TextView mTitleView;

        /**
         * SubTitle
         */
        private TextView mSubTitleView;

        /**
         * ViewStub for the secondHalf card
         */
        private View mSecondHalfView;

    }

    protected abstract void setupInnerViewElementsSecondHalf(View secondHalfView);


    /**
     * Build the title to be applied over the color
     */
    protected void  buildTitleOverColor(ViewHolder viewHolder){

        if (viewHolder != null && viewHolder.mTitleView != null) {
            if (mTitleOverColorResId != 0) {
                if (mContext != null)
                    viewHolder.mTitleView.setText(mContext.getResources().getString(mTitleOverColorResId));
            } else {
                viewHolder.mTitleView.setText(mTitleOverColor);
            }
        }
    }

    /**
     * Build the subtitle to be applied over the color
     */
    protected void  buildSubTitleOverColor(ViewHolder viewHolder){

        if (viewHolder != null && viewHolder.mSubTitleView != null) {
            if (mSubTitleOverColorResId != 0) {
                if (mContext != null)
                    viewHolder.mSubTitleView.setText(mContext.getResources().getString(mSubTitleOverColorResId));
            } else {
                viewHolder.mSubTitleView.setText(mSubTitleOverColor);
            }
        }
    }

    // -------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------


    public int getColorResourceId() {
        return mColorResourceId;
    }

    public void setColorResourceId(int colorResourceId) {
        mColorResourceId = colorResourceId;
    }

    public CharSequence getTitleOverColor() {
        return mTitleOverColor;
    }

    public void setTitleOverColor(CharSequence titleOverColor) {
        mTitleOverColor = titleOverColor;
    }

    public int getTitleOverColorResId() {
        return mTitleOverColorResId;
    }

    public void setTitleOverColorResId(int titleOverColorResId) {
        mTitleOverColorResId = titleOverColorResId;
    }

    public CharSequence getSubTitleOverColor() {
        return mSubTitleOverColor;
    }

    public void setSubTitleOverColor(CharSequence subTitleOverColor) {
        mSubTitleOverColor = subTitleOverColor;
    }

    public int getSubTitleOverColorResId() {
        return mSubTitleOverColorResId;
    }

    public void setSubTitleOverColorResId(int subTitleOverColorResId) {
        mSubTitleOverColorResId = subTitleOverColorResId;
    }

    public void setSecondHalfViewStubLayoutId(int secondHalfViewStubLayoutId) {
        mSecondHalfViewStubLayoutId = secondHalfViewStubLayoutId;
    }
}
