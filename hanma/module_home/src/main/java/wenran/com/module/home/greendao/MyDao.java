package wenran.com.module.home.greendao;

import android.content.Context;

import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.module.home.greendao.bean.DaoMaster;
import wenran.com.module.home.greendao.bean.DaoSession;
import wenran.com.module.home.greendao.bean.HistorySearchBeanDao;


/**
 * Created by crowhine on 2018/8/29.
 *
 * @author crowhine
 */

public class MyDao {
    Context context = null;

    public MyDao(Context context) {
        this.context = context;
    }

    /**
     * 获取历史搜索dao
     */
    public HistorySearchBeanDao getHistorySearchDao() {
        // 创建数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, ConstantTag.GREEN_DAO_CROWHINE.name() + ".db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HistorySearchBeanDao historySearchBeanDao = daoSession.getHistorySearchBeanDao();
        return historySearchBeanDao;
    }
}
