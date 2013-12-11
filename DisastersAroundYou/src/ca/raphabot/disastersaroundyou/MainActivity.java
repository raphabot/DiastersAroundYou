package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class MainActivity extends Activity {

	ListDisastersFragment listFragment;
	ArrayList<String> disastersTitles = new ArrayList<String>();
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		//      finish();
		//      return;
		 //   }
		
		setContentView(R.layout.activity_main);
	}

	public void onItemClicked(int position) { 
		//listFragment.showMap(position); 
		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
