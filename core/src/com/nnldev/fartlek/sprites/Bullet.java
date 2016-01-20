package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;

/**
 * Nick
 */
public class Bullet {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private String path;
    private Rectangle rectangle;
    private int bulSpeed=5;
    public Bullet(String path,float x,float y){
        this.path = path;
        texture = new Texture(path); //there can be multiple textures for the bullet perhaps
        position = new Vector3(x, y, 0); // position
        velocity = new Vector3(0, bulSpeed, 0); //obsolete for now
        rectangle = new Rectangle(x, y, (Fartlek.WIDTH) / 5.5f, (Fartlek.WIDTH) / 5.5f); //idk about this for now - Nick
    }
    public void update(float dt){
        setY(position.y+velocity.y);
        rectangle.setY(position.y + velocity.y);
        if (position.y + rectangle.getHeight() > Fartlek.HEIGHT) {
            velocity.set(0, 0, 0);
            dispose();
        }
    }
    public void setX(float x){
        position.x = x;
    }
    public float getX(){
        return position.x;
    }
    public void setY(float y){
        position.y=y;
    }
    public float getY(){
        return position.y;
    }
    public void dispose(){
        texture.dispose();
    }
    public void render(SpriteBatch sb){//idk about this - Nick

    }
    //String representation of bullet
    @Override
    public String toString(){
        return "Path: " + path + "\nCoordinates: (" + getX() + "," + getY() + ")" + "\nVelocities: X="
                + velocity.x + "\tY=" + velocity.y + "\tZ=" + velocity.z + "\nWidth: " + rectangle.getWidth()
                + "\tHeight: " + rectangle.getHeight();
    }
}
