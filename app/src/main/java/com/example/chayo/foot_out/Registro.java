package com.example.chayo.foot_out;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class Registro extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    EditText n, appat, apmat, matricula, email, pass;
    RadioButton rb1, rb2;
    Spinner carrera;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egresados);

        relacionarvista();
        //accion();
//https://www.youtube.com/watch?v=YI3mYQ6Tatw

    }

    public void relacionarvista() {
        n = (EditText) findViewById(R.id.n);
        appat = (EditText) findViewById(R.id.appat);
        apmat = (EditText) findViewById(R.id.amat);


        matricula = (EditText) findViewById(R.id.matricula);


        btn = (Button) findViewById(R.id.btn);
        carrera = (Spinner) findViewById(R.id.carrera);
        radioGroup = (RadioGroup) findViewById(R.id.group);

        pass = (EditText) findViewById(R.id.pass);

        final String[] valores = {"CARRERA", "ITICS", "IMEC", "IGEM", "ILOG"};
        carrera.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));

        btn.setOnClickListener(this);


    }

    public void accion() {

        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonId);
        int indice = radioGroup.indexOfChild(radioButton);
        RadioButton rb = (RadioButton) radioGroup.getChildAt(indice);


        RequestQueue modoComunicacion = Volley.newRequestQueue(this);
        String nombre = n.getText().toString();
        String apat = appat.getText().toString();
        String apma = apmat.getText().toString();
        String matri = matricula.getText().toString();
        String sexo = rb.getText().toString();
        String con = pass.getText().toString();
        final String car = carrera.getSelectedItem().toString();


        JSONObject verificar = new JSONObject();
        //falta agrgarle la pag de php para que haga la coneccion.
        final String url = "http://192.168.0.8/Android/datos.php";
        try {
            verificar.put("nombre", nombre);
            verificar.put("apat", apat);
            verificar.put("amat", apma);
            verificar.put("n_control", matri);
            verificar.put("sexo", sexo);
            verificar.put("carrera", car);
            verificar.put("psw", con);
        } catch (Exception e) {

        }
                /*"consulta2.php?n="+nombre+"&ap="+apat+"&am="+apma+"&na="+"&cup="+"" +
                "&tele="+"&matri="+matri+"&ingreso="+"&egreso="+"&institu="+"&sexo="+sexo+
                "&carrera="+car+"&correo="+correo+"&celular="+"&contraseña="+con+"";*/


        JsonObjectRequest respuesta = new JsonObjectRequest(Request.Method.POST,
                url, verificar, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //aqui se mostrara el json que retornara el php
                try {
                    JSONObject subObject = response.getJSONObject("Detalle");
                    int data1 = 0;
                    String error = "";
                    JSONArray keys = subObject.names();
                    for (int i = 0; i < keys.length(); i++) {
                        String key = keys.getString(i);
                        String value = subObject.getString(key);
                        data1 = subObject.getInt("error");
                        error = subObject.getString("texto");
                        Log.d("LOG_TAG", key + " : " + value);
                    }
                    if (response.length() <= 0) {
                        Toast.makeText(getApplicationContext(), "No hay datos. ", Toast.LENGTH_LONG).show();
                    } else {
                        if (data1 <= 0) {
                            //para pasar datos en el intent es con intent.putExtra
                            Toast.makeText(getApplicationContext(), "Registro exitoso, redirigiendo a la pagina principal", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ha regresado un: " + e, Toast.LENGTH_LONG).show();
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


    @Override
    public void onClick(View v) {

        accion();
        Intent anuncios = new Intent(this, anuncios.class);
        startActivity(anuncios);

    }


}
