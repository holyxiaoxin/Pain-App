package com.dexterlimjiarong.painapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment_List_Of_Assessments extends Fragment{

	final static String PREFS_NAME = "MyPrefsFile";
	final static String ASSESSMENTS_JSONARRAY = "assessmentsJSONArray";
	final static String KEY_POST = "posts";
    final static String KEY_TITLE = "title";
	JSONArray jsonArrayPosts = null;
    JSONObject jsonAssessment = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_list_of_assessments, container,
	              false);
        
		//retrieves the array of custom assessments stored in SharedPreferences 
		SharedPreferences pref = view.getContext().getSharedPreferences(PREFS_NAME, 0);
		String jsonArrayString =  pref.getString(ASSESSMENTS_JSONARRAY, null);
		if(jsonArrayString!=null){
			try{
				//converts string in SharedPreferences to JSONArray of posts
				jsonArrayPosts = new JSONArray(jsonArrayString); 
			}catch (JSONException e) {
					e.printStackTrace();
				}
		}
		
		String[] listOfCustomAssessmentsTitles = new String[jsonArrayPosts.length()];
		
		//populate listOfCustomAssessmentsTitles
        if(jsonArrayPosts!=null){
        	for (int i=0;i<jsonArrayPosts.length();i++){ 
          	  	try{
					if (jsonArrayPosts.getJSONObject(i) != null) {
						//gets post at index
						jsonAssessment = jsonArrayPosts.getJSONObject(i);
						if (jsonAssessment.getString(KEY_TITLE) != null) {
							//adds title of stringArray
							listOfCustomAssessmentsTitles[i]=jsonAssessment.getString(KEY_TITLE);
						}
					}
    			} catch (JSONException e) {
        				e.printStackTrace();
        		}
            } 
        }
		
        ArrayAdapter<String> codeLearnArrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, listOfCustomAssessmentsTitles);
        
        ListView lv = (ListView)view.findViewById(R.id.customAssessmentList);
        
        lv.setOnItemClickListener(new OnItemClickListener()
        {
           @Override
           public void onItemClick(AdapterView<?> adapter, View view, int position,
                 long id) 
           {
        	   JSONObject tempJSONAssessment =null;
        	   try{
        		   tempJSONAssessment = jsonArrayPosts.getJSONObject(position);
        	   }catch (JSONException e) {
						e.printStackTrace();
        	   }
        	   Context context = view.getContext();
        	   FragmentManager frgManager = ((Activity) context).getFragmentManager();
        	   Fragment_Assessment assessmentFragment = new Fragment_Assessment(tempJSONAssessment);
        	   Fragment spinnerFragment = assessmentFragment;
        	   frgManager.beginTransaction().replace(R.id.content_frame, spinnerFragment).commit();
		        
           }
        });
        
        
        lv.setAdapter(codeLearnArrayAdapter);
		
        return view;
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
	
	
}
