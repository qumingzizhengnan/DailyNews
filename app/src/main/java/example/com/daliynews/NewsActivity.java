/**
 * 
 * This activity is used for presenting detail news
 *
 * 
 */

package example.com.daliynews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import example.com.daliynews.database.DBOperation;
import example.com.daliynews.until.DownLoadImgUtil;
import example.com.daliynews.until.NetWorkUtil;
import example.com.daliynews.until.SharedPreferenceCacheUtil;
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
 * present detail news
 */
public class NewsActivity extends AppCompatActivity {
    //从上个页面获取要分析的news 网址
    private Intent mIintentReceiveURL;
    private String mFormerPageImgUrl;
    private TextView tvTitle;
    private TextView tvContent;
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);


        mImg = (ImageView) findViewById(R.id.img_newPicture);
        tvTitle = (TextView) findViewById(R.id.tv_newsTitle);
        tvContent = (TextView) findViewById(R.id.tv_newsContent);


        //获取网址  get the url of this news from the intent
        mIintentReceiveURL = getIntent();
        final String urlNews = mIintentReceiveURL.getStringExtra("URL");
        mFormerPageImgUrl = mIintentReceiveURL.getStringExtra("IMG_URL");

        if(!urlNews.isEmpty()){
            //Log.d("tag","url is :"+urlNews);
        }

        if(NetWorkUtil.isNetworkConnected(this)){
            //下载html文件，分析  download html file and analyse
            Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    //连接网络，获得xml数据
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request requset = new Request.Builder()
                            .url(urlNews)
                            .build();
                    final Call call = okHttpClient.newCall(requset);
                    Response response=null;
                    try{
                        response= call.execute();
                    } catch (IOException ee){
                        ee.printStackTrace();
                    }

                    if(response!=null){
                        String xmlResult = response.body().string();
                        //Log.d("tag","得到html文件");
                        //Log.d("tag",xmlResult);
                        e.onNext(xmlResult);
                        //saveToTxt(xmlResult,"news.txt");

                    } else {
                        Log.d("tag","没有获取到html文件");
                    }

                }
            });

            Consumer<String> consumer = new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {


                    StringBuilder stringBuilderContent = new StringBuilder();
                    //doc实例化
                    Document doc = Jsoup.parse(s);

//                    if(doc!=null){
//                        Log.d("html","Jsoup实例化成功");
//                    } else {
//                        Log.d("html","Jsoup实例化失败");
//                    }

                    Elements elements = doc.getElementsByClass("story-body");
                    //截取部分后重新实例化document
                    doc = Jsoup.parseBodyFragment(elements.html());

                    //Log.d("html",doc.toString());
                    //saveToTxt(doc.toString(),"story_body.txt");
                    //获取标题
                    Elements title = doc.getElementsByClass("story-body__h1");
                    //Log.d("html","title of news =  "+ title.text());

                    //update  news title
                    tvTitle.setText(title.text());
                    SharedPreferenceCacheUtil.editor.putString("title"+urlNews,title.text());

                    //获取时间
//                    Elements time = doc.select("div.mini-info-list-wrap ul li div");
//                    if(time!=null){
//                        Log.d("html","size of time is  "+time.size());
//                        Log.d("html","time is :"+ time.text());
//                    } else {
//                        Log.d("html","don't get time element");
//                    }

                    //获取正文内容
                    Elements elementsP = doc.select("div.story-body__inner p");
                    //Log.d("html","P size = "+ elementsP.size());
                    for(Element e: elementsP){
                        //Log.d("tag",e.text());
                        stringBuilderContent.append(e.text() + "\n\n");
                    }


                    //set new content
                    tvContent.setText(stringBuilderContent.toString());
                    SharedPreferenceCacheUtil.editor.putString("content"+urlNews,stringBuilderContent.toString());
                    SharedPreferenceCacheUtil.editor.commit();

                    //获取网页图片
                    String srcUrl ="";
                    Elements elementsSpanImg = doc.select("span.image-and-copyright-container");
                    if(elementsSpanImg.size()!=0){
                        srcUrl= elementsSpanImg.get(0).child(0).attr("src");
                        Log.d("tag","img url is "+srcUrl);

                        if(!srcUrl.isEmpty()){
                            DownLoadImgUtil task = new DownLoadImgUtil(mImg,getApplication(),true,"img"+urlNews);
                            task.execute(srcUrl);
                        }else {
//                            Log.d("tag","获取图片失败");
//                            Log.d("tag",elementsSpanImg.toString());
                            srcUrl= elementsSpanImg.get(0).child(0).attr("data-src");
                            Log.d("tag","img url is"+srcUrl);
                            DownLoadImgUtil task = new DownLoadImgUtil(mImg,getApplication(),true,"img"+urlNews);
                            task.execute(srcUrl);
                        }


                    } else {  //this is for a news html that contains video

                        DownLoadImgUtil task = new DownLoadImgUtil(mImg,getApplication(),true,"img"+urlNews);
                        task.execute(mFormerPageImgUrl);
                    }






                    //download img from net
//                    for(int i=0;i<elementsSpanImg.size();i++){
//                        img[i] = elementsSpanImg.get(i).child(0);
//
//                        String srcUrl="";
//                        if(img[i].hasAttr("src")){
//                            srcUrl = img[i].attr("src");
//                            //DownLoadImgUtil task = new DownLoadImgUtil(mImg,getApplicationContext());
//                            //task.execute(srcUrl);
//                            break;
//                        } else if(img[i].hasAttr("data-src")){
//                            //srcUrl = img[i].attr("data-src");
//                            //DownLoadImgUtil task = new DownLoadImgUtil(mImg,getApplicationContext());
//                            //task.execute(srcUrl);
//                            break;
//                        }
//                    }


                }
            };

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);
        } else {
            Log.d("tag","news activity, no network");

            //get data restored in preference
            SharedPreferences pref = getSharedPreferences("NewsDetailData", MODE_PRIVATE);
            tvTitle.setText(pref.getString("title"+urlNews,"error"));
            tvContent.setText(pref.getString("content"+urlNews,"error"));


            //get data restored in database
            boolean result = new DBOperation(getApplication()).isEmpty("img"+urlNews);
            Log.d("tag","database has record? "+result);
            if(result==false){
                byte[] img = new DBOperation(getApplication()).readImage("img"+urlNews);
                Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
                BitmapDrawable drawable = new BitmapDrawable(getApplication().getResources(),bitmap);
                mImg.setImageDrawable(drawable);
            }


        }

    }


    public void saveToTxt(String inputText ,String fileName) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("tag",fileName+" has saved");
    }
}
