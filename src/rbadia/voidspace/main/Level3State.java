package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.PowerUp;
import rbadia.voidspace.sounds.SoundManager;
import sun.lwawt.PlatformComponent;

public class Level3State extends Level2State {
	/** 
	 * 
	 */

	private static final long serialVersionUID = 6330305833847871298L;
	boolean place = false; 
	int randomnum = rand.nextInt(2);
	int randnumcase;
	protected boolean Direction = true;
	//If true platform moves right///
	//iff alse, left  
	protected int numpowerups = 3;
	protected PowerUp powerups = new PowerUp(this.getWidth()/2,this.getHeight()/2);

	//constructor//
	public Level3State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void updateScreen(){
		Graphics2D g2d = getGraphics2D();
		GameStatus status = this.getGameStatus();

		// save original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		clearScreen();
		drawStars(50);
		drawFloor();
		drawPlatforms();
		drawMegaMan();
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		checkBullletAsteroidCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();
		DrawPowerups();
		checkMegaManPowerUpCollisions();
		
		//removePowerup(powerups);

		// update asteroids destroyed (score) label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
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

		this.getGameStatus().setNewAsteroid(true);
		lastAsteroidTime = System.currentTimeMillis();
		// play asteroid explosion sound
		this.getSoundManager().playAsteroidExplosionSound();
		asteroid.setSpeed(rand.nextInt(9)+1);
		System.out.println("speed is" + asteroid.getSpeed());

		switch(randomnum) {
		case 0: { 
			asteroid.setLocation(-asteroid.getPixelsWide(), -asteroid.getPixelsTall());
			place = false;
		}
		case 1: {
			asteroid.setLocation(0,0);
			place= true;
		}
		System.out.println("rand num"+randomnum);
		}

	}
	@Override
	protected void drawAsteroid() {
		//System.out.println("spot"+asteroid.getX());
		Graphics2D g2d = getGraphics2D();
		if(place == true) {

			if((asteroid.getX() + asteroid.getPixelsWide() <  this.getWidth())) {
				asteroid.translate(asteroid.getSpeed(), asteroid.getSpeed()/2);
				getGraphicsManager().drawAsteroid(asteroid, g2d, this);	

			}
			else {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
					asteroid.setLocation(0,
							rand.nextInt(SCREEN_HEIGHT - asteroid.getPixelsTall() - 32));
				}
				else {
					// draw explosion
					getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}
		}
		// if false, it comes from the right
		else {


			if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {

				asteroid.translate(-asteroid.getSpeed(), asteroid.getSpeed()/2);
				getGraphicsManager().drawAsteroid(asteroid, g2d, this);	

			}
			else {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
					asteroid.setLocation(SCREEN_WIDTH - asteroid.getPixelsWide(),
							rand.nextInt(SCREEN_HEIGHT - asteroid.getPixelsTall() - 32));
				}
				else {
					// draw explosion
					getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}

			}
		}	
	}
	public void removePowerup(PowerUp powerup){
		// "remove" asteroid
		
		powerup.setLocation(100000, 100000);
		//this.getGameStatus().setNewAsteroid(true);
		//lastAsteroidTime = System.currentTimeMillis();
		// play asteroid explosion sound
		//this.getSoundManager().playAsteroidExplosionSound();
		
	}
	protected void DrawPowerups() {
		Graphics2D g2d = getGraphics2D();
		getGraphicsManager().drawPowerUp(powerups, g2d, this);
	}
	protected void checkMegaManPowerUpCollisions() {
		GameStatus status = getGameStatus();

		if(powerups.intersects(megaMan)){
			System.out.println("aye");
			status.setLivesLeft(status.getLivesLeft() + 5);
			removePowerup(powerups);
		}
	}
}



