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

package it.gmariotti.cardslib.library.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;

/**
 * Card Thumbnail model.
 * </p>
 * You can customize this component. See https://github.com/gabrielemariotti/cardslib/tree/master/doc/THUMBNAIL.md for more information.
 * </p>
 * Load a image from a Resource ID or an URL.
 * </p>
 * <b>Usage:</b>
 * <pre><code>
 *        //Create thumbnail
 *        CardThumbnail thumb = new CardThumbnail(getActivity().getApplicationContext());
 *
 *        //Set resource
 *        thumb.setDrawableResource(R.drawable.carddemo_ic_gmaps_large);
 *
 *        //Add thumbnail to a card
 *        card.addCardThumbnail(thumb);
 * </code></pre>
 * or
 * <pre><code>
 *        //Set resource
 *        thumb.setUrlResource("https://mydomain.com/photo.jpg");
 * </code></pre>
 *
 * You can easily extend and customize this class providing your inner layout and
 * setting your values with {@link #setupInnerViewElements(android.view.ViewGroup, android.view.View)} method.
 * <pre><code>
 *
 *     public class CustomThumbCard extends CardThumbnail {
 *
 *         public CustomThumbCard(Context context) {
 *           super(context);
 *         }
 *
 *         @Override
 *         public void setupInnerViewElements(ViewGroup parent, View viewImage) {
 *            if (viewImage!=null){
 *                viewImage.getLayoutParams().width=250;
 *                viewImage.getLayoutParams().height=250;
 *            }
 *         }
 *    }
 * </code></pre>
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardThumbnail extends BaseCard {

    /**
     *  Resource Drawable ID
     */
    protected int drawableResource;

    /**
     * Resource Drawable URL
     */
    protected String urlResource;

    /**
     *  Uses an external library to load image
     */
    protected boolean mExternalUsage=false;

    /**
     * Placeholder
     */
    //protected int placeholderResourceId=0;

    /**
     * errorResourceId: image used if an error occurs while downloading file
     */
    protected int errorResourceId=0;

    /**
     * Interface for custom source Thumbnail
     */
    public interface CustomSource {
        /**
         * @return a string tag to identify the image
         */
        String getTag();

        /**
         * @return the bitmap from custom source
         */
        Bitmap getBitmap();
    }

    protected CustomSource customSource = null;

    /**
     * Flag to send a broadcast after the bitmap is attached to imageView
     */
    protected boolean sendBroadcastAfterAttach = true;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context  context
     */
    public CardThumbnail(Context context) {
        super(context);
    }

    /**
     * ThumbnailCard doesn't provide a Thumbnail Inner Layout.
     * Don't use it!
     *
     * @param context   context
     * @param innerLayout layout resource ID
     */
    public CardThumbnail(Context context,int innerLayout) {
        super(context);
    }

    /**
     * This method sets values to header elements and customizes view.
     *
     * Override this method to set your elements inside InnerView.
     *
     * @param parent  parent view (Inner Frame)
     * @param imageView   ImageView
     */
    @Override
    public void setupInnerViewElements(ViewGroup parent, View imageView) {
        return;
    }


    /**
     * You can override this method to transform the bitmap before
     * is attached to ImageView.
     *
     *
     * @param imageView  imageView
     * @param bitmap     bitmap
     * @return true if the callback has already been attached the bitmap to imageView , false otherwise.
     */
    public boolean applyBitmap(View imageView, Bitmap bitmap){
        return false;
    }

    /**
     * Returns the resource ID for Thumbnail
     * @return resource ID
     */
    public int getDrawableResource() {
        return drawableResource;
    }

    /**
     * Sets the resource ID for Thumbnail
     *
     * @param drawableResource resource ID
     */
    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }

    /**
     * Returns the URL for Thumbnail
     *
     * @return url
     */
    public String getUrlResource() {
        return urlResource;
    }

    /**
     * Sets the url for thumbnail
     *
     * @param urlResource url
     */
    public void setUrlResource(String urlResource) {
        this.urlResource = urlResource;
    }

    /**
     * Returns the custom source for Thumbnail
     *
     * @return the listener
     */
    public CustomSource getCustomSource() { return customSource; }

    /**
     * Sets the listener for a custom source Thumbnail
     */
    public void setCustomSource(CustomSource customSource) { this.customSource = customSource; }

    /**
     * Indicates if CardThumbnail will use an external library to load image
     *
     * @return
     */
    public boolean isExternalUsage() {
        return mExternalUsage;
    }

    /**
     * Sets <code>true</code> to use an external library to load image
     *
     * @param externalUsage <code>true</code> to use an external library to load image
     */
    public void setExternalUsage(boolean externalUsage) {
        this.mExternalUsage = externalUsage;
    }

    /**
     * Returns image used if an error occurs while downloading file
     *
     * @return
     */
    public int getErrorResourceId() {
        return errorResourceId;
    }

    /**
     * Sets image used if an error occurs while downloading file
     *
     * @param errorResourceId
     */
    public void setErrorResource(int errorResourceId) {
        this.errorResourceId = errorResourceId;
    }

    /**
     * Returns the flag which indicates to send a broadcast after the bitmap is attached
     *
     * @return
     */
    public boolean isSendBroadcastAfterAttach() {
        return sendBroadcastAfterAttach;
    }

    /**
     * Sets the flag to send a broadcast after the bitmap is attached
     *
     * @param sendBroadcastAfterAttach
     */
    public void setSendBroadcastAfterAttach(boolean sendBroadcastAfterAttach) {
        this.sendBroadcastAfterAttach = sendBroadcastAfterAttach;
    }

    /**
     * Return the CardThumbnailCardView
     * @return
     */
    public CardThumbnailView getCardThumbnailView() {

        if (getParentCard() != null)
            return getParentCard().getCardView().getInternalThumbnailLayout();

        return null;
    }
}
