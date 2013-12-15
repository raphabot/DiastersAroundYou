package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements ListDisastersFragment.Communicator{

	private static String KEY_TITLES = "TitlesKey";
	
	ArrayList<Disaster> disasters = new ArrayList<Disaster>();
	ArrayList<String> disastersTitles = new ArrayList<String>();
	ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
	GoogleMap googleMap;
	
	FragmentManager manager;
	ListDisastersFragment listFragment;
	MapFragment mapFragment;

	
	public void onSaveInstanceState(Bundle savedState) {

	    super.onSaveInstanceState(savedState);

	    savedState.putStringArrayList(KEY_TITLES, disastersTitles);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manager = getFragmentManager();
		
		//Load listDisasters Fragment
		listFragment = (ListDisastersFragment) manager.findFragmentById(R.id.list_fragment);
		listFragment.setCommunicator(this);
		mapFragment = (MapFragment) manager.findFragmentById(R.id.map);
		Log.d("DEBUG","wait for it...");
		
		
		
		/*
		if (savedInstanceState != null) {
			disastersTitles = savedInstanceState.getStringArrayList(KEY_TITLES);
	        if (disastersTitles != null) {
	        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disastersTitles);
	        	adapter.notifyDataSetChanged();
	        }
	    }*/
		
		//disastersTitles.add("title");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disastersTitles);
    	adapter.notifyDataSetChanged();
		
		/*
		// Loading map
		Log.d("Maps","Loading");
		initilizeMap();
		*/
		
		
	}

	/*
	public void onItemClicked(int position) { 
		//listFragment.showMap(position); 
		Log.d("CLICKED!", "position: "+position);
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
			googleMap.addMarker(new MarkerOptions()
	        .position(new LatLng(0, 0))
	        .title("Hello world"));

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void onMapClicked(int position) {
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(17.385044, 78.486671)).zoom(12).build();
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}*/


	@Override
	public void respond(int index) {
		Disaster disaster = disasters.get(index);
		Log.d("DEBUG","Running respond");
		mapFragment = (MapFragment) manager.findFragmentById(R.id.map);
		if ((mapFragment != null) &&  (mapFragment.isVisible())){
			//Landscape orientation
			//Do whatever is necessary to the map
			Log.d("ORIENTATION","Landscape");
			googleMap = mapFragment.getMap();
			for (int i = 0; i < markers.size(); i++){
				googleMap.addMarker(markers.get(i));
			}
			//googleMap.addMarker(new MarkerOptions().position(new LatLng(disaster.getLat(), disaster.getLng())).title(disaster.getDescription()));
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(disaster.getLat(), disaster.getLng())).zoom(7).build();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
		else{
			//Portrait orientation
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra("teste", "teste");
			startActivity(intent);
		}
		
	}

}
