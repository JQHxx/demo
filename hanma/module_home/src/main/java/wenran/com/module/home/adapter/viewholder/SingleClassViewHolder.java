package wenran.com.module.home.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wenran.com.baselibrary.base.widget.NoPaddingTv;
import wenran.com.module.home.R;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function：单项课viewHolder
 */
public class SingleClassViewHolder extends RecyclerView.ViewHolder {
    /**
     * 单项课图片
     */
    public ImageView singleClassIvImg;
    /**
     * 标题
     */
    public TextView singleClassTvTitle;
    /**
     * 作者
     */
    public TextView singleClassTvAuthor;
    /**
     * 课程时长
     */
    public TextView singleClassTvTime;
    /**
     * 已售出数量
     */
    public NoPaddingTv singleClassTvSaleNum;
    /**
     * 课程价格
     */
    public NoPaddingTv singleClassTvPrice;

    public SingleClassViewHolder(@NonNull View itemView) {
        super(itemView);
        singleClassIvImg = itemView.findViewById(R.id.single_class_iv_img);
        singleClassTvTitle = itemView.findViewById(R.id.single_class_tv_title);
        singleClassTvAuthor = itemView.findViewById(R.id.single_class_tv_author);
        singleClassTvTime = itemView.findViewById(R.id.single_class_tv_time);
        singleClassTvSaleNum = itemView.findViewById(R.id.single_class_tv_sale_num);
        singleClassTvPrice = itemView.findViewById(R.id.single_class_tv_price);
    }
}
