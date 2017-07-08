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

package test;

import piggeon.engine.*;
import piggeon.util.*;
import static org.lwjgl.glfw.GLFW.*;


import java.awt.image.BufferedImage;
import java.awt.*;

public class MyGameObject extends GameObject
{
    public MyGameObject(String texturePath)
    {
        super(null);

        int dimen = 50;
        BufferedImage image = new BufferedImage(dimen,dimen, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,dimen,dimen);

        int texture = ImageUtils.loadImageToTexture(ImageUtils.bufferedImageToByteBuffer(image),image.getWidth(), image.getHeight());

        setTexture(new ImageInfo(texture, dimen, dimen));

        dx = 10;
        dy = 10;
    }

    int dx;
    int dy;

    @Override
    public void update(Stage stage)
    {
        if(getX() > 500)
        {
            dx =-(int)( Math.random() * 20) - 1;
        }
        else if(getX() < 0)
        {
            dx =(int)( Math.random() * 20) + 1;
        }

        if(getY() > 500)
        {
            dy =-(int)( Math.random() * 20) - 1;
        }
        else if(getY() < 0)
        {
            dy =(int)( Math.random() * 20) + 1;
        }
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
