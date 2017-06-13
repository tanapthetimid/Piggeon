/*
 * Copyright 2017, Tanapoom Sermchaiwong, All rights reserved.
 *
 * This file is part of Piggeon.
 *
 * Piggeon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Piggeon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Piggeon.  If not, see <http://www.gnu.org/licenses/>.
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