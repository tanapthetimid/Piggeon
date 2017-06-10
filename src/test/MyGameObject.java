package test;

import engine.*;

import java.util.ArrayList;

public class MyGameObject extends GameObject
{
    public MyGameObject(String texturePath)
    {
        super(new String[]{texturePath});
        dx = 10;
        dy = 10;
    }

    int dx;
    int dy;

    @Override
    public void update(Stage stage)
    {
        if(getX() > 500)
        {
            dx =-(int)( Math.random() * 20) - 1;
        }
        else if(getX() < 0)
        {
            dx =(int)( Math.random() * 20) + 1;
        }

        if(getY() > 500)
        {
            dy =-(int)( Math.random() * 20) - 1;
        }
        else if(getY() < 0)
        {
            dy =(int)( Math.random() * 20) + 1;
        }
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
