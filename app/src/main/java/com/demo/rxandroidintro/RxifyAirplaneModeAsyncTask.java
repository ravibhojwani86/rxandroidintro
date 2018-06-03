package com.demo.rxandroidintro;

import android.content.Context;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import java.util.concurrent.Callable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by ravi .
 */

public class RxifyAirplaneModeAsyncTask {


    /**
     * OBSERVABLE
     *
     * It is simplest Observable which can emit more than one value.
     *
     * Reference : http://reactivex.io/documentation/observable.html
     */

    public static class OBSERVABLE {

        public static Observable<Boolean> getAirplaneModeObservableCreate(final Context context){
            return Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                    if(!emitter.isDisposed()) {
                        emitter.onNext(DbUtils.isAirplaneModeOn(context));
                    }
                }
            });
        }

        public static Observable<Boolean> getAirplaneModeObservableFromCallable(final Context context){
            return Observable.fromCallable(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return DbUtils.isAirplaneModeOn(context);
                }
            });
        }

    }


    /**
     * SINGLE
     *
     * Single is used when the Observable has to emit only one value like a response from a network call or
     * a Db call. It always either emits one value or an error notification
     *
     * Reference : http://reactivex.io/documentation/single.html :
     */

    public static class SINGLE {

        public static Single<Boolean> getAirplaneModeObservableCreate(final Context context){
            return Single.create(new SingleOnSubscribe<Boolean>() {
                @Override
                public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
                    if(!emitter.isDisposed()) {
                        emitter.onSuccess(DbUtils.isAirplaneModeOn(context));
                    }
                }
            });
        }

        public static Single<Boolean> getAirplaneModeObservableFromCallable(final Context context){
            return Single.fromCallable(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return DbUtils.isAirplaneModeOn(context);
                }
            });
        }

    }



    /**
     * MAYBE
     *
     * It is a mix of a Single and a Completable i.e. it can either emit a value (emitter.onSuccess(T))
     * or no value (emitter.onComplete())
     * Reference : http://reactivex.io/RxJava/javadoc/io/reactivex/Maybe.html
     */
    public static class MAYBE {

        public static Maybe<Boolean> getAirplaneModeObservableCreate(final Context context){
            return Maybe.create(new MaybeOnSubscribe<Boolean>() {
                @Override
                public void subscribe(MaybeEmitter<Boolean> emitter) throws Exception {
                    if(!emitter.isDisposed()){
                        emitter.onSuccess(DbUtils.isAirplaneModeOn(context));
                    }
                }
            });
        }

        public static Maybe<Boolean> getAirplaneModeObservablefromCallable(final Context context){
            return Maybe.fromCallable(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return DbUtils.isAirplaneModeOn(context);
                }
            });
        }

    }

    /**
     * FLOWABLE
     * Observable cannot handle back pressure.
     * Backpressure : When an Observable is emitting huge numbers of values which can’t be consumed by the Observer
     *
     * Flowable is a special Observable which handles backpressure with strategy called BackPressureStrategy
     *
     * Reference: http://reactivex.io/RxJava/javadoc/io/reactivex/Flowable.html
     */

    public static class FLOWABLE {

        // Airplane mode value fetch is not the right use case to use Flowable
        // as it emits just one value in this particular use case.
        // This is just to show Flowable usage.
        public static Flowable<Boolean> getAirplabeModeFlowableCreate(){
            return Flowable.create(new FlowableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {

                }
            }, BackpressureStrategy.BUFFER);
        }

    }


    /**
     * COMPLETABLE
     *
     * It is used when the Observable has to do some task without emitting a value.
     * It just notifies of task completion (emitter.onComplete) or error (emitter.onError)
     *
     * Reference : http://reactivex.io/RxJava/javadoc/io/reactivex/Completable.html
     */

    public static class COMPLETABLE {

        public static Completable getAirplaneModeCompletableCreate(){
            return Completable.create(new CompletableOnSubscribe() {
                @Override
                public void subscribe(CompletableEmitter emitter) throws Exception {

                }
            });

        }

    }

}
