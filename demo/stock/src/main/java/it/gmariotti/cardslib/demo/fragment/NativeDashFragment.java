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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeCardExpandFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeCardFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeCardWithListFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeHeaderFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeListBaseFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeListColorFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeMaterialCardFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeRecyclerViewFragment;
import it.gmariotti.cardslib.demo.fragment.nativeview.NativeThumbnailFragment;
import it.gmariotti.cardslib.demo.ui.widget.CollectionView;
import it.gmariotti.cardslib.demo.ui.widget.CollectionViewCallbacks;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class NativeDashFragment extends Fragment{

    public static String VIEW_COLOR= "color";

    private CollectionView mCollectionView;

    public interface Callbacks {
        public void onTopicSelected(MenuEntry menuEntry, View clickedView);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {

        @Override
        public void onTopicSelected(MenuEntry menuEntry, View clickedView) {

        }
    };

    private Callbacks mCallbacks = sDummyCallbacks;


    public static NativeDashFragment newInstance() {
        return new NativeDashFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_nativedash, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mCollectionView = (CollectionView) view.findViewById(R.id.menu_collection_vew);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NativeDashAdapter adapter = buildAdapter();
        adapter.setCallbacks(mCallbacks);
        mCollectionView.setCollectionAdapter(adapter);
        mCollectionView.updateInventory(adapter.getInventory());
        mCollectionView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                adapter.hideDescriptionToast();
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    protected NativeDashAdapter buildAdapter(){
        return new NativeDashAdapter(getActivity(),buildMenuList());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    protected static class NativeDashAdapter extends ArrayAdapter<MenuEntry> implements CollectionViewCallbacks {

        protected final Context mContext;
        private Toast mCurrentToast;
        int TOKEN = 0x1;
        int TOKEN2 = 0x2;
        int TOKEN3 = 0x2;
        private Callbacks mCallbacks;

        public NativeDashAdapter(Context context, List<MenuEntry> objects) {
            super(context, 0, objects);
            mContext = context;
        }

        public CollectionView.Inventory getInventory() {
            CollectionView.Inventory inventory = new CollectionView.Inventory();

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(4)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group1))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN2)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(8)
                    .setDataIndexStart(4)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group2))
                    .setShowHeader(true));

            inventory.addGroup(new CollectionView.InventoryGroup(TOKEN3)
                    .setDisplayCols(mContext.getResources().getInteger(R.integer.menu_grid_columns))
                    .setItemCount(4)
                    .setDataIndexStart(12)
                    .setHeaderLabel(mContext.getString(R.string.header_title_group3))
                    .setShowHeader(true));

            return inventory;
        }

        @Override
        public View newCollectionHeaderView(Context context, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_dashmenu_header, parent, false);
        }

        @Override
        public void bindCollectionHeaderView(Context context, View view, int groupId, String headerLabel) {
            ((TextView) view.findViewById(R.id.name)).setText(headerLabel);
        }

        @Override
        public View newCollectionItemView(Context context, int groupId, ViewGroup parent) {
            return newView(context, parent);
        }

        @Override
        public void bindCollectionItemView(Context context, View view, int groupId, int indexInGroup, int dataIndex, Object tag) {
            bindView(view, context, dataIndex);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }


        public View newView(Context context, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_dashmenu, parent, false);
            ViewHolder holder = new ViewHolder();
            assert view != null;
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.description = (ImageButton) view.findViewById(R.id.description);
            view.setTag(holder);
            return view;
        }

        public View bindView(View view, final Context context, int position) {
            ViewHolder holder = (ViewHolder) view.getTag();
            final MenuEntry menuEntry = getItem(position);

            final String hashtag = mContext.getString(menuEntry.titleId);
            holder.name.setText(hashtag);

            view.setBackgroundColor(mContext.getResources().getColor(menuEntry.colorId));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //MenyEntryUtils.openFragment(mContext, menuEntry);
                    mCallbacks.onTopicSelected(menuEntry, view);
                }
            });

            final String desc = mContext.getString(menuEntry.descriptionId);

            if (!TextUtils.isEmpty(desc)) {
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displayDescription(view, desc);
                    }
                });
            } else {
                holder.description.setVisibility(View.GONE);
            }

            return view;
        }

        private void displayDescription(View view, String desc) {
            hideDescriptionToast();
            mCurrentToast = Toast.makeText(mContext.getApplicationContext(), desc, Toast.LENGTH_LONG);
            mCurrentToast.show();
            if (Build.VERSION.SDK_INT >= 16) {
                view.announceForAccessibility(desc);
            }
        }

        public void hideDescriptionToast() {
            if (mCurrentToast != null) {
                mCurrentToast.cancel();
                mCurrentToast = null;
            }
        }

        public void setCallbacks(Callbacks callbacks) {
            mCallbacks = callbacks;
        }

        private static final class ViewHolder {
            TextView name;
            ImageButton description;
        }
    }

    public static class MenyEntryUtils {

        public static BaseFragment openFragment(Context context, String fragmentName) {

            BaseFragment baseFragment;
            try {
                Class<? extends BaseFragment> mClass =   context.getClassLoader().loadClass(fragmentName).asSubclass(BaseFragment.class);
                baseFragment = mClass.newInstance();

            } catch (Exception e) {
                throw new IllegalStateException("Could not load Fragment from class: " + fragmentName, e);
            }

           return baseFragment;
        }


        public static void openFragment(Context context, MenuEntry menuEntry) {

            BaseFragment baseFragment;
            try {
                Class<? extends BaseFragment> mClass = menuEntry.mClass;
                baseFragment = mClass.newInstance();

            } catch (Exception e) {
                throw new IllegalStateException("Could not load Fragment from class: " + menuEntry.mClass.getName(), e);
            }

            if (baseFragment != null) {
                FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container, baseFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }
    }

    public static class MenuEntry {

        public int titleId;
        public int colorId;
        public int descriptionId;
        public Class mClass;

        public MenuEntry(int titleId, int colorId, int descriptionId, Class aClass) {
            this.titleId = titleId;
            this.colorId = colorId;
            this.descriptionId = descriptionId;
            mClass = aClass;
        }
    }

    private static List<MenuEntry> buildMenuList(){
        ArrayList<MenuEntry> list = new ArrayList<MenuEntry>();
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_header,R.color.md_indigo_200,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_thumbnail,R.color.md_orange_300,R.string.header_title_cardthumbnailsubtitle, NativeThumbnailFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_card,R.color.md_yellow_700,R.string.header_title_cardsubtitle, NativeCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_base_card_expand,R.color.md_cyan_400,R.string.header_title_cardexpandsubtitle, NativeCardExpandFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_gplay,R.color.md_green_300,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_gnow,R.color.md_teal_200,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_material,R.color.md_indigo_600,R.string.header_title_cardheadersubtitle, NativeMaterialCardFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_birthday_card,R.color.md_indigo_A400,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_stock_card,R.color.md_pink_A200,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_play_card,R.color.md_brown_300,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_misc_card,R.color.md_green_600,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_ex_card_changevalue,R.color.md_blue_600,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));

        list.add(new MenuEntry(R.string.carddemo_title_tag_list_recyclerView,R.color.md_blue_A200,R.string.header_title_subtitle_recyclerview, NativeRecyclerViewFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_colored,R.color.md_cyan_700,R.string.header_title_subtitle_lcolored, NativeListColorFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_list_base,R.color.md_red_800,R.string.header_title_subtitle_lbase, NativeListBaseFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_todo,R.color.md_deep_purple_500,R.string.header_title_cardheadersubtitle, NativeHeaderFragment.class));
        list.add(new MenuEntry(R.string.carddemo_title_tag_misc_cardwithlist,R.color.md_cyan_700,R.string.header_title_subtitle_cardwithlist, NativeCardWithListFragment.class));

        return list;
    }
}
