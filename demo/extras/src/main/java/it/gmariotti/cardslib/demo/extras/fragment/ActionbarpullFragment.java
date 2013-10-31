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

package it.gmariotti.cardslib.demo.extras.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.MainActivity;
import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.ColorCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

/**
 * This example uses a list card with Thumbnail loaded with built-in method and Picasso library
 * Please refer to https://github.com/square/picasso for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ActionbarpullFragment extends BaseFragment implements PullToRefreshAttacher.OnRefreshListener {

    private PullToRefreshAttacher mPullToRefreshAttacher;

    private CardListView listView;

    public static final int SIMULATED_REFRESH_LENGTH = 5000;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_actionbar_pull_to_refresh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout
        View view = inflater.inflate(R.layout.demo_extras_fragment_actionbarpulltorefresh, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_actionbarpulltorefresh);

        initCard();


        // Now get the PullToRefresh attacher from the Activity. An exercise to the reader
        // is to create an implicit interface instead of casting to the concrete Activity
        mPullToRefreshAttacher = ((MainActivity) getActivity())
                .getPullToRefreshAttacher();

        // Retrieve the PullToRefreshLayout from the content view
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) getActivity().findViewById(R.id.carddemo_extra_ptr_layout);

        // Give the PullToRefreshAttacher to the PullToRefreshLayout, along with a refresh listener.
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

    }

    /**
     * This method builds a simple card
     */
    private void initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            ColorCard card = new ColorCard(this.getActivity());
            card.setTitle("A simple colored card " + i);
            card.setCount(i);
            switch (i) {
                case 0:
                    card.setBackgroundResourceId(R.drawable.demoextra_card_selector_color1);
                    break;
                case 1:
                    card.setBackgroundResourceId(R.drawable.demoextra_card_selector_color2);
                    break;
                case 2:
                    card.setBackgroundResourceId(R.drawable.demoextra_card_selector_color3);
                    break;
                case 3:
                    card.setBackgroundResourceId(R.drawable.demoextra_card_selector_color4);
                    break;
                case 4:
                    card.setBackgroundResourceId(R.drawable.demoextra_card_selector_color5);
                    break;
            }

            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

       // CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_picasso);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }

    }

    @Override
    public void onRefreshStarted(View view) {
        /**
         * Simulate Refresh with 4 seconds sleep
         */
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(SIMULATED_REFRESH_LENGTH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshAttacher that the refresh has finished
                mPullToRefreshAttacher.setRefreshComplete();
            }
        }.execute();
    }
}
