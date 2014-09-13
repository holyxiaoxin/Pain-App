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

public class FragmentQuestion extends Fragment implements OnClickListener{

	private SeekBar volumeControl = null;
	private int pain = 0;
	/**
	 * Setting up the questionnaire requires these attributes
	 * This will not change
	 */
	private String questionnaireType = null;	//eg. PQAS
	private String questionsType[] = null;	//eg. slider, radio, checkbox, etc..  
	private String[][] questions = null;	//this is all the questions for the questionnaire. Each question number can contain several questions (eg. some questions are needed for radiobuttons, checkboxes, etc).
	private int answerSize = 0;	//the number of different answers there are from user inputs
	private int fragmentSize = 0;	//the total number of questions. eg. Q1, Q2, Q3 ..... (i.e. the number of pages/fragments generated for the entire questionnaire)
	/**
	 * This will change depending on the specific questions
	 */
	private int currentQuestionNumber = 0;
	private String currentQuestion = null;
	ImageView ivIcon;
    TextView tvItemName;    
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    //this is the name of the key for the bundle
    public static final String STRING_ARRAY = "stringArray";    
    public static final int QUESTIONNAIRE_TYPE = 0;
    
    //number of answers
    public static final int ONE_ANSWER = 1;
    public static final int TWO_ANSWER = 2;
    
    //constructor
    public FragmentQuestion(String questionnaireType, String[] questionsType, String[][] questions, int answerSize) {
    	this.questionnaireType = questionnaireType;
    	this.questionsType = questionsType;
    	this.questions = questions;
    	this.answerSize = answerSize;
    	this.fragmentSize = questions.length;
    	
    	//sets up currentQuestion
    	if (fragmentSize!=0){
    		currentQuestion = questions[0][0];	//gets first question
    	}
    	//sets up currentQuestionNumber
    	if (fragmentSize!=0){
    		currentQuestionNumber = 1;
    	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_question, container,
                      false);

          ivIcon = (ImageView) view.findViewById(R.id.frag_question_image);
          tvItemName = (TextView) view.findViewById(R.id.frag_question_text);

          ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
          tvItemName.setText(currentQuestion);
          
          volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          //set up seekbar to remember previous entry (if any)
	          if (getArguments().getStringArray(STRING_ARRAY) != null){
	        	  if (getArguments().getStringArray(STRING_ARRAY)[currentQuestionNumber] != null){
	        		  pain = Integer.parseInt(getArguments().getStringArray(STRING_ARRAY)[currentQuestionNumber]);
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
			
          Button nextButton = (Button) view.findViewById(R.id.button_question_next_save);
          nextButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
    		/**
    		 * What happens when the next button is pressed
    		 */
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
	        		questionAnswers = new String[answerSize];
	        			//dont set questionnaire type here: 
	        			//questionAnswers[0] should not contain questionnaireType
	        			//a seperate key for the sharedPeference to store the questionnaireType will be used instead
	        		//questionAnswers[QUESTIONNAIRE_TYPE] = questionnaireType;
	        	}
	        	/**
	        	 * Update current fragment
	        	 */
	        	//questionNumber, question, questionType = Slider, numberOfAnswers = 1
		        Fragment fragment = this;
		        //pain slider value stored in question 1 answer
		        questionAnswers[currentQuestionNumber-1] = Integer.toString(pain);
		        //string array is edited/added to bundle
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
