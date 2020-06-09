package com.example.pavan.annadhaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnSubmit;
    TextView tvNewUser;
    EditText etMail,etPassword;
    String mail,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword1);
        tvNewUser = (TextView) findViewById(R.id.tvNewUser);

        mail = etMail.getText().toString();
        password = etPassword.getText().toString();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataToServer(mail, password);
                Intent i = new Intent(LoginActivity.this, BActivity.class);
                startActivity(i);
                finish();
            }
        });


        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    public void SendDataToServer(final String mail, final String pswd){


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.log_url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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
                params.put("v_email",mail);
                params.put("v_password",pswd);
                return params;
            }

        };

        queue.add(jsonObjRequest);
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
    }//end of Send Data to server
}
