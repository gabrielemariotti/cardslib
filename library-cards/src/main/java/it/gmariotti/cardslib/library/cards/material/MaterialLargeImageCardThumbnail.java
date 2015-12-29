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
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.cards.R;
import it.gmariotti.cardslib.library.cards.material.utils.RoundCornersDrawable;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * CardThumbanail with a TextView over the images
 */
public class MaterialLargeImageCardThumbnail extends CardThumbnail {

    /**
     * TextView for Title Over the Image
     */
    protected TextView mTitleOverImageView;

    /**
     * Title to use for the title over the image
     */
    protected CharSequence mTextOverImage;

    /**
     * Resource Id to use for the title over the image
     */
    protected @StringRes
    int mTextOverImageResId;

    /**
     *
     */
    protected MaterialLargeImageCard.DrawableExternal mExternalCardThumbnail;

    protected @IdRes
    int default_text_id = R.id.card_thumbnail_image_text_over;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public MaterialLargeImageCardThumbnail(Context context) {
        super(context);
    }

    // -------------------------------------------------------------
    // Build
    // -------------------------------------------------------------

    @Override
    public void setupInnerViewElements(ViewGroup parent, View imageView) {
        super.setupInnerViewElements(parent, imageView);

        if (mExternalCardThumbnail != null){
            mExternalCardThumbnail.setupInnerViewElements(parent,imageView);
        }

        //Title over the image
        mTitleOverImageView = (TextView) parent.findViewById(default_text_id);
        buildTextOverImage();

    }

    @Override
    public boolean applyBitmap(View imageView, Bitmap bitmap) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return RoundCornersDrawable.applyRoundedCorners(this,imageView,bitmap);
        } else {
            return super.applyBitmap(imageView, bitmap);
        }

    }

    // -------------------------------------------------------------
    // Gettters and Setters
    // -------------------------------------------------------------

    /**
     * Sets the textView's identifier
     * @param text_id
     */
    public void setTextViewId(@IdRes int text_id){
        default_text_id = text_id;
    }

    /**
     * Build the text to be applied over the image
     */
    protected void  buildTextOverImage(){

        if (mTitleOverImageView != null) {
            if (mTextOverImageResId != 0) {
                if (mContext != null)
                    mTitleOverImageView.setText(mContext.getResources().getString(mTextOverImageResId));
            } else {
                mTitleOverImageView.setText(mTextOverImage);
            }
        }
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


    public void setExternalCardThumbnail(MaterialLargeImageCard.DrawableExternal externalCardThumbnail) {
        mExternalCardThumbnail = externalCardThumbnail;
    }
}