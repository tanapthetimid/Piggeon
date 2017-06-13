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

package test;

import engine.*;

import java.util.LinkedList;

public class MyStage extends Stage
{
    public Camera initStage(Node rootNode, LinkedList<GameObject> updateList)
    {
        for(int x = 0; x < 100; x++)
        {
            GameObject gogo = new MyGameObject("test.png");
            gogo.setScaleX(0.1f);
            gogo.setScaleY(0.1f);
            gogo.setX(gogo.getWidth() / 2);
            gogo.setY(gogo.getHeight() / 2);

            updateList.add(gogo);
            rootNode.attachChild(gogo);
        }
        return new Camera(rootNode);
    }
}
