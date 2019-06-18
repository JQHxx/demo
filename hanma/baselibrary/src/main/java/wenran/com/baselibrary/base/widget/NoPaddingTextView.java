package wenran.com.baselibrary.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import io.reactivex.annotations.Nullable;
import wenran.com.baselibrary.R;

/**
 * Created by Crowhine on 2019/2/25
 *
 * @author Crowhine
 * <p>
 * function:没有内边距
 */
public class NoPaddingTextView extends android.support.v7.widget.AppCompatTextView {

    public NoPaddingTextView(Context context) {
        this(context, null);
    }

    public NoPaddingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoPaddingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NoPaddingTextView, 0, 0);
        int textSize = a.getDimensionPixelSize(R.styleable.NoPaddingTextView_textSize, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
        Paint paint = new Paint();
        Log.e("NoPaddingTextView", "textSize:" + textSize);
        paint.setTextSize(textSize);
        final Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();

        setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.NoPaddingTextView_textSize, 12));
        int top = (int) Math.ceil(Math.abs((fontMetricsInt.top - fontMetricsInt.ascent) / 2.0));
        Log.e("NoPaddingTextView", "top" + top);
        setPadding(0, -(Math.abs(fontMetricsInt.top - fontMetricsInt.ascent) + top)
                , 0,
                fontMetricsInt.top - fontMetricsInt.ascent);
        a.recycle();
        post(new Runnable() {
            @Override
            public void run() {
                Log.e("NoPaddingTextView", "getHeight()" + getHeight());
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
