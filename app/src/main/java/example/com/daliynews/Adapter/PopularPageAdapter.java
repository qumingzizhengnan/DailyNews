package example.com.daliynews.Adapter;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.daliynews.R;
import example.com.daliynews.interfaces.OnItemClickListener;
import example.com.daliynews.until.DownLoadImgUtil;

/**
 * Created by CJ on 2018/3/26.
 *
 * this is for homepage's recyclerView adapter
 */


/**
 *Adapter for TabFragmentPopular's recyclerView
 *
 */
public class PopularPageAdapter extends RecyclerView.Adapter<PopularPageAdapter.AuthorViewHolder>{


    //接受来popular fragment 的数据源
    //receive data from  popular fragment
    private ArrayList<String> titleList;
    private ArrayList<String> picUrlList;
    private ArrayList<String> dateList;
    private Application context;
    private LinearLayoutManager mLinearLayout;

    //实例化点击时间接口
    private OnItemClickListener onItemClickListener;

    //构造函数  接受context参数 和 数据源  constructor

    /**
     *  constructor
     * @param context
     * @param list
     * @param linearLayout
     */
    public PopularPageAdapter(Application context, ArrayList<List<String>> list, LinearLayoutManager linearLayout){

        this.context = context;
        this.mLinearLayout = linearLayout;

        if(list!=null){
            Log.d("tag","container is not empty, size = " + list.size());

            titleList = (ArrayList<String>) list.get(0);
            dateList = (ArrayList<String>) list.get(3);
            picUrlList = (ArrayList<String>) list.get(4);

            //Log.d("tag","titleList is not empty, size = " + titleList.size());
            //Log.d("tag","descripeList is not empty, size = " + descripeList.size());
           // Log.d("tag","picUrlList is not empty, size = " + picUrlList.size());
        } else {
            Log.d("tag","Container is  empty " );
        }
    }


    /**
     * return a view used for show my news
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PopularPageAdapter.AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.popular_page_layout,parent,false);

        PopularPageAdapter.AuthorViewHolder viewHolder= new AuthorViewHolder(childView);
        return  viewHolder;
    }

    /**
     * Bind data with widget
     * @param holder
     * @param position
     */
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

        //异步下载图片并且加载  download Img
        DownLoadImgUtil task = new DownLoadImgUtil(holder.imgItem,context);
        task.execute(picUrlList.get(position));


    }


    //监听事件
    /**
     * set clickListener
     * @param onItemClickListner
     */
    public  void setOnItemClickListner(OnItemClickListener onItemClickListner){

        this.onItemClickListener=onItemClickListner;
    }


    /**
     *
     * @return the number of view in RecyclerView
     */
    @Override
    public int getItemCount() {
        return 10;
    }

    /**
     * this class just a view use for showing the  news in popularPage
     */
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


}
