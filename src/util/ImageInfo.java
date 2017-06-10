package util;

/**
 * ImageInfo class is used to store
 * information related to an image loaded
 * by the Image class.
 * 
 * Stores
 *    -Opengl: texture id
 *    -Height
 *    -Width
 */
public class ImageInfo
{
    /**
     * OpenGL stores texture ID statically.
     * this variable is used to refer to
     * the ID of the loaded texture stored
     * by OpenGL.
     */
   
    /** Value - {@value}, OpenGL texture ID of image*/
    public int id;
    
    public int width;
    public int height;
    
    public ImageInfo(int id, int width, int height)
    {
        this.id = id;
        this.width = width;
        this.height = height;
    }
}
