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

package it.gmariotti.cardslib.demo.fragment.nativeview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class TodoNativeCardFragment extends BaseMaterialFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.demo_fragment_todo_native_card, container, false);
        setupBarHeader(mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCards();
    }

    @Override
    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_bigcard;
    }

    @Override
    protected int getTitleHeaderResourceId() {
        return R.string.header_title_examples;
    }

    @Override
    protected String getDocUrl() {
        return null;
    }

    @Override
    protected String getSourceUrl() {
        return null;
    }

    private void initCards() {
       init_simple_card();
    }

    private void init_simple_card() {
         Card card = new Card(getActivity());

        CardHeader header = new CardHeader(getActivity());
        header.setTitle("Header");

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getActivity(), "Click on " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        card.addCardHeader(header);

        //Set card in the CardViewNative
        CardViewNative CardViewNative = (CardViewNative) getActivity().findViewById(R.id.carddemo_card_id);
        CardViewNative.setCard(card);
    }
}
