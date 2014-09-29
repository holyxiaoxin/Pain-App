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

import android.os.StrictMode;
import android.util.Log;

public class WordPressApiFunctions {
	
	final static String API_USER_URL = "http://jiarong.me/painapp/api/user.php";
	final static String API_POST_URL = "http://jiarong.me/painapp/api/post.php";
	final static String LOGIN_TAG = "login";
	final static String REGISTER_TAG = "register";
	final static String GET_POSTS_TAG = "get";
	
	public JSONObject loginUser(String username, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", LOGIN_TAG));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = getJSONFromUrl(API_USER_URL, params);

		return json;
	}
	
	public JSONObject registerUser(String username, String password ,String email){
		// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("tag", REGISTER_TAG));
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				params.add(new BasicNameValuePair("email", email));
				JSONObject json = getJSONFromUrl(API_USER_URL, params);
				Log.d("register tag",REGISTER_TAG);
				Log.d("username",username);
				Log.d("password",password);
				Log.d("email",email);
				Log.d("URL", API_USER_URL);
		
		return json;
	}
    
	public JSONObject getPosts(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", GET_POSTS_TAG));
		JSONObject json = getJSONFromUrl(API_POST_URL, params);

		return json;
	}
	
	private JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
		
		String jsonString = null;
		JSONObject json = null;
		HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        InputStream is = null;
        
        Log.d("getJSONFromUrl", "getJSONFromUrl");

        try {
            HttpPost post = new HttpPost(url);
            
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);    
            
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
        Log.d("stringResponse", jsonString);
     // try parse the string to a JSON object
     		try {
     			json = new JSONObject(jsonString);			
     		} catch (JSONException e) {
     			Log.e("JSON Parser", "Error parsing data " + e.toString());
     		}

        return json;
	}

}
