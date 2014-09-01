//package com.dexterlimjiarong.painapp;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
// 
//public class FragmentPqasQ2   extends Fragment {
// 
//      ImageView ivIcon;
//      TextView tvItemName;
// 
//      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
//      public static final String ITEM_NAME = "itemName";
//      public static final String STRING_ARRAY = "stringArray";
// 
//      public FragmentPqasQ2(){
//      }
// 
//      @Override
//      public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                  Bundle savedInstanceState) {
//            View view=inflater.inflate(R.layout.fragment_layout_pqas_q2,container, false);
//            //retrieve bundle from previous fragment
//            Bundle bundle = getArguments();
//            ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q2_icon);
//            tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q2_text);
//            //pain scale from previous
//            tvItemName.setText(bundle.getStringArray(STRING_ARRAY)[0]);
//            ivIcon.setImageDrawable(view.getResources().getDrawable(
//            		bundle.getInt(IMAGE_RESOURCE_ID)));
//            return view;
//      }
// 
//}

package com.dexterlimjiarong.painapp;

import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPqasQ2 extends Fragment 
	implements OnClickListener
	{

	private SeekBar volumeControl = null;
	ImageView ivIcon;
    TextView tvItemName;

    
	  public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	  public static final String ITEM_NAME = "itemName";
	  public static final String STRING_ARRAY = "stringArray";
      int pain = 0;

    public FragmentPqasQ2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

          View view = inflater.inflate(R.layout.fragment_layout_pqas_q2, container,
                      false);
        //retrieve bundle from previous fragment
        Bundle bundle = getArguments();
        ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q2_icon);
        tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q2_text);
        //pain scale from previous
        tvItemName.setText(bundle.getStringArray(STRING_ARRAY)[0]);
        ivIcon.setImageDrawable(view.getResources().getDrawable(
        		bundle.getInt(IMAGE_RESOURCE_ID)));
          
          
          volumeControl = (SeekBar) view.findViewById(R.id.volume_bar);
          
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
			
          Button nextButton = (Button) view.findViewById(R.id.button_pqas_q2_next);
          nextButton.setOnClickListener(this);
          Button backButton = (Button) view.findViewById(R.id.button_pqas_q2_back);
          backButton.setOnClickListener(this);
          
          return view;
    }
    
    @Override
    public void onClick(View view) {
    	switch (view.getId()) {
        case R.id.button_pqas_q2_next:
        	Bundle bundle = getArguments();
	        Fragment fragment = new FragmentPqasQ3();
	        //pain slider stored
	        String[] questionAnswers1 = bundle.getStringArray(STRING_ARRAY);
	        //adds pain element to string array
	        List<String> listArray = Arrays.asList(questionAnswers1);  	//temp list array
	        listArray.add(Integer.toString(pain));
	        String[] questionAnswers2 = new String[listArray.size()];
	        questionAnswers2 = listArray.toArray(questionAnswers2);
	        //bundle is sent to next fragment
	        bundle.putStringArray(FragmentPqasQ3.STRING_ARRAY, questionAnswers2);
	        fragment.setArguments(bundle);
	        FragmentManager frgManager = getFragmentManager();
	        
	        getFragmentManager().beginTransaction().add(fragment, "pqas_q1")
            // Add this transaction to the back stack
            .addToBackStack("pqas_q1").commit();
	      	
	        //replace fragment
	        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	        
	        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "tag" ).commit();
	        
	        
	        
	        break;
        case R.id.button_pqas_q2_back:
        	getFragmentManager().addOnBackStackChangedListener(
	                new FragmentManager.OnBackStackChangedListener() {
	                    public void onBackStackChanged() {
	                        // Update your UI here.
//	                    	Bundle args = getArguments();
//	            	        Fragment fragment = new FragmentPqasQ1();
//	            	        //set bundle to fragment
//	            	        fragment.setArguments(args);
//	            	        FragmentManager frgManager = getFragmentManager();
//	            	        getFragmentManager().beginTransaction().add(fragment, "pqas_q1")
//	                        // Add this transaction to the back stack
//	                        .addToBackStack("pqas_q1").commit();
//	            	        //replace fragment
//	            	        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	                    	
	                    	Fragment fragment = new FragmentPqasQ1();
	                    	fragment.getView().setFocusableInTouchMode(true);

	                    	fragment.getView().setOnKeyListener( new OnKeyListener()
	                    	{
	                    	    @Override
	                    	    public boolean onKey( View v, int keyCode, KeyEvent event )
	                    	    {
	                    	        if( keyCode == KeyEvent.KEYCODE_BACK )
	                    	        {
	                    	            return true;
	                    	        }
	                    	        return false;
	                    	    }
	                    	} );
	                    }
	        });
        	break;
    	} 

    }
    
    
}
