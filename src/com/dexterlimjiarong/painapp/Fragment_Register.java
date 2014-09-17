package com.dexterlimjiarong.painapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Register extends Fragment implements OnClickListener{
	
	EditText mEditUsername;
	EditText mEditEmail;
	EditText mEditPassword;
	EditText mEditConfirmPassword;
	TextView registerErrorMsg;
	
	WordPressApiFunctions wordPressApiFunctions = new WordPressApiFunctions();
	
	final static String KEY_SUCCESS = "success";
	final static String KEY_ERROR_STRING = "errorstring";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_register, container,
	              false);
		
		
		mEditUsername   = (EditText) view.findViewById(R.id.editRegisterUsername);
		mEditEmail   = (EditText) view.findViewById(R.id.editRegisterEmail);
		mEditPassword   = (EditText) view.findViewById(R.id.editRegisterPassword);
		mEditConfirmPassword   = (EditText) view.findViewById(R.id.editRegisterConfirmPassword);
		registerErrorMsg = (TextView) view.findViewById(R.id.register_error);
		
		Log.d("register view", "register view");
		Button registerButton = (Button) view.findViewById(R.id.registerButton);
		registerButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.registerButton:{
				String username = mEditUsername.getText().toString();
				String email = mEditEmail.getText().toString();
				String password = mEditPassword.getText().toString();
				String confirmPassword = mEditConfirmPassword.getText().toString();
				
				Log.e("base username" , username);
				Log.e("base email" , email);
				Log.e("base password" , password);
				Log.e("base confirmPassword" , confirmPassword);
				
				//make sure that fields are valid before sending to API
				if(!password.equals(confirmPassword)){
					registerErrorMsg.setText("Passwords do not match.");
					break;
				}
								
				JSONObject json = wordPressApiFunctions.registerUser(username, password, email);
				
				try{
					if (json.getString(KEY_SUCCESS) != null) {
						registerErrorMsg.setText("");
						if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1){	//SUCCESS
							Toast.makeText(view.getContext(),"SUCCESS!", 
									Toast.LENGTH_SHORT).show();
						}else{	//NOT SUCCESS
							String error_string = json.getString(KEY_ERROR_STRING);
							registerErrorMsg.setText(Html.fromHtml(error_string));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
			default:
				Log.e("Button Error","button id not recorded in switch case");
		}
	}
	
}
