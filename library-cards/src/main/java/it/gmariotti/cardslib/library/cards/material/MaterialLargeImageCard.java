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

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MaterialLargeImageCard extends MaterialCard {

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

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public MaterialLargeImageCard(Context context) {
        super(context);
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



}
