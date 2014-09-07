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

public class FragmentPqasQ19 extends Fragment 
	implements OnClickListener
	{

	private SeekBar volumeControl1 = null;
	private SeekBar volumeControl2 = null;
	ImageView ivIcon;
    TextView tvItemName;

    
	  public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	  public static final String ITEM_NAME = "itemName";
	  public static final String STRING_ARRAY = "stringArray";
	  public static final int QUESTION_NINETEEN_PART1 = 19;
	  public static final int QUESTION_NINETEEN_PART2 = 20;
      int pain1 = 0;
      int pain2 = 0;

    public FragmentPqasQ19() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_pqas_q19, container,
                      false);
        ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q19_icon);
        tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q19_text);
        //set question
        tvItemName.setText(R.string.pqas_q19);
        ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          
          //SEEKBAR 1
          volumeControl1 = (SeekBar) view.findViewById(R.id.volume_bar1);
          //set up seekbar1 to remember previous entry (if any)
	          if (getArguments().getStringArray(STRING_ARRAY) != null){
	        	  if (getArguments().getStringArray(STRING_ARRAY)[QUESTION_NINETEEN_PART1] != null){
	        		  pain1 = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[QUESTION_NINETEEN_PART1]);
	        		  Toast.makeText(view.getContext(),"Refresh Pain Scale:"+pain1, 
	        					Toast.LENGTH_SHORT).show();
	        		  
	        	  }
	          }
	          volumeControl1.setProgress(pain1);
          volumeControl1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
   
  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
  				pain1 = progress;
  			}
   
  			public void onStartTrackingTouch(SeekBar seekBar) {
  				// TODO Auto-generated method stub
  			}
   
  			public void onStopTrackingTouch(SeekBar seekBar) {
  				Toast.makeText(seekBar.getContext(),"Pain Scale:"+pain1, 
			Toast.LENGTH_SHORT).show();
  			}
          });
          
	        //SEEKBAR 2
	          volumeControl2 = (SeekBar) view.findViewById(R.id.volume_bar2);
	          //set up seekbar1 to remember previous entry (if any)
		          if (getArguments().getStringArray(STRING_ARRAY) != null){
		        	  if (getArguments().getStringArray(STRING_ARRAY)[QUESTION_NINETEEN_PART2] != null){
		        		  pain2 = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[QUESTION_NINETEEN_PART2]);
		        		  Toast.makeText(view.getContext(),"Refresh Pain Scale:"+pain2, 
		        					Toast.LENGTH_SHORT).show();
		        		  
		        	  }
		          }
		          volumeControl2.setProgress(pain2);
	          volumeControl2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	   
	  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
	  				pain2 = progress;
	  			}
	   
	  			public void onStartTrackingTouch(SeekBar seekBar) {
	  				// TODO Auto-generated method stub
	  			}
	   
	  			public void onStopTrackingTouch(SeekBar seekBar) {
	  				Toast.makeText(seekBar.getContext(),"Pain Scale:"+pain2, 
				Toast.LENGTH_SHORT).show();
	  			}
	          });
          
          
          
          //What happens when next button is pressed
          Button nextButton = (Button) view.findViewById(R.id.button_pqas_q19_next);
          nextButton.setOnClickListener(this);
         //What happens when back button is pressed
          Button backButton = (Button) view.findViewById(R.id.button_pqas_q19_back);
          backButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_pqas_q19_next:{
	        	Bundle bundle = getArguments();
	        	//Answers to the questions are stored in questionAnswers string array, retrieve previous bundle
	        	String[] questionAnswers = bundle.getStringArray(STRING_ARRAY);
	        	//initialize next fragment
/*5!!*/		    Fragment fragment = new FragmentPqasQ20();
		        //pain slider value stored in question 19_part1 and part2 answer
		        questionAnswers[QUESTION_NINETEEN_PART1] = Integer.toString(pain1);
		        questionAnswers[QUESTION_NINETEEN_PART2] = Integer.toString(pain2);
		        //string array is added to bundle
		        bundle.putStringArray(FragmentPqasQ20.STRING_ARRAY, questionAnswers);
		        bundle.putInt(FragmentPqasQ20.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
		        //set bundle to fragment
		        fragment.setArguments(bundle);
		        FragmentManager frgManager = getFragmentManager();
		        //replace fragment
		        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		        break;
	        }
	        case R.id.button_pqas_q19_back:{
            	Bundle args = getArguments();
    	        Fragment fragment = new FragmentPqasQ18();
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
