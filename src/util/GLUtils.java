/*
 * Copyright 2017, Tanapoom Sermchaiwong, All rights reserved.
 *
 * This file is part of Piggeon.
 *
 * Piggeon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Piggeon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Piggeon.  If not, see <http://www.gnu.org/licenses/>.
 */

package util;
import engine.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * GLUtils provides utilities for dealing with openGL
 */
public class GLUtils
{
    //cache vertex array objects of the same GameObjects
    private static HashMap<Integer, Integer> vaoCache = new HashMap<>();

    public static int createVertexArrays(ImageInfo imageInfo)
    {
        Integer savedVAO = vaoCache.get(imageInfo.id);
        if(savedVAO == null)
        {
            int vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);

            float halfW = imageInfo.width/2f;
            float halfH = imageInfo.height/2f;

            //define vertices array
            float[] vertices = new float[]
                    {
                            -halfW,  halfH,
                            -halfW, -halfH,
                             halfW, -halfH,
                            -halfW,  halfH,
                             halfW, -halfH,
                             halfW,  halfH
                    };

            //define texture coordinates
            float[] texCoords = new float[]
                    {
                            0.0f, 0.0f,
                            0.0f, 1.0f,
                            1.0f, 1.0f,
                            0.0f, 0.0f,
                            1.0f, 1.0f,
                            1.0f, 0.0f
                    };

            //bind vertices array to vertex buffer object
            FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
            verticesBuffer.put(vertices).flip();

            int vboVertID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboVertID);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(0);

            //bind texcoords to vertex buffer object
            FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
            texCoordsBuffer.put(texCoords).flip();

            int vboTexCoordsID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboTexCoordsID);
            glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);

            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(1);

            vaoCache.put(imageInfo.id, vaoID);
            return vaoID;
        }
        return savedVAO;
    }
}
