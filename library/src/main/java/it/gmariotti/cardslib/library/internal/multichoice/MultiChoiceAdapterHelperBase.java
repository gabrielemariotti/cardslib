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

package it.gmariotti.cardslib.library.internal.multichoice;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MultiChoiceAdapterHelperBase implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener {

    protected static final String TAG = MultiChoiceAdapterHelperBase.class.getSimpleName();

    protected AbsListView mAdapterView;
    protected BaseAdapter owner;
    protected AbsListView.MultiChoiceModeListener mMultiChoiceModeListener;

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
        mAdapterView.setOnItemLongClickListener(this);
        mAdapterView.setMultiChoiceModeListener(mMultiChoiceModeListener);
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
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;

        //Check if the card is checkable
        if (!adapter.isCardCheckable(position)) {
            return false;
        }

        //Check the item
        int correctedPosition = correctPositionAccountingForHeader(adapterView, position);
        long handle = positionToSelectionHandle(correctedPosition);
        boolean wasChecked =  mAdapterView.isItemChecked((int)handle);
        setItemChecked(handle, !wasChecked);

        return false;

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
                adapter.onItemClick(parent,view,position,id);
            }
        }else{
            adapter.onItemClick(parent,view,position,id);
        }
    }

    // -------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------

    public AbsListView.MultiChoiceModeListener getMultiChoiceModeListener() {
        return mMultiChoiceModeListener;
    }

    public void setMultiChoiceModeListener(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
        mMultiChoiceModeListener = multiChoiceModeListener;
    }
}