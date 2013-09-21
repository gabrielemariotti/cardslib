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

package it.gmariotti.cardslib.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.CustomCard;
import it.gmariotti.cardslib.internal.Card;
import it.gmariotti.cardslib.internal.CardHeader;
import it.gmariotti.cardslib.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardFragment extends BaseFragment {

    protected ScrollView mScrollView;
    protected TextView mTextViewSwipe;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);
        mTextViewSwipe = (TextView) getActivity().findViewById(R.id.carddemo_card3_text);


        initCards();
    }


    private void initCards() {
        init_simple_card();
        init_card_inner_layout();
        init_custom_card();
        init_custom_card_swipe();
        init_custom_card_clickable();
        init_custom_card_partial_listener();
    }

    /**
     * This method builds a simple card
     */
    private void init_simple_card() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Set the card inner text
        card.setTitle(getString(R.string.demo_card_basetitle));

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_card_id);
        cardView.setCard(card);
    }

    /**
     * This method builds a simple card with a custom inner layout
     */
    private void init_card_inner_layout() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext(),R.layout.carddemo_example_inner_content);

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Set the card inner text
        card.setTitle(getString(R.string.demo_card_basetitle));

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_card_inner);
        cardView.setCard(card);
    }

    /**
     * This method builds a custom card
     */
    private void init_custom_card() {

        //Create a Card
        Card card = new CustomCard(getActivity().getApplicationContext());

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3);
        cardView.setCard(card);
    }

    /**
     * This method builds a custom card with a swipe action
     */
    private void init_custom_card_swipe() {

        //Create a Card
        CustomCard card = new CustomCard(getActivity().getApplicationContext());

        //Enable a swipe action
        card.setSwipeable(true);

        //You can set a SwipeListener.
        card.setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                if (mTextViewSwipe!=null)
                    mTextViewSwipe.setVisibility(View.GONE);
            }
        });

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3_swipe);
        cardView.setCard(card);
    }

    /**
     * This method builds a custom card clickable
     */
    private void init_custom_card_clickable() {

        //Create a Card
        CustomCard card = new CustomCard(getActivity().getApplicationContext());

        //Set onClick listener
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Clickable card", Toast.LENGTH_LONG).show();
            }
        });

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card3_clickable);
        cardView.setCard(card);
    }

    /**
     * This method builds a custom card with a swipe action
     */
    private void init_custom_card_partial_listener() {

        //Create a Card
        Card card1 = new Card(getActivity().getApplicationContext(),R.layout.carddemo_example_inner_content);

        CardHeader header1 = new CardHeader(getActivity().getApplicationContext());
        header1.setTitle(getString(R.string.demo_custom_shorttitle));

        card1.addCardHeader(header1);


        //Set the card inner text
        card1.setTitle(getString(R.string.demo_card_shorttitle));

        //Set a clickListener on ContentArea
        card1.addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Click on Content Area", Toast.LENGTH_LONG).show();
            }
        });

        //Set card in the cardView
        CardView cardView1 = (CardView) getActivity().findViewById(R.id.carddemo_example_card3_listeners1);
        cardView1.setCard(card1);

        //----------------------------------------------------------------------------------
        //Create a Card
        Card card2 = new Card(getActivity().getApplicationContext(),R.layout.carddemo_example_inner_content);

        CardHeader header2 = new CardHeader(getActivity().getApplicationContext());
        header2.setTitle(getString(R.string.demo_custom_shorttitle));

        card2.addCardHeader(header2);


        //Set the card inner text
        card2.setTitle(getString(R.string.demo_card_shorttitle));

        //Set a clickListener on Header Area
        card2.addPartialOnClickListener(Card.CLICK_LISTENER_HEADER_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Click on Header Area", Toast.LENGTH_LONG).show();
            }
        });


        //Set card in the cardView
        CardView cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_example_card3_listeners2);
        cardView2.setCard(card2);


    }

}
