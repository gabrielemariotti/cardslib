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

package it.gmariotti.cardslib.library.internal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Please note that this is currently in a preview state.
 * Don't use it.
 *
 * Adapter for {@link Card} model
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardArrayAdapter extends ArrayAdapter<Card> {

    /**
     * Current context
     */
    protected Context mContext;

    /**
     * Default layout used for each row
     */
    protected int mRowLayoutId = R.layout.list_card_layout;

    /**
     * CardListView
     */
    protected CardListView mCardListView;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    /**
     * Constructor
     *
     * @param context The current context.
     * @param cards The cards to represent in the ListView.
     */
    public CardArrayAdapter(Context context,  List<Card> cards) {
        super(context, 0 , cards);
        mContext=context;
    }

    // -------------------------------------------------------------
    // Views
    // -------------------------------------------------------------

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        CardView mCardView;
        Card mCard;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Retrieve card from items
        mCard=(Card) getItem(position);
        if (mCard!=null){

            int layout = mRowLayoutId;
            boolean recycle=false;

            //Inflate layout
            if (view==null){
                recycle=false;
                view = mInflater.inflate(layout, parent, false);
            }else{
                recycle=true;
            }

            //Setup card
            mCardView = (CardView) view.findViewById(R.id.list_cardId);
            if (mCardView!=null){
                //It is important to set recycle value for performance issue
                mCardView.setRecycle(recycle);
                mCardView.setCard(mCard);

                //If card has an expandable button override animation
                if (mCard.getCardHeader()!=null && mCard.getCardHeader().isButtonExpandVisible()){
                    mCardView.setOnExpandListAnimatorListener(mCardListView);
                }
            }
        }

        return view;
    }

    // -------------------------------------------------------------
    //  Getters and Setters
    // -------------------------------------------------------------

    /**
     * Returns current context
     *
     * @return current context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Sets layout resource ID used by rows
     *
     * @param rowLayoutId   layout resource id
     */
    public void setRowLayoutId(int rowLayoutId) {
        this.mRowLayoutId = rowLayoutId;
    }

    /**
     *
     * @return {@link CardListView}
     */
    public CardListView getCardListView() {
        return mCardListView;
    }

    /**
     * Sets the {@link CardListView}
     *
     * @param cardListView cardListView
     */
    public void setCardListView(CardListView cardListView) {
        this.mCardListView = cardListView;
    }
}
