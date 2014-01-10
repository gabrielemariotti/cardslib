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
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.MayKnowCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * List base example wuth different inner layouts
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListDifferentInnerBaseFragment extends BaseFragment {

    protected ScrollView mScrollView;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_list_base_different_inner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_list_base_different_inner, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0;i<50;i++){

            CardExample card = new CardExample(getActivity(),"My title "+i,"Inner text "+i);
            cards.add(card);

            CardExample2 cardx = new CardExample2(this.getActivity());
            cardx.title="Application example "+i;
            cardx.secondaryTitle="A company inc..."+i;
            cardx.rating=(float)(Math.random()*(5.0));
            cardx.count=i;
            cards.add(cardx);

            MayKnowCard card2= new MayKnowCard(getActivity());
            card2.setType(2); //Very important with different inner layout
            card2.setSwipeable(true);
            cards.add(card2);

        }

        // Provide a custom adapter.
        // It is important to set the viewTypeCount
        // You have to provide in your card the type value with {@link Card#setType(int)} method.
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
        mCardArrayAdapter.setInnerViewTypeCount(3);

        // An alternative is to write a own CardArrayAdapter
        // MyCardArrayAdapter mCardArrayAdapter = new MyCardArrayAdapter(getActivity(),cards);


        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_base1);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    /**
     *  With multiple inner layouts you have to set the viewTypeCount with {@link CardArrayAdapter#setInnerViewTypeCount(int)}.
     *  </p>
     *  An alternative is to provide your CardArrayAdapter  where you have to override this method:
     *  </p>
     *  public int getViewTypeCount() {}
     *  </p>
     *  You have to provide in your card the type value with {@link Card#setType(int)} method.
     *
     */
    public class MyCardArrayAdapter extends CardArrayAdapter{

        /**
         * Constructor
         *
         * @param context The current context.
         * @param cards   The cards to represent in the ListView.
         */
        public MyCardArrayAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }
    }

    //--------------------------------------------------------------------------
    // Cards
    //--------------------------------------------------------------------------

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

            setSwipeable(true);
        }


        @Override
        public int getType() {
            //Very important with different inner layouts
            return 0;
        }
    }

    public class CardExample2 extends Card{

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail;
        protected int count;

        protected String title;
        protected String secondaryTitle;
        protected float rating;


        public CardExample2(Context context) {
            super(context, R.layout.carddemo_mycard_inner_content);
            init();
        }

        private void init(){

            //Create a CardHeader
            CardHeader header = new CardHeader(getActivity());
            addCardHeader(header);

            //Add ClickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=" + getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

            setSwipeable(true);
        }


        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            //Retrieve elements
            mTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_title);
            mSecondaryTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_secondaryTitle);
            mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_myapps_main_inner_ratingBar);

            if (mTitle != null)
                mTitle.setText(title);

            if (mSecondaryTitle != null)
                mSecondaryTitle.setText(secondaryTitle);

            if (mRatingBar != null) {
                mRatingBar.setNumStars(5);
                mRatingBar.setMax(5);
                mRatingBar.setStepSize(0.5f);
                mRatingBar.setRating(rating);
            }
        }

        @Override
        public int getType() {
            //Very important with different inner layouts
            return 1;
        }
    }



}
