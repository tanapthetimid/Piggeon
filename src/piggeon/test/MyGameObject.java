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

package piggeon.test;

import piggeon.engine.*;

public class MyGameObject extends GameObject
{
    public MyGameObject(String texturePath)
    {
        super(new String[]{texturePath});
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
