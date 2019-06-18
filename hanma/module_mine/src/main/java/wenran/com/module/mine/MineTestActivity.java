package wenran.com.module.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wenran.com.baselibrary.utils.MyToast;

public class MineTestActivity extends AppCompatActivity {

    @BindView(R2.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_test);
        ButterKnife.bind(this);
        TextView viewById = findViewById(R.id.tv);
    }
    @OnClick(R2.id.tv)
    public void onViewClicked() {
        MyToast.s(this,"2",0);
    }

}
