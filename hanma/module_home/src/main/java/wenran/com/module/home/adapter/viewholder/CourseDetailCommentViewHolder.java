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
 * function：课程详情评论viewHolder
 */
public class CourseDetailCommentViewHolder extends RecyclerView.ViewHolder {

    /**
     * 头像
     */
    @BindView(R2.id.comment_iv_avatar)
    public ImageView commentIvAvatar;
    /**
     * 名字
     */
    @BindView(R2.id.comment_tv_name)
    public TextView commentTvName;
    /**
     * 喜欢
     */
    @BindView(R2.id.comment_iv_like)
    public ImageView commentIvLike;
    /**
     * 喜欢个数
     */
    @BindView(R2.id.comment_tv_like_count)
    public TextView commentIvLikeCount;

    /**
     * 内容
     */
    @BindView(R2.id.comment_tv_content)
    public TextView commentTvContent;


    public CourseDetailCommentViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
