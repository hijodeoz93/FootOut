package com.example.chayo.foot_out;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class login extends AppCompatActivity implements View.OnClickListener
{
    Button btn;
    Button btn2;
    RadioButton rb1,rb2,rb3,rb4;
    EditText usuario,contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
//       httpHandler handler = new httpHandler();
//       String txt = handler.post("http://192.168.0.19/app_android/app.php");
//        TextView t = (TextView)findViewById(R.id.text1);
//        t.setText(txt);
        ConnectivityManager ConnMng= (ConnectivityManager)
                getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo network = ConnMng.getActiveNetworkInfo();
        if (network==null && network.isConnected()!=true){
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
        }

        relacionarvistas();


    }
            public void relacionarvistas() {
                btn = (Button) findViewById(R.id.btn);
                usuario = (EditText) findViewById(R.id.usuario);
                contraseña = (EditText) findViewById(R.id.contraseña);
                btn2=(Button)findViewById(R.id.btn2);
                btn.setOnClickListener(this);
                btn2.setOnClickListener(this);

            }


            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn:
                        RequestQueue modoComunicacion= Volley.newRequestQueue(this);
                        String u=usuario.getText().toString();
                        String p=contraseña.getText().toString();
                        JSONObject jsonEnvio = new JSONObject();
                        try {
                            jsonEnvio.put("User", u);
                            jsonEnvio.put("Pwd", p);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        final String url = "http://192.168.0.6/Android/login.php";
                        JsonObjectRequest respuesta = new JsonObjectRequest(Request.Method.POST,
                                url,jsonEnvio, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject subObject = response.getJSONObject("Detalle");
                                    int data1 = 0;
                                    String nam="",us="",pws="";
                                    JSONArray keys = subObject.names();
                                    for(int i=0; i < keys.length(); i++) {
                                        String key = keys.getString(i);
                                        String value = subObject.getString(key);
                                        data1 = subObject.getInt("error");
                                        us=subObject.getString("user");
                                        pws=subObject.getString("pws");
                                        nam=subObject.getString("Nombre");
                                        Log.d("LOG_TAG", key + " : " + value);
                                    }
                                    if (response.length() <= 0) {
                                        Toast.makeText(getApplicationContext(), "No hay datos. ", Toast.LENGTH_LONG).show();
                                    } else {
                                        if (data1 <=0) {
                                            //para pasar datos en el intent es con intent.putExtra
                                            final Intent carrera = new Intent(login.this, tics.class);
                                            carrera.putExtra("Nombre",nam);
                                            startActivity(carrera);
                                        } else if (us != u) {

                                        } else
                                            Toast.makeText(getApplicationContext(), "Usuario no Registrado,\n favor de regitrarse para continuar: ", Toast.LENGTH_LONG).show();
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
                        break;



    case R.id.btn2:
        Intent egresados = new Intent(this, Registro.class);
        startActivity(egresados);
        break;
    }
    }
    }
