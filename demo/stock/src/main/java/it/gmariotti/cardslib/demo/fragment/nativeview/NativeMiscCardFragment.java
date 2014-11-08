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

package it.gmariotti.cardslib.demo.fragment.nativeview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.MayKnowCard;
import it.gmariotti.cardslib.demo.cards.SuggestedCard;
import it.gmariotti.cardslib.demo.drawable.CircleDrawable;
import it.gmariotti.cardslib.demo.fragment.v1.MaterialV1Fragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeMiscCardFragment extends MaterialV1Fragment {

    protected ScrollView mScrollView;

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_ex_misc;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group2;
    }

    @Override
    protected String getDocUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/CARD.md";
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/nativeview/NativeMiscCardFragment.java";
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_misc_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_native_misc_card, container, false);
    }

    float mElevationCircle;
    int mSizeCircle;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);
        mElevationCircle = getResources().getDimension(R.dimen.circle_elevation);
        mSizeCircle = getResources().getDimensionPixelSize(R.dimen.circle_size);

        initCard();
    }

    private void initCard() {

        initCardMayKnow();
        initCardSuggested();
        initCircleCard();

    }



    /**
     * This method builds a simple card
     */
    private void initCardMayKnow() {

        //Create a Card
        MayKnowCard card= new MayKnowCard(getActivity());
        card.setShadow(false);

        //Set card in the cardView
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_MayKnow);
        cardView.setCard(card);

        MayKnowCard card2 = new MayKnowCard(getActivity());
        card2.addCardHeader(null);
        card2.setShadow(true);
        CardViewNative mayView2 = (CardViewNative) getActivity().findViewById(R.id.carddemo_MayKnow2);
        mayView2.setCard(card2);

    }

    /**
     * This method builds a suggested card example
     */
    private void initCardSuggested() {

        SuggestedCard card = new SuggestedCard(getActivity());
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_suggested);
        cardView.setCard(card);
    }


    private void initCircleCard(){

        Card card = new Card(getActivity());
        card.setTitle("Title");
        //card.setBackgroundResourceId(R.color.demo_card_background_color1);
        CardThumbnailCircle thumb = new CardThumbnailCircle(getActivity());
        card.addCardThumbnail(thumb);

        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_circleleft);
        cardView.setCard(card);

    }

    public class CardThumbnailCircle extends CardThumbnail{

        public CardThumbnailCircle(Context context) {
            super(context);

            float density = getContext().getResources().getDisplayMetrics().density;
            int size = (int) (70*density);
            setUrlResource("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s"+size+"/new%2520profile%2520%25282%2529.jpg");
            setErrorResource(R.drawable.ic_ic_error_loading);
        }

        @Override

        public boolean applyBitmap(final View imageView, Bitmap bitmap) {

            CircleDrawable circle = new CircleDrawable(bitmap);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                imageView.setBackground(circle);
            else
                imageView.setBackgroundDrawable(circle);


            ViewCompat.setElevation(imageView, mElevationCircle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                imageView.setOutlineProvider(
                        new ViewOutlineProvider(){
                            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void getOutline(View view, Outline outline) {
                                outline.setOval(0, 0, mSizeCircle, mSizeCircle);
                            }
                        });
                imageView.setClipToOutline(true);

            }


            /*imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                imageView.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f).translationZ(10);
                            } else {
                                imageView.animate().setDuration(100).scaleX(1.1f).scaleY(1.1f);
                            }
                            return true;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                imageView.animate().setDuration(100).scaleX(1).scaleY(1).translationZ(0);
                            } else {
                                imageView.animate().setDuration(100).scaleX(1).scaleY(1);
                            }
                            return true;
                    }
                    return false;
                }
            });*/


            return true;

        }
    }

}
