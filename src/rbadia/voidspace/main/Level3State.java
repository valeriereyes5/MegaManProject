package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;
import sun.lwawt.PlatformComponent;

public class Level3State extends Level2State {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6330305833847871298L;

	protected boolean Direction = true;
	//If true platform moves right///
	//iff alse, left 
	
	
	//constructor//
	public Level3State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(10 , SCREEN_HEIGHT/2 + 140 - i*40);
		}
		return platforms;

	}
	public boolean PlatformReachesEdge() {
		boolean value = false;
		for(int i=0; i<getNumPlatforms(); i++){
			if ((platforms[i].getX() + platforms[i].getWidth()) == this.getWidth()) {
				value = true;
			}
			else if ((platforms[i].getX() < 0)) {
				value = true;
			}
		}
		return value;
	}
	public int Direction() {
		int Direction = 0;
		for(int i=0; i<getNumPlatforms(); i++){
		if(platforms[i].getX() + platforms[i].getWidth() == this.getWidth()) {
			Direction = -1;
		}
		
		else if (platforms[i].getX() < 0) {
			Direction = 1;
		}
		}
		return Direction;
	}

	@Override
	protected void drawPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<getNumPlatforms(); i++) {
			if (Direction) {
				if (platforms[i].getX() >=0 && platforms[i].getX() + platforms[i].getWidth() < 500) {
					platforms[i].translate(2, 0);
				}
				else if(platforms[i].getX() + platforms[i].getWidth() >= 500) {
					Direction = false;
					
				}
			}
			else {
				if(platforms[i].getX() >0 && platforms[i].getX() + platforms[i].getWidth() <= 500) {
					platforms[i].translate(-2, 0);
				}
				else if(platforms[i].getX() <= 0) {
					Direction = true;
				}
			}
			getGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
		}
	}
	public void removeAsteroid(Asteroid asteroid){
		// "remove" asteroid
		asteroidExplosion = new Rectangle(
				asteroid.x,
				asteroid.y,
				asteroid.getPixelsWide(),
				asteroid.getPixelsTall());
		asteroid.setLocation(-asteroid.getPixelsWide(), -asteroid.getPixelsTall());
		this.getGameStatus().setNewAsteroid(true);
		lastAsteroidTime = System.currentTimeMillis();
		// play asteroid explosion sound
		this.getSoundManager().playAsteroidExplosionSound();
		asteroid.setSpeed(rand.nextInt(9)+1);
		System.out.println("speed is" + asteroid.getSpeed());
	}
}
