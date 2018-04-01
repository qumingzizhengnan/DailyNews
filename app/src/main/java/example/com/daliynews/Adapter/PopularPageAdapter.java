package example.com.daliynews.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.com.daliynews.R;
import example.com.daliynews.interfaces.OnItemClickListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CJ on 2018/3/26.
 */

public class PopularPageAdapter extends RecyclerView.Adapter<PopularPageAdapter.AuthorViewHolder>{


    //接受来popular fragment 的数据源
    ArrayList<String> titleList;
    //ArrayList<String> descripeList;
    ArrayList<String> picUrlList;
    ArrayList<String> dateList;
    private Context context;

    //实例化点击时间接口
    private OnItemClickListener onItemClickListener;

//构造函数  接受context参数 和 数据源
    public PopularPageAdapter(Context context, ArrayList<List<String>> list){
        this.context = context;

        if(list!=null){
            Log.d("tag","container is not empty, size = " + list.size());

            titleList = (ArrayList<String>) list.get(0);
            //descripeList = (ArrayList<String>) list.get(1);
            dateList = (ArrayList<String>) list.get(3);
            picUrlList = (ArrayList<String>) list.get(4);

            Log.d("tag","titleList is not empty, size = " + titleList.size());
            //Log.d("tag","descripeList is not empty, size = " + descripeList.size());
            Log.d("tag","picUrlList is not empty, size = " + picUrlList.size());
        } else {
            Log.d("tag","Container is  empty " );
        }
    }


    @Override
    public PopularPageAdapter.AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.popular_page_layout,parent,false);

        PopularPageAdapter.AuthorViewHolder viewHolder= new AuthorViewHolder(childView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(final PopularPageAdapter.AuthorViewHolder holder, int position) {

        holder.mTitleView.setText(titleList.get(position));
        holder.mTimeView.setText(dateList.get(position));

        //添加监听事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }

        //异步下载图片并且加载
        DownLoadTask task = new DownLoadTask(holder.imgItem);
        task.execute(picUrlList.get(position));


    }

    //监听函数
    public  void setOnItemClickListner(OnItemClickListener onItemClickListner){

        this.onItemClickListener=onItemClickListner;
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder{

        //TODO list 每一行的内容
        ImageView imgItem;
        TextView mTitleView;
        TextView mTimeView;

        public AuthorViewHolder(View itemView){
            super(itemView);
            //TODO list 每一行内容初始化
            imgItem = (ImageView) itemView.findViewById(R.id.iv_portrait_popular);
            mTitleView = (TextView) itemView.findViewById(R.id.tv_title_popular);
            mTimeView = (TextView) itemView.findViewById(R.id.tv_time_popular);
        }
    }


    //异步加载图片
    class DownLoadTask extends AsyncTask<String ,Void,BitmapDrawable> {
        private ImageView mImageView;
        String url;
        public DownLoadTask(ImageView imageView){
            mImageView = imageView;
        }
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = downLoadBitmap(url);
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(),bitmap);
            return  drawable;
        }

        private Bitmap downLoadBitmap(String url) {
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                bitmap = BitmapFactory.decodeStream(response.body().byteStream());
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

}
