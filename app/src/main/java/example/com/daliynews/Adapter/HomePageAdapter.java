package example.com.daliynews.Adapter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.daliynews.R;
import example.com.daliynews.interfaces.OnItemClickListener;
import example.com.daliynews.until.DownLoadImgUtil;

/**
 * Created by CJ on 2018/3/27.
 */

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    ArrayList<String> titleList;
    ArrayList<String> picUrlList;
    ArrayList<String> dateList;
    private Application context;

    private OnItemClickListener onItemClickListener;//声明接口变量

   public HomePageAdapter(Application context, ArrayList<ArrayList<String>> list){
        this.context = context;


        if(list!=null){
            titleList = (ArrayList<String>) list.get(0);
            dateList = (ArrayList<String>) list.get(3);
            picUrlList = (ArrayList<String>) list.get(4);

        } else {
            Log.d("tag","Container is  empty " );
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder =null;

        if(ONE_ITEM == viewType){
            View v = inflater.inflate(R.layout.home_page_layout_one,parent,false);
            holder = new OneViewHolder(v);
        }else {
            View v = inflater.inflate(R.layout.home_page_layout_two,parent,false);
            holder = new TwoViewHolder(v);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof OneViewHolder){
            ((OneViewHolder)holder).tv.setText(titleList.get(position));

            //异步下载图片并且加载
            DownLoadImgUtil task = new DownLoadImgUtil(((OneViewHolder) holder).img,context);
            task.execute(picUrlList.get(position));


        } else {

            ((TwoViewHolder)holder).mTitle.setText(titleList.get(position));
            ((TwoViewHolder)holder).mTime.setText(dateList.get(position));
            //异步下载图片并且加载
            DownLoadImgUtil task = new DownLoadImgUtil(((TwoViewHolder) holder).imgItem,context);
            task.execute(picUrlList.get(position));

        }


       //添加监听事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
    }


    //监听事件
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position){
        if(position==0){
            return ONE_ITEM;
        }else {
            return TWO_ITEM;
        }
    }

    @Override
    public int getItemCount(){
        return 10;
    }


    public void setOnItemClickListen(OnItemClickListener onItemClickListen){
       this.onItemClickListener = onItemClickListen;
    }


    class OneViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView  tv;
        public OneViewHolder(View view){
            super(view);
            img = (ImageView) view.findViewById(R.id.image_item_one);
            tv = (TextView) view.findViewById(R.id.tv_item_one);
        }

    }


    class TwoViewHolder extends RecyclerView.ViewHolder {

       //TODO list 每一行的内容
        ImageView imgItem;
        TextView mTitle;
        TextView mTime;

        public TwoViewHolder(View itemView){
            super(itemView);
            //TODO list 每一行内容初始化
            imgItem = (ImageView) itemView.findViewById(R.id.iv_portrait_home);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title_home);
            mTime = (TextView) itemView.findViewById(R.id.tv_time_home);
        }
    }
}
