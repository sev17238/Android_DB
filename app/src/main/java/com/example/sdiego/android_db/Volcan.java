package com.example.sdiego.android_db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SDiego on 01/03/2018.
 */

public class Volcan implements Parcelable{
    String nombre;
    String altura;

    public Volcan(String nombre,String altura){
        this.nombre=nombre;
        this.altura=altura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }


    protected Volcan(Parcel in) {
        nombre = in.readString();
        altura = in.readString();
    }

    public static final Creator<Volcan> CREATOR = new Creator<Volcan>() {
        @Override
        public Volcan createFromParcel(Parcel in) {
            return new Volcan(in);
        }

        @Override
        public Volcan[] newArray(int size) {
            return new Volcan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(altura);
    }
}
