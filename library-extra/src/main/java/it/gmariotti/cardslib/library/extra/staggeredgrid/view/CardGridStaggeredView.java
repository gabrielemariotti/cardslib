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

package it.gmariotti.cardslib.library.extra.staggeredgrid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListAdapter;

import com.etsy.android.grid.StaggeredGridView;

import it.gmariotti.cardslib.library.extra.R;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;

/**
 * This class realizes a CardGridStaggeredView.
 *  * Card Grid View.
 * It uses an {@link it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter} to populate items.
 * </p>
 * Usage:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      card:item_margin="8dp"
 *      card:column_count_portrait="3"
 *      card:column_count_landscape="3"
 *      card:grid_paddingRight="8dp"
 *      card:grid_paddingLeft="8dp"
 *      card:list_card_layout_resourceID="@layout/carddemo_extras_staggered_card"
 *      android:id="@+id/carddemo_extras_grid_stag"/>
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout.
 * </p>
 * Use this code to populate the grid view
 * <pre><code>
 * CardGridStaggeredView staggeredView = (CardGridStaggeredView) getActivity().findViewById(R.id.carddemo_extras_grid_stag);
 * gridView.setAdapter(mCardGridArrayAdapter);
 * </code></pre>
 * This type of view, doesn't support swipe and collapse/expand actions.
 * </p>
 * Currently you have to use the same inner layout for each card in gridView.
 * </p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardGridStaggeredView extends StaggeredGridView {

    protected static String TAG = "CardGridStaggeredView";

    /**
     *  Card Grid Staggered Array Adapter
     */
    protected CardGridStaggeredArrayAdapter mAdapter;

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default layout to apply to card
     */
    protected int list_card_layout_resourceID = R.layout.list_card_layout;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------


    public CardGridStaggeredView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardGridStaggeredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardGridStaggeredView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
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
     * Forces to use a {@link CardGridStaggeredArrayAdapter}
     *
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof CardGridStaggeredArrayAdapter){
            setAdapter((CardGridStaggeredArrayAdapter)adapter);
        }else{
            Log.w(TAG, "You are using a generic adapter. Pay attention: your adapter has to call cardGridArrayAdapter#getView method.");
            super.setAdapter(adapter);
        }
    }

    /**
     * Set {@link CardGridStaggeredArrayAdapter} and layout used by items in CardGridStaggeredView
     *
     * @param adapter {@link CardGridStaggeredArrayAdapter}
     */
    public void setAdapter(CardGridStaggeredArrayAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardGridView(this);
        mAdapter=adapter;
    }

    /**
     * You can use this method, if you are using external adapters.
     * Pay attention. The generic adapter#getView() method has to call the cardArrayAdapter#getView() method to work.
     *
     * @param adapter {@link ListAdapter} generic adapter
     * @param cardAdapter    {@link CardGridStaggeredArrayAdapter} cardAdapter
     */
    public void setExternalAdapter(ListAdapter adapter, CardGridStaggeredArrayAdapter cardAdapter) {

        setAdapter(adapter);

        mAdapter=cardAdapter;
        mAdapter.setCardGridView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }
}
