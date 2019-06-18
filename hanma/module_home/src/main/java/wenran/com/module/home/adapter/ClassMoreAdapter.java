package wenran.com.module.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.SearchResultViewHolder;
import wenran.com.module.home.bean.SpecialMoreResultBean;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class ClassMoreAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
    Context context;
    List<SpecialMoreResultBean.DataBean>  dataList;

    public ClassMoreAdapter(Context context, List<SpecialMoreResultBean.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (dataList != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_more_class_adapter, viewGroup, false);
            SearchResultViewHolder viewHolder = new SearchResultViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder viewHolder, final int i) {
        final SpecialMoreResultBean.DataBean dataBean = dataList.get(i);
        GlideUtil.setImageByUrl(context, dataBean.getCover(), viewHolder.searchResultIvImg, null, null);
        viewHolder.searchResultTvTitle.setText(dataBean.getTitle());
        viewHolder.searchResultTvAuthor.setText(dataBean.getTeacher());

        String minutes = TimeUtils.getIntMinutes(dataBean.getAudioLength());
        int audioCount = dataBean.getAudioCount();
        viewHolder.searchResultTvTime.setText("时长" + minutes + "分/共" + audioCount + "节");

        viewHolder.searchResultTvSaleNum.setText(dataBean.getSales() + "人已购");
        if (dataBean.isIsbuy()) {
            //显示没有购买的信息
            viewHolder.searchResultTvPrice.setText(dataBean.getUnbuy());
        } else {
            viewHolder.searchResultTvPrice.setText("¥" + dataBean.getPrice());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPath.COURSE_DETAIL_SHOW_ACTIVITY)
                        .withString(ConstantTag.DATA_TAG.getTagValue(), dataBean.getId()+"")
                        .navigation(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void refreshData(List<SpecialMoreResultBean.DataBean> data) {
        this.dataList.clear();
        this.dataList = data;
        notifyDataSetChanged();
    }

    public void addData(List<SpecialMoreResultBean.DataBean> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }


}
