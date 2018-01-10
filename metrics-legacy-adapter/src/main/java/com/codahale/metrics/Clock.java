package com.codahale.metrics;

@Deprecated
public abstract class Clock {

    public abstract long getTick();

    public long getTime() {
        return System.currentTimeMillis();
    }

    public io.dropwizard.metrics5.Clock transform() {
        if (this instanceof DelegateClock) {
            return ((DelegateClock) this).delegate;
        }
        Clock original = this;
        return new io.dropwizard.metrics5.Clock() {
            @Override
            public long getTick() {
                return original.getTick();
            }
        };
    }

    public static Clock defaultClock() {
        return new DelegateClock(io.dropwizard.metrics5.Clock.defaultClock());
    }

    public static class UserTimeClock extends Clock {

        private DelegateClock delegateClock = new DelegateClock(new io.dropwizard.metrics5.Clock.UserTimeClock());

        @Override
        public long getTick() {
            return delegateClock.getTick();
        }
    }

    public static class DelegateClock extends Clock {

        private final io.dropwizard.metrics5.Clock delegate;

        public DelegateClock(io.dropwizard.metrics5.Clock delegate) {
            this.delegate = delegate;
        }

        @Override
        public long getTick() {
            return delegate.getTick();
        }
    }
}
