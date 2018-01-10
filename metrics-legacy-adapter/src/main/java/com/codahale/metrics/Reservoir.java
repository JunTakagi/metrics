package com.codahale.metrics;

import java.io.OutputStream;

@Deprecated
public interface Reservoir {

    int size();

    void update(long value);

    Snapshot getSnapshot();

    io.dropwizard.metrics5.Reservoir getDelegate();

    class Adapter implements io.dropwizard.metrics5.Reservoir {

        private final Reservoir delegate;

        public Adapter(Reservoir delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public void update(long value) {
            delegate.update(value);
        }

        @Override
        public io.dropwizard.metrics5.Snapshot getSnapshot() {
            Snapshot snapshot = delegate.getSnapshot();
            return new io.dropwizard.metrics5.Snapshot() {
                @Override
                public double getValue(double quantile) {
                    return snapshot.getValue(quantile);
                }

                @Override
                public long[] getValues() {
                    return snapshot.getValues();
                }

                @Override
                public int size() {
                    return snapshot.size();
                }

                @Override
                public long getMax() {
                    return snapshot.getMax();
                }

                @Override
                public double getMean() {
                    return snapshot.getMean();
                }

                @Override
                public long getMin() {
                    return snapshot.getMin();
                }

                @Override
                public double getStdDev() {
                    return snapshot.getStdDev();
                }

                @Override
                public void dump(OutputStream output) {
                    snapshot.dump(output);
                }
            };
        }
    }
}
