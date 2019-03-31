package Engine.rendering;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Window {

    private String title;

    private int width;
    private int height;

    private long windowHandle;

    private boolean resize;
    private boolean vSync;

    public Window(String title, int width, int height, boolean vSync){
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.resize = false;
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        windowHandle = glfwCreateWindow(width, height, title, 0, 0);
        if(windowHandle == 0){
            throw new RuntimeException("Failed to create the GLFW window!");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
           this.width = width;
           this.height = height;
           this.setResize(true);
        });

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
                glfwSetWindowShouldClose(window, true);
            }
        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2);

        glfwMakeContextCurrent(windowHandle);

        if(isvSync()){
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowHandle);

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void setClearColor(float r, float g, float b, float a){
        glClearColor(r, g, b, a);
    }

    public boolean windowShouldClose(){
        return glfwWindowShouldClose(windowHandle);
    }

    public void update(){
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public void setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
    }

    public boolean isResize() {
        return resize;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }
}
