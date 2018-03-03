package com.example.sdiego.android_db;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private ItemListAdapter adapter;
    private ArrayList<Pais> paises;

    public MainActivity(){
        paises = new ArrayList<Pais>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list=(ListView) findViewById(R.id.listViewPaises);

        //Adapter Usage
        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        //list.setAdapter(adapter);

        ArrayList<Volcan> listG = new ArrayList<Volcan>();
        Volcan it1 = new Volcan( "Volcan de Pacaya","2552 MSNM");
        Volcan it2 = new Volcan("Volcan de Agua","3760 MSNM");
        Volcan it3 = new Volcan("Volcan Santa Maria", "3772 MSNM");
        Volcan it4 = new Volcan("Volcan de Acatenango", "4010 MSNM");
        listG.add(it1);
        listG.add(it2);
        listG.add(it3);
        listG.add(it4);

        /*Volcan[] volcanesG = new Volcan[5];
        volcanesG[1] = new Volcan( "Volcan de Pacaya","2552 MSNM");
        volcanesG[2] = new Volcan("Volcan de Agua","3760 MSNM");
        volcanesG[3] = new Volcan("Volcan Santa Maria", "3772 MSNM");
        volcanesG[4] = new Volcan("Volcan de Acatenango", "4010 MSNM");*/

        Pais pais1 = new Pais("Guatemala","CentroAmerica", listG);
        paises.add(new Pais("Italia","Europa",listG));
        paises.add(new Pais("Chile","America del Sur",listG));
        paises.add(pais1);

        adapter = new ItemListAdapter(getApplicationContext(), paises);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pais pais = (Pais)adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, Info_Activity.class);

                intent.putExtra("Pais",pais);
                startActivity(intent);

            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
