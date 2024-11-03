package com.cookbook;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import baseDeDatos.UserDbHelper; // Asegúrate de importar correctamente la clase de base de datos.

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsuario, etCorreo, etContrasena, etRepetirContrasena;
    private CheckBox cboxPoliticas, cboxCondiciones;
    private Button btnCrearCuenta;
    private UserDbHelper dbHelper; // Instancia de la base de datos.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        cboxPoliticas = findViewById(R.id.cboxPoliticas);
        cboxCondiciones = findViewById(R.id.cboxCondiciones);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        // Inicializar la base de datos
        dbHelper = new UserDbHelper(this);

        btnCrearCuenta.setOnClickListener(v -> crearCuenta()); // Usar expresión lambda para el click
    }

    private void crearCuenta() {
        String usuario = etUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String repetirContrasena = etRepetirContrasena.getText().toString().trim();

        // Validaciones de campos
        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(repetirContrasena)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(repetirContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cboxPoliticas.isChecked() || !cboxCondiciones.isChecked()) {
            Toast.makeText(this, "Debe aceptar las políticas y condiciones", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar usuario en la base de datos
        boolean insertado = dbHelper.registrarUsuario(usuario, correo, contrasena);
        if (insertado) {
            Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
            finish(); // Finaliza la actividad y regresa a la pantalla anterior
        } else {
            Toast.makeText(this, "Error al crear la cuenta, intenta con otro nombre de usuario", Toast.LENGTH_SHORT).show();
        }
    }
}
