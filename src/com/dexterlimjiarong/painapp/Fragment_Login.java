package com.dexterlimjiarong.painapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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
	
	WordPressApiFunctions wordPressApiFunctions = new WordPressApiFunctions();
	
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
		loginErrorMsg.setText("");	//clear error message at the start
		
		Button loginButton = (Button) view.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		Button registerButton = (Button) view.findViewById(R.id.registerButton);
		registerButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton:{
//				String username = mEditUsername.getText().toString();
//				String password = mEditPassword.getText().toString();
				//uses the wordpress api to login
				new LoginAsyncTask(view.getContext()).execute();
				
//				JSONObject json = wordPressApiFunctions.loginUser(username,password);
//				try{
//					if (json.getString(KEY_SUCCESS) != null) {
//						if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1){	//SUCCESS
//							loginErrorMsg.setText("");	//clear error message if successful
//							Toast.makeText(view.getContext(),"SUCCESS!", 
//									Toast.LENGTH_SHORT).show();
//						}else{	//NOT SUCCESS
//							String error_string = json.getString(KEY_ERROR_STRING);
//							loginErrorMsg.setText(Html.fromHtml(error_string));
//						}
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
				break;
			}
			case R.id.registerButton:{
				Log.d("debug","register LINK pressed");
				//switch page to register button
				FragmentManager frgManager = getFragmentManager();
		        frgManager.beginTransaction().replace(R.id.content_frame, new Fragment_Register()).addToBackStack(null).commit();
				break;
			}
			default:
				Log.e("Button Error","button id not recorded in switch case");
		}
	}
	
	class LoginAsyncTask extends AsyncTask<Void, Void, JSONObject> {
		private Context mContext;
		public LoginAsyncTask (Context context){
	         mContext = context;
	    }
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		// Login from workPress
		@Override
		protected JSONObject doInBackground(Void... params) {
			String username = mEditUsername.getText().toString();
			String password = mEditPassword.getText().toString();
			JSONObject json = wordPressApiFunctions.loginUser(username,password);
			return json;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(JSONObject json) {
			try{
				if (json.getString(KEY_SUCCESS) != null) {
					if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1){	//SUCCESS
						loginErrorMsg.setText("");	//clear error message if successful
						Toast.makeText(mContext,"SUCCESS!", 
								Toast.LENGTH_SHORT).show();
					}else{	//NOT SUCCESS
						String error_string = json.getString(KEY_ERROR_STRING);
						loginErrorMsg.setText(Html.fromHtml(error_string));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
    
    
}
