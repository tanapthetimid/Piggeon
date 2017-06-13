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

package piggeon.engine;

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

	public Stage()
	{
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
}