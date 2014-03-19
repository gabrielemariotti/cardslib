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
import it.gmariotti.cardslib.demo.extras.stickylist.StickyCardArrayAdapter;
import it.gmariotti.cardslib.demo.extras.stickylist.StickyCardListView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * This example uses a StickyListHeaders.
 * Please refer to https://github.com/emilsjolander/StickyListHeaders for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class StickyListHeadersFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_stkhdr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_sticky, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            //Use the first letter for sticky
            String firstLetter = calculateFirstLetter(i);
            card.setTitle(firstLetter + " : simple title " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);
            cards.add(card);
        }

        //Set the adapter
        StickyCardArrayAdapter adapter = new StickyCardArrayAdapter(getActivity(), cards);

        StickyCardListView stickyList = (StickyCardListView) getActivity().findViewById(R.id.carddemo_extra_sticky_list);
        //stickyList.setAreHeadersSticky(false);
        if (stickyList != null) {
            stickyList.setAdapter(adapter);
        }

    }

    /*
     * Only for test
     */
    private String calculateFirstLetter(int i) {

        if (i < 8) return "A";
        else if (i < 16) return "B";
        else if (i < 24) return "C";
        else if (i < 32) return "D";
        else if (i < 40) return "E";
        else if (i < 48) return "F";
        else if (i < 56) return "G";
        else if (i < 64) return "H";
        else if (i < 72) return "I";
        else if (i < 80) return "L";
        else if (i < 88) return "M";
        else if (i < 96) return "N";
        else if (i < 104) return "O";
        else if (i < 112) return "P";
        else if (i < 120) return "Q";
        else if (i < 128) return "R";
        else if (i < 136) return "S";
        else if (i < 144) return "T";
        else if (i < 152) return "U";
        else if (i < 160) return "V";
        else if (i < 168) return "Z";
        else return "W";
    }


}
