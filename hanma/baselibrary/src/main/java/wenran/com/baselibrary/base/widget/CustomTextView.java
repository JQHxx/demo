package wenran.com.baselibrary.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Crowhine on 2019/2/26
 *
 * @author Crowhine
 */
public class CustomTextView  extends  android.support.v7.widget.AppCompatTextView{
    private boolean adjustTopForAscent = true;
    private Paint.FontMetricsInt fontMetricsInt;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置是否remove间距，true为remove
        if (adjustTopForAscent) {
            if (fontMetricsInt == null) {
                fontMetricsInt = new Paint.FontMetricsInt();
                getPaint().getFontMetricsInt(fontMetricsInt);
            }
            canvas.translate(0, fontMetricsInt.top - fontMetricsInt.ascent);
        }
        super.onDraw(canvas);
    }
}
