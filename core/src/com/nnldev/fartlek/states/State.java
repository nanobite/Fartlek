/**
 * @author Nano
 * An abstract class to give guidelines for every state
 */
package com.nnldev.fartlek.states;
/**
 * @author Nano
 * January 20, 2016
 * Abstract class holding all th restrctions for states
 */

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.Fartlek;
import com.nnldev.fartlek.essentials.GameStateManager;

public abstract class State {

    protected GameStateManager gsm;

    /**
     * Creates a new state wit ha camera that zooms in
     *
     * @param gsm The game state manager which currently is handling which states are displayed
     */
    protected State(GameStateManager gsm) {
        this.gsm = gsm;
    }

    /**
     * Handles user input
     */
    protected abstract void handleInput();

    /**
     * Updates the data in the state
     *
     * @param deltaTime The time since the previous update
     */
    public abstract void update(float deltaTime);

    /**
     * Renders all the graphics onto the screen
     *
     * @param sb All the graphics that will be drawn
     */
    public abstract void render(SpriteBatch sb);

    /**
     * Disposes of all objects that are no longer necessary
     */
    public abstract void dispose();

}
