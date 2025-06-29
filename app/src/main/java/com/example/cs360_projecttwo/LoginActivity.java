package com.example.cs360_projecttwo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        loginButton.setOnClickListener(v -> login());
        registerButton.setOnClickListener(v -> register());
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        try (Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=?",
                new String[]{username, password})) {

            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void register() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        try (Cursor check = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username})) {
            if (check.moveToFirst()) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            } else {
                db.execSQL("INSERT INTO users (username, password) VALUES (?, ?)", new Object[]{username, password});
                Toast.makeText(this, "Account created. You can now log in.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


}

