package example.com.daliynews.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.com.daliynews.OnRecyclerItemClickListener;
import example.com.daliynews.R;

/**
 * Created by CJ on 2018/3/26.
 */

public class PopularPageAdapter extends RecyclerView.Adapter<PopularPageAdapter.AuthorViewHolder>{

    private OnRecyclerItemClickListener mRecyclerItemClickListener;//声明接口变量

    @Override
    public PopularPageAdapter.AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.home_page_layout_two,parent,false);

        PopularPageAdapter.AuthorViewHolder viewHolder= new AuthorViewHolder(childView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(final PopularPageAdapter.AuthorViewHolder holder, int position) {
        //判断是否设置了监听器
        if (mRecyclerItemClickListener != null){
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
    public int getItemCount() {
        return 15;
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder{

        //TODO list 每一行的内容
        //ImageView imgItem;
        TextView tvItem;

//        TextView mNickNameView;
//        TextView mMottoView;
        public AuthorViewHolder(View itemView){

            super(itemView);

            //TODO list 每一行内容初始化
            //imgItem = (ImageView) itemView.findViewById(R.id.img_item);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);

//            mNickNameView = (TextView) itemView.findViewById(R.id.tv_nickname);
//            mMottoView = (TextView) itemView.findViewById(R.id.tv_motto);
        }
    }
}
