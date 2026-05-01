package com.tencent.cloud.stream.tts.core.p032ws;

import android.util.Log;
import com.tencent.cloud.stream.tts.core.exception.SynthesizerException;
import com.tencent.cloud.stream.tts.core.exception.SynthesizerExceptionType;

/* JADX INFO: loaded from: classes4.dex */
public class StateMachine {
    public static final String TAG = "StateMachine";
    protected State state = State.STATE_INIT;

    public State getState() {
        return this.state;
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'STATE_FAIL' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static abstract class State {
        private static final /* synthetic */ State[] $VALUES;
        public static final State STATE_CLOSED;
        public static final State STATE_COMPLETE;
        public static final State STATE_FAIL;
        public static final State STATE_INIT;
        public static final State STATE_SEND;
        public static final State STATE_START;
        public static final State STATE_STOP_SENT;
        int value;

        public abstract void checkSend() throws Exception;

        public abstract void checkStart() throws Exception;

        public abstract void checkStop() throws Exception;

        public abstract State closed();

        public abstract State complete();

        public abstract State fail();

        public abstract State init();

        public abstract State send();

        public abstract State start();

        public abstract State stopSend() throws Exception;

        public static State valueOf(String name) {
            return (State) Enum.valueOf(State.class, name);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }

        static {
            int i = 0;
            State state = new State("STATE_FAIL", i, -1) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.1
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() throws SynthesizerException {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't start,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() throws SynthesizerException {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't send,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() throws SynthesizerException {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't stop,current state is " + this);
                }
            };
            STATE_FAIL = state;
            int i2 = 1;
            State state2 = new State("STATE_INIT", i2, i) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.2
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() {
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't send,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't stop,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return STATE_START;
                }
            };
            STATE_INIT = state2;
            int i3 = 2;
            State state3 = new State("STATE_START", i3, i2) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.3
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() {
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't start,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't stop,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return STATE_FAIL;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return STATE_COMPLETE;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return STATE_CLOSED;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return STATE_SEND;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return STATE_STOP_SENT;
                }
            };
            STATE_START = state3;
            int i4 = 3;
            State state4 = new State("STATE_SEND", i4, i3) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.4
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() {
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() {
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't start,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return STATE_FAIL;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return STATE_COMPLETE;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return STATE_CLOSED;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return STATE_STOP_SENT;
                }
            };
            STATE_SEND = state4;
            int i5 = 4;
            State state5 = new State("STATE_STOP_SENT", i5, i4) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.5
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() {
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't send,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't start,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return STATE_FAIL;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return STATE_COMPLETE;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return STATE_CLOSED;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return STATE_STOP_SENT;
                }
            };
            STATE_STOP_SENT = state5;
            State state6 = new State("STATE_COMPLETE", 5, i5) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.6
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() {
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() {
                    Log.w(StateMachine.TAG, "task is completed before sending binary");
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() {
                    Log.w(StateMachine.TAG, "task is completed before sending stop command");
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return STATE_FAIL;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return STATE_CLOSED;
                }
            };
            STATE_COMPLETE = state6;
            int i6 = 6;
            State state7 = new State("STATE_CLOSED", i6, i6) { // from class: com.tencent.cloud.stream.tts.core.ws.StateMachine.State.7
                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State closed() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State complete() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State fail() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State init() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State send() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State start() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public State stopSend() {
                    return this;
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkSend() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't send,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStart() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't start,current state is " + this);
                }

                @Override // com.tencent.cloud.stream.tts.core.ws.StateMachine.State
                public void checkStop() throws Exception {
                    throw new SynthesizerException(SynthesizerExceptionType.INCORRECT_STATE, "can't stop,current state is " + this);
                }
            };
            STATE_CLOSED = state7;
            $VALUES = new State[]{state, state2, state3, state4, state5, state6, state7};
        }

        private State(String $enum$name, int $enum$ordinal, int value) {
            this.value = value;
        }
    }
}
