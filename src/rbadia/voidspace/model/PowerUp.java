package rbadia.voidspace.model;

public class PowerUp extends GameObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6555028163066616009L;
	public static final int WIDTH = 1;
	public static final int HEIGHT = 1;
	
	public PowerUp(int xPos, int yPos) {
		super(xPos, yPos, PowerUp.WIDTH, PowerUp.HEIGHT);
	}
} 
