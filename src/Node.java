

import java.util.ArrayList;

public class Node
{
    private float translationX;
    private float translationY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private Node parent;

    private ArrayList<Node> children;

    private Node(Node parent)
    {
        this.parent = parent;
    }

    public float getTranslationX()
    {
        return translationX;
    }

    public void setTranslationX(float translationX)
    {
        this.translationX = translationX;
    }

    public float getTranslationY()
    {
        return translationY;
    }

    public void setTranslationY(float translationY)
    {
        this.translationY = translationY;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
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

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public Node[] getChildren()
    {
        return children.toArray(new Node[children.size()]);
    }

    public void addChild(Node child)
    {
        children.add(child);
    }
}