package util;

/**
 * FloatMatrixUtils
 */
public class FloatMatrixUtils
{
    public static float[] createIdentity4x4()
    {
        return new float[]{1f,0f,0f,0f,
                           0f,1f,0f,0f,
                           0f,0f,1f,0f,
                           0f,0f,0f,1f};
    }

    public static float[] multiplySquares(float[] mat1, float[] mat2, int sidelength)
    {
        float[] newMat = new float[sidelength*sidelength];
        for(int r = 0; r < sidelength*sidelength; r+=sidelength)
        {
            for(int c = 0; c < sidelength; c++)
            {
                float sum = 0;
                for (int x = 0; x < sidelength; x++)
                {
                    sum += mat1[r + x] * mat2[c + x * sidelength];
                }
                newMat[r + c] = sum;
            }
        }
        return newMat;
    }

    public static float[] scaleTransformMatrix(float scaleX, float scaleY)
    {
        return new float[]{scaleX,  0f    ,     0f,     0f,
                           0f    ,  scaleY,     0f,     0f,
                           0f    ,  0f    ,     1f,     0f,
                           0f    ,  0f    ,     0f,     1f,};
    }

    public static float[] translateTransformMatrix(float tX, float tY)
    {
        return new float[]{1f    ,  0f    ,     0f,     tX,
                           0f    ,  1f    ,     0f,     tY,
                           0f    ,  0f    ,     1f,     0f,
                           0f    ,  0f    ,     0f,     1f,};
    }

    private static final float ROUNDING_PRECISION = 10000000f;

    public static float[] rotateTransformMatrix(float angleInDegrees)
    {
        float sinX = (float) Math.round(Math.sin(angleInDegrees / 180 * Math.PI)*ROUNDING_PRECISION)/ROUNDING_PRECISION;
        float cosX = (float) Math.round(Math.cos(angleInDegrees / 180 * Math.PI)*ROUNDING_PRECISION)/ROUNDING_PRECISION;

        return new float[]{  cosX, -sinX,    0f,    0f,
                             sinX,  cosX,    0f,    0f,
                             0f  ,    0f,    1f,    0f,
                             0f  ,    0f,    0f,    1f,};
    }
}
