package wenran.com.module.mine.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import wenran.com.module.mine.R;


/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function：消息viewHolder
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {
    /**
     * 时间
     */
    public TextView messageTvTime;
    /**
     * 消息状态是否已读
     */
    public ImageView messageIvStatus;
    /**
     * 消息title
     */
    public TextView messageTvTitle;
    /**
     * 消息内容
     */
    public TextView messageTvContent;
    /**
     * 消息详情
     */
    public TextView messageTvDetail;
    /**
     * 消息详情箭头
     */
    public RelativeLayout messageRlDetailArrow;
    public SwipeLayout swipe;
    public LinearLayout messageLlShow;
    public LinearLayout messageLlDelete;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTvTime = itemView.findViewById(R.id.message_tv_time);
        messageIvStatus = itemView.findViewById(R.id.message_iv_status);
        messageTvTitle = itemView.findViewById(R.id.message_tv_title);
        messageTvContent = itemView.findViewById(R.id.message_tv_content);
        messageTvDetail = itemView.findViewById(R.id.message_tv_detail);
        messageRlDetailArrow = itemView.findViewById(R.id.message_rl_detail_arrow);
        swipe = itemView.findViewById(R.id.swipe);
        messageLlShow = itemView.findViewById(R.id.message_ll_show);
        messageLlDelete = itemView.findViewById(R.id.message_ll_delete);
    }
}
