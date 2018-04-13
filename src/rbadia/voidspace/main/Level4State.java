package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

///implent the 2 asteroids and less lives
public class Level4State extends Level3State{
protected Asteroid asteroid2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6710229566087339766L;

	public Level4State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void updateScreen() {
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
		drawAsteroid();
		
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
			this.platforms[i] = new Platform(0,0);
			if(i<4)	platforms[i].setLocation(50+ i*50, SCREEN_HEIGHT/2 + 140 - i*40);
			if(i==4) platforms[i].setLocation(50 +i*50, SCREEN_HEIGHT/2 + 140 - 3*40);
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, SCREEN_HEIGHT/2 + 20 + (i-k)*40 );
				k=k+2;
			}
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
	@Override
	public int Direction() {
		int Direction = 0;
		for(int i=0; i<getNumPlatforms(); i++){
			if(platforms[i].getX() + platforms[i].getHeight() == this.getHeight()) {
				Direction = -1;
			}

			else if (platforms[i].getY() < 0) {
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
				if (platforms[i].getY() >=0 && platforms[i].getY() + platforms[i].getHeight() < 400) {
					platforms[i].translate(0, 2);
				}
				else if(platforms[i].getY() + platforms[i].getHeight() >= 400) {
					Direction = false;

				}
			}
			else {
				if(platforms[i].getY() >0 && platforms[i].getY() + platforms[i].getHeight() <= 400) {
					platforms[i].translate(0, -2);
				}
				else if(platforms[i].getY() <= 0) {
					Direction = true;
				}
			}
			getGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
		}
	}
	
	public void removeAsteroid2(Asteroid asteroid){
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

	protected void drawAsteroid2() {
		//System.out.println("spot"+asteroid.getX());
		Graphics2D g2d = getGraphics2D();
		if(place == true) {

			if((asteroid2.getX() + asteroid2.getPixelsWide() <  this.getWidth())) {
				asteroid2.translate(asteroid2.getSpeed(), asteroid2.getSpeed()/2);
				getGraphicsManager().drawAsteroid(asteroid2, g2d, this);	

			}
			else {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
					asteroid2.setLocation(0,
							rand.nextInt(SCREEN_HEIGHT - asteroid2.getPixelsTall() - 32));
				}
				else {
					// draw explosion
					getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}
			}
		}
		// if false, it comes from the right
		else {


			if((asteroid2.getX() + asteroid2.getPixelsWide() >  0)) {

				asteroid2.translate(-asteroid2.getSpeed(), asteroid2.getSpeed()/2);
				getGraphicsManager().drawAsteroid(asteroid2, g2d, this);	

			}
			else {
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
					asteroid2.setLocation(SCREEN_WIDTH - asteroid2.getPixelsWide(),
							rand.nextInt(SCREEN_HEIGHT - asteroid2.getPixelsTall() - 32));
				}
				else {
					// draw explosion
					getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
				}

			}
		}	
	}

}
