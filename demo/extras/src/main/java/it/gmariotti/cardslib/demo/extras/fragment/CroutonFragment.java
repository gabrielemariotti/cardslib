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

package it.gmariotti.cardslib.demo.extras.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Crouton card
 * Please refer to https://github.com/keyboardsurfer/Crouton for full doc
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CroutonFragment extends BaseFragment {

    @Override
    public int getTitleResourceId() {
        return R.string.carddemo_extras_title_crouton;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_extras_fragment_crouton, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.extras_crouton, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_crouton1:
                crouton1();
                return true;
            case R.id.menu_crouton2:
                crouton2();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * This method builds a simple card
     */
    private void crouton1() {

        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.carddemo_extras_crouton_card, null);

        CardView cardView= (CardView)view.findViewById(R.id.carddemo_card_crouton_id);

        Card card = new Card(getActivity());
        card.setTitle("Crouton Card");
        card.setBackgroundResourceId(R.color.demoextra_card_background_color2);

        CardThumbnail thumb = new CardThumbnail(getActivity());
        thumb.setDrawableResource(R.drawable.ic_action_bulb);
        card.addCardThumbnail(thumb);

        cardView.setCard(card);

        final Crouton crouton;
        crouton = Crouton.make(getActivity(), view);
        crouton.show();

    }

    /**
     * This method builds a simple card
     */
    private void crouton2() {

        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.carddemo_extras_crouton_card, null);

        CardView cardView= (CardView)view.findViewById(R.id.carddemo_card_crouton_id);

        Card card = new Card(getActivity());
        card.setTitle("Crouton Card");

        CardThumbnail thumb = new CardThumbnail(getActivity());
        thumb.setDrawableResource(R.drawable.ic_action_halt);
        card.addCardThumbnail(thumb);

        cardView.setCard(card);

        final Crouton crouton;
        crouton = Crouton.make(getActivity(), view);
        crouton.show();

    }

}
