package src;

import java.util.HashSet;

/**
 * Stage abstract class.
 * This class represents a Stage that can be
 * initiated and saved by the **TO BE IMPLEMENTED** save system.
 * Stage holds a HashSet of GameObjects inside the Stage.
 */
public abstract class Stage
{
	//stores level's GameObject(s)
	private GameObjectStorage goStorage;

	//stage size
	private int stageWidth;
	private int stageHeight;

	public Stage(int width, int height)
	{
		stageWidth = width;
		stageHeight = height;
	}

	/**
	 * Implemented by every stage. This method is called by GameLoop
	 * to initiate every stage.
	 *
	 * - create objects and add them to stage
	 * - sets the objects to their starting position
	 * (optional) - restore saved state
	 */
    public abstract void init();

    //returns GameObjectStorage of this stage
	public GameObjectStorage getGameObjectStorage()
	{
		return goStorage;
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