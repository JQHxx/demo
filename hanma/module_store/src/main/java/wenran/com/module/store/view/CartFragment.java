package wenran.com.module.store.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseFragmentImpl;
import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.help.MyData;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.module.store.R;
import wenran.com.module.store.R2;

/**
 * Created by Crowhine on 2019/1/29
 *
 * @author Crowhine
 */
@Route(path = RouterPath.STORE_MAIN_ACTIVITY)
public class CartFragment extends BaseFragmentImpl {
    @BindView(R2.id.cart_fm_tv)
    TextView cartFmTv;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_cart_fragment;
    }

    @Override
    protected IBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    /**
     * 实例化
     *
     * @param data
     */
    public static CartFragment newInstance(MyData data) {
        CartFragment f = new CartFragment();
        Bundle b = new Bundle();
        b.putParcelable(ConstantTag.DATA_TAG.name(), data);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R2.id.cart_fm_tv)
    public void onViewClicked() {
//        RouterUtil.aliPay(getActivity());
    }
}
