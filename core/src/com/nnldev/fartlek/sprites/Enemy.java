package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
//created by Nick, 15/01/2016
public class Enemy extends Obstacle{
  //variables
  private int health;
  private boolean dead = false;
  //constructor
  public Enemy(String path,float x,float y,int health){
    super(path,x,y);
    this.health = health
  }
  @Override
  public void update(float dt) {
    setYPosition(super.position.y + super.velocity.y);
    super.rectangle.setY(super.position.y + super.velocity.y);
    if (super.position.y + rectangle.getHeight() < 0) {
      super.velocity.set(0, 0, 0);
      dispose();
    }
  }
  //changes texture and changes state to dead
  public void die(){
    //change texture to the dead version
    String newPath = path.substring(0,path.length()-3);
    newPath = newPath+"Dead.png";
    super.texture = new Texture(newPath);
    dead = true;
  }
  //returns dead state
  public boolean getDead(){
    return dead;
  }
/**
 * Gets the health of the enemy
 * @return The health of the enemy
 */
  public int getHealth() {
    return health;
  }
  /**
 * Sets the health of the enemy
 * @param health
 */
  public void setHealth(int health) {
    this.health = health;
  }
  /**
 * @param texture The texture which the obstacle will be set to
 */
  @Override
  public void setTexture(Texture texture) {
    super.texture = texture;
  }
   /**
   * Gets the position of the enemy
   * @return the position of the enemy
   */
  @Override
  public Vector3 getPosition() {
      return super.position;
  }
  /**
  * Sets the position of the enemy
  * @param position The position you would like to set the
  *                 obstacle's position to.
  */
  Override
  public void setPosition(Vector3 position) {
    super.position = position;
  }
  /**
 * Sets the x coordinate of the enemy
 * @param x The x coordinate of the obstacle's position
 */
  @Override
  public void setXPosition(float x) {
    super.position.x = x;
  }
  /**
  * Returns the x position of the obstacle
  * @return the x position of the obstacle
  */
  public float getXPosition() {
    return position.x;
  }
  /**
  * Sets the y position of the enemy
  * @param y The y coordinate of the obstacle's position
  */
  @Override
  public void setYPosition(float y) {
    super.position.y = y;
  }
  /**
 * Gets the y position of the enemy
 * @return The y positionf of the enemy
 */
  @Override
  public float getYPosition() {
    return super.position.y;
  }
  /**
 * Gets the velocity of the enemy
 * @return
 */
  @Override
  public Vector3 getVelocity() {
    return super.getVelocity();
  }
  /**
 * Sets the velocities of the obstacle
 * @param velocity The velocities of theo obstacle
 */
  @Override
  public void setVelocity(Vector3 velocity) {
    super.setVelocity(velocity);
  }
  /**
  * Gets the path of the obstacle
  * @return
  */
  @Override
  public String getPath() {
    return super.getPath();
  }
  /**
   * @param texturePath
   * @deprecated There is no situation where this should be used
   */
  @Override
  public void setPath(String texturePath) {
    super.setPath(texturePath);
  }
  /**
 * Gets the texture of the enemy
 * @return
 */
  @Override
  public Texture getTexture() {
    return texture;
  }
  /**
 * @return
 */
  @Override
  public Rectangle getRectangle() {
    return super.rectangle;
  }
  /**
  * @param rectangle The rectangle for whome the bounds of this obstacle shall be
  *                  set to
  */
  @Override
  public void setRectangle(Rectangle rectangle) {
    this.rectangle = rectangle;
  }
  /**
   * Disposes of the enemy's texture
   */
  @Override
  public void dispose() {
    super.dispose();
  }
  /**
  * Checks if two enemies are equal or not
  *
  * @param obstacle something
  * @return
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
 * Returns a string representation of the enemy
 * @return The string representation of the enemy
 */
  @Override
  public String toString() {
    return "Path: " + path + "\nCoordinates: (" + getXPosition() + "," + getYPosition() + ")" + "\nVelocities: X="
      + velocity.x + "\tY=" + velocity.y + "\tZ=" + velocity.z + "\nWidth: " + rectangle.getWidth()
      + "\tHeight: " + rectangle.getHeight() + "\nHealth: " + health;
  }
}
