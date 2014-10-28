package com.dexterlimjiarong.painapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;
 
public class Fragment_Report extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
	  static final String PREFS_NAME = "MyPrefsFile";
	  static final String REPORT_SIZE = "reportSize";
	  static final String REPORT_TITLE = "reportTitle";
	  static final String REPORT_DATETIME = "reportDateTime";
	  static final String REPORT_FREE_TEXT_VAS = "reportFreeTextVAS";
	  static final String REPORT = "report";
 
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
	            //gets Assessment Title
	            String assessmentTitle = pref.getString(REPORT_TITLE+i, "");
	            //gets Assessment DateTime
	            String assessmentDateTime = pref.getString(REPORT_DATETIME+i, "");
	            
	            //This are the rows
	            TextView a = new TextView(view.getContext());
	            a.setText(assessmentTitle);
	            a.setPadding(10,10,30,10);
	            a.setGravity(Gravity.LEFT);
            	tr.addView(a);
            	a = new TextView(view.getContext());
            	a.setText(assessmentDateTime);
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
	            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
            
            
            Button exportButton = (Button) view.findViewById(R.id.export_csv_button);
            exportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	
                	File file = new File("/sdcard/assessmentReport.csv");
            		if(file.exists()){
            			new AlertDialog.Builder(v.getContext())
            		    .setTitle("Existing report found")
            		    .setMessage("Do you want to overwrite existing report?")
            		    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            		        public void onClick(DialogInterface dialog, int which) { 
            		            // continue with export
            		        	exportToCSV();
            		        }
            		     })
            		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            		        public void onClick(DialogInterface dialog, int which) { 
            		            // do nothing
            		        }
            		     })
            		    .setIcon(android.R.drawable.ic_dialog_alert)
            		     .show();
            		} 
            		else{
            			exportToCSV();
            		}
                }
            });
            
            Button switchViewButton = (Button) view.findViewById(R.id.switch_report_view_button);
            switchViewButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//update the view by replacing the fragment
			        FragmentManager frgManager = getFragmentManager();
			        frgManager.beginTransaction().replace(R.id.content_frame, new Fragment_Report_List()).commit();
				}
            	
            	
            });
            
            
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
      
      private void exportToCSV(){
    	  CSVWriter writer = null;
    	  Context context = this.getView().getContext();
      	try 
      	{
      		writer = new CSVWriter(new FileWriter("/sdcard/assessmentReport.csv"), ',');
      	    //adds the header of the csv file
      	    String[] title = {"Title"};
      	    String[] vasFreeText = {"VAS additional comments"};
      	    String[] emptyRow = {""};
      	    writer.writeNext(title);
      	    
      	    //writes the report into csv file
      	    SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, 0);
  	        int reportSize = pref.getInt(REPORT_SIZE,0);
      	    for(int i=0;i<reportSize;i++){
      	    	//Retrieve report data from persisted data stored in Shared Preference
  	            ArrayList<String> questionAnswersList = getStringArrayPref(context,REPORT+i);
  	            //Convert ArrayList to String Array
  	            String[] questionAnswers = new String[questionAnswersList.size()];
  	            questionAnswers = questionAnswersList.toArray(questionAnswers);
  	            String assessmentTitle = pref.getString(REPORT_TITLE+i, "");
  	          	String assessmentDateTime = pref.getString(REPORT_DATETIME+i, "");
  	            //creates a new row that contains all the answers to each assessment
  	            String[] nextRow = new String[questionAnswers.length+1];
  	            //sets Assessment Title as the first column of the next row
  	            nextRow[0] = assessmentTitle;
  	            //sets Assessment DateTime as the second column of the next row
  	          	nextRow[0] = assessmentDateTime;
  	            for(int j=0;j<questionAnswers.length;j++){
  	            	nextRow[j+1]=questionAnswers[j];
  	            }    	
  	            writer.writeNext(nextRow);
      	    }
      	    
      	    /**
      	     * ONLY FOR VAS
      	     */
      	    writer.writeNext(emptyRow);
      	    writer.writeNext(vasFreeText);
      	    writer.writeNext(title);
      	    for(int i=0;i<reportSize;i++){
      	    	String assessmentTitle = pref.getString(REPORT_TITLE+i, "");
      	    	String assessmentDateTime = pref.getString(REPORT_DATETIME+i, "");
      	    	if(assessmentTitle.equals("VAS")){
      	    		//Retrieve freetextvas data from persisted data stored in Shared Preference
      	            ArrayList<String> freeTextVASList = getStringArrayPref(context,REPORT_FREE_TEXT_VAS+i);
      	            //Convert ArrayList to String Array
      	            String[] freeTextVAS = new String[freeTextVASList.size()];
      	            freeTextVAS = freeTextVASList.toArray(freeTextVAS);
      	            //creates a new row that contains all the response to freetextvas
      	            String[] nextRow = new String[freeTextVAS.length+1];
      	            //sets Assessment Title as the first column of the next row
      	            nextRow[0] = assessmentTitle;
      	        	//sets Assessment DateTime as the second column of the next row
      	          	nextRow[0] = assessmentDateTime;
      	            for(int j=0;j<freeTextVAS.length;j++){
      	            	nextRow[j+1]=freeTextVAS[j];
      	            }    	
      	            writer.writeNext(nextRow);
      	    	}
      	    }
      	    
      	    
      	    
      	    writer.close();
      	    Toast.makeText(context,"Report successfully exported to CSV", 
						Toast.LENGTH_SHORT).show();
      	} 
      	catch (IOException e)
      	{
      	    //error
      		Toast.makeText(context,"Export has failed", 
						Toast.LENGTH_SHORT).show();
      		
      	}
      } 
 
}