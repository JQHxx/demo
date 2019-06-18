package wenran.com.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.module.mine.R;
import wenran.com.module.mine.adapter.viewholder.MessageViewHolder;
import wenran.com.module.mine.bean.MessageResultBean;
import wenran.com.module.mine.contract.MessageContract;
import wenran.com.module.mine.view.MessageDetailActivity;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 */
public class MessageAdapter extends RecyclerSwipeAdapter<MessageViewHolder> {
    private Context context;
    private MessageContract.IMessageView iMessageView;
    private List<MessageResultBean.DataBean> messages;

    public MessageAdapter(Context context, MessageContract.IMessageView iMessageView, List<MessageResultBean.DataBean> messages) {
        this.context = context;
        this.iMessageView = iMessageView;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (messages != null) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_adapter, viewGroup, false);
            MessageViewHolder viewHolder = new MessageViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder viewHolder, final int i) {
        final MessageResultBean.DataBean dataBean = messages.get(i);
        viewHolder.messageTvTime.setText(dataBean.getCreatedAt());
        viewHolder.messageTvTitle.setText(dataBean.getMessage().getTitle());
        if (dataBean.getRtype() == 0) {
            viewHolder.messageIvStatus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.messageIvStatus.setVisibility(View.INVISIBLE);
        }
        viewHolder.messageTvContent.setText(dataBean.getMessage().getContent());
        viewHolder.messageRlDetailArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealClick.deal(context, new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        //设置已读
                        viewHolder.messageIvStatus.setVisibility(View.INVISIBLE);
                        //跳转到详细
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra(ConstantTag.DATA_TAG.getTagValue(), dataBean);
                        context.startActivity(intent);
                    }
                });

            }
        });
        viewHolder.messageLlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMessageView.deleteMessage(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    /**
     * 刷新数据
     *
     * @param messages
     */
    public void refreshData(List<MessageResultBean.DataBean> messages) {
        this.messages.clear();
        this.messages = messages;
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     *
     * @param messages
     */
    public void addData(List<MessageResultBean.DataBean> messages) {
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    /**
     * 移除item
     *
     * @param position
     */
    public void removeItem(int position) {
        this.messages.remove(position);
        notifyDataSetChanged();
        if (messages.size() == 0) {
            iMessageView.setNoDataView();
        }
    }

    /**
     * 获取当前itemId
     *
     * @param position
     */
    public int getRid(int position) {
        if (this.messages != null && this.messages.size() > position) {
            return messages.get(position).getRid();
        }
        return 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

}
