package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Obstacle {
	private Texture texture;
	private int x;
	private int y;
	private Rectangle rectangle;
	private String path; //name of obstacle image, used to compare and make sure there aren't duplicate obstacles
	private boolean empty; //tells whether or not the obstacle is empty
	public static int obstacleSpeed = -2;
	
	/* constructor
	path, name of texture
	x, x position
	y, pretty much just to animate it
	*/
	protected Obstacle(String path, int x, int y,boolean e) {
		this.path = path;
		texture = new Texture(path); //there can be multiple textures for obstacles
		this.x=x;
		this.y=y;
		rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
		empty = e;
	}
	//returns status of box, "empty" or not
	public static boolean emptyStatus(){
		return empty;
	}
	//sets status of box
	public static void setEmpty(boolean e){
		empty = e;
	}
	//set texture of box
	public static void setTexture(String p){
		path = p;
		texture = new Texture(p);
	}
	public static void setY(int y){
		this.y = y;
	}
	public static int getY(){
		return y;
	}
	public static String toString(){
		return "X Value: "+ x +"Texture: "+ path;
	}
	public static Texture getTexture(){
		return texture;
	}
}
