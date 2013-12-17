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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListDisastersFragment extends Fragment implements AdapterView.OnItemClickListener{


	ArrayList<String> disastersTitles = new ArrayList<String>(1);
	ListView listView = null;
	ArrayAdapter<String> adapter;
	ArrayList<Disaster> disasters;
	ArrayList<MarkerOptions> markers;
	ArrayList<CircleOptions> circles;
	GoogleMap googleMap;

	Activity myActivity;
	Exception exception;
	
	Communicator comm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.list_disasters, container, false); 
		listView = (ListView) view.findViewById(R.id.listDisasters); 
		
		myActivity = getActivity(); 
		disastersTitles = ((MainActivity) myActivity).disastersTitles; 
		//disastersTitles.add("teste");
		disasters = ((MainActivity)myActivity).disasters;
		markers = ((MainActivity)myActivity).markers;
		circles = ((MainActivity)myActivity).circles;
		//googleMap = ((MainActivity)myActivity).mapFragment.getMap();
		
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(this); 

		adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_list_item_1, disastersTitles); 

		// adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disasters);



		//Get the data
		DoGet mDoGet = new DoGet(getActivity(),"-1");
		mDoGet.execute("");
		
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
					disastersTitles.add(disaster.getDescription().substring(0, Math.min(40, disaster.getDescription().length())));
					disasters.add(disaster);
					String title = "";
					switch(disaster.getType()){
						case '0':
							title = "Type: Flood";
							break;
						case '1':
							title = "Type: Blizzard";
							break;
						default:
							title = "Type: Other";
							break;
					}
					String snippet = "Description: " + disaster.getDescription();
					markers.add(new MarkerOptions().position(new LatLng(disaster.getLat(), disaster.getLng())).title(title).snippet(snippet));
					circles.add(new CircleOptions().center(new LatLng(disaster.getLat(), disaster.getLng())).radius(disaster.getRadio()).strokeColor(Color.RED).fillColor(Color.TRANSPARENT));
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
			
			//Update maps
			googleMap = ((MainActivity)myActivity).googleMap;
			if (googleMap != null){
				for (int i = 0; i < markers.size(); i++){
					googleMap.addMarker(markers.get(i));
					googleMap.addCircle(circles.get(i));
					
				}
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


	
	public void setCommunicator (Communicator c){	
		this.comm = c;
	}
	
	
	public interface Communicator{
		public void respond(int index);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		String value = String.valueOf(i);
		Log.d("DEBUG",value);
		comm.respond(i);
	}

	
	
}
