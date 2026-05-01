package reactor.util.retry;

import reactor.util.context.Context;
import reactor.util.context.ContextView;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
final class ImmutableRetrySignal implements Retry.RetrySignal {
    final Throwable failure;
    final long failureSubsequentIndex;
    final long failureTotalIndex;
    final ContextView retryContext;

    @Override // reactor.util.retry.Retry.RetrySignal
    public Retry.RetrySignal copy() {
        return this;
    }

    ImmutableRetrySignal(long j, long j2, Throwable th) {
        this(j, j2, th, Context.empty());
    }

    ImmutableRetrySignal(long j, long j2, Throwable th, ContextView contextView) {
        this.failureTotalIndex = j;
        this.failureSubsequentIndex = j2;
        this.failure = th;
        this.retryContext = contextView;
    }

    @Override // reactor.util.retry.Retry.RetrySignal
    public long totalRetries() {
        return this.failureTotalIndex;
    }

    @Override // reactor.util.retry.Retry.RetrySignal
    public long totalRetriesInARow() {
        return this.failureSubsequentIndex;
    }

    @Override // reactor.util.retry.Retry.RetrySignal
    public Throwable failure() {
        return this.failure;
    }

    @Override // reactor.util.retry.Retry.RetrySignal
    public ContextView retryContextView() {
        return this.retryContext;
    }

    public String toString() {
        return "attempt #" + (this.failureTotalIndex + 1) + " (" + (this.failureSubsequentIndex + 1) + " in a row), last failure={" + this.failure + '}';
    }
}
