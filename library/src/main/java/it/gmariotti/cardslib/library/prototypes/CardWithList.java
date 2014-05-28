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

package it.gmariotti.cardslib.library.prototypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public abstract class CardWithList extends Card{

    protected List<ListObject> mChildren;
    protected CardHeader mCardHeader;

    protected ListLayout mList;
    protected ListAdapter mListAdapter;

    /**
     * Default layout used for each row
     */
    protected int mChildLayoutId;


    public CardWithList (Context context){
        this (context,R.layout.inner_base_main_cardwithlist);
    }

    public CardWithList(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init(){

        //Init the CardHeader
        mCardHeader = initCardHeader();
        if (mCardHeader != null)
            addCardHeader(mCardHeader);

        initCard();

        mChildren = initChildren();

        mChildLayoutId = getChildLayoutId();
    }

    /**
     *
     * @return
     */
    protected abstract CardHeader initCardHeader();

    /**
     *
     * @return
     */
    protected abstract void initCard();

    /**
     *
     * @return
     */
    protected abstract List<ListObject> initChildren();

    protected int getListResourceId(){
        return R.id.card_inner_base_main_cardwithlist;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        mList = (ListLayout) view.findViewById(getListResourceId());
        if (mList!=null){
            mListAdapter = new ListAdapter(super.getContext(), mChildren);
            mList.setAdapter(mListAdapter);
        }
    }

    public abstract View setupChildView(int childPosition, ListObject object , View convertView, ViewGroup parent);

    public abstract int getChildLayoutId();

    /**
     *
     */
    public interface ListObject{

    }


    private class ListAdapter extends ArrayAdapter<ListObject> {

        List<ListObject> mObjects;
        LayoutInflater mLayoutInflater;

        public ListAdapter(Context context, List<ListObject> objects) {
            super(context, 0, objects);
            mObjects = objects;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListObject object = getItem(position);

            View view = convertView;
            if (view == null) {
                view = mLayoutInflater.inflate(getChildLayoutId(), parent, false);
            }

            setupChildView(position,object,view,parent);

            return view;
        }


        @Override
        public int getViewTypeCount() {
            return 1;
        }
    }


}


