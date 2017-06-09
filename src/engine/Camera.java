package engine;

public class Camera
{
	private float x;
	private float y;
	private float size;
	private Node cameraNode;

	public void update()
	{
		//default camera has empty update method
	}

	public Camera(Node cameraNode)
	{
		this.cameraNode = cameraNode;
	}

	public Camera(float x, float y, float size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getSize()
	{
		return size;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public void setSize(float size)
	{
		this.size = size;
	}

	public Node getNode()
	{
		return cameraNode;
	}

	public void bindNode(Node cameraNode)
	{
		this.cameraNode = cameraNode;
	}
}