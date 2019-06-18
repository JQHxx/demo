package wenran.com.module.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.CourseDetailCommentViewHolder;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.bean.LikeParamBean;
import wenran.com.module.home.contract.CourseDetailShowContract;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class CourseDetailCommentAdapter extends RecyclerView.Adapter<CourseDetailCommentViewHolder> {
    private Context context;
    private List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classCommentsBeans;
    private CourseDetailShowContract.ICourseDetailShowPresenter presenter;
    private int currentPlayId = -1;

    public CourseDetailCommentAdapter(Context context, List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classCommentsBeans, CourseDetailShowContract.ICourseDetailShowPresenter presenter) {
        this.context = context;
        this.classCommentsBeans = classCommentsBeans;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CourseDetailCommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (classCommentsBeans != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_course_detail_comment_adapter, viewGroup, false);
            CourseDetailCommentViewHolder viewHolder = new CourseDetailCommentViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseDetailCommentViewHolder viewHolder, final int i) {
        final CourseDetailShowResultBean.DataBean.ClassCommentsBean classCommentsBean = classCommentsBeans.get(i);
        GlideUtil.setCircleImageByUrl(context, classCommentsBean.getUser().getAvatar(), viewHolder.commentIvAvatar, null, null);
        viewHolder.commentTvName.setText(classCommentsBean.getUser().getNickname());
        viewHolder.commentIvLikeCount.setText(classCommentsBean.getCount() + "");
        viewHolder.commentTvContent.setText(classCommentsBean.getContent());
        boolean fabulous = classCommentsBean.isFabulous();
        if (fabulous) {
            GlideUtil.setImageByRes(context, R.mipmap.comment_like, viewHolder.commentIvLike);
        } else {
            GlideUtil.setImageByRes(context, R.mipmap.comment_unlike, viewHolder.commentIvLike);
        }
        viewHolder.commentIvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLike(classCommentsBean,i, viewHolder.commentIvLike,
                        classCommentsBean.getId(),viewHolder.commentIvLikeCount);
            }
        });


    }

    @Override
    public int getItemCount() {
        return classCommentsBeans.size();
    }


    public void refreshData(List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classCommentsBeans) {
        this.classCommentsBeans.clear();
        this.classCommentsBeans = classCommentsBeans;
        notifyDataSetChanged();
    }

    public void addData(List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classCommentsBeans) {
        this.classCommentsBeans.addAll(classCommentsBeans);
        notifyDataSetChanged();
    }


    /**
     * 点赞
     */
    private void doLike(final CourseDetailShowResultBean.DataBean.ClassCommentsBean classCommentsBean
            , int postition, final ImageView currentIv, final int commentId
    , final TextView commentTv) {
        DealClick.deal(context, new DealClick.ClickCallback() {
            @Override
            public void isSuccess() {
                LikeParamBean likeParamBean = new LikeParamBean(commentId, null);
                presenter.doLike(likeParamBean, new INormalCallback<StandardResultBean>() {
                    @Override
                    public void success(StandardResultBean msg) {
                        if (classCommentsBean.isFabulous()) {
                            GlideUtil.setImageByRes(context, R.mipmap.comment_unlike, currentIv);
                            classCommentsBean.setFabulous(false);

                            classCommentsBean.setCount(classCommentsBean.getCount() - 1);
                            commentTv.setText(classCommentsBean.getCount()+"");
                        } else {
                            GlideUtil.setImageByRes(context, R.mipmap.comment_like, currentIv);
                            classCommentsBean.setFabulous(true);
                            classCommentsBean.setCount(classCommentsBean.getCount() + 1);
                            commentTv.setText(classCommentsBean.getCount()+"");
                        }
                    }

                    @Override
                    public void failure(String failureInfo) {

                    }
                });
            }
        });
    }
}
