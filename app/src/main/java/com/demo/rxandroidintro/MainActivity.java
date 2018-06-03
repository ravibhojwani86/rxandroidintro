package com.demo.rxandroidintro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TextView mAirplaneModeView;

    private SingleObserver<Boolean> mSingleObserver = new SingleObserver<Boolean>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(Boolean modeOn) {
            setAirplaneModeOnView(modeOn);
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    private DisposableObserver<Boolean> mDisposableObserver = new DisposableObserver<Boolean>() {
        @Override
        public void onNext(Boolean aBoolean) {
            setAirplaneModeOnView(aBoolean);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAirplaneModeView = findViewById(R.id.tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        supscribeAirplaneMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unscribeAirplaneMode();
    }

    private void unscribeAirplaneMode(){
        if(!mCompositeDisposable.isDisposed()){
            mCompositeDisposable.dispose();
        }
    }

    private void supscribeAirplaneMode(){
        String executionMode = "Executed using async task";
        int mode = new Random().nextInt(5);

        switch (mode) {
            case 0: // run traditional async task
                new AirplaneModeAsyncTask(this, new Callback() {
                    @Override
                    public void setAirplaneMode(Boolean mode) {
                        setAirplaneModeOnView(mode);
                    }
                }).execute();
                break;

            case 1: // using Single create api
                executionMode = "Executed using Single.create()...";
                subscribeAirplaneModeSingleCreateObservable();
                break;

            case 2: // using Single fromCallable api
                executionMode = "Executed using Single.fromCallable()...";
                subscribeAirplaneModeSingleCallableObservable();
                break;

            case 3: // using Observable create api
                executionMode = "Executed using Observable.create()...";
                subscribeAirplaneModeCreateObservable();
                break;

            case 4: // using Observable fromCallable api
                executionMode = "Executed using Observable.fromCallable()...";
                subscribeAirplaneModeFromCallableObservable();
                break;
        }

        Toast.makeText(this, executionMode, Toast.LENGTH_SHORT).show();
    }

    private void subscribeAirplaneModeSingleCreateObservable(){
        RxifyAirplaneModeAsyncTask.SINGLE.getAirplaneModeObservableCreate(this)
                // As we subscribe on Schedulers.io() subscription happens on background thread
                .subscribeOn(Schedulers.io())
                // As we observe on AndroidSchedulers.mainThread() Observor observes on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSingleObserver);
    }

    private void subscribeAirplaneModeSingleCallableObservable(){
        RxifyAirplaneModeAsyncTask.SINGLE.getAirplaneModeObservableFromCallable(this)
                // As we subscribe on Schedulers.io() subscription happens on background thread
                .subscribeOn(Schedulers.io())
                // As we observe on AndroidSchedulers.mainThread() Observor observes on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSingleObserver);
    }

    private void subscribeAirplaneModeCreateObservable(){
        Disposable disposable = RxifyAirplaneModeAsyncTask.OBSERVABLE.getAirplaneModeObservableCreate(this)
                // subscription happens on background thread
                .subscribeOn(Schedulers.io())
                // As we observe on AndroidSchedulers.mainThread() Observor observes on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);

        // Adding to composite disposable so subscription can be disposed
        // during activity/fragment lifecycle
        mCompositeDisposable.add(disposable);
    }

    private void subscribeAirplaneModeFromCallableObservable(){
        Disposable disposable = RxifyAirplaneModeAsyncTask.OBSERVABLE.getAirplaneModeObservableFromCallable(this)
                // subscription happens on background thread
                .subscribeOn(Schedulers.io())
                // As we observe on AndroidSchedulers.mainThread() Observor observes on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(mDisposableObserver);

        // Adding to composite disposable so subscription can be disposed
        // during activity/fragment lifecycle
        mCompositeDisposable.add(disposable);
    }

    private void setAirplaneModeOnView(boolean mode){
        mAirplaneModeView.setText(getFormattedString(mode));
    }

    private String getFormattedString(boolean isOn){
        return String.format(" Airplane mode on %b ", isOn);
    }

}
