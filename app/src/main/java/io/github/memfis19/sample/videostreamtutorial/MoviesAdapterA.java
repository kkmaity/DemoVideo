package io.github.memfis19.sample.videostreamtutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.HashMap;
import java.util.List;

import io.github.memfis19.sample.R;

/**
 * Created by kamal on 03/06/2018.
 */

public class MoviesAdapterA extends RecyclerView.Adapter<MoviesAdapterA.MyViewHolder> {

private List<VideoData> moviesList;
    private  Context mContext;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public UniversalVideoView mVideoView;
    public UniversalMediaController mMediaController;
    public ImageView ivThumb;
    public ImageView ivPlay;

    public MyViewHolder(View view) {
        super(view);
        mVideoView = (UniversalVideoView) view.findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) view.findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
        ivPlay = (ImageView) view.findViewById(R.id.ivPlay);

    }
}


    public MoviesAdapterA(Context context,List<VideoData> moviesList) {
        this.moviesList = moviesList;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_video, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /*try {
            holder.ivThumb.setImageBitmap(retriveVideoFrameFromVideo(moviesList.get(position)));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/
        decodeimage(holder.ivThumb,moviesList.get(position).getThumnail(),holder.ivPlay);
        holder.ivThumb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setVisibility(View.GONE);
            RelativeLayout rl = (RelativeLayout) v.getParent();
            ImageView imageView = (ImageView)rl.findViewById(R.id.ivPlay) ;
            imageView.setVisibility(View.GONE);
            View view1 = View.inflate(mContext,R.layout.loading,null);
            holder.mMediaController.setOnLoadingView(view1);
            Uri uri=Uri.parse(moviesList.get(position).getLink());


            holder.mVideoView.setVideoURI(uri);
            holder.mVideoView.start();

        }
    });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    public Bitmap retriveVideoFrameFromVideo(String videoPath)
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
    }

    public void decodeimage(ImageView image,String strBase64,ImageView image1){
    if(strBase64.length()<10){
        image.setImageResource(R.drawable.thumb);
        image1.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);

    }else{
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);
        image1.setVisibility(View.VISIBLE);

    }
    }

}