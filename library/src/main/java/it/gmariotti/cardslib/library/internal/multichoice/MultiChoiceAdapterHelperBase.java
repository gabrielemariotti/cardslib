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

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

import it.gmariotti.cardslib.library.R;

public abstract class MultiChoiceAdapterHelperBase implements AdapterView.OnItemLongClickListener {

    protected static final String TAG = MultiChoiceAdapterHelperBase.class.getSimpleName();
    private static final String BUNDLE_KEY = "card__selection";

    private Set<Long> checkedItems = new HashSet<Long>();

    protected AbsListView mAdapterView;
    protected BaseAdapter owner;


    private boolean ignoreCheckedListener;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    protected MultiChoiceAdapterHelperBase(BaseAdapter owner) {
        this.owner = owner;
    }


    public void restoreSelectionFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        long[] array = savedInstanceState.getLongArray(BUNDLE_KEY);
        checkedItems.clear();
        if (array != null) {
            for (long id : array) {
                checkedItems.add(id);
            }
        }
    }

    public void setAdapterView(AbsListView adapterView) {
        mAdapterView=adapterView;
        mAdapterView.setOnItemLongClickListener(this);
        //checkActivity();

        if (!checkedItems.isEmpty()) {
            startActionMode();
            onItemSelectedStateChanged();
        }
    }

    public void checkActivity() {
        Context context = mAdapterView.getContext();
        if (context instanceof ListActivity) {
            throw new RuntimeException("ListView cannot belong to an activity which subclasses ListActivity");
        }
    }


    public void save(Bundle outState) {
        long[] array = new long[checkedItems.size()];
        int i = 0;
        for (Long id : checkedItems) {
            array[i++] = id;
        }
        outState.putLongArray(BUNDLE_KEY, array);
    }

    public void setItemChecked(long handle, boolean checked) {
        if (checked) {
            checkItem(handle);
        } else {
            uncheckItem(handle);
        }
    }

    public void checkItem(long handle) {
        boolean wasSelected = isChecked(handle);
        if (wasSelected) {
            return;
        }
        if (!isActionModeStarted()) {
            startActionMode();
        }
        checkedItems.add((long) handle);
        owner.notifyDataSetChanged();
        onItemSelectedStateChanged();
    }

    public void uncheckItem(long handle) {
        boolean wasSelected = isChecked(handle);
        if (!wasSelected) {
            return;
        }
        checkedItems.remove(handle);
        if (getCheckedItemCount() == 0) {
            finishActionMode();
            return;
        }
        owner.notifyDataSetChanged();
        onItemSelectedStateChanged();
    }

    public Set<Long> getCheckedItems() {
        // Return a copy to prevent concurrent modification problems
        return new HashSet<Long>(checkedItems);
    }

    public int getCheckedItemCount() {
        return checkedItems.size();
    }

    public boolean isChecked(long handle) {
        return checkedItems.contains(handle);
    }

    public Context getContext() {
        return mAdapterView.getContext();
    }

    private void onItemSelectedStateChanged() {
        int count = getCheckedItemCount();
        if (count == 0) {
            finishActionMode();
            return;
        }
        Resources res = mAdapterView.getResources();
        String title = res.getQuantityString(R.plurals.card_selected_items, count, count);
        setActionModeTitle(title);
    }

    protected abstract void setActionModeTitle(String title);
    protected abstract boolean isActionModeStarted();
    protected abstract void startActionMode();
    protected abstract void finishActionMode();
    protected abstract void clearActionMode();


    //
    // OnItemLongClickListener implementation
    //

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        MultiChoiceAdapter adapter = (MultiChoiceAdapter) owner;
        if (!adapter.isCardCheckable(position)) {
            return false;
        }
        int correctedPosition = correctPositionAccountingForHeader(adapterView, position);
        long handle = positionToSelectionHandle(correctedPosition);
        boolean wasChecked = isChecked(handle);
        setItemChecked(handle, !wasChecked);

        return adapter.onItemLongClick(view,position,id);

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

    //
    // ActionMode.Callback related methods
    //
    public void onDestroyActionMode() {
        checkedItems.clear();
        clearActionMode();
        owner.notifyDataSetChanged();
    }


    public View getView(int position, View viewWithoutSelection) {
        long handle = positionToSelectionHandle(position);
        boolean selected = isChecked(handle);
        viewWithoutSelection.setSelected(selected);

        return viewWithoutSelection;
    }
}