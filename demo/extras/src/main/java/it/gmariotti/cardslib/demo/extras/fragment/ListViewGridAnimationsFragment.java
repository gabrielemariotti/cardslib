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

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;

/**
 * This example uses a grid of cards animated with ListViewAnimations.
 * Please refer to https://github.com/nhaarman/ListViewAnimations for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListViewGridAnimationsFragment extends BaseFragment implements
        ActionBar.OnNavigationListener {

    private CardGridView mListView;
    private CardGridArrayAdapter mCardArrayAdapter;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_listviewgridanimations;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_grid_gplay, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
    }

    @Override
    public void onResume() {
        super.onResume();

        populateNavigationList();
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }

    /**
     * Populate the downDownValues to select the different animations
     */
    private void populateNavigationList() {

        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        final String[] dropdownValues = {"Alpha", "Left", "Right", "Bottom", "Bottom right", "Scale"};

        ActionBar actionBar = getActivity().getActionBar();

        // Specify a SpinnerAdapter to populate the dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                dropdownValues);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(adapter, this);

    }

    /**
     * This method builds a simple list of cards
     */
    private void initCard() {

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {

            GplayGridCard card = new GplayGridCard(getActivity());

            //Only for test, use different titles and ratings
            card.headerTitle = "App example " + i;
            card.secondaryTitle = "Some text here " + i;
            card.rating = (float) (Math.random() * (5.0));

            //Only for test, change some icons
            if ((i % 6 == 0)) {
                card.resourceIdThumbnail = R.drawable.ic_ic_dh_bat;
            } else if ((i % 6 == 1)) {
                card.resourceIdThumbnail = R.drawable.ic_ic_dh_net;
            } else if ((i % 6 == 2)) {
                card.resourceIdThumbnail = R.drawable.ic_tris;
            } else if ((i % 6 == 3)) {
                card.resourceIdThumbnail = R.drawable.ic_info;
            } else if ((i % 6 == 4)) {
                card.resourceIdThumbnail = R.drawable.ic_smile;
            }

            card.init();
            cards.add(card);
        }

        //Set the adapter
        mCardArrayAdapter = new CardGridArrayAdapter(getActivity(), cards);

        mListView = (CardGridView) getActivity().findViewById(R.id.carddemo_extras_grid_base1);
        if (mListView != null) {
            setAlphaAdapter();
        }
    }


    /**
     * Alpha animation
     */
    private void setAlphaAdapter() {
        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    /**
     * Left animation
     */
    private void setLeftAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    /**
     * Right animation
     */
    private void setRightAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingRightInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    /**
     * Bottom animation
     */
    private void setBottomAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    /**
     * Bottom-right animation
     */
    private void setBottomRightAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(new SwingRightInAnimationAdapter(mCardArrayAdapter));
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    /**
     * Scale animation
     */
    private void setScaleAdapter() {
        AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                setAlphaAdapter();
                return true;
            case 1:
                setLeftAdapter();
                return true;
            case 2:
                setRightAdapter();
                return true;
            case 3:
                setBottomAdapter();
                return true;
            case 4:
                setBottomRightAdapter();
                return true;
            case 5:
                setScaleAdapter();
                return true;
            default:
                return false;
        }

    }

    /**
     * A card as Google Play
     */
    public class GplayGridCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;

        protected String headerTitle;
        protected String secondaryTitle;
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.carddemo_extras_gplay_inner_content);
        }

        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {

            //Add header with the overflow button
            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);
            header.setPopupMenu(R.menu.extras_popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

            addCardHeader(header);

            //Add thumbnail
            GplayGridThumb thumbnail = new GplayGridThumb(getContext());
            if (resourceIdThumbnail > -1)
                thumbnail.setDrawableResource(resourceIdThumbnail);
            else
                thumbnail.setDrawableResource(R.drawable.ic_ic_launcher_web);
            addCardThumbnail(thumbnail);

            //Listeners
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    //Do something
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            //Populate the inner elements

            TextView title = (TextView) view.findViewById(R.id.carddemo_extras_gplay_main_inner_title);
            title.setText("FREE");

            TextView subtitle = (TextView) view.findViewById(R.id.carddemo_extras_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);

            RatingBar mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_extras_gplay_main_inner_ratingBar);

            mRatingBar.setNumStars(5);
            mRatingBar.setMax(5);
            mRatingBar.setStepSize(0.5f);
            mRatingBar.setRating(rating);
        }

        /**
         * CardThumbnail
         */
        class GplayGridThumb extends CardThumbnail {

            public GplayGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                /*
                viewImage.getLayoutParams().width = 196;
                viewImage.getLayoutParams().height = 196;
                */

            }
        }

    }


}
