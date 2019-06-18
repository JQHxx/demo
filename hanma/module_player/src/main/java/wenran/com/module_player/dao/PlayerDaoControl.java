package wenran.com.module_player.dao;

import android.content.Context;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wenran.com.baselibrary.bean.StandardResultBean;
import wenran.com.baselibrary.callback.INormalCallback;
import wenran.com.baselibrary.constant.ConstantTag;
import wenran.com.baselibrary.dao.BaseGreenDao;
import wenran.com.baselibrary.dao.CourseInfo;
import wenran.com.baselibrary.dao.CourseInfoDao;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.utils.RxUtil;
import wenran.com.baselibrary.utils.SpUtil;
import wenran.com.module_player.bean.CourseProgressParamBean;
import wenran.com.module_player.model.NetModel;


/**
 * Created by crowhine on 2018/8/29.
 *
 * @author crowhine
 */

public class PlayerDaoControl {

    /**
     * 获取课程信息dao
     */
    private static CourseInfoDao getCourseInfoDao(Context context) {
        // 创建数据
        CourseInfoDao courseInfoDao = new BaseGreenDao().getDaoSession(context).getCourseInfoDao();
        return courseInfoDao;
    }


    /**
     * 增加
     */
    public static void addHistorySearchData(final Context context,
                                            final CourseInfo courseInfo, final IObtainCourseCallback iObtainCourseCallback) {
        RxUtil.doSth(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                addData(context, courseInfo, emitter);
            }
        }, new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                if (iObtainCourseCallback != null) {
                    iObtainCourseCallback.callback(null);
                }
            }

            @Override
            public void onComplete() {
                if (iObtainCourseCallback != null) {
                    iObtainCourseCallback.callback(null);
                }
            }
        });
    }

    /**
     * 更新或创建本地课程信息数据库
     */
    private static void addData(Context context, CourseInfo courseInfo, ObservableEmitter emitter) {
        if (courseInfo != null) {
            CourseInfoDao courseInfoDao = getCourseInfoDao(context);
            int stuSumCount = courseInfoDao.queryBuilder().list().size();
            if (stuSumCount != 0) {
                CourseInfo unique = courseInfoDao.queryBuilder().
                        where(CourseInfoDao.Properties.CourseId.eq(courseInfo.getCourseId())).build().unique();
                if (unique != null) {
                    //更新当前播放的进度和总播放时长
                    int currentPlayLength = courseInfo.getCurrentPlayLength();
                    int totalPlayLength = unique.getTotalPlayLength();
                    int audioLength = courseInfo.getAudioLength();
//防止第一次播放complete方法调用，视频长度没有获取到，这里重复赋值
                    unique.setAudioLength(courseInfo.getAudioLength());

                    //需要上传的
                    CourseInfo needUnique = unique;
                    updateCourseProgress(context, needUnique, courseInfo);

                    if (totalPlayLength < audioLength) {
                        //记录总播放的时长，如果超过或等于了视频长度，不记录
                        unique.setTotalPlayLength(unique.getTotalPlayLength() + currentPlayLength);
                    }
                    if (audioLength == currentPlayLength) {
                        //已经播放完成了,归零，下次从头播放
                        currentPlayLength = 0;
                    }
                    unique.setCurrentPlayLength(currentPlayLength);

                    courseInfoDao.update(unique);
                    if (emitter != null) {
                        emitter.onComplete();
                    }

                } else {
                    courseInfoDao.insert(courseInfo);
                    if (emitter != null) {
                        emitter.onComplete();
                    }
                    updateCourseProgress(context, null, courseInfo);
                }
            } else {
                courseInfoDao.insert(courseInfo);
                if (emitter != null) {
                    emitter.onComplete();
                }
                updateCourseProgress(context, null, courseInfo);
            }
        }

    }


    /**
     * 根据课程id查询数据库
     */
    public static void queryCourseInfo(final Context context, final Integer courseId, final IObtainCourseCallback iObtainCourseCallback) {
        RxUtil.doSth(new ObservableOnSubscribe<CourseInfo>() {
            @Override
            public void subscribe(ObservableEmitter<CourseInfo> emitter) throws Exception {
                CourseInfo courseInfo = query(context, courseId);
                emitter.onNext(courseInfo);
                emitter.onComplete();
            }
        }, new Observer<CourseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CourseInfo courseInfo) {
                iObtainCourseCallback.callback(courseInfo);
            }

            @Override
            public void onError(Throwable e) {
                iObtainCourseCallback.callback(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * 查询历史搜索数据库
     */
    private static CourseInfo query(Context context, Integer courseId) {
        int stuSumCount = getCourseInfoDao(context).queryBuilder().list().size();
        if (stuSumCount != 0) {
            CourseInfo unique = getCourseInfoDao(context).queryBuilder().
                    where(CourseInfoDao.Properties.CourseId.eq(courseId)).build().unique();
            if (unique != null) {
                return unique;
            }
        }

        return null;
    }


    /**
     * 根据课程id查询课程播放进度数据库
     */
    public static void queryCoursePlayProgress(final Context context, final Integer courseId, final IObtainCourseCallback iObtainCourseCallback) {
        RxUtil.doSth(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Integer integer = queryProgress(context, courseId);
                emitter.onNext(integer);
                emitter.onComplete();
            }
        }, new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object courseProgress) {
                iObtainCourseCallback.callback(courseProgress);
            }

            @Override
            public void onError(Throwable e) {
                iObtainCourseCallback.callback(null);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * 查询历史搜索数据库
     */
    private static Integer queryProgress(Context context, Integer courseId) {
        int stuSumCount = getCourseInfoDao(context).queryBuilder().list().size();
        if (stuSumCount != 0) {
            CourseInfo unique = getCourseInfoDao(context).queryBuilder().
                    where(CourseInfoDao.Properties.CourseId.eq(courseId)).build().unique();
            if (unique != null) {
                return unique.getCurrentPlayLength();
            }
        }
        return null;
    }

    /**
     * 更新后课程进度信息
     * bug：用户滑动到末端，能够刷新进度。2，播放百分之百后还是去更新课程进度
     *
     * @param unique      数据库以前存储的课程bean
     * @param struts      1学完，0未学完
     * @param classItemId 课程id
     * @param lookAt      观看的总时长
     */
    private static void updateCourseProgress(Context context, CourseInfo unique, CourseInfo currentCourse) {
        int audioLength = currentCourse.getAudioLength() / 1000;
        if (audioLength == 0) {
            return;
        }
        int currentPlayLength = currentCourse.getCurrentPlayLength() / 1000;
        int courseId = currentCourse.getCourseId();
        if (unique == null) {
            //第一次播放
            if (currentPlayLength >= audioLength) {
                //播放百分之百
                updateCourseProgress(context, 1, courseId, audioLength);
            } else {
                //更新进度
                updateCourseProgress(context, 0, courseId, currentPlayLength);
            }

        } else {
            int uniqueTotalPlayLength = unique.getTotalPlayLength() / 1000;
            if (uniqueTotalPlayLength + currentPlayLength >= audioLength) {
                //播放百分之百
                updateCourseProgress(context, 1, courseId, audioLength);
            } else {
                //更新进度
                updateCourseProgress(context, 0, courseId, uniqueTotalPlayLength + currentPlayLength);
            }
        }
    }

    /**
     * @param struts      1学完，0未学完
     * @param classItemId 课程id
     * @param lookAt      观看的总时长
     */
    private static void updateCourseProgress(Context context, int struts, int classItemId, int lookAt) {
        CourseProgressParamBean courseProgressParamBean = new CourseProgressParamBean();
        String appToken = SpUtil.getString(context, ConstantTag.APP_TOKEN.name());
        courseProgressParamBean.setApptoken(appToken);
        courseProgressParamBean.setLookAt(lookAt);
        courseProgressParamBean.setStruts(struts);
        new NetModel().updateCourseProgress(classItemId, courseProgressParamBean, new INormalCallback<StandardResultBean>() {
            @Override
            public void success(StandardResultBean msg) {
                StandardResultBean msg2 = msg;
            }

            @Override
            public void failure(String failureInfo) {

            }
        });
    }
}
