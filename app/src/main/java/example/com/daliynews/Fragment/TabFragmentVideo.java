package example.com.daliynews.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import example.com.daliynews.Adapter.VideoPageAdapter;
import example.com.daliynews.NewsContent;
import example.com.daliynews.OnRecyclerItemClickListener;
import example.com.daliynews.R;

/**
 * Created by CJ on 2018/3/27.
 */

public class TabFragmentVideo extends Fragment {
    public static Fragment newInstance() {
        TabFragmentVideo fragment = new TabFragmentVideo();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_tab,container,false);
        RecyclerView recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        VideoPageAdapter mVideoPageAdapter = new VideoPageAdapter();
        mVideoPageAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),  "The Video", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mVideoPageAdapter);
        return  rootView;
    }
}
