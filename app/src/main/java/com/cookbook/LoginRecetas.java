package com.cookbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import baseDeDatos.UserDbHelper;

public class LoginRecetas extends AppCompatActivity {

    private UserDbHelper dbHelper;
    private EditText txtUsuario;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_recetas);

        // Inicializar el objeto UserDbHelper
        dbHelper = new UserDbHelper(this);

        // Obtener referencias a los campos de texto
        txtUsuario = findViewById(R.id.txtUsuarioxd);
        txtPassword = findViewById(R.id.txtPassxd);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegistro = findViewById(R.id.buttonRegistro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar el botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar alerta con "Hola"
                mostrarAlerta("Hola");
            }
        });

        // Configurar el botón de registro
        buttonRegistro.setOnClickListener(v -> {
            // Aquí puedes manejar la acción de registro
            // Por ejemplo, abrir una nueva actividad para registrar usuarios
        });
    }

    // Método para mostrar una alerta
    private void mostrarAlerta(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
