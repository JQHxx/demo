package wenran.com.module.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.IOException;
import java.util.List;

import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.PlayerStatusEnum;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.MyToast;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.CourseDetailShowViewHolder;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.util.CourseProgressUtil;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class CourseDetailShowAdapter extends BasePlayerAdapter<CourseDetailShowViewHolder> {
    private boolean isBought;
    private String coverUrl;
    private List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classComments;

    public CourseDetailShowAdapter(Context context, boolean isBought, String coverUrl, List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classComments) {
        this.context = context;
        this.isBought = isBought;
        this.coverUrl = coverUrl;
        this.classComments = classComments;
        getCurrentPlayIdForInit();
        getCurrentPlayStatus();
    }

    @NonNull
    @Override
    public CourseDetailShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (classComments != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_course_detail_adapter, viewGroup, false);
            CourseDetailShowViewHolder viewHolder = new CourseDetailShowViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseDetailShowViewHolder viewHolder, final int i) {
        final CourseDetailShowResultBean.DataBean.ClassItemsBean classItemsBean = classComments.get(i);
        if (currentPlayId == classItemsBean.getId()) {
            if (playerStatus == PlayerStatusEnum.PLAYING_STATUS) {
                viewHolder.courseDetailAdapterTvTitle.setTextColor(context.getResources().getColor(R.color.common_tv_tc_ff943d));
                viewHolder.courseDetailAdapterIvPlay.setImageResource(R.mipmap.course_detail_pause);
            } else if (playerStatus==PlayerStatusEnum.COMPLETION_STATUS){
                viewHolder.courseDetailAdapterTvTitle.setTextColor(context.getResources().getColor(R.color.common_tv_tc_ff943d));
                viewHolder.courseDetailAdapterIvPlay.setImageResource(R.mipmap.course_detail_play);
            }
        } else {
            viewHolder.courseDetailAdapterIvPlay.setImageResource(R.mipmap.course_detail_play);
            viewHolder.courseDetailAdapterTvTitle.setTextColor(context.getResources().getColor(R.color.common_et_black_3333333));

        }
        if (isBought == false && classItemsBean.getFree() == 1) {
            viewHolder.courseDetailAdapterTvFree.setVisibility(View.VISIBLE);
        } else {
            viewHolder.courseDetailAdapterTvFree.setVisibility(View.GONE);
        }
        String minutes = TimeUtils.getIntMinutes(classItemsBean.getAudioLength());
        viewHolder.courseDetailAdapterTvTime.setText("时长" + minutes + "分");
        String speed = classItemsBean.getSpeed();
        int intProgress = CourseProgressUtil.getIntProgress(speed);
        viewHolder.courseDetailAdapterTvSpeed.setText("已学" + intProgress + "%");
        viewHolder.courseDetailAdapterIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealClick.deal(context, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        if (isBought || classItemsBean.getFree() == 1) {
                            dealPlayClick(i);
                        } else {
                            MyToast.s(context, "请购买", 0);
                        }
                    }
                });
            }
        });
        viewHolder.courseDetailAdapterTvTitle.setText(classItemsBean.getTitle());
        viewHolder.courseDetailAdapterTvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealClick.deal(context, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        ARouter.getInstance().build(RouterPath.COURSE_EXPLAIN_ACTIVITY)
                                .withString(ConstantTag.DATA_TAG.getTagValue(), classItemsBean.getId() + "")
                                .withInt(ConstantTag.AUDIO_LENGTH.getTagValue(), classItemsBean.getAudioLength())
                                .navigation(context);
                    }
                });
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealClick.deal(context, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        if (isBought || classItemsBean.getFree() == 1) {
                            dealPlayClick(i);
                        } else {
                            MyToast.s(context, "请购买", 0);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return classComments.size();
    }

    /**
     * 处理播放点击
     *
     * @param currentPosition
     */
    private void dealPlayClick(int currentPosition) {
        CourseDetailShowResultBean.DataBean.ClassItemsBean classItemsBean = classComments.get(currentPosition);
        int id = classItemsBean.getId();
        if (currentPlayId == id && this.playerStatus == PlayerStatusEnum.PLAYING_STATUS) {
            //做暂停操作
            MyToast.s(context, "暂停", 0);
            IPlayer navigation = RouterUtil.getIPlayer();
            navigation.pause();
        } else {
            //做播放操作
            MyToast.s(context, "播放", 0);
            IPlayer navigation =RouterUtil.getIPlayer();
            try {
                CourseInfo courseInfo = new CourseInfo();
                courseInfo.setCourseId(classItemsBean.getId());
                courseInfo.setTitle(classItemsBean.getTitle());
                courseInfo.setAudioUrl(classItemsBean.getAudio());
                courseInfo.setImgUrl(coverUrl);
                navigation.play(courseInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void refreshData(List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classComments) {
        this.classComments.clear();
        this.classComments = classComments;
        notifyDataSetChanged();
    }

    public void addData(List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classComments) {
        this.classComments.addAll(classComments);
        notifyDataSetChanged();
    }

}
