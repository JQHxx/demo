package wenran.com.module.home.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.base.basemvp.IBaseOriginalPresenter;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.SomeData;
import wenran.com.baselibrary.utils.EventBusUtil;
import wenran.com.baselibrary.utils.FragmentControl;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.baselibrary.utils.StringUtil;
import wenran.com.baselibrary.utils.ViewUtil;
import wenran.com.module.home.R;
import wenran.com.module.home.R2;
import wenran.com.module.home.constant.HomeConstantTag;

/**
 * @author crowhine
 */
@Route(path = RouterPath.HOME_SEARCH_ACTIVITY)
public class SearchActivity extends BaseActivityImpl implements TextWatcher {

    @BindView(R2.id.search_et_search)
    EditText searchEtSearch;
    @BindView(R2.id.search_tv_cancel)
    TextView searchTvCancel;
    private Unbinder bind;
    private FragmentControl fragmentControl;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        searchEtSearch.addTextChangedListener(this);
        fragmentControl = new FragmentControl((FragmentActivity) getSelfActivity());
        showFragment(HomeConstantTag.SEARCH_SHOW_HOT);
    }

    @Override
    protected IBaseOriginalPresenter bindPresenter() {
        return null;
    }

    @OnClick(R2.id.search_tv_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        dealInputStr();
    }


    /**
     * 输入的文字
     */
    private void dealInputStr() {
        String inputStr = getInputStr();
        if (StringUtil.isEmptyStr(inputStr)) {
            //没有文字
            showFragment(HomeConstantTag.SEARCH_SHOW_HOT);
        } else {
            showFragment(HomeConstantTag.SEARCH_SHOW_POPUP);
        }
    }


    private void showFragment(HomeConstantTag showFragment) {
        switch (showFragment) {
            case SEARCH_SHOW_HOT:
                dealHotSearch();
                break;
            case SEARCH_SHOW_POPUP:
                dealPopup();
                break;
            default:
                break;
        }

    }

    private void dealHotSearch() {
        if (!fragmentControl.isAddFragment(SearchHotFragment.class)) {
            Fragment fragment = RouterUtil.getFragment(RouterPath.HOME_SEARCH_HOT_FRAGMENT,
                    null, null);
            fragmentControl.showAndHide(R.id.search_fl, fragment, null);
        } else {
            fragmentControl.showIsAddFragment(SearchHotFragment.class);
        }
    }

    private void dealPopup() {
        if (!fragmentControl.isAddFragment(SearchPopupFragment.class)) {
            Fragment fragment = RouterUtil.getFragment(RouterPath.HOME_SEARCH_POPUP_FRAGMENT,
                    ConstantTag.DATA_TAG.getTagValue(), getInputStr());
            fragmentControl.showAndHide(R.id.search_fl, fragment, null);
        } else {
            if (fragmentControl.isCurrentFragment(SearchPopupFragment.class)) {
                EventBusUtil.doSerializableEvent(HomeConstantTag.UPDATE_POPUP_SEARCH.getTagValue()
                        , getInputStr(), false);
            } else {
                EventBusUtil.doSerializableEvent(HomeConstantTag.NO_UPDATE_POPUP_SEARCH.getTagValue()
                        , getInputStr(), false);
                fragmentControl.showIsAddFragment(SearchPopupFragment.class);
            }
        }
    }


    /**
     * 处理搜索结果
     *
     * @param searchContent 需要搜索的内容
     */
    private void dealSearchResult(String searchContent) {
        if (!fragmentControl.isAddFragment(SearchResultFragment.class)) {
            Fragment fragment = RouterUtil.getFragment(RouterPath.HOME_SEARCH_RESULT_FRAGMENT,
                    ConstantTag.DATA_TAG.getTagValue(), searchContent);
            fragmentControl.showAndHide(R.id.search_fl, fragment, null);
        } else {
            EventBusUtil.doSerializableEvent(HomeConstantTag.UPDATE_SEARCH_RESULT.getTagValue()
                    , searchContent, false);
            fragmentControl.showIsAddFragment(SearchResultFragment.class);
        }
    }


    /**
     * 获取输入的文字
     */
    private String getInputStr() {
        String textFromView = ViewUtil.getTextFromView(searchEtSearch);
        return textFromView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SomeData event) {
        if (event.getTag().equals(HomeConstantTag.SHOW_SEARCH_RESULT.getTagValue())) {
            String t = (String) event.getT();
            if (!getInputStr().equals(t)){
                searchEtSearch.setText(t);
            }
            dealSearchResult((String) event.getT());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return true;
    }

}
