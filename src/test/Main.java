package test;

import engine.*;

/**
 * Main test class
 */
public class Main
{
    public static void main(String[] args)
    {
        GameLoop engine = new GameLoop();
        engine.init("test", null
                , 500,500
                ,50,50,0);

        MyStage stage = new MyStage();
        engine.startStage(stage);
    }
}
