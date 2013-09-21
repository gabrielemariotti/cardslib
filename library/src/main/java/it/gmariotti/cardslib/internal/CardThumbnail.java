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

package it.gmariotti.cardslib.internal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.internal.base.BaseCard;

/**
 * Card Thumbnail model.
 * </p>
 * Load a image from a Resource ID or an URL.
 * </p>
 * <b>Usage:</b>
 * <pre><code>
 *
 * </code></pre>
 *
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
}
