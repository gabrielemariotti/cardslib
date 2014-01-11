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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Shadow Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ShadowFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_shadow;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_shadow, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCards();
    }


    private void initCards() {
        init_card_without_shadow();
        init_card_custom_shadow_layout();
    }

    /**
     * This method builds a card without shadow
     */
    private void init_card_without_shadow() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Hidden shadow
        card.setShadow(false);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_shadow_no);
        cardView.setCard(card);
    }


    /**
     * This methods builds a card with a custom shadow layout (compound view)
     * <b>WARNING</b>
     * See https://github.com/gabrielemariotti/cardslib/tree/master/SHADOW.md for more information.
     * You can quickly modify your shadow with your style and drawable files without modifying shadow layout.
     */
    private void init_card_custom_shadow_layout() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        header.setTitle(getString(R.string.demo_header_basetitle));

        card.addCardHeader(header);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_shadow_layout);
        cardView.setCard(card);
    }


}
