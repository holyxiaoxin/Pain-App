package com.dexterlimjiarong.painapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPqasQ1 extends Fragment implements OnClickListener{

	private SeekBar volumeControl = null;
	ImageView ivIcon;
    TextView tvItemName;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    public static final String STRING_ARRAY = "stringArray";    
    public static final int QUESTION_TYPE = 0;
    public static final int QUESTION_ONE = 1;
    int pain = 0;

    public FragmentPqasQ1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_pqas_q1, container,
                      false);

          ivIcon = (ImageView) view.findViewById(R.id.frag_pqas_q1_icon);
          tvItemName = (TextView) view.findViewById(R.id.frag_pqas_q1_text);

          ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          tvItemName.setText(R.string.pqas_q1);
          
          volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          //set up seekbar to remember previous entry (if any)
	          if (getArguments().getStringArray(STRING_ARRAY) != null){
	        	  if (getArguments().getStringArray(STRING_ARRAY)[QUESTION_ONE] != null){
	        		  pain = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[QUESTION_ONE]);
	        		  Toast.makeText(view.getContext(),"Refresh Pain Scale:"+pain, 
	        					Toast.LENGTH_SHORT).show();
	        		  
	        	  }
	          }
	          volumeControl.setProgress(pain);
          
          volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
   
  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
  				pain = progress;
  			}
   
  			public void onStartTrackingTouch(SeekBar seekBar) {
  				// TODO Auto-generated method stub
  			}
   
  			public void onStopTrackingTouch(SeekBar seekBar) {
  				Toast.makeText(seekBar.getContext(),"Pain Scale:"+pain, 
			Toast.LENGTH_SHORT).show();
  			}
          });
			
          Button nextButton = (Button) view.findViewById(R.id.button_pqas_q1_next);
          nextButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_pqas_q1_next:{
	        	Bundle bundle = getArguments();
	        	//Answers to the questions are stored in questionAnswers string array
	        	String[] questionAnswers = bundle.getStringArray(STRING_ARRAY);
	        	//check if there is a bundle from previous fragment, eg: coming from back button of fragment 2
	        	if(questionAnswers == null){
	        		bundle = new Bundle();
	        		//questionnaire type is string[0]
	        		//answers to questions 1-18 are string[1] - string[18]
	        		//answer to question 19 part 1 is string[19]
	        		//answer to question 19 part 2 is string [20]
	        		//answer to question 20 is string[21]
	        		questionAnswers = new String[22];
	        		questionAnswers[QUESTION_TYPE] = "PQAS";
	        	}
	        	//initialize next fragment
		        Fragment fragment = new FragmentPqasQ2();
		        //pain slider value stored in question 1 answer
		        questionAnswers[QUESTION_ONE] = Integer.toString(pain);
		        //string array is added to bundle
		        bundle.putStringArray(FragmentPqasQ2.STRING_ARRAY, questionAnswers);
		        //set bundle to fragment
		        fragment.setArguments(bundle);
		        FragmentManager frgManager = getFragmentManager();
		        //replace fragment
		        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		        break;
	        }
    	} 

    }
    
    
}
