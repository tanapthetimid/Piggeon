/*
 * Copyright 2017, Tanapoom Sermchaiwong, All rights reserved.
 *
 * This file is part of Piggeon.
 *
 * Piggeon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Piggeon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Piggeon.  If not, see <http://www.gnu.org/licenses/>.
 */

package util;

/**
 * FloatMatrixUtils is used to facilitate matrix manipulations
 */
public class FloatMatrixUtils
{
    /**
     * creates an identy 4 by 4 matrix
     * @return  identity 4x4 matrix
     */
    public static float[] createIdentity4x4()
    {
        return new float[]{1f,0f,0f,0f,
                           0f,1f,0f,0f,
                           0f,0f,1f,0f,
                           0f,0f,0f,1f};
    }

    /**
     * multiplies two square matrices (same width and height)
     * @param mat1          first matrix
     * @param mat2          second matrix
     * @param sidelength    side lengths of both matrix
     * @return              resulting matrix of multiplication
     */
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

    /**
     * creates a new 4 by 4 matrix for the scale provided
     * @param scaleX    horizontal scale
     * @param scaleY    vertical scale
     * @return          scaling matrix
     */
    public static float[] scaleTransformMatrix(float scaleX, float scaleY)
    {
        return new float[]{scaleX,  0f    ,     0f,     0f,
                           0f    ,  scaleY,     0f,     0f,
                           0f    ,  0f    ,     1f,     0f,
                           0f    ,  0f    ,     0f,     1f,};
    }

    /**
     * creates a new 4x4 matrix for the translation provided
     * @param tX    translation in x axis
     * @param tY    translation in y axis
     * @return      translation matrix
     */
    public static float[] translateTransformMatrix(float tX, float tY)
    {
        return new float[]{1f    ,  0f    ,     0f,     tX,
                           0f    ,  1f    ,     0f,     tY,
                           0f    ,  0f    ,     1f,     0f,
                           0f    ,  0f    ,     0f,     1f,};
    }

    /*rounding precision of the rotation matrix since java FLOAT may
     *have rounding error*/
    private static final float ROUNDING_PRECISION = 10000000f;

    /**
     * creates a 4x4 rotation matrix which rotates around Z axis
     * (Z axis rotation is the only one applicable in 2D)
     * @param angleInDegrees    The angle in degrees measure
     * @return                  rotation matrix
     */
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
