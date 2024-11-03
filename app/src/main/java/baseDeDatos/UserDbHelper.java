package baseDeDatos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "login.db";
    private static final String TABLE_USERS = "t_usuarios";
    private final Context context;

    public UserDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo_electronico TEXT NOT NULL, " +
                "pass TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Método para registrar usuario
    public boolean registrarUsuario(String nombre, String email, String password) {
        if (nombreExiste(nombre)) {
            mostrarAlerta("Nombre de usuario ya en uso", "Por favor, elige otro nombre de usuario.");
            return false;
        } else if (correoExiste(email)) {
            mostrarAlerta("Correo electrónico ya registrado", "Por favor, usa otro correo electrónico.");
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo_electronico", email);
        values.put("pass", password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    // Verifica si el nombre de usuario ya existe
    private boolean nombreExiste(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE nombre = ?", new String[]{nombre});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Verifica si el correo electrónico ya existe
    private boolean correoExiste(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE correo_electronico = ?", new String[]{email});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Muestra una alerta
    private void mostrarAlerta(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Método para validar usuario
    public boolean validarUsuario(String nombre, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{"id"},
                "nombre=? AND pass=?",
                new String[]{nombre, password},
                null, null, null);

        boolean existe = (cursor.getCount() > 0);
        cursor.close(); // Cerrar el cursor después de usarlo
        return existe; // Devuelve verdadero si el usuario existe
    }

}
