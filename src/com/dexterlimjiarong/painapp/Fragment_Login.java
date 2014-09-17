package com.dexterlimjiarong.painapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Login extends Fragment implements OnClickListener{
	
	EditText mEditUsername;
	EditText mEditPassword;
	TextView loginErrorMsg;
	Button loginButton;
	
	WordPressApiFunctions wordPressApiFunctions = new WordPressApiFunctions();
	
	final static String LOGIN_URL = "http://jiarong.me/painapp/api/login-api.php";
	final static String REGISTER_URL = "http://jiarong.me/painapp/api/register-api.php";
	final static String LOGIN_TAG = "login";
	final static String KEY_SUCCESS = "success";
	final static String KEY_ERROR_STRING = "errorstring";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_login, container,
	              false);
		
		
		mEditUsername   = (EditText) view.findViewById(R.id.editLoginUsername);
		mEditPassword   = (EditText) view.findViewById(R.id.editLoginPassword);
		loginErrorMsg = (TextView) view.findViewById(R.id.login_error);
		
		loginButton = (Button) view.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton:
				String username = mEditUsername.getText().toString();
				String password = mEditPassword.getText().toString();
				
				JSONObject json = wordPressApiFunctions.loginUser(username,password);
				try{
					if (json.getString(KEY_SUCCESS) != null) {
						loginErrorMsg.setText("");
						if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1){	//SUCCESS
							Toast.makeText(view.getContext(),"SUCCESS!", 
									Toast.LENGTH_SHORT).show();
						}else{
							String error_string = json.getString(KEY_ERROR_STRING);
							String formatted_error_code = Html.fromHtml(error_string).toString();
							loginErrorMsg.setText(Html.fromHtml(error_string));
							Toast.makeText(view.getContext(),formatted_error_code, 
									Toast.LENGTH_SHORT).show();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
		}
		
	}
    
    
}
