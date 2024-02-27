package com.example.chayo.foot_out;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class anuncios extends AppCompatActivity {

    String url="http:192.168.0.140:85/proyecto_app/egresados.php";
String [] empresa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);



    consulta();


    }
   private void consulta( ) {
        final StringRequest busca=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray datos = new JSONArray(response);
                    String a=datos.getString(0);
                    String b=datos.getString(1);
                    String c=datos.getString(2);
                    Toast.makeText(anuncios.this,a+"\n"+b+"\n"+c,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );



   }

   }
