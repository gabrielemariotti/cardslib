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

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.extras.R;
import it.gmariotti.cardslib.demo.extras.ui.widget.CollectionView;
import it.gmariotti.cardslib.demo.extras.ui.widget.CollectionViewCallbacks;


/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class V1DashFragment extends NativeDashFragment{

    public static V1DashFragment newInstance() {
        return new V1DashFragment();
    }

    protected NativeDashAdapter buildAdapter(){
        return new V1DashAdapter(getActivity(),buildMenuList());
    }

    private static class V1DashAdapter extends NativeDashFragment.NativeDashAdapter implements CollectionViewCallbacks {

        int TOKEN = 0x1;
        int TOKEN2 = 0x2;
        int TOKEN3 = 0x3;
        int TOKEN4 = 0x4;
        int TOKEN5 = 0x5;

        public V1DashAdapter(Context context, List<MenuEntry> objects) {
            super(context, objects);
        }

        public CollectionView.Inventory getInventory() {
            CollectionView.Inventory inventory = new CollectionView.Inventory();

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(5)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group1))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN2)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(5)
                    .setDataIndexStart(5)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group2))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN3)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(11)
                    .setDataIndexStart(10)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group3))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN4)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(3)
                    .setDataIndexStart(21)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group4))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN5)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(3)
                    .setDataIndexStart(24)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group5))
                    .setShowHeader(true));

            return inventory;
        }

    }


    private static List<MenuEntry> buildMenuList(){
        ArrayList<MenuEntry> list = new ArrayList<MenuEntry>();

        return list;
    }
}
