package baseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RecetasDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recetasDatabase.db";
    private static final String TABLE_RECETAS = "t_recetas";
    private final Context context;

    public RecetasDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_RECETAS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "ingredientes TEXT NOT NULL, " +
                "instrucciones TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETAS);
        onCreate(db);
    }

    // Método para agregar una receta
    public boolean agregarReceta(String nombre, String ingredientes, String instrucciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("ingredientes", ingredientes);
        values.put("instrucciones", instrucciones);

        long result = db.insert(TABLE_RECETAS, null, values);
        db.close();
        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    // Método para obtener todas las recetas
    public Cursor obtenerRecetas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RECETAS, null);
    }

    // Método para obtener una receta por nombre
    public Cursor obtenerRecetaPorNombre(String nombreReceta) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RECETAS + " WHERE nombre = ?", new String[]{nombreReceta});
    }

    // Método para verificar si una receta ya existe
    public boolean recetaExiste(String nombreReceta) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECETAS + " WHERE nombre = ?", new String[]{nombreReceta});
        boolean existe = (cursor.getCount() > 0); // Si hay resultados, la receta ya existe
        cursor.close();
        return existe;
    }

    // Método para actualizar una receta
    public boolean actualizarReceta(String nombreAntiguo, String nuevoNombre, String ingredientes, String instrucciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nuevoNombre);
        values.put("ingredientes", ingredientes);
        values.put("instrucciones", instrucciones);

        // Actualizar la receta
        int rowsAffected = db.update(TABLE_RECETAS, values, "nombre = ?", new String[]{nombreAntiguo});
        db.close();
        return rowsAffected > 0; // Devuelve true si la actualización fue exitosa
    }

    // Método para eliminar una receta
    public boolean eliminarReceta(String nombreReceta) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_RECETAS, "nombre = ?", new String[]{nombreReceta});
        db.close();
        return rowsAffected > 0; // Devuelve true si la eliminación fue exitosa
    }


}
