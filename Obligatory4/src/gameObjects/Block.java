package gameObjects;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

	public static ArrayList<Block> blockList = new ArrayList<>();
	
	final static int BLOCKWIDTH = 50;
	final static int BLOCKHEIGHT = 10;
	
	public Block() {
		super(BLOCKWIDTH, BLOCKHEIGHT);
		setVisible(false);
		
	}
	
	public Block(double x, double y) {
		super(x, y, BLOCKWIDTH, BLOCKHEIGHT);
		setVisible(false);
		
	}
	
	public Block(int lineNumber, double x, double y) {
		super(x, y, BLOCKWIDTH, BLOCKHEIGHT);

		switch (lineNumber){
		
		case 1: case 2: 
			setFill(Color.RED);
			break;
		case 3: case 4: 
			setFill(Color.GREEN);
			break;
		case 5: case 6: 
			setFill(Color.BLUE);
			break;
		case 7: case 8: 
			setFill(Color.PURPLE);
			break;
		case 9: case 10: 
			setFill(Color.ORANGE);
			break;
		}
		blockList.add(this);
		
	}
	
	public boolean collides(Ball ball) {
		return ball.intersects(getX(), getY(), getWidth(),
					getHeight()) && isVisible();
		
	}
	public static ArrayList<Block> getBlocks(){
		return blockList;
	}
	
}
