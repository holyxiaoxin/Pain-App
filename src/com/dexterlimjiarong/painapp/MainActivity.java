package com.dexterlimjiarong.painapp;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity implements
	OnItemClickListener {
	
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] socialSites;
	//private String[] planets;
	private ActionBarDrawerToggle drawerListener;
	private MyAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//planets = getResources().getStringArray(R.array.planets);
		socialSites = getResources().getStringArray(R.array.navigationDrawerItems);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		listView = (ListView) findViewById(R.id.drawerList);
		myAdapter = new MyAdapter(this);
		listView.setAdapter(myAdapter);

		// set up the drawer's list view with items and click listener
//		listView.setAdapter(new ArrayAdapter<>(this,
//				android.R.layout.simple_list_item_1, planets));
		listView.setOnItemClickListener(this);

		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				//super.onDrawerClosed(drawerView);
				SingleToast.show(MainActivity.this, "Drawer Closed", Toast.LENGTH_SHORT);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				//super.onDrawerOpened(drawerView);
				SingleToast.show(MainActivity.this, "Drawer Opened", Toast.LENGTH_SHORT);
			}
		};
		drawerLayout.setDrawerListener(drawerListener);
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		// The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
		if(drawerListener.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//SingleToast.show(this, planets[position] + " was selected",
		//		Toast.LENGTH_SHORT);
		SingleToast.show(this, socialSites[position] + " was selected",
				Toast.LENGTH_SHORT);
		selectItem(position);
	}

	public void selectItem(int position) {
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setItemChecked(position, true);
		setTitle(socialSites[position]);
		//setTitle(planets[position]);
	}

	public void setTitle(String title) {
		getSupportActionBar().setTitle(title);
	}
}

// class to avoid accumulation of toast
class SingleToast {
	private static Toast mToast;

	public static void show(Context context, String text, int duration) {
		if (mToast != null)
			mToast.cancel();
		mToast = Toast.makeText(context, text, duration);
		mToast.show();
	}

}

class MyAdapter extends BaseAdapter{
	private Context context;
	String[] socialSites;
	int[] images = {R.drawable.ic_facebook, R.drawable.ic_facebook, 
			R.drawable.ic_facebook, R.drawable.ic_facebook, R.drawable.ic_facebook};
	
	public MyAdapter(Context context){
		this.context=context;
		socialSites=context.getResources().getStringArray(R.array.social);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return socialSites.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return socialSites[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null;
		// TODO Auto-generated method stub
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.custom_row, parent, false);
		}else{
			row = convertView;
		}
		TextView titleTextView = (TextView) row.findViewById(R.id.textView1);
		ImageView titleImageView = (ImageView) row.findViewById(R.id.imageView1);
		titleTextView.setText(socialSites[position]);
		titleImageView.setImageResource(images[position]);
		
		return row;
	}
	
}