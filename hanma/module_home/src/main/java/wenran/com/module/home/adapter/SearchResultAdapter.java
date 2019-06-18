package wenran.com.module.home.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.SearchResultViewHolder;
import wenran.com.module.home.bean.SearchCourseResultBean;
import wenran.com.module.home.greendao.HistorySearchControl;
import wenran.com.module.home.greendao.bean.HistorySearchBean;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
    Context context;
    List<SearchCourseResultBean.DataBean> dataList;

    public SearchResultAdapter(Context context, List<SearchCourseResultBean.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (dataList != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_result_adapter, viewGroup, false);
            SearchResultViewHolder viewHolder = new SearchResultViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder viewHolder, final int i) {
        if (dataList.size() - 1 == i) {
            viewHolder.searchResultLine.setVisibility(View.GONE);
        }
        final SearchCourseResultBean.DataBean dataBean = dataList.get(i);
        GlideUtil.setImageByUrl(context, dataBean.getCover(), viewHolder.searchResultIvImg, null, null);
        viewHolder.searchResultTvTitle.setText(dataBean.getTitle());
        viewHolder.searchResultTvAuthor.setText(dataBean.getTeacher());

        String minutes = TimeUtils.getIntMinutes(dataBean.getAudioLength());
        int audioCount = dataBean.getAudioCount();
        viewHolder.searchResultTvTime.setText("时长" + minutes + "分/共" + audioCount + "节");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToDataBase(dataBean.getTitle());
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

    /**
     * 处理点击事件
     *
     * @param playIv
     * @param title
     * @param position
     */
    private void dealClickForPlay(ImageView playIv, TextView title, int position) {
        Resources resources = playIv.getResources();
    }


    public void refreshData(List<SearchCourseResultBean.DataBean> data) {
        this.dataList.clear();
        this.dataList = data;
        notifyDataSetChanged();
    }

    public void addData(List<SearchCourseResultBean.DataBean> data) {
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }


    /**
     * 添加历史搜索
     */
    private void addItemToDataBase(String tag) {
        //添加数据库
        HistorySearchBean searchBean = new HistorySearchBean();
        searchBean.setSearchNum(1);
        searchBean.setSearchTag(tag);

        HistorySearchControl.addHistorySearchData(context, searchBean);
    }
}
