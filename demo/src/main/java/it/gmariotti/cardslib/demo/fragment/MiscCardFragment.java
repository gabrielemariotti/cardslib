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

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.MayKnowCard;
import it.gmariotti.cardslib.demo.cards.SuggestedCard;
import it.gmariotti.cardslib.view.CardView;

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

    }



    /**
     * This method builds a simple card
     */
    private void initCardMayKnow() {

        //Create a Card
        MayKnowCard card= new MayKnowCard(getActivity().getApplicationContext());
        card.setShadow(false);

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_MayKnow);
        cardView.setCard(card);

        MayKnowCard card2 = new MayKnowCard(getActivity().getApplicationContext());
        card2.addCardHeader(null);
        card2.setShadow(true);
        CardView mayView2 = (CardView) getActivity().findViewById(R.id.carddemo_MayKnow2);
        mayView2.setCard(card2);

    }

    /**
     * This method builds a suggested card example
     */
    private void initCardSuggested() {

        SuggestedCard card = new SuggestedCard(getActivity().getApplicationContext());
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_suggested);
        cardView.setCard(card);
    }


}
