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

package it.gmariotti.cardslib.library.cards.material;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.R;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.base.BaseMaterialCard;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MaterialLargeImageCard extends BaseMaterialCard {

    /**
     *  Resource Drawable ID
     */
    protected @DrawableRes int mDrawableIdCardThumbnail;

    /**
     * Resource Drawable URL
     */
    protected String mUrlCardThumbnail;

    /**
     * Interface for external usage for Thumbnail
     */
    protected DrawableExternal mExternalCardThumbnail;

    /**
     * Title to use for the title over the image
     */
    protected CharSequence mTextOverImage;

    /**
     * Resource Id to use for the title over the image
     */
    protected @StringRes int mTextOverImageResId;

    /**
     * The subtitle
     */
    protected CharSequence mSubTitle;

    public interface DrawableExternal{
        void setupInnerViewElements(ViewGroup parent, View viewImage);
    }

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public MaterialLargeImageCard(Context context) {
        this(context, R.layout.native_material_largeimage_inner_base_main);
    }

    public MaterialLargeImageCard(Context context, @LayoutRes int innerLayout) {
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
        private
        @DrawableRes
        int mDrawableCardThumbnail;
        private String mUrlCardThumbnail;
        private DrawableExternal mExternalCardThumbnail;
        private CharSequence mTextOverImage;
        private
        @StringRes
        int mTextOverImageResId;
        private CharSequence mTitle;
        private CharSequence mSubTitle;
        private ArrayList<BaseSupplementalAction> mActions;
        private int mSupplementalActionLayoutId;

        private SetupWizard(Context context) {
            mContext = context;
        }

        public SetupWizard useDrawableId(@DrawableRes int drawableIdCardThumbnail){
            mDrawableCardThumbnail = drawableIdCardThumbnail;
            return this;
        }

        public SetupWizard useDrawableUrl(String drawableUrlCardThumbnail){
            mUrlCardThumbnail = drawableUrlCardThumbnail;
            return this;
        }

        public SetupWizard useDrawableExternal(DrawableExternal externalCardThumbnail){
            mExternalCardThumbnail = externalCardThumbnail;
            return this;
        }

        public SetupWizard setTextOverImage(CharSequence textOverImage){
            mTextOverImage = textOverImage;
            return this;
        }

        public SetupWizard setTextOverImage(int textOverImageResId){
            mTextOverImageResId = textOverImageResId;
            return this;
        }

        public SetupWizard setTitle(CharSequence title){
            mTitle = title;
            return this;
        }

        public SetupWizard setSubTitle(CharSequence subTitle){
            mSubTitle = subTitle;
            return this;
        }

        public SetupWizard setupSupplementalActions(@LayoutRes int layoutId,ArrayList<BaseSupplementalAction> actions){
           mSupplementalActionLayoutId = layoutId;
           mActions = actions;
           return this;
        }

        public MaterialLargeImageCard build() {
            return build(new MaterialLargeImageCard(mContext));
        }

        public MaterialLargeImageCard build(MaterialLargeImageCard card) {
            if (mExternalCardThumbnail != null){
                card.setExternalCardThumbnail(mExternalCardThumbnail);
            } else {
                card.setDrawableIdCardThumbnail(mDrawableCardThumbnail);
                card.setUrlCardThumbnail(mUrlCardThumbnail);
            }
            card.setTextOverImage(mTextOverImage);
            card.setTextOverImageResId(mTextOverImageResId);
            if (mTitle != null)
                card.setTitle(mTitle.toString());
            card.setSubTitle(mSubTitle);

            if (mActions != null){
                for (BaseSupplementalAction ac:mActions)
                    card.addSupplementalAction(ac);
            }
            card.setLayout_supplemental_actions_id(mSupplementalActionLayoutId);
            card.build();
            return card;
        }
    }

    // -------------------------------------------------------------
    // Build
    // -------------------------------------------------------------

    @Override
    public void build(){

        //Set CardThumbnail
        if (mCardThumbnail == null) {
            mCardThumbnail = initializeCardThumbnail();

            if (mExternalCardThumbnail != null){
                mCardThumbnail.setExternalUsage(true);
                ((MaterialLargeImageCardThumbnail)mCardThumbnail).setExternalCardThumbnail(mExternalCardThumbnail);
            } else {
                if (mDrawableIdCardThumbnail != 0) {
                    mCardThumbnail.setDrawableResource(mDrawableIdCardThumbnail);
                } else if (mUrlCardThumbnail != null) {
                    mCardThumbnail.setUrlResource(mUrlCardThumbnail);
                }
            }
            addCardThumbnail(mCardThumbnail);
        }

        ((MaterialLargeImageCardThumbnail)mCardThumbnail).setTextOverImage(mTextOverImage);
        ((MaterialLargeImageCardThumbnail)mCardThumbnail).setTextOverImageResId(mTextOverImageResId);

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //Use the title in super method
        super.setupInnerViewElements(parent, view);

        //Add a simple subtitle
        if (view != null) {
            TextView mTitleView = view.findViewById(R.id.card_main_inner_simple_subtitle);
            if (mTitleView != null)
                mTitleView.setText(mSubTitle);
        }
    }

    @Override
    public void setOnClickListener(OnCardClickListener onClickListener) {
        addPartialOnClickListener(CLICK_LISTENER_ACTIONAREA1_VIEW, onClickListener);
    }

    /**
     * Initialize the MaterialLargeImageCardThumbnail
     * @return
     */
    protected MaterialLargeImageCardThumbnail initializeCardThumbnail(){
        return new MaterialLargeImageCardThumbnail(mContext);
    }

    // -------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------

    /**
     * Returns
     * the title over the image
     * @return
     */
    public CharSequence getTextOverImage() {
        return mTextOverImage;
    }

    /**
     * Sets the title over the image
     *
     * @param textOverImage
     */
    public void setTextOverImage(CharSequence textOverImage) {
        mTextOverImage = textOverImage;
    }


    /**
     * Sets the Resource Id to use for the title over the image
     * @param textOverImageResId
     */
    public void setTextOverImageResId(int textOverImageResId) {
        mTextOverImageResId = textOverImageResId;
    }


    /**
     * Returns the Resource Drawable ID
     *
     * @return
     */
    public int getDrawableIdCardThumbnail() {
        return mDrawableIdCardThumbnail;
    }

    /**
     * Sets the Resource Drawable ID
     *
     * @param drawableIdCardThumbnail
     */
    public void setDrawableIdCardThumbnail(int drawableIdCardThumbnail) {
        mDrawableIdCardThumbnail = drawableIdCardThumbnail;
    }

    /**
     * Returns the Drawable URL
     * @return
     */
    public String getUrlCardThumbnail() {
        return mUrlCardThumbnail;
    }

    /**
     * Sets the Drawable URL
     *
     * @param urlCardThumbnail
     */
    public void setUrlCardThumbnail(String urlCardThumbnail) {
        mUrlCardThumbnail = urlCardThumbnail;
    }

    /**
     * Sets the interface to be called with the thumbnail
     * @param externalCardThumbnail
     */
    public void setExternalCardThumbnail(DrawableExternal externalCardThumbnail) {
        mExternalCardThumbnail = externalCardThumbnail;
    }


    /**
     * Returns the subtitle
     * @return
     */
    public CharSequence getSubTitle() {
        return mSubTitle;
    }

    /**
     * Sets the subtitle
     * @param subTitle
     */
    public void setSubTitle(CharSequence subTitle) {
        mSubTitle = subTitle;
    }
}
