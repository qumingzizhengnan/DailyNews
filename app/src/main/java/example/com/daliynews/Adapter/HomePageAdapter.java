package example.com.daliynews.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import example.com.daliynews.OnRecyclerItemClickListener;
import example.com.daliynews.R;

/**
 * Created by CJ on 2018/3/27.
 */


public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    private OnRecyclerItemClickListener mRecyclerItemClickListener;//声明接口变量


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder =null;

        if(ONE_ITEM == viewType){
            View v = inflater.inflate(R.layout.home_page_layout_one,parent,false);
            holder = new OneViewHolder(v);
        }else {
            View v = inflater.inflate(R.layout.home_page_layout_two,parent,false);
            //holder = new OneViewHolder(v);
            holder = new TwoViewHolder(v);
        }



        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
       //判断是否设置了监听器
        if (mRecyclerItemClickListener != null){
            Log.d("HomePageAdapter","有监听器");
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mRecyclerItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener mRecyclerItemClickListener){
        this.mRecyclerItemClickListener = mRecyclerItemClickListener;
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
        return 15;
    }

    //item_one
    class OneViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView  tv_title;
        public OneViewHolder(View view){
            super(view);
            img = (ImageView) view.findViewById(R.id.image_item_one);
            tv_title = (TextView) view.findViewById(R.id.tv_item_one);
        }

    }

    //fragment1
    class TwoViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView  tv_title,tv_time;
        public TwoViewHolder(View view){
            super(view);
            img = (ImageView) view.findViewById(R.id.img_item);
            tv_title = (TextView) view.findViewById(R.id.tv_item);
            tv_time = (TextView) view.findViewById(R.id.tv_item_time);
        }
    }

}
