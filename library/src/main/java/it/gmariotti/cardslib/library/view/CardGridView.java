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

package it.gmariotti.cardslib.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardGridCursorAdapter;

/**
 * Card Grid View.
 * It uses an {@link it.gmariotti.cardslib.library.internal.CardGridArrayAdapter} to populate items.
 * </p>
 * Usage:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.view.CardGridView
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     android:columnWidth="190dp"
 *     android:numColumns="auto_fit"
 *     android:verticalSpacing="3dp"
 *     android:horizontalSpacing="2dp"
 *     android:stretchMode="columnWidth"
 *     android:gravity="center"
 *     android:id="@+id/carddemo_grid_base1"/>
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout.
 * </p>
 * Use this code to populate the grid view
 * <pre><code>
 * CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.gridId);
 * gridView.setAdapter(mCardGridArrayAdapter);
 * </code></pre>
 * This type of view, doesn't support swipe and collapse/expand actions.
 * </p>
 * Currently you have to use the same inner layout for each card in gridView.
 * </p>
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardGridView extends GridView implements CardView.OnExpandListAnimatorListener {

    protected static String TAG = "CardGridView";

    /**
     *  Card Grid Array Adapter
     */
    protected CardGridArrayAdapter mAdapter;

    /**
     * Card Cursor Adapter
     */
    protected CardGridCursorAdapter mCursorAdapter;

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


    public CardGridView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardGridView(Context context, AttributeSet attrs, int defStyle) {
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
     * Forces to use a {@link CardGridArrayAdapter}
     *
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof CardGridArrayAdapter){
            setAdapter((CardGridArrayAdapter)adapter);
        }else if (adapter instanceof CardGridCursorAdapter){
            setAdapter((CardGridCursorAdapter)adapter);
        }else{
            Log.w(TAG,"You are using a generic adapter. Pay attention: your adapter has to call cardGridArrayAdapter#getView method." );
            super.setAdapter(adapter);
        }
    }

    /**
     * Set {@link CardGridArrayAdapter} and layout used by items in ListView
     *
     * @param adapter {@link CardGridArrayAdapter}
     */
    public void setAdapter(CardGridArrayAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardGridView(this);
        mAdapter=adapter;
    }

    /**
     * Set {@link it.gmariotti.cardslib.library.internal.CardGridCursorAdapter} and layout used by items in ListView
     *
     * @param adapter {@link it.gmariotti.cardslib.library.internal.CardGridCursorAdapter}
     */
    public void setAdapter(CardGridCursorAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);

        adapter.setCardGridView(this);
        mCursorAdapter=adapter;
    }

    /**
     * You can use this method, if you are using external adapters.
     * Pay attention. The generic adapter#getView() method has to call the cardArrayAdapter#getView() method to work.
     *
     * @param adapter {@link ListAdapter} generic adapter
     * @param cardGridArrayAdapter    {@link it.gmariotti.cardslib.library.internal.CardGridArrayAdapter} cardGridArrayAdapter
     */
    public void setExternalAdapter(ListAdapter adapter, CardGridArrayAdapter cardGridArrayAdapter) {

        setAdapter(adapter);

        mAdapter=cardGridArrayAdapter;
        mAdapter.setCardGridView(this);
        mAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    /**
     * You can use this method, if you are using external adapters.
     * Pay attention. The generic adapter#getView() method has to call the cardCursorAdapter#getView() method to work.
     *
     * @param adapter {@link ListAdapter} generic adapter
     * @param cardCursorAdapter    {@link it.gmariotti.cardslib.library.internal.CardCursorAdapter} cardArrayAdapter
     */
    public void setExternalAdapter(ListAdapter adapter, CardGridCursorAdapter cardCursorAdapter) {

        setAdapter(adapter);

        mCursorAdapter=cardCursorAdapter;
        mCursorAdapter.setCardGridView(this);
        mCursorAdapter.setRowLayoutId(list_card_layout_resourceID);
    }

    //--------------------------------------------------------------------------
    // Expand and Collapse animator
    // Don't use this animator in a grid.
    // All cells in the same row should expand/collapse a hidden area of same dimensions.
    //--------------------------------------------------------------------------

    @Override
    public void onExpandStart(CardView viewCard,View expandingLayout) {
        //do nothing. Don't use this kind of animation in a grid
        Log.w(TAG,"Don't use this kind of animation in a grid");
    }

    @Override
    public void onCollapseStart(CardView viewCard,View expandingLayout) {
        //do nothing. Don't use this kind of animation in a grid
        Log.w(TAG,"Don't use this kind of animation in a grid");
    }

}
