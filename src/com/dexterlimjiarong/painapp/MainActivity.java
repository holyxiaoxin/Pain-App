package com.dexterlimjiarong.painapp;
 
import java.util.ArrayList;
import java.util.List;
 
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
 
public class MainActivity extends Activity {
 
      private DrawerLayout mDrawerLayout;
      private ListView mDrawerList;
      private ActionBarDrawerToggle mDrawerToggle;
 
      private CharSequence mDrawerTitle;
      private CharSequence mTitle;
      CustomDrawerAdapter adapter;
 
      List<DrawerItem> dataList;
 
      @Override
      protected void onCreate(Bundle savedInstanceState) {
    	  	//extending the existing parent class' method
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
 
            // Initializing
            dataList = new ArrayList<DrawerItem>();
            mTitle = mDrawerTitle = getTitle();
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);
 
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                        GravityCompat.START);
 
         //There must always be a drawerItem Header for every drawerItem
         // Add Drawer Item to dataList
 
            //Username Header
            dataList.add(new DrawerItem("Username"));
            dataList.add(new DrawerItem("Home", R.drawable.ic_action_email));	//1. Home
            
            //Questionaire Header
            dataList.add(new DrawerItem("Questionaire")); // adding a header to the list
            dataList.add(new DrawerItem("PQAS", R.drawable.ic_action_labels));	//3. PQAS
            //Spinner Future Implementation will allow user to choose which questionnaires to do
            dataList.add(new DrawerItem(true)); // adding a spinner to the list
            	//dataList.add(new DrawerItem("Likes", R.drawable.ic_action_good));
            	//dataList.add(new DrawerItem("Games", R.drawable.ic_action_gamepad));
            	//dataList.add(new DrawerItem("Lables", R.drawable.ic_action_labels));
 
            //History
            dataList.add(new DrawerItem("History"));
            dataList.add(new DrawerItem("View Reports", R.drawable.ic_action_search));	//6. View Reports
            	//dataList.add(new DrawerItem("Cloud", R.drawable.ic_action_cloud));
            	//dataList.add(new DrawerItem("Camara", R.drawable.ic_action_camera));
            	//dataList.add(new DrawerItem("Video", R.drawable.ic_action_video));
            	//dataList.add(new DrawerItem("Groups", R.drawable.ic_action_group));
            	//dataList.add(new DrawerItem("Import & Export", R.drawable.ic_action_import_export));
 
            //Pain Assessment
            dataList.add(new DrawerItem("Pain Assessment"));
            dataList.add(new DrawerItem("About", R.drawable.ic_action_about));	//8. Settings
            dataList.add(new DrawerItem("Settings", R.drawable.ic_action_settings));	//9. Quit
            	//dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));
 
            adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                        dataList);
 
            mDrawerList.setAdapter(adapter);
 
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
 
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                        R.drawable.ic_drawer, R.string.drawer_open,
                        R.string.drawer_close) {
                  public void onDrawerClosed(View view) {
                        getActionBar().setTitle(mTitle);
                        invalidateOptionsMenu(); // creates call to
                                                                  // onPrepareOptionsMenu()
                  }
 
                  public void onDrawerOpened(View drawerView) {
                        getActionBar().setTitle(mDrawerTitle);
                        invalidateOptionsMenu(); // creates call to
                                                                  // onPrepareOptionsMenu()
                  }
            };
 
            mDrawerLayout.setDrawerListener(mDrawerToggle);
 
            if (savedInstanceState == null) {
            	 
                if (dataList.get(0).isSpinner()
                            & dataList.get(1).getTitle() != null) {
                      SelectItem(2);
                } else if (dataList.get(0).getTitle() != null) {
                      SelectItem(1);
                } else {
                      SelectItem(0);
                }
          }
 
      }
 
      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
      }
 
      
      //You need to update this according to the “dataList” item position. 
      //Fragment should be only need to change when we click in a selectable 
      //item not in header or spinner.  So change the case statements to point 
      //correct selectable item. (Be careful you may get runtime errors if you 
      //update incorrectly.) I delete the  0,1 and 6 case statement and 
      //add new case statement for 14,15 and 16 positions .
      public void SelectItem(int position) {
    	  
          Fragment fragment = null;
          Bundle args = new Bundle();
          switch (position) {
     
        case 1:	//Home
        	//since it did not fetch previous bundle, all data will be lost once the home navigation button is pressed
        	//should remember questionnaires entries? 
              fragment = new FragmentOne();
              args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
                          .getItemName());
              args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList
                          .get(position).getImgResID());
              break;
        case 3:	//PQAS
            fragment = new FragmentPqasQ1();
            args.putString(FragmentPqasQ1.ITEM_NAME, dataList.get(position)
                        .getItemName());
            args.putInt(FragmentPqasQ1.IMAGE_RESOURCE_ID, dataList
                        .get(position).getImgResID());
            break;
            
        case 6:
            fragment = new FragmentTwo();
            args.putString(FragmentTwo.ITEM_NAME, dataList.get(position)
                        .getItemName());
            args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList
                        .get(position).getImgResID());
            break;
        case 8:
            fragment = new FragmentTwo();
            args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
                        .getItemName());
            args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList
                        .get(position).getImgResID());
            break;
        
          default:
                break;
          }
     
          fragment.setArguments(args);
          FragmentManager frgManager = getFragmentManager();
          frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                      .commit();
     
          mDrawerList.setItemChecked(position, true);
          setTitle(dataList.get(position).getItemName());
          mDrawerLayout.closeDrawer(mDrawerList);
    }
 
      @Override
      public void setTitle(CharSequence title) {
            mTitle = title;
            getActionBar().setTitle(mTitle);
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
            // Pass any configuration change to the drawer toggles
            mDrawerToggle.onConfigurationChanged(newConfig);
      }
 
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
            // The action bar home/up action should open or close the drawer.
            // ActionBarDrawerToggle will take care of this.
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                  return true;
            }
 
            return false;
      }
 
        private class DrawerItemClickListener implements
                  ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                        long id) {
            	if (dataList.get(position).getTitle() == null) {
                    SelectItem(position);
              }
 
            }
      }
 
}