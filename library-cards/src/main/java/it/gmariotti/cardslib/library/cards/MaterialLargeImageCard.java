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
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MaterialLargeImageCard extends MaterialCard {

    protected CharSequence mTextOverImage;
    protected @StringRes int mTextOverImageResId;

    protected MaterialLargeImageCardThumbnail mCardThumbnail;
    protected @DrawableRes int mDrawableCardThumbnail;
    protected String mUrlCardThumbnail;

    protected TextView mTitleOverImageView;

    public MaterialLargeImageCard(Context context) {
        super(context);
    }

    public MaterialLargeImageCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    public void build(){

        //Set CardThumbnail
        if (mCardThumbnail == null){
            mCardThumbnail = new MaterialLargeImageCardThumbnail(mContext);
            if (mDrawableCardThumbnail != 0) {
                mCardThumbnail.setDrawableResource(mDrawableCardThumbnail);
            }else if (mUrlCardThumbnail != null){
                mCardThumbnail.setUrlResource(mUrlCardThumbnail);
            }
        }
    }

    public class MaterialLargeImageCardThumbnail extends CardThumbnail{

        public MaterialLargeImageCardThumbnail(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View imageView) {
            super.setupInnerViewElements(parent, imageView);

            mTitleOverImageView = (TextView) parent.findViewById(R.id.card_thumbnail_image_text_over);
            if (mTitleOverImageView != null)
                if (mTextOverImageResId != 0) {
                    mTitleOverImageView.setText(mContext.getResources().getString(mTextOverImageResId));
                } else {
                    mTitleOverImageView.setText(mTextOverImage);
                }
        }
    }

    @Override
    public CardThumbnail getCardThumbnail() {
        return mCardThumbnail;
    }

    public void setCardThumbnail(MaterialLargeImageCardThumbnail cardThumbnail) {
        mCardThumbnail = cardThumbnail;
    }

    public CharSequence getTextOverImage() {
        return mTextOverImage;
    }

    public void setTextOverImage(CharSequence textOverImage) {
        mTextOverImage = textOverImage;
    }

    public int getDrawableCardThumbnail() {
        return mDrawableCardThumbnail;
    }

    public void setDrawableCardThumbnail(int drawableCardThumbnail) {
        mDrawableCardThumbnail = drawableCardThumbnail;
    }

    public String getUrlCardThumbnail() {
        return mUrlCardThumbnail;
    }

    public void setUrlCardThumbnail(String urlCardThumbnail) {
        mUrlCardThumbnail = urlCardThumbnail;
    }



}
