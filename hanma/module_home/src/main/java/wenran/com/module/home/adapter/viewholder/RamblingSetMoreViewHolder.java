package wenran.com.module.home.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wenran.com.module.home.R2;


/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function：漫谈集更多viewHolder
 */
public class RamblingSetMoreViewHolder extends RecyclerView.ViewHolder {
    /**
     * item是否选中
     */
    @BindView(R2.id.rambling_set_more_iv_square)
    public ImageView ramblingSetMoreIvSquare;
    /**
     * 标题
     */
    @BindView(R2.id.rambling_set_more_tv_title)
    public TextView ramblingSetMoreTvTitle;
    /**
     * 正在学习
     */
    @BindView(R2.id.rambling_set_more_tv_studying)
    public TextView ramblingSetMoreTvStudying;
    /**
     * 年
     */
    @BindView(R2.id.rambling_set_more_tv_year)
    public TextView ramblingSetMoreTvYear;
    /**
     * 时间
     */
    @BindView(R2.id.rambling_set_more_tv_time)
    public TextView ramblingSetMoreTvTime;
    /**
     * 进度
     */
    @BindView(R2.id.rambling_set_more_tv_progress)
    public TextView ramblingSetMoreTvProgress;
    /**
     * 详情
     */
    @BindView(R2.id.rambling_set_more_iv_detail)
    public ImageView ramblingSetMoreIvDetail;
    /**
     * 播放器
     */
    @BindView(R2.id.rambling_set_more_iv_play)
    public ImageView ramblingSetMoreIvPlay;

    public RamblingSetMoreViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
