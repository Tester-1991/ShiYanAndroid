package com.shiyan.android.basemodule.util;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxBus管理者
 */
public class RxBus {

    private volatile  static RxBus rxBus;

    //非背压处理
    private Subject subject;

    private RxBus(){

        subject = PublishSubject.create().toSerialized();

    }

    public static RxBus getInstance(){

        if(rxBus == null){

            synchronized (RxBus.class){

                if(rxBus == null){

                    rxBus = new RxBus();

                }
            }
        }

        return rxBus;
    }

    /**
     * 发布消息
     * @param object
     */
    public void post(Object object){

        if(!hasObservable()) return;

        subject.onNext(object);
    }

    /**
     * 是否有观察者
     * @return
     */
    public boolean hasObservable(){

        return subject.hasObservers();
    }

    /**
     * 返回观察者
     */
    public Observable<Object> toObservable(){

        return subject;
    }

    /**
     * 返回特定类型的观察者
     */
    public <T> Observable<T> toObservable(Class<T> type){

        return subject.ofType(type);
    }
}
