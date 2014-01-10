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

package it.gmariotti.cardslib.library.internal.overflowanimation;

import android.content.Context;
import android.view.View;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * BaseAnimation to overlay the Card
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class BaseCardOverlayAnimation extends BaseOverflowAnimation {

    /**
     * Original card
     */
    protected Card originalCard;

    /**
     * Animation Duration
     */
    protected int mAnimationDuration;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public BaseCardOverlayAnimation(Context context, Card card) {
        super(context);
        this.originalCard = card;
    }

    // -------------------------------------------------------------
    // CardInfoToAnimate model
    // -------------------------------------------------------------

    /**
     * Public interface for Card Animation
     */
    public interface CardInfo {
        /**
         *
         * @param layoutId
         */
        public void setupLayoutsIdToRemove(int[] layoutId);

        /**
         *
         */
        public int[] getLayoutsIdToAdd();
    }

    /**
     * CardInfo provides info about layouts to remove, and layouts to add
     */
    public abstract class CardInfoToAnimate implements CardInfo {

        /*
         * Layouts to Remove
         */
        protected int[] mLayoutsIdToRemove;

        // -----------------------------
        // Constructors
        // -----------------------------

        public CardInfoToAnimate() {
            defaultIdToRemove();
        }

        /**
         * Default layouts to remove
         */
        private void defaultIdToRemove(){
            mLayoutsIdToRemove = new int[]
                    {
                            R.id.card_header_inner_frame,
                            R.id.card_thumbnail_layout,
                            R.id.card_main_content_layout
                    };
        }

        @Override
        public void setupLayoutsIdToRemove(int[] layoutId) {
               this.mLayoutsIdToRemove=layoutId;
        }

        @Override
        public abstract int[] getLayoutsIdToAdd();

        /**
         * Indicates if card need a navigator
         *
         * @return
         */
        protected boolean isWithNavigator(){
            if (getLayoutsIdToAdd()!=null && getLayoutsIdToAdd().length>1)
                return true;
            else
                return false;
        }
    }

    protected abstract CardInfoToAnimate setCardToAnimate(Card card);

    // -------------------------------------------------------------
    // Animation
    // -------------------------------------------------------------

    @Override
    public void doAnimation(final Card card, View imageOverflow) {
        //Store selected values
        super.doAnimation(card, imageOverflow);

        if (card == null || card.getCardView() == null) return;

        //animation duration
        //final int mShortAnimationDuration = getAnimationDuration();

        final CardInfoToAnimate infoAnimation = setCardToAnimate(card);

        if (infoAnimation == null) return;

        if (!selected) {
            doOverFirstAnimation(card,infoAnimation, imageOverflow);
        } else {
            //TODO More Cards
            doOverOtherAnimation(card,infoAnimation, imageOverflow);
        }

        //Update icon and model status
        toggleOverflowIcon();
    }

    protected abstract void doOverOtherAnimation(Card card,CardInfoToAnimate infoAnimation,View imageOverflow);

    protected abstract void doOverFirstAnimation(Card card,CardInfoToAnimate infoAnimation,View imageOverflow);


    // -------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------

    /**
     * Returns the animation duration
     *
     * @return
     */
    protected int getAnimationDuration() {
        return mAnimationDuration = mContext.getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }


}
