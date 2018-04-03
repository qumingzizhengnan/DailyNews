package example.com.daliynews.until;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import example.com.daliynews.database.DBOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadImgUtil extends AsyncTask<String ,Void,BitmapDrawable> {
    private ImageView mImageView;
    String url;
    Context mContext;
    boolean mCache =false;
    public DownLoadImgUtil(ImageView imageView ,Context context){
        mImageView = imageView;
        mContext = context;
    }

    public DownLoadImgUtil(ImageView imageView ,Context context ,boolean cache){
        mImageView = imageView;
        mContext = context;
        mCache = cache;
    }
    @Override
    protected BitmapDrawable doInBackground(String... params) {
        url = params[0];
        Bitmap bitmap = downLoadBitmap(url,mCache,mContext);
        BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(),bitmap);
        return  drawable;
    }

    public static Bitmap downLoadBitmap(String url ,boolean cache,Context mContext) {
        Bitmap bitmap = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            byte[] img = response.body().bytes();
            String imgFile = new String(img,0,img.length);
            //bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
           /* if(cache){
                //TODO: save the picture
                DBOperation.saveImage(mContext,img);
                Log.d("tag","insert img data");
            }*/


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }



    @Override
    protected void onPostExecute(BitmapDrawable drawable) {
        super.onPostExecute(drawable);

        if ( mImageView != null && drawable != null){
            mImageView.setImageDrawable(drawable);
        }
    }
}
