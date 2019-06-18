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
import wenran.com.module.home.adapter.viewholder.RamblingSetMoreViewHolder;
import wenran.com.module.home.bean.RamblingSetMoreResultBean;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class RamblingSetMoreAdapter extends BasePlayerAdapter<RamblingSetMoreViewHolder> {
    List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble;
    public RamblingSetMoreAdapter(Context context, List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble) {
        this.context = context;
        this.ramble = ramble;
        getCurrentPlayIdForInit();
        getCurrentPlayStatus();
    }

    @NonNull
    @Override
    public RamblingSetMoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (ramble != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_rambling_set_more_adapter, viewGroup, false);
            RamblingSetMoreViewHolder viewHolder = new RamblingSetMoreViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RamblingSetMoreViewHolder viewHolder, final int i) {
        final RamblingSetMoreResultBean.DataBean.RambleBean rambleBean = ramble.get(i);
        if (currentPlayId == rambleBean.getId()) {
            if (playerStatus==PlayerStatusEnum.PLAYING_STATUS){
                viewHolder.ramblingSetMoreTvTitle.setTextColor(context.getResources().getColor(R.color.common_tv_tc_ff943d));
                viewHolder.ramblingSetMoreIvPlay.setImageResource(R.mipmap.home_rambling_set_pause);
            }else if (playerStatus==PlayerStatusEnum.COMPLETION_STATUS){
                viewHolder.ramblingSetMoreTvTitle.setTextColor(context.getResources().getColor(R.color.common_tv_tc_ff943d));
                viewHolder.ramblingSetMoreIvPlay.setImageResource(R.mipmap.home_rambling_set_play);
            }
            viewHolder.ramblingSetMoreIvSquare.setVisibility(View.VISIBLE);
            viewHolder.ramblingSetMoreTvStudying.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ramblingSetMoreIvSquare.setVisibility(View.GONE);
            viewHolder.ramblingSetMoreTvStudying.setVisibility(View.GONE);
            viewHolder.ramblingSetMoreIvPlay.setImageResource(R.mipmap.home_rambling_set_play);
            viewHolder.ramblingSetMoreTvTitle.setTextColor(context.getResources().getColor(R.color.common_et_black_3333333));
        }
        viewHolder.ramblingSetMoreTvTitle.setText(rambleBean.getTitle());
        String minutes = TimeUtils.getIntMinutes(rambleBean.getAudioLength());
        viewHolder.ramblingSetMoreTvTime.setText("时长" + minutes + "分/共");
        viewHolder.ramblingSetMoreTvProgress.setText(rambleBean.getSpeed());
        viewHolder.ramblingSetMoreIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealPlayClick(i);
            }
        });
        viewHolder.ramblingSetMoreIvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealClick.deal(context, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        ARouter.getInstance().build(RouterPath.COURSE_EXPLAIN_ACTIVITY)
                                .withString(ConstantTag.DATA_TAG.getTagValue(), rambleBean.getId() + "")
                                .withInt(ConstantTag.AUDIO_LENGTH.getTagValue(), rambleBean.getAudioLength())
                                .navigation(context);
                    }
                });
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealPlayClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ramble.size();
    }



    public void refreshData(List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble) {
        this.ramble.clear();
        this.ramble = ramble;
        notifyDataSetChanged();
    }

    public void addData(List<RamblingSetMoreResultBean.DataBean.RambleBean> ramble) {
        this.ramble.addAll(ramble);
        notifyDataSetChanged();
    }


    /**
     * 处理播放点击
     *
     * @param currentPosition
     */
    private void dealPlayClick(int currentPosition) {
        RamblingSetMoreResultBean.DataBean.RambleBean rambleBean = ramble.get(currentPosition);
        int id = rambleBean.getId();
        if (currentPlayId == id&&this.playerStatus==PlayerStatusEnum.PLAYING_STATUS) {
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
                courseInfo.setCourseId(rambleBean.getId());
                courseInfo.setTitle(rambleBean.getTitle());
                courseInfo.setAudioUrl(ramble.get(currentPosition).getAudio());
                navigation.play(courseInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
