package com.cookbook;

import android.content.Intent; // Importa Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import baseDeDatos.UserDbHelper;

public class MainActivity extends AppCompatActivity {

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
                // Obtener el usuario y la contraseña ingresados
                String usuario = txtUsuario.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    // Mostrar alerta si hay campos vacíos
                    mostrarAlerta("Por favor, completa todos los campos.");
                } else {
                    // Validar el usuario en la base de datos
                    if (dbHelper.validarUsuario(usuario, password)) {
                        // Mostrar alerta de éxito
                        mostrarAlerta("Inicio de sesión exitoso");

                        // Navegar a RecipesActivity después de aceptar la alerta
                        new android.os.Handler().postDelayed(() -> {
                            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                            startActivity(intent);
                            finish(); // Opcional: finaliza la actividad actual
                        }, 1000); // Espera 1 segundo para que el usuario vea el mensaje
                    } else {
                        // Mostrar alerta de error
                        mostrarAlerta("Usuario o contraseña incorrectos");
                    }
                }
            }
        });

        // Configurar el botón de registro
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de registro
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
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
