//Lazar and Nano
//Dec 12 2015
//Concrete class used to create obstacles from obstacle abstract class

package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;

public class Box extends Obstacle {
	//instance vars
	private int health;
	//class vars
	public static final float BOX_WIDTH = (Fartlek.WIDTH) / 5.5f;

	/**
	 * Creates a box
	 * @param path the path to sprite image
	 * @param x the x position
	 * @param y the y position
	 * @param health the health
	 */
	public Box(String path, float x, float y, int health) {
		super(path, x, y, BOX_WIDTH, BOX_WIDTH);
		this.health = health;
	}

	/**
	 * Updates the box
	 * @param dt The time which has passed since the last update
	 */
	@Override
	public void update(float dt) {
		//update its y position and its rectangles y position
		setYPosition(super.position.y+super.velocity.y);
		super.rectangle.setY(super.position.y+super.velocity.y);
		if (super.position.y + rectangle.getHeight() < 0) {
			//stop it and destroy it if it goes below the screen
			super.velocity.set(0, 0, 0);
			dispose();
		}
	}

	/**
	 * Gets the position of the box
	 *
	 * @return
	 */
	@Override
	public Vector3 getPosition() {
		return super.position;
	}

	/**
	 * Sets the position of the box
	 *
	 * @param position
	 *            The Vector3 of the position you would like to set the
	 *            obstacle's position to.
	 */
	@Override
	public void setPosition(Vector3 position) { super.position = position; }

	/**
	 * Sets x position
	 * @param x The x position being set
	 */
	@Override
	public void setXPosition(float x) {
		super.position.x = x;
	}

	/**
	 * gets x position
	 * @return the x position
	 */
	public float getXPosition() {
		return position.x;
	}

	/**
	 * Sets y position
	 * @param y The x position being used
	 */
	@Override
	public void setYPosition(float y) {
		super.position.y = y;

	}

	/**
	 * gets y position
	 * @return the x position
	 */
	public float getYPosition() {
		return super.position.y;
	}

	/**
	 * gets the velocity
	 * @return the velocity
	 */
	@Override
	public Vector3 getVelocity() {
		return super.getVelocity();
	}

	/**
	 * sets the velocity
	 * @return the velocity being used
	 */
	@Override
	public void setVelocity(Vector3 velocity) {
		super.setVelocity(velocity);
	}

	/**
	 * gets the path to the sprite image
	 * @return the path
	 */
	@Override
	public String getPath() {
		return super.getPath();
	}

	/**
	 * sets the path
	 * @param texturePath the path being used
	 */
	@Override
	public void setPath(String texturePath) {
		super.setPath(texturePath);
	}

	/**
	 * Gets the texture of the box
	 *
	 * @return
	 */
	@Override
	public Texture getTexture() {
		return texture;
	}

	/**
	 * @param texture
	 *            The texture which the obstacle will be set to
	 */
	@Override
	public void setTexture(Texture texture) {
		super.texture = texture;
	}

	/**
	 * @return
	 */
	@Override
	public Rectangle getRectangle() {
		return super.rectangle;
	}

	/**
	 * @param rectangle
	 *            The rectangle for whome the bounds of this obstacle shall be
	 *            set to
	 */
	@Override
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	/**
	 * Gets the health of the box
	 *
	 * @return The health of the box
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the health of the box
	 *
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Disposes of the box's texture
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * checks if an box is equal to another
	 * @param obstacle the obstacle being compared
	 * @return true or false
	 */
	@Override
	public boolean equals(Obstacle obstacle) {
		if ((obstacle.path.equals(this.path))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sends all info about the box as a string
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Path: " + path + "\nCoordinates: (" + getXPosition() + "," + getYPosition() + ")" + "\nVelocities: X="
				+ velocity.x + "\tY=" + velocity.y + "\tZ=" + velocity.z + "\nWidth: " + rectangle.getWidth()
				+ "\tHeight: " + rectangle.getHeight() + "\nHealth: " + health;
	}

}
