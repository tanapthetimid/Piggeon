package src;

import java.util.HashSet;

public abstract class Level{
    public abstract void init();

    private HashSet<GameObject> gameObjects = new HashSet<>();
    private HashSet<GameObject> collidableObjects = new HashSet<>();
    private HashSet<GameObject> cubes = new HashSet<>();
    //interface methods
    //modifiers
    public void addGameObject(GameObject go){gameObjects.add(go);}
    public void addCollidableObject(GameObject go){
        collidableObjects.add(go);
        addGameObject(go);
    }
    public void addCube(GameObject go){
        cubes.add(go);
        addGameObject(go);
    }
    public void removeGameObject(GameObject go){gameObjects.remove(go);}
    //accessors
    public HashSet<GameObject> getGameObjects(){return gameObjects;}
    public HashSet<GameObject> getCollidableObjects(){return collidableObjects;}
    public HashSet<GameObject> getCubes(){return cubes;}
}