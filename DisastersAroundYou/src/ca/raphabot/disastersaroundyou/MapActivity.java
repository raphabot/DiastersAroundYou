package ca.raphabot.disastersaroundyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		Intent intent = getIntent();
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		if (mapFragment != null){
			googleMap = mapFragment.getMap();
			googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Hello world"));
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
