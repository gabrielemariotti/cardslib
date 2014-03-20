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

package it.gmariotti.cardslib.demo.extras.staggered;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.staggered.data.Image;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class DynamicHeightPicassoCardThumbnailView extends CardThumbnailView {

    Picasso picasso;
    private RequestCreator request;

    protected float mHeightRatio = 1;
    protected Image image;
    //protected ImageView imageView;

    public DynamicHeightPicassoCardThumbnailView(Context context) {
        super(context);
    }

    public DynamicHeightPicassoCardThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightPicassoCardThumbnailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //public void bindInto(ImageView imageView) {
    //    this.imageView = imageView;
    //}

    public void bindTo(Image item) {
        this.image = item;
        if (picasso == null)
            initPicasso();
        request = picasso.load(item.link);
        mHeightRatio = 1f * item.width / item.height;
        requestLayout();
    }

    /**
     * Init Picasso
     */
    private void initPicasso() {

        boolean useCache=getResources().getBoolean(R.bool.carddemo_staggered_cache);

        Picasso.Builder builder= new Picasso.Builder(getContext())
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                        Log.e("DynamicHeightPicassoCardThumbnailView", "Failed to load image: " + uri, e);
                    }
                });

        if (!useCache){
                    builder
                    .memoryCache(Cache.NONE);
        }

        picasso = builder.build();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("layout_width must be match_parent");
        }

        int width = MeasureSpec.getSize(widthMeasureSpec);
        // Honor aspect ratio for height but no larger than 2x width.
        int height = Math.min((int) (width / mHeightRatio), width * 2);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Load the image
        if (request != null) {
            request.resize(width, height).centerCrop().into(mImageView);
            request = null;
        }
    }
}
