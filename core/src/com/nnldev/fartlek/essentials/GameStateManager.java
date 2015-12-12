/**
 * @author Nano
 * Class that handles the game states, allowing for a central location for all the states
 */
package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nnldev.fartlek.states.State;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

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
