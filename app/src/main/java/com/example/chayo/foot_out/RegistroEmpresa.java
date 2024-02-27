package com.example.chayo.foot_out;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistroEmpresa extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    EditText nameE,giro,direccion,col,zipCode, ciudad, tel, MlMision;
    Spinner Empresas;
    RadioGroup radioGroup;
    final String urlSpiner ="http://192.168.0.6/Android/spiner.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        llenaSpiner(urlSpiner);
    }

    public void relacionaVistas(){
        nameE=(EditText) findViewById(R.id.NameE);
        giro=(EditText) findViewById(R.id.GiroE);
        direccion=(EditText) findViewById(R.id.DireccionE);
        col=(EditText) findViewById(R.id.ColE);
        zipCode=(EditText) findViewById(R.id.ZipCodE);
        ciudad=(EditText) findViewById(R.id.CityE);
        tel=(EditText) findViewById(R.id.TelE);
        MlMision=(EditText) findViewById(R.id.MisionE);
        btn.setOnClickListener(this);
    }

    public void accion() {
        RequestQueue modoComunicacion= Volley.newRequestQueue(this);
        String nombreE=nameE.getText().toString();
        String gi=giro.getText().toString();
        String dir=direccion.getText().toString();
        String colo=col.getText().toString();
        String zcode= zipCode.getText().toString();
        String ciu= ciudad.getText().toString();
        String tele=tel.getText().toString();
        String mis=MlMision.getText().toString();


        JSONObject verificar = new JSONObject();
        //falta agrgarle la pag de php para que haga la coneccion.
        final String url ="http://192.168.0.6/Android/datosE.php";
        try {
            verificar.put("nombreE",nombreE);
            verificar.put("giro",gi);
            verificar.put("dir",dir);
            verificar.put("colo",colo);
            verificar.put("zcode",zcode);
            verificar.put("city",ciu);
            verificar.put("telE",tele);
            verificar.put("mision",mis);
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


    public void llenaSpiner(String url){
        String[] datosArray= new String[]{};
        Empresas = (Spinner) findViewById(R.id.spinner);
        final StringRequest busca=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray datos = new JSONArray(response);
                    for(int i=0; i < datos.length(); i++) {
                        datosArray[i]=datos.getString(i);
                    }
                    Empresas.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,datosArray));
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error.\n" + e, Toast.LENGTH_SHORT).show();
                    Log.e("DATA_ERROR", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Error de Comunicacion. Favor de contactar con la institucion.\n" + error, Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY", error.toString());
            }
        }
        );
    }

}

