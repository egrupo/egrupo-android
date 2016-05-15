package pt.egrupo.app.utils.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    private static RxBus instance;

    public static RxBus instanceOf() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }


    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }
}