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

package it.gmariotti.cardslib.library.internal.overflowanimation;

import android.content.Context;
import android.view.View;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class SimpleOverflowAnimation implements CardHeader.CustomOverflowAnimation{

    protected Context mContext;
    protected boolean selected=false;
    protected Card mCard;

    public  SimpleOverflowAnimation(Context context){
        mContext=context;
    }

    @Override
    public void doAnimation(Card card, View imageOverflow) {

        if (card==null) return;
        mCard=card;

        CardHeader header= card.getCardHeader();
        if (header!=null){
            selected=header.isOverflowSelected();
        }
    }

    /**
     * Select overflow icon
     */
    protected void selectOverflowIcon(){
      changeOverflowIconSelection(true);
    }

    /**
     * UnSelect overflow icon
     */
    protected void unSelectOverflowIcon(){
        changeOverflowIconSelection(false);
    }

    protected void toggleOverflowIcon(){

        if (mCard==null) return;

        CardHeader header = mCard.getCardHeader();
        if (header!=null){
            changeOverflowIconSelection(!selected);
        }
    }

    private void changeOverflowIconSelection(boolean selected){
        if (mCard==null) return;

        CardHeader header = mCard.getCardHeader();
        if (header!=null){
            this.selected=selected;
            header.setOverflowSelected(selected);
        }

        CardView cardView = mCard.getCardView();
        if (cardView!=null){
            if (cardView.getInternalHeaderLayout()!=null && cardView.getInternalHeaderLayout().getImageButtonOverflow()!=null)
                cardView.getInternalHeaderLayout().getImageButtonOverflow().setSelected(selected);
        }
    }


}
