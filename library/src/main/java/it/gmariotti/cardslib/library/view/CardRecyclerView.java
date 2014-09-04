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
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import it.gmariotti.cardslib.library.R;
import it.gmariotti.cardslib.library.internal.recyclerview.BaseRecyclerViewAdapter;


/**
 *
 * RecyclerView for Cards.
 * It uses a {@link BaseRecyclerViewAdapter} to populate items.
 * </p>
 * Usage:
 * <pre><code>
 *    <it.gmariotti.cardslib.library.view.CardRecyclerView
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      android:id="@+id/listId"
 *      card:list_card_layout_resourceID="@layout/list_card_thumbnail_layout" /> *
 * </code></pre>
 * It provides a default layout id for each row @layout/list_card_layout
 * Use can easily customize it using card:list_card_layout_resourceID attr in your xml layout.
 * </p>
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CardRecyclerView extends RecyclerView {

    /**
     *  Card Adapter
     */
    protected BaseRecyclerViewAdapter mAdapter;

    //--------------------------------------------------------------------------
    // Custom Attrs
    //--------------------------------------------------------------------------

    /**
     * Default layout to apply to card
     */
    protected @LayoutRes
    int list_card_layout_resourceID = R.layout.list_card_layout;

    /**
     * Layouts to apply to card
     */
    protected @LayoutRes int[] list_card_layout_resourceIDs;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public CardRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
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
    protected void init(Context context,AttributeSet attrs, int defStyle){

        //Init attrs
        initAttrs(context,attrs,defStyle);

    }


    /**
     * Init custom attrs.
     *
     * @param attrs
     * @param defStyle
     */
    protected void initAttrs(Context context,AttributeSet attrs, int defStyle) {

        list_card_layout_resourceID = R.layout.list_card_layout;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.card_options, defStyle, defStyle);

        try {
            list_card_layout_resourceID = a.getResourceId(R.styleable.card_options_list_card_layout_resourceID, this.list_card_layout_resourceID);

            int arrayIds = a.getResourceId(R.styleable.card_options_list_card_layout_resourceIDs, 0);
            if (arrayIds > 0 ) {
                TypedArray layouts = context.getResources().obtainTypedArray(arrayIds);
                if (layouts != null){
                    list_card_layout_resourceIDs = new int[layouts.length()];
                    for (int i=0; i<layouts.length();i++){
                        list_card_layout_resourceIDs[i] = layouts.getResourceId(i, R.layout.list_card_layout);
                    }
                }
                layouts.recycle();
            }


        } finally {
            a.recycle();
        }
    }

    //--------------------------------------------------------------------------
    // Adapter
    //--------------------------------------------------------------------------

    /**
     * Set {@link it.gmariotti.cardslib.library.internal.recyclerview.BaseRecyclerViewAdapter} and layout used by items in RecyclerView
     *
     * @param adapter {@link it.gmariotti.cardslib.library.internal.recyclerview.BaseRecyclerViewAdapter}
     */
    public void setAdapter(BaseRecyclerViewAdapter adapter) {
        super.setAdapter(adapter);

        //Set Layout used by items
        adapter.setRowLayoutId(list_card_layout_resourceID);
        adapter.setRowLayoutIds(list_card_layout_resourceIDs);

        mAdapter=adapter;

        setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof BaseRecyclerViewAdapter.CardViewHolder){
                    ((BaseRecyclerViewAdapter.CardViewHolder)viewHolder).recycled = true;
                }
            }
        });
    }
}
