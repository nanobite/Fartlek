package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Animation;

/**
 * Created by Nano on 20/12/2015.
 */
public class Bullet {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private String path;
    private Rectangle rectangle;
    private float verticalSpeed;

    public Bullet() {
        position = new Vector3(0, 160, 0);
        verticalSpeed = 8;
    }

    public Bullet(String path, float x) {
        this();
        this.path = path;
        texture = new Texture(path);
        setXPosition(x);
        velocity = new Vector3(0, verticalSpeed, 0);
        rectangle = new Rectangle(x, getYPosition(), texture.getWidth() / 6, texture.getHeight() / 6);
    }

    public void update(float dt) {
        setYPosition(position.y + velocity.y);
        rectangle.setY(position.y + velocity.y);
        if (position.y > Fartlek.HEIGHT) {
            velocity.set(0, 0, 0);
            dispose();
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, getXPosition(), getYPosition(), texture.getWidth() / 6, texture.getHeight() / 6);
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setXPosition(float x) {
        position.x = x;
    }

    public float getXPosition() {
        return position.x;
    }

    public void setYPosition(float y) {
        position.y = y;

    }

    public float getYPosition() {
        return position.y;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String texturePath) {
        path = texturePath;
    }

    public void dispose() {
        texture.dispose();
    }
}
