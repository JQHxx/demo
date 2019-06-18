package wenran.com.module.home.view;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.callbackrepalce.ErrorCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.help.GlideImageLoader;
import wenran.com.baselibrary.utils.DealClick;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.baselibrary.utils.StringUtil;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.bean.CourseExplainParamBean;
import wenran.com.module.home.bean.CourseExplainResultBean;
import wenran.com.module.home.contract.CourseExplainContract;
import wenran.com.module.home.presenter.CourseExplainPresenterImpl;
import wenran.com.module_player.service.AudioPlayerBinder;

/**
 * @author crowhine
 * <p>
 * 课程介绍
 */
@Route(path = RouterPath.COURSE_EXPLAIN_ACTIVITY)
public class CourseExplainActivity extends BaseActivityImpl<CourseExplainContract.ICourseExplainPresenter>
        implements CourseExplainContract.ICourseExplainView, View.OnScrollChangeListener {

    Unbinder unbinder;
    @BindView(R2.id.base_title_iv_left)
    ImageView baseTitleIvLeft;
    @BindView(R2.id.course_explain_banner)
    Banner courseExplainBanner;
    @BindView(R2.id.course_explain_tv_title)
    TextView courseExplainTvTitle;
    @BindView(R2.id.course_explain_tv_author)
    TextView courseExplainTvAuthor;
    @BindView(R2.id.course_explain_ll_player)
    LinearLayout courseExplainLlPlayer;
    @BindView(R2.id.course_explain_wv_content)
    WebView courseExplainWvContent;
    @BindView(R2.id.course_explain_sv)
    ScrollView courseExplainSv;
    @Autowired()
    String dataTag;
    @Autowired()
    int audioLength;
    @BindView(R2.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R2.id.course_explain_tv_title2)
    TextView courseExplainTvTitle2;
    @BindView(R2.id.course_explain_tv_author2)
    TextView courseExplainTvAuthor2;
    @BindView(R2.id.course_explain_tv_total_time)
    TextView courseExplainTvTotalTime;
    @BindView(R2.id.course_explain_iv_play_control)
    ImageView courseExplainIvPlayControl;
    @BindView(R2.id.player_ll_all)
    LinearLayout playerLlAll;
    private CourseExplainPlayControl courseExplainPlayControl;
    private AudioPlayerBinder audioPlayerBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (courseExplainPlayControl != null) {
            courseExplainPlayControl.registerEventBus(true);
        }
        if (audioPlayerBinder!=null){
            audioPlayerBinder.doResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (courseExplainPlayControl != null) {
            courseExplainPlayControl.registerEventBus(false);
        }
        if (audioPlayerBinder!=null){
            audioPlayerBinder.doPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (courseExplainPlayControl != null) {
            courseExplainPlayControl = null;
        }
        if (audioPlayerBinder!=null){
            audioPlayerBinder.doDestroy();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_explain;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);
        courseExplainSv.setOnScrollChangeListener(this);
        ARouter.getInstance().inject(this);
        baseTitleTvCenter.setText(R.string.course_explain_title);
        getData();
    }


    @Override
    protected CourseExplainContract.ICourseExplainPresenter bindPresenter() {
        return new CourseExplainPresenterImpl(this);
    }

    @Override
    public void dealCourseExplainBannerSuccess(List<Integer> imageIdList, List<String> imageList) {
        courseExplainBanner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();
        courseExplainBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(final int position) {
                DealClick.deal(getSelfActivity(), new DealClick.ClickCallback() {
                    @Override
                    public void isSuccess() {
                        showHint(getResources().getString(R.string.developing) + position);
                    }
                });
            }
        });
    }

    @Override
    public void dealCourseExplainBannerFailure(int code, String msg) {

    }

    @Override
    public void dealCourseExplainSuccess(CourseExplainResultBean.DataBean data) {
        courseExplainSv.setVisibility(View.VISIBLE);
        showLoadServiceSuccess();
        courseExplainTvTitle.setText(data.getClassItem().getTitle());
        courseExplainTvTitle2.setText(data.getClassItem().getTitle());
        if (!StringUtil.isEmptyStr(data.getTeacher().trim())) {
            courseExplainTvAuthor.setText(data.getTeacher());
            courseExplainTvAuthor.setVisibility(View.VISIBLE);
            courseExplainTvAuthor2.setVisibility(View.VISIBLE);
            courseExplainTvAuthor2.setText(data.getTeacher());
        } else {
            courseExplainTvAuthor.setVisibility(View.GONE);
            courseExplainTvAuthor2.setVisibility(View.GONE);
        }
        if (audioLength != 0) {
            courseExplainTvTotalTime.setText(TimeUtils.getDetailTime2(audioLength));
        }
        if (!StringUtil.isEmptyStr(dataTag)) {
            //播放键控制器
            CourseInfo courseInfo = getCourseInfo(Integer.valueOf(dataTag), data.getClassItem().getTitle(),
                    data.getClassItem().getAudio());
            courseExplainPlayControl = new CourseExplainPlayControl(getSelfActivity(), courseInfo
                    , courseExplainIvPlayControl);
            courseExplainPlayControl.registerEventBus(true);
        }

        courseExplainWvContent.loadUrl(data.getClassItem().getContent());

        if (audioPlayerBinder == null) {
            audioPlayerBinder = new AudioPlayerBinder(playerLlAll);
            audioPlayerBinder.registerEventBus(true);
        }
    }

    @Override
    public void dealCourseExplainFailure(int code, String msg) {
        dealFailure(code, msg);
        getLoadService(courseExplainSv, ErrorCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getData();
            }
        });
    }

    @Override
    public void showException(String exceptionInfo) {
        super.showException(exceptionInfo);
        getLoadService(courseExplainSv, ErrorCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                getData();
            }
        });
    }

    @OnClick({R2.id.base_title_iv_left})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.base_title_iv_left) {
            finish();
        }
    }


    private void getData() {
        getPresenter().getCourseExplainData(new CourseExplainParamBean(dataTag));
    }

    private CourseInfo getCourseInfo(int id, String title, String audioUrl) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseId(id);
        courseInfo.setTitle(title);
        courseInfo.setAudioUrl(audioUrl);
        return courseInfo;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (oldScrollY - scrollY > ConstantNum.SCROLL_VALUE) {
            //向上滑,隐藏
            RouterUtil.setPlayerShowStatus(false);
        } else if (scrollY - oldScrollY > ConstantNum.SCROLL_VALUE) {
            //向下滑，显示
            RouterUtil.setPlayerShowStatus(true);
        }
    }
}
