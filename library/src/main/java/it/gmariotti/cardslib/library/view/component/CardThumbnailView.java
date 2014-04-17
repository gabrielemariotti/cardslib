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

package it.gmariotti.cardslib.library.view.component;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

import it.gmariotti.cardslib.library.Constants;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.utils.CacheUtil;
import it.gmariotti.cardslib.library.view.base.CardViewInterface;

import it.gmariotti.cardslib.library.R;

/**
 * Compound View for Thumbnail Component.
 * </p>
 * It is built with base_thumbnail_layout.xml.
 * </p>
 * Please note that this is currently in a preview state.
 * This means that the API is not fixed and you should expect changes between releases.
 * </p>
 * This class load a bitmap resource using {@link android.util.LruCache} and using an
 * AsyncTask to prevent UI blocks.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardThumbnailView extends FrameLayout implements CardViewInterface {

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default Layout for Thumbnail View
     */
    protected int card_thumbnail_layout_resourceID = R.layout.base_thumbnail_layout;

    /** Global View for this Component */
    protected View mInternalOuterView;

    /**
     * CardThumbnail model
     */
    protected CardThumbnail mCardThumbnail;

    /**
     * Memory Cache
     */
    protected LruCache<String, Bitmap> mMemoryCache;

    /**
     * Used to recycle ui elements.
     */
    protected boolean mIsRecycle=false;

    /**
     * Used to replace inner layout elements.
     */
    protected boolean mForceReplaceInnerLayout =false;


    protected boolean mLoadingErrorResource = false;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public CardThumbnailView(Context context) {
        super(context);
        init(null,0);
    }

    public CardThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public CardThumbnailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs,defStyle);
    }

    //--------------------------------------------------------------------------
    // View
    //--------------------------------------------------------------------------

    /**
     * ImageView inside CardThumbnail
     */
    protected ImageView mImageView;

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------


    /**
     * Initialize
     *
     * @param attrs
     * @param defStyle
     */
    protected void init(AttributeSet attrs, int defStyle){
        //Init attrs
        initAttrs(attrs,defStyle);

        //Init View
        if(!isInEditMode())
            initView();
    }
    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            card_thumbnail_layout_resourceID= a.getResourceId(R.styleable.card_options_card_thumbnail_layout_resourceID, card_thumbnail_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    /**
     * Init view
     */
    protected void initView() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInternalOuterView = inflater.inflate(card_thumbnail_layout_resourceID,this,true);

        //Get ImageVIew
        mImageView= (ImageView) findViewById(R.id.card_thumbnail_image);


        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = CacheUtil.getMemoryCache();
        if (mMemoryCache==null){
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1) {
                        return bitmap.getByteCount() / 1024;
                    } else {
                        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                    }
                }
            };
            CacheUtil.putMemoryCache(mMemoryCache);
        }
    }

    //--------------------------------------------------------------------------
    // Add Thumbnail
    //--------------------------------------------------------------------------

    /**
     * Adds a {@link CardThumbnail}.
     * It is important to set all thumbnail values before launch this method.
     *
     * @param cardThumbail thumbnail model
     */
    public void addCardThumbnail(CardThumbnail cardThumbail ){
        mCardThumbnail=cardThumbail;
        buildUI();
    }

    /**
     * Refresh UI
     */
    protected void buildUI() {
        if (mCardThumbnail==null) return;

        if (mIsRecycle)
            mLoadingErrorResource=false;

        //Setup InnerView
        setupInnerView();
    }


    /**
     * Sets the inner view.
     *
     */
    protected void setupInnerView(){

        //Setup Elements before load image
        if (mInternalOuterView!=null)
            mCardThumbnail.setupInnerViewElements((ViewGroup)mInternalOuterView,mImageView);

        //Load bitmap
        if (!mCardThumbnail.isExternalUsage()){
            if (mCardThumbnail.getCustomSource() != null)
                loadBitmap(mCardThumbnail.getCustomSource(), mImageView);
            else if(mCardThumbnail.getDrawableResource()>0)
                loadBitmap(mCardThumbnail.getDrawableResource(), mImageView);
            else
                loadBitmap(mCardThumbnail.getUrlResource(), mImageView);
        }
    }

    //--------------------------------------------------------------------------
    // Load Bitmap and cache manage
    //--------------------------------------------------------------------------

    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if (bitmap != null) {
            if (!mCardThumbnail.applyBitmap(imageView,bitmap))
                imageView.setImageBitmap(bitmap);
            sendBroadcast();
        } else {
            if (cancelPotentialWork(resId, imageView)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(getResources(), null, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(resId);
            }
        }
    }

    public void loadBitmap(String url, ImageView imageView) {
        final String imageKey = url;
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if (bitmap != null){
            if (!mCardThumbnail.applyBitmap(imageView,bitmap))
                imageView.setImageBitmap(bitmap);
            sendBroadcast();
        }else{
            if (cancelPotentialWork(url, imageView)) {
                final BitmapWorkerUrlTask task = new BitmapWorkerUrlTask(imageView);
                final AsyncDrawableUrl asyncDrawable =
                        new AsyncDrawableUrl(getResources(), null, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(url);
            }
        }
    }

    public void loadBitmap(CardThumbnail.CustomSource customSource, ImageView imageView) {
        final String imageKey = customSource.getTag();
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if (bitmap != null){
            if (!mCardThumbnail.applyBitmap(imageView,bitmap))
                imageView.setImageBitmap(bitmap);
            sendBroadcast();
        }else{
            if (cancelPotentialWork(customSource, imageView)) {
                final BitmapWorkerCustomSourceTask task = new BitmapWorkerCustomSourceTask(imageView);
                final AsyncDrawableCustomSource asyncDrawable =
                        new AsyncDrawableCustomSource(getResources(), null, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(customSource);
            }
        }
    }

    protected void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (!mLoadingErrorResource && getBitmapFromMemCache(key) == null) {
            if (key!=null && bitmap!=null){
                mMemoryCache.put(key, bitmap);
            }
        }
    }

    protected Bitmap getBitmapFromMemCache(String key) {
        if (key==null) return null;
        return mMemoryCache.get(key);
    }



    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, String resUrl,
                                                         int reqWidth, int reqHeight) {

        try {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            //BitmapFactory.decodeResource(res, resId, options);
            BitmapFactory.decodeStream(new URL(resUrl).openStream());

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(new URL(resUrl).openStream());

        }catch (IOException ioe){
            //Url not available
            //ioe.printStackTrace();
            Log.w("CardThumbnailView","Error while retrieving image",ioe);
        }
        return null;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (reqWidth == 0 || reqHeight == 0) return inSampleSize;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    //--------------------------------------------------------------------------
    // Worker
    //--------------------------------------------------------------------------

    public static boolean cancelPotentialWork(int resId, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapWorkerTaskResId = bitmapWorkerTask.resId;
            if (bitmapWorkerTaskResId != resId) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    public static boolean cancelPotentialWork(String url, ImageView imageView) {
        final BitmapWorkerUrlTask bitmapWorkerTask = getBitmapWorkerUrlTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapWorkerTaskResUrl = bitmapWorkerTask.resUrl;
            if (!bitmapWorkerTaskResUrl.equals(url)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    public static boolean cancelPotentialWork(CardThumbnail.CustomSource customSource, ImageView imageView) {
        final BitmapWorkerCustomSourceTask bitmapWorkerTask = getBitmapWorkerCustomSourceTask(imageView);

        if (bitmapWorkerTask != null && bitmapWorkerTask.customSource != null) {
            final CardThumbnail.CustomSource bitmapWorkerTaskCustomSource = bitmapWorkerTask.customSource;
            if (bitmapWorkerTaskCustomSource.getTag() != null) {
                if (!bitmapWorkerTaskCustomSource.getTag().equals(customSource.getTag())) {
                    // Cancel previous task
                    bitmapWorkerTask.cancel(true);
                } else {
                    // The same work is already in progress
                    return false;
                }
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    protected static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    protected static BitmapWorkerUrlTask getBitmapWorkerUrlTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawableUrl) {
                final AsyncDrawableUrl asyncDrawable = (AsyncDrawableUrl) drawable;
                return asyncDrawable.getBitmapWorkerUrlTask();
            }
        }
        return null;
    }

    protected static BitmapWorkerCustomSourceTask getBitmapWorkerCustomSourceTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawableCustomSource) {
                final AsyncDrawableCustomSource asyncDrawable = (AsyncDrawableCustomSource) drawable;
                return asyncDrawable.getBitmapWorkerCustomSourceTask();
            }
        }
        return null;
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int resId = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            resId = params[0];
            ImageView thumbnail = imageViewReference.get();
            Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), resId, thumbnail.getWidth(),
                    thumbnail.getHeight());
            if (bitmap!=null){
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
                return bitmap;
            }else{
                return (Bitmap)null;
            }

        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    if (!mCardThumbnail.applyBitmap(imageView,bitmap))
                        imageView.setImageBitmap(bitmap);
                    sendBroadcast();
                    mLoadingErrorResource=false;
                }
            }else{
                sendBroadcast(false);
                if (mCardThumbnail!=null && mCardThumbnail.getErrorResourceId()!=0){
                    if (!mLoadingErrorResource){
                        //To avoid a loop
                        loadBitmap(mCardThumbnail.getErrorResourceId(), mImageView);
                    }
                    mLoadingErrorResource=true;
                }
            }
        }
    }

    class BitmapWorkerUrlTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String resUrl = "";

        public BitmapWorkerUrlTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            resUrl = params[0];
            ImageView thumbnail = imageViewReference.get();
            Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), resUrl, thumbnail.getWidth(),
                    thumbnail.getHeight());
            if (bitmap!=null){
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
                return bitmap;
            }else
                return (Bitmap) null;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerUrlTask bitmapWorkerTask =
                        getBitmapWorkerUrlTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    if (!mCardThumbnail.applyBitmap(imageView,bitmap))
                        imageView.setImageBitmap(bitmap);
                    sendBroadcast();
                    mLoadingErrorResource=false;
                }
            }else{
                sendBroadcast(false);
                if (mCardThumbnail!=null && mCardThumbnail.getErrorResourceId()!=0){
                    if (!mLoadingErrorResource){
                        //To avoid a loop
                        loadBitmap(mCardThumbnail.getErrorResourceId(), mImageView);
                    }
                    mLoadingErrorResource=true;
                }
            }
        }
    }

    class BitmapWorkerCustomSourceTask extends AsyncTask<CardThumbnail.CustomSource, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private CardThumbnail.CustomSource customSource = null;

        public BitmapWorkerCustomSourceTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(CardThumbnail.CustomSource... params) {
            customSource = params[0];
            ImageView thumbnail = imageViewReference.get();
            Bitmap bitmap = customSource.getBitmap();
            if (bitmap!=null){
                addBitmapToMemoryCache(customSource.getTag(), bitmap);
                return bitmap;
            }else{
                return (Bitmap)null;
            }

        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerCustomSourceTask bitmapWorkerTask =
                        getBitmapWorkerCustomSourceTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    if (!mCardThumbnail.applyBitmap(imageView,bitmap))
                        imageView.setImageBitmap(bitmap);
                    sendBroadcast();
                    mLoadingErrorResource=false;
                }
            }else{
                sendBroadcast(false);
                if (mCardThumbnail!=null && mCardThumbnail.getErrorResourceId()!=0){
                    if (!mLoadingErrorResource){
                        //To avoid a loop
                        loadBitmap(mCardThumbnail.getErrorResourceId(), mImageView);
                    }
                    mLoadingErrorResource=true;
                }
            }
        }
    }


    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    static class AsyncDrawableUrl extends BitmapDrawable {
        private final WeakReference<BitmapWorkerUrlTask> bitmapWorkerTaskReference;

        public AsyncDrawableUrl(Resources res, Bitmap bitmap,
                             BitmapWorkerUrlTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerUrlTask>(bitmapWorkerTask);
        }

        public BitmapWorkerUrlTask getBitmapWorkerUrlTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    static class AsyncDrawableCustomSource extends BitmapDrawable {
        private final WeakReference<BitmapWorkerCustomSourceTask> bitmapWorkerTaskReference;

        public AsyncDrawableCustomSource(Resources res, Bitmap bitmap,
                                BitmapWorkerCustomSourceTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerCustomSourceTask>(bitmapWorkerTask);
        }

        public BitmapWorkerCustomSourceTask getBitmapWorkerCustomSourceTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    //--------------------------------------------------------------------------
    // Broadcast
    //--------------------------------------------------------------------------

    /**
     * Send a successful broadcast when image is downloaded
     */
    protected void sendBroadcast(){
        sendBroadcast(true);
    }

    /**
     * Send a broadcast when image is downloaded
     *
     * @param result
     */
    protected void sendBroadcast(boolean result) {

        if (mCardThumbnail.isSendBroadcastAfterAttach()) {
            Intent intent = new Intent();
            intent.setAction(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED);
            intent.putExtra(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT, result);
            if (mLoadingErrorResource)
                intent.putExtra(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING, true);
            else
                intent.putExtra(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING, false);

            if (mCardThumbnail != null && mCardThumbnail.getParentCard() != null)
                intent.putExtra(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID, mCardThumbnail.getParentCard().getId());
            if (getContext() != null)
                getContext().sendBroadcast(intent);
        }

    }

    //--------------------------------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------------------------------

    @Override
    public View getInternalOuterView() {
        return null;
    }

    /**
     * Indicates if view can recycle ui elements.
     *
     * @return <code>true</code> if views can recycle ui elements
     */
    public boolean isRecycle() {
        return mIsRecycle;
    }

    /**
     * Sets if view can recycle ui elements
     *
     * @param isRecycle  <code>true</code> to recycle
     */
    public void setRecycle(boolean isRecycle) {
        this.mIsRecycle = isRecycle;
    }

    /**
     * Indicates if inner layout have to be replaced
     *
     * @return <code>true</code> if inner layout can be recycled
     */
    public boolean isForceReplaceInnerLayout() {
        return mForceReplaceInnerLayout;
    }

    /**
     * Sets if inner layout have to be replaced
     *
     * @param forceReplaceInnerLayout  <code>true</code> to recycle
     */
    public void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout) {
        this.mForceReplaceInnerLayout = forceReplaceInnerLayout;
    }


}
