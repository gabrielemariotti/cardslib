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
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayMultiChoiceAdapter;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * List of Google Play cards Example with MultiChoice
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListGplayCardCABFragment extends BaseFragment {

    MyCardArrayMultiChoiceAdapter mCardArrayAdapter;
    CardListView listView;
    ActionMode mActionMode;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_cab_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_list_gplaycard_cab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCards(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mActionMode!=null){
            mActionMode.finish();
        }
    }

    /**
     * This method builds a simple list of cards
     */
    private void initCards(Bundle savedInstanceState) {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            GooglePlaySmallCard card = new GooglePlaySmallCard(this.getActivity());
            card.setTitle("Application example " + i);
            card.setSecondaryTitle("A company inc..." + i);
            card.setBackgroundResourceId(R.drawable.card_multichoice_selector);
            card.setRating((float) (Math.random() * (5.0)));
            card.count = i;
            card.setId("" + i);

            //Only for test, change some icons
            if ((i > 10 && i < 15) || (i > 35 && i < 45)) {
                card.setResourceIdThumbnail(R.drawable.ic_launcher);
            }
            card.init();

            //It is very important.
            //You have to implement this onLongClickListener in your cards to enable the multiChoice
            card.setOnLongClickListener(new Card.OnLongCardClickListener() {
                @Override
                public boolean onLongClick(Card card, View view) {
                    return mCardArrayAdapter.startActionMode(getActivity());

                }
            });

            cards.add(card);
        }


        mCardArrayAdapter = new MyCardArrayMultiChoiceAdapter(getActivity(), cards);
        listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_gplaycard_cab);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        }
    }

    /**
     *
     */
    public class MyCardArrayMultiChoiceAdapter extends CardArrayMultiChoiceAdapter {

        public MyCardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //It is very important to call the super method
            super.onCreateActionMode(mode, menu);

            mActionMode=mode; // to manage mode in your Fragment/Activity

            //If you would like to inflate your menu
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.carddemo_multichoice, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_share) {
                Toast.makeText(getContext(), "Share;" + formatCheckedCard(), Toast.LENGTH_SHORT).show();
                return true;
            }
            if (item.getItemId() == R.id.menu_discard) {
                discardSelectedItems(mode);
                return true;
            }
            return false;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked, CardView cardView, Card card) {
            Toast.makeText(getContext(), "Click;" + position + " - " + checked, Toast.LENGTH_SHORT).show();
        }

        private void discardSelectedItems(ActionMode mode) {
            ArrayList<Card> items = getSelectedCards();
            for (Card item : items) {
                remove(item);
            }
            mode.finish();
        }


        private String formatCheckedCard() {

            SparseBooleanArray checked = mCardListView.getCheckedItemPositions();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < checked.size(); i++) {
                if (checked.valueAt(i) == true) {
                    sb.append("\nPosition=" + checked.keyAt(i));
                }
            }
            return sb.toString();
        }

    }

    //-------------------------------------------------------------------------------------------------------------
    // Cards
    //-------------------------------------------------------------------------------------------------------------

    /**
     * This class provides a simple card as Google Play
     *
     * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
     */
    public class GooglePlaySmallCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail;
        protected int count;

        protected String title;
        protected String secondaryTitle;
        protected float rating;


        public GooglePlaySmallCard(Context context) {
            this(context, R.layout.carddemo_mycard_inner_content);
        }

        public GooglePlaySmallCard(Context context, int innerLayout) {
            super(context, innerLayout);
            //init();
        }

        private void init() {

            //Add thumbnail
            CardThumbnail cardThumbnail = new CardThumbnail(mContext);

            if (resourceIdThumbnail == 0)
                cardThumbnail.setDrawableResource(R.drawable.ic_std_launcher);
            else {
                cardThumbnail.setDrawableResource(resourceIdThumbnail);
            }
            addCardThumbnail(cardThumbnail);

            //Simple clickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(mContext, "Click Listener",Toast.LENGTH_SHORT).show();
                }
            });


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


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSecondaryTitle() {
            return secondaryTitle;
        }

        public void setSecondaryTitle(String secondaryTitle) {
            this.secondaryTitle = secondaryTitle;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public int getResourceIdThumbnail() {
            return resourceIdThumbnail;
        }

        public void setResourceIdThumbnail(int resourceIdThumbnail) {
            this.resourceIdThumbnail = resourceIdThumbnail;
        }
    }


}
