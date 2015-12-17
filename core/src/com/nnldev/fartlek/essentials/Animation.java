/**
 * @author Nano
 * 
 */
package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
	private Array<TextureRegion> frames;
	private float maxFrameTime, currentFrameTime;
	// nuber of frames inthe animation
	private int frameCount;
	// current frame
	private int frame;

	/**
	 * Takes a texture region and chops it up into samller pics
	 *
	 * @param region
	 *            The texture region which contains all the frames
	 * @param frameCount
	 *            The number of frames in the texture region
	 * @param cycleTime
	 *            The amount of time each frame should be displayed for
	 */
	public Animation(TextureRegion region, int frameCount, float cycleTime) {
		frames = new Array<TextureRegion>();
		int frameWidth = region.getRegionWidth() / frameCount;
		// cuts up the texture into individual pictures
		for (int i = 0; i < frameCount; i++) {
			frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
		}
		this.frameCount = frameCount;
		maxFrameTime = cycleTime / frameCount;
		frame = 0;
	}

	/**
	 * Creates an animation from a texture, the number of frames in the image
	 * and the amount of time to cycle between each animation
	 *
	 * @param texture
	 *            The image which should be in the android assets folder
	 * @param frameCount
	 *            The number of frames in the animation
	 * @param cycleTime
	 *            The amount of time to show each animation
	 */
	public Animation(Texture texture, int frameCount, float cycleTime) {
		this(new TextureRegion(texture), frameCount, cycleTime);
	}

	/**
	 * Creates an animation from a path for a texture, the number of frames in
	 * the image and the amount of time to cycle between each animation
	 *
	 * @param path
	 *            The name of the image which should be in the android assets
	 *            folder
	 * @param frameCount
	 *            The number of frames in the animation
	 * @param cycleTime
	 *            The amount of time to show each animation
	 */
	public Animation(String path, int frameCount, float cycleTime) {
		this(new Texture(path), frameCount, cycleTime);
	}

	public void update(float dt) {
		currentFrameTime += dt;
		if (currentFrameTime > maxFrameTime) {
			frame++;
			currentFrameTime = 0;
		}
		if (frame >= frameCount) {
			frame = 0;
		}
	}

	public TextureRegion getFrame() {
		return frames.get(frame);
	}
}
