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

package it.gmariotti.cardslib.demo.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import it.gmariotti.cardslib.demo.R;
import it.gmariotti.cardslib.demo.fragment.BaseFragment;
import it.gmariotti.cardslib.demo.fragment.NativeDashFragment;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class DemoSingleTopicActivity extends BaseActivity {

    public static final String EXTRA_FRAGMENT_NAME = "topic_fragment_name";
    public static final String EXTRA_FRAGMENT_COLOR = "topic_fragment_color";

    private BaseFragment mFragment;

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_INFO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carddemo_activity_singletopic);

        if (getIntent().hasExtra(Intent.EXTRA_TITLE)) {
            setTitle(getIntent().getStringExtra(Intent.EXTRA_TITLE));
        }

        final String customTitle = getIntent().getStringExtra(Intent.EXTRA_TITLE);
        setTitle(customTitle != null ? customTitle : getTitle());

        if (savedInstanceState == null) {
            mFragment = getFragmentFromIntent();
            mFragment.setArguments(intentToFragmentArguments(getIntent()));
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment, "single_pane")
                    .commit();
        } else {
            mFragment = (BaseFragment) getFragmentManager().findFragmentByTag("single_pane");
        }

        //getLPreviewUtils().trySetActionBar();
    }

    private BaseFragment getFragmentFromIntent() {
        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra(EXTRA_FRAGMENT_NAME);
        return NativeDashFragment.MenyEntryUtils.openFragment(this,fragmentName);
    }


    @Override
    protected void onNavDrawerStateChanged(boolean isOpen, boolean isAnimating) {
        super.onNavDrawerStateChanged(isOpen, isAnimating);
        updateActionBarNavigation();
    }

    private void updateActionBarNavigation() {
        boolean show = !isNavDrawerOpen();
        if (getLPreviewUtils().shouldChangeActionBarForDrawer()) {
            ActionBar ab = getActionBar();
            ab.setDisplayShowTitleEnabled(show);
            ab.setDisplayUseLogoEnabled(!show);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
