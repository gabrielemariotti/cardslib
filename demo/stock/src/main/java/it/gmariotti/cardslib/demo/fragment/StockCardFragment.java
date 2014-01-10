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

import android.content.Intent;
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
import it.gmariotti.cardslib.demo.cards.GoogleNowStockCard;
import it.gmariotti.cardslib.library.utils.BitmapUtils;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Card Examples
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class StockCardFragment extends BaseFragment {

    protected ScrollView mScrollView;
    private CardView cardView;
    private ShareActionProvider mShareActionProvider;
    private File photofile;

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_stock_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_stock_card, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mScrollView = (ScrollView) getActivity().findViewById(R.id.card_scrollview);

        initCard();

        if (photofile==null){
            if (mShareActionProvider != null) {
                getActivity().invalidateOptionsMenu();
            }
        }
    }


    /**
     * This method builds a simple card
     */
    private void initCard() {

        //Create a Card
        GoogleNowStockCard card= new GoogleNowStockCard(getActivity());

        //Set card in the cardView
        cardView = (CardView) getActivity().findViewById(R.id.carddemo_GoogleNowStock);
        cardView.setCard(card);
    }

    private Intent getShareIntent(){
        if (cardView!=null){
            photofile = BitmapUtils.createFileFromBitmap(cardView.createBitmap());
            if (photofile!=null){
                return BitmapUtils.createIntentFromImage(photofile);
            }else{
                return getDefaultIntent();
            }
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


}
