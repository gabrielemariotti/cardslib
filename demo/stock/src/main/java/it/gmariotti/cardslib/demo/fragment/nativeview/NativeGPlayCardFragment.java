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

package it.gmariotti.cardslib.demo.fragment.nativeview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.Utils;
import it.gmariotti.cardslib.demo.cards.GooglePlaySmallCard;
import it.gmariotti.cardslib.demo.cards.GplayCard;
import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeGPlayCardFragment extends BaseMaterialFragment {

    protected ScrollView mScrollView;
    private GooglePlaySmallCard cardGmap;
    private CardViewNative cardViewGmap;

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_ex_gplay;
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
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/nativeview/NativeGPlayCardFragment.java";
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_gplay;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_native_gplay_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCardSmallCard();
        initCardGooglePlay();
    }


    /**
     * This method builds a simple card
     */
    private void initCardSmallCard() {

        //Create a Card
        cardGmap= new GooglePlaySmallCard(getActivity());
        cardGmap.setId("gplaysmall");

        //Set card in the cardView
        cardViewGmap = (CardViewNative) getActivity().findViewById(R.id.carddemo_gmaps);
        cardViewGmap.setCard(cardGmap);
    }


    /**
     * This method builds a simple card
     */
    private void initCardGooglePlay() {

        //Create a Card
        GplayCard card= new GplayCard(getActivity());

        //Set card in the cardView
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_Gplay1);
        cardView.setCard(card);

        //Create a Card
        GplayCard card2= new GplayCard(getActivity());

        //Set card in the cardView
        CardViewNative cardView2 = (CardViewNative) getActivity().findViewById(R.id.carddemo_Gplay2);
        cardView2.setCard(card2);

        if (Utils.isTablet(getActivity())){
            //Create a Card
            GplayCard card3= new GplayCard(getActivity());

            //Set card in the cardView
            CardViewNative cardView3 = (CardViewNative) getActivity().findViewById(R.id.carddemo_Gplay3);
            if (cardView3!=null)
                cardView3.setCard(card3);
        }
    }

}
