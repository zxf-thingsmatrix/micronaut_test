package lost.canvas.micronaut_test.interceptor;

import io.micronaut.aop.InterceptPhase;

public enum InterceptPhaseEx {

    /**
     * ResultInterceptor
     */
    result(InterceptPhase.VALIDATE.getPosition() - 20);

    private final int position;


    InterceptPhaseEx(int position) {
        this.position = position;
    }


    public int getPosition() {
        return position;
    }
}
