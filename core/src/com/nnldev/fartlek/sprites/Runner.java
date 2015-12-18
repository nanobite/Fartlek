package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Animation;

/**
 * Created by Nano on 11/12/2015.
 */
public class Runner {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle rectangle;
    private Animation playerAnimation;
    private int health;
    private float horizontalSpeed;
    private float horizontalDeceleration = 0.5f;
    private final int RUNNER_Y = 160;
    private int animFrames = 4;
    private final float ANIM_CYCLE_TIME = 0.25f;

    /**
     * Makes a new Runner who has a picture and stuff
     *
     * @param path The path for the image
     */
    public Runner(String path) {
        texture = new Texture(path);
        velocity = new Vector3(0, 0, 0);
        position = new Vector3(((Fartlek.WIDTH / 2) - (texture.getWidth() / animFrames)), RUNNER_Y, 0);
        rectangle = new Rectangle(position.x, position.y, texture.getWidth() / animFrames, texture.getHeight());

        horizontalSpeed = 8;

    }

    /**
     * Makes a new runner
     *
     * @param path       The path for the runner's pic
     * @param animFrames The number of frames in the picture t oallow for animation of the runner
     */
    public Runner(String path, int animFrames) {
        this(path);
        this.animFrames = animFrames;
        playerAnimation = new Animation(new TextureRegion(texture), animFrames, ANIM_CYCLE_TIME);
    }

    /**
     * Updates the runner
     *
     * @param dt The change in time since the last time the runner's position was updated
     */
    public void update(float dt) {
        playerAnimation.update(dt);
        if (velocity.x > 0) {
            velocity.x -= horizontalDeceleration;
        } else if (velocity.x < 0) {
            velocity.x += horizontalDeceleration;
        } else {
            velocity.x = 0;
        }

        position.x += velocity.x;
        position.y += velocity.y;
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x + rectangle.getWidth() > Fartlek.WIDTH) {
            position.x = Fartlek.WIDTH - rectangle.getWidth();
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

    /**
     * Returns the current texture for the player.
     *
     * @return
     */
    public TextureRegion getTexture() {
        return playerAnimation.getFrame();
    }

    /**
     * Sets the texture for the animations to somethign new
     *
     * @param texture
     */
    public void setTexture(Texture texture) {
        setPlayerAnimation(new Animation(new TextureRegion(texture), animFrames, ANIM_CYCLE_TIME));
    }

    /**
     * Gets the player animation
     *
     * @return
     */
    public Animation getPlayerAnimation() {
        return playerAnimation;
    }

    /**
     * Sets the player animation
     *
     * @param playerAnimation
     */
    public void setPlayerAnimation(Animation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    public void dispose() {
        texture.dispose();
    }
}
