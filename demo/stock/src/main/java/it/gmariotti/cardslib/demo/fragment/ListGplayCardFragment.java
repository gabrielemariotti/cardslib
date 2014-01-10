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
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * List of Google Play cards Example
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListGplayCardFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_list_gplaycard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_list_gplaycard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0;i<200;i++){
            GooglePlaySmallCard card = new GooglePlaySmallCard(this.getActivity());
            card.setTitle("Application example "+i);
            card.setSecondaryTitle("A company inc..."+i);
            card.setRating((float)(Math.random()*(5.0)));
            card.count=i;

            //Only for test, change some icons
            if ((i>10 && i<15) || (i>35 && i<45)){
                card.setResourceIdThumbnail(R.drawable.ic_launcher);
            }


            card.init();

            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_gplaycard);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }


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

            if (resourceIdThumbnail==0)
                cardThumbnail.setDrawableResource(R.drawable.ic_std_launcher);
            else{
                cardThumbnail.setDrawableResource(resourceIdThumbnail);
            }

            addCardThumbnail(cardThumbnail);

            //Only for test, some cards have different clickListeners
            if (count==12){

                setTitle(title + " No Click");
                setClickable(false);

            }else if (count==20){

                setTitle(title + " Partial Click");
                addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW,new OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Toast.makeText(getContext(), "Partial click Listener card=" + title, Toast.LENGTH_SHORT).show();
                    }
                });

            }else{

                //Add ClickListener
                setOnClickListener(new OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        Toast.makeText(getContext(), "Click Listener card=" + title, Toast.LENGTH_SHORT).show();
                    }
                });

            }


            //Swipe
            if (count>5 && count<13){

                setTitle(title + " Swipe enabled");
                setSwipeable(true);
                setOnSwipeListener(new OnSwipeListener() {
                    @Override
                    public void onSwipe(Card card) {
                        Toast.makeText(getContext(), "Removed card=" + title, Toast.LENGTH_SHORT).show();
                    }
                });
            }

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
