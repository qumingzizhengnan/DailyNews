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
 *
 * this is for homepage's recyclerView adapter
 */


/**
 *Adapter for TabFragmentHome's recyclerView
 *
 */
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;


    //datas from the internet
    ArrayList<String> mTitleList;
    ArrayList<String> mPicUrlList;
    ArrayList<String> mDateList;
    private Application mContext;


    private OnItemClickListener onItemClickListener;//声明接口变量

    /**
     * Constructor
     * @param context
     * @param list  datas from internet
     */
   public HomePageAdapter(Application context, ArrayList<ArrayList<String>> list){
        this.mContext = context;

        if(list!=null){
            mTitleList = (ArrayList<String>) list.get(0);
            mDateList = (ArrayList<String>) list.get(3);
            mPicUrlList = (ArrayList<String>) list.get(4);

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

    /**
     * Bind data with widget
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof OneViewHolder){
            ((OneViewHolder)holder).mTv.setText(mTitleList.get(position));

            //异步下载图片并且加载
            DownLoadImgUtil task = new DownLoadImgUtil(((OneViewHolder) holder).mImg,mContext);
            task.execute(mPicUrlList.get(position));


        } else {

            ((TwoViewHolder)holder).mTitle.setText(mTitleList.get(position));
            ((TwoViewHolder)holder).mTime.setText(mDateList.get(position));
            //异步下载图片并且加载
            DownLoadImgUtil task = new DownLoadImgUtil(((TwoViewHolder) holder).mImgItem,mContext);
            task.execute(mPicUrlList.get(position));

        }


       //添加监听事件  add click listener
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
    /**
     * set clickListener
     * @param mOnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.onItemClickListener = mOnItemClickListener;
    }

    /**
     * return view type
     *
     * @param position  current view's  position
     * @return
     */
    @Override
    public int getItemViewType(int position){
        if(position==0){
            return ONE_ITEM;
        }else {
            return TWO_ITEM;
        }
    }

    /**
     *
     * @return the number of view in RecyclerView
     */
    @Override
    public int getItemCount(){
        return 10;
    }


    /**
     * this class just a view use for showing the first news in HomePage
     */
    class OneViewHolder extends RecyclerView.ViewHolder{

        ImageView mImg;
        TextView  mTv;
        public OneViewHolder(View view){
            super(view);
            mImg = (ImageView) view.findViewById(R.id.image_item_one);
            mTv = (TextView) view.findViewById(R.id.tv_item_one);
        }

    }


    /**
     * this class just a view use for showing the other news in HomePage
     */
    class TwoViewHolder extends RecyclerView.ViewHolder {

       //TODO list 每一行的内容
        ImageView mImgItem;
        TextView mTitle;
        TextView mTime;

        public TwoViewHolder(View itemView){
            super(itemView);
            //TODO list 每一行内容初始化
            mImgItem = (ImageView) itemView.findViewById(R.id.iv_portrait_home);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title_home);
            mTime = (TextView) itemView.findViewById(R.id.tv_time_home);
        }
    }
}
