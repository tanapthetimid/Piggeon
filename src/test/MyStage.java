package test;

import engine.*;

import java.util.LinkedList;

public class MyStage extends Stage
{
    public Camera initStage(Node rootNode, LinkedList<GameObject> updateList)
    {
        for(int x = 0; x < 10; x++)
        {
            GameObject gogo = new MyGameObject("game_engine_design_class.png");
            rootNode.attachChild(gogo);
            updateList.add(gogo);
            gogo.setX(gogo.getWidth() / 2);
            gogo.setY(gogo.getHeight() / 2);
            gogo.setY(gogo.getHeight() / 2);
            gogo.setScaleX(0.2f);
            gogo.setScaleY(0.2f);
        }
        return new Camera(rootNode);
    }
}
