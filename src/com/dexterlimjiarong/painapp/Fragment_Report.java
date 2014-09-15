package com.dexterlimjiarong.painapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
 
public class Fragment_Report extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
	  public static final String PREFS_NAME = "MyPrefsFile";
	  public static final String REPORT_SIZE = "reportSize";
	  public static final String REPORT_TYPE = "reportType";
	  public static final String REPORT = "report";
 
      public Fragment_Report() {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view = inflater.inflate(R.layout.fragment_layout_report, container,
                        false);
            
            //adding some rows dynamically
            TableLayout tl = (TableLayout) view.findViewById(R.id.frag_report_table);
            SharedPreferences pref = view.getContext().getSharedPreferences(PREFS_NAME, 0);
	        int reportSize = pref.getInt(REPORT_SIZE,0);
	        //loop through the entire report size
            for(int i=0;i<reportSize;i++){
            	System.out.println("ENTERED LOOP");
	            /* Create a new row to be added. */
	            TableRow tr = new TableRow(view.getContext());
	            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
	            //Retrieve report data from persisted data stored in Shared Preference
	            ArrayList<String> questionAnswersList = getStringArrayPref(view.getContext(),REPORT+i);
	            //Convert ArrayList to String Array
	            String[] questionAnswers = new String[questionAnswersList.size()];
	            questionAnswers = questionAnswersList.toArray(questionAnswers);
	            String questionType = pref.getString(REPORT_TYPE+i, "");
	            TextView a = new TextView(view.getContext());
	            a.setText(questionType);
	            a.setPadding(10,10,30,10);
	            a.setGravity(Gravity.LEFT);
            	tr.addView(a);
	         	// create a new TextView and add it to the table row through the string array
	            for(String item:questionAnswers){
	            	a = new TextView(view.getContext());
	            	a.setText(item);
	            	//edit padding here to suit table's column titles
	            	a.setPadding(10,10,30,10);
	            	a.setGravity(Gravity.LEFT);
	            	tr.addView(a);
	            }
	            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
            
            return view;
      }
      
      public static ArrayList<String> getStringArrayPref(Context context, String key) {
    	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	    String json = prefs.getString(key, null);
    	    ArrayList<String> questionAnswers = new ArrayList<String>();
    	    if (json != null) {
    	        try {
    	            JSONArray a = new JSONArray(json);
    	            for (int i = 0; i < a.length(); i++) {
    	                String questionAnswer = a.optString(i);
    	                questionAnswers.add(questionAnswer);
    	            }
    	        } catch (JSONException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	    return questionAnswers;
      }
      
      
 
}