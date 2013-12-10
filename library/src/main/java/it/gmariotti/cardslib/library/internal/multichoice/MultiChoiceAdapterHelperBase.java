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

import java.util.HashSet;
import java.util.Set;

public class MultiChoiceAdapterHelperBase implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener {

    protected static final String TAG = MultiChoiceAdapterHelperBase.class.getSimpleName();
    private static final String BUNDLE_KEY = "card__selection";

    private Set<Long> checkedItems = new HashSet<Long>();

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


    public void setAdapterView(AbsListView adapterView) {
        mAdapterView=adapterView;
        mAdapterView.setOnItemLongClickListener(this);
       //mAdapterView.setOnItemClickListener(this);
        mAdapterView.setMultiChoiceModeListener(getMultiChoiceModeListener());
    }

    public void setItemChecked(long handle, boolean checked) {
        if (checked) {
            checkItem(handle);
        } else {
            uncheckItem(handle);
        }
    }

    public void checkItem(long handle) {
        mAdapterView.setItemChecked((int)handle,true);
       }

    public void uncheckItem(long handle) {
        mAdapterView.setItemChecked((int)handle,false);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        if (!adapter.isCardCheckable(position)) {
            return false;
        }
        int correctedPosition = correctPositionAccountingForHeader(adapterView, position);
        long handle = positionToSelectionHandle(correctedPosition);
        boolean wasChecked =  mAdapterView.isItemChecked((int)handle);
        setItemChecked(handle, !wasChecked);

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

    public AbsListView.MultiChoiceModeListener getMultiChoiceModeListener() {
        return mMultiChoiceModeListener;
    }

    public void setMultiChoiceModeListener(AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
        mMultiChoiceModeListener = multiChoiceModeListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        if (adapter.isActionModeStarted()){
            onItemLongClick(parent, view, position, id);
            return;
        }
        adapter.onItemClick(parent,view,position,id);
    }
}