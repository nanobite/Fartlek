package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;

/**
 * Created by Nano on 11/12/2015.
 */
public class Runner {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle rectangle;
    private float horizontalSpeed;
    private float horizontalDeceleration = 0.5f;

    public Runner(String path) {
        texture = new Texture(path);
        position = new Vector3((Fartlek.WIDTH / 2) - (texture.getWidth() / 2), 160, 0);
        rectangle = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        velocity = new Vector3();
        horizontalSpeed = 8;
    }

    public void update() {
        if(velocity.x>0){
            velocity.x-=horizontalDeceleration;
        }else if(velocity.x<0){
            velocity.x+=horizontalDeceleration;
        }else{
            velocity.x=0;
        }

        position.x += velocity.x;
        position.y += velocity.y;
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x + texture.getWidth() > Fartlek.WIDTH) {
            position.x = Fartlek.WIDTH - texture.getWidth();
        }
    }

    /**
     * Moves the character left
     */
    public void left() {
        velocity.x = -horizontalSpeed;
    }

    /**
     * Moves the character right
     */
    public void right() {
        velocity.x = horizontalSpeed;
    }

    /**
     * Returns the rectangle hitbox of the character
     *
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Sets the rectangle hitbox of the character
     *
     * @param rectangle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Gets the position of the character
     *
     * @return
     */
    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void dispose() {
        texture.dispose();
    }
    public void changePic(String path){
        texture = new Texture(path);
    }
}
