package com.dexterlimjiarong.painapp;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPqasQ20 extends Fragment 
	implements OnClickListener
	{

	private SeekBar volumeControl = null;
	ImageView ivIcon;
    TextView tvItemName;

    
	  public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	  public static final String ITEM_NAME = "itemName";
	  public static final String STRING_ARRAY = "stringArray";
	  public static final String PREFS_NAME = "MyPrefsFile";
	  public static final String REPORT_SIZE = "reportSize";
	  public static final int QUESTION_TWENTY = 21;
      int radioButtonNumber = 0;

    public FragmentPqasQ20() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.fragment_layout_pqas_q20, container,
                      false);
        ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q20_icon);
        tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q20_text);
        //set question
        tvItemName.setText(R.string.pqas_q20);
        ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          
        //radio buttons
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup); 
	    //set up radio buttons to remember previous entry (if any)
	        if (getArguments().getStringArray(STRING_ARRAY) != null){
	      	  if (getArguments().getStringArray(STRING_ARRAY)[QUESTION_TWENTY] != null){
	      		radioButtonNumber = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[QUESTION_TWENTY]);
	      		  Toast.makeText(view.getContext(),"Refresh radiobutton:"+radioButtonNumber, 
	      					Toast.LENGTH_SHORT).show();
	      	  }
	        }
	        
        	switch(radioButtonNumber){
    		case 0:
    			radioGroup.check(R.id.radio0);
    			break;
    		case 1:
    			radioGroup.check(R.id.radio1);
    			break;
    		case 2:
    			radioGroup.check(R.id.radio2);
    			break;
    		default:
    			System.out.println("radiobutton1DEFAULTED");
    			break;
        	}
	        	
	        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	// finds which radiobutton is pressed and sets radiobuttonnumber
            	switch(checkedId){
            		case R.id.radio0:
            			radioButtonNumber = 0;
            			break;
            		case R.id.radio1:
            			radioButtonNumber = 1;
            			break;
            		case R.id.radio2:
            			radioButtonNumber = 2;
            			break;
            		default:
            			System.out.println("radiobutton2DEFAULTED");
            			break;
            	}
            }
        });
          
          //What happens when next button is pressed
          Button nextButton = (Button) view.findViewById(R.id.button_pqas_q20_save);
          nextButton.setOnClickListener(this);
        //What happens when back button is pressed
          Button backButton = (Button) view.findViewById(R.id.button_pqas_q20_back);
          backButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_pqas_q20_save:{
		        //sets up a POP-UP
		        AlertDialog.Builder alertDB = new AlertDialog.Builder(getActivity());
		        alertDB.setMessage("Do you really want to save this entry?");
		        alertDB.setCancelable(true);
		        alertDB.setPositiveButton("Confirm",
	                    new DialogInterface.OnClickListener() {
		        	@Override
	                public void onClick(DialogInterface dialogInterface, int id) {
	                    //process save
	    	        	Bundle bundle = getArguments();
	    	        	//Answers to the questions are stored in questionAnswers string array, retrieve previous bundle
	    	        	String[] questionAnswers = bundle.getStringArray(STRING_ARRAY);
	    	        	//initialize next fragment
	    	        	Fragment fragment = new FragmentPqasQ20();
	    		        //pain slider value stored in question 1 answer
	    		        questionAnswers[QUESTION_TWENTY] = Integer.toString(radioButtonNumber);
	    		        //string array is added to bundle
	    		        bundle.putStringArray(FragmentPqasQ20.STRING_ARRAY, questionAnswers);
	    		        bundle.putInt(FragmentPqasQ20.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
	    		        //set bundle to fragment
	    		        fragment.setArguments(bundle);
	    		        FragmentManager frgManager = getFragmentManager();
	    		        //replace fragment
	    		        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	    		        
		    		        //persist the data
		    		        Dialog dialog  = (Dialog) dialogInterface;
		    		        Context context = dialog.getContext();
		    		        //gets the size of the reports
		    		        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, 0);
		    		        int reportSizeInt = pref.getInt(REPORT_SIZE,0);
		    		        String reportSize = Integer.toString(reportSizeInt);
		    		        SharedPreferences.Editor editor = pref.edit();
		    		        //increase the size of the report by one, so that report fragment knows how many rows to print
		    		        editor.putInt(REPORT_SIZE,reportSizeInt+1);
		    		        editor.commit();
		    		        setStringArrayPref(context,"report"+reportSize, new ArrayList(Arrays.asList(questionAnswers)));
	    		        
	    		        dialogInterface.cancel();
	                }
	            });
		        alertDB.setNegativeButton("Back",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialogInterface, int id) {
	                    dialogInterface.cancel();
	                    //do nothing
	                }
	            });

	            AlertDialog confirmSave = alertDB.create();
	            confirmSave.show();
		        break;
	        }
	        case R.id.button_pqas_q20_back:{
            	Bundle args = getArguments();
    	        Fragment fragment = new FragmentPqasQ19();
    	        //set bundle to fragment
    	        fragment.setArguments(args);
    	        FragmentManager frgManager = getFragmentManager();
    	        //replace fragment
    	        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	        	break;
	        }
    	} 
    }
    
    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
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
