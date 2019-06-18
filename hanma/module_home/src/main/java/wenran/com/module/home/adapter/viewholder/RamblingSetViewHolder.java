package wenran.com.module.home.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wenran.com.module.home.R;


/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function：漫谈集viewHolder
 */
public class RamblingSetViewHolder extends RecyclerView.ViewHolder {
    /**
     * 播放按钮
     */
    public ImageView homeRamblingSetAdapterIvPlay;
    /**
     * 标题内容
     */
    public TextView homeRamblingSetAdapterTvTitle;
    /**
     * 详情按钮
     */
    public ImageView homeRamblingSetAdapterIvDetail;

    public RamblingSetViewHolder(@NonNull View itemView) {
        super(itemView);
        homeRamblingSetAdapterIvPlay = itemView.findViewById(R.id.home_rambling_set_adapter_iv_play);
        homeRamblingSetAdapterTvTitle = itemView.findViewById(R.id.home_rambling_set_adapter_tv_title);
        homeRamblingSetAdapterIvDetail = itemView.findViewById(R.id.home_rambling_set_adapter_iv_detail);
    }
}
