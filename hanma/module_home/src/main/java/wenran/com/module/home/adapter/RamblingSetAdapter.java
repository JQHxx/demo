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
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.RamblingSetViewHolder;
import wenran.com.module.home.bean.HomeDataResultBean;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class RamblingSetAdapter extends BasePlayerAdapter<RamblingSetViewHolder> {
    List<HomeDataResultBean.DataBean.RambleBean> ramblingSetList;

    public RamblingSetAdapter(Context context, List<HomeDataResultBean.DataBean.RambleBean> ramblingSetList) {
        this.context = context;
        this.ramblingSetList = ramblingSetList;
        getCurrentPlayIdForInit();
        getCurrentPlayStatus();
    }

    @NonNull
    @Override
    public RamblingSetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (ramblingSetList != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_home_rambling_set_adapter, viewGroup, false);
            RamblingSetViewHolder viewHolder = new RamblingSetViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RamblingSetViewHolder viewHolder, final int i) {
        final HomeDataResultBean.DataBean.RambleBean rambleBean = ramblingSetList.get(i);
        if (currentPlayId == rambleBean.getId()) {
            if (playerStatus==PlayerStatusEnum.PLAYING_STATUS){
                viewHolder.homeRamblingSetAdapterTvTitle.setTextColor(context.getResources().getColor(R.color.common_tv_tc_ff943d));
                viewHolder.homeRamblingSetAdapterIvPlay.setImageResource(R.mipmap.home_rambling_set_pause);
            }else if (playerStatus==PlayerStatusEnum.COMPLETION_STATUS){
                viewHolder.homeRamblingSetAdapterTvTitle.setTextColor(context.getResources().getColor(R.color.common_tv_tc_ff943d));
                viewHolder.homeRamblingSetAdapterIvPlay.setImageResource(R.mipmap.home_rambling_set_play);
            }
        } else {
            viewHolder.homeRamblingSetAdapterIvPlay.setImageResource(R.mipmap.home_rambling_set_play);
            viewHolder.homeRamblingSetAdapterTvTitle.setTextColor(context.getResources().getColor(R.color.common_et_black_3333333));
        }
        viewHolder.homeRamblingSetAdapterTvTitle.setText(rambleBean.getTitle());
        viewHolder.homeRamblingSetAdapterTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealPlayClick(i);
            }
        });
        viewHolder.homeRamblingSetAdapterIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealPlayClick(i);
            }
        });
        viewHolder.homeRamblingSetAdapterIvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealClick.deal(context, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        int id = ramblingSetList.get(i).getId();
                        ARouter.getInstance().build(RouterPath.COURSE_EXPLAIN_ACTIVITY)
                                .withString(ConstantTag.DATA_TAG.getTagValue(), id + "")
                                .withInt(ConstantTag.AUDIO_LENGTH.getTagValue(), ramblingSetList.get(i).getAudioLength())
                                .navigation(context);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return ramblingSetList.size();
    }


    /**
     * 处理播放点击
     *
     * @param currentPosition
     */
    private void dealPlayClick(int currentPosition) {
        HomeDataResultBean.DataBean.RambleBean rambleBean = ramblingSetList.get(currentPosition);
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
                courseInfo.setAudioUrl(ramblingSetList.get(currentPosition).getAudio());
                navigation.play(courseInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
