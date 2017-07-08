/*
 * Copyright (c) 2017, Tanapoom Sermchaiwong
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * - Neither the name Piggeon nor the names of its contributors may be
 *   used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package piggeon.util;

import javax.swing.*;
import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
public class TextUtils
{
    public enum Orientation
    {
        HORIZONTAL, VERTICAL;
    }

    public static ImageInfo generateTextBoxTexture(String text, int width, int height, boolean wordWrap, int fontSize, java.awt.Color color)
    {
        return generateTextBoxTexture(text, width, height, wordWrap, color, new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
    }

    public static ImageInfo generateTextBoxTexture(String text, int width, int height, boolean wordWrap, int fontSize)
    {
        return generateTextBoxTexture(text, width, height, wordWrap, Color.WHITE, new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
    }

    public static ImageInfo generateTextBoxTexture(String text, int width, int height, boolean wordWrap, java.awt.Color color , java.awt.Font font)
    {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawRect(0,0, width-1,height-1);

        LineMetrics lineMetrics = font.getLineMetrics(text, g2d.getFontRenderContext());

        float lineHeight = lineMetrics.getHeight();

        FontMetrics fontMetrics = g2d.getFontMetrics(font);

        int lineNum = 1;
        while(!text.isEmpty())
        {
            int index = 0;
            while(index < text.length() && fontMetrics.stringWidth(text.substring(0,index)) < (width - fontMetrics.charWidth('W'))) // 'W' has one of the largest char width
            {
                ++index;
            }

            int unwrappedIndex = index;

            if(wordWrap && index < text.length())
            {
                while(index > 0 && text.charAt(index-1) != ' ')
                {
                    --index;
                }

                if(index == 0)
                {
                    index = unwrappedIndex;
                }
            }
            String line = text.substring(0, index);
            text = text.substring(index);

            g2d.drawString(line, 0, lineHeight*lineNum);
            ++lineNum;
        }

        ByteBuffer bb = ImageUtils.bufferedImageToByteBuffer(bufferedImage);
        int texture = ImageUtils.loadImageToTexture(bb, bufferedImage.getWidth(), bufferedImage.getHeight());

        return new ImageInfo(texture, width, height);
    }

    public static ImageInfo simpleGenerateTextTexture(String text, int width, int height, java.awt.Color color, java.awt.Font font)
    {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text,0,10);

        ByteBuffer bb = ImageUtils.bufferedImageToByteBuffer(bufferedImage);
        int texture = ImageUtils.loadImageToTexture(bb, bufferedImage.getWidth(), bufferedImage.getHeight());

        return new ImageInfo(texture, width, height);
    }
}
