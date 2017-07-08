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

package piggeon.engine;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundEffect
{

    private String soundFileName;
    private Clip audioClip;

    public SoundEffect(String soundFileName)
    {
        this.soundFileName = soundFileName;
    }

    public void open()
    {
        try
        {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
        }
        catch(UnsupportedAudioFileException e)
        {
            System.err.println("Unsupported Audio Format");
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.err.println("Audio File IO error");
            e.printStackTrace();
        }
        catch(LineUnavailableException e)
        {
            System.err.println("Line Unavailable");
            e.printStackTrace();
        }
    }

    public void play()
    {
        if(audioClip != null)
        {
            audioClip.setFramePosition(0);
            audioClip.loop(0);
        }
    }

    public void playAndClose()
    {
        if(audioClip != null)
        {
            audioClip.addLineListener(new LineListener(){
                public void update(LineEvent event){
                    if(event.getType() == LineEvent.Type.STOP){
                        event.getLine().close();
                        SoundEffect.this.close();
                        audioClip = null;
                    }
                }
            });
            audioClip.setFramePosition(0);
            audioClip.start();
        }
    }

    public void pause()
    {
        if(audioClip != null && audioClip.isRunning())
        {
            audioClip.stop();
        }
    }

    public void reset()
    {
        if(audioClip != null )
        {
            if(audioClip.isRunning())
            {
                audioClip.stop();
            }
            audioClip.setFramePosition(0);
        }
    }

    public void close()
    {
        if(audioClip.isRunning())
            audioClip.stop();
        if(audioClip.isOpen())
            audioClip.close();
    }
}
