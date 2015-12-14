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

    /**
     * Creates a new floortile with a given X and Y position
     *
     * @param path The path of the image for the tile
     * @param x    The x position of the tile
     * @param y    The y position of the tile
     */
    public FloorTile(String path, float x, float y) {
        velocity = new Vector3(0, -4, 0);
        texture = new Texture(path);
        position = new Vector3(x, y, 0);
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Returns the texture for the tile
     *
     * @return The tile picture to draw
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets the texture to something
     *
     * @param texture The tile picture
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Gets the position of the tile
     *
     * @return The position of the current tile
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the tile
     *
     * @param position The new position for this tile
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Gets the rectangle hitbox of the tile if you need that for some reason
     *
     * @return The rectangle which works at the tile's hitbox
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Sets the rectangle of the tile if you want to do that for some od reason
     *
     * @param rectangle The
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Updates the tile's position
     */
    public void update() {
        position.x += velocity.x;
        position.y += velocity.y;
        if (position.y + rectangle.getHeight() < 0) {
            dispose();
        }
    }

    /**
     * Disposes of the tile's texture
     */
    public void dispose() {
        texture.dispose();
    }
}
