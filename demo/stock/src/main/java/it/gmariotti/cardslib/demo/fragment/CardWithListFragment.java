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

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.GoogleNowStockCardwithList;
import it.gmariotti.cardslib.demo.cards.GoogleNowWeatherCard;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardWithListFragment extends BaseFragment {

    GoogleNowWeatherCard card;
    GoogleNowStockCardwithList card2;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_carwithlist_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_cardwithlist_card, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
    }

    /**
     * This method builds a simple card
     */
    private void initCard() {

        //Create a Card
         card= new GoogleNowWeatherCard(getActivity());
         card.init();

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_weathercard);
        cardView.setCard(card);


        //Card
        card2 = new GoogleNowStockCardwithList(getActivity());
        card2.init();

        //Set card in the cardView
        CardView cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_stockcard);
        cardView2.setCard(card2);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (card != null)
            card.unregisterDataSetObserver();
        if (card2 != null)
            card2.unregisterDataSetObserver();
    }
}
