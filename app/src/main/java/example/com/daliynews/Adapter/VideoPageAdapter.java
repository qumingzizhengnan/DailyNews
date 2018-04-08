package example.com.daliynews.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;

import cn.jzvd.JZVideoPlayerStandard;
import example.com.daliynews.R;



/**
 * Created by CJ on 2018/3/27.
 */

public class VideoPageAdapter extends RecyclerView.Adapter<VideoPageAdapter.VideoViewHolder>{

    private String []mVideos;
    private String []mVideosTitles;
    private String []mVideosImages;
    private Context mContext;
    //private String url = "http://mpianatra.com/Videos/Five%20ways%20to%20break%20up%20with%20plastic%20-%20BBC%20News%20%5b360p%5d.mp4";
    public VideoPageAdapter(Context context, String []videosUrls, String []videosTitles, String []videosImages){

        mContext = context;
        mVideos = videosUrls;
        mVideosTitles = videosTitles;
        mVideosImages = videosImages;
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
        String videoUrl = mVideos[position];
        String videoTitle = mVideosTitles[position];
        String videoImage = mVideosImages[position];
        InputStream is = null;
        try {
            is = mContext.getAssets().open(videoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        //Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoUrl, MediaStore.Video.Thumbnails.MICRO_KIND);
        JZVideoPlayerStandard player = holder.videoPlayer;
        player.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "DailyNews");
        player.thumbImageView.setImageBitmap(bitmap);
        TextView textView = holder.tv;
        textView.setText(videoTitle);
    }



    @Override
    public int getItemCount() {
        return 6;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        JZVideoPlayerStandard videoPlayer;
        public VideoViewHolder(View itemView){
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.tv_item_videotitle);
            videoPlayer =  (JZVideoPlayerStandard) itemView.findViewById(R.id.player_item_video);
        }
    }
}
