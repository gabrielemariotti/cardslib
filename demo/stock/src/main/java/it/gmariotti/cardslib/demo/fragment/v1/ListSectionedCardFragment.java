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

package it.gmariotti.cardslib.demo.fragment.v1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.prototypes.CardSection;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * List of Google Play cards with sections
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListSectionedCardFragment extends MaterialV1Fragment {

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_sectioned;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group5;
    }

    @Override
    protected String getDocUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/EXPAND.md";
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/v1/ListSectionedCardFragment.java";
    }

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_sectioned_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_list_gplaycard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init the cards
        initCards();
    }

    /**
     * Init
     */
    private void initCards() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i=0;i<200;i++){
            GooglePlaySmallCard card = new GooglePlaySmallCard(this.getActivity());
            card.setTitle("Application example "+i);
            card.setSecondaryTitle("A company inc..."+i);
            card.setRating((float)(Math.random()*(5.0)));
            card.count=i;
            card.init();

            //Add card to array
            cards.add(card);
        }

        //Standard array
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        // Sections code.
        // Add the card sections
        List<GplayCardSection> sections =
                new ArrayList<GplayCardSection>();

        sections.add(new GplayCardSection(1,"Section 1","More"));
        sections.add(new GplayCardSection(3,"Section 2","Other"));

        GplayCardSection[] dummy = new GplayCardSection[sections.size()];

        //Sectioned adapter
        GPlaySectionedAdapter mAdapter = new GPlaySectionedAdapter(getActivity(),
                mCardArrayAdapter);
        mAdapter.setCardSections(sections.toArray(dummy));

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_list_gplaycard);
        if (listView!=null){
            listView.setExternalAdapter(mAdapter,mCardArrayAdapter);
        }


    }

    /**
     * Sectioned adapter
     */
    public class GPlaySectionedAdapter extends SectionedCardAdapter {

        public GPlaySectionedAdapter(Context context, CardArrayAdapter cardArrayAdapter) {
            super(context, R.layout.carddemo_gplay_section_layout,cardArrayAdapter);
        }

        @Override
        protected View getSectionView(int position, View view, ViewGroup parent) {

            //Override this method to customize your section's view

            //Get the section
            GplayCardSection section = (GplayCardSection) getCardSections().get(position);

            if (section != null ) {
                //Set the title
                TextView title = (TextView) view.findViewById(R.id.carddemo_section_gplay_title);
                if (title != null)
                    title.setText(section.getTitle());

                //Set the button
                TextView buttonMore = (TextView) view.findViewById(R.id.carddemo_section_gplay_textmore);
                if (buttonMore != null)
                    buttonMore.setText(section.mButtonTxt);
            }

            return view;
        }
    }


    /*
     * Custom Card section
     */
    public class GplayCardSection extends CardSection {

        public CharSequence mButtonTxt;

        public GplayCardSection(int firstPosition, CharSequence title, CharSequence buttonText) {
            super(firstPosition, title);
            mButtonTxt = buttonText;
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
            init();
        }

        private void init() {

            //Add thumbnail
            CardThumbnail cardThumbnail = new CardThumbnail(mContext);
            cardThumbnail.setDrawableResource(R.drawable.ic_std_launcher);

            addCardThumbnail(cardThumbnail);

            //Add ClickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=" + title, Toast.LENGTH_SHORT).show();
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
