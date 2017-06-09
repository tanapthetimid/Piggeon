package engine;

import util.ImageUtils;

import java.util.ArrayList;
public abstract class GameObject extends Node
{
    private int textureID;
    private float width;
    private float height;

    public GameObject(String texturePath)
    {
    	 ImageInfo imageInfo = ImageUtils.loadImage(texturePath);
    	 setTextureID(imageInfo.id);
    	 setRawWidth(imageInfo.width);
    	 setRawHeight(imageInfo.height);
    	 
    }
    /**
     * returns an arraylist formatted as such: a positive or
     * negative int followed by objects to operate on
     * positive 1 means to add that game object -1 means to remove the game object
     * example {1,object1,object2, -1, object3}
     */
    public abstract ArrayList<Object> update();
    
    //interface methods

    //modifiers
    public void setRawWidth(float width){this.width = width;}
    public void setRawHeight(float height){this.height = height;}
    public void setLocation(float x, float y){setX(x); setY(y);}
    public void setTextureID(int textureID){this.textureID = textureID;}

    //accessors
    public float getRawWidth(){return width;}
    public float getRawHeight(){return height;}
    public float getWidth(){return width * getScaleX();}
    public float getHeight(){return height * getScaleY();}
    public int getTextureID(){return textureID;}
}