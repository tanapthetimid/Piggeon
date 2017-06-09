package engine;

import util.*;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import static util.FloatMatrixUtils.multiplySquares;

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

    //the current working stage
    private Stage stage;

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
        WindowHandler.init(title, cursorFile
                , canvasWidth, canvasHeight
                , winOffsetX, winOffsetY
                , borderWidth
        );

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.borderWidth = borderWidth;
        shaderProgram = new ShaderProgram();
        shaderProgram.attachVertexShader("shader/vertex_shader/shader.vs");
        shaderProgram.attachFragmentShader("shader/fragment_shader/shader.fs");
        shaderProgram.link();
        //debug
        //fakeinit();
        //run();
    }

    public void startStage(Stage stage)
    {
        this.stage = stage;
        run();
    }

    public void stopLoop()
    {
        stopRequest = true;
    }


    //game loop
    public void run()
    {
        while (!glfwWindowShouldClose(WindowHandler.getWindowId()) && !stopRequest)
        {
            processUpdates();//glfwPollEvents();
            render();
        }
        shaderProgram.dispose();
        glfwDestroyWindow(WindowHandler.getWindowId());
        glfwTerminate();
    }

    private void processUpdates()
    {
        glfwPollEvents();

        GameObject[] updateObjects = stage.getUpdateListAsArray();
        for (GameObject gameObject : updateObjects)
        {
            gameObject.update();
        }
    }

    private void render()
    {
        glClear(GL_COLOR_BUFFER_BIT);
        //bind shader program
        shaderProgram.bind();

        renderNode(stage.getRootNode(), FloatMatrixUtils.createIdentity4x4());
    }

    private void renderNode(Node node, float[] transformMat4fv)
    {
        float[] scale = FloatMatrixUtils.scaleTransformMatrix(node.getScaleX(), node.getScaleY());
        float[] rotate = FloatMatrixUtils.rotateTransformMatrix(node.getRotationAngle());
        float[] translate = FloatMatrixUtils.translateTransformMatrix(node.getX(), node.getY());

        float[] mat4fvNew = multiplySquares(translate,
                                    multiplySquares(rotate,
                                            multiplySquares(scale, transformMat4fv, 4),4),4);

        if(node instanceof GameObject)
        {
            renderGameObject((GameObject) node, mat4fvNew);
        }

        for (Node child : node.getChildren())
        {
            renderNode(child, mat4fvNew);
        }
    }

    private void renderGameObject(GameObject gameObject, float[] transformMat4fv)
    {

    }

    private int currentTexture = -1;
    int imageID;
    int vaoID;

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
                        1, 0, 0, 0.5f,
                        0, 1, 0, 0.5f,
                        0, 0, 1, 0f,
                        0, 0, 0, 1
                };

        int tloc = glGetUniformLocation(shaderProgram.getID(), TRANSLATE);
        glUniformMatrix4fv(tloc, false, translate);

        float[] scale = new float[]
                {
                        2, 0, 0, 0,
                        0, 2, 0, 0,
                        0, 0, 2, 0,
                        0, 0, 0, 1
                };

        int sloc = glGetUniformLocation(shaderProgram.getID(), SCALE);
        glUniformMatrix4fv(sloc, false, scale);

        float[] rotate = new float[]
                {
                        1, 0, 0, 0,
                        0, 1, 0, 0,
                        0, 0, 1, 0,
                        0, 0, 0, 1
                };

        int rloc = glGetUniformLocation(shaderProgram.getID(), ROTATE);
        glUniformMatrix4fv(rloc, false, rotate);

        glDrawArrays(GL_TRIANGLES, 0, 6);
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
                        -0.5f, 0.5f,
                        -0.5f, -0.5f,
                        0.5f, -0.5f,
                        -0.5f, 0.5f,
                        0.5f, -0.5f,
                        0.5f, 0.5f
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

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordsBuffer.put(texCoords).flip();

        int vboTexCoordsID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboTexCoordsID);
        glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
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
        return 0;
    }
}