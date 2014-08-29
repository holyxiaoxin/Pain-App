package com.dexterlimjiarong.painapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class FragmentSeekBar extends Fragment{
	
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
	 
	private SeekBar volumeControl = null;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.activity_seekbar, container,
                      false);

          
         	volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          
  			volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	  			int progressChanged = 0;
	   
	  			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
	  				progressChanged = progress;
	  			}
	   
	  			public void onStartTrackingTouch(SeekBar seekBar) {
	  				// TODO Auto-generated method stub
	  			}
	   
	  			public void onStopTrackingTouch(SeekBar seekBar) {
	  				Toast.makeText(seekBar.getContext(),"seek bar progress:"+progressChanged, 
	  						Toast.LENGTH_SHORT).show();
	  			}
  			});
          
          return view;
    }
}
