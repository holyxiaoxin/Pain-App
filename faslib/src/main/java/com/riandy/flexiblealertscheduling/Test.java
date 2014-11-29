package com.riandy.flexiblealertscheduling;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Test {

	public Test(Context context,String hello){
		Toast.makeText(context, hello, Toast.LENGTH_LONG).show();
		Intent in = new Intent(context,AlarmListActivity1.class);
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(in);
	}
}
