package wenran.com.baselibrary.dao;

import android.content.Context;

import wenran.com.baselibrary.constant.ConstantTag;

/**
 * Created by Crowhine on 2019/4/18
 *
 * @author Crowhine
 */
public class BaseGreenDao {
    public DaoSession getDaoSession(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, ConstantTag.GREEN_DAO_CROWHINE.name()+getClass().getSimpleName() + ".db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
