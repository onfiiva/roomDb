package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class AuthenticationActivity extends Activity {

    EditText userLogin, userPassword;
    Button authUser;
    TextView toReg;
    SharedPreferences userLogs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_window);

        userLogin = findViewById(R.id.login_auth);
        userPassword = findViewById(R.id.password_auth);
        authUser = findViewById(R.id.auth_button);
        toReg = findViewById(R.id.to_reg);

        userLogs = this.getSharedPreferences(
                "com.example.trynaroomdb", Context.MODE_PRIVATE);

        authUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                if (!db.userDao().getAllUsers().isEmpty()){
                    if (db.userDao().getUser(userLogin.getText().toString()) != null) {
                        if (((db.userDao().getUser(userLogin.getText().toString()).getPassword().equals(userPassword.getText().toString()))
                                && (!userLogin.getText().toString().equals("admin")))){

                            userLogs.edit().putString("Login", userLogin.getText().toString()).apply();
                            userLogs.edit().putString("Password", userPassword.getText().toString()).apply();

                            Intent intent = new Intent(getApplicationContext(), UserWindow.class);
                            intent.putExtra("Login", userLogin.getText().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        }
                        else if (userLogin.getText().toString().equals("admin") && userPassword.getText().toString().equals("admin1234")){

                            userLogs.edit().putString("Login", userLogin.getText().toString()).apply();
                            userLogs.edit().putString("Password", userPassword.getText().toString()).apply();

                            Intent intent = new Intent(getApplicationContext(), AdminWindow.class);
                            intent.putExtra("Login", userLogin.getText().toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Введены неверные данные",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Такого пользователя не существует.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Такого пользователя не существует.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }
}
