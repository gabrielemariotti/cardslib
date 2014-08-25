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

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeHeaderFragment;
import it.gmariotti.cardslib.demo.fragment.v1.CardExpandFragment;
import it.gmariotti.cardslib.demo.fragment.v1.CardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.HeaderFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ShadowFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ThumbnailFragment;
import it.gmariotti.cardslib.demo.ui.widget.CollectionView;
import it.gmariotti.cardslib.demo.ui.widget.CollectionViewCallbacks;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class V1DashFragment extends NativeDashFragment{

    public static V1DashFragment newInstance() {
        return new V1DashFragment();
    }

    protected NativeDashAdapter buildAdapter(){
        return new NativeDashAdapter(getActivity(),buildMenuList());
    }

    private static class V1DashAdapter extends NativeDashFragment.NativeDashAdapter implements CollectionViewCallbacks {

        int TOKEN = 0x1;
        int TOKEN2 = 0x2;
        int TOKEN3 = 0x2;

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
                    .setItemCount(8)
                    .setDataIndexStart(5)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group2))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN3)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(4)
                    .setDataIndexStart(13)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group3))
                    .setShowHeader(true));

            return inventory;
        }

    }


    private static List<MenuEntry> buildMenuList(){
        ArrayList<MenuEntry> list = new ArrayList<MenuEntry>();
        list.add(new MenuEntry(R.string.carddemo_title_tag_header,R.color.md_indigo_200,R.string.header_title_cardheadersubtitle, HeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_shadow,R.color.md_green_300,R.string.header_title_cardshadowsubtitle, ShadowFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_thumbnail,R.color.md_orange_300,R.string.header_title_cardthumbnailsubtitle, ThumbnailFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_card,R.color.md_yellow_700,R.string.header_title_cardsubtitle, CardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_card_expand,R.color.md_cyan_400,R.string.header_title_cardexpandsubtitle, CardExpandFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_gplay,R.color.md_green_300,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_gnow,R.color.md_teal_200,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_gplus,R.color.md_indigo_600,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_birthday_card,R.color.md_indigo_A400,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_stock_card,R.color.md_pink_A200,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_play_card,R.color.md_brown_300,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_misc_card,R.color.md_green_600,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_card_changevalue,R.color.md_blue_600,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_todo,R.color.md_blue_A200,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_todo,R.color.md_cyan_700,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_todo,R.color.md_red_800,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_todo,R.color.md_blue_900,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));

        return list;
    }
}
