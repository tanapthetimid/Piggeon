package src;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
public class CollisionDetection{
    public static final int NONE = 0;
    public static final int BOTTOM = 1;
    public static final int TOP = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final int TOP_LEFT = 5;
    public static final int TOP_RIGHT = 6;
    public static final int BOTTOM_LEFT = 7;
    public static final int BOTTOM_RIGHT = 8;

    public static final int CORNER_LINIENCY = 5;
    
    //returns the collided side with respect to the first argument
    public static int checkCollision(GameObject obj1, GameObject obj2){
        return checkCollisionVelocityAware(obj1,0,0,obj2,0,0);
    }
    
    public static boolean checkCollisionSimple(GameObject obj1, GameObject obj2){
        double xOverlap 
        = oneDimensionalOverlap(obj1.getX(), obj1.getWidth()
            , obj2.getX(), obj2.getWidth());
        double yOverlap
        = oneDimensionalOverlap(obj1.getY(), obj1.getHeight()
            , obj2.getY(), obj2.getHeight());
            
        return xOverlap > 0 && yOverlap > 0;
    }
    
    

    public static int checkCollisionExpensiveVelocityAware(GameObject obj1, double velocityX1, double velocityY1
    , GameObject obj2, double velocityX2, double velocityY2){
        //expensive
        double magnitude = Math.sqrt(velocityX1*velocityX1 + velocityY1 * velocityY1);
        double unitX = velocityX1 / magnitude;
        double unitY = velocityY1 / magnitude;
        double currentX = unitX;
        double currentY = unitY;
        int collision = NONE;
        while(Math.abs(currentX) < Math.abs(velocityX1) || Math.abs(currentY) < Math.abs(velocityY1)){
            collision = checkCollisionVelocityAware(obj1, currentX, currentY, obj2, 0,0);
            currentX += unitX;
            currentY += unitY;
            if(collision != NONE){
                return collision;
            }
        }
        return collision;
    }

    public static int checkCollisionVelocityAware(GameObject obj1, double velocityX1, double velocityY1
    , GameObject obj2, double velocityX2, double velocityY2){
        return (checkCollisionSurfaceAreaAware(obj1, velocityX1, velocityY1, obj2, velocityX2, velocityY2))[0];
    }
    
    public static ArrayList<Integer> objectCollisionSurfaceAreaAwareHelper
                    (GameObject obj1, float velocityX1, float velocityY1
                    , float velocityX2, float velocityY2, HashSet<GameObject> gameObjects){
        TreeMap<Integer, Integer> collisions = new TreeMap<>();

        for(GameObject objOther: gameObjects){
            int[] result = CollisionDetection.checkCollisionSurfaceAreaAware(obj1,velocityX1,velocityY1, objOther,0,0);
            if(result[0] != CollisionDetection.NONE){
                collisions.put(result[1], result[0]);
            }
        }

        ArrayList<Integer> collisionDir = new ArrayList<>();

        Set<Integer> keyset = (Set<Integer>) collisions.descendingKeySet();

        int x = 0;
        for(int key: keyset){
            int dir = collisions.get(key);
            if(dir == CollisionDetection.TOP || dir == CollisionDetection.BOTTOM){
                if(key >= obj1.getWidth()/3){
                    collisionDir.add(dir);
                }
            }else if(dir == CollisionDetection.LEFT || dir == CollisionDetection.RIGHT){
                if(key >= obj1.getHeight()/3){
                    collisionDir.add(dir);
                }
            }
            x++;
        }

        if(collisionDir.isEmpty()){
            collisionDir.addAll(collisions.values());
        }
        
        return collisionDir;
    }

    public static int[] checkCollisionSurfaceAreaAware(GameObject obj1, double velocityX1, double velocityY1
    , GameObject obj2, double velocityX2, double velocityY2){
        int[] values = new int[2];
        //get overlap
        double xOverlap 
        = oneDimensionalOverlap(obj1.getX()+velocityX1, obj1.getWidth()
            , obj2.getX()+velocityX2, obj2.getWidth());
        double yOverlap
        = oneDimensionalOverlap(obj1.getY()+velocityY1, obj1.getHeight()
            , obj2.getY()+velocityY2, obj2.getHeight());
        values[1] = (int) (Math.max(yOverlap, xOverlap) + 0.5);

        if(xOverlap > 0 && yOverlap > 0){
            //overlap ratio determines if the collision is horizontal or vertical
            if(xOverlap - yOverlap > CORNER_LINIENCY){//vertical collision
                if(obj1.getY()+velocityY1 > obj2.getY()+velocityY2){
                    values[0] = TOP;
                }else{
                    values[0] = BOTTOM;
                }
            }else if(yOverlap - xOverlap > CORNER_LINIENCY){//horizontal collision
                if(obj1.getX()+velocityX1 > obj2.getX()+velocityX2){
                    values[0] = LEFT;
                }else{
                    values[0] = RIGHT;
                }
            }else{//
                if(obj1.getX()+velocityX1 > obj2.getX()+velocityX2 
                && obj1.getY()+velocityY1 > obj2.getY()+velocityY2){
                    values[0] = TOP_LEFT;
                }else if(obj1.getX()+velocityX1 < obj2.getX()+velocityX2 
                && obj1.getY()+velocityY1 > obj2.getY()+velocityY2){
                    values[0] = TOP_RIGHT;
                }else if(obj1.getX()+velocityX1 > obj2.getX()+velocityX2 
                && obj1.getY()+velocityY1 < obj2.getY()+velocityY2){
                    values[0] = BOTTOM_LEFT;
                }else{
                    values[0] = BOTTOM_RIGHT;
                }
            }
        }else{
            values[0] = NONE;
        }

        return values;
    }

    private static double oneDimensionalOverlap(double point1, double size1, double point2, double size2){
        if(point1 < point2){
            double overlap = size1+point1 - point2;
            return (overlap >= 0 ? overlap : 0);
        }
        else if(point2 < point1){
            double overlap = size2+point2 - point1;
            return (overlap >= 0 ? overlap : 0);
        }else{
            if(size2 > size1){
                return size1+point1 - point2;
            }else{
                return size2+point2 - point1;
            }
        }
    }
}