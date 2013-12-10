package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DoPost extends AsyncTask<String, Void, Boolean>{

	Context mContext = null;
	String strNameToSearch = "";

	//Result data
	String strFirstName;
	String strLastName;
	int intAge;
	int intPoints;

	Exception exception = null;

	public DoPost(Context context, String nameToSearch){
		mContext = context;
		strNameToSearch = nameToSearch;
	}

	@Override
	protected Boolean doInBackground(String... arg0) {

		try{

			//Setup the parameters
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id", "-1"));        
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

			/*
                    //Retrieve the data from the JSON object
                    strFirstName = jsonObject.getString("FirstName");
                    strLastName = jsonObject.getString("LastName");
                    intAge = jsonObject.getInt("Age");
                    intPoints = jsonObject.getInt("Points");
			 */

		}catch (Exception e){
			Log.e("ClientServerDemo", "Error:", e);
			exception = e;
		}

		return true;
	}

	/*
    @Override
    protected void onPostExecute(Boolean valid){
            //Update the UI
            textViewFirstName.setText("First Name: " + strFirstName);
            textViewLastName.setText("Last Name: " + strLastName);
            textViewAge.setText("Age: " + intAge);
            textViewPoints.setText("Points: " + intPoints);        

            buttonGetData.setEnabled(true);

            if(exception != null){
                    Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
    }*/

}
