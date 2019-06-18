package wenran.com.baselibrary.base.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import wenran.com.baselibrary.R;
import wenran.com.baselibrary.utils.StringUtil;


/**
 * Created by crowhine on 2017/8/9.
 * description：一个给用选择是否的对话框
 *
 * @author crowhine
 */

public class ShowYesNoDialog {
    Context context;
    DoConfirmCallBack doConfirmCallBack;
    String hint;

    public ShowYesNoDialog(Context context, DoConfirmCallBack doConfirmCallBack, String hint) {
        this.context = context;
        this.doConfirmCallBack = doConfirmCallBack;
        this.hint = hint;
        showHint();
    }

    private void showHint() {
        View view = LayoutInflater.from(context).inflate(R.layout.yes_no_dialog, null);
        final MyDialogWrap builder = new MyDialogWrap(context, 0, 0, view, R.style.dialog);
        TextView tvLeft = view.findViewById(R.id.tv_left);
        TextView tvRight = view.findViewById(R.id.tv_right);
        TextView content = view.findViewById(R.id.content);
        if (!StringUtil.isEmptyStr(hint)) {
            content.setText(hint);
        }
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doConfirmCallBack.clickConfirm();
                builder.dismiss();
            }
        });
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.dismiss();
            }
        });
        builder.show();
    }

    public interface DoConfirmCallBack {
        /**
         * 点击确认
         */
        void clickConfirm();
    }
}
