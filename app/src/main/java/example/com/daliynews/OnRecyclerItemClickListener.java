package example.com.daliynews;

import android.view.View;

/**
 * Created by echo on 2018/3/30.
 */

public interface OnRecyclerItemClickListener{
    /*
     * item view 回调方法
     * @param view 被点击的view
     * @param position 点击索引
    * */
    void onItemClick(View view, int position);
}
