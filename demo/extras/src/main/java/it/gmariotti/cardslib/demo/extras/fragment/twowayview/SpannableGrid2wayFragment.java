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

package it.gmariotti.cardslib.demo.extras.fragment.twowayview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.SpacingItemDecoration;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.fragment.BaseListFragment;
import it.gmariotti.cardslib.library.extra.twoway.view.CardTwowayView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.internal.recyclerview.CardArrayRecyclerViewAdapter;

/**
 * This example uses a staggered card with different different photos and text.
 * <p/>
 * This example uses cards with a foreground layout.
 * Pay attention to style="@style/card.main_layout_foreground" in card layout.
 * <p/>
 * .DynamicHeightPicassoCardThumbnailView
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SpannableGrid2wayFragment extends BaseListFragment {

    CardArrayRecyclerViewAdapter mCardArrayAdapter;
    CardTwowayView mRecyclerView;

    public SpannableGrid2wayFragment() {
        super();
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_spannable2viewway;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_extras_fragment_spannablegrid_twowayview, container, false);
        setupListFragment(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hideList(false);

        //Set the arrayAdapter
        ArrayList<Card> cards = new ArrayList<Card>();

        mCardArrayAdapter = new SpanCardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        mRecyclerView = (CardTwowayView) getActivity().findViewById(R.id.carddemo_extras_span_2wayview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.addItemDecoration(new SpacingItemDecoration(16,16));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }

    class SpanCardArrayRecyclerViewAdapter extends CardArrayRecyclerViewAdapter{

        /**
         * Constructor
         *
         * @param context The current context.
         * @param cards   The cards to represent in the ListView.
         */
        public SpanCardArrayRecyclerViewAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public void onBindViewHolder(CardViewHolder cardViewHolder, int position) {
            super.onBindViewHolder(cardViewHolder, position);

            boolean isVertical = (mRecyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
            final View itemView = cardViewHolder.itemView;

            final SpannableGridLayoutManager.LayoutParams lp =
                    (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();

            GPlayCommonCard card = (GPlayCommonCard) getItem(position);

            final int colSpan = card.colSpan;
            final int rowSpan = card.rowSpan;

            if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
                lp.rowSpan = rowSpan;
                lp.colSpan = colSpan;
                itemView.setLayoutParams(lp);
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    // Loader
    //-------------------------------------------------------------------------------------------------------------


    /**
     * Async Task to elaborate images
     */
    class LoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<Card>> {

        LoaderAsyncTask() {
        }

        @Override
        protected ArrayList<Card> doInBackground(Void... params) {
            //elaborate images
            SystemClock.sleep(1000); //delay to simulate download, don't use it in a real app
            ArrayList<Card> cards = initCard();
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            updateAdapter(cards);
            displayList();
        }
    }


    /**
     * This method builds a simple list of cards
     */
    private ArrayList<Card> initCard() {

        int numCols = getResources().getInteger(R.integer.carddemo_extra_spannedgrid_numcols);



        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 100; i++) {

            GPlayCommonCard card;

            if (i == 0 || i % numCols == 0){
                card = new GPlayGridCardLarge(getActivity());
                card.secondaryTitle = "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi" + i;
            }else{
                card = new GplayGridCard(getActivity());
                card.secondaryTitle = "Some text here " + i;
            }

            //Only for test, use different titles and ratings
            card.headerTitle = "App example " + i;

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

        return cards;

    }

    /**
     * Update the adapter
     */
    private void updateAdapter(ArrayList<Card> cards) {
        if (cards != null) {
            mCardArrayAdapter.addAll(cards);
            //mCardArrayAdapter.notifyDataSetChanged();
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    // Cards
    //-------------------------------------------------------------------------------------------------------------

    public class SpannedCommonCard extends  Card {

        public int rowSpan = 1;
        public int colSpan = 1;

        public SpannedCommonCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

    }

    public class GPlayCommonCard extends  SpannedCommonCard {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;

        protected String headerTitle;
        protected String secondaryTitle;
        protected float rating;

        public GPlayCommonCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        public void init() {

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

    /**
     * A card as Google Play
     */
    public class GplayGridCard extends GPlayCommonCard {

        public GplayGridCard(Context context) {
            super(context, R.layout.carddemo_extras_gplay_inner_content);
        }

    }


    public class GPlayGridCardLarge extends GPlayCommonCard{

        public GPlayGridCardLarge(Context context) {
            super(context, R.layout.carddemo_extras_gplay_inner_large_content);
            super.colSpan = 2;
        }

        @Override
        public int getType() {
            return 1;
        }
    }
}
