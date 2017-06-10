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