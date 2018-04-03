package example.com.daliynews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import example.com.daliynews.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by CJ on 2018/3/27.
 */

public class VideoPageAdapter extends RecyclerView.Adapter<VideoPageAdapter.VideoViewHolder>{

    private Context mContext;
    private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    public VideoPageAdapter(Context context){
        mContext = context;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.video_page_layout,parent,false);

        VideoViewHolder view = new VideoViewHolder(childView);
        return view;
    }


    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        JCVideoPlayerStandard player = holder.videoPlayer;
        if (player != null) {
            player.release();
        }
        boolean setUp = player.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
       /* if (setUp) {
            Glide.with(mContext).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
        }*/
    }



    @Override
    public int getItemCount() {
        return 15;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        JCVideoPlayerStandard videoPlayer;
        public VideoViewHolder(View itemView){
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.tv_item_videotitle);
            videoPlayer =  (JCVideoPlayerStandard) itemView.findViewById(R.id.player_item_video);
        }
    }
}
