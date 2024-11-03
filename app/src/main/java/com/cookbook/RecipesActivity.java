package com.cookbook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import baseDeDatos.RecetasDbHelper;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private RecipesAdapter recipesAdapter;
    private TextView tvNoRecipes;
    private RecetasDbHelper recetasDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        tvNoRecipes = findViewById(R.id.tvNoRecipes);
        rvRecipes = findViewById(R.id.rvRecipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        recetasDbHelper = new RecetasDbHelper(this);

        cargarRecetas();

        Button btnAgregarReceta = findViewById(R.id.btnAgregarReceta);
        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PresionarAgregar_Recetas();
            }
        });
    }

    private void cargarRecetas() {
        List<Recetas> recetas = new ArrayList<>();
        Cursor cursor = recetasDbHelper.obtenerRecetas();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                    String ingredientes = cursor.getString(cursor.getColumnIndex("ingredientes"));
                    String instrucciones = cursor.getString(cursor.getColumnIndex("instrucciones"));

                    // Agregar receta a la lista
                    recetas.add(new Recetas(nombre, ingredientes, instrucciones));
                } while (cursor.moveToNext());
            }
            cursor.close(); // No olvides cerrar el cursor
        }

        recipesAdapter = new RecipesAdapter(recetas);
        rvRecipes.setAdapter(recipesAdapter);

        // Mostrar mensaje si no hay recetas
        if (recetas.isEmpty()) {
            tvNoRecipes.setVisibility(View.VISIBLE);
        } else {
            tvNoRecipes.setVisibility(View.GONE);
        }
    }


    private void PresionarAgregar_Recetas() {
        // Iniciar la actividad para agregar recetas
        Intent intent = new Intent(this, AgregarRecetasCrud.class);
        startActivity(intent);
    }
}
