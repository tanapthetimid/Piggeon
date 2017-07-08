/* 
 * Copyright (c) 2017, Tanapoom Sermchaiwong
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name 'Piggeon' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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