package com.dexterlimjiarong.painapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FragmentPqasQ3   extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "iconResourceID";
      public static final String ITEM_NAME = "itemName";
      public static final String STRING_ARRAY = "stringArray";
 
      public FragmentPqasQ3(){
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_layout_pqas_q3,container, false);
            //retrieve bundle from previous fragment
            Bundle bundle = getArguments();
            ivIcon=(ImageView)view.findViewById(R.id.frag_pqas_q3_icon);
            tvItemName=(TextView)view.findViewById(R.id.frag_pqas_q3_text);
            //pain scale from previous
            tvItemName.setText(bundle.getStringArray(STRING_ARRAY)[0]);
            ivIcon.setImageDrawable(view.getResources().getDrawable(
            		bundle.getInt(IMAGE_RESOURCE_ID)));
            return view;
      }
 
}