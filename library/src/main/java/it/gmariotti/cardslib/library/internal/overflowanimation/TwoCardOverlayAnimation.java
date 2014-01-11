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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Simple implementation with only 2 card (the original and the overlay)
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class TwoCardOverlayAnimation extends BaseCardOverlayAnimation {

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public TwoCardOverlayAnimation(Context context, Card card) {
        super(context,card);
    }

    /**
     *
     */
    public abstract class TwoCardToAnimate  extends  CardInfoToAnimate{

        @Override
        public int[] getLayoutsIdToAdd() {
            int[] layouts= new int[1];
            layouts[0]=getLayoutIdToAdd();
            return layouts;
        }

        public abstract int getLayoutIdToAdd();

    }


    // -------------------------------------------------------------
    // Animation
    // -------------------------------------------------------------


    @Override
    protected void doOverFirstAnimation(final Card card, CardInfoToAnimate infoAnimation, View imageOverflow) {

       if (infoAnimation==null) return;

        final ViewGroup mInternalLayoutOverlay = (ViewGroup)card.getCardView().findViewById(R.id.card_overlap);

        //Checks
        if (mInternalLayoutOverlay==null){
            Log.e(TAG, "Overlap layout not found!");
            return;
        }
        if (infoAnimation.getLayoutsIdToAdd()==null){
            Log.e(TAG,"You have to specify layouts to add!");
            return;
        }

        //Views to remove
        View[] viewsOut= getOutViews(card,infoAnimation);

        //Get the layout to add
        final int layoutIdIn= infoAnimation.getLayoutsIdToAdd()[0];

        AnimatorSet animAlpha = new AnimatorSet();
        if (viewsOut != null && layoutIdIn > 0) {

            ArrayList<Animator> animators= new ArrayList<Animator>();

            for (final View viewOut:viewsOut){
                    if (viewOut!=null){
                    ObjectAnimator anim = ObjectAnimator.ofFloat(viewOut, "alpha", 1f, 0f);
                    anim.setDuration(getAnimationDuration());
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            viewOut.setVisibility(View.GONE);
                        }
                    });
                    animators.add(anim);
                    }
            }
            animAlpha.playTogether(animators);
        }


        animAlpha.addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewIn= inflater.inflate(layoutIdIn,mInternalLayoutOverlay,false);
                if (viewIn!=null){

                    if (card.getCardView()!=null &&
                            card.getCardView().getInternalMainCardLayout()!=null &&
                            card.getCardView().getInternalHeaderLayout()!=null &&
                            card.getCardView().getInternalHeaderLayout().getFrameButton()!=null){
                        int h1=card.getCardView().getInternalMainCardLayout().getMeasuredHeight();
                        int h2=card.getCardView().getInternalHeaderLayout().getFrameButton().getMeasuredHeight();
                        viewIn.setMinimumHeight(h1-h2);
                    }
                    mInternalLayoutOverlay.addView(viewIn);

                    viewIn.setAlpha(0);
                    viewIn.setVisibility(View.VISIBLE);

                    viewIn.animate()
                            .alpha(1f)
                            .setDuration(getAnimationDuration())
                            .setListener(null);
                }
            }
        });

        animAlpha.start();
    }

    @Override
    protected void doOverOtherAnimation(final Card card, CardInfoToAnimate infoAnimation, View imageOverflow) {

        //Checks
        if (infoAnimation == null) return;

        final ViewGroup mInternalLayoutOverlay = (ViewGroup) card.getCardView().findViewById(R.id.card_overlap);
        if (mInternalLayoutOverlay == null) {
            Log.e(TAG, "Overlap layout not found!");
            return;
        }
        if (infoAnimation.getLayoutsIdToAdd() == null) {
            Log.e(TAG, "You have to specify layouts to add!");
            return;
        }

        final View[] viewsLastOut = getOutViews(card, infoAnimation);

        //int layoutIdIn= infoAnimation.getLayoutsIdToAdd()[0];
        final View viewLastIn = mInternalLayoutOverlay.getChildAt(0);
        //final View viewIn = card.getCardView().findViewById(R.id.afterContent);

        //Views to remove
        final View[] viewsFirstOut = getOutViews(card, infoAnimation);

        if (viewLastIn != null) {
            viewLastIn.animate()
                    .alpha(0f)
                    .setDuration(getAnimationDuration())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            viewLastIn.setVisibility(View.GONE);


                            for (final View viewOut : viewsLastOut) {
                                if (viewOut != null) {
                                    viewOut.setVisibility(View.VISIBLE);
                                }
                            }

                            if (mInternalLayoutOverlay != null) {
                                mInternalLayoutOverlay.removeView(viewLastIn);
                            }

                            for (final View viewOut : viewsLastOut) {
                                if (viewOut != null) {
                                    viewOut.animate()
                                            .alpha(1f)
                                            .setDuration(getAnimationDuration());
                                }
                            }

                        }
                    });
        }
    }

    protected View[] getOutViews(Card card,CardInfoToAnimate infoAnimation){
        //Views to remove
        View[] viewsOut=null;
        if (infoAnimation.mLayoutsIdToRemove!=null){

            viewsOut = new View[infoAnimation.mLayoutsIdToRemove.length];
            int i=0;
            for (int layoutIdOut:infoAnimation.mLayoutsIdToRemove){
                View viewOut =card.getCardView().findViewById(layoutIdOut);
                viewsOut[i]=viewOut;
                i++;
            }
        }
        return viewsOut;
    }


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
