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

package piggeon.util;

/**
 * ImageInfo class is used to store
 * information related to an image loaded
 * by the Image class.
 * 
 * Stores
 *    -Opengl: texture id
 *    -Height
 *    -Width
 */
public class ImageInfo
{
    /**
     * OpenGL stores texture ID statically.
     * this variable is used to refer to
     * the ID of the loaded texture stored
     * by OpenGL.
     */
   
    /** Value - {@value}, OpenGL texture ID of image*/
    public int id;
    
    public int width;
    public int height;
    
    public ImageInfo(int id, int width, int height)
    {
        this.id = id;
        this.width = width;
        this.height = height;
    }
}
