package wenran.com.baselibrary.server;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wenran.com.baselibrary.callback.INormalCallback;

/**
 * Created by crowhine on 2017/12/12.
 *
 * @author crowhine
 */

public class ObtainNetData {
    INormalCallback iNormalCallback;

    public ObtainNetData(Observable netDataObservable, INormalCallback iNormalCallback) {
        this.iNormalCallback = iNormalCallback;
        netDataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer observer = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object value) {
            Log.i("onNext", "onNext: " + value);
            iNormalCallback.success(value);
        }

        @Override
        public void onError(Throwable e) {
            iNormalCallback.failure(e.toString());
            Log.e("onError", "onError: ", e);
        }

        @Override
        public void onComplete() {
            Log.i("onComplete", "onComplete: ");
        }
    };

}
