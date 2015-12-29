/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
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

package it.gmariotti.cardslib.library.cards.material.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCardThumbnail;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Image with rounded corners
 *
 * You can find the original source here:
 * http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class RoundCornersDrawable extends Drawable {

    private static final boolean USE_VIGNETTE = false;

    private final float mCornerRadius;
    private final RectF mRect = new RectF();
    private final RectF mRectBottomR = new RectF();
    private final RectF mRectBottomL = new RectF();
    private final BitmapShader mBitmapShader;
    private final Paint mPaint;
    private final int mMargin;

    public RoundCornersDrawable(Bitmap bitmap, float cornerRadius, int margin) {
        mCornerRadius = cornerRadius;

        mBitmapShader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);

        mMargin = margin;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRect.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);
        mRectBottomR.set( (bounds.width() -mMargin) / 2, (bounds.height() -mMargin)/ 2,bounds.width() - mMargin, bounds.height() - mMargin);
        mRectBottomL.set( 0,  (bounds.height() -mMargin) / 2, (bounds.width() -mMargin)/ 2, bounds.height() - mMargin);

        if (USE_VIGNETTE) {
            RadialGradient vignette = new RadialGradient(
                    mRect.centerX(), mRect.centerY() * 1.0f / 0.7f, mRect.centerX() * 1.3f,
                    new int[] { 0, 0, 0x7f000000 }, new float[] { 0.0f, 0.7f, 1.0f },
                    Shader.TileMode.CLAMP);

            Matrix oval = new Matrix();
            oval.setScale(1.0f, 0.7f);
            vignette.setLocalMatrix(oval);

            mPaint.setShader(
                    new ComposeShader(mBitmapShader, vignette, PorterDuff.Mode.SRC_OVER));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
        canvas.drawRect(mRectBottomR, mPaint);
        canvas.drawRect(mRectBottomL, mPaint);

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }


    /**
     * This method applies a top rounded corners to the bitmap for android api<21
     * and attaches the bitmap to the view.
     *
     * @param thumbnail   @link{MaterialLargeImageCardThumbnail}
     * @param imageView   the view
     * @param bitmap      the bitmap
     */
    public static boolean applyRoundedCorners(MaterialLargeImageCardThumbnail thumbnail,View imageView, Bitmap bitmap){

        if (thumbnail!=null && thumbnail.getParentCard()!= null
                && thumbnail.getParentCard().getCardView() != null
                && thumbnail.getParentCard().getCardView() instanceof CardViewNative) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ((CardViewNative) thumbnail.getParentCard().getCardView()).setPreventCornerOverlap(false);
                RoundCornersDrawable round = new RoundCornersDrawable(bitmap, ((CardViewNative) thumbnail.getParentCard().getCardView()).getRadius(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    imageView.setBackground(round);
                else
                    imageView.setBackgroundDrawable(round);

                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}