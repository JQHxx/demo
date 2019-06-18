package wenran.com.module.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.SpecialClassViewHolder;
import wenran.com.module.home.bean.HomeDataResultBean;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class SpecialClassAdapter extends RecyclerView.Adapter<SpecialClassViewHolder> {
    Context context;
    List<HomeDataResultBean.DataBean.SpecialBean> specialClasses;

    public SpecialClassAdapter(Context context, List<HomeDataResultBean.DataBean.SpecialBean> specialClasses) {
        this.context = context;
        this.specialClasses = specialClasses;
        registerEventBus(true);
    }

    @NonNull
    @Override
    public SpecialClassViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (specialClasses != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_special_class_adapter, viewGroup, false);
            SpecialClassViewHolder viewHolder = new SpecialClassViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialClassViewHolder viewHolder, final int i) {
        final HomeDataResultBean.DataBean.SpecialBean specialBean = specialClasses.get(i);
        GlideUtil.setImageByUrl(context, specialBean.getCover(), viewHolder.specialClassIvImg, null, null);
        viewHolder.specialClassTvTitle.setText(specialBean.getTitle());
        viewHolder.specialClassTvAuthor.setText(specialBean.getTeacher());

        String minutes = TimeUtils.getIntMinutes(specialBean.getAudioLength());
        int audioCount = specialBean.getAudioCount();
        viewHolder.specialClassTvTime.setText("时长" + minutes + "分/共" + audioCount + "节");
        viewHolder.specialClassTvSaleNum.setText(specialBean.getSales() + context.getString(R.string.person_bought));
        if (specialBean.isIsbuy()) {
            //显示没有购买的信息
            viewHolder.specialClassTvPrice.setText(specialBean.getUnbuy());
        } else {
            viewHolder.specialClassTvPrice.setText("¥" + specialBean.getPrice());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPath.COURSE_DETAIL_SHOW_ACTIVITY)
                        .withString(ConstantTag.DATA_TAG.getTagValue(), specialBean.getId() + "")
                        .navigation(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialClasses.size();
    }


    /**
     * 是否注册eventBus
     *
     * @param isRegister
     */
    public void registerEventBus(boolean isRegister) {
        if (isRegister) {
            boolean registered = EventBus.getDefault().isRegistered(this);
            if (registered == false) {
                EventBus.getDefault().register(this);
            }
        } else {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 收到购买后发送过来的消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SomeData event) {
        if (event != null) {
            if (event != null && event.getTag().equals(ConstantTag.UPDATE_BUY_STATUS.name())) {
                Serializable serializable = event.getT();
                Integer t = null;
                if (serializable instanceof String){
                    String tStr= (String) serializable;
                    t=Integer.valueOf(tStr);
                }
                if (t != null && specialClasses != null) {
                    int isBuyPosition = -1;
                    for (int i = 0; i < specialClasses.size(); i++) {
                        if (t == specialClasses.get(i).getId()) {
                            //记录下已经购买过的位置
                            isBuyPosition = i;
                            break;
                        }
                    }
                    if (isBuyPosition != -1) {
                        specialClasses.get(isBuyPosition).setIsbuy(true);
                        specialClasses.get(isBuyPosition).setUnbuy("已购");
                        notifyItemChanged(isBuyPosition);
                    }
                }
            }
        }
    }
}
