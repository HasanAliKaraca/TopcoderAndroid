/*
* Copyright (C) 2015 Hasan Ali Karaca - http://www.hasanalikaraca.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.ali.topcoderandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ali.topcoderandroid.Api.ChallengeType;
import com.example.ali.topcoderandroid.Models.ChallengeModel;
import com.example.ali.topcoderandroid.R;
import com.example.ali.topcoderandroid.ui.RecyclerViewFragment;


public class MainActivity extends AppCompatActivity {

    ChallengeType preferredChallengeType;
    RecyclerViewFragment recyclerViewFragment;
    private CoordinatorLayout coordinatorLayout;
    private Context context = this;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ShareActionProvider mShareActionProvider;
    // Tracks current contextual action mode
    private ActionMode currentActionMode;
    private ChallengeModel selectedItem;
    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Actions");
            mode.getMenuInflater().inflate(R.menu.menu_list_item_context, menu);
            return true;
        }

        // Called each time the action mode is shown.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_share:
                    if (selectedItem != null) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = selectedItem.getChallengeName();
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check this challenge:");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }


                    //Toast.makeText(MainActivity.this, "Editing!", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the contextual menu
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            currentActionMode = null; // Clear current action mode
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This will make our app properly initialized with default settings when first loaded.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setDrawerListener(drawerLayout);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            recyclerViewFragment = new RecyclerViewFragment();
            transaction.replace(R.id.recyclerview_fragment_container, recyclerViewFragment);
            transaction.commit();
        }

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        preferredChallengeType = ChallengeType.getPreferred(context);
        setNavigation(navigationView, preferredChallengeType);

    }


    private void setDrawerListener(DrawerLayout drawerLayout) {

        //new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    private void setNavigation(NavigationView navigationView, ChallengeType challengeType) {
        int preferredCheckItemId = 0;
        switch (challengeType) {
            case Design:
                preferredCheckItemId = 0;
                break;
            case Develop:
                preferredCheckItemId = 1;
                break;
            case Data:
                preferredCheckItemId = 2;
                break;
        }

        Menu menu = navigationView.getMenu();
        if (menu != null)
            menu.getItem(preferredCheckItemId).setChecked(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (!menuItem.isChecked()) {
                    menuItem.setChecked(true);
                }

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.navDesign:
                        //Toast.makeText(getApplicationContext(), "Design Challenges", Toast.LENGTH_SHORT).show();
                        preferredChallengeType = ChallengeType.Design;
                        break;

                    case R.id.navDevelop:
                        //Toast.makeText(getApplicationContext(), "Develop Challenges", Toast.LENGTH_SHORT).show();
                        preferredChallengeType = ChallengeType.Develop;
                        break;

                    case R.id.navData:
                        //Toast.makeText(getApplicationContext(), "Data Challenges", Toast.LENGTH_SHORT).show();
                        preferredChallengeType = ChallengeType.Data;
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }

                recyclerViewFragment.fetchChallenges(preferredChallengeType);
                return true;

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch reference to the share action provider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;

    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
           /* case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;*/
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

}
