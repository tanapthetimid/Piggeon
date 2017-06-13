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

package engine;

import java.util.ArrayList;

public class Node
{
    private float x;
    private float y;
    private float rotation;
    private float scaleX = 1;
    private float scaleY = 1;

    //name used to identify nodes. can be useful when searching for a node
    private String nodeName = "";

    //children node
    private ArrayList<Node> children = new ArrayList<>();

    public Node()
    {
        //empty constructor
    }

    public Node(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getName()
    {
        return nodeName;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getRotationAngle()
    {
        return rotation;
    }

    public void setRotationAngle(float rotation)
    {
        this.rotation = rotation;
    }

    public float getScaleX()
    {
        return scaleX;
    }

    public void setScaleX(float scaleX)
    {
        this.scaleX = scaleX;
    }

    public float getScaleY()
    {
        return scaleY;
    }

    public void setScaleY(float scaleY)
    {
        this.scaleY = scaleY;
    }

    public Node[] getChildrenAsArray()
    {
        return children.toArray(new Node[children.size()]);
    }

    public ArrayList<Node> getChildren()
    {
        return children;
    }

    public void attachChild(Node child)
    {
        children.add(child);
    }
}