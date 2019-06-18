package wenran.com.baselibrary.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by crowhine on 2017/9/21.
 * description:添加和替换隐藏fragment；
 *
 * @author crowhine
 */

public class FragmentControl {
    private FragmentManager mManger;
    private Fragment mCurrentFragment;
    private FragmentActivity context;

    public FragmentControl(FragmentActivity context) {
        this.context = context;
        mManger = context.getSupportFragmentManager();
    }

    /**
     * @param contentId 布局id
     * @param fragment  类
     * @param bundle    fragment需要携带的数据，为空时或者fragment已经存在时不添加
     */
    public void showAndHide(int contentId, Fragment fragment, Bundle bundle) {
        //判断当前的fragment和需要替换的fragment是否是统一一个
        if (mCurrentFragment != null && mCurrentFragment.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())) {
            return;
        }

        //判断fragment有没有添加过
        FragmentTransaction transaction = mManger.beginTransaction();

        Fragment fragmentByTag = mManger.findFragmentByTag(fragment.getClass().getSimpleName());
        if (fragmentByTag != null) {
            //显示需要的fragment
            transaction.show(fragmentByTag);
            //隐藏当前的fragment
            transaction.hide(mCurrentFragment);
            //让记录当前的fragment赋值为显示的fragment
            mCurrentFragment = fragmentByTag;
        } else {
            //通过无参的 公开的构造函数来创建Fragment实例
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            //当前的Fragment没有添加过 把Fragment添加到manager里面
            transaction.add(contentId, fragment, fragment.getClass().getSimpleName());
            if (mCurrentFragment != null) {
                //隐藏当前的Fragment
                transaction.hide(mCurrentFragment);
            }
            //记录当前的Fragment是那个
            mCurrentFragment = fragment;
        }
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    /**显示已经添加过的fragment
     * @param isAddedFragment
     * */
    public void showIsAddFragment(Class isAddedFragment){
        FragmentTransaction transaction = mManger.beginTransaction();
        Fragment fragmentByTag = mManger.findFragmentByTag(isAddedFragment.getSimpleName());
        //显示需要的fragment
        transaction.show(fragmentByTag);
        //隐藏当前的fragment
        transaction.hide(mCurrentFragment);
        //让记录当前的fragment赋值为显示的fragment
        mCurrentFragment = fragmentByTag;
        transaction.addToBackStack(isAddedFragment.getSimpleName());
        transaction.commit();
    }


    public String doSthForBack() {
        // 获取当前回退栈中的Fragment个数
        int backStackEntryCount = mManger.getBackStackEntryCount();
        // 判断当前回退栈中的fragment个数,
        if (backStackEntryCount > 1) {
            // 立即回退一步
            mManger.popBackStackImmediate();
//                // 获取当前退到了哪一个Fragment上,重新获取当前的Fragment回退栈中的个数
            FragmentManager.BackStackEntry backStack = mManger
                    .getBackStackEntryAt(mManger
                            .getBackStackEntryCount() - 1);
            mCurrentFragment = mManger.findFragmentByTag(backStack.getName());
            if (backStack == null) {
                doSthForBack();
            } else {
                return backStack.getName();
            }
        } else {
            //回退栈中只剩一个时,退出应用
            context.finish();
            return "";
        }
        return "";
    }


    /**
     * 判断任务栈中是否已经添加了fragment
     *
     * @param c
     */
    public boolean isAddFragment(Class c) {
        Fragment fragmentByTag = mManger.findFragmentByTag(c.getSimpleName());
        if (fragmentByTag == null) {
            return false;
        } else {
            return true;
        }
    }

    /**是否正在显示
     *
     * @param c
     * */
    public boolean isCurrentFragment(Class c) {
        if (mCurrentFragment != null && mCurrentFragment.getClass().getSimpleName().equals(c.getSimpleName())) {
            return true;
        }
        return false;
    }

}
