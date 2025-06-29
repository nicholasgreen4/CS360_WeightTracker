package com.example.cs360_projecttwo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddWeightActivity extends AppCompatActivity {

    EditText weightInput;
    Button saveWeightButton;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        weightInput = findViewById(R.id.weightInput);
        saveWeightButton = findViewById(R.id.saveWeightButton);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        saveWeightButton.setOnClickListener(v -> saveWeight());
    }

    private void saveWeight() {
        String input = weightInput.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(input);

            // Get today's date in yyyy-MM-dd format
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            // Insert weight with date
            db.execSQL("INSERT INTO weights (date, weight) VALUES (?, ?)", new Object[]{date, weight});

            Toast.makeText(this, "Weight saved!", Toast.LENGTH_SHORT).show();

            weightInput.setText(""); // Clear input for next entry

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid weight format", Toast.LENGTH_SHORT).show();
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

