package example.com.daliynews.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.daliynews.Adapter.PopularPageAdapter;
import example.com.daliynews.R;

/**
 * Created by CJ on 2018/3/27.
 */

public class TabFragmentPopular extends Fragment{
    public static Fragment newInstance() {
        TabFragmentPopular fragment = new TabFragmentPopular();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_tab,container,false);
        RecyclerView recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new PopularPageAdapter());
        return  rootView;
    }
}
