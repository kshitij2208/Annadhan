package com.example.pavan.annadhaan;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    Button btnLoc;
    TextView tvLoc,tvSkip;
    GoogleApiClient gac;
    Button btnShare;
    EditText etNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        btnLoc = (Button) findViewById(R.id.btnLoc);
        tvLoc = (TextView) findViewById(R.id.tvLoc);
        btnShare=(Button)findViewById(R.id.btnShare);
        etNeed=(EditText)findViewById(R.id.etNeed);
        tvSkip=(TextView)findViewById(R.id.tvSkip);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        gac = builder.build();

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Loc=tvLoc.getText().toString();
                String ppl=etNeed.getText().toString();
                Log.v("hello",Loc);

                SendDataToServer(Loc,ppl);
                Intent i=new Intent(BActivity.this,DonorActivity.class);
                startActivity(i);

            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BActivity.this,DonorActivity.class);
                startActivity(i);
            }
        });


    }//oncreate

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");

        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();;
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("EXIT");
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (gac != null) {
            gac.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (gac != null) {
            gac.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc!=null)
        {
            Geocoder g=new Geocoder(this, Locale.ENGLISH);
            try {

                List<Address> ad= g.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                Address address=ad.get(0);
                String msg=address.getCountryName()+" "+address.getAdminArea()+" "+address.getSubAdminArea()+address.getLocality()+" "+address.getSubLocality()+" "+address.getThoroughfare()+" "+address.getSubThoroughfare()+" "+address.getPostalCode();
                tvLoc.setText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public void SendDataToServer(final String loc, final String ppl){


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.loc_url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("location",loc);
                params.put("no_of_people",ppl);


                return params;
            }

        };

        queue.add(jsonObjRequest);
        Toast.makeText(this, "Shared successfully", Toast.LENGTH_SHORT).show();


    }//end of Send Data to server
}
