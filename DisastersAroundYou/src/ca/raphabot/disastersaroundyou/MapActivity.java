package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	public static String KEY_TITLES = "TitlesKey";
	public static String MARKERS = "MarkersKey";
	public static String DISASTERS = "DisastersKey";

	GoogleMap googleMap;
	ArrayList<Disaster> disasters;// = new ArrayList<Disaster>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		Intent intent = getIntent();
		disasters = intent.getParcelableArrayListExtra(DISASTERS);

		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		if (mapFragment != null){
			if (googleMap != null){
				for (int i = 0; i < disasters.size(); i++){
					Disaster disaster = disasters.get(i);

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
					googleMap.addMarker((new MarkerOptions().position(new LatLng(disaster.getLat(), disaster.getLng())).title(title).snippet(snippet)));
					googleMap.addCircle(new CircleOptions().center(new LatLng(disaster.getLat(), disaster.getLng())).radius(disaster.getRadio()).strokeColor(Color.RED).fillColor(Color.TRANSPARENT));
				}


			}
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

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

}
