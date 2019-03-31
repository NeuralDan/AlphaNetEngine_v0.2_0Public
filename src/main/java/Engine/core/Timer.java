package Engine.core;

public class Timer {

    private double lastLoopTime;

    public void init(){
        lastLoopTime = getTime();
    }

    public double getTime(){
        return System.nanoTime() / 100_000_000.0;
    }

    public float getElapsedTime(){
        double time = getTime();
        float elapseTime = (float)(time - lastLoopTime);
        lastLoopTime = time;
        return elapseTime;
    }

    public double getLastLoopTime(){
        return lastLoopTime;
    }

}
