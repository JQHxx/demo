package wenran.com.module.home.view;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.base.widget.MyDialogBottom;
import wenran.com.baselibrary.base.widget.NoPaddingTv;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.callbackrepalce.ErrorCallback;
import wenran.com.baselibrary.constant.ConstantNum;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.utils.EventBusUtil;
import wenran.com.baselibrary.utils.GlideUtil;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.baselibrary.utils.TimeUtils;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.adapter.CourseDetailCommentAdapter;
import wenran.com.module.home.adapter.CourseDetailShowAdapter;
import wenran.com.module.home.bean.CollectParamBean;
import wenran.com.module.home.bean.CommentParamBean;
import wenran.com.module.home.bean.CourseDetailShowParamBean;
import wenran.com.module.home.bean.CourseDetailShowResultBean;
import wenran.com.module.home.contract.CourseDetailShowContract;
import wenran.com.module.home.presenter.CourseDetailShowPresenterImpl;
import wenran.com.module.home.util.CourseProgressUtil;
import wenran.com.module_player.service.AudioPlayerBinder;

/**
 * 专题课程详情展示,二级界面
 *
 * @author crowhine
 */
@Route(path = RouterPath.COURSE_DETAIL_SHOW_ACTIVITY)
public class CourseDetailShowActivity extends BaseActivityImpl<CourseDetailShowContract.ICourseDetailShowPresenter>
        implements CourseDetailShowContract.ICourseDetailShowView {

    @BindView(R2.id.base_title_tv_center)
    TextView baseTitleTvCenter;
    @BindView(R2.id.base_title_iv_note)
    ImageView baseTitleIvNote;
    @BindView(R2.id.base_title_iv_right)
    ImageView baseTitleIvRight;
    @BindView(R2.id.course_detail_iv_img)
    ImageView courseDetailIvImg;
    @BindView(R2.id.course_detail_tv_title)
    NoPaddingTv courseDetailTvTitle;
    @BindView(R2.id.course_detail_tv_author)
    TextView courseDetailTvAuthor;
    @BindView(R2.id.course_detail_tv_time)
    TextView courseDetailTvTime;
    @BindView(R2.id.course_detail_tv_sale_num)
    NoPaddingTv courseDetailTvSaleNum;
    @BindView(R2.id.course_detail_pb_progress)
    ProgressBar courseDetailPbProgress;
    @BindView(R2.id.course_detail_tv_progress)
    TextView courseDetailTvProgress;
    @BindView(R2.id.course_detail_ll_progress)
    LinearLayout courseDetailLlProgress;
    @BindView(R2.id.course_detail_tv_introduction)
    TextView courseDetailTvIntroduction;
    @BindView(R2.id.course_detail_tv_intended_population)
    TextView courseDetailTvIntendedPopulation;
    @BindView(R2.id.course_detail_tv_subscription_notice)
    TextView courseDetailTvSubscriptionNotice;
    @BindView(R2.id.course_detail_tv_remark)
    TextView courseDetailTvRemark;
    @BindView(R2.id.course_detail_ll_introduction)
    LinearLayout courseDetailLlIntroduction;
    @BindView(R2.id.course_outline_iv_order)
    ImageView courseOutlineIvOrder;
    @BindView(R2.id.course_outline_tv_order)
    TextView courseOutlineTvOrder;
    @BindView(R2.id.course_outline_ll_order)
    LinearLayout courseOutlineLlOrder;
    @BindView(R2.id.course_outline_recycler_view)
    RecyclerView courseOutlineRecyclerView;
    @BindView(R2.id.course_detail_course_outline_ll)
    LinearLayout courseDetailCourseOutlineLl;
    @BindView(R2.id.leave_word_recycler_view)
    RecyclerView leaveWordRecyclerView;
    @BindView(R2.id.course_detail_ll_leave_word)
    LinearLayout courseDetailLlLeaveWord;
    @BindView(R2.id.course_detail_tv_price)
    TextView courseDetailTvPrice;
    @BindView(R2.id.course_detail_tv_buy)
    TextView courseDetailTvBuy;
    @BindView(R2.id.course_detail_ll_buy)
    LinearLayout courseDetailLlBuy;
    @BindView(R2.id.nsv)
    NestedScrollView nsv;
    @BindView(R2.id.course_detail_rl)
    RelativeLayout courseDetailRl;
    @BindView(R2.id.player_ll_all)
    LinearLayout playerLlAll;
    private int orderType = 1;
    @Autowired()
    String dataTag;
    Unbinder bind;
    private CourseDetailShowAdapter courseDetailShowAdapter;
    private CourseDetailCommentAdapter courseDetailCommentAdapter;
    private boolean isCollect;
    private int classInfoId;
    private MyDialogBottom commentDialog;
    private EditText commentContent;
    private AudioPlayerBinder audioPlayerBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (courseDetailShowAdapter != null) {
            courseDetailShowAdapter.registerEventBus(true);
        }
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doResume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (courseDetailShowAdapter != null) {
            courseDetailShowAdapter.registerEventBus(false);
        }
        if (audioPlayerBinder != null) {
            audioPlayerBinder.doPause();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_detail_show;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        nsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getSelfActivity());
        courseOutlineRecyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getSelfActivity());
        leaveWordRecyclerView.setLayoutManager(linearLayoutManager2);
        askData();
    }


    @Override
    protected CourseDetailShowContract.ICourseDetailShowPresenter bindPresenter() {
        return new CourseDetailShowPresenterImpl(this);
    }

    @Override
    public void dealInitCourseDetailSuccess(CourseDetailShowResultBean.DataBean.ClassInfoBean classInfo) {
        showLoadServiceSuccess();
        courseDetailRl.setVisibility(View.VISIBLE);
        baseTitleIvRight.setVisibility(View.VISIBLE);
        baseTitleTvCenter.setText(classInfo.getTitle());
        GlideUtil.setImageByUrl(getSelfActivity(), classInfo.getCover(), courseDetailIvImg, null, null);
        courseDetailTvTitle.setText(classInfo.getTitle());
        courseDetailTvAuthor.setText(getString(R.string.author) + classInfo.getTeacher());
        String minutes = TimeUtils.getIntMinutes(classInfo.getAudioLength());
        int audioCount = classInfo.getAudioCount();
        courseDetailTvTime.setText("时长" + minutes + "分/共" + audioCount + "节");
        courseDetailTvIntroduction.setText(classInfo.getClassIntro());
        courseDetailTvIntendedPopulation.setText(classInfo.getTargetUser());
        courseDetailTvSubscriptionNotice.setText(classInfo.getSubscribeNotice());
        courseDetailTvRemark.setText(classInfo.getRemark());

        boolean isBuy = classInfo.isIsbuy();
        if (isBuy) {
            baseTitleIvNote.setVisibility(View.VISIBLE);
            courseDetailLlProgress.setVisibility(View.VISIBLE);
            courseDetailTvSaleNum.setVisibility(View.GONE);
            String speed = classInfo.getSpeed();
            int intProgress = CourseProgressUtil.getIntProgress(speed);
            courseDetailPbProgress.setProgress(intProgress);
            courseDetailTvProgress.setText(intProgress + "%");
            courseDetailLlBuy.setVisibility(View.GONE);
        } else {
            baseTitleIvNote.setVisibility(View.GONE);
            courseDetailLlProgress.setVisibility(View.GONE);
            courseDetailTvSaleNum.setVisibility(View.VISIBLE);
            courseDetailTvSaleNum.setText(classInfo.getSales() + getString(R.string.person_bought));
            courseDetailLlBuy.setVisibility(View.VISIBLE);
            courseDetailTvPrice.setText("¥" + classInfo.getPrice() + "/" + classInfo.getAudioCount() + "讲");
        }
        isCollect = classInfo.isIsCollect();
        classInfoId = classInfo.getId();
        if (isCollect) {
            GlideUtil.setImageByRes(getSelfActivity(), R.mipmap.course_detail_collect, baseTitleIvRight);
        } else {
            GlideUtil.setImageByRes(getSelfActivity(), R.mipmap.course_detail_no_collect, baseTitleIvRight);
        }

        if (audioPlayerBinder == null) {
            audioPlayerBinder = new AudioPlayerBinder(playerLlAll);
            audioPlayerBinder.registerEventBus(true);
        }
    }

    @Override
    public void dealInitCourseDetailFailure(int code, String hint) {
        dealFailure(code, hint);
        getLoadService(this, ErrorCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                askData();
            }
        });
    }

    @Override
    public void showException(String exceptionInfo) {
        super.showException(exceptionInfo);
        getLoadService(this, ErrorCallback.class, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                askData();
            }
        });
    }

    @Override
    public void showCourseOutlineRecyclerView(boolean isSuccess, boolean isBought, String coverUrl, List<CourseDetailShowResultBean.DataBean.ClassItemsBean> classComments) {
        if (isSuccess) {
            courseDetailCourseOutlineLl.setVisibility(View.VISIBLE);
            if (courseDetailShowAdapter == null) {
                courseDetailShowAdapter = new CourseDetailShowAdapter(getSelfActivity(), isBought, coverUrl, classComments);
                courseOutlineRecyclerView.setAdapter(courseDetailShowAdapter);
            } else {
                courseDetailShowAdapter.refreshData(classComments);
            }
            courseDetailShowAdapter.registerEventBus(true);
        } else {
            courseDetailCourseOutlineLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLeaveWordRecyclerView(boolean isSuccess, List<CourseDetailShowResultBean.DataBean.ClassCommentsBean> classCommentsBeans) {
        if (isSuccess) {
            courseDetailLlLeaveWord.setVisibility(View.VISIBLE);
            if (courseDetailCommentAdapter == null) {
                courseDetailCommentAdapter = new CourseDetailCommentAdapter(getSelfActivity(), classCommentsBeans, getPresenter());
                leaveWordRecyclerView.setAdapter(courseDetailCommentAdapter);
            } else {
                courseDetailCommentAdapter.refreshData(classCommentsBeans);
            }
        } else {
            courseDetailLlLeaveWord.setVisibility(View.GONE);
        }
    }

    @Override
    public void dealCollectSuccess() {
        if (isCollect) {
            GlideUtil.setImageByRes(getSelfActivity(), R.mipmap.course_detail_no_collect, baseTitleIvRight);
            isCollect = false;
        } else {
            GlideUtil.setImageByRes(getSelfActivity(), R.mipmap.course_detail_collect, baseTitleIvRight);
            isCollect = true;
        }
    }

    @Override
    public void dealCommentSuccess() {

    }


    @OnClick({R2.id.base_title_iv_left, R2.id.base_title_iv_note,
            R2.id.base_title_iv_right, R2.id.course_outline_ll_order,
            R2.id.course_detail_tv_buy})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.base_title_iv_left) {
            finish();
        } else if (view.getId() == R.id.base_title_iv_note) {
            //写留言
            showCommentDialog();
        } else if (view.getId() == R.id.base_title_iv_right) {
            //收藏
            getPresenter().doCollect(new CollectParamBean(null, classInfoId));
        } else if (view.getId() == R.id.course_outline_ll_order) {
            if (orderType == 0) {
                changeOrder(1);
                courseOutlineIvOrder.setImageResource(R.mipmap.rambling_set_order_desc);
                courseOutlineTvOrder.setText(R.string.rambling_set_tv_order_desc_tx);
                askData();
            } else {
                changeOrder(0);
                courseOutlineIvOrder.setImageResource(R.mipmap.rambling_set_order_asc);
                courseOutlineTvOrder.setText(R.string.rambling_set_tv_order_asc_tx);
                askData();
            }
        } else if (view.getId() == R.id.course_detail_tv_buy) {
            if (classInfoId == 0) {
                showHint("课程id为空");
                return;
            }
            RouterUtil.<String>aliPay(getSelfActivity(), classInfoId, new INormalCallback<String>() {
                @Override
                public void success(String msg) {
                    showHint("购买成功");
                    //刷新刷机
                    askData();
                    EventBusUtil.doSerializableEvent(ConstantTag.UPDATE_BUY_STATUS.name(),dataTag,false);
                }

                @Override
                public void failure(String failureInfo) {
                    showHint("购买失败");
                }
            });
        }
    }

    /**
     * 更改顺序
     */
    private void changeOrder(int orderType) {
        this.orderType = orderType;
    }

    /**
     * 请求数据
     */
    private void askData() {
        getPresenter().getCourseDetailShowData(dataTag, new CourseDetailShowParamBean(null, orderType, false));
    }

    private void showCommentDialog() {
        if (commentDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_course_detail_comment, null);
            View titleView = view.findViewById(R.id.course_detail_course_comment_title);
            ImageView ivLeftTitle = titleView.findViewById(R.id.base_title_iv_left);
            TextView centerTitle = titleView.findViewById(R.id.base_title_tv_center);
            TextView rightTittle = titleView.findViewById(R.id.base_title_tv_right);
            rightTittle.setVisibility(View.VISIBLE);
            commentContent = view.findViewById(R.id.course_detail_comment_et);
            centerTitle.setText(R.string.write_comment);
            ivLeftTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentDialog.hide();
                }
            });
            rightTittle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String context = commentContent.getText().toString();
                    getPresenter().doComment(new CommentParamBean(null, context, classInfoId));
                    commentDialog.hide();
                }
            });
            commentDialog = new MyDialogBottom(this, 0, 1, view, R.style.dialog);
            commentDialog.setCancelable(true);
            commentDialog.show();
        } else {
            if (commentDialog.isShowing() == false) {
                commentDialog.show();
            }
        }

    }
}
