package com.example.cs360_projecttwo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddGoalActivity extends AppCompatActivity {

    EditText goalWeightInput;
    Button saveGoalButton;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        goalWeightInput = findViewById(R.id.goalWeightInput);
        saveGoalButton = findViewById(R.id.saveGoalButton);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        saveGoalButton.setOnClickListener(v -> saveGoalWeight());
    }

    private void saveGoalWeight() {
        String input = goalWeightInput.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a goal weight", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double goalWeight = Double.parseDouble(input);
            // Replace existing goal (ID = 1) or insert new
            db.execSQL("INSERT OR REPLACE INTO goals (id, goal_weight) VALUES (1, ?)", new Object[]{goalWeight});
            Toast.makeText(this, "Goal weight saved!", Toast.LENGTH_SHORT).show();
            finish(); // return to MainActivity
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

