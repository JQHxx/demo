package wenran.com.module.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import wenran.com.baselibrary.help.SomeData;
import wenran.com.module.home.R;
import wenran.com.module.home.adapter.viewholder.PopupSearchViewHolder;
import wenran.com.module.home.constant.HomeConstantTag;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class PopupSearchAdapter extends RecyclerView.Adapter<PopupSearchViewHolder> {
    Context context;
    List<String> titleList;

    public PopupSearchAdapter(Context context, List<String> titleList) {
        this.context = context;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public PopupSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (titleList != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_popup_adapter, viewGroup, false);
            PopupSearchViewHolder viewHolder = new PopupSearchViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final PopupSearchViewHolder viewHolder, final int i) {
        viewHolder.title.setText(titleList.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送更改数据消息
                SomeData<String> stringSomeData =
                        new SomeData<>(HomeConstantTag.SHOW_SEARCH_RESULT.getTagValue(), titleList.get(i));
                EventBus.getDefault().postSticky(stringSomeData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


    /**
     * 更新数据
     */
    public void refreshData(List<String> data) {
        titleList.clear();
        titleList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        titleList.clear();
        notifyDataSetChanged();
    }

}
