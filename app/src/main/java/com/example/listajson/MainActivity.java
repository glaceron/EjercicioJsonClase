package com.example.listajson;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.listajson.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CONTACTOS = R.raw.contactos;
   // public static final int CONTACTS = R.raw.contacts;
    private ActivityMainBinding binding;
    ArrayList<Contacto> contactos = null;
    ContactosAdapter adapter;
    String contenido;
    //Gson gson;
    //Persona persona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.button.setOnClickListener(this);
    }

    public String leerFicheroRaw(int valor) throws IOException {
        StringBuilder cadena = new StringBuilder();
        String linea = "";
        InputStream in = getApplicationContext().getResources().openRawResource(valor);
        // Otra forma
        // String filePath = "res/raw/fichero_raw";
        // InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while ((linea = br.readLine()) != null) {
            cadena.append(linea + "\n");
        }
        in.close();
        return cadena.toString();
    }
    private void mostrarMensaje(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void crearAdapter() {
        // Create adapter passing in the sample user data
        adapter = new ContactosAdapter(contactos);
        // Attach the adapter to the recyclerview to populate items
        binding.recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void obtenerContactos()
    {
        // Inicializar contactos
        if(!binding.switch1.isChecked())
        {
            try {
                contenido = leerFicheroRaw(CONTACTOS);
                contactos = Analisis.analizarContactos(contenido);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                mostrarMensaje("Error:  " + e.getMessage());
            }
        }
        else
        {
            //Usar biblioteca GSON
        }

    }

    @Override
    public void onClick(View v)
    {
        if (v == binding.button)
        {
            obtenerContactos();
            if(contactos != null)
            {
                if(adapter != null)
                {
                    adapter.actualizar(contactos);
                }
                else
                {
                    crearAdapter();
                }
            }else
            {
                mostrarMensaje("Error al obtener los contactos");
            }
        }
    }

}