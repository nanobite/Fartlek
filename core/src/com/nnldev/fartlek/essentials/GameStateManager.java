/**
 * @author Nano
 * Class that handles the game states, allowing for a central location for all the states
 */
package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.states.State;

import java.util.Stack;

public class GameStateManager {
    //A stack of states, kinda like an arraylist or something
    private Stack<State> states;

    /**
     * Makes a new game state manager which handles the stack of states
     */
    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Pushes the state into the stack
     *
     * @param state
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Pops the state at the top of the stack
     */
    public void pop() {
        states.pop();
    }

    /**
     * Sets the state at the top of the stack to something new
     *
     * @param state
     */
    public void set(State state) {
        states.pop();
        states.push(state);
    }

    /**
     * Updates the state at the top of the stack
     *
     * @param deltaTime
     */
    public void update(float deltaTime) {
        states.peek().update(deltaTime);
    }

    /**
     * Renders the state at the top of the states stack.
     *
     * @param sb The batch of sprites which will be rendered
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
