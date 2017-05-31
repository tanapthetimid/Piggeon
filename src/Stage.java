package src;

import java.util.HashSet;

/**
* Stage abstract class.
* This class represents a Stage that can be
* initiated and saved by the **TO BE IMPLEMENTED** save system.
* Stage holds a HashSet of GameObjects inside the Stage.
*/
public abstract class Stage{

	/**
	* Implemented by every stage. This method is called by GameLoop
	* to initiate every stage.
	*
	* - create objects and add them to stage
	* - sets the objects to their starting position
	* (optional) - restore saved state
	*/
    public abstract void init();

	//HashSet storing EVERY game object on stage
    private HashSet<GameObject> gameObjects = new HashSet<>();
    
    //Stores only collidable objects for processing collisions
    private HashSet<GameObject> collidableObjects = new HashSet<>();
        
    /*interface methods*/
    //modifiers
    public void addGameObject(GameObject go){gameObjects.add(go);}
    
    //adds the object to collidable Hashset AND gameObjects hashset
    public void addCollidableObject(GameObject go){
        collidableObjects.add(go);
        addGameObject(go);
    }
    
    /**
    * Removes gameobject. SHOULD NOT BE CALLED BY GAMEOBJECT
    */
     
    //accessors
    public HashSet<GameObject> getGameObjects(){return gameObjects;}
    public HashSet<GameObject> getCollidableObjects(){return collidableObjects;}
}