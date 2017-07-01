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

public abstract class GameObject extends Node
{
    private int textureID;
    private float width;
    private float height;
    private int vaoID;

    /**
     * initializes game object's texture ID and vertex arrays object ID
     * @param texturePath path to sprite
     */
    public GameObject(String[] texturePath)
    {
        if(texturePath != null && texturePath.length > 0)
        {
            for (String path : texturePath)
            {
                GLUtils.createVertexArrays(ImageUtils.loadImage(path));
            }

            setTexture(texturePath[0]);
        }
    }

    /**
     * returns an arraylist formatted as such: a positive or
     * negative int followed by objects to operate on
     * positive 1 means to add that game object -1 means to remove the game object
     * example {1,object1,object2, -1, object3}
     */
    public abstract void update(Stage stage);
    
    //interface methods

    //modifiers
    public void setRawWidth(float width){this.width = width;}
    public void setRawHeight(float height){this.height = height;}

    /**
     * Set the texture to the texture specified in path.
     * No need to worry about reloading textures, since ImageUtils
     * cache textureID, so there is no performance hit when switching
     * between textures.
     * @param path path of texture file
     */
    public void setTexture(String path)
    {
        ImageInfo info = ImageUtils.loadImage(path);
        textureID = info.id;
        setRawHeight(info.height);
        setRawWidth(info.width);

        vaoID = GLUtils.createVertexArrays(info);
    }

    //accessors
    public float getRawWidth(){return width;}
    public float getRawHeight(){return height;}
    public float getWidth(){return width * getScaleX();}
    public float getHeight(){return height * getScaleY();}
    public int getTextureID(){return textureID;}
    public int getVertexArraysObjectID(){return vaoID;}
}