package com.example.sdiego.android_db.Entitys;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by SDiego on 03/03/2018.
 */

public interface ICountryDataSource {
    Flowable<Country> getCountryByID(int countryID);

    Flowable<List<Country>> getAllCountries();

    void insertCountry(Country... country);

    void updateCountry(Country... country);

    void deleteCountry(Country... country);

    void deleteAllCountries();

}
