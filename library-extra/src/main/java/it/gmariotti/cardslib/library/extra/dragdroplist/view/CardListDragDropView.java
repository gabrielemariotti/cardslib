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

package it.gmariotti.cardslib.library.extra.dragdroplist.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListAdapter;

import com.nhaarman.listviewanimations.widget.DynamicListView;

import it.gmariotti.cardslib.library.extra.R;
import it.gmariotti.cardslib.library.extra.dragdroplist.internal.CardDragDropArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * Card List View with drag and drop support.
 * It uses an {@link CardDragDropArrayAdapter} to populate items.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardListDragDropView extends DynamicListView {

    protected static String TAG = "CardListDragDropView";

    /**
     *  Card Array Adapter
     */
    protected CardDragDropArrayAdapter mAdapter;

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default layout to apply to card
     */
    protected int list_card_layout_resourceID = R.layout.list_card_layout;

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public CardListDragDropView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardListDragDropView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public CardListDragDropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    //--------------------------------------------------------------------------
    // Init
    //--------------------------------------------------------------------------

    /**
     * Initialize
     *
     * @param attrs
     * @param defStyle
     */
    protected void init(AttributeSet attrs, int defStyle){

        //Init attrs
        initAttrs(attrs,defStyle);

        //Set divider to 0dp
        setDividerHeight(0);

    }


    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(AttributeSet attrs, int defStyle) {

        list_card_layout_resourceID = R.layout.list_card_layout;

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            list_card_layout_resourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID, this.list_card_layout_resourceID);
        } finally {
            a.recycle();
        }
    }

    //--------------------------------------------------------------------------
    // Adapter
    //--------------------------------------------------------------------------

    /**
     * Set the adapter. You can provide a {@link it.gmariotti.cardslib.library.extra.dragdroplist.internal.CardDragDropArrayAdapter}
     * or a generic adapter.
     * Pay attention: your generic adapter has to call {@link it.gmariotti.cardslib.library.extra.dragdroplist.internal.CardDragDropArrayAdapter#getView} method
     *
     * @param adapter
     */
    @SuppressWarnings("deprecation")
    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof CardDragDropArrayAdapter){
            setAdapter((CardArrayAdapter)adapter);
        }else {
            Log.w(TAG, "You are using a generic adapter. Pay attention: your adapter has to call cardArrayAdapter#getView method");
            super.setAdapter(adapter);
        }
    }

    /**
     * Set {@link CardDragDropArrayAdapter} and layout used by items in ListView
     *
     * @param adapter {@link (CardDragDropArrayAdapter}
     */
    public void setAdapter(CardDragDropArrayAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardListDragDropView(this);
        mAdapter=adapter;
    }


    /**
     * You can use this method, if you are using external adapters.
     * Pay attention. The generic adapter#getView() method has to call the cardArrayAdapter#getView() method to work.
     *
     * @param adapter {@link ListAdapter} generic adapter
     * @param cardArrayAdapter    {@link CardDragDropArrayAdapter} cardArrayAdapter
     */
    public void setExternalAdapter(ListAdapter adapter, CardDragDropArrayAdapter cardArrayAdapter) {

        setAdapter(adapter);

        mAdapter=cardArrayAdapter;
        mAdapter.setCardListDragDropView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }


}
