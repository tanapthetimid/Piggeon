package engine;

/**
 * The Camera class is used to update the Node object
 * bounded to camera.
 * Usage: bind a node to camera. Transform camera's node to
 * simulate camera movement.
 */
public class Camera
{
	//the node bounded to this camera
	private Node cameraNode;

	//empty constructor
	public Camera()
	{
		//empty constructor
	}

	//constructor with node
	public Camera(Node cameraNode)
	{
		this.cameraNode = cameraNode;
	}

	/**
	 * update interface called by gameLoop
	 */
	public void update()
	{
		onUpdate(cameraNode);
	}

    /**
     * to be implemented by user. This method should update
     * camera's Node to reflect camera movement
     *
     * @param cameraNode the node bounded to camera
     */
	private void onUpdate(Node cameraNode)
	{
		//empty camera update
	}

    /**
     * binds the Node object to camera
     * @param cameraNode
     */
	public void bindNode(Node cameraNode)
	{
		this.cameraNode = cameraNode;
	}
}