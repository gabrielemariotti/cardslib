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
import it.gmariotti.cardslib.demo.fragment.v1.BirthDayCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.CardExpandFragment;
import it.gmariotti.cardslib.demo.fragment.v1.CardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.CardWithListFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ChangeValueCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.DismissAnimFragment;
import it.gmariotti.cardslib.demo.fragment.v1.GPlayCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.GridBaseFragment;
import it.gmariotti.cardslib.demo.fragment.v1.GridCursorCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.GridGplayCABFragment;
import it.gmariotti.cardslib.demo.fragment.v1.GridGplayFragment;
import it.gmariotti.cardslib.demo.fragment.v1.HeaderFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListBaseFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListColorFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListCursorCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListDifferentInnerBaseFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListExpandCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListGplayCardCABFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListGplayCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListGplayCursorCardCABFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListGplayUndoCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ListSectionedCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.MiscCardFragment;
import it.gmariotti.cardslib.demo.fragment.v1.ShadowFragment;
import it.gmariotti.cardslib.demo.fragment.v1.StockCardFragment;
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
                    .setItemCount(10)
                    .setDataIndexStart(10)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group3))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN4)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(3)
                    .setDataIndexStart(20)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group4))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN5)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(3)
                    .setDataIndexStart(23)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group5))
                    .setShowHeader(true));

            return inventory;
        }

    }


    private static List<MenuEntry> buildMenuList(){
        ArrayList<MenuEntry> list = new ArrayList<MenuEntry>();
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_header,R.color.md_indigo_200,R.string.header_title_cardheadersubtitle, HeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_shadow,R.color.md_green_300,R.string.header_title_cardshadowsubtitle, ShadowFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_thumbnail,R.color.md_orange_300,R.string.header_title_cardthumbnailsubtitle, ThumbnailFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_card,R.color.md_yellow_700,R.string.header_title_cardsubtitle, CardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_card_expand,R.color.md_cyan_400,R.string.header_title_cardexpandsubtitle, CardExpandFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_birthday_card,R.color.md_green_300,R.string.header_title_subtitle_ex_gbirth, BirthDayCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_gplay,R.color.md_teal_200,R.string.header_title_subtitle_ex_gplay, GPlayCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_stock_card,R.color.md_indigo_600,R.string.header_title_subtitle_ex_gstock, StockCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_misc_card,R.color.md_indigo_A400,R.string.header_title_subtitle_ex_misc, MiscCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_card_changevalue,R.color.md_pink_A200,R.string.header_title_subtitle_ex_refresh, ChangeValueCardFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_list_base,R.color.md_brown_300,R.string.header_title_subtitle_lbase, ListBaseFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_base_different_layout,R.color.md_green_600,R.string.header_title_subtitle_ldiff, ListDifferentInnerBaseFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_expandable_card,R.color.md_blue_600,R.string.header_title_subtitle_lexpand, ListExpandCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_google_play,R.color.md_blue_A200,R.string.header_title_subtitle_lgplay, ListGplayCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_swipe_undo,R.color.md_blue_A200,R.string.header_title_subtitle_swipe, ListGplayUndoCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_grid_base,R.color.md_cyan_700,R.string.header_title_subtitle_gbase, GridBaseFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_grid_google_play,R.color.md_red_800,R.string.header_title_subtitle_ggplay, GridGplayFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_colored,R.color.md_blue_900,R.string.header_title_subtitle_lcolored, ListColorFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_cursor,R.color.md_orange_800,R.string.header_title_subtitle_lcursor, ListCursorCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_grid_cursor,R.color.md_brown_400,R.string.header_title_subtitle_gcursor, GridCursorCardFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_cab_multichoice,R.color.md_green_500,R.string.header_title_subtitle_cab_multi, ListGplayCardCABFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_cab_grid_multichoice,R.color.md_deep_orange_900,R.string.header_title_subtitle_cab_gmulti, GridGplayCABFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_cab_cursor_multichoice,R.color.md_purple_400,R.string.header_title_subtitle_cab_lcursor, ListGplayCursorCardCABFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_misc_cardwithlist,R.color.md_deep_purple_500,R.string.header_title_subtitle_cardwithlist, CardWithListFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_misc_sectioned,R.color.md_indigo_300,R.string.header_title_subtitle_sectioned, ListSectionedCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_misc_dismiss,R.color.md_teal_300,R.string.header_title_subtitle_dismiss, DismissAnimFragment.class));

        return list;
    }
}
