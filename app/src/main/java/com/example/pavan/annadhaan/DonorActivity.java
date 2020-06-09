package com.example.pavan.annadhaan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DonorActivity extends AppCompatActivity {

    EditText quantity,package1;
    Button ready;
    EditText location,time;
    ImageView ivUpload;
    Bitmap bm;
    byte[] data1;
    // private EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        quantity=(EditText)findViewById(R.id.et_quantity);
        package1=(EditText)findViewById(R.id.et_package);

        location=(EditText)findViewById(R.id.et_location);
        time=(EditText)findViewById(R.id.et_time);

        ready=(Button)findViewById(R.id.btn_ready);

        ivUpload=(ImageView)findViewById(R.id.ivUpload);



        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);
            }
        });


    }//oncreate ends

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==100)
        {
            bm=(Bitmap)data.getExtras().get("data");
            ivUpload.setImageBitmap(bm);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();

            bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
            data1=baos.toByteArray();

            ready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Time=time.getText().toString();
                    String Location=location.getText().toString();
                    String Package=package1.getText().toString();
                    String Quantity=quantity.getText().toString();
                    String data = new String(data1);
                    Log.v("hello",data);


                    SendDataToServer(Quantity,Package,Location,data,Time);
                }
            });


        }
    }

    public void SendDataToServer(final String Quantity, final String Package, final String Location, final String data, final String time)
    {


    RequestQueue queue = Volley.newRequestQueue(this);


    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
            getResources().getString(R.string.donor_url),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
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
            params.put("id","9");
            params.put("quantity",Quantity);
            params.put("packaging",Package);
            params.put("location",Location);
            params.put("picture",data);
            params.put("pickup_time",time);

            return params;
        }

    };

    queue.add(jsonObjRequest);
    Toast.makeText(this, "Thank you for Donation!", Toast.LENGTH_SHORT).show();

}//end of Send Data to server


}
