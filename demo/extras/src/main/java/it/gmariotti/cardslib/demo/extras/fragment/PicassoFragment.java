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

package it.gmariotti.cardslib.demo.extras.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.PicassoCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * This example uses a list of cards with Thumbnail loaded with built-in method and Picasso library
 * Please refer to https://github.com/square/picasso for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class PicassoFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_picasso;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_picasso, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
    }

    /**
     * This method builds a simple list of cards
     */
    private void initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            PicassoCard card = new PicassoCard(this.getActivity());
            card.setTitle("A simple card loaded with Picasso " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);
            cards.add(card);
        }

        //Set the adapter
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_picasso);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }

    }

}
