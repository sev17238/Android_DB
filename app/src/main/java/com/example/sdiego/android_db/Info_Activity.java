package com.example.sdiego.android_db;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Info_Activity extends AppCompatActivity {

    TextView campoPais, campoCont, campoPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        campoPais = (TextView) findViewById(R.id.country);
        campoCont = (TextView) findViewById(R.id.continent);
        //campoPrueba = (TextView) findViewById(R.id.textview1);

        Bundle sentobj = getIntent().getExtras();
        Pais pais = sentobj.getParcelable("Pais");

        if(pais!=null){
            campoPais.setText(pais.getNombrepais());
            campoCont.setText(pais.getContinente());
            //campoPrueba.setText(pais.getVolcanes()[0].getNombre());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
