package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.Animation;
import com.nnldev.fartlek.states.PlayState;

import java.util.ArrayList;

/**
 * Created by Nano on 11/12/2015.
 */
public class Runner {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle rectangle;
    public ArrayList<Bullet> bullets;
    public boolean shoot;
    public boolean reloaded;
    private boolean startCounting;
    public float shotTimer;
    private Animation playerAnimation;
    private int health;
    private float horizontalSpeed;
    private final int RUNNER_Y = 160;
    private final float ANIM_CYCLE_TIME = 0.3f;
    private Sound moveSound;
    private float soundTimer;
    private boolean soundPlayable;
    /**
     * Makes a new runner
     *
     * @param path       The path for the runner's pic
     * @param animFrames The number of frames in the picture t oallow for animation of
     *                   the runner
     */
    public Runner(String path, int animFrames) {
        soundTimer = 0;
        soundPlayable = false;
        texture = new Texture(path);
        velocity = new Vector3(0, 0, 0);
        position = new Vector3(((Fartlek.WIDTH / 2) - ((texture.getWidth() / animFrames) / 2)), RUNNER_Y, 0);
        rectangle = new Rectangle(position.x, position.y, texture.getWidth() / animFrames, texture.getHeight());
        horizontalSpeed = 8;
        playerAnimation = new Animation(new TextureRegion(texture), animFrames, ANIM_CYCLE_TIME);
        moveSound = Gdx.audio.newSound(Gdx.files.internal("Sounds\\movesound1.ogg"));
        bullets = new ArrayList<Bullet>();
        shoot = false;
        reloaded = false;
        startCounting = true;
        shotTimer = 0;
    }

    /**
     * The play sound
     */
    private void playMoveSound() {
        if (soundPlayable) {
            moveSound.stop();
            moveSound.play(0.01f);
            soundPlayable = false;
        }

    }

    /**
     * Updates the runner
     *
     * @param dt The change in time since the last time the runner's position
     *           was updated
     */
    public void update(float dt) {
        soundTimer += dt;
        if (soundTimer >= 0.3f) {
            soundTimer = 0;
            soundPlayable = true;
        }
        playerAnimation.update(dt);
        if (velocity.x > 0) {
            position.x += velocity.x;
        } else if (velocity.x < 0) {
            position.x += velocity.x;
        } else {
            velocity.x = 0;
        }
        velocity.x = 0;
        position.y += velocity.y;
        rectangle.y = position.y;
        rectangle.x = position.x;
        if (position.x < 0)
            position.x = 0;
        if (position.x + rectangle.getWidth() > Fartlek.WIDTH)
            position.x = Fartlek.WIDTH - rectangle.getWidth();
        if (startCounting) {
            shotTimer += dt;
        }
        if (shotTimer >= 1) {
            shotTimer = 0;
            reloaded = true;
            startCounting = false;
        }
        if (shoot) {
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).update(dt);
                if (bullets.get(i).done) {
                    bullets.remove(i);
                    PlayState.killerID = -1;
                }
            }
        }
    }

    /**
     * Moves the character left
     */
    public void left() {
        if (Fartlek.soundFXEnabled) {
            playMoveSound();
        }
        velocity.x = -horizontalSpeed;
    }

    /**
     * Moves the character right
     */
    public void right() {
        if (Fartlek.soundFXEnabled) {
            playMoveSound();
        }
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

    /**
     * Sets the position of the runner
     *
     * @param position The vector3 position of where the runner will be drawn
     */
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
    public void setTexture(Texture texture, int animFrames) {
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

    /**
     * Adds a new bullet to the bullet timer
     */
    public void shoot() {
        if (reloaded) {
            bullets.add(new Bullet("Items\\bullet.png", getPosition().x));
            shoot = true;
            reloaded = false;
            startCounting = true;
        }
    }

    public void dispose() {
        texture.dispose();
        moveSound.dispose();
    }
}
