package example.com.daliynews.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import example.com.daliynews.R;


/**
 * Created by CJ on 2018/3/27.
 */

public class VideoPageAdapter extends RecyclerView.Adapter<VideoPageAdapter.VideoViewHolder>{

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.video_page_layout,parent,false);

        VideoViewHolder view = new VideoViewHolder(childView);
        return view;
    }


    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 15;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{

//        VideoView videoView;

        ImageView imgView;
        public VideoViewHolder(View itemView){
            super(itemView);
            //videoView = (VideoView) itemView.findViewById(R.id.videoView);
            imgView = (ImageView) itemView.findViewById(R.id.img_video);
        }
    }
}
