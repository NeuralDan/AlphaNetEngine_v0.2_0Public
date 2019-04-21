package Engine.core;

import Engine.rendering.Window;

public interface IGame {

    void init(Window window) throws Exception;

    void input(MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);

    void render(Window window);

    void cleanUp();

}
