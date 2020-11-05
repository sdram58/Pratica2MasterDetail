package com.catata.pratica2masterdetail.modelo;

import android.content.Context;

import com.catata.pratica2masterdetail.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionLibros {

    public static List<LibroItem> LIBROS = new ArrayList<LibroItem>();


    public static LibroItem getLibroById(String id){
        LibroItem l = null;
        for (LibroItem libro:LIBROS) {
            if(libro.id.toLowerCase().compareTo(id)==0){
                l=libro;
                break;
            }
        }

        return l;
    }

    public static int getTotalLibros(){
        return LIBROS.size();
    }

    public static void cargarLibros(Context c){
        InputStream raw = c.getResources().openRawResource(R.raw.datos_json_url);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Type listType = new TypeToken<List<LibroItem>>() {}.getType();
        Gson gson = new Gson();
        LIBROS = gson.fromJson(rd, listType);
    }

}
