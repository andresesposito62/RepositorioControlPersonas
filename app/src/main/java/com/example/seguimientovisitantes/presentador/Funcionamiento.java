package com.example.seguimientovisitantes.presentador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seguimientovisitantes.modelo.AdminSQLiteOpenHelper;
import com.example.seguimientovisitantes.R;

public class Funcionamiento extends AppCompatActivity {

    EditText editTextIdentificacion, editTextNombres, editTextApellidos, editTextTelefono, editTextTemperatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionamiento);

        editTextIdentificacion = (EditText)findViewById(R.id.editTextIdentificacion);
        editTextNombres = (EditText)findViewById(R.id.editTextNombres);
        editTextApellidos = (EditText)findViewById(R.id.editTextApellidos);
        editTextTelefono = (EditText)findViewById(R.id.editTextTelefono);
        editTextTemperatura = (EditText)findViewById(R.id.editTextTemperatura);

    }

    public void registrar(View view){
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String identificacion = editTextIdentificacion.getText().toString();
        String nombres = editTextNombres.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String temperatura = editTextTemperatura.getText().toString();

        if(!identificacion.isEmpty() && !nombres.isEmpty() &&
            !apellidos.isEmpty() && !telefono.isEmpty() &&
            !temperatura.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("identificacion",identificacion);
            registro.put("nombres",nombres);
            registro.put("apellidos",apellidos);
            registro.put("telefono",telefono);
            registro.put("temperatura",temperatura);

            BaseDeDatos.insert("visitantes", null, registro);
            BaseDeDatos.close();
            editTextIdentificacion.setText("");
            editTextNombres.setText("");
            editTextApellidos.setText("");
            editTextTelefono.setText("");
            editTextTemperatura.setText("");

            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String identificacion = editTextIdentificacion.getText().toString();

        if(!identificacion.isEmpty()){

            Cursor fila = BaseDeDatos.rawQuery
                    ("select nombres, apellidos, telefono, temperatura from visitantes where identificacion=" + identificacion, null);
            if(fila.moveToFirst()){
                editTextNombres.setText(fila.getString(0));
                editTextApellidos.setText(fila.getString(1));
                editTextTelefono.setText(fila.getString(2));
                editTextTemperatura.setText(fila.getString(3));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el número de identificación", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String identificacion = editTextIdentificacion.getText().toString();

        if(!identificacion.isEmpty()){
            int cantidad = BaseDeDatos.delete("visitantes","identificacion=" + identificacion,null);
            BaseDeDatos.close();
            editTextIdentificacion.setText("");
            editTextNombres.setText("");
            editTextApellidos.setText("");
            editTextTelefono.setText("");
            editTextTemperatura.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir el numero de identificacion", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String identificacion = editTextIdentificacion.getText().toString();
        String nombres = editTextNombres.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String temperatura = editTextTemperatura.getText().toString();

        if(!identificacion.isEmpty() && !nombres.isEmpty() &&
                !apellidos.isEmpty() && !telefono.isEmpty() &&
                !temperatura.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("identificacion",identificacion);
            registro.put("nombres",nombres);
            registro.put("apellidos",apellidos);
            registro.put("telefono",telefono);
            registro.put("temperatura",temperatura);

            int cantidad = BaseDeDatos.update("visitantes", registro, "identificacion=" + identificacion, null);
            BaseDeDatos.close();
            if(cantidad == 1){
                Toast.makeText(this, "Registro modificado correctamente", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Debes introducir el número de identificacion", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "El registro no existe", Toast.LENGTH_SHORT).show();
        }
    }
}