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

package it.gmariotti.cardslib.demo.fragment.v1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.GoogleNowBirthCard;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class BirthDayCardFragment extends MaterialV1Fragment {

    protected ScrollView mScrollView;
    private CardView cardView;
    private GoogleNowBirthCard birthCard;

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_ex_gbirth;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group2;
    }

    @Override
    protected String getDocUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/CARD.md";
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/v1/BirthDayCardFragment.java";
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_birthday_card;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_birthday_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);
        initCard();
    }

    /**
     * This method builds a simple card
     */
    private void initCard() {
        init1();
        init2();
        init3();
    }


    private void init1(){
        //Create a Card
        birthCard= new GoogleNowBirthCard(getActivity());
        birthCard.setId("myId");

        //Set card in the cardView
        cardView = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth);
        cardView.setCard(birthCard);
    }

    private void init2(){
        GoogleNowBirthCard card2 = new GoogleNowBirthCard(getActivity());
        card2.setId("myId2");
        card2.USE_VIGNETTE=1;

        //Set card in the cardView
        CardView cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth2);
        cardView2.setCard(card2);

    }

    private void init3(){
        GoogleNowBirthCard card3 = new GoogleNowBirthCard(getActivity());
        card3.setId("myId3");
        card3.USE_VIGNETTE=2;

        //Set card in the cardView
        CardView cardView3 = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth3);
        cardView3.setCard(card3);
    }
}
