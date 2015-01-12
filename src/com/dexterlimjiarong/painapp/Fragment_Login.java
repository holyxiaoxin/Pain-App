package com.dexterlimjiarong.painapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
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
				//uses the wordpress api to login
				new LoginAsyncTask(view.getContext()).execute();
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
	
	/**
	 * AsyncTask for Login
	 */
	private class LoginAsyncTask extends AsyncTask<Void, Void, JSONObject> {
		private Context mContext;
		private ProgressDialog loadingDialog;
		
		public LoginAsyncTask (Context context){
	         mContext = context;
	         loadingDialog = new ProgressDialog((Activity) context);
	    }
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingDialog.setMessage("Logging in, please wait.");
			loadingDialog.setCancelable(false);
			loadingDialog.show();
		}
		@Override
		protected JSONObject doInBackground(Void... params) {
			String username = mEditUsername.getText().toString();
			String password = mEditPassword.getText().toString();
			JSONObject json = wordPressApiFunctions.loginUser(username,password);
			
			//testing
			wordPressApiFunctions.testAPI("jiarong", "jiarong");
			
			return json;
		}
		@Override
		protected void onPostExecute(JSONObject json) {
			if (loadingDialog.isShowing()) {
				loadingDialog.dismiss();
	        }
			try{
				if (json.getString(KEY_SUCCESS) != null) {
					if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1){	//SUCCESS
						loginErrorMsg.setText("");	//clear error message if successful
						Toast.makeText(mContext,"You are now logged in.", 
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
