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
import piggeon.util.GameLoopUninitializedException;

/**
 * ExampleMain test class
 */
public class ExampleMain
{
    public static void main(String[] args)
    {
        GameLoop.init("test", null
                , 700,500
                ,50,50,0, true);

        SaveState load = SaveState.loadFromFile("save1.sv");


        Stage stage = new ExampleStage();

        if(load != null && !load.isEmpty())
        {
            stage = load.getStage(1);
            stage.reload();
        }

        if(load == null || load.isEmpty())
        {
            try
            {
                stage.init();
            } catch (GameLoopUninitializedException ex)
            {
                ex.printStackTrace();
            }
        }

        GameLoop.startStage(stage);

        System.out.println("end");

        ExampleStageTwo stage2 = new ExampleStageTwo();

        try {
            stage2.init();
        }catch(GameLoopUninitializedException ex)
        {
            ex.printStackTrace();
        }

        System.out.println("start2");

        GameLoop.startStage(stage2);

        SaveState saveState = new SaveState();
        saveState.addStage(1, stage);
        saveState.saveToFile("save1.sv");

        GameLoop.destroyGameLoop();
    }
}
