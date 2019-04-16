package Engine.core;

import Engine.rendering.Window;

public interface IGame {

    void init(Window window) throws Exception;

    void input(CoreEngine coreEngine);

    void update(float interval);

    void render(Window window);

    void cleanUp();

}
