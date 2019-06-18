package wenran.com.module.home.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wenran.com.baselibrary.base.widget.NoPaddingTv;
import wenran.com.module.home.R2;


/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function：搜索结果展示viewHolder
 */
public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    /**
     * 搜索结果图片
     */
    @BindView(R2.id.search_result_iv_img)
    public ImageView searchResultIvImg;
    /**
     * 标题
     */
    @BindView(R2.id.search_result_tv_title)
    public TextView searchResultTvTitle;
    /**
     * 作者
     */
    @BindView(R2.id.search_result_tv_author)
    public TextView searchResultTvAuthor;
    /**
     * 课程时长
     */
    @BindView(R2.id.search_result_tv_time)
    public NoPaddingTv searchResultTvTime;
    /**
     * 已售出数量
     */
    @BindView(R2.id.search_result_tv_sale_num)
    public TextView searchResultTvSaleNum;
    /**
     * 课程价格
     */
    @BindView(R2.id.search_result_tv_price)
    public NoPaddingTv searchResultTvPrice;

    @BindView(R2.id.search_result_v_line)
    public View searchResultLine;

    /**
     * 箭头
     */
    @BindView(R2.id.search_result_iv_arrow)
    public ImageView searchResultIvArrow;
    public SearchResultViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
