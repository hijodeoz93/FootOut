package com.example.chayo.foot_out;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataEst extends AppCompatActivity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personales);
    }
    Button btn;
    EditText email,cel,direccionEs,otroSS, ciudadEst, Nss;
    RadioGroup rGrup;
    Spinner Empresas;
    String radio;
    public void relacionaVistas(){
        email=(EditText) findViewById(R.id.emailEst);
        cel=(EditText) findViewById(R.id.celEst);
        direccionEs=(EditText) findViewById(R.id.direccEst);
        otroSS=(EditText) findViewById(R.id.OT_NSS);
        ciudadEst=(EditText) findViewById(R.id.CityEst);
        Nss=(EditText) findViewById(R.id.CityE);
        btn.setOnClickListener(this);
    }
    public void accion() {
        RequestQueue modoComunicacion= Volley.newRequestQueue(this);
        String emailE=email.getText().toString();
        String celE=cel.getText().toString();
        String dirE=direccionEs.getText().toString();
        String otroNss =otroSS.getText().toString();
        String cityE= ciudadEst.getText().toString();
        String numSS= Nss.getText().toString();
        String num_segT=radio;


        JSONObject verificar = new JSONObject();
        //falta agrgarle la pag de php para que haga la coneccion.
        final String url ="http://192.168.0.8/Android/datosE.php";
        try {
            verificar.put("email_est",emailE);
            verificar.put("celEst",celE);
            verificar.put("DireccEs",dirE);
            verificar.put("otronss",otroNss);
            verificar.put("CityEst",cityE);
            verificar.put("NSS",numSS);
            verificar.put("Num_NSS",num_segT);
        }catch (Exception e){

        }



        JsonObjectRequest respuesta=new JsonObjectRequest(Request.Method.POST,
                url,verificar, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //aqui se mostrara el json que retornara el php
                try {
                    JSONObject subObject = response.getJSONObject("Detalle");
                    int data1 = 0;
                    String error="";
                    JSONArray keys = subObject.names();
                    for(int i=0; i < keys.length(); i++) {
                        String key = keys.getString(i);
                        String value = subObject.getString(key);
                        data1 = subObject.getInt("error");
                        error=subObject.getString("texto");
                        Log.d("LOG_TAG", key + " : " + value);
                    }
                    if (response.length() <= 0) {
                        Toast.makeText(getApplicationContext(), "No hay datos. ", Toast.LENGTH_LONG).show();
                    }else {
                        if (data1 <=0) {
                            //para pasar datos en el intent es con intent.putExtra
                            Toast.makeText(getApplicationContext(), "Registro exitoso, redirigiendo a la pagina principal", Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Ha regresado un: "+e, Toast.LENGTH_LONG).show();
                    Log.e("Catch", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Error de Comunicacion. Favor de contactar con la institucion.\n" + error, Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY", error.toString());
            }
        });
        modoComunicacion.add(respuesta);
    }
    public void onClick(View v) {


        accion();
        Intent anuncios = new Intent(this, DataEst.class);
        startActivity(anuncios);

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonIM:
                if (checked)
                    radio="IMSS";
                    break;
            case R.id.radioButtonIS:
                if (checked)
                    radio="ISSSTE";
                    break;

            case R.id.radioButtonOT:
                if (checked)
                    radio="OTRO";
                    break;
        }
    }
}
