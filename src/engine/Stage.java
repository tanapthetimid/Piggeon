package engine;

import java.util.LinkedList;
/**
 * Stage abstract class.
 * This class represents a Stage that can be
 * initiated and saved by the **TO BE IMPLEMENTED** save system.
 * Stage holds a HashSet of GameObjects inside the Stage.
 */
public abstract class Stage
{
	//stores level's GameObject(s) in n-tree
    //root node of the object's n-tree
    private Node rootNode;

    //list of objects to call update() at every frame
    private LinkedList<GameObject> updateList;

    //Stage's camera
    private Camera camera;

	//stage size
	private int stageWidth;
	private int stageHeight;

	public Stage(int width, int height)
	{
		stageWidth = width;
		stageHeight = height;
		//instantiate var
		rootNode = new Node();
		updateList = new LinkedList<>();
		//calls child initStage method
		camera = initStage(rootNode, updateList);
	}

	/**
	 * Implemented by every stage. Called automatically by parent
     *
     *
	 * Usage: Create game objects and nodes. Add game objects and
     * nodes to scenegraph n-tree. Add objects that need updating
     * to updateList
     *
     * Note: Do not call this method manually.
     *
     * @param rootNode      Root node of scene graph.
     * @param updateList    List of objects that should have it's update() method
     *                      called every frame.
     *
     * @return              Implementation should return a new Camera object
     *                      that has the root node or a child of the root node
     *                      bind to it.
	 */
    public abstract Camera initStage(Node rootNode
                            , LinkedList<GameObject> updateList);

    //return Stage's Camera
    public Camera getCamera()
    {
        return camera;
    }

    //return root node of scene graph
    public Node getRootNode()
    {
        return rootNode;
    }

    //return objects that require updating
    public LinkedList<GameObject> getUpdateList()
    {
        return updateList;
    }

    //return update list as array
    public GameObject[] getUpdateListAsArray()
    {
        return updateList.toArray(new GameObject[updateList.size()]);
    }

	//returns the width of stage
	public int getStageWidth()
	{
		return stageWidth;
	}

	//returns height of stage
	public int getStageHeight()
	{
		return stageHeight;
	}

	//changes stage width
	public void setStageWidth(int width)
	{
		stageWidth = width;
	}

	//changes stage height
	public void setStageHeight(int height)
	{
		stageHeight = height;
	}
}