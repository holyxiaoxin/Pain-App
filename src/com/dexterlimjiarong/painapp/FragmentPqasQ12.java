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

public class FragmentPqasQ12 extends Fragment 
	implements OnClickListener
	{

	private SeekBar volumeControl = null;
	ImageView ivIcon;
    TextView tvItemName;

    
	  public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	  public static final String ITEM_NAME = "itemName";
	  public static final String STRING_ARRAY = "stringArray";
	  public static final int QUESTION_TWELVE = 11;
      int pain = 0;

    public FragmentPqasQ12() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_pqas_q12, container,
                      false);
        ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q12_icon);
        tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q12_text);
        //set question
        tvItemName.setText(R.string.pqas_q12);
        ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          
          
          volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          //set up seekbar to remember previous entry (if any)
	          if (getArguments().getStringArray(STRING_ARRAY) != null){
	        	  if (getArguments().getStringArray(STRING_ARRAY)[QUESTION_TWELVE] != null){
	        		  pain = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[QUESTION_TWELVE]);
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
          //What happens when next button is pressed
          Button nextButton = (Button) view.findViewById(R.id.button_pqas_q12_next);
          nextButton.setOnClickListener(this);
        //What happens when back button is pressed
          Button backButton = (Button) view.findViewById(R.id.button_pqas_q12_back);
          backButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_pqas_q12_next:{
	        	Bundle bundle = getArguments();
	        	//Answers to the questions are stored in questionAnswers string array, retrieve previous bundle
	        	String[] questionAnswers = bundle.getStringArray(STRING_ARRAY);
	        	//initialize next fragment
/*5!!*/		    Fragment fragment = new FragmentPqasQ13();
		        //pain slider value stored in question 1 answer
		        questionAnswers[QUESTION_TWELVE] = Integer.toString(pain);
		        //string array is added to bundle
		        bundle.putStringArray(FragmentPqasQ13.STRING_ARRAY, questionAnswers);
		        bundle.putInt(FragmentPqasQ13.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
		        //set bundle to fragment
		        fragment.setArguments(bundle);
		        FragmentManager frgManager = getFragmentManager();
		        //replace fragment
		        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		        break;
	        }
	        case R.id.button_pqas_q12_back:{
            	Bundle args = getArguments();
    	        Fragment fragment = new FragmentPqasQ11();
    	        //set bundle to fragment
    	        fragment.setArguments(args);
    	        FragmentManager frgManager = getFragmentManager();
    	        //replace fragment
    	        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	        	break;
	        }
    	} 
    }
    
    
}
