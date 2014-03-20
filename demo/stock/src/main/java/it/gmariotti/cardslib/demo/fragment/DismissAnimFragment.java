/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
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

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.drawable.CircleDrawable;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.dismissanimation.SwipeDismissAnimation;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class DismissAnimFragment extends BaseFragment {

    protected CardArrayAdapter mCardArrayAdapter;
    SwipeDismissAnimation dismissAnimation;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_dismiss_animation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_list_dismiss_animator, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }

    private void initCards() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            Card card = initDismissCard(i);
            cards.add(card);
        }

        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        dismissAnimation = (SwipeDismissAnimation) new SwipeDismissAnimation(getActivity()).
                setup(mCardArrayAdapter);


        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_dismissanim);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    private Card initDismissCard(int i) {

        CardDismiss card = new CardDismiss(getActivity());
        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity(), R.layout.carddemo_dismissanim_inner_header);

        //Set visible the expand/collapse button
        header.setOtherButtonVisible(true);
        header.setOtherButtonDrawable(R.drawable.card_menu_button_other_dismiss);
        header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
            @Override
            public void onButtonItemClick(Card card, View view) {
                dismissAnimation.animateDismiss(card);
            }
        });
        //Add Header to card
        card.addCardHeader(header);
        CardDismissThumb thumb = new CardDismissThumb(getActivity());
        float density = getActivity().getResources().getDisplayMetrics().density;
        int size = (int) (75 * density);
        switch (i % 3) {
            case 0:
                thumb.setUrlResource("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s"+size+"/new%2520profile%2520%25282%2529.jpg");
                break;
            case 1:
                thumb.setDrawableResource(R.drawable.ic_ic_error_loading);
                break;
            case 2:
                thumb.setDrawableResource(R.drawable.ic_ic_s);
                break;

        }
        thumb.setErrorResource(R.drawable.ic_ic_error_loading);
        card.addCardThumbnail(thumb);


        return card;
    }

    class CardDismiss extends Card {


        public CardDismiss(Context context) {
            super(context, R.layout.carddemo_dismissanim_inner_main);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

        }
    }


    class CardDismissThumb extends CardThumbnail {

        public CardDismissThumb(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            /*
            viewImage.getLayoutParams().width = 250;
            viewImage.getLayoutParams().height = 250;
            */
        }

        @Override
        public boolean applyBitmap(View imageView, Bitmap bitmap) {

            CircleDrawable circle = new CircleDrawable(bitmap, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                imageView.setBackground(circle);
            else
                imageView.setBackgroundDrawable(circle);
            return true;

        }
    }


}
