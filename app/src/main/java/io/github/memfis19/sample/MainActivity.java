package io.github.memfis19.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malmstein.fenster.controller.FensterPlayerControllerVisibilityListener;
import com.malmstein.fenster.controller.SimpleMediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import io.github.memfis19.sample.firbase.MyDownloadService;
import io.github.memfis19.sample.firbase.MyUploadService;
import io.github.memfis19.sample.videostreamtutorial.MoviesAdapterA;
import io.github.memfis19.sample.videostreamtutorial.VideoData;


/**
 * Created by memfis on 11/8/16.
 */

public class MainActivity extends AppCompatActivity implements FensterPlayerControllerVisibilityListener {

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
    private FloatingActionButton fab;
    private String base64Thumnail = "";
    private ProgressBar progressbar;
    private LinearLayout ll_main;
    private TextView txt;
    private View view;
    private Uri uri;
    private TextView eventA;
    private TextView eventB;
    private TextView eventC;
    private TextView eventD;
    //private MoviesAdapterA moviesAdapterA,moviesAdapterB,moviesAdapterC,moviesAdapterD;
   // RecyclerView rvA;
   // RecyclerView rvB;
    //RecyclerView rvC ;
   // RecyclerView rvD;
   // private TextView noA,noB,noC,noD;



    private String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    // Declare some variables
    private ProgressDialog pDialog;
    //VideoView videoview;
    private FensterVideoView textureView;
    private SimpleMediaFensterPlayerController fullScreenMediaPlayerController;
    private DatabaseReference mDatabase;
   // private FirebaseDatabase mFirebaseInstance;
   // private DatabaseReference mFirebaseDatabase;
    List<VideoData> typeA=new ArrayList<>();
    List<VideoData> typeB=new ArrayList<>();
    List<VideoData> typeC=new ArrayList<>();
    List<VideoData> typeD=new ArrayList<>();
    private  List<VideoData> list = new ArrayList<VideoData>();;
    ArrayList<MyModel> videoData = new ArrayList<>();
    private String thumnailPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("A DEMO APPLICATION");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        textView = (TextView) findViewById(R.id.picture_download_uri);
        eventA = (TextView) findViewById(R.id.eventA);
        eventB = (TextView) findViewById(R.id.eventB);
        eventC = (TextView) findViewById(R.id.eventC);
        eventD = (TextView) findViewById(R.id.eventD);
        eventA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoData.clear();
                for(int i = 0; i<typeA.size(); i++){
                    videoData.add(new MyModel(typeA.get(i).getLink(),typeA.get(i).getThumnail(), "A sample uploaded video"));
                    videoData.add(new MyModel(typeA.get(i).getLink(),typeA.get(i).getThumnail(), ""));
                }

                Constant.myModels = videoData;
                startActivity(new Intent(getApplicationContext(), VideoViewActivity.class));
            }
        });
        eventB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoData.clear();
                for(int i = 0; i<typeB.size(); i++){
                    videoData.add(new MyModel(typeB.get(i).getLink(),typeB.get(i).getThumnail(), "A sample uploaded video"));
                    videoData.add(new MyModel(typeB.get(i).getLink(),typeB.get(i).getThumnail(), ""));
                }

                Constant.myModels = videoData;
                startActivity(new Intent(getApplicationContext(), VideoViewActivity.class));
            }
        });
        eventC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoData.clear();
                for(int i = 0; i<typeC.size(); i++){
                    videoData.add(new MyModel(typeC.get(i).getLink(),typeC.get(i).getThumnail(), "A sample uploaded video"));
                    videoData.add(new MyModel(typeC.get(i).getLink(),typeC.get(i).getThumnail(), ""));
                }

                Constant.myModels = videoData;
                startActivity(new Intent(getApplicationContext(), VideoViewActivity.class));
            }
        });
        eventD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoData.clear();
                for(int i = 0; i<typeD.size(); i++){
                    videoData.add(new MyModel(typeD.get(i).getLink(),typeD.get(i).getThumnail(), "A sample uploaded video"));
                    videoData.add(new MyModel(typeD.get(i).getLink(),typeD.get(i).getThumnail(), ""));
                }

                Constant.myModels = videoData;
                startActivity(new Intent(getApplicationContext(), VideoViewActivity.class));
            }
        });
       /* noA = (TextView) findViewById(R.id.noA);
        noB = (TextView) findViewById(R.id.noB);
        noC = (TextView) findViewById(R.id.noC);
        noD = (TextView) findViewById(R.id.noD);
        noA.setVisibility(View.GONE);
        noB.setVisibility(View.GONE);
        noC.setVisibility(View.GONE);
        noD.setVisibility(View.GONE);*/
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        view = ll_main;
        ll_main.setVisibility(View.GONE);
        txt = (TextView) findViewById(R.id.txt);
        txt.setVisibility(View.GONE);
        progressbar.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);
        textView.setVisibility(View.GONE);
        /* rvA = (RecyclerView) findViewById(R.id.rvA);
         rvB = (RecyclerView) findViewById(R.id.rvB);
         rvC = (RecyclerView) findViewById(R.id.rvC);
         rvD = (RecyclerView) findViewById(R.id.rvD);*/
       /* GridLayoutManager managerA = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager managerB = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager managerC = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager managerD = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL, false);

        RecyclerView.LayoutManager mLayoutManagerA = new LinearLayoutManager(getApplicationContext());
        rvA.setLayoutManager(managerA);
        rvA.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerB = new LinearLayoutManager(getApplicationContext());
        rvB.setLayoutManager(managerB);
        rvB.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerC = new LinearLayoutManager(getApplicationContext());
        rvC.setLayoutManager(managerC);
        rvC.setItemAnimator(new DefaultItemAnimator());


        rvD.setLayoutManager(managerD);
        rvD.setItemAnimator(new DefaultItemAnimator());


        moviesAdapterA = new MoviesAdapterA(this,typeA);
        moviesAdapterB = new MoviesAdapterA(this,typeB);
        moviesAdapterC = new MoviesAdapterA(this,typeC);
        moviesAdapterD = new MoviesAdapterA(this,typeD);
        rvA.setAdapter(moviesAdapterA);
        rvB.setAdapter(moviesAdapterB);
        rvC.setAdapter(moviesAdapterC);
        rvD.setAdapter(moviesAdapterD);
*/
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
               // hideProgressDialog();
                switch (intent.getAction()) {
                    case MyUploadService.UPLOAD_COMPLETED:
                    case MyUploadService.UPLOAD_ERROR:
                        onUploadResultIntent(intent);
                        break;
                }
            }
        };

        progressbar.setVisibility(View.VISIBLE);
        mFirebaseDatabase = mFirebaseInstance.getReference("videos");
        mFirebaseInstance.getReference("videos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e(TAG, "App title updated");
				/*GenericTypeIndicator<Map<String, VideoData>> t = new GenericTypeIndicator<Map<String, VideoData>>() {};
				Map<String, VideoData> map = dataSnapshot.getValue(t);
				System.out.println("!!!!!!!!!!!!!!!"+map.size());*/

                list.clear();
                typeA.clear();
                typeB.clear();
                typeC.clear();
                typeD.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    list.add(child.getValue(VideoData.class));
                }

                for (int i=0;i<list.size();i++){
                    if (list.get(i).type.equalsIgnoreCase("A")) {
                        typeA.add(list.get(i));
                    }
                    else if (list.get(i).type.equalsIgnoreCase("B")){
                        typeB.add(list.get(i));
                    }else if (list.get(i).type.equalsIgnoreCase("C")){
                        typeC.add(list.get(i));
                    }else if (list.get(i).type.equalsIgnoreCase("D")){
                        typeD.add(list.get(i));
                    }
                }




                /*if(list.size() == 0){
                    txt.setVisibility(View.VISIBLE);
                    ll_main.setVisibility(View.GONE);
                }else{
                    txt.setVisibility(View.GONE);
                    ll_main.setVisibility(View.VISIBLE);

                    if(typeA.size()>0){
                       noA.setVisibility(View.GONE);
                    }else{
                        noA.setVisibility(View.VISIBLE);
                    }

                    if(typeB.size()>0){
                        noB.setVisibility(View.GONE);
                    }else{
                        noB.setVisibility(View.VISIBLE);
                    }

                    if(typeC.size()>0){
                        noC.setVisibility(View.GONE);
                    }else{
                        noC.setVisibility(View.VISIBLE);
                    }

                    if(typeD.size()>0){
                        noD.setVisibility(View.GONE);
                    }else{
                        noD.setVisibility(View.VISIBLE);
                    }
                }*/
                init();

                System.out.println("!!!!!!!!!kk!!!!!!"+list.size());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                // Failed to read value
                //	Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
        //bindViews();
        //initVideo();
    }

    private void init() {
        ll_main.setVisibility(View.VISIBLE);
      /*  moviesAdapterA.notifyDataSetChanged();
        moviesAdapterB.notifyDataSetChanged();
        moviesAdapterC.notifyDataSetChanged();
        moviesAdapterD.notifyDataSetChanged();*/
       /* RecyclerView rvA = (RecyclerView) findViewById(R.id.rvA);
        RecyclerView rvB = (RecyclerView) findViewById(R.id.rvB);
        RecyclerView rvC = (RecyclerView) findViewById(R.id.rvC);
        RecyclerView rvD = (RecyclerView) findViewById(R.id.rvD);
*/






       /* rvA.setAdapter(new MoviesAdapterA(this,typeA));
        rvB.setAdapter(new MoviesAdapterA(this,typeB));
        rvC.setAdapter(new MoviesAdapterA(this,typeC));
        rvD.setAdapter(new MoviesAdapterA(this,typeD));*/
        progressbar.setVisibility(View.GONE);
      //  ll_main.setVisibility(View.VISIBLE);

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Check if this Activity was launched by clicking on an upload notification
        if (intent.hasExtra(MyUploadService.EXTRA_DOWNLOAD_URL)) {
           // onUploadResultIntent(intent);
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
                case R.id.fab:
                case R.id.videoLimitedConfiguration:
                    base64Thumnail = "";
                   // getEvent();
                    /*registerForContextMenu(view);
                    openContextMenu(view);
                    unregisterForContextMenu(view);
*/
                    callRecorder();



                    break;
               /* case R.id.streaming_video:
                    if(textView.getText().toString().trim().length()>0) {


                       loadDataInFirebase(textView.getText().toString().trim());



                        Intent intent = new Intent(MainActivity.this, io.github.memfis19.sample.videostreamtutorial.MainActivity.class);
                        intent.putExtra("url", textView.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this,"Please upload the video..",Toast.LENGTH_LONG).show();
                    }





                    break;*/
                case R.id.uploaded_video:

                        /*Intent intent = new Intent(MainActivity.this, io.github.memfis19.sample.videostreamtutorial.MainActivity.class);
                        startActivity(intent);
                        finish();*/


                    break;
            }
        }
    };

/*    private void getEvent() {
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


    }*/

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle("Select Event");
        inflater.inflate(R.menu.menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu item was pressed
        switch (item.getItemId()) {
            case R.id.option1:
                eventType="A";
                uploadFromUri(uri);
               // doOptionOne();
                return true;
            case R.id.option2:
                eventType="B";
                uploadFromUri(uri);
                //doOptionTwo();
                return true;
            case R.id.option3:
                eventType="C";
                uploadFromUri(uri);
                //doOptionTwo();
                return true;
            case R.id.option4:
                eventType="D";
                uploadFromUri(uri);
                //doOptionTwo();
                return true;
            default:
                return false;
        }
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


    private void loadDataInFirebase(String vPath) {

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("videos");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
        createUser(eventType,vPath);
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

        VideoData user = new VideoData(type, url, base64Thumnail);

        mFirebaseDatabase.child(userId).setValue(user);
        Toast.makeText(activity, "Uploaded", Toast.LENGTH_SHORT).show();

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
        findViewById(R.id.uploaded_video).setOnClickListener(onClickListener);
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
            //Toast.makeText(this, "Media captured."+data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH), Toast.LENGTH_SHORT).show();
       //     uploadFromUri(Uri.parse(data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH)));
            thumnailPath = data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH);
            uri = Uri.fromFile(new File(data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH)));
            registerForContextMenu(view);
            openContextMenu(view);
            unregisterForContextMenu(view);

           //
        }
    }

    private void uploadFromUri(Uri fileUri) {

        Log.d("!!!!", "uploadFromUri:src:" + fileUri.toString());
        startService(new Intent(this, MyUploadService.class)
                .putExtra(MyUploadService.EXTRA_FILE_URI, fileUri)
                .setAction(MyUploadService.ACTION_UPLOAD));

        // Show loading spinner
        //showProgressDialog(getString(R.string.progress_uploading));
    }
   /* private void showProgressDialog(String caption) {
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
    }*/

    private void onUploadResultIntent(Intent intent) {

        textView.setVisibility(View.GONE);
        textView.setText(""+intent.getParcelableExtra(MyUploadService.EXTRA_DOWNLOAD_URL));
       // Toast.makeText(activity, "AAA", Toast.LENGTH_SHORT).show();
        String vPath  = textView.getText().toString();
        try {
           // Bitmap b  = retriveVideoFrameFromVideo(vPath);
            Bitmap b  = createThumbnailFromPath(thumnailPath, MediaStore.Images.Thumbnails.MINI_KIND);
            if(b!=null){
                base64Thumnail = toBase64(b);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        loadDataInFirebase(vPath);

    }

    @Override
    public void onControlsVisibilityChange(boolean value) {

    }


    public String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }



   /* public Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }*/

    public Bitmap createThumbnailFromPath(String filePath, int type){
        return ThumbnailUtils.createVideoThumbnail(filePath, type);
    }
}
