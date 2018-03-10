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

package it.gmariotti.cardslib.library.cards.topcolored;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.gmariotti.cardslib.library.cards.R;
import it.gmariotti.cardslib.library.cards.base.BaseMaterialCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class TopColoredCard extends BaseMaterialCard {

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


    private OnSetupInnerElements mOnSetupInnerElements;

    /**
     * Callback to setup inner Elements
     */
    public interface OnSetupInnerElements {
        void setupInnerViewElementsSecondHalf(View secondHalfView);
    }

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public TopColoredCard(Context context) {
        this(context, R.layout.native_material_topcolored_inner_base_main);
    }

    public TopColoredCard(Context context, @LayoutRes int innerLayout) {
        super(context, innerLayout);
    }

    // -------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------

    public static SetupWizard with(Context context) {
        return new SetupWizard(context);
    }

    public static final class SetupWizard {
        private final Context mContext;
        protected @ColorRes   int mColorResourceId;
        protected CharSequence mTitleOverColor;
        protected @StringRes  int mTitleOverColorResId;
        protected CharSequence mSubTitleOverColor;
        protected @StringRes   int mSubTitleOverColorResId;
        private @LayoutRes int mSecondHalfViewStubLayoutId;
        private OnSetupInnerElements mOnSetupInnerElements;

        private SetupWizard(Context context) {
            mContext = context;
        }

        public SetupWizard setColorResId(@ColorRes int colorId){
            mColorResourceId = colorId;
            return this;
        }

        public SetupWizard setTitleOverColor(CharSequence textOverColor){
            mTitleOverColor = textOverColor;
            return this;
        }

        public SetupWizard setTitleOverColor(int textOverColorResId){
            mTitleOverColorResId = textOverColorResId;
            return this;
        }

        public SetupWizard setSubTitleOverColor(CharSequence subtitleOverColor){
            mSubTitleOverColor = subtitleOverColor;
            return this;
        }

        public SetupWizard setSubTitleOverColor(int subtitleOverColorResId){
            mSubTitleOverColorResId = subtitleOverColorResId;
            return this;
        }

        public SetupWizard setupSubLayoutId(@LayoutRes int layoutId){
            mSecondHalfViewStubLayoutId = layoutId;
            return this;
        }

        public SetupWizard setupInnerElements(OnSetupInnerElements innerElements){
            mOnSetupInnerElements = innerElements;
            return this;
        }

        public TopColoredCard build() {
            TopColoredCard card = new TopColoredCard(mContext);
            card.setColorResourceId(mColorResourceId);
            card.setTitleOverColor(mTitleOverColor);
            card.setTitleOverColorResId(mTitleOverColorResId);
            card.setSubTitleOverColor(mSubTitleOverColor);
            card.setSubTitleOverColorResId(mSubTitleOverColorResId);
            card.setSecondHalfViewStubLayoutId(mSecondHalfViewStubLayoutId);
            card.setOnSetupInnerElements(mOnSetupInnerElements);
            card.build();
            return card;
        }
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
                holder.mLinearLayoutContainer = view.findViewById(R.id.card_main_inner_topcolored_layout);
                holder.mTitleView = view.findViewById(R.id.card_main_inner_topcolored_title);
                holder.mSubTitleView = view.findViewById(R.id.card_main_inner_topcolored_subtitle);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    holder.mLinearLayoutContainer.setBackgroundColor(mContext.getResources().getColor(mColorResourceId));
                else{
                    ((CardViewNative)(getCardView()) ).setPreventCornerOverlap(false);
                    GradientDrawable shapeDrawable = (GradientDrawable) (holder.mLinearLayoutContainer.getBackground());
                    shapeDrawable.setColor(mContext.getResources().getColor(mColorResourceId));
                }
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

    protected  void setupInnerViewElementsSecondHalf(View secondHalfView){

        if (mOnSetupInnerElements != null)
            mOnSetupInnerElements.setupInnerViewElementsSecondHalf(secondHalfView);

    }


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
                viewHolder.mSubTitleView.setVisibility(View.VISIBLE);
                if (mContext != null)
                    viewHolder.mSubTitleView.setText(mContext.getResources().getString(mSubTitleOverColorResId));
            } else if (mSubTitleOverColor != null) {
                viewHolder.mSubTitleView.setText(mSubTitleOverColor);
                viewHolder.mSubTitleView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mSubTitleView.setVisibility(View.GONE);
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


    public void setOnSetupInnerElements(OnSetupInnerElements onSetupInnerElements) {
        mOnSetupInnerElements = onSetupInnerElements;
    }
}
