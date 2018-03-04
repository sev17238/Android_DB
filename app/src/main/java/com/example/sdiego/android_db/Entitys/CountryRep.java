package com.example.sdiego.android_db.Entitys;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by SDiego on 03/03/2018.
 */

public class CountryRep implements ICountryDataSource {


    private ICountryDataSource localdatasource;
    private static CountryRep Instance ;

    public CountryRep(ICountryDataSource localdatasource) {
        this.localdatasource = localdatasource;
    }

    public static CountryRep getInstance(ICountryDataSource localdatasource){
        if(Instance==null){
            Instance = new CountryRep(localdatasource);
        }
        return Instance;
    }

    @Override
    public Flowable<Country> getCountryByID(int countryID) {
        return localdatasource.getCountryByID(countryID);
    }

    @Override
    public Flowable<List<Country>> getAllCountries() {
        return localdatasource.getAllCountries();
    }

    @Override
    public void insertCountry(Country... country) {
        localdatasource.insertCountry(country);
    }

    @Override
    public void updateCountry(Country... country) {
        localdatasource.updateCountry(country);

    }

    @Override
    public void deleteCountry(Country... country) {
        localdatasource.deleteCountry(country);
    }

    @Override
    public void deleteAllCountries() {
        localdatasource.deleteAllCountries();
    }
}
