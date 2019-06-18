package wenran.com.module.mine.model;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.module.mine.bean.ChangeMessageStatusParam;
import wenran.com.module.mine.bean.MessageParamBean;
import wenran.com.module.mine.bean.MessageResultBean;

public interface MineService {
    /**
     * 获取消息
     *
     * @param messageParamBean
     * @return
     */
    @POST("home/records.json")
    Observable<MessageResultBean> getMessages(@Body MessageParamBean messageParamBean);

    /**
     * 修改消息已读状态
     *
     * @param changeMessageStatusParam
     * @return
     */
    @POST("home/modifyrecord.json")
    Observable<StandardResultBean> changeMessageStatus(@Body ChangeMessageStatusParam changeMessageStatusParam);

    /**
     * 删除消息
     *
     * @param changeMessageStatusParam
     * @return
     */
    @POST("home/delyrecord.json")
    Observable<StandardResultBean> deleteMessage(@Body ChangeMessageStatusParam changeMessageStatusParam);

    /**
     * 获取消息状态，是否有新消息
     *
     * @param apptoken
     * @return
     */
    @GET("home/isread.json")
    Observable<StandardResultBean> getMessageStatus(@Query("apptoken") String apptoken);
}
