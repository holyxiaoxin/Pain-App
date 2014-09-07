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
import android.widget.RadioButton;
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
	  public static final int QUESTION_TWENTY = 20;
      int pain = -1;	//stores the radio button identifier

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
	      		  pain = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[QUESTION_TWENTY]);
	      		  Toast.makeText(view.getContext(),"Refresh radiobutton:"+pain, 
	      					Toast.LENGTH_SHORT).show();
	      	  }
	        }
	        if (pain == -1){
	        	radioGroup.check(R.id.radio0);
	        }else{
	        	radioGroup.check(pain);
	        }
//	        switch(pain){
//	        	case 0:{
//	        		radioGroup.check(R.id.radio0);
//	        		//RadioButton rb1 = (RadioButton) view.findViewById(R.id.radio0);
//		        	//rb1.setChecked(true);
//	        		break;
//	        		}
//	        	case 1:{
//	        		radioGroup.check(R.id.radio1);
//	        		//RadioButton rb2 = (RadioButton) view.findViewById(R.id.radio1);
//		        	//rb2.setChecked(true);
//	        		break;
//	        	}
//	        	case 2:{
//	        		radioGroup.check(R.id.radio2);
//	        		//RadioButton rb3 = (RadioButton) view.findViewById(R.id.radio2);
//		        	//rb3.setChecked(true);
//	        		break;
//	        	}
//	        	default:{
//	        		radioGroup.check(R.id.radio0);
//	        		//RadioButton rb1 = (RadioButton) view.findViewById(R.id.radio0);
//		        	//rb1.setChecked(true);
//	        		break;
//	        	}
//	        }
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            	pain = checkedId;
            }
        });
          
        
          //What happens when next button is pressed
          Button nextButton = (Button) view.findViewById(R.id.button_pqas_q20_next);
          nextButton.setOnClickListener(this);
        //What happens when back button is pressed
          Button backButton = (Button) view.findViewById(R.id.button_pqas_q20_back);
          backButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_pqas_q20_next:{
	        	Bundle bundle = getArguments();
	        	//Answers to the questions are stored in questionAnswers string array, retrieve previous bundle
	        	String[] questionAnswers = bundle.getStringArray(STRING_ARRAY);
	        	//initialize next fragment
/*5!!*/		    Fragment fragment = new FragmentPqasQ20();
		        //pain slider value stored in question 1 answer
		        questionAnswers[QUESTION_TWENTY] = Integer.toString(pain);
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
    
    
}
