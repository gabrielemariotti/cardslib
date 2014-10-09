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

import it.gmariotti.cardslib.library.cards.R;
import it.gmariotti.cardslib.library.cards.base.BaseMaterialCard;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MaterialLargeImageCard extends BaseMaterialCard {

    /**
     *  Resource Drawable ID
     */
    protected @DrawableRes int mDrawableCardThumbnail;

    /**
     * Resource Drawable URL
     */
    protected String mUrlCardThumbnail;

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
    // Build
    // -------------------------------------------------------------

    @Override
    public void build(){

        //Set CardThumbnail
        if (mCardThumbnail == null) {
            mCardThumbnail = new MaterialLargeImageCardThumbnail(mContext);

            if (mDrawableCardThumbnail != 0) {
                mCardThumbnail.setDrawableResource(mDrawableCardThumbnail);
            }else if (mUrlCardThumbnail != null){
                mCardThumbnail.setUrlResource(mUrlCardThumbnail);
            }
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
            TextView mTitleView = (TextView) view.findViewById(R.id.card_main_inner_simple_subtitle);
            if (mTitleView != null)
                mTitleView.setText(mSubTitle);
        }
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
    public int getDrawableCardThumbnail() {
        return mDrawableCardThumbnail;
    }

    /**
     * Sets the Resource Drawable ID
     *
     * @param drawableCardThumbnail
     */
    public void setDrawableCardThumbnail(int drawableCardThumbnail) {
        mDrawableCardThumbnail = drawableCardThumbnail;
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
