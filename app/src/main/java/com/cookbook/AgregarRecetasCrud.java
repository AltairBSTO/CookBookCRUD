package com.cookbook;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import baseDeDatos.RecetasDbHelper;

public class AgregarRecetasCrud extends AppCompatActivity {

    private EditText etNombreReceta, etIngredientes, etInstrucciones;
    private Button btnGuardar, btnActualizar, btnEliminar;
    private RecetasDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_recetas_crud);

        etNombreReceta = findViewById(R.id.etNombreReceta);
        etIngredientes = findViewById(R.id.etIngredientes);
        etInstrucciones = findViewById(R.id.etInstrucciones);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnActualizar = findViewById(R.id.Actualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        dbHelper = new RecetasDbHelper(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarReceta();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarReceta();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarReceta();
            }
        });
    }

    private void agregarReceta() {
        String nombre = etNombreReceta.getText().toString().trim();
        String ingredientes = etIngredientes.getText().toString().trim();
        String instrucciones = etInstrucciones.getText().toString().trim();

        if (nombre.isEmpty() || ingredientes.isEmpty() || instrucciones.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.recetaExiste(nombre)) {
            Toast.makeText(this, "La receta ya existe.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.agregarReceta(nombre, ingredientes, instrucciones)) {
            Toast.makeText(this, "Receta agregada con éxito.", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al agregar la receta.", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarReceta() {
        String nombre = etNombreReceta.getText().toString().trim();
        String ingredientes = etIngredientes.getText().toString().trim();
        String instrucciones = etInstrucciones.getText().toString().trim();

        if (nombre.isEmpty() || ingredientes.isEmpty() || instrucciones.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dbHelper.recetaExiste(nombre)) {
            Toast.makeText(this, "La receta no existe.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí puedes usar el nombre original y los nuevos valores
        if (dbHelper.actualizarReceta(nombre, nombre, ingredientes, instrucciones)) {
            Toast.makeText(this, "Receta actualizada con éxito.", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al actualizar la receta.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarReceta() {
        String nombre = etNombreReceta.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el nombre de la receta a eliminar.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dbHelper.recetaExiste(nombre)) {
            Toast.makeText(this, "La receta no existe.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.eliminarReceta(nombre)) {
            Toast.makeText(this, "Receta eliminada con éxito.", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al eliminar la receta.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombreReceta.setText("");
        etIngredientes.setText("");
        etInstrucciones.setText("");
    }
}
