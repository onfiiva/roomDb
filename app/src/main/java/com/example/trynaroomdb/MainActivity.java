package com.example.trynaroomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText userLogin, userPassword, confirmPassword;
    Button registerUser;
    TextView toAuth;
    String login, password;
    SharedPreferences userLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLogin = findViewById(R.id.login_reg);
        userPassword = findViewById(R.id.password_reg);
        confirmPassword = findViewById(R.id.password_reg_confirm);
        registerUser = findViewById(R.id.reg_button);
        toAuth = findViewById(R.id.to_auth);

        userLogs = this.getSharedPreferences(
                "com.example.trynaroomdb", Context.MODE_PRIVATE);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                if (db.userDao().getUser(userLogin.getText().toString()) == null){
                    if (userPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                        User user = new User(
                                userLogin.getText().toString(),
                                userPassword.getText().toString()
                        );
                        db.userDao().insertUser(user);
                        Toast.makeText(getApplicationContext(), "Успешная регистрация.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Пароли не совпадают.", Toast.LENGTH_LONG).show();
                    }
                }
                else if (userLogin.getText().toString().equals("") || userPassword.getText().toString().equals("")
                        || confirmPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Введите данные.", Toast.LENGTH_LONG).show();
                }
                else if (userLogin.getText().toString().equals(db.userDao().getUser(userLogin.getText().toString()).getLogin())) {
                    Toast.makeText(getApplicationContext(), "Пользователь с таким логином уже существует.", Toast.LENGTH_LONG).show();
                }
            }
        });

        toAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        if (!userLogs.getString("Login", "").equals("") && !userLogs.getString("Password", "").equals("")){
            if (userLogs.getString("Login", "").equals("admin") && userLogs.getString("Password", "").equals("admin1234")){
                Intent intent = new Intent(getApplicationContext(), AdminWindow.class);
                intent.putExtra("Login", userLogs.getString("Login", ""));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), UserWindow.class);
                intent.putExtra("Login", userLogs.getString("Login", ""));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        }
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        if (db.typeDao().getType("Transfer") == null){
            Type transfer = new Type("Transfer");
            Type cashing = new Type("Cashing out");
            Type market = new Type("Market");
            Type food = new Type("Food");
            Type electronics = new Type("Electronics");

            db.typeDao().insertType(transfer);
            db.typeDao().insertType(cashing);
            db.typeDao().insertType(market);
            db.typeDao().insertType(food);
            db.typeDao().insertType(electronics);

            Toast.makeText(getApplicationContext(), "Types are added.", Toast.LENGTH_SHORT).show();
        }
        if (db.userDao().isExists("admin") == false){
            Toast.makeText(getApplicationContext(), "Создаем админа.", Toast.LENGTH_LONG).show();
            login = "admin";
            password = "admin1234";
            User user = new User(login, password);
            db.userDao().insertUser(user);
        }
        super.onStart();
    }
}