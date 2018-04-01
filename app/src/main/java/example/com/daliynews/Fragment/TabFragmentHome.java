package example.com.daliynews.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import example.com.daliynews.Adapter.HomePageAdapter;
import example.com.daliynews.Adapter.PopularPageAdapter;
import example.com.daliynews.NewsActivity;
import example.com.daliynews.R;
import example.com.daliynews.interfaces.OnItemClickListener;
import example.com.daliynews.until.NetWorkUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CJ on 2018/3/26.
 */

public class TabFragmentHome extends Fragment {

    private View rootView;

    public static Fragment newInstance() {
        TabFragmentHome fragment = new TabFragmentHome();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        rootView = inflater.inflate(R.layout.fragment_tab,container,false);


        if(NetWorkUtil.isNetworkConnected(getContext())){

            Observable<ArrayList<List<String>>> observable =  Observable.create(new ObservableOnSubscribe<ArrayList<List<String>>>() {
                @Override
                public void subscribe(final ObservableEmitter<ArrayList<List<String>>> emitter ) throws Exception {

                    //连接网络，获得xml数据
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request requset = new Request.Builder()
                            .url("http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk")
                            .build();
                    final Call call = okHttpClient.newCall(requset);
                    Response response=null;
                    try{
                        response= call.execute();
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    String xmlResult = response.body().string();
                    ArrayList<List<String>> container  = parseXMLWithPull(xmlResult);

                    emitter.onNext(container);
                    Log.d("tag","发送container");
                }
            });

            Consumer<ArrayList<List<String>>> consumer = new Consumer<ArrayList<List<String>>>() {
                @Override
                public void accept(final ArrayList<List<String>> containerList) throws Exception {
                    //RecycleView 的初始化
                    RecyclerView recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setLayoutManager(layoutManager);
                    HomePageAdapter adapter = new HomePageAdapter(getActivity(),containerList);

                    //为Adpter 设置监听事件
                    adapter.setOnItemClickListen(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(getActivity(),  ((ArrayList<String>)containerList.get(2)).get(position), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),NewsActivity.class);
                            intent.putExtra("URL",((ArrayList<String>)containerList.get(2)).get(position));
                            startActivity(intent);


                        }
                    });
                    recyclerView.setAdapter(adapter);

                }
            };

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);


        } else {
            Log.d("ERR","网络无连接");
            Toast.makeText(getContext(), "网络无连接", Toast.LENGTH_SHORT).show();
        }





//        RecyclerView recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        recyclerView.setLayoutManager(layoutManager);
//        HomePageAdapter mHomePageAdapter = new HomePageAdapter();
//        mHomePageAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(getActivity(),  "sssssssss", Toast.LENGTH_SHORT).show();
//                //Intent intent = new Intent(getActivity(), NewsActivity.class);
//                //startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(mHomePageAdapter);
        return  rootView;
    }

    private ArrayList<List<String>> parseXMLWithPull(String xmlData) {

        ArrayList<List<String>> container = new ArrayList<List<String>>();
        ArrayList<String> descriptionList = new ArrayList<String>();
        ArrayList<String> linkList = new ArrayList<String>();
        ArrayList<String> dateList = new ArrayList<String>();
        ArrayList<String> pictureUrlList = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();

            String title = "";
            String description = "";
            String link = "";
            String date="";
            String pictureUrl="";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //  开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("title".equals(nodeName)) {
                            title = xmlPullParser.nextText();

                        } else if ("description".equals(nodeName)) {
                            description = xmlPullParser.nextText();

                        } else if ("link".equals(nodeName)) {
                            link = xmlPullParser.nextText();

                        } else if ("pubDate".equals(nodeName)) {
                            date = xmlPullParser.nextText();

                        } else if ("media:thumbnail".equals(nodeName)) {
                            pictureUrl = xmlPullParser.getAttributeValue(2).toString();

                        }
                        break;
                    }

                    //  完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("item".equals(nodeName)) {
//                            Log.d("MainActivity", "title is " + title);
//                            Log.d("MainActivity", "description is " + description);
//                            Log.d("MainActivity", "link is " + link);
//                            Log.d("MainActivity", "date is " + date);
//                            Log.d("MainActivity", "pictureUrl is " + pictureUrl);
                            titleList.add(title);
                            descriptionList.add(description);
                            linkList.add(link);
                            dateList.add(date);
                            pictureUrlList.add(pictureUrl);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        container.add(titleList);
        container.add(descriptionList);
        container.add(linkList);
        container.add(dateList);
        container.add(pictureUrlList);

        Log.d("tag","解析结束");
        return  container;
    }

}
