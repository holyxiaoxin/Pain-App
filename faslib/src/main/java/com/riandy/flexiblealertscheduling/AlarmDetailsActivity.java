package com.riandy.flexiblealertscheduling;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmDetailsActivity extends Activity {
	
	private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
	
	private AlarmModel alarmDetails;
	
	private TimePicker timePicker;
	private EditText edtName;
	private CustomSwitch chkWeekly;
	private CustomSwitch chkSunday;
	private CustomSwitch chkMonday;
	private CustomSwitch chkTuesday;
	private CustomSwitch chkWednesday;
	private CustomSwitch chkThursday;
	private CustomSwitch chkFriday;
	private CustomSwitch chkSaturday;
	private TextView txtToneSelection,txtAlarmLabel;
	
	public static final String ALARM_RINGTONE = "ringtone";
	public static final String ALARM_MUSIC = "music";
	public static final String ALARM_LAUNCH_APP = "launchApp";
	public static final String ALARM_SILENT = "silent";
	
	private String alarmType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_details);

		getActionBar().setTitle("Create New Alarm");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		timePicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
		edtName = (EditText) findViewById(R.id.alarm_details_name);
		chkWeekly = (CustomSwitch) findViewById(R.id.alarm_details_repeat_weekly);
		chkSunday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_sunday);
		chkMonday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_monday);
		chkTuesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_tuesday);
		chkWednesday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_wednesday);
		chkThursday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_thursday);
		chkFriday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_friday);
		chkSaturday = (CustomSwitch) findViewById(R.id.alarm_details_repeat_saturday);
		txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
		txtAlarmLabel = (TextView) findViewById(R.id.alarm_label_tone);

		long id = getIntent().getExtras().getLong("id");
		
		if (id == -1) {
			alarmDetails = new AlarmModel();
		} else {
			alarmDetails = dbHelper.getAlarm(id);
			
			timePicker.setCurrentMinute(alarmDetails.timeMinute);
			timePicker.setCurrentHour(alarmDetails.timeHour);
			
			edtName.setText(alarmDetails.name);
			
			chkWeekly.setChecked(alarmDetails.repeatWeekly);
			chkSunday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
			chkMonday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
			chkTuesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
			chkWednesday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
			chkThursday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
			chkFriday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRDIAY));
			chkSaturday.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));

			txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
		}

		final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
		ringToneContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//silent mode
				
				if(alarmType == ALARM_RINGTONE) {
					//selecting ringtone
					Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
					startActivityForResult(intent , 1);
				}else if(alarmType == ALARM_MUSIC) {
					//selecting music
					Toast.makeText(getApplicationContext(), "Sorry,  not yet implemented",Toast.LENGTH_SHORT).show();
				}else if(alarmType == ALARM_LAUNCH_APP) {
					//selecting Apps
					//display list of Apps in another activity
					Intent intent = new Intent(getApplicationContext(),AppsListActivity.class);
					startActivityForResult(intent,2);
				}else {
					Toast.makeText(getApplicationContext(), "Sorry,  not yet implemented",Toast.LENGTH_SHORT).show();					
				}
			}
		});
		
		final LinearLayout alarmTypeContainer = (LinearLayout) findViewById(R.id.alarm_type_container);
		alarmTypeContainer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				PopupMenu popup = new PopupMenu(getApplicationContext(), alarmTypeContainer);
                popup.getMenuInflater()
                    .inflate(R.menu.popup_menu_alert_type, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        
                    	int id = item.getItemId();
              
                    	TextView alarmLabel = (TextView) findViewById(R.id.alarm_label_type_selection);
                    	alarmLabel.setText(item.getTitle());
                    	if (id == R.id.alert_silent) {
                    		txtAlarmLabel.setTextColor(Color.GRAY);
                    		txtAlarmLabel.setText("No sound..");
                    		txtToneSelection.setText("None");
                    		alarmType = ALARM_SILENT;
                    	} else if(id == R.id.alert_music) {
                        	txtAlarmLabel.setText("Alarm "+item.getTitle());
                        	txtToneSelection.setText("Select an alarm "+item.getTitle());
                        	alarmType = ALARM_MUSIC;
                    	} else if (id == R.id.alert_launch_app) {
                        	txtAlarmLabel.setText("Alarm "+item.getTitle());
                        	txtToneSelection.setText("Select an alarm "+item.getTitle());
                        	alarmType = ALARM_LAUNCH_APP;
                    	} else if (id == R.id.alert_ringtone) {
                        	txtAlarmLabel.setText("Alarm "+item.getTitle());
                        	txtToneSelection.setText("Select an alarm "+item.getTitle());
                        	alarmType = ALARM_RINGTONE;
                    	}
                    	
                        return true;
                    }
                });

                popup.show(); //showing popup menu
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        switch (requestCode) {
		        case 1: 
		        	alarmDetails.alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		        	txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.alarmTone).getTitle(this));
		            break;
		        case 2:
		        	Toast.makeText(getApplicationContext(), data.getStringExtra("appSelected"), Toast.LENGTH_SHORT).show();
		        	txtToneSelection.setText(data.getStringExtra("appSelected"));
		        	break;
		        default: {
		            break;
		        }
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm_details, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			finish();
		} else if (itemId == R.id.action_save_alarm_details) {
			updateModelFromLayout();
			AlarmManagerHelper.cancelAlarms(this);
			if (alarmDetails.id < 0) {
				dbHelper.createAlarm(alarmDetails);
			} else {
				dbHelper.updateAlarm(alarmDetails);
			}
			AlarmManagerHelper.setAlarms(this);
			setResult(RESULT_OK);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}
	
	private void updateModelFromLayout() {		
		alarmDetails.timeMinute = timePicker.getCurrentMinute().intValue();
		alarmDetails.timeHour = timePicker.getCurrentHour().intValue();
		alarmDetails.name = edtName.getText().toString();
		alarmDetails.repeatWeekly = chkWeekly.isChecked();	
		alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, chkSunday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.MONDAY, chkMonday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, chkTuesday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, chkWednesday.isChecked());	
		alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, chkThursday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.FRDIAY, chkFriday.isChecked());
		alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, chkSaturday.isChecked());
		alarmDetails.isEnabled = true;
	}
	
}
