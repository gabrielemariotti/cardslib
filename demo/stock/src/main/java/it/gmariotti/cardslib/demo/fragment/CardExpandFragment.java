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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.CustomCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Expand Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardExpandFragment extends BaseFragment {


    ScrollView mScrollView;


    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_card_expand;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.demo_fragment_card_expand, container, false);

        mScrollView = (ScrollView) root.findViewById(R.id.card_scrollview);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initCards();
    }


    private void initCards() {
        init_standard_header_with_expandcollapse_button();
        init_custom_card_expand();
        init_custom_card_expand_clicking_text();
        init_custom_card_expand_clicking_image();
        init_custom_card_expand_inside();
    }

    /**
     * This method builds a standard header with base expand/collapse
     */
    private void init_standard_header_with_expandcollapse_button() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Set visible the expand/collapse button
        header.setButtonExpandVisible(true);

        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity());
        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_customtitle3));
        card.addCardExpand(expand);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand1);
        cardView.setCard(card);
    }

    /**
     * This method builds a custom card with expand/collapse action clickable in all card view
     */
    private void init_custom_card_expand() {

        //Create a Card
        CustomCard card = new CustomCard(getActivity());

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity());
        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_customtitle3));
        card.addCardExpand(expand);


        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand2);
        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .setupView(cardView);
        card.setViewToClickToExpand(viewToClickToExpand);
        cardView.setCard(card);
    }


    /**
     * This method builds a custom card with expand/collapse action clickable in all card view
     */
    private void init_custom_card_expand_clicking_text() {

        //Create a Card
        CustomCard2 card = new CustomCard2(getActivity());
        card.setTitle("Click here to expand/collapse");

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity());
        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_customtitle3));
        card.addCardExpand(expand);


        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand3);
        cardView.setCard(card);
    }

    /**
     * This method builds a custom card with expand/collapse action clickable in all card view
     */
    private void init_custom_card_expand_clicking_image() {

        //Create a Card
        Card card = new Card(getActivity());

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity());
        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_customtitle3));
        card.addCardExpand(expand);

        CustomThumbnail thumb = new CustomThumbnail(getActivity());
        thumb.setDrawableResource(R.drawable.ic_smile);
        card.addCardThumbnail(thumb);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand4);
        cardView.setCard(card);
    }

    /**
     * This method builds a card with a collpse/expand section inside
     */
    private void init_custom_card_expand_inside() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_expand_area_inside));
        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CardExpandInside expand = new CardExpandInside(getActivity());
        card.addCardExpand(expand);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand5);
        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupView(cardView);
        card.setViewToClickToExpand(viewToClickToExpand);

        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                /*
                if (mScrollView!=null){
                    mScrollView.post(new Runnable() {
                        public void run() {
                            mScrollView.scrollTo(0, mScrollView.getBottom());
                        }
                    });
                }*/
            }
        });

        cardView.setCard(card);
    }

    class CustomCard2 extends Card{

        public CustomCard2(Context context) {
            super(context,R.layout.carddemo_example_cardexpand_inner_content);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (view != null) {
                TextView mTitleView = (TextView) view.findViewById(it.gmariotti.cardslib.library.R.id.card_main_inner_simple_title);
                if (mTitleView != null){
                    mTitleView.setText(mTitle);

                    ViewToClickToExpand viewToClickToExpand =
                            ViewToClickToExpand.builder()
                                    .setupView(mTitleView);
                    setViewToClickToExpand(viewToClickToExpand);
                }
            }
        }
    }


    class CustomThumbnail extends CardThumbnail{

        public CustomThumbnail(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View imageView) {

            ViewToClickToExpand viewToClickToExpand =
                    ViewToClickToExpand.builder()
                            .highlightView(false)
                            .setupView(imageView);
            getParentCard().setViewToClickToExpand(viewToClickToExpand);
        }
    }

    class CardExpandInside extends CardExpand {

        public CardExpandInside(Context context) {
            super(context,R.layout.carddemo_example_expandinside_expand_layout);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            ImageView img = (ImageView) view.findViewById(R.id.carddemo_inside_image);

            //It is just an example. You should load your images in an async way
            if (img!=null){
                img.setImageResource(R.drawable.rose);
            }
        }
    }
}
