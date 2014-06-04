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

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.staggered.DynamicHeightPicassoCardThumbnailView;
import it.gmariotti.cardslib.demo.extras.staggered.data.Image;
import it.gmariotti.cardslib.demo.extras.staggered.data.MockImageLoader;
import it.gmariotti.cardslib.demo.extras.staggered.data.Section;
import it.gmariotti.cardslib.demo.extras.staggered.data.ServerDatabase;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

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
public class StaggeredGridFragment extends BaseListFragment {

    ServerDatabase mServerDatabase;
    CardGridStaggeredArrayAdapter mCardArrayAdapter;

    public StaggeredGridFragment() {
        super();
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_staggered;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_extras_fragment_staggeredgrid, container, false);
        setupListFragment(root);
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new LoaderInitAsyncTask(activity).execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hideList(false);

        //Set the arrayAdapter
        ArrayList<Card> cards = new ArrayList<Card>();
        mCardArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(), cards);

        //Staggered grid view
        CardGridStaggeredView staggeredView = (CardGridStaggeredView) getActivity().findViewById(R.id.carddemo_extras_grid_stag);

        //Set the empty view
        staggeredView.setEmptyView(getActivity().findViewById(android.R.id.empty));
        if (staggeredView != null) {
            staggeredView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }


    //-------------------------------------------------------------------------------------------------------------
    // Images loader
    //-------------------------------------------------------------------------------------------------------------

    /**
     * Async Task to init images
     */
    class LoaderInitAsyncTask extends AsyncTask<Void, Void, Void> {

        Context mContext;


        LoaderInitAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Initialize Image loader
            MockImageLoader loader = MockImageLoader.getInstance(((Activity) mContext).getApplication());
            mServerDatabase = new ServerDatabase(loader);
            return null;
        }
    }

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
            mServerDatabase.getImagesForSection(Section.STAG);
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

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 100; i++) {

            StaggeredCard card = new StaggeredCard(getActivity());
            card.headerTitle = "PHOTO " + i;

            //Only for test, use different images from images loader
            int xx = i % 8;
            switch (xx) {
                case 0:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(0);
                    break;
                case 1:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(1);
                    break;
                case 2:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(2);
                    break;
                case 3:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(3);
                    break;
                case 4:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(4);
                    break;
                case 5:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(5);
                    break;
                case 6:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(6);
                    break;
                case 7:
                    card.image = mServerDatabase.getImagesForSection(Section.STAG).get(7);
                    break;
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
            mCardArrayAdapter.notifyDataSetChanged();
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    // Cards
    //-------------------------------------------------------------------------------------------------------------


    /**
     * Card
     */
    public class StaggeredCard extends Card {

        protected int height;
        protected String headerTitle;

        protected Image image;

        public StaggeredCard(Context context) {
            super(context, R.layout.carddemo_extras_staggered_inner_main);
        }

        private void init() {
            /*
            //Add the header
            CardHeader header = new CardHeader(getContext());
            header.setTitle(headerTitle);
            header.setPopupMenu(R.menu.extras_popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    Toast.makeText(getContext(),"Header:"+ ((Card) card).getCardHeader().getTitle() + "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            addCardHeader(header);
            */

            //Add the thumbnail
            StaggeredCardThumb thumbnail = new StaggeredCardThumb(getContext());
            thumbnail.image = image;
            addCardThumbnail(thumbnail);

            //A simple clickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    //Do something
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView title = (TextView) view.findViewById(R.id.carddemo_staggered_inner_title);
            title.setText(image.title.toUpperCase());

            TextView subtitle = (TextView) view.findViewById(R.id.carddemo_staggered_inner_subtitle);
            subtitle.setText(getString(R.string.carddemo_extras_title_stag));
        }


        /**
         * A StaggeredCardThumbnail.
         * It uses a DynamicHeightPicassoCardThumbnailView which  maintains its own width to height ratio.
         */
        class StaggeredCardThumb extends CardThumbnail {

            Image image;

            public StaggeredCardThumb(Context context) {
                super(context);
                setExternalUsage(true);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                //Use a DynamicHeightPicassoCardThumbnailView to maintain width/height ratio
                DynamicHeightPicassoCardThumbnailView thumbView = (DynamicHeightPicassoCardThumbnailView) getCardThumbnailView();
                thumbView.bindTo(image);

            }
        }

    }


}
