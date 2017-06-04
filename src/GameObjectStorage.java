package src;

import java.util.HashSet;

/**
 * This class is used to store and arrange GameObjects by their
 * respective children class. This is useful in many cases, for example
 * when checking collision with members of a certain class.
 */
public class GameObjectStorage
{
	//number of objects in storage
	private int size;

	/*stores the sets of objects, arranged by the String representation
	  a string representation of the class name of each object.*/
	private HashMap<String, HashSet<GameObject>> storage = new HashSet<>();

	/**
	 * Adds GameObject to the storage. Creates a new Set for the
	 * class if it doesn't already exist
	 */
	public void addGameObject(GameObject gameObject)
	{
		String className = gameObject.getClass().getName();
		HashSet objSet = storage.get(className);

		/*if the HashSet of this particular class doesn't
		  exist yet, create a HashSet and put it in the Map*/
		if(objSet == null)
		{
			objSet = new HashSet<GameObject>;
			storage.put(className, objSet);
		}
		//adds gameObject to the HashSet
		objSet.add(gameObject);
		size++;
	}

	/**
	 * Removes the GameObject from the storage.
	 */
	public void removeGameObject(GameObject gameObject)
	{
		String className = gameObject.getClass().getName();
		HashSet objSet = storage.get(className);
		if(objSet != null)
		{
			objSet.remove(gameObject);

			/*if there is no more objects left in the set
			  for this class, remove it from the Map.*/
			if(objSet.isEmpty())
			{
				storage.remove(className);
			}
		}
	}

	/**
	 * Returns all objects of the class specified by variable className.
	 * If there is no Object of specified class, return null;
	 */
	public GameObject[] getGameObjectsByClassName(String className)
	{
		HashSet objSet = storage.get(className);
		if(objSet != null)
		{
			GameObject[] gos = new GameObject[objSet.size()];
			int x = 0;
			for(GameObject g: objSet)
			{
				gos[x] = g;
				x++;
			}
			return gos;
		}
		return null;
	}

	/**
	 * Returns all gameObjects.
	 */
	public GameObject[] getGameObjects()
	{
		GameObject[] gameObjects = new GameObject[size];
		int x = 0;
		for(HashSet set: storage.values())
		{
			for(GameObject go: set){
				gameObjects[x] = go;
				x++;
			}
		}

		return gameObjects;
	}
}