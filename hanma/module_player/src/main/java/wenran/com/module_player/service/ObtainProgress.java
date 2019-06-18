package wenran.com.module_player.service;

import android.os.SystemClock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wenran.com.baselibrary.interfaces.IObtainCourseCallback;
import wenran.com.baselibrary.interfaces.IPlayer;
import wenran.com.baselibrary.utils.RouterUtil;

/**
 * Created by Crowhine on 2019/4/24
 *
 * @author Crowhine
 */
public class ObtainProgress {
    private Disposable disposable;
    private boolean isNeedNotify;
    IObtainCourseCallback<Integer> iObtainCourseCallback;

    public ObtainProgress(IObtainCourseCallback<Integer> iObtainCourseCallback) {
        this.iObtainCourseCallback=iObtainCourseCallback;
        init();
    }

    private void init() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                while (!disposable.isDisposed()) {
                    if (isNotify()) {
                        SystemClock.sleep(1000);
                        getCurrentProgress(new IObtainCourseCallback<Integer>() {
                            @Override
                            public void callback(Integer courseInfo) {
                                emitter.onNext(courseInfo);
                            }
                        });
                    }
                }
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer value) {
                        iObtainCourseCallback.callback(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iObtainCourseCallback.callback(-1);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getCurrentProgress(final IObtainCourseCallback<Integer> integerIObtainCourseCallback) {
        IPlayer navigation = RouterUtil.getIPlayer();
        navigation.getCurrentProgress(new IObtainCourseCallback<Integer>() {
            @Override
            public void callback(Integer courseInfo) {
                integerIObtainCourseCallback.callback(courseInfo);
            }
        });
    }

    /**
     * 是否需要发送消息
     */
    private boolean isNotify() {
        return isNeedNotify;
    }

    /**
     * 设置是否需要发送消息
     */
    public void setNotify(boolean isNeedNotify) {
        this.isNeedNotify = isNeedNotify;
    }

    public void doDestroy() {
        setNotify(false);
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
