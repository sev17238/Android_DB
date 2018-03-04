package com.example.sdiego.android_db.Entitys;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by SDiego on 03/03/2018.
 */

@Dao
public interface CountryDAO {

    @Query("SELECT * FROM Countries WHERE id=:countryID")
    Flowable<Country> getCountryByID(int countryID);

    @Query("SELECT * FROM Countries")
    Flowable<List<Country>> getAllCountries();

    @Insert
    void insertCountry(Country... Countries);

    @Update
    void updateCountry(Country... Countries);

    @Delete
    void deleteCountry(Country... Countries);

    @Query("DELETE FROM Countries")
    void deleteAllCountries();




}
