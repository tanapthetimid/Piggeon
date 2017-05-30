package src;

import java.util.ArrayList;
public abstract class GameObject{
    private int texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private float rotation = 0;
    private float scaleX = 1;
    private float scaleY = 1;
    private boolean isLine = false;
    private float[] lineStart = new float[2];
    private float[] lineEnd = new float[2];
    private float lineThickness = 1;

    public GameObject(float width, float height){
        this.width = width;
        this.height = height;
    }
    
    //returns an arraylist formatted as such: a positive or negative int followed by objects to operate on
    //positive 1 means to add that game object -1 means to remove the game object
    //example {1,object1,object2, -1, object3}
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
    public void setTexture(int texture){this.texture = texture;}
    public void setRotation(float degrees){this.rotation = degrees;}
    public void setIsLine(boolean isLine){this.isLine = isLine;}
    public void setLineStart(float[] start){lineStart = start;}
    public void setLineEnd(float[] end){lineEnd = end;}
    public void setLineThickness(float thickness){lineThickness = thickness;}
    //accessors
    public float getX(){return x;}
    public float getY(){return y;}
    public int getIntX(){return (int)x;}//for rendering
    public int getIntY(){return (int)y;}//for rendering
    public float getRawWidth(){return width;}
    public float getRawHeight(){return height;}
    public float getWidth(){return width * scaleX;}
    public float getHeight(){return height * scaleY;}
    public float getScaleX(){return scaleX;}
    public float getScaleY(){return scaleY;}
    public int getIntWidth(){return (int)width;}//for rendering
    public int getIntHeight(){return (int)height;}//for rendering
    public int getTexture(){return texture;}
    public float getRotation(){return rotation;}
    public boolean isLine(){return isLine;}
    public float[] getLineStart(){return lineStart;}
    public float[] getLineEnd(){return lineEnd;}
    public float getLineThickness(){return lineThickness;}
    //rendering values need to be integer because of AWT's parameter
}