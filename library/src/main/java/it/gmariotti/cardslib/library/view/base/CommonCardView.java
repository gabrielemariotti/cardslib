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
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public interface CommonCardView {


    Context getContext();

    void refreshCard(Card card);

    /**
     * Interface to listen any callbacks when expand/collapse animation starts
     */
    public interface OnExpandListAnimatorListener {
        public void onExpandStart(CommonCardView viewCard,View expandingLayout);
        public void onCollapseStart(CommonCardView viewCard,View expandingLayout);
    }


    void changeBackgroundResourceId(int drawableResourceId);

    void changeBackgroundResource(Drawable drawableResource);

    Card getCard();

    void setCard(Card mCard);

    void setForceReplaceInnerLayout(boolean b);

    void setRecycle(boolean recycle);

    CardThumbnailView getInternalThumbnailLayout();

    void setOnTouchListener(View.OnTouchListener onTouchListener);

    void setOnExpandListAnimatorListener(OnExpandListAnimatorListener onExpandListAnimatorListener);

    void setOnClickListener(View.OnClickListener advanceClickListener);

    void setExpanded(boolean b);
}
