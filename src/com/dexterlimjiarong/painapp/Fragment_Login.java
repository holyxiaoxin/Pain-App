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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment_Login extends Fragment implements OnClickListener{
	
	EditText mEditEmail;
	EditText mEditPassword;
	Button loginButton;
	
	InputStream is = null;
	String jsonString = "";
	
	final static String URL = "http://jiarong.me/android_login_api/";
	final static String LOGIN_TAG = "login";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_login, container,
	              false);
		
		
		mEditEmail   = (EditText) view.findViewById(R.id.editLoginEmail);
		mEditPassword   = (EditText) view.findViewById(R.id.editLoginPassword);
		
		loginButton = (Button) view.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton:
				String email = mEditEmail.getText().toString();
				String password = mEditPassword.getText().toString();
				
				String stringResponse = sendJson(email,password);
				Log.d("stringResponse", stringResponse);
		}
		
	}
	
	protected String sendJson(final String email, final String pwd) {
		
		String jsonString = null;
		HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        InputStream is = null;

        try {
            HttpPost post = new HttpPost(URL);
            
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("tag", LOGIN_TAG));
            Log.d("EMAIL: ", email);
            Log.d("PASSWORD: ", pwd);
            postParameters.add(new BasicNameValuePair("email", email));
            postParameters.add(new BasicNameValuePair("password", pwd));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);    
            
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("tag", LOGIN_TAG));
//    		params.add(new BasicNameValuePair("email", email));
//    		params.add(new BasicNameValuePair("password", pwd));
//    		post.setEntity(new UrlEncodedFormEntity(params));
            
//            jsonObject.put("tag", LOGIN_TAG);
//            jsonObject.put("email", email);
//            jsonObject.put("password", pwd);
//            StringEntity se = new StringEntity( jsonObject.toString());  
//            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(formEntity);
            
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
            
            response = client.execute(post);

            /*Checking response */
            if(response!=null){
                is = response.getEntity().getContent(); //Get the data in the entity
            }

        } catch(Exception e) {
            e.printStackTrace();
            Log.e("Connection Error", e.toString());
            Log.d("Error", "Cannot Estabilish Connection");
        }
        
        try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			jsonString = sb.toString();
			Log.e("JSON", jsonString);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
        
        return jsonString;
	}
}
