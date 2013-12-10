package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListDisastersFragment extends Fragment{


	ArrayList<String> disastersTitles = new ArrayList<String>(1);
	ListView listView = null;
	ArrayAdapter<String> adapter = null;

	Activity myActivity;
	Exception exception;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view; 
		view = inflater.inflate(R.layout.list_disasters, container, false); 
		myActivity = getActivity(); 
		disastersTitles = ((MainActivity) myActivity).disastersTitles; 
		disastersTitles.add("teste");
		adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_list_item_1, disastersTitles); 

		listView = (ListView) view.findViewById(R.id.listDisasters); 
		listView.setAdapter(adapter); 
		//listView.setOnItemClickListener(sightingSelected); 


		// adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disasters);



		//Get the data
		DoGet mDoGet = new DoGet(getActivity(),"-1");
		mDoGet.execute("");
		/*
		try{
			//Create the HTTP request
			HttpParams httpParameters = new BasicHttpParams();
	
			//Setup timeouts
			HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
			HttpConnectionParams.setSoTimeout(httpParameters, 15000);                        
	
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			String url = "http://raphabot.zxq.net/json.php";
			url = addParam(url, "id", "-1"); //Retrieve all Disasters
			HttpGet httpget = new HttpGet(url);      
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			// Create a JSON object from the request response
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++){
				Log.d("JOSN", jsonArray.get(i).toString());
				adapter.add(jsonArray.get(i).toString());
			}
		}catch (Exception e){
			Log.e("Parse Error", "Error:", e);
			exception = e;
		}
		
		*/
		return view; 

	}


	public class DoGet extends AsyncTask<String, Void, Boolean>{

		Context mContext = null;
		String strNameToSearch = "";

		//Result data
		String strFirstName;
		String strLastName;
		int intAge;
		int intPoints;
		JSONArray jsonArray = null;

		Exception exception = null;

		public DoGet(Context context, String nameToSearch){
			mContext = context;
			strNameToSearch = nameToSearch;
		}

		@Override
		protected Boolean doInBackground(String... arg0) {

			try{

				//Create the HTTP request
				HttpParams httpParameters = new BasicHttpParams();

				//Setup timeouts
				HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
				HttpConnectionParams.setSoTimeout(httpParameters, 15000);                        

				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				String url = "http://raphabot.zxq.net/json.php";
				url = addParam(url, "id", "-1"); //Retrieve all Disasters
				HttpGet httpget = new HttpGet(url);      
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				// Create a JSON object from the request response
				jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++){
					Log.d("JOSN", jsonArray.get(i).toString());
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Disaster disaster= new Disaster(jsonObject.getString("id"),jsonObject.getString("description"),jsonObject.getString("type"),jsonObject.getString("started"),jsonObject.getString("ended"),jsonObject.getString("lat"),jsonObject.getString("lng"),jsonObject.getString("radio"));
					disastersTitles.add(disaster.getDescription());
					//adapter.add(disaster.toString());
				}

			}catch (Exception e){
				Log.e("Parse Error", "Error:", e);
				exception = e;
			}

			return true;
		}


		@Override
		protected void onPostExecute(Boolean valid){

			//Update the UI
			new ArrayAdapter<String>(myActivity, android.R.layout.simple_list_item_1, disastersTitles); 
			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			if(exception != null){
				Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
			}

		}

		protected String addParam(String url, String name, String value){
			if(!url.endsWith("?"))
				url += "?";

			List<NameValuePair> params = new LinkedList<NameValuePair>();

			params.add(new BasicNameValuePair(name, value));

			String paramString = URLEncodedUtils.format(params, "utf-8");

			url += paramString;
			return url;
		}

	}

	public void showMap(int position) {
		// TODO Auto-generated method stub

	}
	

}