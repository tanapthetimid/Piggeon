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

public class ExampleMovingBoxObject extends GameObject
{
    public void onLoad()
    {
        int dimen = 50;
        BufferedImage image = new BufferedImage(dimen,dimen, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,dimen,dimen);

        int texture = ImageUtils.loadImageToTexture(ImageUtils.bufferedImageToByteBuffer(image),image.getWidth(), image.getHeight());

        //setTexture(new ImageInfo(texture, dimen, dimen));

        dx = 10;
        dy = 10;

        delayb = 10;

        Animator animator = new Animator("box_color");
        animator.setLooping(true);
        animator.addSpriteToMap("testres/frame1.png", 30);
        animator.addSpriteToMap("testres/frame2.png", 30);
        animator.addSpriteToMap("testres/frame3.png", 30);
        animator.addSpriteToMap("testres/frame4.png", 30);
        setAnimator(animator);

    }

    int dx;
    int dy;

    int delayb = 10;

    int delayx = 10;

    long a = 0;

    @Override
    public void onUpdate(Stage stage)
    {
        if(delayb > 0) delayb--;
        if(InputHandler.isKeyDown(GLFW_KEY_ENTER) && delayb <= 0)
        {
            delayb = 10;
            GameLoop.stopLoop();
        }
        if(delayx > 0) delayx--;

        if(InputHandler.isKeyDown(GLFW_KEY_S) && delayx <=0)
        {
            delayx = 10;
            delayb = 10;
            System.out.println("----------------------save");
            ExampleMain.load.saveStage(2,stage);
        }

        a++;
        System.out.println(a);

        if(getX() > 500)
        {
            dx =-(int)( Math.random() * 5) - 1;
        }
        else if(getX() < 0)
        {
            dx =(int)( Math.random() * 5) + 1;
        }

        if(getY() > 500)
        {
            dy =-(int)( Math.random() * 5) - 1;
        }
        else if(getY() < 0)
        {
            dy =(int)( Math.random() * 5) + 1;
        }
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
