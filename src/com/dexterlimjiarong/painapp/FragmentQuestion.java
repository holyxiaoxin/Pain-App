package com.dexterlimjiarong.painapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
	private int painSliderValue = 0;
	/**
	 * Setting up the questionnaire requires these attributes
	 * This will not change
	 */
	private String questionnaireType = null;	//eg. PQAS
	private String[] questionsType = null;	//eg. slider, radio, checkbox, etc..  
	private String[][] questions = null;	//this is all the questions for the questionnaire. Each question number can contain several questions (eg. some questions are needed for radiobuttons, checkboxes, etc).
	private String[] sliderTitles = null; //this is the slider title for different questions (if the question has a slider in the first place)
	private int answerSize = 0;	//the number of different answers there are from user inputs
	private int fragmentSize = 0;	//the total number of questions. eg. Q1, Q2, Q3 ..... (i.e. the number of pages/fragments generated for the entire questionnaire)
	/**
	 * This will change depending on the specific questions
	 */
	private int currentQuestionNumber = 0;
	private String currentQuestion = null;
	
	/**
	 * This stores all the answers
	 */
	//For now, the questionsAnswers stores answers for the static questionnaire: PQAS with the following questions below
	//answers to questions 1-21 are string[0] - string[20]
	private String[] questionAnswers = null;
	
	ImageView ivIcon;
    TextView tvItemName;    
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    
    //constructor
    public FragmentQuestion(String questionnaireType, String[] questionsType, String[][] questions, String[] sliderTitles, int answerSize) {
    	this.questionnaireType = questionnaireType;
    	this.questionsType = questionsType;
    	this.questions = questions;
    	this.sliderTitles = sliderTitles;
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
    	//sets up questionAnswers
    	if (answerSize!=0){
    		questionAnswers = new String[answerSize];
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
		  
		  /**
		   * Cater the questionnaires view based on the questionType and questionNumber
		   */
			 //remove every type of inputs before adding the inputs base on the questionType
			 View seekBarView = view.findViewById(R.id.slider_view);
			 TextView seekBarTitleView = (TextView) view.findViewById(R.id.frag_slider_text);
			 seekBarView.setVisibility(View.GONE);
			 
			 System.out.println("Question Number: "+currentQuestionNumber);
			 System.out.println("Slider Title: "+ sliderTitles[currentQuestionNumber-1]);
			 switch (questionsType[currentQuestionNumber-1]){
			 	case "slider":
					seekBarView.setVisibility(View.VISIBLE);
					seekBarTitleView.setText(sliderTitles[currentQuestionNumber-1]);
					painSliderValue = 0; //resets pain slider value
					System.out.println(painSliderValue);
					break;
				case "radio":
					break;
				case "checkbox":
					break;
				default:
					//do nothing
				 	break;
			 }
			 
			 //remove every type of buttons
			 View backButtonView = view.findViewById(R.id.button_question_back);
			 
			 Button nextSaveButton = (Button) view.findViewById(R.id.button_question_next_save);
			 Button backButton = (Button) view.findViewById(R.id.button_question_back);
			 
			 if (currentQuestionNumber==1){	//first question does not have a back button
				 backButtonView.setVisibility(View.GONE);
				 if(currentQuestionNumber==fragmentSize){	//if the first question is also the last question
					 nextSaveButton.setText("Save");	//sets the next button to save instead
				 }else{	//else there are more questions to come, therefore "next"
					 nextSaveButton.setText("Next");
				 }
			 }else if(currentQuestionNumber==fragmentSize){
				 backButtonView.setVisibility(View.VISIBLE);
				 nextSaveButton.setText("Save");
			 }else{	//middle questions
				 backButtonView.setVisibility(View.VISIBLE);
				 nextSaveButton.setText("Next");
			 }
		  /**
		   * Programs the SeekBar to remember the value of the slider
		   */
		  volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
		  //set up seekbar to remember previous entry (if any)
		  if (currentQuestionNumber!=fragmentSize){ //last question do not have a previous entry from next question
			  if (questionAnswers[currentQuestionNumber] != null){ //this is actually the next question's answer
				  System.out.println(painSliderValue);
				  painSliderValue = Integer.parseInt(questionAnswers[currentQuestionNumber]);
				  Toast.makeText(view.getContext(),"Refresh Pain Scale:"+painSliderValue, 
							Toast.LENGTH_SHORT).show();
				  
			  }
		  }
		  //this is not workinggggg???
		  //System.out.println("painSliderValue:"+painSliderValue);
		  volumeControl.setProgress(painSliderValue);
		  //System.out.println("Progress: "+ volumeControl.getProgress());
		  
          volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
   
  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
  				painSliderValue = progress;
  			}
   
  			public void onStartTrackingTouch(SeekBar seekBar) {
  				// TODO Auto-generated method stub
  			}
   
  			public void onStopTrackingTouch(SeekBar seekBar) {
  				Toast.makeText(seekBar.getContext(),"Pain Scale:"+painSliderValue, 
			Toast.LENGTH_SHORT).show();
  			}
          });
          /**
           * Set listeners for the different buttons
           */
          //nextSaveButton = (Button) view.findViewById(R.id.button_question_next_save);
          backButton.setOnClickListener(this);
          nextSaveButton.setOnClickListener(this);

          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
    		/**
    		 * What happens when the next button is pressed
    		 */
	    	case R.id.button_question_back:{
	    		/**
	        	 * Update current fragment
	        	 */
	    		if(currentQuestionNumber!=1){	//first question does not have a back button
	    			currentQuestionNumber--;
			        currentQuestion = questions[currentQuestionNumber-1][0];
			      //update the view by detach and attach fragment
			        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
			        fragTransaction.detach(this);
			        fragTransaction.attach(this);
			        fragTransaction.commit();
	    		}
	        	break;
	        }
	        case R.id.button_question_next_save:{
	        	/**
	        	 * Update current fragment
	        	 */
		        questionAnswers[currentQuestionNumber-1] = Integer.toString(painSliderValue);	//save painSliderValue
		        //FragmentManager frgManager = getFragmentManager();
		        if(currentQuestionNumber!=fragmentSize){	//if not the last question update variables to next question in questionnaire
		        	currentQuestionNumber++;
			        currentQuestion = questions[currentQuestionNumber-1][0];
			        //update the view by detach and attach fragment
			        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
			        fragTransaction.detach(this);
			        fragTransaction.attach(this);
			        fragTransaction.commit();
		        }else{	//this is the last question, save it to the report
		        	Toast.makeText(view.getContext(),"Report:"+questionAnswers[0]+questionAnswers[1]+questionAnswers[2]+questionAnswers[3]+questionAnswers[4]+questionAnswers[5], 
							Toast.LENGTH_SHORT).show();
		        }
		        
		        break;
	        }
    	} 

    }
    
    
}
