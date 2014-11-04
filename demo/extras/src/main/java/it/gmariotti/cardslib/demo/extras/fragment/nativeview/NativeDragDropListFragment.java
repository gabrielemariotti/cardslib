/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
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

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;

import java.util.ArrayList;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.PicassoCard;
import it.gmariotti.cardslib.library.extra.dragdroplist.internal.CardDragDropArrayAdapter;
import it.gmariotti.cardslib.library.extra.dragdroplist.view.CardListDragDropView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * This example uses a list of cards with drap and drop support.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeDragDropListFragment extends BaseNativeListFragment implements
        ActionBar.OnNavigationListener {


    private CardListDragDropView  mListView;
    private CardDragDropArrayAdapter mCardArrayAdapter;


    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_dragdrop;
    }

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.carddemo_extras_title_dragdrop;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group1;
    }

    @Override
    protected String getDocUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/RECYCLERVIEW.md";
    }

    @Override
    protected String getSourceUrl() {
        return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/extra/src/main/java/it/gmariotti/cardslib/demo/fragment/nativeview/NativeDragDropListFragment.java";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_native_fragment_dragdrop, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCard();
    }

    /**
     * This method builds a simple list of cards
     */
    private void initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 25; i++) {


            PicassoCard card = new PicassoCard(this.getActivity());
            card.setTitle("A simple card loaded with Picasso " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);

            //Card must have a stable Id.
            card.setId("a"+i);
            card.setSwipeable(true);

            cards.add(card);
        }


        //Set the adapter
        mCardArrayAdapter = new CardDragDropArrayAdapter(getActivity(), cards);

        mListView = (CardListDragDropView) getActivity().findViewById(R.id.carddemo_extra_list_dragdrop);
        if (mListView != null) {
               mListView.setAdapter(mCardArrayAdapter);
        }

        //Listener
        mListView.setOnItemMovedListener(new OnItemMovedListener() {

            @Override
            public void onItemMoved(int originalPosition, int newPosition) {
                Card card = mCardArrayAdapter.getItem(newPosition);
                Toast.makeText(getActivity(),"Card "+card.getId() + " moved to position " + newPosition, Toast.LENGTH_SHORT ).show();
            }
        });
    }

    //-------------------------------------------------------------------------------------------------------------
    // Animations. (these method aren't used in this demo, but they can be called to enable the animations)
    //-------------------------------------------------------------------------------------------------------------

    /**
     * Alpha animation
     */
    private void setAlphaAdapter() {
        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
          mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
        }
    }

    /**
     * Left animation
     */
    private void setLeftAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
            mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
        }
    }

    /**
     * Right animation
     */
    private void setRightAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingRightInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
            mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
        }
    }

    /**
     * Bottom animation
     */
    private void setBottomAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
    }

    /**
     * Bottom-right animation
     */
    private void setBottomRightAdapter() {
        AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(new SwingRightInAnimationAdapter(mCardArrayAdapter));
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
            mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
        }
    }

    /**
     * Scale animation
     */
    private void setScaleAdapter() {
        AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        if (mListView != null) {
            mListView.setExternalAdapter(animCardArrayAdapter,mCardArrayAdapter);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0:
                setAlphaAdapter();
                return true;
            case 1:
                setLeftAdapter();
                return true;
            case 2:
                setRightAdapter();
                return true;
            case 3:
                setBottomAdapter();
                return true;
            case 4:
                setBottomRightAdapter();
                return true;
            case 5:
                setScaleAdapter();
                return true;
            default:
                return false;
        }

    }


}
