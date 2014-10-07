package com.dexterlimjiarong.painapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
 
      static Context context;
      static List<DrawerItem> drawerItemList;
      static int layoutResID;
      
      final static String PREFS_NAME = "MyPrefsFile";
      final static String ASSESSMENTS_JSONARRAY = "assessmentsJSONArray";
      final static String KEY_POST = "posts";
      final static String KEY_TITLE = "title";
      
      boolean isAssessmentsUpdated = false;
 
      public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
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
            
//            if(view!=null){
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
//            }
 
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
                  
                  //draws spinner based on WordPress posts
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
 
                  CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, R.layout.custom_spinner_item, userList);
                  drawerHolder.spinner.setAdapter(adapter);
                  
//                  drawerHolder.spinner.setSelection(position, false);
                  
                  final JSONArray tempJSONArray = jsonArray;
                  drawerHolder.spinner.setOnItemSelectedListener(new OnItemSelectedListenerWrapper(new OnItemSelectedListener() {
		                @Override
		                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		                		
		                		int indexOfSpinner = position;
		                		final JSONObject tempJSONObject = getTempJSONObject(tempJSONArray, position);
		                		
			                	Toast.makeText(context, "IS SPINNER: "+ indexOfSpinner, Toast.LENGTH_SHORT).show();
			                	Context context = view.getContext();
			                	FragmentManager frgManager = ((Activity) context).getFragmentManager();
			                	Fragment_Assessment assessmentFragment = new Fragment_Assessment(tempJSONObject);
			                	Fragment spinnerFragment = assessmentFragment;
						        frgManager.beginTransaction().replace(R.id.content_frame, spinnerFragment).commit();
						        
						        MainActivity mainActivity  = new MainActivity();
						        mainActivity.mDrawerLayout.closeDrawer(mainActivity.mDrawerList);
						        
					            Activity activity = (Activity) context;
					            activity.setTitle(assessmentFragment.getAssessmentTitle());
		                }
		 
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                              // TODO Auto-generated method stub
                        }
                  }));
 
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
      
      private JSONObject getTempJSONObject(JSONArray jsonArray, int position){
    	  JSONObject json =null;
    	  try{
    		  json = jsonArray.getJSONObject(position);
	  		}catch (JSONException e) {
					e.printStackTrace();
	    	}
    	  return json;
    	  
      }
      
      public void updateView(){
    	  notifyDataSetChanged();
      }
      
      //prevents newly instantiated spinner from firing onItemSelected
      public class OnItemSelectedListenerWrapper implements OnItemSelectedListener {

    	    private int lastPosition;
    	    private OnItemSelectedListener listener;

    	    public OnItemSelectedListenerWrapper(OnItemSelectedListener aListener) {
    	        lastPosition = 0;
    	        listener = aListener;
    	    }

    	    @Override
    	    public void onItemSelected(AdapterView<?> aParentView, View aView, int aPosition, long anId) {
    	        if (lastPosition == aPosition) {
    	            Log.d(getClass().getName(), "Ignoring onItemSelected for same position: " + aPosition);
    	        } else {
    	            Log.d(getClass().getName(), "Passing on onItemSelected for different position: " + aPosition);
    	            listener.onItemSelected(aParentView, aView, aPosition, anId);
    	        }
    	        lastPosition = aPosition;
    	    }

    	    @Override
    	    public void onNothingSelected(AdapterView<?> aParentView) {
    	        listener.onNothingSelected(aParentView);
    	    }
    	}
      
}