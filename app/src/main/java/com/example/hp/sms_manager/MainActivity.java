package com.example.hp.sms_manager;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int SMS_PERMISSION_CODE=1;
    Button mSend;
    EditText mNumber,mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSend=(Button)findViewById(R.id.send);
        mMessage=(EditText)findViewById(R.id.message);
        mNumber=(EditText)findViewById(R.id.number);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    10);
        }else{
            Toast.makeText(MainActivity.this, "SMS Send Permission is Already Granted" , Toast.LENGTH_LONG).show();
        }
//now call send function
        mSend.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==10){
            if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "SMS Send Permission is Granted" , Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        final String number=mNumber.getText().toString();
        final String message=mMessage.getText().toString();
        if(!message.isEmpty()&& !number.isEmpty()){
            AlertDialog.Builder mAlert = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Are you sure???")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //editText.getText();
                            SmsManager manager = SmsManager.getDefault();
                            manager.sendTextMessage(number, null, message, null, null);
                            Toast.makeText(MainActivity.this, "Message Send Successfully to" + mNumber.getText().toString(), Toast.LENGTH_LONG).show();
                            mNumber.setText("");
                            mMessage.setText("");
                        }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                        AlertDialog alert = mAlert.create();
                        alert.setTitle("CONFIRM");
                        alert.show();
        }else{
            Toast.makeText(MainActivity.this, "Please fill all the Fields", Toast.LENGTH_LONG).show();
        }
    }
}


