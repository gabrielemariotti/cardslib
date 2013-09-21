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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.CustomExpandCard;
import it.gmariotti.cardslib.demo.cards.CustomHeaderExample1;
import it.gmariotti.cardslib.demo.cards.CustomHeaderInnerCard;
import it.gmariotti.cardslib.internal.Card;
import it.gmariotti.cardslib.internal.CardExpand;
import it.gmariotti.cardslib.internal.CardHeader;
import it.gmariotti.cardslib.internal.base.BaseCard;
import it.gmariotti.cardslib.view.CardView;

/**
 * Card Header Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class HeaderFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_header;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_header, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCards();
    }


    private void initCards() {
        init_standard_header_without_buttons();
        init_standard_header_with_overflow_button();
        init_standard_header_with_expandcollapse_button();
        init_standard_header_with_expandcollapse_button_custom_area();
        init_standard_header_with_custom_other_button();
        init_standard_header_with_custom_other_button_programmatically();
        init_header_with_custom_inner_layout();
        init_header_with_custom_layout();
    }

    /**
     * This method builds a standard header without buttons
     */
    private void init_standard_header_without_buttons() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_std);
        cardView.setCard(card);
    }


    /**
     * This method builds a standard header with overflow button
     */
    private void init_standard_header_with_overflow_button() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getActivity().getApplicationContext(), "Click on " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_overflow);
        cardView.setCard(card);
    }


    /**
     * This method builds a standard header with base expand/collpase
     */
    private void init_standard_header_with_expandcollapse_button() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Set visible the expand/collapse button
        header.setButtonExpandVisible(true);

        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity().getApplicationContext());
        //Set inner title in Expand Area
        expand.setTitle(getString(R.string.demo_expand_basetitle));
        card.addCardExpand(expand);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_expand);
        cardView.setCard(card);
    }

    /**
     * This method builds a standard header with a custom expand/collpase
     */
    private void init_standard_header_with_expandcollapse_button_custom_area() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Set visible the expand/collapse button
        header.setButtonExpandVisible(true);

        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CustomExpandCard expand = new CustomExpandCard(getActivity().getApplicationContext());
        //Add Expand Area to Card
        card.addCardExpand(expand);

        //Set card in the cardView
        final CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_expand_custom_area);

        // It is not required.
        card.setOnExpandAnimatorEndListener(new Card.OnExpandAnimatorEndListener() {
            @Override
            public void onExpandEnd(Card card) {
                //TODO: check if hidden area is visible and it would be better an animator to do this
            }
        });

        cardView.setCard(card);
    }

    /**
     * This method builds a standard header with other button visible
     */
    private void init_standard_header_with_custom_other_button() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Set visible the expand/collapse button
        header.setOtherButtonVisible(true);

        //Add a callback
        header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
            @Override
            public void onButtonItemClick(Card card, View view) {
                Toast.makeText(getActivity(), "Click on Other Button", Toast.LENGTH_LONG).show();
            }
        });

        //Add Header to card
        card.addCardHeader(header);

        //Set card in the cardView
        final CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_other_button);

        cardView.setCard(card);

    }

    /**
     * This method builds a standard header with other button visible which drawable is defined programmatically
     */
    private void init_standard_header_with_custom_other_button_programmatically() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity().getApplicationContext());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        //Set visible the expand/collapse button
        header.setOtherButtonVisible(true);

        //Add a callback
        header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
            @Override
            public void onButtonItemClick(Card card, View view) {
                Toast.makeText(getActivity(), "Click on Other Button", Toast.LENGTH_LONG).show();
            }
        });

        //Use this code to set your drawable
        header.setOtherButtonDrawable(R.drawable.card_menu_button_other_add);

        //Add Header to card
        card.addCardHeader(header);

        //Set card in the cardView
        final CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_other_button_programmatically);

        cardView.setCard(card);

    }


    /**
     * This method builds a header with custom inner layout
     */
    private void init_header_with_custom_inner_layout() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(getActivity().getApplicationContext());

        //Add Header to card
        card.addCardHeader(header);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_inner);

        cardView.setCard(card);
    }

    /**
     * This method builds a header with full custom layout
     */
    private void init_header_with_custom_layout() {

        //Create a Card
        Card card = new Card(getActivity().getApplicationContext());

        //Create a CardHeader
        CustomHeaderExample1 header = new CustomHeaderExample1(getActivity().getApplicationContext());

        //Add Header to card
        card.addCardHeader(header);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_header_layout);

        cardView.setCard(card);
    }
}
