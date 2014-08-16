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

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lucasr.twowayview.widget.SpacingItemDecoration;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.PicassoCard;
import it.gmariotti.cardslib.demo.extras.fragment.BaseListFragment;
import it.gmariotti.cardslib.library.extra.twoway.view.CardTwowayView;
import it.gmariotti.cardslib.library.internal.Card;
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
public class List2wayFragment extends BaseListFragment {

    CardArrayRecyclerViewAdapter mCardArrayAdapter;

    public List2wayFragment() {
        super();
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_list2viewway;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_extras_fragment_list_twowayview, container, false);
        setupListFragment(root);
        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hideList(false);

        //Set the arrayAdapter
        ArrayList<Card> cards = new ArrayList<Card>();

        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        CardTwowayView mRecyclerView = (CardTwowayView) getActivity().findViewById(R.id.carddemo_extras_list_2wayview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.addItemDecoration(new SpacingItemDecoration(8,8));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }


    //-------------------------------------------------------------------------------------------------------------
    // Images loader
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

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            PicassoCard card = new PicassoCard(this.getActivity());
            card.setTitle("A simple card loaded with Picasso " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);
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
        }
    }



}
