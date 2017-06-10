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

package engine;

/**
 * The Camera class is used to update the Node object
 * bounded to camera.
 * Usage: bind a node to camera. Transform camera's node to
 * simulate camera movement.
 */
public class Camera
{
	//the node bounded to this camera
	private Node cameraNode;

	//empty constructor
	public Camera()
	{
		//empty constructor
	}

	//constructor with node
	public Camera(Node cameraNode)
	{
		this.cameraNode = cameraNode;
	}

	/**
	 * update interface called by gameLoop
	 */
	public void update()
	{
		onUpdate(cameraNode);
	}

    /**
     * to be implemented by user. This method should update
     * camera's Node to reflect camera movement
     *
     * @param cameraNode the node bounded to camera
     */
	private void onUpdate(Node cameraNode)
	{
		//empty camera update
	}

    /**
     * binds the Node object to camera
     * @param cameraNode
     */
	public void bindNode(Node cameraNode)
	{
		this.cameraNode = cameraNode;
	}
}