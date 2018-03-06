package io.github.memfis19.sample.videostreamtutorial;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import io.github.memfis19.sample.R;

/**
 * Created by kamal on 03/06/2018.
 */

public class MoviesAdapterA extends RecyclerView.Adapter<MoviesAdapterA.MyViewHolder> {

private List<String> moviesList;
    private  Context mContext;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public VideoView video_preview;
    public ImageView ivThumb;

    public MyViewHolder(View view) {
        super(view);
        video_preview = (VideoView) view.findViewById(R.id.video_preview);
        ivThumb = (ImageView) view.findViewById(R.id.ivThumb);

    }
}


    public MoviesAdapterA(Context context,List<String> moviesList) {
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
    holder.ivThumb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setVisibility(View.GONE);
            Uri uri=Uri.parse(moviesList.get(position));


            holder.video_preview.setVideoURI(uri);
            holder.video_preview.start();

        }
    });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}