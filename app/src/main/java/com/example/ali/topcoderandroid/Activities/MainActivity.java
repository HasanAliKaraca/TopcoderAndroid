package com.example.ali.topcoderandroid.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ali.topcoderandroid.Api.ChallengeType;
import com.example.ali.topcoderandroid.Api.TopcoderClient;
import com.example.ali.topcoderandroid.Helpers.LogHelper;
import com.example.ali.topcoderandroid.Models.ChallengeModel;
import com.example.ali.topcoderandroid.Models.DataInfoModel;
import com.example.ali.topcoderandroid.R;
import com.example.ali.topcoderandroid.ui.ChallengeRecyclerView;
import com.example.ali.topcoderandroid.ui.ChallengeRecyclerViewAdapter;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private CoordinatorLayout coordinatorLayout;
    private Context context = this;
    private ChallengeRecyclerView recyclerView;
    private ChallengeRecyclerViewAdapter challengeRecyclerViewAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ChallengeType preferredChallengeType;
    private SwipeRefreshLayout swipeRefreshLayout;
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        preferredChallengeType = ChallengeType.getPreferred(sharedPreferences);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);


        setNavigation(navigationView, preferredChallengeType);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setDrawerListener(drawerLayout);

        /*init list*/
        recyclerView = (ChallengeRecyclerView) this.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setEmptyView(findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);


        /*init pull to refresh*/
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        setSwipeRefresh(swipeRefreshLayout);

        fetchChallenges(preferredChallengeType);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

    }

    private void setSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchChallenges(preferredChallengeType);
            }
        });
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

                fetchChallenges(preferredChallengeType);
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

    public void fetchChallenges(ChallengeType challengeType) {
        //todo change if fragment to --> context = getActivity();
        //context = this;//getApplicationContext();

        TopcoderClient client = new TopcoderClient();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //ObjectMapper mapper = new ObjectMapper();

                try {

                    ArrayList<ChallengeModel> list = DataInfoModel.mapJsonToChallengeModelArray(response);

                    //String str = response.toString();
                    //DataInfoModel d = mapper.readValue(str,DataInfoModel.class);

                    //JSONArray data = response.getJSONArray("data");
                    //ChallengeModel[] info = mapper.readValue(data.toString(), ChallengeModel[].class);


                    UpdateChallengeListOnUI(list);

                } catch (Exception e) {

                    LogHelper.Log(e);
                    //Toast.makeText(MainActivity.this, "Server error occured when getting list.", Toast.LENGTH_LONG).show();
                    Snackbar.make(coordinatorLayout, "Server error occured when getting list.", Snackbar.LENGTH_LONG).show();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    if (error.networkResponse != null) {
                        String networkResponseStatusCode = String.valueOf(error.networkResponse.statusCode);
                        String errorMessage = "Network error: " + networkResponseStatusCode + ". Try again in a few sec";
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    LogHelper.Log(e);
                }

                LogHelper.Log(error);

                UpdateChallengeListOnUI(new ArrayList<ChallengeModel>());

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        client.getChallenges(context, challengeType, responseListener, errorListener);

    }

    private void UpdateChallengeListOnUI(ArrayList<ChallengeModel> list) {
        challengeRecyclerViewAdapter = new ChallengeRecyclerViewAdapter(recyclerView.getContext(), list);

        recyclerView.setAdapter(challengeRecyclerViewAdapter);

      /*  recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {

                if (childView != null) {
                    int childPosition = recyclerView.getChildAdapterPosition(childView);

                    ChallengeRecyclerViewAdapter adapter = (ChallengeRecyclerViewAdapter) recyclerView.getAdapter();
                    ChallengeModel item = adapter.get(childPosition);

                    Toast.makeText(MainActivity.this, item.getChallengeName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongPress(View childView, int position) {

                if (currentActionMode != null) {
                    return;
                }

                if (childView != null) {
                    int childPosition = recyclerView.getChildAdapterPosition(childView);

                    ChallengeRecyclerViewAdapter adapter = (ChallengeRecyclerViewAdapter) recyclerView.getAdapter();
                    selectedItem = adapter.get(childPosition);


                    //Toast.makeText(MainActivity.this, item.getChallengeName(), Toast.LENGTH_SHORT).show();
                }
                startSupportActionMode(modeCallBack);
                childView.setSelected(true);


            }
        }));*/


    }
}
