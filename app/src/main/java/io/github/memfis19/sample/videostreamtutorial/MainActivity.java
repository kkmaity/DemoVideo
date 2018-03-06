package io.github.memfis19.sample.videostreamtutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.malmstein.fenster.controller.FensterPlayerControllerVisibilityListener;
import com.malmstein.fenster.controller.SimpleMediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.memfis19.sample.R;

public class MainActivity extends AppCompatActivity implements FensterPlayerControllerVisibilityListener {
	// Put in your Video URL here
	private String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
	// Declare some variables
	private ProgressDialog pDialog;
	//VideoView videoview;
	private FensterVideoView textureView;
	private SimpleMediaFensterPlayerController fullScreenMediaPlayerController;
	private DatabaseReference mDatabase;
	private FirebaseDatabase mFirebaseInstance;
	private DatabaseReference mFirebaseDatabase;
	List<String> typeA=new ArrayList<>();
	List<String> typeB=new ArrayList<>();
	List<String> typeC=new ArrayList<>();
	List<String> typeD=new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set the layout from video_main.xml
		setContentView(R.layout.activity_event_list_main);



		mDatabase = FirebaseDatabase.getInstance().getReference();
		//VideoURL=getIntent().getExtras().getString("url");

		// Find your VideoView in your video_main.xml layout
		//videoview = (VideoView) findViewById(R.id.VideoView);
		// Execute StreamVideo AsyncTask
	//	new StreamVideo().execute();
		/*textureView = (FensterVideoView) findViewById(R.id.play_video_texture);
		fullScreenMediaPlayerController = (SimpleMediaFensterPlayerController) findViewById(R.id.play_video_controller);

		textureView.setMediaController(fullScreenMediaPlayerController);

		textureView.setVideo(VideoURL,
				fullScreenMediaPlayerController.DEFAULT_VIDEO_START);
		textureView.start();*/
		mFirebaseInstance = FirebaseDatabase.getInstance();

		// get reference to 'users' node
		mFirebaseDatabase = mFirebaseInstance.getReference("videos");
		mFirebaseInstance.getReference("videos").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				//Log.e(TAG, "App title updated");
				/*GenericTypeIndicator<Map<String, VideoData>> t = new GenericTypeIndicator<Map<String, VideoData>>() {};
				Map<String, VideoData> map = dataSnapshot.getValue(t);
				System.out.println("!!!!!!!!!!!!!!!"+map.size());*/
				List<VideoData> list = new ArrayList<VideoData>();
				for (DataSnapshot child: dataSnapshot.getChildren()) {
					list.add(child.getValue(VideoData.class));
				}

				for (int i=0;i<list.size();i++){
					if (list.get(i).type.equalsIgnoreCase("A")) {
						typeA.add(list.get(i).link);
					}
					else if (list.get(i).type.equalsIgnoreCase("B")){
						typeB.add(list.get(i).link);
					}else if (list.get(i).type.equalsIgnoreCase("C")){
						typeC.add(list.get(i).link);
					}else if (list.get(i).type.equalsIgnoreCase("D")){
						typeD.add(list.get(i).link);
					}
				}

				System.out.println("!!!!!!!!!kk!!!!!!"+list.size());

			}

			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
			//	Log.e(TAG, "Failed to read app title value.", error.toException());
			}
		});
		//bindViews();
		//initVideo();
		init();

	}

	private void init() {
		RecyclerView rvA = (RecyclerView) findViewById(R.id.rvA);
		RecyclerView rvB = (RecyclerView) findViewById(R.id.rvB);
		RecyclerView rvC = (RecyclerView) findViewById(R.id.rvC);
		RecyclerView rvD = (RecyclerView) findViewById(R.id.rvD);
		RecyclerView.LayoutManager mLayoutManagerA = new LinearLayoutManager(getApplicationContext());
		rvA.setLayoutManager(mLayoutManagerA);
		rvA.setItemAnimator(new DefaultItemAnimator());
		RecyclerView.LayoutManager mLayoutManagerB = new LinearLayoutManager(getApplicationContext());
		rvB.setLayoutManager(mLayoutManagerB);
		rvB.setItemAnimator(new DefaultItemAnimator());
		RecyclerView.LayoutManager mLayoutManagerC = new LinearLayoutManager(getApplicationContext());
		rvC.setLayoutManager(mLayoutManagerC);
		rvC.setItemAnimator(new DefaultItemAnimator());
		RecyclerView.LayoutManager mLayoutManagerD = new LinearLayoutManager(getApplicationContext());
		rvD.setLayoutManager(mLayoutManagerD);
		rvD.setItemAnimator(new DefaultItemAnimator());
		rvA.setAdapter(new MoviesAdapterA(MainActivity.this,typeA));
		rvB.setAdapter(new MoviesAdapterA(MainActivity.this,typeB));
		rvC.setAdapter(new MoviesAdapterA(MainActivity.this,typeC));
		rvD.setAdapter(new MoviesAdapterA(MainActivity.this,typeD));


	}

	/*@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		if (getIntent().getExtras().getString("url")!=null) {
			textureView.setVideo(VideoURL);
			*//*AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.big_buck_bunny);
			textureView.setVideo(assetFileDescriptor);*//*
		} else {
			textureView.setVideo(VideoURL);
		}

		textureView.start();
	}
*/
	private void bindViews() {
		textureView = (FensterVideoView) findViewById(R.id.play_video_texture);
		fullScreenMediaPlayerController = (SimpleMediaFensterPlayerController) findViewById(R.id.play_video_controller);
	}

	private void initVideo() {
		fullScreenMediaPlayerController.setVisibilityListener(this);
		textureView.setMediaController(fullScreenMediaPlayerController);
		textureView.setOnPlayStateListener(fullScreenMediaPlayerController);
	}

	/*private void setSystemUiVisibility(final boolean visible) {
		int newVis = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

		if (!visible) {
			newVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		}

		final View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(newVis);
		decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(final int visibility) {
				if ((visibility & View.SYSTEM_UI_FLAG_LOW_PROFILE) == 0) {
					fullScreenMediaPlayerController.show();
				}
			}
		});
	}
*/
	/*@Override
	public void onControlsVisibilityChange(final boolean value) {
		setSystemUiVisibility(value);
	}*/

	/*// StreamVideo AsyncTask
	private class StreamVideo extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressbar
			pDialog = new ProgressDialog(MainActivity.this);
			// Set progressbar title
			pDialog.setTitle("Please wait");
			// Set progressbar message
			pDialog.setMessage("Buffering...");
			pDialog.setIndeterminate(false);
			// Show progressbar
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {

			try {
				// Start the MediaController
				MediaController mediacontroller = new MediaController(
						MainActivity.this);
				mediacontroller.setAnchorView(videoview);
				// Get the URL from String VideoURL
				Uri video = Uri.parse(VideoURL);
				videoview.setMediaController(mediacontroller);
				videoview.setVideoURI(video);

				videoview.requestFocus();
				videoview.setOnPreparedListener(new OnPreparedListener() {
					// Close the progress bar and play the video
					public void onPrepared(MediaPlayer mp) {
						pDialog.dismiss();
						videoview.start();
					}
				});
			} catch (Exception e) {
				pDialog.dismiss();
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

		}

	}
*/

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(MainActivity.this,io.github.memfis19.sample.MainActivity.class));
		finish();
	}

	@Override
	public void onControlsVisibilityChange(boolean value) {

	}
}