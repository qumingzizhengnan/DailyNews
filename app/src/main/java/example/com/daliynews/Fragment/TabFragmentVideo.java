package example.com.daliynews.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import cn.jzvd.JZVideoPlayer;
import example.com.daliynews.Adapter.VideoPageAdapter;

import example.com.daliynews.R;


/**
 * Created by CJ on 2018/3/27.
 *
 * show video news
 */

public class TabFragmentVideo extends Fragment {

    private RecyclerView mRecyclerView;
    private View mRootView;
    private LinearLayoutManager mLayoutManager;
    private VideoPageAdapter mVideoPageAdapter;


    public static Fragment newInstance() {
        TabFragmentVideo fragment = new TabFragmentVideo();
        return fragment;

    }



    protected void loadData(){

        mRecyclerView =(RecyclerView) mRootView.findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        VideoPageAdapter mVideoPageAdapter = new VideoPageAdapter(getActivity());
        mRecyclerView.setAdapter(mVideoPageAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        Log.d("fragment","frament video_oncreateview");
        mRootView = inflater.inflate(R.layout.fragment_tab,container,false);

        loadData();

        return  mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment","frament video_onresume");

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    if (JZVideoPlayer.backPress()){
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment","frament video_onpause");
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("fragment","frament video_onstart");
    }





    @Override
    public void onStop(){
        super.onStop();
        Log.d("fragment","frament video_onstop");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d("fragment","frament video_ondestroyview");
    }

}
