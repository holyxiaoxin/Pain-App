package com.dexterlimjiarong.painapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.riandy.flexiblealertscheduling.AlarmDBHelper;
import com.riandy.flexiblealertscheduling.AlarmManagerHelper;
import com.riandy.flexiblealertscheduling.AlarmModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
 
public class MainActivity extends Activity {
 
      public static DrawerLayout mDrawerLayout;
      public static ListView mDrawerList;
      private ActionBarDrawerToggle mDrawerToggle;
 
      private CharSequence mDrawerTitle;
      public CharSequence mTitle;
      CustomDrawerAdapter adapter;
      List<DrawerItem> dataList;
 
      /**
       * PQAS
       */
      //questions
      public static final String PQAS_QUESTION_1 = "Please use the scale below to tell us how INTENSE your pain has been over the past week, on average.";
      public static final String PQAS_QUESTION_2 = "Please use the scale below to tell us how SHARP your pain has felt over the past week. Words used to describe sharp feelings include like a knife like a spike piercing";
      public static final String PQAS_QUESTION_3  = "Please use the scale below to tell us how HOT your pain has felt over the past week. Words used to describe very hot feelings include burning and on fire";
      public static final String PQAS_QUESTION_4  = "Please use the scale below to tell us how DULL your pain has felt over the past week.";
      public static final String PQAS_QUESTION_5  = "Please use the scale below to tell us how COLD your pain has felt over the past week. Words used to describe very cold pain include like ice and freezing";
      public static final String PQAS_QUESTION_6  = "Please use the scale below to tell us how SENSITIVE your skin has been to light touch or clothing rubbing against it over the past week. Words used to describe sensitive skin include like sunburned skin and raw skin";
      public static final String PQAS_QUESTION_7  = "Please use the scale below to tell us how TENDER your pain is when something has pressed against it over the past week. Another word to describe tender pain is like a bruise";
      public static final String PQAS_QUESTION_8  = "Please use the scale below to tell us how ITCHY your pain has felt over the past week. Words used to describe itchy pain include like poison ivy and like mosquito bite";
      public static final String PQAS_QUESTION_9  = "Please use the scale below to tell us how much your pain has felt like it has been SHOOTING over the past week. Another word used to describe shooting pain is zapping";
      public static final String PQAS_QUESTION_10  = "Please use the scale below to tell us how NUMB your pain has felt over the past week. A phrase that can be used to describe numb pain is like it is asleep";
      public static final String PQAS_QUESTION_11  = "Please use the scale below to tell us how much pain sensations have felt ELECTRICAL over the past week. Words used to describe electrical pain include shocks lightning sparking";
      public static final String PQAS_QUESTION_12  = "Please use the scale below to tell us how TINGLING your pain has felt over the past week. Words used to describe tingling pain include like pins and needles prickling";
      public static final String PQAS_QUESTION_13  = "Please use the scale below to tell us how CRAMPING your pain has felt over the past week. Words used to describe cramping pain include squeezing tight";
      public static final String PQAS_QUESTION_14  = "Please use the scale below to tell us how RADIATING your pain has felt over the past week. Another word used to describe radiating pain is spreading";
      public static final String PQAS_QUESTION_15  = "Please use the scale below to tell us how THROBBING your pain has felt over the past week. Another word used to describe throbbing pain is pounding";
      public static final String PQAS_QUESTION_16  = "Please use the scale below to tell us how ACHING your pain has felt over the past week. Another word used to describe aching pain is like a toothache";
      public static final String PQAS_QUESTION_17  = "Please use the scale below to tell us how HEAVY your pain has felt over the past week. Other words used to describe heavy pain are pressure and weighted down";
      public static final String PQAS_QUESTION_18  = "Now that you have told us the different types of pain sensation you have felt, we want you to tell us overall how UNPLEASANT your pain has been to you over the past week. Words used to describe very unpleasant pain include annoying bothersome miserable intolerable. Remember, pain can have a low intensity but still feel very unpleasant, and some kinds of pain can have high intensity but be very tolerable. With this scale,  please tell us how unpleasant your pain feels.";
      public static final String PQAS_QUESTION_19  = "We want you to give us an estimate of the severity of your DEEP versus SURFACE pain over the past week. We want to rate each location of pain separately. We realised that it can be difficult to make these estimates, and most likely it will be best guess, but please give us your best estimate.";
      public static final String PQAS_QUESTION_20  = "We want you to give us an estimate of the severity of your DEEP versus SURFACE pain over the past week. We want to rate each location of pain separately. We realised that it can be difficult to make these estimates, and most likely it will be best guess, but please give us your best estimate.";
      public static final String PQAS_QUESTION_21  = "Pain can also have different time qualities. For some people, the pain comes and goes and so they have some moments that are completely without pain; in other words the pain comes and goes. This is called INTERMITTENT pain. Others are never pain free, but their pain types and pain severity can vary from one moment to the next. This is called VARIABLE pain. For these people, the increases can be severe, so that they feel they have moments of very intense pain (breakthrough pain), but at other times they can feel lower levels of pain (background pain). Still, they are never pain free. Other people have pain that really does not change that much from one moment to another. This is called STABLE pain. Which of these best describes the time pattern of your pain (please select only one):";
      public static final String PQAS_QUESTION_21_1 ="I have INTERMITTENT pain (I feel pain sometimes but I am pain-free at other times).";
      public static final String PQAS_QUESTION_21_2 ="I have variable pain (background pain all the time, but also moments of more pain, or severe breakthrough pain or varying types of pain).";
      public static final String PQAS_QUESTION_21_3 ="I have stable pain (constant pain that does not change very much from one moment to another, and no pain-free periods).";
      
      public static final String PQAS_SLIDER_TITLE_1 = "INTENSE";
      public static final String PQAS_SLIDER_TITLE_2 = "SHARP";
      public static final String PQAS_SLIDER_TITLE_3 = "HOT";
      public static final String PQAS_SLIDER_TITLE_4 = "DULL";
      public static final String PQAS_SLIDER_TITLE_5 = "COOL";
      public static final String PQAS_SLIDER_TITLE_6 = "SENSITIVE";
      public static final String PQAS_SLIDER_TITLE_7 = "TENDER";
      public static final String PQAS_SLIDER_TITLE_8 = "ITCHY";
      public static final String PQAS_SLIDER_TITLE_9 = "SHOOTING";
      public static final String PQAS_SLIDER_TITLE_10 = "NUMB";
      public static final String PQAS_SLIDER_TITLE_11 = "ELECTRICAL";
      public static final String PQAS_SLIDER_TITLE_12 = "TINGLING";
      public static final String PQAS_SLIDER_TITLE_13 = "CRAMPING";
      public static final String PQAS_SLIDER_TITLE_14 = "RADIATING";
      public static final String PQAS_SLIDER_TITLE_15 = "THROBBING";
      public static final String PQAS_SLIDER_TITLE_16 = "ACHING";
      public static final String PQAS_SLIDER_TITLE_17 = "HEAVY";
      public static final String PQAS_SLIDER_TITLE_18 = "UNPLEASANT";
      public static final String PQAS_SLIDER_TITLE_19 = "DEEP";
      public static final String PQAS_SLIDER_TITLE_20 = "SURFACE";
      public static final String PQAS_SLIDER_TITLE_21 = "";
      
      /**
       * HADS - Hospital Anxiety and Depression Scale
       */
      public static final String HADS_QUESTION_1 = "I wake early and then sleep badly for the rest of the night.";
      public static final String HADS_QUESTION_2 = "I get very frustrated or have panic feelings for apparently no reason at all.";
      public static final String HADS_QUESTION_3 = "I feel miserable and sad.";
      public static final String HADS_QUESTION_4 = "I feel anxious when I go out of the house on my own.";
      public static final String HADS_QUESTION_5 = "I have lost interest in things.";
      public static final String HADS_QUESTION_6 = "I get palpitations, or sensations or 'butterflies' in my stomach or chest.";
      public static final String HADS_QUESTION_7 = "I have a good appetite.";
      public static final String HADS_QUESTION_8 = "I feel scared or frightened.";
      public static final String HADS_QUESTION_9 = "I feel life is not worth living.";
      public static final String HADS_QUESTION_10 = "I still enjoy the things I used to.";
      public static final String HADS_QUESTION_11 = "I am restless and can't keep still.";
      public static final String HADS_QUESTION_12 = "I am more irritable than usual.";
      public static final String HADS_QUESTION_13 = "I feel as if I have slowed down.";
      public static final String HADS_QUESTION_14 = "Worrying thoughts constantly go through my mind.";
      public static final String HADS_OPTION_1 = "No, not at all";
      public static final String HADS_OPTION_2 = "No, not much";
      public static final String HADS_OPTION_3 = "Yes, sometimes";
      public static final String HADS_OPTION_4 = "Yes, definitely";
      public static final String HADS_MAXVALUE = "3";
      public static final String[] HADS_QUESTION_LIST = {HADS_QUESTION_1, HADS_QUESTION_2,HADS_QUESTION_3,HADS_QUESTION_4,HADS_QUESTION_5,HADS_QUESTION_6,HADS_QUESTION_7,HADS_QUESTION_8,HADS_QUESTION_9,
    	  							HADS_QUESTION_10,HADS_QUESTION_11,HADS_QUESTION_12,HADS_QUESTION_13,HADS_QUESTION_14};
      public static final String[] HADS_OPTION_LIST = {HADS_OPTION_1, HADS_OPTION_2, HADS_OPTION_3, HADS_OPTION_4};
      public static final String DEFAULT_COLOR = "default";
      public static final String[] HADS_COLOR_LIST = {DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, 
    	  											DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR};
      JSONObject hads_json = null; //JSON object to be passed for HADS assessment
      
      /**
       * VAS - Visual Analogue Scale
       */
      public static final String VAS_QUESTION_1 = "How is your EMOTIONAL STATE today?";
      public static final String VAS_QUESTION_2 = "How is your PHYSICAL HEALTH today?";
      public static final String VAS_MAXVALUE = "10";
      public static final String[] VAS_QUESTION_LIST = {VAS_QUESTION_1, VAS_QUESTION_2};
      public static final String VAS_QUESTION_1_COLOR = "blue";
      public static final String VAS_QUESTION_2_COLOR = "green";
      public static final String[] VAS_COLOR_LIST = {VAS_QUESTION_1_COLOR, VAS_QUESTION_2_COLOR};
      JSONObject vas_json = null; //JSON object to be passed for VAS assessment
      
      //question types
      public static final String TYPE_SLIDER = "slider";
      public static final String TYPE_RADIO = "radio";
      
      public static final String[] questionsType = {TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER
    	  											, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_SLIDER, TYPE_RADIO};
      public static final String[][] questions = {{PQAS_QUESTION_1}, {PQAS_QUESTION_2}, {PQAS_QUESTION_3}, {PQAS_QUESTION_4}, {PQAS_QUESTION_5}, {PQAS_QUESTION_6}, {PQAS_QUESTION_7}
      								,{PQAS_QUESTION_8}, {PQAS_QUESTION_9}, {PQAS_QUESTION_10}, {PQAS_QUESTION_11}, {PQAS_QUESTION_12}, {PQAS_QUESTION_13}, {PQAS_QUESTION_14}
      								,{PQAS_QUESTION_15}, {PQAS_QUESTION_16}, {PQAS_QUESTION_17}, {PQAS_QUESTION_18}, {PQAS_QUESTION_19}, {PQAS_QUESTION_20}
      								, {PQAS_QUESTION_21, PQAS_QUESTION_21_1, PQAS_QUESTION_21_2, PQAS_QUESTION_21_3}}; 
      public static final String[] sliderTitle = {PQAS_SLIDER_TITLE_1, PQAS_SLIDER_TITLE_2, PQAS_SLIDER_TITLE_3, PQAS_SLIDER_TITLE_4, PQAS_SLIDER_TITLE_5, PQAS_SLIDER_TITLE_6, PQAS_SLIDER_TITLE_7
    	  										, PQAS_SLIDER_TITLE_8, PQAS_SLIDER_TITLE_9, PQAS_SLIDER_TITLE_10, PQAS_SLIDER_TITLE_11, PQAS_SLIDER_TITLE_12, PQAS_SLIDER_TITLE_13, PQAS_SLIDER_TITLE_14
    	  										, PQAS_SLIDER_TITLE_15, PQAS_SLIDER_TITLE_16, PQAS_SLIDER_TITLE_17, PQAS_SLIDER_TITLE_18, PQAS_SLIDER_TITLE_19, PQAS_SLIDER_TITLE_20, PQAS_SLIDER_TITLE_21};
      
      @Override
      protected void onCreate(Bundle savedInstanceState) {
    	  	//extending the existing parent class' method
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

              getApplicationContext().deleteDatabase(AlarmDBHelper.DATABASE_NAME);
              scheduleReminders();

            
            //sets up the JSONObject for HADS Assessment 
            hads_json = new JSONObject();
            /**
             * this is only needed if radio buttons are used
             */
            JSONArray hads_options = new JSONArray();
            JSONObject hads_option = null;
            try{
            	for(int i=0;i<4;i++){
            		hads_option = new JSONObject();
            		hads_option.put("option",HADS_OPTION_LIST[i]);
            		hads_options.put(hads_option);
            	}
            }catch (JSONException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            }
            /**
             * end
             */
            
            JSONArray hads_questions = new JSONArray();
            try{
            	//add the title to the HADS JSONObject
            	hads_json.put("title","FEEL");
            	for(int i=0;i<HADS_QUESTION_LIST.length;i++){
            		//creates a new single question object
            		JSONObject hads_question = new JSONObject();
            		//adds the question into the question object
            		hads_question.put("question",HADS_QUESTION_LIST[i]);
            		//adds the color into the question object
            		hads_question.put("color", HADS_COLOR_LIST[i]);
            		//adds the questionTpes into the question object
            		/**
            		 * yet to decide to use slider or radio buttons
            		 * choose 1
            		 */
            		//1. for slider
//            		hads_question.put("questionType","slider");
//            		hads_question.put("maxValue", HADS_MAXVALUE);
            		//2. for radio button
            		hads_question.put("questionType","radio");
            		hads_question.put("options",hads_options);
            		hads_question.put("maxValue", "");
            		/**
            		 * end
            		 */
            		//append the question to the questions JSONArray
            		hads_questions.put(hads_question);
            	}
            	//add questions JSONArray to the HADS JSONObject
            	hads_json.put("questions",hads_questions);
            }catch (JSONException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            }
            
          //sets up the JSONObject for VAS Assessment 
            vas_json = new JSONObject();
            JSONArray vas_questions = new JSONArray();
            try{
            	vas_json.put("title","VAS");
            	for(int i=0;i<VAS_QUESTION_LIST.length;i++){
            		JSONObject vas_question = new JSONObject();
            		vas_question.put("question",VAS_QUESTION_LIST[i]);
            		vas_question.put("questionType","slider");
            		vas_question.put("maxValue", VAS_MAXVALUE);
            		vas_question.put("color", VAS_COLOR_LIST[i]);
            		vas_questions.put(vas_question);
            	}
            	vas_json.put("questions",vas_questions);
            }catch (JSONException e) {
            	// TODO Auto-generated catch block
            	e.printStackTrace();
            }
            
            // Initializing
            dataList = new ArrayList<DrawerItem>();
            mTitle = mDrawerTitle = getTitle();
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);
 
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                        GravityCompat.START);
 
         //There must always be a drawerItem Header for every drawerItem
         // Add Drawer Item to dataList
 
//            dataList.add(new DrawerItem("Username"));	//0. header for username
//            dataList.add(new DrawerItem("Home", R.drawable.ic_action_email));	//1. Home
//
//            dataList.add(new DrawerItem("Assessments")); //2. header for assessment
//            dataList.add(new DrawerItem("HADS", R.drawable.ic_action_labels));	//3. HADS
//            dataList.add(new DrawerItem("VAS", R.drawable.ic_action_labels));	//4. VAS
//            dataList.add(new DrawerItem("Custom Assessments", R.drawable.ic_action_labels));	//5. Custom Assessments
//            //dataList.add(new DrawerItem(true)); // adding a spinner to the list
//            	//dataList.add(new DrawerItem("Likes", R.drawable.ic_action_good));
//            	//dataList.add(new DrawerItem("Games", R.drawable.ic_action_gamepad));
//            	//dataList.add(new DrawerItem("Lables", R.drawable.ic_action_labels));
//
//            dataList.add(new DrawerItem("History"));	//6. header for history
//            dataList.add(new DrawerItem("View Reports", R.drawable.ic_action_search));	//7. View Reports
//            	//dataList.add(new DrawerItem("Cloud", R.drawable.ic_action_cloud));
//            	//dataList.add(new DrawerItem("Camara", R.drawable.ic_action_camera));
//            	//dataList.add(new DrawerItem("Video", R.drawable.ic_action_video));
//            	//dataList.add(new DrawerItem("Groups", R.drawable.ic_action_group));
//            	//dataList.add(new DrawerItem("Import & Export", R.drawable.ic_action_import_export));
//
//            dataList.add(new DrawerItem("Configurations"));	//7. header for configs
//            dataList.add(new DrawerItem("Settings", R.drawable.ic_action_about));	//9. Settings
//            dataList.add(new DrawerItem("Quit", R.drawable.ic_action_settings));	//10. Quit
//            	//dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));

          dataList.add(new DrawerItem("Home"));	//0. header for username
          dataList.add(new DrawerItem("Instructions", R.drawable.ic_action_email));	//1. Home

          dataList.add(new DrawerItem("Assessments")); //2. header for assessment
          dataList.add(new DrawerItem("FEEL", R.drawable.ic_action_labels));	//3. HADS is renamed to FEEL
          dataList.add(new DrawerItem("VAS", R.drawable.ic_action_labels));	//4. VAS

          dataList.add(new DrawerItem("History"));	//5. header for history
          dataList.add(new DrawerItem("View Reports", R.drawable.ic_action_search));	//6. View Reports

          dataList.add(new DrawerItem("Configurations"));	//7. header for configs
          dataList.add(new DrawerItem("Quit", R.drawable.ic_action_settings));	//8. Quit


 
            adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                        dataList);
 
            mDrawerList.setAdapter(adapter);
 
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
 
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                        R.drawable.ic_drawer, R.string.drawer_open,
                        R.string.drawer_close) {
                  public void onDrawerClosed(View view) {
                        getActionBar().setTitle(mTitle);
                        invalidateOptionsMenu(); // creates call to
                                                                  // onPrepareOptionsMenu()
                  }
 
                  public void onDrawerOpened(View drawerView) {
                	  	//forces updateView to the spinner whenever the drawer is opened
                	  	adapter.updateView();
                        getActionBar().setTitle(mDrawerTitle);
                        invalidateOptionsMenu(); // creates call to
                                                                  // onPrepareOptionsMenu()
                  }
            };
 
            mDrawerLayout.setDrawerListener(mDrawerToggle);
 
            if (savedInstanceState == null) {
            	 
                if (dataList.get(0).isSpinner()
                            & dataList.get(1).getTitle() != null) {
                      SelectItem(2);
                } else if (dataList.get(0).getTitle() != null) {
                      SelectItem(1);
                } else {
                      SelectItem(0);
                }
          }
 
      }
 
      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
      }
 
      
      //You need to update this according to the �dataList� item position. 
      //Fragment should be only need to change when we click in a selectable 
      //item not in header or spinner.  So change the case statements to point 
      //correct selectable item. (Be careful you may get runtime errors if you 
      //update incorrectly.) I delete the  0,1 and 6 case statement and 
      //add new case statement for 14,15 and 16 positions .
      public void SelectItem(int position) {
    	  
          Fragment fragment = null;
          Bundle args = new Bundle();
          switch (position) {
     
//        case 1:	//Login
//        	//since it did not fetch previous bundle, all data will be lost once the home navigation button is pressed
//        	//should remember questionnaires entries?
////              fragment = new FragmentOne();
////              args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
////                          .getItemName());
////              args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList
////                          .get(position).getImgResID());
//        	fragment = new Fragment_Login();
//            break;
//        case 3:	//HADS Assessment
//            fragment = new Fragment_Assessment(hads_json);
//            break;
//        case 4:	//VAS Assessment
//            fragment = new Fragment_Assessment(vas_json);
//            break;
//        case 5:	//Custom Assessment
//            fragment = new Fragment_List_Of_Assessments();
//            break;
//        case 7:	//Reports
//            fragment = new Fragment_Report();
//            args.putString(Fragment_Report.ITEM_NAME, dataList.get(position)
//                        .getItemName());
//            args.putInt(Fragment_Report.IMAGE_RESOURCE_ID, dataList
//                        .get(position).getImgResID());
//            break;
//        case 9:	//Settings
//            fragment = new Fragment_Settings();
//            break;
//        case 10:	//Quit
//            fragment = new FragmentTwo();
//            args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
//                        .getItemName());
//            args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList
//                        .get(position).getImgResID());
//            break;
//
//          default:
//                break;
//          }


              case 1:	//Instructions
                  fragment = new Fragment_Instructions();
                  break;
              case 3:	//HADS Assessment
                  fragment = new Fragment_Assessment(hads_json);
                  break;
              case 4:	//VAS Assessment
                  fragment = new Fragment_Assessment(vas_json);
                  break;
              case 6:	//Reports
                  fragment = new Fragment_Report();
                  break;
              case 8:	//Quit
                  fragment = new FragmentTwo();
                  break;
              default:
                  break;
          }
     
          fragment.setArguments(args);
          FragmentManager frgManager = getFragmentManager();
          frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                      .commit();
     
          mDrawerList.setItemChecked(position, true);
          setTitle(dataList.get(position).getItemName());
          mDrawerLayout.closeDrawer(mDrawerList);
    }
 
      @Override
      public void setTitle(CharSequence title) {
            mTitle = title;
            getActionBar().setTitle(mTitle);
      }
 
      @Override
      protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
      }
 
      @Override
      public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggles
            mDrawerToggle.onConfigurationChanged(newConfig);
      }
 
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
            // The action bar home/up action should open or close the drawer.
            // ActionBarDrawerToggle will take care of this.
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                  return true;
            }
 
            return false;
      }
 
        private class DrawerItemClickListener implements
                  ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                        long id) {
            	if (dataList.get(position).getTitle() == null) {
                    SelectItem(position);
              }
 
            }
      }

    public void scheduleReminders(){

        AlarmDBHelper dbHelper = new AlarmDBHelper(this);
        File dbFile = getApplicationContext().getDatabasePath(AlarmDBHelper.DATABASE_NAME);
        if(dbFile.exists()){
            Log.d("Database exist", "do not schedule again!");
            return;
        }

        //Emotional and Physical discomfort (0-19) days = 20
        //HADS day 7,9,11,13,15,17,19
        //time will be 6.30pm
        int alarmHour = 18;
        int alarmMinute = 30;
        int numOfDays = 20;

        String EmotionalAndPhysicalMessage = "Please fill in the Emotional and Physical Discomfort survey.";
        String HADSMessage = "Please fill in the HADS survey";

        Calendar today = Calendar.getInstance();
        //today.set(2014, Calendar.NOVEMBER, 5,0,0);
        Calendar startDate = (Calendar) today.clone();
        Calendar endDate = (Calendar) today.clone();
        endDate.add(Calendar.DATE, numOfDays);

        //Emotional and Physical discomfort (daily)
        AlarmModel alarmDetails = new AlarmModel();
        alarmDetails.name = EmotionalAndPhysicalMessage;
        alarmDetails.timeHour = alarmHour;
        alarmDetails.timeMinute = alarmMinute;
        alarmDetails.isEnabled = true;
        alarmDetails.repeatWeekly = true;
        for (int i=0; i<7; i++)
            alarmDetails.setRepeatingDay(i, true);
        alarmDetails.startDate = startDate.getTime();
        alarmDetails.endDate = endDate.getTime();
        dbHelper.createAlarm(alarmDetails);

        //HADS (7,9,11,13,15,17,19)
        int[] arr = {7,9,11,13,15,17,19};
        for (int i=0; i<arr.length; i++) {
            alarmDetails = new AlarmModel();
            alarmDetails.name = HADSMessage+" day "+arr[i];
            alarmDetails.timeHour = alarmHour;
            alarmDetails.timeMinute = alarmMinute+10;
            alarmDetails.isEnabled = true;
            alarmDetails.repeatWeekly = false;
            for (int j=0; j<7; j++)
                alarmDetails.setRepeatingDay(j, true);
            startDate = (Calendar) today.clone();
            startDate.add(Calendar.DATE, (arr[i]));
            endDate = (Calendar) startDate.clone();
            alarmDetails.startDate = startDate.getTime();
            alarmDetails.endDate = endDate.getTime();
            dbHelper.createAlarm(alarmDetails);
        }

        AlarmManagerHelper.setAlarms(getApplicationContext());
    }
 
}