package com.codahale.metrics;

import java.io.Closeable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Deprecated
public class Timer implements Metered, Sampling {

    private final io.dropwizard.metrics5.Timer delegate;

    public static class Context implements Closeable {
        final io.dropwizard.metrics5.Timer.Context context;

        public Context(io.dropwizard.metrics5.Timer.Context context) {
            this.context = context;
        }

        public long stop() {
            return context.stop();
        }

        @Override
        public void close() {
            context.close();
        }
    }

    public Timer() {
        this(new io.dropwizard.metrics5.Timer());
    }

    public Timer(Reservoir reservoir) {
        this(reservoir, Clock.defaultClock());
    }

    public Timer(Reservoir reservoir, Clock clock) {
        this(new io.dropwizard.metrics5.Timer(new Reservoir.Adapter(reservoir), clock.transform()));
    }

    public Timer(io.dropwizard.metrics5.Timer delegate) {
        this.delegate = delegate;
    }

    @Override
    public Snapshot getSnapshot() {
        return new Snapshot.Adapter(delegate.getSnapshot());
    }

    @Override
    public long getCount() {
        return delegate.getCount();
    }

    @Override
    public double getFifteenMinuteRate() {
        return delegate.getFifteenMinuteRate();
    }

    @Override
    public double getFiveMinuteRate() {
        return delegate.getFiveMinuteRate();
    }

    @Override
    public double getMeanRate() {
        return delegate.getMeanRate();
    }

    @Override
    public double getOneMinuteRate() {
        return delegate.getOneMinuteRate();
    }

    public void update(long duration, TimeUnit unit) {
        delegate.update(duration, unit);
    }

    public <T> T time(Callable<T> event) throws Exception {
        return delegate.time(event);
    }

    public void time(Runnable event) {
        delegate.time(event);
    }

    public Context time() {
        return new Context(delegate.time());
    }

    public io.dropwizard.metrics5.Timer getDelegate() {
        return delegate;
    }
}
