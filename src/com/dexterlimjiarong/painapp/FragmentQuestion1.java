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

public class FragmentQuestion1 extends Fragment implements OnClickListener{

	private SeekBar volumeControl = null;
	private int pain = 0;
	//Setting up the questionnaire bundles requires this attributes
	//This is only required for the initial fragment (i.e. FragmentQuestion1.java)
	private String questionnaireType = null;
	private int answerSize = 0;
	//this is the specific to the unique questions
	private int questionNumber = 0;
	private String question = null;
	ImageView ivIcon;
    TextView tvItemName;
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    //this is the name of the key for the bundle
    public static final String STRING_ARRAY = "stringArray";    
    public static final int QUESTIONNAIRE_TYPE = 0;
    //types of questions
    public static final int TYPE_SLIDER = 0;
    public static final int TYPE_RADIO = 1;
    
    //number of answers
    public static final int ONE_ANSWER = 1;
    public static final int TWO_ANSWER = 2;
    
    //different questions:
    public static final String PQAS_QUESTION_2 = "Please use the scale below to tell us how SHARP your pain has felt over the past week. Words used to describe sharp feelings include \"like a knife\" \"like a spike\" \"piercing\"";
    
    public FragmentQuestion1(String questionType, int answerSize, int questionNumber, String question) {
    	this.questionnaireType = questionType;
    	this.answerSize = answerSize;
    	this.questionNumber = questionNumber;
    	this.question = question;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_question_1, container,
                      false);

          ivIcon = (ImageView) view.findViewById(R.id.frag_question_1_image);
          tvItemName = (TextView) view.findViewById(R.id.frag_question_1_text);

          ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          tvItemName.setText(question);
          
          volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          //set up seekbar to remember previous entry (if any)
	          if (getArguments().getStringArray(STRING_ARRAY) != null){
	        	  if (getArguments().getStringArray(STRING_ARRAY)[questionNumber] != null){
	        		  pain = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[questionNumber]);
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
			
          Button nextButton = (Button) view.findViewById(R.id.button_question_next);
          nextButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
	        case R.id.button_question_next:{
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
	        		questionAnswers = new String[answerSize+1];
	        		questionAnswers[QUESTIONNAIRE_TYPE] = questionnaireType;
	        	}
	        	//initialize next fragment
		        //Fragment fragment = new FragmentPqasQ2();
	        	//questionNumber, question, questionType = Slider, numberOfAnswers = 1
		        Fragment fragment = new FragmentQuestion2(questionNumber+1, PQAS_QUESTION_2, TYPE_SLIDER, ONE_ANSWER);
		        //pain slider value stored in question 1 answer
		        questionAnswers[questionNumber] = Integer.toString(pain);
		        //string array is added to bundle
		        bundle.putStringArray(STRING_ARRAY, questionAnswers);
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
