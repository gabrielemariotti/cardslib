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

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.ColorCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * List base example
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListColorFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_list_color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_colors, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            ColorCard card = new ColorCard(this.getActivity());
            card.setTitle("A simple colored card " + i);
            card.setCount(i);
            switch (i) {
                case 0:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
                    break;
                case 1:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color4);
                    break;
                case 2:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
                    break;
                case 3:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color2);
                    break;
                case 4:
                    card.setBackgroundResourceId(R.drawable.demo_card_selector_color1);
                    break;
            }

            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_colors);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }

}
