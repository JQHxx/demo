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
 * function：专题课viewHolder
 */
public class SpecialClassViewHolder extends RecyclerView.ViewHolder {
    /**
     * 单项课图片
     */
    public ImageView specialClassIvImg;
    /**
     * 标题
     */
    public TextView specialClassTvTitle;
    /**
     * 作者
     */
    public TextView specialClassTvAuthor;
    /**
     * 课程时长
     */
    public TextView specialClassTvTime;
    /**
     * 已售出数量
     */
    public TextView specialClassTvSaleNum;
    /**
     * 课程价格
     */
    public TextView specialClassTvPrice;

    public SpecialClassViewHolder(@NonNull View itemView) {
        super(itemView);
        specialClassIvImg = itemView.findViewById(R.id.special_class_iv_img);
        specialClassTvTitle = itemView.findViewById(R.id.special_class_tv_title);
        specialClassTvAuthor = itemView.findViewById(R.id.special_class_tv_author);
        specialClassTvTime = itemView.findViewById(R.id.special_class_tv_time);
        specialClassTvSaleNum = itemView.findViewById(R.id.special_class_tv_sale_num);
        specialClassTvPrice = itemView.findViewById(R.id.special_class_tv_price);
    }
}
