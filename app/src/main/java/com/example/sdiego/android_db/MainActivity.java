package com.example.sdiego.android_db;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sdiego.android_db.Entitys.Country;
import com.example.sdiego.android_db.Entitys.CountryDB;
import com.example.sdiego.android_db.Entitys.CountryDataSource;
import com.example.sdiego.android_db.Entitys.CountryRep;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private FloatingActionButton fab;

    //Adapter
    List<Country> countryList;
    ArrayAdapter adapter;

    //DataBase
    private CompositeDisposable compositeDisposable;
    private CountryRep countryRep;

    public MainActivity(){
        countryList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init view
        listview=(ListView) findViewById(R.id.listViewPaises);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,countryList);
        registerForContextMenu(listview);
        listview.setAdapter(adapter);

        //DataBase
        CountryDB countryDB = CountryDB.getInstance(this);
        countryRep = CountryRep.getInstance(CountryDataSource.getInstance(countryDB.countryDAO()));

        //Load all data from DataBase
        loadData();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country count = (Country)adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, Info_Activity.class);

                intent.putExtra("Country",count);
                startActivity(intent);
            }
        });

        //Event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add new country
                Disposable disposable= io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Country country = new Country("XCountry","YContinent");
                        countryRep.insertCountry(country);
                        e.onComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                                       @Override
                                       public void accept(Object o) throws Exception {
                                           Toast.makeText(MainActivity.this, "Country Added.", Toast.LENGTH_SHORT).show();
                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   },
                                new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        loadData(); //Refresh Data
                                    }
                                }
                        );
            }
        });
    }

    private void loadData(){
        //Use RXJava
        Disposable disposable = countryRep.getAllCountries()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Country>>() {
                    @Override
                    public void accept(List<Country> countries) throws Exception {
                        onGetAllCountrySuccess(countries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, ""+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                compositeDisposable.add(disposable);
    }

    private void onGetAllCountrySuccess(List<Country> countries){
        countryList.clear();
        countryList.addAll(countries);
        adapter.notifyDataSetChanged();

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

        switch(id){
            case R.id.menu_clear:
            deleteAllCountries();
            break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Se borran los paises de la lista con ayuda del metodo deleteAllCountries de countryRep.
    private void deleteAllCountries(){
        Disposable disposable= io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                countryRep.deleteAllCountries();
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   //Toast.makeText(MainActivity.this, "Country Added.", Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData(); //Refresh Data
                            }
                        }
                );
        compositeDisposable.add(disposable);
    }
    //Se crea el menu para actualizar el pais o para borrarlo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Select action:");
        menu.add(Menu.NONE,0,Menu.NONE,"Update");
        menu.add(Menu.NONE,1,Menu.NONE,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Country country = countryList.get(info.position);

        switch (item.getItemId()){

            case 0://update
                final EditText edtName = new EditText(MainActivity.this);
                edtName.setText(country.getName());
                edtName.setHint("Enter new name");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Edit")
                        .setMessage("Edit country name")
                        .setView(edtName)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(TextUtils.isEmpty(edtName.getText().toString())){
                                    return ;
                                }else{
                                    country.setName(edtName.getText().toString());
                                    updateCountry(country);
                                }
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;

            case 1: //Delete
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Do you want to delete "+country.getName()+"from list?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               deleteCountry(country);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }
        return true;
    }

    private void deleteCountry(final Country country) {
        Disposable disposable= io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                countryRep.deleteCountry(country);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   //Toast.makeText(MainActivity.this, "Country Added.", Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData(); //Refresh Data
                            }
                        }

                );
        compositeDisposable.add(disposable);
    }

    private void updateCountry(final Country country) {
        Disposable disposable= io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                countryRep.updateCountry(country);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   //Toast.makeText(MainActivity.this, "Country Added.", Toast.LENGTH_SHORT).show();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData(); //Refresh Data
                            }
                        }

                );
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


}
