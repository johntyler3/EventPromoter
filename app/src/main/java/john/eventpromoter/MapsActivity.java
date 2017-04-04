package john.eventpromoter;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private HashSet<Event> mEventSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mEventSet = (HashSet) getIntent().getSerializableExtra("EventSet");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        for (Event e : mEventSet) {
            // TODO: 4/3/17 make sure events don't stack on eachother 
            BuildingCodeLocationEnum building = BuildingCodeLocationEnum.valueOf(e.getBuildingCode().substring(0,3));
            double[] gps = building.getGPS();
            mMap.addMarker(new MarkerOptions().position(new LatLng(gps[0],gps[1])).title(e.getEventName()));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(30.286103, -97.739362)));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 3.0f ) );

    }
}
