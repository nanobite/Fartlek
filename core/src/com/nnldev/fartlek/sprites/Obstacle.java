package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class Obstacle {
    protected Vector3 position;
    protected Texture texture;
    protected Rectangle rectangle;

    public Obstacle(String path, int x, int y) {
        texture = new Texture(path);
        position = new Vector3(x, y, 0);
        rectangle = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public abstract Vector3 getPosition();

    public abstract void setPosition(Vector3 position);

    public abstract Texture getTexture();

    public abstract void setTexture(Texture texture);

    public abstract Rectangle getRectangle();

    public abstract void setRectangle(Rectangle rectangle);
}
