package io.github.memfis19.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import io.github.memfis19.sample.firbase.MyDownloadService;
import io.github.memfis19.sample.firbase.MyUploadService;
import io.github.memfis19.sample.videostreamtutorial.VideoData;


/**
 * Created by memfis on 11/8/16.
 */

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mBroadcastReceiver;
    private ProgressDialog mProgressDialog;

    ///////////////////////
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    private static final int CAPTURE_MEDIA = 368;

    private Activity activity;
    private TextView textView;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String userId;
    private String eventType="A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_layout);
        textView = (TextView) findViewById(R.id.picture_download_uri);
        textView.setVisibility(View.GONE);

        activity = this;

        if (Build.VERSION.SDK_INT > 15) {
            askForPermissions(new String[]{
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.RECORD_AUDIO,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSIONS);
        } else {
            enableCamera();
        }

        /////////////////////////////////////////
        // Initialize Firebase Auth
        onNewIntent(getIntent());

        // Local broadcast receiver
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                hideProgressDialog();
                switch (intent.getAction()) {
                    case MyUploadService.UPLOAD_COMPLETED:
                    case MyUploadService.UPLOAD_ERROR:
                        onUploadResultIntent(intent);
                        break;
                }
            }
        };
    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Check if this Activity was launched by clicking on an upload notification
        if (intent.hasExtra(MyUploadService.EXTRA_DOWNLOAD_URL)) {
            onUploadResultIntent(intent);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //updateUI(mAuth.getCurrentUser());

        // Register receiver for uploads and downloads
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(mBroadcastReceiver, MyDownloadService.getIntentFilter());
        manager.registerReceiver(mBroadcastReceiver, MyUploadService.getIntentFilter());
    }

    @Override
    public void onStop() {
        super.onStop();

        // Unregister download receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.videoLimitedConfiguration:

                    getEvent();





                    break;
                case R.id.streaming_video:
                    if(textView.getText().toString().trim().length()>0) {


                       loadDataInFirebase(textView.getText().toString().trim());



                        Intent intent = new Intent(MainActivity.this, io.github.memfis19.sample.videostreamtutorial.MainActivity.class);
                        intent.putExtra("url", textView.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this,"Please upload the video..",Toast.LENGTH_LONG).show();
                    }





                    break;
            }
        }
    };

    private void getEvent() {
        final Dialog dialog = new Dialog(MainActivity.this);
        // Include dialog.xml file
        dialog.setTitle("Select the Event");
        dialog.setContentView(R.layout.dialog);
        // Set dialog title
        // set values for custom dialog components - text, image and button
        TextView text2 = (TextView) dialog.findViewById(R.id.textView2);
        TextView text3 = (TextView) dialog.findViewById(R.id.textView3);
        TextView text4 = (TextView) dialog.findViewById(R.id.textView4);
        TextView text5 = (TextView) dialog.findViewById(R.id.textView5);

        dialog.show();



        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                eventType="A";
                dialog.dismiss();
                callRecorder();
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                eventType="B";
                dialog.dismiss();
                callRecorder();
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                eventType="C";
                dialog.dismiss();
                callRecorder();
            }
        });
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                eventType="D";
                dialog.dismiss();
                callRecorder();
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void callRecorder() {

        AnncaConfiguration.Builder videoLimited = new AnncaConfiguration.Builder(activity, CAPTURE_MEDIA);
        videoLimited.setMediaAction(AnncaConfiguration.MEDIA_ACTION_UNSPECIFIED);
        videoLimited.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_AUTO);
        videoLimited.setVideoFileSize(10 * 1024 * 1024);
        videoLimited.setMinimumVideoDuration(11 * 1000);
        new Annca(videoLimited.build()).launchCamera();
    }


    private void loadDataInFirebase(String trim) {

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("videos");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
        createUser(eventType,trim);
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TAG", "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read app title value.", error.toException());
            }
        });
    }



    /**
     * Creating new user node under 'users'
     */
    private void createUser(String type, String url) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        VideoData user = new VideoData(type, url);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VideoData user = dataSnapshot.getValue(VideoData.class);

                // Check for null
                if (user == null) {
                    Log.e("TAG", "User data is null!");
                    return;
                }

                Log.e("TAG", "User data is changed!" + user.type + ", " + user.link);

                // Display newly updated name and email
                //txtDetails.setText(user.type + ", " + user.link);

                // clear edit text
               // inputEmail.setText("");
               // inputName.setText("");

               /// toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String email) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("type").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("link").setValue(email);
    }
    protected final void askForPermissions(String[] permissions, int requestCode) {
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), requestCode);
        } else enableCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) return;
        enableCamera();
    }

    protected void enableCamera() {
        findViewById(R.id.defaultConfiguration).setOnClickListener(onClickListener);
        findViewById(R.id.photoConfiguration).setOnClickListener(onClickListener);
        findViewById(R.id.videoConfiguration).setOnClickListener(onClickListener);
        findViewById(R.id.videoLimitedConfiguration).setOnClickListener(onClickListener);
        findViewById(R.id.universalConfiguration).setOnClickListener(onClickListener);
        findViewById(R.id.dialogDemo).setOnClickListener(onClickListener);
        findViewById(R.id.customDemo).setOnClickListener(onClickListener);
        findViewById(R.id.squareDemo).setOnClickListener(onClickListener);
        findViewById(R.id.streaming_video).setOnClickListener(onClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            Toast.makeText(this, "Media captured."+data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH), Toast.LENGTH_SHORT).show();
       //     uploadFromUri(Uri.parse(data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH)));
            uploadFromUri(Uri.fromFile(new File(data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH))));
        }
    }

    private void uploadFromUri(Uri fileUri) {

        Log.d("!!!!", "uploadFromUri:src:" + fileUri.toString());
        startService(new Intent(this, MyUploadService.class)
                .putExtra(MyUploadService.EXTRA_FILE_URI, fileUri)
                .setAction(MyUploadService.ACTION_UPLOAD));

        // Show loading spinner
        showProgressDialog(getString(R.string.progress_uploading));
    }
    private void showProgressDialog(String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.setMessage(caption);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void onUploadResultIntent(Intent intent) {

        textView.setVisibility(View.GONE);
        textView.setText(""+intent.getParcelableExtra(MyUploadService.EXTRA_DOWNLOAD_URL));

    }
}
