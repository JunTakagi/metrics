package com.codahale.metrics;

@Deprecated
public class UniformReservoir implements Reservoir {

    private io.dropwizard.metrics5.UniformReservoir delegate;

    public UniformReservoir() {
        this(new io.dropwizard.metrics5.UniformReservoir());
    }

    public UniformReservoir(int size) {
        this(new io.dropwizard.metrics5.UniformReservoir(size));
    }

    public UniformReservoir(io.dropwizard.metrics5.UniformReservoir delegate) {
        this.delegate = delegate;
    }

    @Override
    public io.dropwizard.metrics5.Reservoir getDelegate() {
        return delegate;
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
    public Snapshot getSnapshot() {
        return new Snapshot.Adapter(delegate.getSnapshot());
    }
}
