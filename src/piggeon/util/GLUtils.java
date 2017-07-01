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

package piggeon.util;

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
