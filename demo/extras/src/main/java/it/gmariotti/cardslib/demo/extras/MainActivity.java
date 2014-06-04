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

package it.gmariotti.cardslib.demo.extras;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.gmariotti.cardslib.demo.extras.fragment.ActionbarpullFragment;
import it.gmariotti.cardslib.demo.extras.fragment.AnimateStaggeredGridFragment;
import it.gmariotti.cardslib.demo.extras.fragment.BaseFragment;
import it.gmariotti.cardslib.demo.extras.fragment.BaseStaggeredGridFragment;
import it.gmariotti.cardslib.demo.extras.fragment.CardWithListFragment;
import it.gmariotti.cardslib.demo.extras.fragment.CroutonFragment;
import it.gmariotti.cardslib.demo.extras.fragment.DragDropListFragment;
import it.gmariotti.cardslib.demo.extras.fragment.ExpandPicassoFragment;
import it.gmariotti.cardslib.demo.extras.fragment.IonFragment;
import it.gmariotti.cardslib.demo.extras.fragment.ListViewAnimationsFragment;
import it.gmariotti.cardslib.demo.extras.fragment.ListViewGridAnimationsFragment;
import it.gmariotti.cardslib.demo.extras.fragment.PicassoFragment;
import it.gmariotti.cardslib.demo.extras.fragment.StaggeredGridFragment;
import it.gmariotti.cardslib.demo.extras.fragment.StickyListHeadersFragment;
import it.gmariotti.cardslib.demo.extras.fragment.UniversalImageLoaderFragment;
import it.gmariotti.cardslib.demo.extras.iabutils.IabHelper;
import it.gmariotti.cardslib.demo.extras.iabutils.IabResult;
import it.gmariotti.cardslib.demo.extras.iabutils.IabUtil;


public class MainActivity extends Activity {

    private ListView mDrawerList;
    private DrawerLayout mDrawer;
    private CustomActionBarDrawerToggle mDrawerToggle;
    private int mCurrentTitle = R.string.app_name;
    private int mSelectedFragment;
    private BaseFragment mBaseFragment;


    private static String TAG = "MainActivity";
    private IabHelper mHelper;

    //Used in savedInstanceState
    private static String BUNDLE_SELECTEDFRAGMENT = "BDL_SELFRG";

    private static final int CASE_STD = 0;
    private static final int CASE_PICASSO = 0;
    private static final int CASE_ION = 1;
    private static final int CASE_UNILOADER = 2;
    private static final int CASE_ACTIONPULL = 3;
    private static final int CASE_LISTVIEWANIMATOR = 4;
    private static final int CASE_GRIDVIEWANIMATOR = 5;
    private static final int CASE_CROUTON = 6;
    private static final int CASE_STKHDR = 7;
    private static final int CASE_EXPANDINSIDE = 8;
    private static final int CASE_BASESTAG = 9;
    private static final int CASE_STAG = 10;
    private static final int CASE_ALL = 11;
    private static final int CASE_DRAGDROP = 12;
    private static final int CASE_WEATHER = 13;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SELECTEDFRAGMENT, mSelectedFragment);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_extras_activity_main);

        // The attacher should always be created in the Activity's onCreate
        //mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout_extras);

        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _initMenu();
        mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
        mDrawer.setDrawerListener(mDrawerToggle);

        // ---------------------------------------------------------------
        // ...
        String base64EncodedPublicKey = IabUtil.key;

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
            if (fragmentManager.findFragmentById(R.id.fragment_main_extras) == null)
                mBaseFragment = selectFragment(mSelectedFragment);
            //if (mBaseFragment==null)
            //    mBaseFragment = selectFragment(mSelectedFragment);
        } else {
            mBaseFragment = new PicassoFragment();
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
        inflater.inflate(R.menu.extras_main, menu);
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
                    R.string.app_name_extras,
                    mCurrentTitle);
        }

        @Override
        public void onDrawerClosed(View view) {
            getActionBar().setTitle(getString(mCurrentTitle));
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            getActionBar().setTitle(getString(R.string.app_name_extras));
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

            /*
            mDrawer.setDrawerListener(
                    new DrawerLayout.SimpleDrawerListener() {
                        @Override
                        public void onDrawerClosed(View drawerView) {
                            super.onDrawerClosed(drawerView);
                            if (mBaseFragment != null)
                                openFragment(mBaseFragment);
                        }
                    }
            );*/
            mDrawer.closeDrawer(mDrawerList);
            if (mBaseFragment != null)
                openFragment(mBaseFragment);

        }
    }


    private BaseFragment selectFragment(int position) {
        BaseFragment baseFragment = null;

        switch (position) {

            case CASE_PICASSO:
                baseFragment = new PicassoFragment();
                break;
            case CASE_ION:
                baseFragment = new IonFragment();
                break;
            case CASE_UNILOADER:
                baseFragment = new UniversalImageLoaderFragment();
                break;
            case CASE_ACTIONPULL:
                baseFragment = new ActionbarpullFragment();
                break;
            case CASE_LISTVIEWANIMATOR:
                baseFragment = new ListViewAnimationsFragment();
                break;
            case CASE_GRIDVIEWANIMATOR:
                baseFragment = new ListViewGridAnimationsFragment();
                break;
            case CASE_CROUTON:
                baseFragment = new CroutonFragment();
                break;
            case CASE_STKHDR:
                baseFragment = new StickyListHeadersFragment();
                break;
            case CASE_EXPANDINSIDE:
                baseFragment = new ExpandPicassoFragment();
                break;
            case CASE_BASESTAG:
                baseFragment = new BaseStaggeredGridFragment();
                break;
            case CASE_STAG:
                baseFragment = new StaggeredGridFragment();
                break;
            case CASE_ALL:
                baseFragment = new AnimateStaggeredGridFragment();
                break;
            case CASE_DRAGDROP:
                baseFragment = new DragDropListFragment();
                break;
            case CASE_WEATHER:
                baseFragment = new CardWithListFragment();
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
            Fragment prev = fm.findFragmentByTag("carddemo_dialog_extras");
            if (prev != null) {
                ft.remove(prev);
            }
            //ft.addToBackStack(null);

            dialogStandardFragment.show(ft, "carddemo_dialog_extras");
        }
    }

    private void openFragment(BaseFragment baseFragment) {
        if (baseFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.setCustomAnimations(R.animator.carddemo_fag_fade_in,R.animator.carddemo_frag_fade_out);

            fragmentTransaction.replace(R.id.fragment_main_extras, baseFragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            if (baseFragment.getTitleResourceId() > 0)
                mCurrentTitle = baseFragment.getTitleResourceId();
        }
    }


    public static final String[] options = {
            "Thumbnail with Picasso",
            "Thumbnail with Ion",
            "Thumbnail with Universal-Image-Loader",
            "ActionBar-PullToRefresh",
            "ListViewAnimations",
            "GridViewAnimations",
            "Crouton",
            "StickyListHeaders",
            "Expand inside",
            "Base StaggeredGrid",
            "StaggeredGrid",
            "AnimateStaggeredGrid",
            "Drag And Drop CardList",
            "Card and Weather List"
    };


    private void _initMenu() {
        mDrawerList = (ListView) findViewById(R.id.drawer_extras);

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
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    public IabHelper getHelper() {
        return mHelper;
    }
}

