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

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.PhoneUtil;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.SingleClassViewHolder;
import wenran.com.module.home.bean.HomeDataResultBean;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class SingleClassAdapter extends RecyclerView.Adapter<SingleClassViewHolder> {
    Context context;
    List<HomeDataResultBean.DataBean.IndividualBean> singleClasses;

    public SingleClassAdapter(Context context, List<HomeDataResultBean.DataBean.IndividualBean> singleClasses) {
        this.context = context;
        this.singleClasses = singleClasses;
        registerEventBus(true);
    }

    @NonNull
    @Override
    public SingleClassViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (singleClasses != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_single_class_adapter, viewGroup, false);
            SingleClassViewHolder viewHolder = new SingleClassViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleClassViewHolder viewHolder, final int i) {
        final HomeDataResultBean.DataBean.IndividualBean individualBean = singleClasses.get(i);
        GlideUtil.setRoundedCornersImageByUrl(context, individualBean.getCover(), viewHolder.singleClassIvImg,
                PhoneUtil.Dp2px(context,10), 0, RoundedCornersTransformation.CornerType.TOP, null, null);        viewHolder.singleClassTvTitle.setText(individualBean.getTitle());
        viewHolder.singleClassTvAuthor.setText(individualBean.getTeacher());

        String minutes = TimeUtils.getIntMinutes(individualBean.getAudioLength());
        int audioCount = individualBean.getAudioCount();
        viewHolder.singleClassTvTime.setText("时长"+minutes+"分/共"+audioCount+"节");
        viewHolder.singleClassTvSaleNum.setText(individualBean.getSales() + "人已购");
        if (individualBean.isIsbuy()) {
            //显示没有购买的信息
            viewHolder.singleClassTvPrice.setText(individualBean.getUnbuy());
        } else {
            viewHolder.singleClassTvPrice.setText("¥"+individualBean.getPrice());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPath.COURSE_DETAIL_SHOW_ACTIVITY)
                        .withString(ConstantTag.DATA_TAG.getTagValue(), individualBean.getId()+"")
                        .navigation(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return singleClasses.size();
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
                if (t != null && singleClasses != null) {
                    int isBuyPosition = -1;
                    for (int i = 0; i < singleClasses.size(); i++) {
                        if (t == singleClasses.get(i).getId()) {
                            //记录下已经购买过的位置
                            isBuyPosition = i;
                            break;
                        }
                    }
                    if (isBuyPosition != -1) {
                        singleClasses.get(isBuyPosition).setIsbuy(true);
                        singleClasses.get(isBuyPosition).setUnbuy("已购");
                        notifyItemChanged(isBuyPosition);
                    }
                }
            }
        }
    }
}
