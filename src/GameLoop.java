package src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop{
	/**
	* Specifies operations to be done on the return GameObject from the update
	* Method
	*/
    public static final int OPERATION_REMOVE = -1;
    public static final int OPERATION_ADD = 1;
    //variables for width and height
    private static int canvasWidth;
    private static int canvasHeight;

    /**
    * the current working stage
    */
    private static Stage stage;
    
    //GameObject(s) that are processed and drawn
    private static HashSet<GameObject> gameObjects;
    
    //specifies width of black border around the window
    private static int borderWidth;
	
	//manual stop gameloop request
	private static boolean stopRequest = false;
	
	/**
	* initialises the current stage and initialises instance variables
	* @param stage1 		- the current stage object
	* @param width 		- width of window
	* @param height 	- height of window
	*/
    public static void init(Stage stage1, int width, int height, int borderWidth){
        canvasWidth = width;
        canvasHeight = height;
        stage = stage1;
        GameLoop.borderWidth = borderWidth;
        //initializes stage
        stage.init();
        gameObjects = stage.getGameObjects();
        glMatrixMode(GL_MODELVIEW);
    }
    
    public static void stopLoop(){
    	stopRequest = true;
    }
    

    /**
    * GameLoop
    */
    public static void start(){
        while(!glfwWindowShouldClose(Game.getWindowId()) && !stopRequest){
            //handles processing in here
            processUpdates();
            renderFrame();
        }
    }

    private static void processUpdates(){
        glfwPollEvents();

        HashSet<GameObject> toRemove = new HashSet<>();
        HashSet<GameObject> toAdd = new HashSet<>();
        Iterator<GameObject> it = gameObjects.iterator();
        while(it.hasNext()){
            GameObject go = it.next();
            ArrayList returnVal = go.update();
            int operation = 0;
            if(returnVal != null){
                for(Object o: returnVal){
                    if(o instanceof Integer){
                        operation = (Integer) o;
                    }else{
                        if(operation == OPERATION_ADD){
                            toAdd.add((GameObject)o);
                        }else if(operation == OPERATION_REMOVE){
                            toRemove.add((GameObject)o);
                        }
                    }
                }
            }
        }
        gameObjects.removeAll(toRemove);
        gameObjects.addAll(toAdd);
    }
    
    private static int currentTexture = -1;
    
    private static void renderFrame(){
        glClear(GL_COLOR_BUFFER_BIT);
        glPushMatrix();
        
        for(GameObject go: gameObjects){
            if(go.isLine()){
                glDisable(GL_TEXTURE_2D);
                float[] start = go.getLineStart();
                float[] end = go.getLineEnd();
                
                glLineWidth(go.getLineThickness());
                
                glColor3f(0f,0f,0f);
                glBegin(GL_LINE_STRIP);
                    glVertex2f(start[0]+borderWidth, start[1]+borderWidth);
                    glVertex2f(end[0]+borderWidth, end[1]+borderWidth);
                glEnd();
                
                glColor3f(1f,1f,1f);
                glEnable(GL_TEXTURE_2D);
            }else{
                int texture = go.getTexture();
                if(texture != currentTexture || currentTexture == -1){
                    glBindTexture(GL_TEXTURE_2D, texture);
                    currentTexture = texture;
                }
    
                float x = go.getX();
                float y = go.getY();
    
                float width = go.getRawWidth() * go.getScaleX();
                float height = go.getRawHeight() * go.getScaleY();
    
                float rotation = (float)(go.getRotation()*180/Math.PI);
                if(Math.abs(rotation) > 1){
                    glPushMatrix();
                    glTranslatef((x+borderWidth+width/2),(y+borderWidth+height/2),0);
                    glRotatef(rotation,0,0,1);
                    glTranslatef(-(x+borderWidth+width/2), -(y+borderWidth+height/2),0);
                }
                
                float x2 = x + width;
                float y2 = y + height;
                
                glBegin(GL_QUADS);
                    glTexCoord2f(0,0);
                    glVertex2f(x+borderWidth,y+borderWidth);
        
                    glTexCoord2f(1, 0);
                    glVertex2f(x2+borderWidth, y+borderWidth);
        
                    glTexCoord2f(1, 1);
                    glVertex2f(x2+borderWidth, y2+borderWidth);
        
                    glTexCoord2f(0, 1);
                    glVertex2f(x+borderWidth, y2+borderWidth);   
                glEnd();
                
                if(Math.abs(rotation) > 1){
                    glPopMatrix();
                }
            }
        }
        
        drawCrop();
        
        glPopMatrix();
        glfwSwapBuffers(Game.getWindowId());
    }
    
    private static void drawCrop(){
        glDisable(GL_TEXTURE_2D);
        
        glLineWidth(borderWidth*2);
        glColor3f(0f,0f,0f);
                glBegin(GL_LINE_STRIP);
                    glVertex2f(0, 0);
                    glVertex2f(canvasWidth+borderWidth*2, 0);
                glEnd();
                glBegin(GL_LINE_STRIP);
                    glVertex2f(0, 0);
                    glVertex2f(0, canvasHeight+borderWidth*2);
                glEnd();
                glBegin(GL_LINE_STRIP);
                    glVertex2f(canvasWidth+borderWidth, 0);
                    glVertex2f(canvasWidth+borderWidth, canvasHeight+borderWidth*2);
                glEnd();
                glBegin(GL_LINE_STRIP);
                    glVertex2f(0, canvasHeight+borderWidth);
                    glVertex2f(canvasWidth+borderWidth*2, canvasHeight+borderWidth);
                glEnd();
        glColor3f(1f,1f,1f);
        
        glEnable(GL_TEXTURE_2D);
    }
}