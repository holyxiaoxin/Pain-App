package com.dexterlimjiarong.painapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FragmentReport extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
 
      public FragmentReport() {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view = inflater.inflate(R.layout.fragment_layout_report, container,
                        false);
 
            ivIcon = (ImageView) view.findViewById(R.id.frag_report_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag_report_text);
 
            ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
            //tvItemName.setText(R.string.home_text1);
            
            
            return view;
      }
      
      public static ArrayList<String> getStringArrayPref(Context context, String key) {
    	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	    String json = prefs.getString(key, null);
    	    ArrayList<String> urls = new ArrayList<String>();
    	    if (json != null) {
    	        try {
    	            JSONArray a = new JSONArray(json);
    	            for (int i = 0; i < a.length(); i++) {
    	                String url = a.optString(i);
    	                urls.add(url);
    	            }
    	        } catch (JSONException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	    return urls;
      }
      
      
 
}