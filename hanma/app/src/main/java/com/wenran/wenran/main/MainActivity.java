package com.wenran.wenran.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.wenran.wenran.R;
import com.wenran.wenran.main.login.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wenran.com.baselibrary.base.basemvp.BaseActivityImpl;
import wenran.com.baselibrary.base.basemvp.IBasePresenter;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.RouterPath;
import wenran.com.baselibrary.utils.RouterUtil;
import wenran.com.module_player.service.AudioPlayerBinder;

/**
 * @author crowhine
 */
public class MainActivity extends BaseActivityImpl {

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.player_ll_all)
    LinearLayout playerLlAll;
    private Unbinder bind;
    private AudioPlayerBinder audioPlayerBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioPlayerBinder!=null){
            audioPlayerBinder.doResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioPlayerBinder!=null){
            audioPlayerBinder.doPause();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        IPlayer.IDestroyService navigation = RouterUtil.getIDestroyService();
        navigation.destroyPlayerService();
        if (audioPlayerBinder!=null){
            audioPlayerBinder.doDestroy();
        }
        if (bind != null) {
            bind.unbind();
        }
        super.onDestroy();

    }

    @Override
    protected IBasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        AHBottomNavigationItem home = new AHBottomNavigationItem(getString(R.string.home), R.mipmap.home_normal, R.color.main_color);
        AHBottomNavigationItem cart = new AHBottomNavigationItem(getString(R.string.shopping_cart), R.mipmap.shopping_cart_normal, R.color.main_color);
        AHBottomNavigationItem mine = new AHBottomNavigationItem(getString(R.string.mine), R.mipmap.mine_normal, R.color.main_color);

        // Add items
        bottomNavigation.addItem(home);
        bottomNavigation.addItem(cart);
        bottomNavigation.addItem(mine);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.main_color));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.bottom_nav_gray_b7b7b7));

        List<Fragment> fragmentList = getFragmentList();
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getSelfActivity(), fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(position, false);
                }
                return true;
            }
        });

        if (audioPlayerBinder==null){
            audioPlayerBinder = new AudioPlayerBinder(playerLlAll);
            audioPlayerBinder.registerEventBus(true);
        }
    }

    public List<Fragment> getFragmentList() {
        ArrayList<Fragment> arrayList = new ArrayList();
        arrayList.add((Fragment) ARouter.getInstance().build(RouterPath.HOME_MAIN_ACTIVITY).navigation());
        arrayList.add((Fragment) ARouter.getInstance().build(RouterPath.STORE_MAIN_ACTIVITY).navigation());
        arrayList.add((Fragment) ARouter.getInstance().build(RouterPath.MINE_MAIN_ACTIVITY).navigation());
        return arrayList;
    }


}
