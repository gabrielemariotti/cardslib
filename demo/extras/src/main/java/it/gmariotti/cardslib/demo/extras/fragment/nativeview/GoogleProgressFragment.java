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

package it.gmariotti.cardslib.demo.extras.fragment.nativeview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.fragment.BaseFragment;
import it.gmariotti.cardslib.library.cards.ProgressCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.internal.recyclerview.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.view.CardRecyclerView;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GoogleProgressFragment extends BaseFragment {

    public static final int SIMULATED_REFRESH_LENGTH = 5000;

    CardArrayRecyclerViewAdapter mCardArrayAdapter;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_googleprogress;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.demo_extras_fragment_googleprogress, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
        new UpdateAsyncTask((GoogleProgressBarCard)mCardArrayAdapter.getItem(1)).execute();
    }

    /**
     * This method builds a simple list of cards
     */
    private void initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 20; i++) {
            GoogleProgressBarCard card = new GoogleProgressBarCard(this.getActivity());
            card.setTitle("A simple card loaded with Picasso " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);
            card.setId(""+i);
            card.setProgressBarViewStubLayoutId(R.layout.carddemo_native_smoothprogress);
            card.setUseProgressBar(true);

            cards.add(card);
        }


        //Set the adapter
        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        CardRecyclerView mRecyclerView = (CardRecyclerView) getActivity().findViewById(R.id.carddemo_extras_recycler_progress);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

    }

    /**
     * Only for test
     */
    class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        GoogleProgressBarCard mCard;

        UpdateAsyncTask(GoogleProgressBarCard card){
            mCard = card;
        }

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
            mCard.setTitle("Updated card ");
            mCard.updateProgressBar(true, true);
            mCardArrayAdapter.notifyDataSetChanged();

        }
    }


    public class GoogleProgressBarCard extends ProgressCard {

        protected String mTitle;
        protected String mSecondaryTitle;
        protected int count;

        public GoogleProgressBarCard(Context context) {
            this(context, R.layout.carddemo_extra_picasso_inner_content);
        }

        public GoogleProgressBarCard(Context context, int innerLayout) {
            super(context, innerLayout);
            init();
        }

        private void init() {

            //Add Progress
            CardHeader header = new CardHeader(getActivity());
            header.setTitle("Progress Card");
            header.setPopupMenu(R.menu.update,new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
                    if (item.getItemId() == R.id.action_update){
                        new UpdateAsyncTask((GoogleProgressBarCard)card).execute();
                        ((GoogleProgressBarCard)card).updateProgressBar(false, false);
                        //mCardArrayAdapter.notifyDataSetChanged();
                    }
                }
            });
            addCardHeader(header);

            //Add thumbnail
            PicassoCardThumbnail cardThumbnail = new PicassoCardThumbnail(mContext);
            //It must be set to use a external library!
            cardThumbnail.setExternalUsage(true);
            addCardThumbnail(cardThumbnail);

            //Add ClickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    String cardTitle = mId != null ? mId : mTitle;
                    Toast.makeText(getContext(), "Click Listener card=" + cardTitle, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            super.setupInnerViewElements(parent, view);

            //Retrieve elements
            TextView title = (TextView) parent.findViewById(R.id.carddemo_extra_picasso_main_inner_title);
            TextView secondaryTitle = (TextView) parent.findViewById(R.id.carddemo_extra_picasso_main_inner_secondaryTitle);

            if (title != null)
                title.setText(mTitle);

            if (secondaryTitle != null)
                secondaryTitle.setText(mSecondaryTitle);

        }

        /**
         * CardThumbnail which uses Picasso Library.
         * If you use an external library you have to provide your login inside #setupInnerViewElements.
         * <p/>
         * This method is called before built-in method.
         * If {@link it.gmariotti.cardslib.library.internal.CardThumbnail#isExternalUsage()} is false it uses the built-in method.
         */
        class PicassoCardThumbnail extends CardThumbnail {

            public PicassoCardThumbnail(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                //It is just an example !
                Picasso.with(getContext()).setIndicatorsEnabled(true);  //only for debug tests
                Picasso.with(getContext())
                        .load(R.drawable.ic_tris)
                        .resize(96, 96)
                        .into((ImageView) viewImage);
            }
        }


        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getSecondaryTitle() {
            return mSecondaryTitle;
        }

        public void setSecondaryTitle(String secondaryTitle) {
            mSecondaryTitle = secondaryTitle;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
