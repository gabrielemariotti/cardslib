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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * List base example
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListBaseFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_list_base;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_list_base, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0;i<200;i++){
            CardExample card = new CardExample(getActivity(),"My title "+i,"Inner text "+i);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_base1);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    public class CardExample extends Card{

        protected String mTitleHeader;
        protected String mTitleMain;

        public CardExample(Context context,String titleHeader,String titleMain) {
            super(context, R.layout.carddemo_example_inner_content);
            this.mTitleHeader=titleHeader;
            this.mTitleMain=titleMain;
            init();
        }

        private void init(){

            //Create a CardHeader
            CardHeader header = new CardHeader(getActivity());

            //Set the header title
            header.setTitle(mTitleHeader);

            //Add a popup menu. This method set OverFlow button to visible
            header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    Toast.makeText(getActivity(), "Click on card menu" + mTitleHeader +" item=" +  item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            addCardHeader(header);

            //Add ClickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=" + mTitleHeader, Toast.LENGTH_SHORT).show();
                }
            });

            //Set the card inner text
            setTitle(mTitleMain);
        }

    }

}
