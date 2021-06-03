package com.soul_six.aplicacionsostenesv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button Register = (Button) findViewById(R.id.btnRegistrarse);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            }
        });
    }

    public void GetTextFromMaterialTextFields(View v){
        int cont = 0;
        EditText EtUsername = (EditText)findViewById(R.id.TextLogin_user);
        EditText EtPassword = (EditText)findViewById(R.id.TextLogin_password);
        String username = EtUsername.getText().toString();
        String password = EtPassword.getText().toString();
        String getUser = "";
        String getPass = "";
        int getId = 0;
        if(!username.equals("")){
            if(!password.equals("")){
                try{
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect  = connectionHelper.connectionclass();
                    if(connect != null){
                        String query = "SELECT idUsuario, NombreUsuario, Contrasenna FROM Usuarios where NombreUsuario = '" + username + "'";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        while(rs.next()){
                            getId = Integer.parseInt(rs.getString(1));
                            getUser = rs.getString(2);
                            getPass = rs.getString(3);
                            cont++;
                        }
                        if (cont <= 0){
                            //Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(this)
                                    .setMessage("User not found")
                                    .setPositiveButton(this.getString(R.string.ok), null)
                                    .show();
                        } else if (cont > 0){
                            if(password.equals(getPass)){
                                new AlertDialog.Builder(this)
                                        .setMessage("Welcome " + getUser + " ")
                                        .setPositiveButton(this.getString(R.string.ok), null)
                                        .show();
                                Intent intent = new Intent(LoginActivity.this, ContactActivity.class);

                                startActivity(intent);
                            } else {
                                //Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();\
                                new AlertDialog.Builder(this)
                                        .setMessage("Incorrect password")
                                        .setPositiveButton(this.getString(R.string.ok), null)
                                        .show();
                            }
                        }
                    }else{
                        //Toast.makeText(getApplicationContext(), "Aqui esta el error", Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(this)
                                .setMessage("Conexion ex")
                                .setPositiveButton(this.getString(R.string.ok), null)
                                .show();
                    }
                } catch (Exception e){
                    ConnectionResult = "Check Connection";
                }
            }else{
                //Toast.makeText(getApplicationContext(), "Ingrese una Contraseña", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setMessage("Insert Password")
                        .setPositiveButton(this.getString(R.string.ok), null)
                        .show();
            }
        }else{
            //Toast.makeText(getApplicationContext(), "Ingrese un Usuario", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setMessage("Insert User")
                    .setPositiveButton(this.getString(R.string.ok), null)
                    .show();
        }
    }
}