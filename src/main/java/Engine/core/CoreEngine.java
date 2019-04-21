package Engine.core;

import Engine.rendering.Window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class CoreEngine implements Runnable {

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

    private Thread gameLoopThread;

    private Timer timer;

    private IGame game;

    private static Window window;
    private static MouseInput mouseInput;

    public CoreEngine(String title, int width, int height, boolean vSync, IGame game) throws Exception{
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(title, width, height, vSync);
        mouseInput = new MouseInput();
        this.game = game;
        timer = new Timer();
    }

    public void start(){
        gameLoopThread.start();
    }

    @Override
    public void run(){
        try{
            init();
            gameLoop();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            cleanUp();
        }
    }

    protected void init() throws Exception{
        window.init();
        mouseInput.init(window);
        timer.init();
        game.init(window);
    }

    protected void gameLoop(){
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while(running && !window.windowShouldClose()){
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while(accumulator >= interval){
                update(interval);
                accumulator -= interval;
            }

            render();

            if(!window.isvSync()){
                sync();
            }
        }
        gameLoopThread.interrupt();
    }

    private void sync(){
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while(timer.getTime() < endTime){
            try{
                Thread.sleep(1);
            }catch (InterruptedException ie){}
        }
    }

    protected void cleanUp(){
        game.cleanUp();
    }

    public static boolean isKeyPressed(int keyCode){
        return glfwGetKey(window.getWindowHandle(), keyCode) == GLFW_PRESS;
    }

    protected void input(){
        mouseInput.input(window);
        game.input(mouseInput);
    }

    protected void update(float interval){
        game.update(interval, mouseInput);
    }

    protected void render(){
        game.render(window);
        window.update();
    }

    public static Window getWindow() {
        return window;
    }
}
