package wenran.com.module.home.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wenran.com.module.home.R2;


/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function：搜索联系viewHolder
 */
public class PopupSearchViewHolder extends RecyclerView.ViewHolder {
    /**
     * 标题
     */
    @BindView(R2.id.popup_tv_content)
    public TextView title;

    public PopupSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
