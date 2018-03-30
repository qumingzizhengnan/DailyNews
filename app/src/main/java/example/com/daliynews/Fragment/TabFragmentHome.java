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

import example.com.daliynews.Adapter.HomePageAdapter;
import example.com.daliynews.MainActivity;
import example.com.daliynews.NewsContent;
import example.com.daliynews.R;
import example.com.daliynews.OnRecyclerItemClickListener;
/**
 * Created by CJ on 2018/3/26.
 */

public class TabFragmentHome extends Fragment {



    public static Fragment newInstance() {
        TabFragmentHome fragment = new TabFragmentHome();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_tab,container,false);
        RecyclerView recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        HomePageAdapter mHomePageAdapter = new HomePageAdapter();
       mHomePageAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
               Toast.makeText(getActivity(),  "sssssssss", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getActivity(), NewsContent.class);
               startActivity(intent);
           }
       });
        recyclerView.setAdapter(mHomePageAdapter);
        return  rootView;
    }

   /* public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomePageAdapter mHomePageAdapter = new HomePageAdapter();
        mHomePageAdapter.setOnRecyclerItemClickListener(new HomePageAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsContent.class);
                startActivity(intent);
            }
        });
    }*/
}
