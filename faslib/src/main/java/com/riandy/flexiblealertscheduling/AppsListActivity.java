package com.riandy.flexiblealertscheduling;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AppsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apps_list);
		PInfo pInfo = new PInfo(getApplicationContext());
		final List<PInfo> appList = pInfo.getPackages();
		
		ListView appListView = (ListView) findViewById(R.id.apps_listView);
		AppsArrayAdapter adapter = new AppsArrayAdapter(getApplicationContext(),appList);
		appListView.setAdapter(adapter);
		
		appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("appSelected", appList.get(position).getAppname());
				setResult(RESULT_OK,returnIntent);
				finish();
			}		
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apps_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
