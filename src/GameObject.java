package src;

import java.util.ArrayList;
public abstract class GameObject
{
    private int textureID;
    private float x;
    private float y;
    private float width;
    private float height;
    private float rotation = 0;
    private float scaleX = 1;
    private float scaleY = 1;

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
    public void setX(float x){this.x = x;}
    public void setY(float y){this.y = y;}
    public void setRawWidth(float width){this.width = width;}
    public void setRawHeight(float height){this.height = height;}
    public void setScaleX(float scaleX){this.scaleX = scaleX;}
    public void setScaleY(float scaleY){this.scaleY = scaleY;}
    public void setLocation(float x, float y){this.x = x; this.y = y;}
    public void setTextureID(int textureID){this.textureID = textureID;}
    public void setRotation(float degrees){this.rotation = degrees;}
    //a ccessors
    public float getX(){return x;}
    public float getY(){return y;}
    //(Depricated)public int getIntX(){return (int)x;}//for rendering
    //(Depricated)public int getIntY(){return (int)y;}//for rendering
    public float getRawWidth(){return width;}
    public float getRawHeight(){return height;}
    public float getWidth(){return width * scaleX;}
    public float getHeight(){return height * scaleY;}
    public float getScaleX(){return scaleX;}
    public float getScaleY(){return scaleY;}
    //(Depricated) public int getIntRawWidth(){return (int)width;}//for rendering
    //(Depricated)public int getIntRawHeight(){return (int)height;}//for rendering
    public int getTextureID(){return textureID;}
    public float getRotation(){return rotation;}
    //rendering values need to be integer because of AWT's parameter
}