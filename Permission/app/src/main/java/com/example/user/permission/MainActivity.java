package com.example.user.permission;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button check_permission = (Button) findViewById(R.id.btntry);
        Button request_permission = (Button) findViewById(R.id.btnacorda);
        check_permission.setOnClickListener(this);
        request_permission.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        view = v;

        int id = v.getId();
        switch (id) {
            case R.id.btntry:
                if (checkPermission()) {
                    Toast.makeText(this, "Permisiunea este deja acordata.", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "Va rog acceptati permisiunea.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnacorda:
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    Toast.makeText(this, "Permisiunea este deja acordata.", Toast.LENGTH_SHORT).show();

                }

                break;
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (locationAccepted )
                        Toast.makeText(this, "Permisiune acordata, Acum poti accesa locatia.", Toast.LENGTH_SHORT).show();
                    else {

                        Toast.makeText(this, "Permisiune respinsa, Nu poti accesa locatia .", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("Trebuie sa accepti permisiunea",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION,},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}