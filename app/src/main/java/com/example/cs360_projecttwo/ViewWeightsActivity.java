package com.example.cs360_projecttwo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class ViewWeightsActivity extends AppCompatActivity {

    private WeightDatabaseHelper dbHelper;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_weights);

        gridView = findViewById(R.id.gridView);
        dbHelper = new WeightDatabaseHelper(this);

        loadGrid();
    }

    private void loadGrid() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id AS _id, date, weight FROM daily_weights", null);

        if (cursor.getCount() == 0) {
            Log.d("ViewWeightsActivity", "No weight entries found in database.");
        } else {
            Log.d("ViewWeightsActivity", "Found " + cursor.getCount() + " weight entries.");
        }

        String[] from = new String[]{WeightDatabaseHelper.COLUMN_DATE, WeightDatabaseHelper.COLUMN_WEIGHT};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                from,
                to,
                0
        );

        gridView.setAdapter(adapter);
    }

}
