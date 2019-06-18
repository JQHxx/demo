package wenran.com.baselibrary.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Crowhine on 2019/3/20
 *
 * @author Crowhine
 */
public class RxUtil {
    public static void doSth(ObservableOnSubscribe observableOnSubscribe, Observer observer) {
        Observable listObservable = Observable.create(observableOnSubscribe).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        listObservable.subscribe(observer);
    }
}
