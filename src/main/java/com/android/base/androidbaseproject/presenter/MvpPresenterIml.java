package com.android.base.androidbaseproject.presenter;


import com.android.base.androidbaseproject.rxbus.RxBus;
import com.android.base.androidbaseproject.exception.RetrofitHttpException;
import com.android.base.androidbaseproject.exception.ServerException;
import com.android.base.androidbaseproject.retrofit.RetrofitAppClient;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import retrofit2.Response;

/**
 * mvp base Presenter
 * Created by secretqi on 2018/2/28.
 */

public abstract class MvpPresenterIml<K, V> implements IPresenter<V> {

    public V mvpView;
    private CompositeDisposable mCompositeDisposable;
    public K apiStores;

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = new RetrofitAppClient.Builder()
                .baseUrl(this.baseUrl())
                .interceptor(this.interceptor())
                .cookieJar(this.cookieJar())
                .build()
                .retrofit()
                .create(this.getAPIStores());
    }

    public abstract String baseUrl();

    public abstract Class<K> getAPIStores();

    public abstract Interceptor interceptor();

    public abstract PersistentCookieJar cookieJar();

    @Override
    public void detachView() {
        this.mvpView = null;
        this.clearSubscribe();
    }

    /**
     * RXjava取消注册，以避免内存泄露
     */
    private void clearSubscribe() {
        if (null != this.mCompositeDisposable) {
            this.mCompositeDisposable.clear();
        }
    }

    /**
     * Rxbus post event
     * @param o
     */
    public void post(Object o) {
        RxBus.getInstance().post(o);
    }

    /**
     * Rxbus register event
     * @param tClass
     * @param callback
     */
    public <T> void registerEvent(Class<T> tClass, RxBusCallback<T> callback) {
        if (null == this.mCompositeDisposable) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(RxBus.getInstance().toObservable(tClass)
                .subscribe(t -> {
                    if (null != callback) {
                        callback.accept(t);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.error(throwable.getMessage());
                    }
                }));
    }

    public <T> void addSubscription(final Observable<Response<T>> observable, final RetrofitResponse<T> response) {
        if (null == this.mCompositeDisposable) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        Consumer<T> consumer = t -> {
            if (null != response) {
                response.accept(t);
                response.onComplete();
            }
        };
        Consumer<Throwable> throwableConsumer = throwable -> {
            RetrofitHttpException.ResponseThrowable responseThrowable = RetrofitHttpException.retrofitException(throwable);
            if (null != response) {
                response.responseMessage(responseThrowable.code, responseThrowable.message);
                response.onComplete();
            }
            if (responseThrowable.code == 401) {
                onCookieTimeOut();
            }
        };
        this.mCompositeDisposable.add(observable
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, throwableConsumer));

    }

    public <T> void concatSubscription(final Observable<Response<? extends T>> observable1, final Observable<Response<? extends T>> observable2, final RetrofitResponse<T> response) {
        if (null == this.mCompositeDisposable) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        Observable concatObservable = Observable.concat(observable1, observable2);
        Consumer<? extends T> consumer = t -> {
            if (null != response) {
                response.accept(t);
                response.onComplete();
            }
        };
        Consumer<Throwable> throwableConsumer = throwable -> {
            RetrofitHttpException.ResponseThrowable responseThrowable = RetrofitHttpException.retrofitException(throwable);
            if (null != response) {
                response.responseMessage(responseThrowable.code, responseThrowable.message);
                response.onComplete();
            }
            if (responseThrowable.code == 401) {
                onCookieTimeOut();
            }
        };
        this.mCompositeDisposable.add(concatObservable
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, throwableConsumer));
    }

    public void addSubscription(Disposable disposable) {
        if (null == this.mCompositeDisposable) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    protected abstract void onCookieTimeOut();

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Function<Response<T>, T> {

        @Override
        public T apply(Response<T> response) {
            if (response.code() != 200) {
                String message;
                try {
                    message = response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    message = e.getMessage();
                }
                throw new ServerException(response.code(), message);
            }
            return response.body();
        }
    }

    /**
     *
     * @param <T>
     */
    public interface RetrofitResponse<T> {

        void accept(T t);

        void responseMessage(int code, String message);

        void onComplete();
    }

    public interface RxBusCallback<T> {

        void accept(T t);

        void error(String message);
    }

}
