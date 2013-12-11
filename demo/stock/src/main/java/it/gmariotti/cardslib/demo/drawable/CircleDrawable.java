/*
 * ******************************************************************************
 *   Copyright (c) 2013 Gabriele Mariotti.
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

package it.gmariotti.cardslib.demo.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Image with rounded corners
 *
 * You can find the original source here:
 * http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CircleDrawable extends Drawable {

    private static final boolean USE_VIGNETTE = true;


    private final BitmapShader mBitmapShader;
    private final Paint mPaint;
    int circleCenterX;
    int circleCenterY;
    int mRadus;

    public CircleDrawable(Bitmap bitmap) {

        mBitmapShader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        circleCenterX = bounds.width() / 2;
        circleCenterY = bounds.height() / 2;

        if (bounds.width()>=bounds.height())
            mRadus= bounds.width() / 2;
        else
            mRadus= bounds.height() / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(circleCenterX, circleCenterY, mRadus, mPaint);
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


}