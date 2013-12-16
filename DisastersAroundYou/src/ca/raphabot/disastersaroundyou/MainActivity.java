package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements ListDisastersFragment.Communicator{

	private static String KEY_TITLES = "TitlesKey";
	
	ArrayList<Disaster> disasters = new ArrayList<Disaster>();
	ArrayList<String> disastersTitles = new ArrayList<String>();
	ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
	ArrayList<CircleOptions> circles = new ArrayList<CircleOptions>();
	GoogleMap googleMap;
	
	FragmentManager manager;
	ListDisastersFragment listFragment;
	MapFragment mapFragment;
	
	//Create the action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	//Define the actions for the action bar buttons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			Intent intent = new Intent(this, AddDisasterActivity.class);
			intent.putExtra("teste", "teste");
			startActivity(intent);

		default:
			break;
		}

		return true;
	} 

	
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
		if (mapFragment != null){
			googleMap = mapFragment.getMap();
		}
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
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(disaster.getLat(), disaster.getLng())).zoom(10).build();
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
