package com.dexterlimjiarong.painapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Settings extends Fragment implements OnClickListener{
	
	final static String PREFS_NAME = "MyPrefsFile";
	final static String ASSESSMENTS_JSONARRAY = "assessmentsJSONArray";
	final static String KEY_POST = "posts";
	final static String KEY_TITLE = "title";
	
	TextView mUpdateAssessments;
	
	WordPressApiFunctions wordPressApiFunctions = new WordPressApiFunctions();
	
    public Fragment_Settings(){
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_settings, container,
	              false);
		
		
		mUpdateAssessments   = (TextView) view.findViewById(R.id.updateAssessments);
		mUpdateAssessments.setOnClickListener(this); 
		
		return view;
	}
	
	@Override 
	  public void onClick(View view) { 
		switch (view.getId()) {
			case R.id.updateAssessments:{
				JSONObject json = null;
				JSONArray jsonArray = null;
				
				json = wordPressApiFunctions.getPosts();
				try{
    				if (json.getJSONArray(KEY_POST) != null) {
    					//gets all posts
    					jsonArray = json.getJSONArray(KEY_POST);
    					//store titles of posts in SharedPreferences
    					Context context = view.getContext();    					
    					SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, 0);
    					SharedPreferences.Editor editor = pref.edit();
    					editor.putString(ASSESSMENTS_JSONARRAY, jsonArray.toString());
    					editor.commit();
    					//updated assessments successfully
    					Toast.makeText(view.getContext(),"Updated Assessments successfully!", Toast.LENGTH_SHORT).show();
    				}
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
			}
		}
	  } 
    
	
	static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }
    
}
