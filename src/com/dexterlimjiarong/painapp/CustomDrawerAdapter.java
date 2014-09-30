package com.dexterlimjiarong.painapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
 
public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
 
      Context context;
      List<DrawerItem> drawerItemList;
      int layoutResID;
      
      final static String PREFS_NAME = "MyPrefsFile";
      final static String ASSESSMENTS_JSONARRAY = "assessmentsJSONArray";
      final static String KEY_POST = "posts";
      final static String KEY_TITLE = "title";
      
      boolean isAssessmentsUpdated = false;
 
      public CustomDrawerAdapter(Context context, int layoutResourceID,
                  List<DrawerItem> listItems) {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.layoutResID = layoutResourceID;
 
      }
 
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
 
            DrawerItemHolder drawerHolder;
            View view = convertView;
            JSONArray jsonArray = null;
            JSONObject json = null;
            
            //setting up the list of assessments to be displayed by spinner
//            WordPressApiFunctions wordPressApiFunctions = new WordPressApiFunctions();
//            int postsSize=0;
//            JSONObject json = null;
//            JSONArray jsonArray = null;
//            
//            if (!isAssessmentsUpdated){
//            	json = wordPressApiFunctions.getPosts();
//            	try{
//    				if (json.getJSONArray(KEY_POST) != null) {
//    					//gets all posts
//    					jsonArray = json.getJSONArray(KEY_POST);
//    					postsSize = jsonArray.length();
//    				}
//    			} catch (JSONException e) {
//    				e.printStackTrace();
//    			}
//            	isAssessmentsUpdated = true;
//            }
            
            if(view!=null){
            	Context context = view.getContext();
    			SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, 0);
    			String jsonArrayString =  pref.getString(ASSESSMENTS_JSONARRAY, null);
    			if(jsonArrayString!=null){
    				try{
        				//converts string in SharedPreferences to JSONArray of posts
        				jsonArray = new JSONArray(jsonArrayString); 
        			}catch (JSONException e) {
          				e.printStackTrace();
          			}
    			}
            }
 
            if (view == null) {
                  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                  drawerHolder = new DrawerItemHolder();
 
                  view = inflater.inflate(layoutResID, parent, false);
                  drawerHolder.ItemName = (TextView) view
                              .findViewById(R.id.drawer_itemName);
                  drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
 
                  drawerHolder.spinner = (Spinner) view
                              .findViewById(R.id.drawerSpinner);
 
                  drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);
 
                  drawerHolder.headerLayout = (LinearLayout) view
                              .findViewById(R.id.headerLayout);
                  drawerHolder.itemLayout = (LinearLayout) view
                              .findViewById(R.id.itemLayout);
                  drawerHolder.spinnerLayout = (LinearLayout) view
                              .findViewById(R.id.spinnerLayout);
 
                  view.setTag(drawerHolder);
 
            } else {
                  drawerHolder = (DrawerItemHolder) view.getTag();
 
            }
 
            DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
 
            if (dItem.isSpinner()) {
                  drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);
 
                  List<SpinnerItem> userList = new ArrayList<SpinnerItem>();
                  
                  if(jsonArray!=null){
                	  for (int i=0;i<jsonArray.length();i++){ 
                    	  try{
              				if (jsonArray.getJSONObject(i) != null) {
              					//gets post at index
              					json = jsonArray.getJSONObject(i);
              					try{
                      				if (json.getString(KEY_TITLE) != null) {
                      					//gets title of post
                      					userList.add(new SpinnerItem(R.drawable.user1, json.getString(KEY_TITLE),
                                                "extra details"));
            	          			}
            	          		} catch (JSONException e) {
            	          				e.printStackTrace();
            	          		}
    	          			}
    	          		} catch (JSONException e) {
    	          				e.printStackTrace();
    	          		}
                      } 
                  }
                  
//                    userList.add(new SpinnerItem(R.drawable.user1, "Ahamed Ishak",
//                              "ishakgmail.com"));
// 
//                  userList.add(new SpinnerItem(R.drawable.user2, "Brain Jekob",
//                              "brain.jgmail.com"));
 
                  CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context,
                              R.layout.custom_spinner_item, userList);
 
                  drawerHolder.spinner.setAdapter(adapter);
 
                  drawerHolder.spinner
                              .setOnItemSelectedListener(new OnItemSelectedListener() {
 
                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0,
                                                View arg1, int arg2, long arg3) {
 
                                          //Toast.makeText(context, "User Changed",
                                          //            Toast.LENGTH_SHORT).show();
                                    }
 
                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                          // TODO Auto-generated method stub
 
                                    }
                              });
 
            } else if (dItem.getTitle() != null) {
                  drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.title.setText(dItem.getTitle());
 
            } else {
 
                  drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
                  drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
 
                  drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                              dItem.getImgResID()));
                  drawerHolder.ItemName.setText(dItem.getItemName());
 
            }
            return view;
      }
 
      private static class DrawerItemHolder {
            TextView ItemName, title;
            ImageView icon;
            LinearLayout headerLayout, itemLayout, spinnerLayout;
            Spinner spinner;
      }
}