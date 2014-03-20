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

package it.gmariotti.cardslib.demo.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.MayKnowCard;
import it.gmariotti.cardslib.demo.cards.SuggestedCard;
import it.gmariotti.cardslib.demo.drawable.CircleDrawable;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MiscCardFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_misc_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_misc_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

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
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_MayKnow);
        cardView.setCard(card);

        MayKnowCard card2 = new MayKnowCard(getActivity());
        card2.addCardHeader(null);
        card2.setShadow(true);
        CardView mayView2 = (CardView) getActivity().findViewById(R.id.carddemo_MayKnow2);
        mayView2.setCard(card2);

    }

    /**
     * This method builds a suggested card example
     */
    private void initCardSuggested() {

        SuggestedCard card = new SuggestedCard(getActivity());
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_suggested);
        cardView.setCard(card);
    }


    private void initCircleCard(){

        Card card = new Card(getActivity());
        card.setTitle("Title");
        card.setBackgroundResourceId(R.color.demo_card_background_color1);
        CardThumbnailCircle thumb = new CardThumbnailCircle(getActivity());
        card.addCardThumbnail(thumb);

        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_circleleft);
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
        public boolean applyBitmap(View imageView, Bitmap bitmap) {

            CircleDrawable circle = new CircleDrawable(bitmap);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                imageView.setBackground(circle);
            else
                imageView.setBackgroundDrawable(circle);
            return true;

        }
    }

}
