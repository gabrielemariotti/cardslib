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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;

import java.io.File;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.Utils;
import it.gmariotti.cardslib.demo.cards.GooglePlaySmallCard;
import it.gmariotti.cardslib.demo.cards.GplayCard;
import it.gmariotti.cardslib.library.Constants;
import it.gmariotti.cardslib.library.utils.BitmapUtils;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GPlayCardFragment extends BaseFragment {

    protected ScrollView mScrollView;
    private GooglePlaySmallCard cardGmap;
    private CardView cardViewGmap;

    private ShareActionProvider mShareActionProvider;
    private File photofile;
    private ImageBroadcastReceiver mReceiver;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_gplay;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_gplay_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCardSmallCard();
        initCardGooglePlay();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mReceiver==null)
            mReceiver = new ImageBroadcastReceiver();
        activity.registerReceiver(mReceiver,new IntentFilter(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mReceiver!=null)
            getActivity().unregisterReceiver(mReceiver);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sharemenu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.carddemo_menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider.setShareIntent(getShareIntent());

        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * This method builds a simple card
     */
    private void initCardSmallCard() {

        //Create a Card
        cardGmap= new GooglePlaySmallCard(getActivity());
        cardGmap.setId("gplaysmall");

        //Set card in the cardView
        cardViewGmap = (CardView) getActivity().findViewById(R.id.carddemo_gmaps);
        cardViewGmap.setCard(cardGmap);
    }


    /**
     * This method builds a simple card
     */
    private void initCardGooglePlay() {

        //Create a Card
        GplayCard card= new GplayCard(getActivity());

        //Set card in the cardView
        CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_Gplay1);
        cardView.setCard(card);

        //Create a Card
        GplayCard card2= new GplayCard(getActivity());

        //Set card in the cardView
        CardView cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_Gplay2);
        cardView2.setCard(card2);

        if (Utils.isTablet(getActivity())){
            //Create a Card
            GplayCard card3= new GplayCard(getActivity());

            //Set card in the cardView
            CardView cardView3 = (CardView) getActivity().findViewById(R.id.carddemo_Gplay3);
            if (cardView3!=null)
                cardView3.setCard(card3);
        }
    }

    private void updateIntentToShare(){
        if (mShareActionProvider != null) {

            photofile = BitmapUtils.createFileFromBitmap(cardViewGmap.createBitmap());
            getActivity().invalidateOptionsMenu();
        }
    }

    private Intent getShareIntent(){
        if (photofile!=null){
            return BitmapUtils.createIntentFromImage(photofile);
        }else{
            return getDefaultIntent();
        }
    }

    /** Defines a default (dummy) share intent to initialze the action provider.
     * However, as soon as the actual content to be used in the intent
     * is known or changes, you must update the share intent by again calling
     * mShareActionProvider.setShareIntent()
     */
    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }


    /**
     * Broadcast for image downloaded by CardThumbnail
     */
    private class ImageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras!=null){
                boolean result = extras.getBoolean(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT);
                String id = extras.getString(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID);
                if (result){
                    if (id!=null && id.equalsIgnoreCase(cardGmap.getId())){
                        updateIntentToShare();
                    }
                }
            }
        }
    }


}
