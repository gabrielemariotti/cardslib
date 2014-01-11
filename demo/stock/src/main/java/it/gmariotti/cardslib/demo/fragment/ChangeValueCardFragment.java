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
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.cards.ColorCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Refresh/Replace card value example
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ChangeValueCardFragment extends BaseFragment {

    protected ScrollView mScrollView;
    private CardExample card1;
    private CardExample2 card2;
    private CardExample3 card3;
    private ColorCard card4;

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;



    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_card_changevalue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_changevalue, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCards();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.changevalue, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_refresh:
                changeCard1();
                changeCard2();
                changeCard3();
                changeCard4();
                return true;
            default:
                break;
        }
        return false;
    }


    /**
     * Inits the initial card
     */
    private void initCards() {

        card1 = new CardExample(getActivity(),"Header", "Title");
        cardView1 = (CardView) getActivity().findViewById(R.id.carddemo_card_changevalue_id);
        cardView1.setCard(card1);

        card2 = new CardExample2(getActivity(),"Header", "Title");
        cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_card_changevalue_id2);
        cardView2.setCard(card2);

        card3 = new CardExample3(getActivity(),"Header", "Title");
        cardView3 = (CardView) getActivity().findViewById(R.id.carddemo_card_changevalue_id3);
        cardView3.setCard(card3);

        card4 = new ColorCard(getActivity());
        card4.setTitle("A simple car");
        cardView4 = (CardView) getActivity().findViewById(R.id.carddemo_card_changevalue_id4);

        StateListDrawable initDrawable = new StateListDrawable();
        initDrawable.addState(new int[]{android.R.attr.state_pressed},
                getResources().getDrawable(R.drawable.pressed_background_card));
        initDrawable.addState(new int[] {}, getResources().getDrawable(R.drawable.demo_card_background_color1));
        card4.setBackgroundResource(initDrawable);

        cardView4.setCard(card4);
    }


    /**
     * Changes same values
     */
    private void changeCard1() {

        //Change Header
        card1.getCardHeader().setTitle("New header");

        //Change main
        card1.setTitle("New title");

        //Remove click listener
        card1.setOnClickListener(null);
        card1.setClickable(false);

        //Remove shadow
        card1.setShadow(false);

        //Call refresh
        cardView1.refreshCard(card1);

    }


    /**
     * Changes same values
     */
    private void changeCard2() {

        //Change Header
        card2.getCardHeader().setTitle("New image");

        card2.getCardThumbnail().setDrawableResource(R.drawable.ic_std_launcher);

        //Call refresh
        cardView2.refreshCard(card2);
    }

    /**
     * Replaces inner layout
     */
    private void changeCard3() {

        card3.setInnerLayout(R.layout.carddemo_suggested_inner_content);
        card3.force=true;
        cardView3.replaceCard(card3);
    }

    /**
     * Change background dinamically
     */
    private void changeCard4() {

        StateListDrawable newDrawable = new StateListDrawable();
        newDrawable.addState(new int[]{android.R.attr.state_pressed},
                getResources().getDrawable(R.drawable.pressed_background_card));
        newDrawable.addState(new int[] {}, getResources().getDrawable(R.drawable.demo_card_background_color2));

        card4.setBackgroundResource(newDrawable);
        cardView4.refreshCard(card4);

    }

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
                    Toast.makeText(getContext(), "Click Listener card=" + mTitleHeader, Toast.LENGTH_LONG).show();
                }
            });

            //Set the card inner text
            setTitle(mTitleMain);
        }

    }


    public class CardExample2 extends Card{

        protected String mTitleHeader;
        protected String mTitleMain;

        public CardExample2(Context context,String titleHeader,String titleMain) {
            super(context);
            this.mTitleHeader=titleHeader;
            this.mTitleMain=titleMain;
            init();
        }

        private void init(){

            //Create a CardHeader
            CardHeader header = new CardHeader(getActivity());

            //Set the header title
            header.setTitle(mTitleHeader);
            addCardHeader(header);

            CardThumbnail thumb = new CardThumbnail(getActivity());
            thumb.setDrawableResource(R.drawable.ic_launcher);
            addCardThumbnail(thumb);

            //Add ClickListener
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getContext(), "Click Listener card=" + mTitleHeader, Toast.LENGTH_LONG).show();
                }
            });

            //Set the card inner text
            setTitle(mTitleMain);
        }

    }

    public class CardExample3 extends Card{

        protected String mTitleHeader;
        protected String mTitleMain;
        protected boolean force=false;

        public CardExample3(Context context,String titleHeader,String titleMain) {
            super(context);
            this.mTitleHeader=titleHeader;
            this.mTitleMain=titleMain;
            init();
        }

        private void init(){

            //Create a CardHeader
            CardHeader header = new CardHeader(getActivity());

            //Set the header title
            header.setTitle(mTitleHeader);
            addCardHeader(header);

            CardThumbnail thumb = new CardThumbnail(getActivity());
            thumb.setDrawableResource(R.drawable.ic_launcher);
            addCardThumbnail(thumb);

            //Set the card inner text
            setTitle(mTitleMain);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (force){
                TextView title = (TextView) view.findViewById(R.id.carddemo_suggested_title);
                TextView member = (TextView) view.findViewById(R.id.carddemo_suggested_memeber);
                TextView subtitle = (TextView) view.findViewById(R.id.carddemo_suggested_subtitle);
                TextView community = (TextView) view.findViewById(R.id.carddemo_suggested_community);
                if (title!=null && member!=null && subtitle!=null && community!=null){
                    if (title != null)
                        title.setText(R.string.demo_suggested_title);

                    if (member != null)
                        member.setText(R.string.demo_suggested_member);

                    if (subtitle != null)
                        subtitle.setText(R.string.demo_suggested_subtitle);

                    if (community != null)
                        community.setText(R.string.demo_suggested_community);
                }
            }else{
                super.setupInnerViewElements(parent,view);
            }
        }

    }

}
