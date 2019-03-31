package Engine.core;

import game.TestGame;

public class Main {

    public static void main(String[] args) {
        try{
            boolean vSync = true;
            IGame game = new TestGame();
            CoreEngine engine = new CoreEngine("GAME", 600, 480, vSync, game);
            engine.start();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
