package rbadia.voidspace.main;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles user input events.
 */
@SuppressWarnings("unused")
public class InputHandler implements KeyListener{
	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean downIsPressed;
	private boolean upIsPressed;
	private boolean spaceIsPressed = false;
	private boolean shiftIsPressed;
	private boolean eIsPressed;
	private boolean qIsPressed;
	private static boolean nIsPressed;
	private boolean sIsPressed;
	private boolean iIsPressed;
	private boolean rIsPressed;
	
	public static boolean getnIsPressed() {
		return nIsPressed;
	}

	private LevelState levelState;
	//private GameScreen gScreen;

	public LevelState getLevelState() { return levelState; }
	public void setLevelState(LevelState levelState) { this.levelState = levelState; }

	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler(){
		reset();
	}

	public void reset() {
		leftIsPressed = false;
		rightIsPressed = false;
		downIsPressed = false;
		upIsPressed = false;
		spaceIsPressed = false;
		shiftIsPressed = false;
		eIsPressed = false;
		qIsPressed = false;
		nIsPressed = false;
		sIsPressed = false;
		iIsPressed = false;
		rIsPressed = false;
	}

	public boolean isLeftPressed() {
		return leftIsPressed;
	}

	public boolean isRightPressed() {
		return rightIsPressed;
	}

	public boolean isDownPressed() {
		return downIsPressed;
	}

	public boolean isUpPressed() {
		return upIsPressed;
	}

	public boolean isSpacePressed() {
		return spaceIsPressed;
	}

	public boolean isShiftPressed() {
		return shiftIsPressed;
	}

	public boolean isEPressed() {
		return eIsPressed;
	}

	public boolean isQPressed() {
		return qIsPressed;
	}

	public boolean isNPressed() {
		return nIsPressed;
	}

	public boolean isSPressed() {
		return sIsPressed;
	}

	public boolean isIPressed() {
		return iIsPressed;
	}
	
	public boolean isRPressed() {
		return rIsPressed;
	}

	/**
	 * Handle a key input event.
	 */
	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = true;
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = true;
			break;
		case KeyEvent.VK_ESCAPE:
				System.exit(1);
			break;
		case KeyEvent.VK_E:
			this.eIsPressed = true;
			break;
		case KeyEvent.VK_Q:
			this.qIsPressed= true;
			break;
		case KeyEvent.VK_N:
			this.nIsPressed= true;
			break;
		case KeyEvent.VK_S:
			this.sIsPressed = true;
			break;
		case KeyEvent.VK_I:
			this.iIsPressed = true;
			break;
		case KeyEvent.VK_R:
			this.rIsPressed = true;
			break;
		}
		e.consume();
	}

	/**
	 * Handle a key release event.
	 */
	@SuppressWarnings("static-access")
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = false;
			this.getLevelState().slowDownMegaMan();
			break;
		case KeyEvent.VK_E:
			this.eIsPressed = false;
			break;
		case KeyEvent.VK_Q:
			this.qIsPressed = false;
			break;
		case KeyEvent.VK_N:
			this.nIsPressed = false;
			break;
		case KeyEvent.VK_S:
			this.sIsPressed = false;
			break;
		case KeyEvent.VK_I:
			this.iIsPressed = false;
			break;
		case KeyEvent.VK_R:
			this.rIsPressed = false;
			break;
		}
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// not used
	}

	public boolean getSpace(){
		return spaceIsPressed;
	}

}
