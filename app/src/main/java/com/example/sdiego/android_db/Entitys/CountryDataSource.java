package com.example.sdiego.android_db.Entitys;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by SDiego on 03/03/2018.
 */

public class CountryDataSource implements ICountryDataSource {

    private CountryDAO countryDAO;
    private static CountryDataSource Instance;

    public CountryDataSource(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    public static CountryDataSource getInstance(CountryDAO countryDAO) {
        if(Instance==null){
            Instance = new CountryDataSource(countryDAO);
        }
        return Instance;
    }



    @Override
    public Flowable<Country> getCountryByID(int countryID) {
        return countryDAO.getCountryByID(countryID);
    }

    @Override
    public Flowable<List<Country>> getAllCountries() {
        return countryDAO.getAllCountries();
    }

    @Override
    public void insertCountry(Country... country) {
        countryDAO.insertCountry(country);
    }

    @Override
    public void updateCountry(Country... country) {
        countryDAO.updateCountry(country);
    }

    @Override
    public void deleteCountry(Country... country) {
        countryDAO.deleteCountry(country);
    }

    @Override
    public void deleteAllCountries() {
        countryDAO.deleteAllCountries();
    }
}
