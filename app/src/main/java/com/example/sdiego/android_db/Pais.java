package com.example.sdiego.android_db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by SDiego on 01/03/2018.
 */

public class Pais implements Parcelable {
    String nombrepais;
    String Continente;
    ArrayList<Volcan> Volcanes;
    //Volcan[] Volcanes;

    public Pais(String nombre, String Continente,ArrayList<Volcan> volcanes /*Volcan[] volcanes*/){
        this.nombrepais=nombre;
        this.Continente=Continente;
        this.Volcanes = volcanes;
    }

    public String getNombrepais() {
        return nombrepais;
    }

    public void setNombrepais(String nombrepais) {
        this.nombrepais = nombrepais;
    }

    public String getContinente() {
        return Continente;
    }

    public void setContinente(String continente) {
        Continente = continente;
    }

    public ArrayList<Volcan> getVolcanes() {
        return Volcanes;
    }

    public void setVolcanes(ArrayList<Volcan> volcanes) {
        Volcanes = volcanes;
    }

    /*public Volcan[] getVolcanes() {
        return Volcanes;
    }

    public void setVolcanes(Volcan[] volcanes) {
        Volcanes = volcanes;
    }*/


    protected Pais(Parcel in) {
        nombrepais = in.readString();
        Continente = in.readString();
        //Volcanes = in.createTypedArray(Volcan.CREATOR);
    }

    public static final Creator<Pais> CREATOR = new Creator<Pais>() {
        @Override
        public Pais createFromParcel(Parcel in) {
            return new Pais(in);
        }

        @Override
        public Pais[] newArray(int size) {
            return new Pais[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombrepais);
        dest.writeString(Continente);
        //dest.writeTypedArray(Volcanes, flags);
    }
}
