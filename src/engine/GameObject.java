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

package engine;

import util.*;

import java.util.ArrayList;
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