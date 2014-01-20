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

package it.gmariotti.cardslib.library.internal.multichoice;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */

import android.app.Activity;
import android.content.res.Resources;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

public class MultiChoiceAdapterHelperBase implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener {

    protected static final String TAG = MultiChoiceAdapterHelperBase.class.getSimpleName();

    protected AbsListView mAdapterView;
    protected BaseAdapter owner;
    protected AbsListView.MultiChoiceModeListener mMultiChoiceModeListener;

    /**
     * ActionMode
     */
    protected ActionMode actionMode;

    private boolean ignoreCheckedListener;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public MultiChoiceAdapterHelperBase(BaseAdapter owner) {
        this.owner = owner;
    }

    // -------------------------------------------------------------
    // View
    // -------------------------------------------------------------

    /**
     * Sets the adapter and
     * @param adapterView
     */
    public void setAdapterView(AbsListView adapterView) {
        mAdapterView=adapterView;
        //mAdapterView.setOnItemLongClickListener(this);
        mAdapterView.setMultiChoiceModeListener(mMultiChoiceModeListener);
    }

    /**
     * Used to setup some element events for multichoice
     *
     * @param view
     * @param mCard
     * @param mCardView
     * @param position
     */
    public void setupMultichoice(View view, Card mCard, CardView mCardView, long position) {
        final MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;


        View.OnClickListener advanceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CardView cardView = (CardView) v;
                int position = adapter.getPosition(cardView.getCard());
                onItemClick(mAdapterView, v, position, adapter.getItemId(position));
            }
        };


        //You need it to enable the CAB
        if (mCard.isCheckable()) {
            //mCardView.setLongClickable(true);

            mCardView.setOnClickListener(advanceClickListener);
        }else{
            if (mCard.getOnClickListener()!=null){
                mCardView.setOnClickListener(advanceClickListener);
            }
        }
    }
    /**
     * Checks and unchecks the items
     *
     * @param handle    position
     * @param checked   true if item is checked, false otherwise
     */
    protected void setItemChecked(long handle, boolean checked) {
        if (checked) {
            checkItem(handle);
        } else {
            uncheckItem(handle);
        }
    }

    /**
     * Checks the item
     *
     * @param handle
     */
    protected void checkItem(long handle) {
        mAdapterView.setItemChecked((int)handle,true);
    }

    /**
     * Unckecks the item
     *
     * @param handle
     */
    protected void uncheckItem(long handle) {
        mAdapterView.setItemChecked((int)handle,false);
    }

    // -------------------------------------------------------------
    // OnItemLongClickListener implementation
    // -------------------------------------------------------------

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

        //Check if the card is checkable
        if (!isCardCheckable(position)) {
            return false;
        }


        //Check the item
        int correctedPosition = correctPositionAccountingForHeader(adapterView, position);
        long handle = positionToSelectionHandle(correctedPosition);
        boolean wasChecked =  mAdapterView.isItemChecked((int)handle);

        // invoke the contextual action mode by setting the respective list item to the checked
        setItemChecked(handle, !wasChecked);
        view.setActivated(!wasChecked);

        if (actionMode!=null){
            //You need it to enable the CAB
            //((CardView)view).setLongClickable(false);
        }

        return true;

    }

    private int correctPositionAccountingForHeader(AdapterView<?> adapterView, int position) {
        ListView listView = (adapterView instanceof ListView) ? (ListView) adapterView : null;
        int headersCount = listView == null ? 0 : listView.getHeaderViewsCount();
        if (headersCount > 0) {
            position -= listView.getHeaderViewsCount();
        }
        return position;
    }

    protected long positionToSelectionHandle(int position) {
        return position;
    }

    /**
     * Indicates if the card is checkable
     *
     * @param position
     * @return
     */
    public boolean isCardCheckable(int position) {
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        Card card = adapter.getItem(position);
        if (card != null)
            return card.isCheckable();

        return false;
    }

    // -------------------------------------------------------------
    // OnItemClickListener implementation
    // -------------------------------------------------------------

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        if (adapter.getOptionMultiChoice().isSelectItemClickInActionMode()){
            if (adapter.isActionModeStarted()){
                onItemLongClick(parent, view, position, id);
                return;
            }else{
                //Default card onItemClick
                internal_onItemClick(parent, view, position, id);
            }
        }else{
            //Default card onItemClick
            internal_onItemClick(parent, view, position, id);
        }
    }


    public void internal_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        Card mCard = adapter.getItem(position);
        if (mCard != null && mCard.getOnClickListener() != null)
            mCard.getOnClickListener().onClick(mCard, view);
    }

    // -------------------------------------------------------------
    // ActionMode
    // -------------------------------------------------------------

    public boolean startActionMode(Activity activity) {
        if (activity!=null){
            if (!isActionModeStarted())
                activity.startActionMode(mMultiChoiceModeListener);
        }
        return false;
    }

    /**
     * Called when action mode is first created. The menu supplied will be used to
     * generate action buttons for the action mode.
     *
     * @param mode ActionMode being created
     * @param menu Menu used to populate action buttons
     * @return true if the action mode should be created, false if entering this
     *              mode should be aborted.
     */
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        actionMode = mode;
        onItemSelectedStateChanged(mode);
        //mAdapterView.setOnItemLongClickListener(null);
        //mAdapterView.setLongClickable(false);
        return false;
    }

    /**
     * Called when an action mode is about to be exited and destroyed.
     *
     * @param mode The current ActionMode being destroyed
     */
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        //mAdapterView.setOnItemLongClickListener(this);
    }

    /**
     * Called when an item is checked or unchecked during selection mode.
     *
     * @param mode The {@link ActionMode} providing the selection mode
     * @param position Adapter position of the item that was checked or unchecked
     * @param id Adapter ID of the item that was checked or unchecked
     * @param checked <code>true</code> if the item is now checked, <code>false</code>
     *                if the item is now unchecked.
     */
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        onItemSelectedStateChanged(mode);
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        Card card = adapter.getItem(position);
        adapter.onItemCheckedStateChanged(mode, position, id, checked, card.getCardView(), card);
    }

    /**
     * Manage the title when the items are selected
     *
     * @param mode
     */
    protected void onItemSelectedStateChanged(ActionMode mode) {
        int count = mAdapterView.getCheckedItemCount();

        if (count > 0) {
            Resources res = mAdapterView.getResources();
            String mTitleSelected = res.getQuantityString(R.plurals.card_selected_items, count, count);
            mode.setTitle(mTitleSelected);
        }
    }

    /**
     * Indicate if action mode is started
     *
     * @return
     */
    public boolean isActionModeStarted() {
        if (actionMode != null) {
            return true;
        }
        return false;
    }


    // -------------------------------------------------------------
    // Utility methods
    // -------------------------------------------------------------

    /**
     * Returns the selected cards
     * @return
     */
    public ArrayList<Card> getSelectedCards() {
        SparseBooleanArray checked = mAdapterView.getCheckedItemPositions();
        ArrayList<Card> items = new ArrayList<Card>();
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;


        for (int i =  checked.size()-1; i>=0; i--) {
            if (checked.valueAt(i) == true) {
                items.add( adapter.getItem((int) checked.keyAt(i)));
            }
        }

        return items;
    }
    // -------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------

    /**
     * Get the {@link android.widget.AbsListView.MultiChoiceModeListener}
     * @return
     */
    public AbsListView.MultiChoiceModeListener getMultiChoiceModeListener() {
        return mMultiChoiceModeListener;
    }

    /**
     * Set the {@link android.widget.AbsListView.MultiChoiceModeListener}
     *
     * @param multiChoiceModeListener
     */
    public void setMultiChoiceModeListener(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
        mMultiChoiceModeListener = multiChoiceModeListener;
    }

}