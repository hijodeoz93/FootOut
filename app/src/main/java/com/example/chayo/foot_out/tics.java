package com.example.chayo.foot_out;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class tics extends AppCompatActivity implements View.OnClickListener {
    Button btn,btn2;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tics);
        name=(TextView)findViewById(R.id.txt2);
        name.setText(getIntent().getExtras().getString("Nombre"));
        btn=(Button)findViewById(R.id.btn);
        btn2=(Button)findViewById(R.id.btn2);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn.getBackground().setColorFilter(0x81F7BE, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                Intent residencias =new Intent(this,anuncios.class);
                startActivity(residencias);
                break;

            case R.id.btn2:
                Intent servicio= new Intent(this,servicio.class);
                startActivity(servicio);

        }
    }
}
