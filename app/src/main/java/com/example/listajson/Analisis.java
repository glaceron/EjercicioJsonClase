package com.example.listajson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Analisis {
    public static ArrayList<Contacto> analizarContactos(String cadena) throws JSONException {
        JSONArray jAcontactos;
        JSONObject objeto, jOcontacto, jOtelefono;
        Contacto contacto;
        Telefono telefono;
        ArrayList<Contacto> personas = new ArrayList<>();


        // añadir contactos (en formato JSON) a personas


        objeto = new JSONObject(cadena);
        jAcontactos = objeto.getJSONArray("contactos");

        for(int i = 0; i < jAcontactos.length(); i++)
        {
            jOcontacto = jAcontactos.getJSONObject(i);
            contacto = new Contacto();
            contacto.setNombre(jOcontacto.getString("nombre"));
            contacto.setDireccion(jOcontacto.getString("direccion"));
            contacto.setEmail(jOcontacto.getString("email"));

            jOtelefono = jOcontacto.getJSONObject("telefono");

            telefono = new Telefono();
            telefono.setCasa(jOtelefono.getString("casa"));
            telefono.setMovil(jOtelefono.getString("movil"));
            telefono.setTrabajo(jOtelefono.getString("trabajo"));

            contacto.setTelefono(telefono);

            personas.add(contacto);

        }
        return personas;
    }
}