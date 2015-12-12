/**
 * @authos Nano
 * A class to allow for easy creation of buttons that allows for easy handling of user input.
 */
package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Button {
    private Vector3 position;
    private Texture texture;
    private Rectangle rectangle;

    /**
     * Draws a button, allowing for the decision to draw the button centred or not textured.
     *
     * @param path   The path of the image to fetch the image from.
     * @param x      The X coordinate of the image to be drawn.
     * @param y      The Y coordinate of the image to be drawn.
     * @param centre Whether the image will be centred onto the point or not.
     */
    public Button(String path, float x, float y, boolean centre) {
        if (centre) {
            texture = new Texture(path);
            position = new Vector3(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), 0);
        } else {
            texture = new Texture(path);
            position = new Vector3(x, y, 0);
        }
        rectangle = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void dispose() {
        texture.dispose();
    }
}
