package com.example.sdiego.android_db.Entitys;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import static com.example.sdiego.android_db.Entitys.CountryDB.DATABASE_VERSION;


/**
 * Created by SDiego on 03/03/2018.
 */

@Database(entities = Country.class,version = DATABASE_VERSION)
public abstract class CountryDB extends RoomDatabase{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="First-DataBase";

    public abstract CountryDAO countryDAO();

    private static CountryDB Instance;

    public static CountryDB getInstance(Context context){
        if(Instance ==null){
            Instance = Room.databaseBuilder(context,CountryDB.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return Instance;
    }


}
