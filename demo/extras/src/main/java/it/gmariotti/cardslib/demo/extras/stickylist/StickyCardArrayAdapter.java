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

package it.gmariotti.cardslib.demo.extras.stickylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.cards.ColorCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class StickyCardArrayAdapter extends CardArrayAdapter implements StickyListHeadersAdapter {

    /**
     * {@link CardListView}
     */
    protected StickyCardListView mCardListView;

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards   The cards to represent in the ListView.
     */
    public StickyCardArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {

       // Build your custom HeaderView
       //In this case I will use a Card, but you can use any view

        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(R.layout.carddemo_extras_sticky_header, null);

        CardView cardView= (CardView)view.findViewById(R.id.carddemo_card_sticky_header_id);
        Card card = getItem(position);
        char headerChar = card.getTitle().subSequence(0, 1).charAt(0);

        ColorCard colorCard = new ColorCard(getContext());
        colorCard.setTitle("Header : " + headerChar);
        switch (position / 8) {
            case 0:
                colorCard.setBackgroundResourceId(R.drawable.demoextra_card_selector_color1);
                break;
            case 1:
                colorCard.setBackgroundResourceId(R.drawable.demoextra_card_selector_color2);
                break;
            case 2:
                colorCard.setBackgroundResourceId(R.drawable.demoextra_card_selector_color3);
                break;
            case 3:
                colorCard.setBackgroundResourceId(R.drawable.demoextra_card_selector_color4);
                break;
            case 4:
                colorCard.setBackgroundResourceId(R.drawable.demoextra_card_selector_color5);
                break;
            default:
                colorCard.setBackgroundResourceId(R.drawable.demoextra_card_selector_color1);
                break;
        }

        cardView.setCard(colorCard);
        return view;
    }

    @Override
     public long getHeaderId(int position) {
        Card card = getItem(position);
        return card.getTitle().subSequence(0, 1).charAt(0);
    }


    /**
     * Sets the {@link CardListView}
     *
     * @param cardListView
     *            cardListView
     */
    public void setCardListView(StickyCardListView cardListView) {
        this.mCardListView = cardListView;
    }
}
