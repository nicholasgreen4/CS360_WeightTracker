package com.example.cs360_projecttwo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SMS_PERMISSION = 1001;
    private static final String TAG = "MainActivity";

    // Constants to avoid hardcoded strings
    private static final String GOAL_SMS_PHONE = "1234567890";
    private static final String GOAL_SMS_MESSAGE = "Congratulations! You've reached your goal weight!";

    private boolean canSendSMS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddGoal = findViewById(R.id.buttonAddGoal);
        Button buttonAddWeight = findViewById(R.id.buttonAddWeight);
        Button buttonViewWeights = findViewById(R.id.buttonViewWeights);
        Button sendSMSButton = findViewById(R.id.sendSMSButton);

        // Navigate to Add Goal Weight screen
        buttonAddGoal.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddGoalActivity.class))
        );

        // Navigate to Add Today's Weight screen
        buttonAddWeight.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddWeightActivity.class))
        );

        // Navigate to View Weight History screen
        buttonViewWeights.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewWeightsActivity.class))
        );

        // Send goal SMS if permission granted
        sendSMSButton.setOnClickListener(v -> {
            if (canSendSMS) {
                sendGoalReachedSMS(GOAL_SMS_PHONE, GOAL_SMS_MESSAGE);
            } else {
                Toast.makeText(MainActivity.this, "SMS permission not granted.", Toast.LENGTH_SHORT).show();
            }
        });

        checkSMSPermission(); // Make sure SMS permission is checked during setup
    }


    private void checkSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SMS_PERMISSION);
        } else {
            canSendSMS = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                canSendSMS = true;
                Toast.makeText(this, "SMS permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                canSendSMS = false;
                Toast.makeText(this, "SMS permission denied. SMS feature disabled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendGoalReachedSMS(String phoneNumber, String message) {
        try {
            SmsManager sms = getSystemService(SmsManager.class); // No cast needed
            sms.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "SMS sending failed", e);
        }
    }


}
