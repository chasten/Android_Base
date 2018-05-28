package com.android.base.androidbaseproject.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Not {@link io.reactivex.Flowable}
 */
public class RxBus {

    private Subject<Object> mBus;

    private static RxBus instance;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (null == instance) {
            //同步保证线程安全
            synchronized (RxBus.class) {
                if (null == instance) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object object) {
        mBus.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}
