package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Obstacle {
	private static Texture texture;
	private static int x;
	private static int y;
	private Rectangle rectangle;
	private static String path; //name of obstacle image, used to compare and make sure there aren't duplicate obstacles
	private static boolean empty; //tells whether or not the obstacle is empty
	public static int obstacleSpeed = -2;
	
	/* constructor
	path, name of texture
	x, x position
	y, pretty much just to animate it
	*/
<<<<<<< HEAD
	public Obstacle(String pathh, int xx, int yy,boolean e) {//i had to use weird double letters to avoid errors with this.
		path = pathh;
=======
	protected Obstacle(String path, int x, int y,boolean e) {
		this.path = path;
>>>>>>> 58d5ed07ed5e9c9e87b699ec0e02c2b5d586d0dc
		texture = new Texture(path); //there can be multiple textures for obstacles
		x=xx;
		y=yy;
		rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
		empty = e;
	}
	//returns status of box, "empty" or not
	public boolean emptyStatus(){
		return empty;
	}
	//sets status of box
	public void setEmpty(boolean e){
		empty = e;
	}
	//set texture of box
	public void setTexture(String p){
		path = p;
		texture = new Texture(p);
	}
	public void setY(int yy){
		y = yy;
	}
	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	public void setX(int xx){
		x=xx;
	}
	/*public static String toString(){
		return "X Value: "+ x +"Texture: "+ path;
	}*/
	public Texture getTexture(){
		return texture;
	}
}
