package wenran.com.module.home.greendao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wenran.com.baselibrary.utils.RxUtil;
import wenran.com.module.home.greendao.bean.HistorySearchBean;
import wenran.com.module.home.greendao.bean.HistorySearchBeanDao;

/**
 * Created by Crowhine on 2019/3/20
 *
 * @author Crowhine
 */
public class HistorySearchControl {
    /**
     * 查询显示的条目数
     */
    static final int limitNum = 5;

    /**
     * 增加
     */
    public static void addHistorySearchData(final Context context, final HistorySearchBean historySearchBean){
        RxUtil.doSth(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                addData(context,historySearchBean);
                emitter.onComplete();
            }
        },new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 查询历史搜索数据库
     */
    public static void queryHistorySearchData(final Context context, Observer<List<HistorySearchBean>> observer) {
        RxUtil.doSth(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                List<HistorySearchBean> historySearchBeanList = query(context);
                emitter.onNext(historySearchBeanList);
                emitter.onComplete();
            }
        },observer);
    }


    /**
     * 查询历史搜索数据库
     */
    private static List<HistorySearchBean> query(Context context) {
        HistorySearchBeanDao historySearchBeanDao = new MyDao(context).getHistorySearchDao();
        int stuSumCount = historySearchBeanDao.queryBuilder().list().size();
        if (stuSumCount == 0) {
            return null;
        } else {
            List<HistorySearchBean> historySearchBeanList = historySearchBeanDao.queryBuilder()
                    .orderDesc(HistorySearchBeanDao.Properties.SearchNum).limit(limitNum).list();
            if (historySearchBeanList != null && historySearchBeanList.size() != 0) {
                List<HistorySearchBean> historySearches = new ArrayList<>();
                historySearches.addAll(historySearchBeanList);
                return historySearches;
            }
        }
        return null;
    }

    /**
     * 更新或创建本地个人信息数据库
     */
    private static void addData(Context context, HistorySearchBean historySearchBean) {
        if (historySearchBean != null) {
            HistorySearchBeanDao historySearchBeanDao = new MyDao(context).getHistorySearchDao();
            int stuSumCount = historySearchBeanDao.queryBuilder().list().size();
            if (stuSumCount >= limitNum) {
                //更新
                addItem(historySearchBeanDao, historySearchBean, true);
            } else {
                addItem(historySearchBeanDao, historySearchBean, false);
            }
        }
    }


    /**
     * 增加
     */
    private static void addItem(HistorySearchBeanDao historySearchBeanDao,
                                HistorySearchBean historySearchBean, boolean isGreaterLimit) {
        HistorySearchBean unique = historySearchBeanDao.queryBuilder().where(HistorySearchBeanDao.Properties.SearchTag.eq(historySearchBean.getSearchTag())).build().unique();
        if (unique != null) {
            //数据库里存在，把点击数加一
            unique.setSearchNum(unique.getSearchNum() + 1);
            historySearchBeanDao.update(unique);
        } else {
            if (isGreaterLimit) {
                //大于限制的个数，去比较是否需要替换
                compareNum(historySearchBeanDao, historySearchBean);
            } else {
                //直接插入
                historySearchBeanDao.insert(historySearchBean);
            }
        }
    }

    /**
     * 比较次数，如果同样只有一次就替换
     */
    private static void compareNum(HistorySearchBeanDao historySearchBeanDao,
                                   HistorySearchBean historySearchBean) {
        List<HistorySearchBean> list = historySearchBeanDao.queryBuilder().list();
        for (HistorySearchBean searchBean :
                list) {
            if (searchBean.getSearchNum() <= historySearchBean.getSearchNum()) {
                searchBean.setSearchTag(historySearchBean.getSearchTag());
                searchBean.setSearchNum(historySearchBean.getSearchNum());
                historySearchBeanDao.update(searchBean);
                break;
            }
        }
    }
}
