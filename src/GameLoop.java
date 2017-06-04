package src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import static org.lwjgl.opengl.GL13.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL13;

import org.lwjgl.BufferUtils;

public class GameLoop
{
	/**
	 * Specifies operations to be done on the return GameObject from the update
	 * Method
	 */
    public final int OPERATION_REMOVE = -1;
    public final int OPERATION_ADD = 1;

    //names for translate, scale, and rotate mat4 in shader
    private final String TRANSLATE = "translate";
    private final String ROTATE = "rotate";
    private final String SCALE = "scale";

    //variables for width and height
    private int canvasWidth;
    private int canvasHeight;

    /**
     * the current working stage
     */
    private Stage stage;
    
    //GameObject(s) that are processed and drawn
    private HashSet<GameObject> gameObjects;
    
    //specifies width of black border around the window
    private int borderWidth;
	
	//manual stop gameloop request
	private boolean stopRequest = false;

	//OpenGL shader program ID
	private ShaderProgram shaderProgram;
	
	public void init(String title, String cursorFile
                        , int canvasWidth, int canvasHeight
                        , int winOffsetX, int winOffsetY
                        , int borderWidth)
	{
		WindowHandler.init(    title		,  cursorFile
	                        ,  canvasWidth  ,  canvasHeight
	                        ,  winOffsetX   ,  winOffsetY
	                        ,  borderWidth
	                        );

		this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.borderWidth = borderWidth;
		shaderProgram = new ShaderProgram();
		shaderProgram.attachVertexShader("src/shader/vertex_shader/shader.vs");
		shaderProgram.attachFragmentShader("src/shader/fragment_shader/shader.fs");
		shaderProgram.link();
		//debug
		fakeinit();
		run();
	}

	/**
	 * initialises the current stage and initialises instance variables
	 * @param stage1 		- the current stage object
	 * @param width 		- width of window
	 * @param height 	- height of window
	 */
    public void initStage(Stage stage)
    {
        this.stage = stage;
        //initializes stage
        stage.init();

        /*Deprecated ==============*/
        gameObjects = stage.getGameObjects();
        /*=========================*/
    }
    
    public void stopLoop()
    {
    	stopRequest = true;
    }
    

    /**
     * GameLoop
     */
    public void run()
    {
        while(!glfwWindowShouldClose(WindowHandler.getWindowId()) && !stopRequest)
        {
            //handles processing in here
            /*processUpdates();*/glfwPollEvents();
            renderFrame();
        }
        shaderProgram.dispose();
        glfwDestroyWindow(WindowHandler.getWindowId());
        glfwTerminate();
    }

    private void processUpdates()
    {
        glfwPollEvents();

        HashSet<GameObject> toRemove = new HashSet<>();
        HashSet<GameObject> toAdd = new HashSet<>();
        Iterator<GameObject> it = gameObjects.iterator();
        while(it.hasNext())
        {
            GameObject go = it.next();
            ArrayList returnVal = go.update();
            int operation = 0;
            if(returnVal != null)
            {
                for(Object o: returnVal)
                {
                    if(o instanceof Integer)
                    {
                        operation = (Integer) o;
                    }
                    else
                    {
                        if(operation == OPERATION_ADD)
                        {
                            toAdd.add((GameObject)o);
                        }
                        else if(operation == OPERATION_REMOVE)
                        {
                            toRemove.add((GameObject)o);
                        }
                    }
                }
            }
        }
        gameObjects.removeAll(toRemove);
        gameObjects.addAll(toAdd);
    }
    
    private int currentTexture = -1;
    
    private void renderFrame()
    {
        glClear(GL_COLOR_BUFFER_BIT);
        /*
        //to be implemented
        for(GameObject go: gameObjects)
        {
        	
        }
        */
        drawCrop();

        shaderProgram.bind();

        GL13.glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, imageID);

        glBindVertexArray(vaoID);

        //transformation matrices
        float[] translate = new float[]
        {
        	1,0,0,0.5f,
        	0,1,0,0.5f,
        	0,0,1,0f,
        	0,0,0,1
        };

        int tloc = glGetUniformLocation(shaderProgram.getID(), TRANSLATE);
        glUniformMatrix4fv(tloc, false, translate);

        float[] scale = new float[]
        {
        	2,0,0,0,
        	0,2,0,0,
        	0,0,2,0,
        	0,0,0,1
        };

        int sloc = glGetUniformLocation(shaderProgram.getID(), SCALE);
        glUniformMatrix4fv(sloc, false, scale);

        float[] rotate = new float[]
        {
        	1,0,0,0,
        	0,1,0,0,
        	0,0,1,0,
        	0,0,0,1
        };

        int rloc = glGetUniformLocation(shaderProgram.getID(), ROTATE);
        glUniformMatrix4fv(rloc, false, rotate);

        glDrawArrays(GL_TRIANGLES, 0 , 6);
        //glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, 0);
        glBindVertexArray(0);
        shaderProgram.unbind();
        
        glfwSwapBuffers(WindowHandler.getWindowId());
    }
    
    private void drawCrop()
    {
        //to be implemented
    }

    private void fakeinit()
    {
    	ImageInfo info = ImageUtils.loadImage("test/res/scorched_earth_desktop.jpg");
    	imageID = info.id;

    	vaoID = glGenVertexArrays();
    	glBindVertexArray(vaoID);

    	float[] vertices = new float[]
    	{
            -0.5f,  0.5f,
		    -0.5f, -0.5f,
		     0.5f, -0.5f,
		    -0.5f,  0.5f,
		     0.5f, -0.5f,
		     0.5f,  0.5f
        };

        float[] texCoords = new float[]
        {
        	0.0f, 0.0f,
    		0.0f, 1.0f,
    		1.0f, 1.0f,
    		0.0f, 0.0f,
		    1.0f, 1.0f,
		    1.0f, 0.0f
        };
/*
        short[] indices = new short[]
        {
            0, 1, 2,  // The indices for the left triangle
            1, 2, 3   // The indices for the right triangle
        };
*/
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vboVertID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0,2,GL_FLOAT, false, 0,0);

        FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordsBuffer.put(texCoords).flip();

        int vboTexCoordsID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTexCoordsID);
        glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(1,2,GL_FLOAT, false , 0 , 0);
/*
        ShortBuffer indicesBuffer = BufferUtils.createShortBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        int eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
*/
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    public static int createVertexArrayObject()
    {
    	int vaoID = glGenVertexArrays();
    	glBindVertexArray(vaoID);
    }
}