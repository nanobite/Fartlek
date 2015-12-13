package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;

/**
 * Created by Nano on 12/12/2015.
 */
public class FloorTile {
    public static final int TILES_PER_ROW = 8;
    private Texture texture;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle rectangle;

    public FloorTile(String path, float x, float y) {
        velocity = new Vector3(0, -4, 0);
        texture = new Texture(path);
        position = new Vector3(x, y, 0);
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void update() {
        position.x += velocity.x;
        position.y += velocity.y;
        if (position.y + rectangle.getHeight() < 0) {
            dispose();
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
