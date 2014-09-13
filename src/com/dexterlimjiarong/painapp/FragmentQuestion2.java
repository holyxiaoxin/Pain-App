//package com.dexterlimjiarong.painapp;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
// 
//public class FragmentPqasQ2   extends Fragment {
// 
//      ImageView ivIcon;
//      TextView tvItemName;
// 
//      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
//      public static final String ITEM_NAME = "itemName";
//      public static final String STRING_ARRAY = "stringArray";
// 
//      public FragmentPqasQ2(){
//      }
// 
//      @Override
//      public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                  Bundle savedInstanceState) {
//            View view=inflater.inflate(R.layout.fragment_layout_pqas_q2,container, false);
//            //retrieve bundle from previous fragment
//            Bundle bundle = getArguments();
//            ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q2_icon);
//            tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q2_text);
//            //pain scale from previous
//            tvItemName.setText(bundle.getStringArray(STRING_ARRAY)[0]);
//            ivIcon.setImageDrawable(view.getResources().getDrawable(
//            		bundle.getInt(IMAGE_RESOURCE_ID)));
//            return view;
//      }
// 
//}

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

public class FragmentQuestion2 extends Fragment 
	implements OnClickListener
	{

	private SeekBar volumeControl = null;
	private int pain = 0;
	//this is the specific to the unique questions
	private int questionNumber = 0;
	private String question = null;
	private int questionType = 0;
	private int numberOfAnswers = 0;
	ImageView ivIcon;
    TextView tvItemName;

    
	  public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	  public static final String ITEM_NAME = "itemName";
	  public static final String STRING_ARRAY = "stringArray";
	  //types of questions
	    public static final int TYPE_SLIDER = 0;
	    public static final int TYPE_RADIO = 1;

    public FragmentQuestion2(int questionNumber, String question, int questionType, int numberOfAnswers) {
    	this.questionNumber = questionNumber;
    	this.question = question;
    	this.questionType = questionType;
    	this.numberOfAnswers = numberOfAnswers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_question_2, container,
                      false);
        ivIcon=(ImageView)view.findViewById(R.id.frag_question_2_image);
        tvItemName=(TextView)view.findViewById(R.id.frag_question_2_text);
        //set question
        tvItemName.setText(question);
        ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          
          
          volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          //set up seekbar to remember previous entry (if any)
	          if (getArguments().getStringArray(STRING_ARRAY) != null){
	        	  if (getArguments().getStringArray(STRING_ARRAY)[questionNumber] != null){
	        		  pain = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[questionNumber]);
	        		  Toast.makeText(view.getContext(),"Refresh Pain Scale:"+getArguments().getStringArray(STRING_ARRAY)[questionNumber], 
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
          Button nextButton = (Button) view.findViewById(R.id.button_question_next);
          nextButton.setOnClickListener(this);
        //What happens when back button is pressed
          Button backButton = (Button) view.findViewById(R.id.button_question_back);
          backButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_question_next:{
	        	Bundle bundle = getArguments();
	        	//Answers to the questions are stored in questionAnswers string array, retrieve previous bundle
	        	String[] questionAnswers = bundle.getStringArray(STRING_ARRAY);
	        	//initialize next fragment
		        Fragment fragment = new FragmentPqasQ3();
		        //pain slider value stored in question 1 answer
		        questionAnswers[questionNumber] = Integer.toString(pain);
		        //string array is added to bundle
		        bundle.putStringArray(FragmentPqasQ3.STRING_ARRAY, questionAnswers);
		        bundle.putInt(FragmentPqasQ3.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
		        //set bundle to fragment
		        fragment.setArguments(bundle);
		        FragmentManager frgManager = getFragmentManager();
		        //replace fragment
		        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		        break;
	        }
	        case R.id.button_question_back:{
            	Bundle args = getArguments();
    	        Fragment fragment = new FragmentPqasQ1();
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
