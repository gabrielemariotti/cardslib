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
import it.gmariotti.cardslib.demo.cards.GoogleNowBirthCard;
import it.gmariotti.cardslib.library.Constants;
import it.gmariotti.cardslib.library.utils.BitmapUtils;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class BirthDayCardFragment extends BaseFragment {

    protected ScrollView mScrollView;
    private CardView cardView;
    private GoogleNowBirthCard birthCard;

    private ShareActionProvider mShareActionProvider;
    private File photofile;
    private ImageBroadcastReceiver mReceiver;


    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_birthday_card;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_birthday_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);
        initCard();
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
    private void initCard() {
        init1();
        init2();
        init3();
    }


    private void init1(){
        //Create a Card
        birthCard= new GoogleNowBirthCard(getActivity());
        birthCard.setId("myId");

        //Set card in the cardView
        cardView = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth);
        cardView.setCard(birthCard);
    }

    private void init2(){
        GoogleNowBirthCard card2 = new GoogleNowBirthCard(getActivity());
        card2.setId("myId2");
        card2.USE_VIGNETTE=1;

        //Set card in the cardView
        CardView cardView2 = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth2);
        cardView2.setCard(card2);

    }

    private void init3(){
        GoogleNowBirthCard card3 = new GoogleNowBirthCard(getActivity());
        card3.setId("myId3");
        card3.USE_VIGNETTE=2;

        //Set card in the cardView
        CardView cardView3 = (CardView) getActivity().findViewById(R.id.carddemo_cardBirth3);
        cardView3.setCard(card3);
    }


    private void updateIntentToShare(){
        if (mShareActionProvider != null) {

            photofile = BitmapUtils.createFileFromBitmap(cardView.createBitmap());
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
    private class ImageBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras!=null){
                boolean result = extras.getBoolean(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_RESULT);
                String id = extras.getString(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_CARD_ID);
                boolean processError = extras.getBoolean(Constants.IntentManager.INTENT_ACTION_IMAGE_DOWNLOADED_EXTRA_ERROR_LOADING);
                if (result){
                    if (id!=null && id.equalsIgnoreCase(birthCard.getId())){
                        updateIntentToShare();
                    }
                }
            }
        }
    }

}
