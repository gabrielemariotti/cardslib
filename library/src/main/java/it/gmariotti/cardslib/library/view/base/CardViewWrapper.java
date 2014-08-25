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

package it.gmariotti.cardslib.library.view.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;

/**
 * Common interface for CardView.
 * <p>
 * Necessary to merge the native cardview and the library cardview.
 * <p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface CardViewWrapper {

    /**
     * Return the context
     *
     * @return context
     */
    Context getContext();

    /**
     * Interface to listen any callbacks when expand/collapse animation starts
     */
    public interface OnExpandListAnimatorListener {
        public void onExpandStart(CardViewWrapper viewCard,View expandingLayout);
        public void onCollapseStart(CardViewWrapper viewCard,View expandingLayout);
    }

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResourceId drawable resource Id
     */
    void changeBackgroundResourceId(int drawableResourceId);

    /**
     * Changes dynamically the drawable resource to override the style of MainLayout.
     *
     * @param drawableResource drawable resource
     */
    void changeBackgroundResource(Drawable drawableResource);

    /**
     * Returns {@link Card} model
     *
     * @return  {@link Card} model
     */
    Card getCard();

    /**
     * Add a {@link Card}.
     * It is very important to set all values and all components before launch this method.
     *
     * @param card {@link Card} model
     */
    void setCard(Card card);

    /**
     * Indicates if inner layout have to be replaced
     *
     *
     */
    void setForceReplaceInnerLayout(boolean forceReplaceInnerLayout);

    /**
     * Sets if view can recycle ui elements
     *
     * @param recycle  <code>true</code> to recycle
     */
    void setRecycle(boolean recycle);

    /**
     * Implement Refreshes the card content (it doesn't inflate layouts again)
     *
     * @param card
     */
    void refreshCard(Card card);

    /** Returns the view used by Thumbnail
     *
     * @return {@link CardThumbnailView}
     */
    CardThumbnailView getInternalThumbnailLayout();


    void setOnTouchListener(View.OnTouchListener onTouchListener);

    /**
     * Sets the listener invoked when expand/collapse animation starts
     * It is used internally. Don't override it.
     *
     * @param onExpandListAnimatorListener listener
     */
    void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener);

    void setOnClickListener(View.OnClickListener advanceClickListener);

    /**
     * Sets the card as expanded or collapsed
     *
     * @param expanded  <code>true</code> if the card is expanded
     */
    void setExpanded(boolean expanded);


    boolean isNative();
}
