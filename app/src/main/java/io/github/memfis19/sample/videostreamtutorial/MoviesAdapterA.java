package io.github.memfis19.sample.videostreamtutorial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.memfis19.sample.R;

/**
 * Created by kamal on 03/06/2018.
 */

public class MoviesAdapterA extends RecyclerView.Adapter<MoviesAdapterA.MyViewHolder> {

private List<String> moviesList;
    private  Context mContext;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, year, genre;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);

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
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}