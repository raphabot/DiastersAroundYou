package ca.raphabot.disastersaroundyou;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.*;


public class MainActivity extends Activity {

	ListDisastersFragment listFragment;
	ArrayList<String> disastersTitles = new ArrayList<String>();
	// private GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onItemClicked(int position) { 
		//listFragment.showMap(position); 
		try {
            // Loading map
           // initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	} 
	/*
	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
*/
}
