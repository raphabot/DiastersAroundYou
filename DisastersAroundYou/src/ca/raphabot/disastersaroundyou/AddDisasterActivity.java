package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddDisasterActivity extends Activity {

	String description;
	int radius;
	int type;
	Activity thisActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_disaster);
		
		//Set button action
		Button addButton = (Button) findViewById(R.id.add_button);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDisaster();
			}
		});
		
		//Config Spinner (DropDown)
		configSpinner();
			
		
	}
	
	public void configSpinner(){
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		List<String> optionsList = new ArrayList<String>();
		optionsList.add("Flood");
		optionsList.add("Blizzard");
		optionsList.add("Other");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_dropdown_item, optionsList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(dataAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}
	
	public void addDisaster(){
		EditText editableDescription = (EditText) findViewById(R.id.editable_disaster_description);
		description = editableDescription.getText().toString();
		if (editableDescription.getText().toString().matches(""))
			description = "No description available for this disaster.";
		else
			description = editableDescription.getText().toString();
		
		EditText editableRadius = (EditText) findViewById(R.id.editable_radius);
		if (editableRadius.getText().toString().matches(""))
			radius = 0;
		else
			radius = Integer.parseInt(editableRadius.getText().toString());
		//Spinner spinner = (Spinner)findViewById(R.id.spinner_type);
		//type = spinner.getSelectedItemPosition();
		
		//Get the data
		DoPost mDoPost = new DoPost(this,"-1");
		mDoPost.execute("");
	}
	
	public class DoPost extends AsyncTask<String, Void, Boolean>{

		Context mContext = null;
		Exception exception = null;

		public DoPost(Context context, String nameToSearch){
			mContext = context;
			//strNameToSearch = nameToSearch;
		}

		@Override
		protected Boolean doInBackground(String... arg0) {

			try{

				//Setup the parameters
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("Description", description));
				nameValuePairs.add(new BasicNameValuePair("Radio", String.valueOf(radius)));
				nameValuePairs.add(new BasicNameValuePair("Type", String.valueOf(type)));
				
				//Add more parameters as necessary

				//Create the HTTP request
				HttpParams httpParameters = new BasicHttpParams();

				//Setup timeouts
				HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
				HttpConnectionParams.setSoTimeout(httpParameters, 15000);                        

				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost("http://raphabot.zxq.net/json.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				String result = EntityUtils.toString(entity);

				// Create a JSON object from the request response
				JSONObject jsonObject = new JSONObject(result);
				Log.e("JOSN", jsonObject.toString());


			}catch (Exception e){
				Log.e("ClientServerDemo", "Error:", e);
				exception = e;
			}

			return true;
		}

		
	    @Override
	    protected void onPostExecute(Boolean valid){
	    	/*
	            //Update the UI
	            textViewFirstName.setText("First Name: " + strFirstName);
	            textViewLastName.setText("Last Name: " + strLastName);
	            textViewAge.setText("Age: " + intAge);
	            textViewPoints.setText("Points: " + intPoints);        

	            buttonGetData.setEnabled(true);

	            if(exception != null){
	                    Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
	            }
	            */
	    	Intent intent = new Intent(thisActivity, MainActivity.class);
			intent.putExtra("teste", "teste");
			startActivity(intent);
	    }

	}

}
