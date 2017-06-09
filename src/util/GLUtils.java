package util;
import engine.*;

import java.util.HashMap;

import static org.lwjgl.opengl.GL30.*;
/**
 * GLUtils
 */
public class GLUtils
{
    private static HashMap<String, Integer> vaoCache = new HashMap<>();

    public static int createVertexArrays(GameObject gameObject)
    {
        Integer savedVAO = vaoCache.get(gameObject.getClass().getName());
        if(savedVAO == null)
        {
            int vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);
        }
        return savedVAO;
    }
}
