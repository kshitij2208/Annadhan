package com.example.pavan.annadhaan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import  java.net.*;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    EditText etFN, etLN, etMail, etPhone, etPassword, etAddress;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFN = (EditText) findViewById(R.id.etFN);
        etLN = (EditText) findViewById(R.id.etLN);
        etMail = (EditText) findViewById(R.id.etMail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAddress = (EditText) findViewById(R.id.etAddress);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FN = etFN.getText().toString();
                String LN = etLN.getText().toString();
                String Mail = etMail.getText().toString();
                String Phone = etPhone.getText().toString();
                String Password = etPassword.getText().toString();
                String Address = etAddress.getText().toString();



                SendDataToServer(FN,LN,Mail,Phone,Password,Address);


            }
        });
    }   //oncraete ends


    public void SendDataToServer(final String FN, final String LN, final String Mail, final String Phone, final String Password, final String Address){
        /*class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String ... params) throws   {

                String urlParameters  = "d_firstname="+FN+"&d_secondname="+LN+"&d_email="+Mail+"&d_contact="+Phone+"&d_address="+Address+"d_password"+Password;
                byte[] postData = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                }
                int postDataLength = postData.length;
                String request = "<Url here>";

                URL url = null;
                try {
                    url = new URL(request);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn= null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                try {
                    conn.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
                conn.setUseCaches(false);
                try {

                    DataOutputStream wr = null;
                    try {
                        wr = new DataOutputStream(conn.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    wr.write( postData );
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.base_url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
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
            params.put("d_id","5");
            params.put("d_firstname",FN);
            params.put("d_secondname",LN);
            params.put("d_email",Mail);
            params.put("d_contact",Phone);
            params.put("d_address",Address);
            params.put("d_password",Password);

            return params;
        }

    };

queue.add(jsonObjRequest);
        Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();

        Intent i=new Intent(MainActivity.this,BActivity.class);
        startActivity(i);
    }//end of Send Data to server

}




