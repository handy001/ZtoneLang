package android.collection;

import android.log.Log;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Deprecated
public final class ChokePoint<E> extends LinkedBlockingQueue<E> {

    private static final String TAG = "ChokePoint";
    public ChokePoint() {
        super(1);
    }

    @Override
    public E poll(long timeout, TimeUnit unit) {
        E e = null;

        try {
            e = super.poll(timeout, unit);
        } catch (Exception exc) {
            Log.i(TAG, exc);
        }

        return e;
    }
}
