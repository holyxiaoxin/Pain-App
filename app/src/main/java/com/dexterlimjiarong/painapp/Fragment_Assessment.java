package com.dexterlimjiarong.painapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Assessment extends Fragment implements OnClickListener{

	int painSliderValue = 0;
	int radioButtonValue = 0;
	/**
	 * Setting up the assessment requires these attributes
	 * This will not change
	 */
	static String assessmentTitle = null;	//eg. PQAS
	static String[] questionType = null;	//eg. slider, radio, checkbox, etc.. 
	static String[] questions = null;	//this are all the questions for the assessment.
	static String[][] options = null;	//this are all the options for the assessment. Eg, for question 1, the options are stored in options[0][], option 1 is in options[0][0] and so on.
	static String[] maxValues = null;	//this are all the maxValues of sliders, can be empty if the question is not a slider
	static String[] sliderTitles = null; //this are all the slider titles for different questions (if the question has a slider in the first place)
	static String[] colors = null; //this are all the color for the scales
	static int questionsSize = 0;	//the total number of questions. eg. Q1, Q2, Q3 ..... (i.e. the number of pages/fragments generated for the entire assessment)
	
	/**
	 * This will change depending on the specific questions
	 */
	private static int currentQuestionNumber = 0;
	private static String currentQuestion = null;
	
	/**
	 * This stores all the answers
	 */
	//For now, the questionsAnswers stores answers for the static assessment: PQAS with the following questions below
	//answers to questions 1-21 are string[0] - string[20]
	static String[] response = null;
	
	/**
	 * ONLY FOR VAS
	 */
	static String[] freeTextVAS = null;
	
	
	ImageView ivIcon;
    TextView tvItemName;    
    
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    
	static final String PREFS_NAME = "MyPrefsFile";
	static final String REPORT_SIZE = "reportSize";
	static final String REPORT_MAX_QUESTION = "reportMaxQuestion";
	static final String REPORT_TITLE = "reportTitle";
	static final String REPORT_DATETIME = "reportDateTime";
	static final String REPORT_FREE_TEXT_VAS = "reportFreeTextVAS";
	static final String REPORT = "report";
    static final String TYPE_SLIDER = "slider";
    static final String TYPE_RADIO = "radio";
    static final String TYPE_CHECKBOX = "checkbox";
    static final int NUMBER_OF_RADIO_IDS_AVAILABLE = 10;
    static final int QUESTION_INDEX = 0;
    static final int DEFAULT_INT_ZERO = 0;

    //json keys
    static final String KEY_TITLE = "title";
    static final String KEY_QUESTIONS = "questions";
    static final String KEY_QUESTION = "question";
    static final String KEY_OPTIONS = "options";
    static final String KEY_OPTION = "option";
    static final String KEY_MAX_VALUE = "maxValue";
    static final String KEY_QUESTION_TYPE = "questionType";
    static final String KEY_NUMBER_OF_OPTIONS = "numberOfOptions";
    static final String KEY_COLOR = "color";
    
    //constructor for newly created questions
    public Fragment_Assessment(JSONObject jsonAssessment) {
    	JSONArray jsonArrayQuestions = null;
    	JSONArray jsonArrayOptions = null;
    	JSONObject jsonQuestion = null;
    	JSONObject jsonOption = null;
    	
    	/**
    	 * ONLY FOR VAS
    	 */
    	freeTextVAS = new String[2];
    	
    	try{
    		jsonArrayQuestions = jsonAssessment.getJSONArray(KEY_QUESTIONS);
    		assessmentTitle = jsonAssessment.getString(KEY_TITLE);
    		questionsSize = jsonArrayQuestions.length();
    		//initialise questions
    		questions = new String[questionsSize];
    		//initialise options
    		options = new String[questionsSize][];
    		//initialise questionsType
    		questionType = new String[questionsSize];
    		//intialise maxValues
    		maxValues = new String[questionsSize];
    		//initialise slider titles
    		sliderTitles = new String[questionsSize];
    		//have not code for slider titles, set as Slider as default
    		for(int i=0;i<questionsSize;i++){
    			sliderTitles[i]="Slider";
    		}
    		//initialise colors
    		colors = new String[questionsSize];
    		
    		//intialise questions
    		for(int i=0;i<jsonArrayQuestions.length();i++){
    			jsonQuestion = jsonArrayQuestions.getJSONObject(i);
				Log.d("frag_question", jsonQuestion.toString());
				
				questionType[i] = jsonQuestion.getString(KEY_QUESTION_TYPE);
				Log.d("frag_questionType", questionType[i]);
				
				switch(questionType[i]){
					case TYPE_SLIDER:
						//initialise question
						questions[i] = jsonQuestion.getString(KEY_QUESTION);
						//fill maxValue
						maxValues[i] = jsonQuestion.getString(KEY_MAX_VALUE);
						//fill colors
						colors[i] = jsonQuestion.getString(KEY_COLOR);
						break;
					case TYPE_RADIO:
						//intialise question
						jsonArrayOptions = jsonQuestion.getJSONArray(KEY_OPTIONS);
						int numberOfOptions = jsonArrayOptions.length();
						options[i] = new String[numberOfOptions];
						questions[i] = jsonQuestion.getString(KEY_QUESTION);
						//initialising number of options
						for(int j=0;j<numberOfOptions;j++){
							jsonOption = jsonArrayOptions.getJSONObject(j);
							options[i][j] = jsonOption.getString(KEY_OPTION);
						}
						//fill colors
						colors[i] = jsonQuestion.getString(KEY_COLOR);
						Log.d("questions array inside radio switch: ", Arrays.deepToString(questions));
						break;
				}
    		}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("Assessment not in proper format.");
		}
    	
    	//sets up currentQuestion
    	if (questionsSize!=0){
    		currentQuestion = questions[0];	//sets up first question
    		currentQuestionNumber = 1;	//sets up question number
    		response = new String[questionsSize];	//sets up response sheet
    	}
    }
    
    //constructor for existing questions
    public Fragment_Assessment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

		  View view = inflater.inflate(R.layout.fragment_layout_assessment, container,
		              false);
		
		  ivIcon = (ImageView) view.findViewById(R.id.frag_question_image);
		  tvItemName = (TextView) view.findViewById(R.id.frag_question_text);
		
		  ivIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_action_labels));
		  tvItemName.setText(currentQuestion);
		  
		  /**
		   * Cater the assessments view based on the question,questionType,questionNumber
		   */
			 //remove every type of inputs before adding the inputs base on the questionType
			 View seekBarView = view.findViewById(R.id.slider_view);
			 seekBarView.setVisibility(View.GONE);
			 
			 View radioGroupView = view.findViewById(R.id.radioGroup);
			 RadioGroup radioGroup = (RadioGroup) radioGroupView;
			 radioGroupView = view.findViewById(R.id.radio_view);
			 radioGroupView.setVisibility(View.GONE);
			 
//			 Log.d("debug questionType Array: ",Arrays.toString(questionType));
//			 Log.d("debug questionType currentQuestionNumber: ",Integer.toString(currentQuestionNumber));
			 
			 switch (questionType[currentQuestionNumber-1]){
			 	case "slider":
					seekBarView.setVisibility(View.VISIBLE);
					
					painSliderValue = 0; //resets pain slider value
					break;
				case "radio":
					radioGroupView.setVisibility(View.VISIBLE);
					for (int i=0; i< radioGroup.getChildCount(); i++){
						radioGroup.removeViewAt(i);
				    }
					radioButtonValue = 0;
					
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
			 Button backButton = (Button) backButtonView;
			 
			 if (currentQuestionNumber==1){	//first question does not have a back button
				 backButtonView.setVisibility(View.GONE);
				 if(currentQuestionNumber==questionsSize){	//if the first question is also the last question
					 nextSaveButton.setText("Save");	//sets the next button to save instead
				 }else{	//else there are more questions to come, therefore "next"
					 nextSaveButton.setText("Next");
				 }
			 }else if(currentQuestionNumber==questionsSize){
				 backButtonView.setVisibility(View.VISIBLE);
				 nextSaveButton.setText("Save");
			 }else{	//middle questions
				 backButtonView.setVisibility(View.VISIBLE);
				 nextSaveButton.setText("Next");
			 }
		  
			 switch (questionType[currentQuestionNumber-1]){
				 case TYPE_SLIDER:{
					 /**
					   * SEEKBAR
					   */
					 	//sets seekbar title
					 	TextView seekBarTitleView = (TextView) view.findViewById(R.id.frag_slider_text);
					 	seekBarTitleView.setText(sliderTitles[currentQuestionNumber-1]);
					 	//sets seekbar color
					 	Log.d("color",colors[currentQuestionNumber-1]);
					 	switch(colors[currentQuestionNumber-1]){
						 	case "grey":
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_grey));
						 		break;
						 	case "pink":
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_pink));
						 		break;
                            case "red":
                                seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_red));
                                break;
						 	case "orange":
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_orange));
						 		break;
						 	case "green":
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_green));
						 		break;
						 	case "blue":
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_blue));
						 		break;
						 	case "yellow":
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_yellow));
						 		break;
						 	default:
						 		seekBarView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_grey));
						 		break;
					 	}
					 	SeekBar seekBar = (SeekBar) view.findViewById(R.id.volume_bar);
						  //set up seekbar to remember previous entry (if any)
						  if (response[currentQuestionNumber-1] != null){
							  System.out.println(painSliderValue);
							  painSliderValue = Integer.parseInt(response[currentQuestionNumber-1]);
                              //debug purposes
//							  Toast.makeText(view.getContext(),"Refresh Pain Scale:"+painSliderValue,
//										Toast.LENGTH_SHORT).show();
						  }
						  seekBar.setProgress(painSliderValue);
					//sets max for slider if it is not defaulted
					if(maxValues[currentQuestionNumber-1]!="" && !maxValues[currentQuestionNumber-1].isEmpty()){
						int maxValue = Integer.parseInt(maxValues[currentQuestionNumber-1]);
						seekBar.setMax(maxValue);
						if(maxValue>=5){//multiples of 5 or >5
							//hides l2 and show l1
							LinearLayout l1 = (LinearLayout) view.findViewById(R.id.seekbar_mul_5);
							LinearLayout l2 = (LinearLayout) view.findViewById(R.id.seekbar_mul_3);
							l1.setVisibility(View.VISIBLE);
							l2.setVisibility(View.GONE);
							//changes the labels of steps until max value shown in the seekbar slider
							TextView seekbarLabel1 = (TextView) view.findViewById(R.id.seekbar_label_typeA_1);
							TextView seekbarLabel2 = (TextView) view.findViewById(R.id.seekbar_label_typeA_2);
							TextView seekbarLabel3 = (TextView) view.findViewById(R.id.seekbar_label_typeA_3);
							TextView seekbarLabel4 = (TextView) view.findViewById(R.id.seekbar_label_typeA_4);
							TextView seekbarLabel5 = (TextView) view.findViewById(R.id.seekbar_label_typeA_5);
							seekbarLabel1.setText(Integer.toString((maxValue)*1/5));
							seekbarLabel2.setText(Integer.toString((maxValue)*2/5));
							seekbarLabel3.setText(Integer.toString((maxValue)*3/5));
							seekbarLabel4.setText(Integer.toString((maxValue)*4/5));
							seekbarLabel5.setText(Integer.toString((maxValue)*5/5));
						}else if(maxValue==3){//multiples of 3 or 3
							//hides l1 and show l2
							LinearLayout l1 = (LinearLayout) view.findViewById(R.id.seekbar_mul_5);
							LinearLayout l2 = (LinearLayout) view.findViewById(R.id.seekbar_mul_3);
							l1.setVisibility(View.GONE);
							l2.setVisibility(View.VISIBLE);
							//changes the labels of steps until max value shown in the seekbar slider
							TextView seekbarLabel1 = (TextView) view.findViewById(R.id.seekbar_label_typeB_1);
							TextView seekbarLabel2 = (TextView) view.findViewById(R.id.seekbar_label_typeB_2);
							TextView seekbarLabel3 = (TextView) view.findViewById(R.id.seekbar_label_typeB_3);
							seekbarLabel1.setText(Integer.toString((maxValue)*1/3));
							seekbarLabel2.setText(Integer.toString((maxValue)*2/3));
							seekbarLabel3.setText(Integer.toString((maxValue)*3/3));
						}else{
							//hides l2 and show l1
							LinearLayout l1 = (LinearLayout) view.findViewById(R.id.seekbar_mul_5);
							LinearLayout l2 = (LinearLayout) view.findViewById(R.id.seekbar_mul_3);
							l1.setVisibility(View.VISIBLE);
							l2.setVisibility(View.GONE);
							//changes the labels of steps until max value shown in the seekbar slider
							TextView seekbarLabel1 = (TextView) view.findViewById(R.id.seekbar_label_typeA_1);
							TextView seekbarLabel2 = (TextView) view.findViewById(R.id.seekbar_label_typeA_2);
							TextView seekbarLabel3 = (TextView) view.findViewById(R.id.seekbar_label_typeA_3);
							TextView seekbarLabel4 = (TextView) view.findViewById(R.id.seekbar_label_typeA_4);
							TextView seekbarLabel5 = (TextView) view.findViewById(R.id.seekbar_label_typeA_5);
							seekbarLabel1.setText("");
							seekbarLabel2.setText("");
							seekbarLabel3.setText("");
							seekbarLabel4.setText("");
							seekbarLabel5.setText(Integer.toString(maxValue));
						}
					}else{//default
						seekBar.setMax(10);
						//hides l2 and show l1
						LinearLayout l1 = (LinearLayout) view.findViewById(R.id.seekbar_mul_5);
						LinearLayout l2 = (LinearLayout) view.findViewById(R.id.seekbar_mul_3);
						l1.setVisibility(View.VISIBLE);
						l2.setVisibility(View.GONE);
						//changes the labels of steps until max value shown in the seekbar slider to default
						TextView seekbarLabel1 = (TextView) view.findViewById(R.id.seekbar_label_typeA_1);
						TextView seekbarLabel2 = (TextView) view.findViewById(R.id.seekbar_label_typeA_2);
						TextView seekbarLabel3 = (TextView) view.findViewById(R.id.seekbar_label_typeA_3);
						TextView seekbarLabel4 = (TextView) view.findViewById(R.id.seekbar_label_typeA_4);
						TextView seekbarLabel5 = (TextView) view.findViewById(R.id.seekbar_label_typeA_5);
						seekbarLabel1.setText("2");
						seekbarLabel2.setText("4");
						seekbarLabel3.setText("6");
						seekbarLabel4.setText("8");
						seekbarLabel5.setText("10");
					}
					seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
			  				painSliderValue = progress;
			  			}
			  			public void onStartTrackingTouch(SeekBar seekBar) {
			  				// TODO Auto-generated method stub
			  			}
			  			public void onStopTrackingTouch(SeekBar seekBar) {
//					  				Toast.makeText(seekBar.getContext(),"Pain Scale:"+painSliderValue,
//					  				Toast.LENGTH_SHORT).show();
						}
					});
					break;
				 }
				 case TYPE_RADIO:{
					 /**
			           * RADIO BUTTONS
			           */
					//sets radio color
					 	switch(colors[currentQuestionNumber-1]){
						 	case "grey":
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_grey));
						 		break;
						 	case "pink":
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_pink));
						 		break;
                            case "red":
                                radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_red));
                                break;
						 	case "orange":
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_orange));
						 		break;
						 	case "green":
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_green));
						 		break;
						 	case "blue":
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_blue));
						 		break;
						 	case "yellow":
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_yellow));
						 		break;
						 	default:
						 		radioGroupView.setBackground(getResources().getDrawable(R.drawable.background_view_rounded_orange));
						 		break;
					 	}
					 
					 final int numberOfRadioButtons = options[currentQuestionNumber-1].length;
					 //sets up radio button
					 if (numberOfRadioButtons<=NUMBER_OF_RADIO_IDS_AVAILABLE){	//but first check if numberOfRadioButtons given by the questions exceeds the available ID Resources created for the radio buttons, CURRENTLY: 10
						 final RadioButton[] rb = new RadioButton[numberOfRadioButtons]; 
				         for(int i=0; i<numberOfRadioButtons; i++){
				        	  rb[i] = new RadioButton(view.getContext());
				        	  rb[i].setText(options[currentQuestionNumber-1][i]);
				        	  int idResource = getResources().getIdentifier("rb"+i, "id", view.getContext().getPackageName());
				        	  rb[i].setId(idResource);
				        	  radioGroup.addView(rb[i]);
				         }
					     //set up radio buttons to remember previous entry (if any)
					        if (response[currentQuestionNumber-1] != null){
					        	radioButtonValue = Integer.parseInt(response[currentQuestionNumber-1]);
                                //debug purposes
//					      		  Toast.makeText(view.getContext(),"Refresh radiobutton:"+radioButtonValue,
//					      					Toast.LENGTH_SHORT).show();
					        }
					        int idResource = getResources().getIdentifier("rb"+radioButtonValue, "id", view.getContext().getPackageName());
					        
					        radioGroup.check(idResource);
				        //sets up on checked listener
					        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
				        {
				            public void onCheckedChanged(RadioGroup group, int checkedId) {
				            	// finds which radiobutton is pressed and sets radiobuttonnumber
				            	for(int i=0; i<numberOfRadioButtons; i++){
				            		int idResource = getResources().getIdentifier("rb"+i, "id", group.getContext().getPackageName());
				            		if (checkedId==idResource){
				            			radioButtonValue = i;
				            			System.out.println("radioButtonValue: "+ radioButtonValue);
				            		}
				            	}
				            }
				        });
				         
					 }else{//not enough radio button ids
						 //output some error message
					 }
					 break;
				 }
				 default:
					 //prints out error message
					 break;
			 }
			 
			 
          
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
	    		if(currentQuestionNumber!=1){	//first question does not have a back button
	    			currentQuestionNumber--;
			        currentQuestion = questions[currentQuestionNumber-1];
			        //update the view by replacing the fragment
			        FragmentManager frgManager = getFragmentManager();
			        frgManager.beginTransaction().replace(R.id.content_frame, new Fragment_Assessment()).commit();
	    		}
	        	break;
	        }
	        case R.id.button_question_next_save:{
	        	//save values into response
	        	switch(questionType[currentQuestionNumber-1]){
	        		case TYPE_SLIDER:
	        			response[currentQuestionNumber-1] = Integer.toString(painSliderValue);
	        			break;
	        		case TYPE_RADIO:
	        			response[currentQuestionNumber-1] = Integer.toString(radioButtonValue);	
	        			break;
	        		default:
	        			//do nothing
	        			break;
	        	}
	        	
	        	/**
    			 * ONLY FOR VAS
    			 * This is a special request made by NUH Paediatrics Department
    			 */
    			final EditText input = new EditText(this.getActivity());
    			if(assessmentTitle.equals("VAS")&&painSliderValue<5){
    				new AlertDialog.Builder(view.getContext())
//    			    .setTitle("")
    			    .setMessage("Please tell us more.")
    			    .setView(input)
    			    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
    			        public void onClick(DialogInterface dialog, int whichButton) {
    			            Editable editableValue = input.getText();
    			            Toast.makeText(getActivity(),editableValue.toString(), Toast.LENGTH_SHORT).show();
    			            freeTextVAS[currentQuestionNumber-1] = editableValue.toString();
    			            proceedToNextQuestionOrSave();
    			        }
    			    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    			        public void onClick(DialogInterface dialog, int whichButton) {
    			            // Do nothing.
    			        }
    			    }).show();
    			}else{
    				proceedToNextQuestionOrSave();
    			}        
		        break;
	        }
    	} 

    }
    
    void proceedToNextQuestionOrSave(){
    	if(currentQuestionNumber!=questionsSize){	//if not the last question update variables to next question in assessment
        	currentQuestionNumber++;
	        currentQuestion = questions[currentQuestionNumber-1];
	      //update the view by replacing the fragment
	        FragmentManager frgManager = getFragmentManager();
	        frgManager.beginTransaction().replace(R.id.content_frame, new Fragment_Assessment()).commit();
        }else{	//this is the last question, save it to the report
        	//sets up a POP-UP
	        AlertDialog.Builder alertDB = new AlertDialog.Builder(getActivity());
	        alertDB.setMessage("Do you really want to save this entry?");
	        alertDB.setCancelable(true);
	        alertDB.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
	        	@Override
                public void onClick(DialogInterface dialogInterface, int id) {
	    		        //persist the data
	    		        Dialog dialog  = (Dialog) dialogInterface;
	    		        Context context = dialog.getContext();
	    		        //gets the size of the reports
	    		        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, DEFAULT_INT_ZERO);
	    		        //gets the current reportsize so far
	    		        int reportSizeInt = pref.getInt(REPORT_SIZE, DEFAULT_INT_ZERO);
	    		        String reportSize = Integer.toString(reportSizeInt);
	    		        SharedPreferences.Editor editor = pref.edit();
	    		        //increase the size of the report by one, so that report fragment knows how many rows to print
	    		        editor.putInt(REPORT_SIZE,reportSizeInt+1);
	    		        //add assessmentTitle to the memory
	    		        editor.putString(REPORT_TITLE+reportSize, assessmentTitle);
	    		        //get datetime and add it to memory
	    		        String currentDateTime = getDateTime(System.currentTimeMillis());
	    		        editor.putString(REPORT_DATETIME+reportSize, currentDateTime);
	    		        //updates max question number
	    		        int maxQuestion = pref.getInt(REPORT_MAX_QUESTION, DEFAULT_INT_ZERO);
	    		        if(questionsSize>maxQuestion){
	    		        	editor.putInt(REPORT_MAX_QUESTION, questionsSize);
	    		        }
	    		        editor.commit();
	    		        setStringArrayPref(context,REPORT+reportSize, new ArrayList(Arrays.asList(response)));
	    		        setStringArrayPref(context,REPORT_FREE_TEXT_VAS+reportSize, new ArrayList(Arrays.asList(freeTextVAS)));

                          Toast.makeText(dialog.getContext(),"Report successfully recorded.",
                                    Toast.LENGTH_SHORT).show();

                        //switch to report fragment
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, new Fragment_Report()).commit();

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
	        //create the alertdialog after customizing
            AlertDialog confirmSave = alertDB.create();
            confirmSave.show();
        }
    }
    
    public String getAssessmentTitle(){
    	return assessmentTitle;
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
    
    public  String getDateTime(long timestamp) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    	String currentDateandTime = sdf.format(new Date());
    	return currentDateandTime;
    }
    
    
}
