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

package it.gmariotti.cardslib.demo;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.demo.fragment.BirthDayCardFragment;
import it.gmariotti.cardslib.demo.fragment.CardExpandFragment;
import it.gmariotti.cardslib.demo.fragment.CardFragment;
import it.gmariotti.cardslib.demo.fragment.CardWithListFragment;
import it.gmariotti.cardslib.demo.fragment.ChangeValueCardFragment;
import it.gmariotti.cardslib.demo.fragment.DismissAnimFragment;
import it.gmariotti.cardslib.demo.fragment.GPlayCardFragment;
import it.gmariotti.cardslib.demo.fragment.GridBaseFragment;
import it.gmariotti.cardslib.demo.fragment.GridCursorCardFragment;
import it.gmariotti.cardslib.demo.fragment.GridGplayCABFragment;
import it.gmariotti.cardslib.demo.fragment.GridGplayFragment;
import it.gmariotti.cardslib.demo.fragment.HeaderFragment;
import it.gmariotti.cardslib.demo.fragment.ListBaseFragment;
import it.gmariotti.cardslib.demo.fragment.ListColorFragment;
import it.gmariotti.cardslib.demo.fragment.ListCursorCardFragment;
import it.gmariotti.cardslib.demo.fragment.ListDifferentInnerBaseFragment;
import it.gmariotti.cardslib.demo.fragment.ListExpandCardFragment;
import it.gmariotti.cardslib.demo.fragment.ListGplayCardCABFragment;
import it.gmariotti.cardslib.demo.fragment.ListGplayCardFragment;
import it.gmariotti.cardslib.demo.fragment.ListGplayCursorCardCABFragment;
import it.gmariotti.cardslib.demo.fragment.ListGplayUndoCardFragment;
import it.gmariotti.cardslib.demo.fragment.MiscCardFragment;
import it.gmariotti.cardslib.demo.fragment.OverflowAnimFragment;
import it.gmariotti.cardslib.demo.fragment.ShadowFragment;
import it.gmariotti.cardslib.demo.fragment.StockCardFragment;
import it.gmariotti.cardslib.demo.fragment.ThumbnailFragment;
import it.gmariotti.cardslib.demo.iabutils.IabHelper;
import it.gmariotti.cardslib.demo.iabutils.IabResult;
import it.gmariotti.cardslib.demo.iabutils.IabUtil;

public class MainActivity extends Activity {

    private ListView mDrawerList;
    private DrawerLayout mDrawer;
    private CustomActionBarDrawerToggle mDrawerToggle;
    private int mCurrentTitle=R.string.app_name;
    private int mSelectedFragment;
    private BaseFragment mBaseFragment;

    protected ActionMode mActionMode;

    private IabHelper mHelper;

    private static String TAG= "MainActivity";

    //Used in savedInstanceState
    private static String BUNDLE_SELECTEDFRAGMENT = "BDL_SELFRG";

    private static final int CASE_STD = 0;
    private static final int CASE_HEADER = 0;
    private static final int CASE_SHADOW = 1;
    private static final int CASE_THUMBNAIL = 2;
    private static final int CASE_CARD = 3;
    private static final int CASE_CARD_EXPAND = 4;
    private static final int CASE_BIRTH = 5;
    private static final int CASE_GPLAY = 6;
    private static final int CASE_STOCK = 7;
    private static final int CASE_MISC = 8;
    private static final int CASE_CHG_VALUE = 9;
    private static final int CASE_LIST_BASE = 10;
    private static final int CASE_LIST_BASE_INNER = 11;
    private static final int CASE_LIST_EXPAND = 12;
    private static final int CASE_LIST_GPLAY = 13;
    private static final int CASE_LIST_GPLAY_UNDO = 14;
    private static final int CASE_GRID_BASE = 15;
    private static final int CASE_GRID_GPLAY = 16;
    private static final int CASE_LIST_COLORS = 17;
    private static final int CASE_CURSOR_LIST = 18;
    private static final int CASE_CURSOR_GRID = 19;
    private static final int CASE_LIST_GPLAY_CAB = 20;
    private static final int CASE_GRID_GPLAY_CAB = 21;
    private static final int CASE_CURSOR_LIST_GPLAY_CAB = 22;
    //private static final int CASE_EXPLIB = 23;
    private static final int CASE_CARDWITHLIST = 23;
    private static final int CASE_DISMISS_ANIM = 24;
    private static final int CASE_OVERFLOW_ANIM = 25;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SELECTEDFRAGMENT, mSelectedFragment);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _initMenu();
        mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
        mDrawer.setDrawerListener(mDrawerToggle);

        // ---------------------------------------------------------------
        // ...
        String base64EncodedPublicKey= IabUtil.key;

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // Hooray, IAB is fully set up!
                IabUtil.getInstance().retrieveData(mHelper);
            }
        });

        //-----------------------------------------------------------------
        //BaseFragment baseFragment = null;
        if (savedInstanceState != null) {
            mSelectedFragment = savedInstanceState.getInt(BUNDLE_SELECTEDFRAGMENT);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragmentManager.findFragmentById(R.id.fragment_main)==null)
                mBaseFragment = selectFragment(mSelectedFragment);
            //if (mBaseFragment==null)
            //    mBaseFragment = selectFragment(mSelectedFragment);
        } else {
            mBaseFragment = new HeaderFragment();
            openFragment(mBaseFragment);
        }

        //-----------------------------------------------------------------
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawer.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
		 * The action bar home/up should open or close the drawer.
		 * ActionBarDrawerToggle will take care of this.
		 */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {

            //About
            case R.id.menu_about:
                Utils.showAbout(this);
                return true;
            case R.id.menu_beer:
                IabUtil.showBeer(this, mHelper);
                return true;
            default:
                break;
        }


        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

        public CustomActionBarDrawerToggle(Activity mActivity, DrawerLayout mDrawerLayout) {
            super(
                    mActivity,
                    mDrawerLayout,
                    R.drawable.ic_navigation_drawer,
                    R.string.app_name,
                    mCurrentTitle);
        }

        @Override
        public void onDrawerClosed(View view) {
            getActionBar().setTitle(getString(mCurrentTitle));
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            getActionBar().setTitle(getString(R.string.app_name));
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            // Highlight the selected item, update the title, and close the drawer
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mBaseFragment = selectFragment(position);
            mSelectedFragment = position;

            mDrawer.closeDrawer(mDrawerList);
            if (mBaseFragment != null)
                openFragment(mBaseFragment);
        }
    }


    private BaseFragment selectFragment(int position) {
        BaseFragment baseFragment = null;

        switch (position) {

            case CASE_HEADER:
                baseFragment = new HeaderFragment();
                break;
            case CASE_SHADOW:
                baseFragment = new ShadowFragment();
                break;
            case CASE_THUMBNAIL:
                baseFragment = new ThumbnailFragment();
                break;
            case CASE_CARD:
                baseFragment = new CardFragment();
                break;
            case CASE_CARD_EXPAND:
                baseFragment = new CardExpandFragment();
                break;
            case CASE_BIRTH:
                baseFragment = new BirthDayCardFragment();
                break;
            case CASE_GPLAY:
                baseFragment = new GPlayCardFragment();
                break;
            case CASE_STOCK:
                baseFragment = new StockCardFragment();
                break;
            case CASE_MISC:
                baseFragment = new MiscCardFragment();
                break;
            case CASE_CHG_VALUE:
                baseFragment = new ChangeValueCardFragment();
                break;
            case CASE_LIST_BASE:
                baseFragment = new ListBaseFragment();
                break;
            case CASE_LIST_BASE_INNER:
                baseFragment = new ListDifferentInnerBaseFragment();
                break;
            case CASE_LIST_EXPAND:
                baseFragment = new ListExpandCardFragment();
                break;
            case CASE_LIST_GPLAY:
                baseFragment = new ListGplayCardFragment();
                break;
            case CASE_LIST_GPLAY_UNDO:
                baseFragment = new ListGplayUndoCardFragment();
                break;
            case CASE_GRID_BASE:
                baseFragment = new GridBaseFragment();
                break;
            case CASE_GRID_GPLAY:
                baseFragment = new GridGplayFragment();
                break;
            case CASE_LIST_COLORS:
                baseFragment = new ListColorFragment();
                break;
            case CASE_CURSOR_LIST:
                baseFragment = new ListCursorCardFragment();
                break;
            case CASE_CURSOR_GRID:
                baseFragment = new GridCursorCardFragment();
                break;
            case CASE_LIST_GPLAY_CAB:
                baseFragment = new ListGplayCardCABFragment();
                break;
            case CASE_GRID_GPLAY_CAB:
                baseFragment = new GridGplayCABFragment();
                break;
            case CASE_CURSOR_LIST_GPLAY_CAB:
                baseFragment = new ListGplayCursorCardCABFragment();
                break;
            //case CASE_EXPLIB:
            //    baseFragment = new ExpandableListCardFragment();
            //    break;
            case CASE_CARDWITHLIST:
                baseFragment = new CardWithListFragment();
                break;
            case CASE_DISMISS_ANIM:
                baseFragment = new DismissAnimFragment();
                break;
            case CASE_OVERFLOW_ANIM:
                baseFragment = new OverflowAnimFragment();
                break;
            default:
                break;
        }

        return baseFragment;
    }


    private void openDialogFragment(DialogFragment dialogStandardFragment) {
        if (dialogStandardFragment != null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment prev = fm.findFragmentByTag("carddemo_dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            //ft.addToBackStack(null);

            dialogStandardFragment.show(ft, "carddemo_dialog");
        }
    }

    private void openFragment(BaseFragment baseFragment) {
        if (baseFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_main, baseFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            if (baseFragment.getTitleResourceId()>0)
                mCurrentTitle = baseFragment.getTitleResourceId();

        }
    }


    public static final String[] options = {
            "CardHeader",
            "CardShadow",
            "CardThumbnail",
            "Card",
            "Card Expand",
            "Google Birthday",
            "Google Play",
            "Google Stock",
            "Misc",
            "Refresh Card",
            "List base",
            "List base with different Inner Layouts" ,
            "List and expandable card",
            "List Google Play",
            "List with swipe and undo",
            "Grid base",
            "Grid Google Play",
            "List colored cards",
            "List with Cursor",
            "Grid with Cursor",
            "List with MultiChoice",
            "Grid with MultiChoice",
            "List with Cursor and MultiChoice",
            //"ExpandableList",
            "Card with List",
            "Dismiss Animation (exp)",
            "Overflow Animation (exp)"
    };


    private void _initMenu() {
        mDrawerList = (ListView) findViewById(R.id.drawer);

        if (mDrawerList != null) {
            mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, options));

            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    public IabHelper getHelper() {
        return mHelper;
    }


}

