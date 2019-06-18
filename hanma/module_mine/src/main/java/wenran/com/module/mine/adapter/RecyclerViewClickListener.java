package wenran.com.module.mine.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * fuction:recycleView 点击和长按事件监听
 */
public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {
    private Context context;
    /**
     * GestureDetectorCompat 是为了版本兼容
     */
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView mRecyclerView;
    private OnItemsClickListener mListener;

    public RecyclerViewClickListener(Context context, final RecyclerView mRecyclerView, final OnItemsClickListener mListener) {
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.mListener = mListener;

        // SimpleOnGestureListener 是为了选择重写需要的方法
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            //单击事件
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("mGestureDetector", "onSingleTapUp");
                View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childViewUnder != null && mListener != null) {
                    mListener.onItemClick(childViewUnder, mRecyclerView.getChildLayoutPosition(childViewUnder));
                    return true;
                }
                return false;
            }

            //长按事件
            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("mGestureDetector", "onLongPress");
                View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, mRecyclerView.getChildLayoutPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        //是否拦截事件交给 mGestureDetector 处理
        if (mGestureDetector.onTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    /**
     * 自定义内部监听
     */
    public interface OnItemsClickListener {
        /**
         * 单击
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 长按
         *
         * @param view
         * @param position
         */
        void onItemLongClick(View view, int position);
    }
}
