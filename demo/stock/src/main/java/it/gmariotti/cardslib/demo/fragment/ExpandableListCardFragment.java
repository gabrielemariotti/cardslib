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

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.library.internal.BaseGroupExpandableCard;
import it.gmariotti.cardslib.library.internal.CardExpandableListAdapter;
import it.gmariotti.cardslib.library.view.CardExpandableListView;

/**
 * Example with ExpandableList
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ExpandableListCardFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_title_expandablelistcard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_expandablelist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        //Init an array of Cards
        SparseArray<BaseGroupExpandableCard<String>> cards = new SparseArray<BaseGroupExpandableCard<String>>();
        for (int i=0;i<50;i++){

            List<String> children = new ArrayList<String>();
            for (int j = 0; j < 5; j++) {
                children.add("Sub item"+ j);
            }
            BaseGroupExpandableCard card = new BaseGroupExpandableCard(this.getActivity(),children);
            card.setTitle("Application example "+i);

            cards.append(i,card);
        }

        CardExpandableListAdapter mCardArrayAdapter = new CardExpandableListAdapter(getActivity(),cards);

        CardExpandableListView listView = (CardExpandableListView) getActivity().findViewById(R.id.carddemo_list_expand);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

}
