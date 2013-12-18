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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.stock.ListStockAdapter;
import it.gmariotti.cardslib.demo.stock.Stock;
import it.gmariotti.cardslib.demo.stock.StockListLayout;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.overflowanimation.ChangeCardAnimation;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class OverflowAnimFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_overflowanim, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }

    private void initCards() {
        init_simple_card();
    }

    /**
     * This method builds a simple card
     */
    private void init_simple_card() {


        //Create a Card
        GoogleNowStockCardMod card= new GoogleNowStockCardMod(getActivity());

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_overflowanim1_id);
        cardView.setCard(card);
    }


    public class SimpleAnimation extends ChangeCardAnimation{

        public SimpleAnimation(Context context, Card card) {
            super(context, card);
        }

        @Override
        protected int getLayoutIn(Card card) {
            return R.layout.carddemo_overflowanim_inner_content2;
        }

        @Override
        protected int getLayoutOut(Card card) {
            return R.id.firstContent;
        }

        @Override
        protected int getLayoutInAfter(Card card) {
            return R.layout.carddemo_overflowanim_inner_content;
        }

        @Override
        protected int getLayoutOutAfter(Card card) {
            return R.id.afterContent;
        }

    }


    class GoogleNowStockCardMod extends Card {

        public GoogleNowStockCardMod(Context context) {
            this(context, R.layout.carddemo_overflowanim_inner_content);
        }

        public GoogleNowStockCardMod(Context context, int innerLayout) {
            super(context, innerLayout);
            init();

        }

        private void init() {
            //Add Header
            CardHeader header = new CardHeader(getContext());
            header.setCustomOverflowAnimation(new SimpleAnimation(getActivity(),this));
            addCardHeader(header);

        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView textView = (TextView) view.findViewById(R.id.carddemo_googlenow_main_inner_lastupdate);
            if (textView!=null){
                textView.setText("Update 14:57, 16 September"); //should use R.string.
            }

            StockListLayout list = (StockListLayout) view.findViewById(R.id.carddemo_googlenow_main_inner_list);
            if (list!=null){
                ListStockAdapter mAdapter = new ListStockAdapter(super.getContext(), buildArrayHelper());
                list.setAdapter(mAdapter);
            }
        }


        //------------------------------------------------------------------------------------------


        public ArrayList<Stock> buildArrayHelper() {

            Stock s1 = new Stock("GOOG", 889.07f, 0.00f, 0.00f);
            Stock s2 = new Stock("AAPL", 404.27f, 0.00f, 0.00f);
            Stock s3 = new Stock("ENI", 17.59f, 0.06f, 0.34f);
            Stock s4 = new Stock("Don Jones", 15.376f, 0.00f, 0.00f);

            ArrayList<Stock> list = new ArrayList<Stock>();
            list.add(s1);
            list.add(s2);
            list.add(s3);
            list.add(s4);
            return list;
        }


    }

}
