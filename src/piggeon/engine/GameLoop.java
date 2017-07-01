/* 
 * Copyright (c) 2017, Tanapoom Sermchaiwong
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name 'Piggeon' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package piggeon.engine;

import piggeon.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import static piggeon.util.FloatMatrixUtils.multiplySquares;

import org.lwjgl.opengl.GL13;

public class  GameLoop
{
    /**
     * Specifies operations to be done on the return GameObject from the update
     * Method
     */
    public static final int OPERATION_REMOVE = -1;
    public static final int OPERATION_ADD = 1;

    //names for translate, scale, and rotate mat4 in piggeon.shader
    private static final String TRANSFORMATION_MATRIX = "transformation";

    //variables for width and height
    private static float canvasWidth;
    private static float canvasHeight;

    //the current working stage
    private static Stage stage;

    //specifies width of black border around the window
    private static int borderWidth;

    //manual stop gameloop request
    private static boolean stopRequest = false;

    //OpenGL piggeon.shader program ID
    private static ShaderProgram shaderProgram;

    /**
     * initializes the game loop piggeon.engine and initializes piggeon.shader program
     * @param title         Window title
     * @param cursorFile    File for cursor's image. Null for default cursor.
     * @param canvasWidth   Window width;
     * @param canvasHeight  Window height;
     * @param winOffsetX    Window offset from the left side of screen.
     * @param winOffsetY    Window offset from the top of the screen.
     * @param borderWidth   Width of border around game window
     */
    public static void init(String title, String cursorFile
            , int canvasWidth, int canvasHeight
            , int winOffsetX, int winOffsetY
            , int borderWidth)
    {
        //initialize window
        WindowHandler.init(title, cursorFile
                , canvasWidth, canvasHeight
                , winOffsetX, winOffsetY
                , borderWidth
        );

        //initializes variables
        GameLoop.canvasWidth = canvasWidth;
        GameLoop.canvasHeight = canvasHeight;
        GameLoop.borderWidth = borderWidth;

        //initializes piggeon.shader program
        shaderProgram = new ShaderProgram();
        shaderProgram.attachVertexShader("piggeon/shader/vertex_shader/shader.vs");
        shaderProgram.attachFragmentShader("piggeon/shader/fragment_shader/shader.fs");
        shaderProgram.link();
    }

    /**
     * starts specified stage
     * @param stage
     */
    public static void startStage(Stage stage)
    {
        GameLoop.stage = stage;
        run();
    }

    /**
     * Request to stop the gameloop and exit the game.
     * User should handle stage saving BEFORE this method
     * is called.
     */
    public static void stopLoop()
    {
        stopRequest = true;
    }


    //game loop
    private static void run()
    {
        //DEBUG
        int count = 0;
        long last = System.currentTimeMillis();
        while (!glfwWindowShouldClose(WindowHandler.getWindowId()) && !stopRequest)
        {
            //DEBUG
            if(count == 100)
            {
                long diff = System.currentTimeMillis() - last;

                System.out.println((double)count * 1000.0 / (double)diff);

                last = System.currentTimeMillis();
                count = 0;
            }
            count++;
            //end
            processUpdates();//glfwPollEvents();
            render();
        }

        //clean up
        shaderProgram.dispose();
        glfwDestroyWindow(WindowHandler.getWindowId());
        glfwTerminate();
    }

    private static void processUpdates()
    {
        glfwPollEvents();

        //updates stage's camera
        stage.getCamera().update();

        //updates all updatable game objects
        GameObject[] updateObjects = stage.getUpdateListAsArray();
        for (GameObject gameObject : updateObjects)
        {
            gameObject.update(stage);
        }
    }

    //renders all game objects attached to root node
    private static void render()
    {
        glClear(GL_COLOR_BUFFER_BIT);

        //bind piggeon.shader program
        shaderProgram.bind();

        /*normalizing transformation, which is applied LAST by property
         *of matrices multiplication.*/
        float[] scale = FloatMatrixUtils
                .scaleTransformMatrix(2f/canvasWidth, 2f/ canvasHeight);
        float[] translate = FloatMatrixUtils
                .translateTransformMatrix(-1f, -1f);

        //starts rendering from root node
        renderNode(stage.getRootNode(), multiplySquares(translate, scale,4));

        glfwSwapBuffers(WindowHandler.getWindowId());
    }

    //recursively render game objects and nodes
    private static void renderNode(Node node, float[] transformMat4fv)
    {
        //converts transformations into matrices
        float[] scale = FloatMatrixUtils.scaleTransformMatrix(node.getScaleX(), node.getScaleY());
        float[] rotate = FloatMatrixUtils.rotateTransformMatrix(node.getRotationAngle());
        float[] translate = FloatMatrixUtils.translateTransformMatrix(node.getX(), node.getY());

        //create new transformation matrix
        float[] mat4fvNew = multiplySquares(transformMat4fv,
                                multiplySquares(translate,
                                    multiplySquares(rotate, scale, 4), 4), 4);

        if (node instanceof GameObject)
        {
            renderGameObject((GameObject) node, mat4fvNew);
        }

        for (Node child : node.getChildren())
        {
            renderNode(child, mat4fvNew);
        }
    }

    private static void renderGameObject(GameObject gameObject, float[] transformMat4fv)
    {
        //binds game object's texture and vertex arrays object
        GL13.glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, gameObject.getTextureID());
        glBindVertexArray(gameObject.getVertexArraysObjectID());

        //set GLSL Uniform
        int uniloc = glGetUniformLocation(shaderProgram.getID(), TRANSFORMATION_MATRIX);
        glUniformMatrix4fv(uniloc, false, transformMat4fv);

        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

}