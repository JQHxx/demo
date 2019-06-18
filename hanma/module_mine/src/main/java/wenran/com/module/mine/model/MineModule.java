package wenran.com.module.mine.model;


import wenran.com.baselibrary.server.RetrofitManager;

/**
 * @author mumuji
 * @version 1.0.0
 * @date 2018/5/7
 * @description
 */

public class MineModule {
    public static MineService createrRetrofit() {
        return RetrofitManager.getInstance().getRetrofitService(MineService.class);
    }
}
