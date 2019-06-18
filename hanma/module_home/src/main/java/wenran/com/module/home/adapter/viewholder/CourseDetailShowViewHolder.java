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
 * function：课程详情课程展示viewHolder
 */
public class CourseDetailShowViewHolder extends RecyclerView.ViewHolder {

    /**
     * 标题
     */
    @BindView(R2.id.course_detail_adapter_tv_title)
    public TextView courseDetailAdapterTvTitle;
    /**
     * 是否免费
     */
    @BindView(R2.id.course_detail_adapter_tv_free)
    public TextView courseDetailAdapterTvFree;
    /**
     * 时长
     */
    @BindView(R2.id.course_detail_adapter_tv_time)
    public TextView courseDetailAdapterTvTime;
    /**
     * 已学
     */
    @BindView(R2.id.course_detail_adapter_tv_speed)
    public TextView courseDetailAdapterTvSpeed;

    /**
     * 播放按钮
     */
    @BindView(R2.id.course_detail_adapter_iv_play)
    public ImageView courseDetailAdapterIvPlay;
    /**
     * 详情
     */
    @BindView(R2.id.course_detail_adapter_iv_detail)
    public ImageView courseDetailAdapterTvDetail;

    public CourseDetailShowViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
