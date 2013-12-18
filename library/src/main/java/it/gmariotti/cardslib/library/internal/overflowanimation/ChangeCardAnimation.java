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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class ChangeCardAnimation extends  SimpleOverflowAnimation{

    protected Card originalCard;

    public ChangeCardAnimation(Context context,Card card) {
        super(context);
        this.originalCard = card;
    }

    /**
     *
     * @param card
     * @return
     */
    protected abstract int getLayoutIn(Card card);

    /**
     *
     * @param card
     * @return
     */
    protected abstract int getLayoutOut(Card card);


    /**
     *
     * @param card
     * @return
     */
    protected abstract int getLayoutInAfter(Card card);

    /**
     *
     * @param card
     * @return
     */
    protected abstract int getLayoutOutAfter(Card card);


    @Override
    public void doAnimation(final Card card, View imageOverflow) {
        super.doAnimation(card, imageOverflow);

        final int mShortAnimationDuration= mContext.getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        if (!selected){

            final int layoutIn=getLayoutIn(card);
            final int layoutOut= getLayoutOut(card);
            final View viewOut = card.getCardView().findViewById(layoutOut);


            if (viewOut!=null && layoutIn>0){


                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't
                // participate in layout passes, etc.)
                viewOut.animate()
                        .alpha(0f)
                        .setDuration(mShortAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                viewOut.setVisibility(View.GONE);

                                View viewIn = card.getCardView().setupNewInnerLayout(layoutIn);

                                viewIn.setAlpha(0);
                                viewIn.setVisibility(View.VISIBLE);
                                viewIn.animate()
                                        .alpha(1f)
                                        .setDuration(mShortAnimationDuration)
                                        .setListener(null);
                            }
                        });
            }

        }else{

            final int layoutIn=getLayoutInAfter(card);
            final int layoutOut= getLayoutOutAfter(card);
            final View viewOut = card.getCardView().findViewById(layoutOut);


            if (viewOut!=null && layoutIn>0){


                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't
                // participate in layout passes, etc.)
                viewOut.animate()
                        .alpha(0f)
                        .setDuration(mShortAnimationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                viewOut.setVisibility(View.GONE);

                                View viewIn = card.getCardView().setupNewInnerLayout(layoutIn);

                                viewIn.setAlpha(0);
                                viewIn.setVisibility(View.VISIBLE);
                                viewIn.animate()
                                        .alpha(1f)
                                        .setDuration(mShortAnimationDuration)
                                        .setListener(null);
                            }
                        });
            }

        }

        toggleOverflowIcon();
    }


}
