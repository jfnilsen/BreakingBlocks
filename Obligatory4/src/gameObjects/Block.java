package gameObjects;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

	public static ArrayList<Block> blockList = new ArrayList<>();
	
	public final static int BLOCKWIDTH = 50;
	public final static int BLOCKHEIGHT = 10;
	
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
	
	public boolean collides(BallAnimation ball) {
		return ball.intersects(getX(), getY(), getWidth(),
					getHeight()) && isVisible();
		
	}
	public static ArrayList<Block> getBlocks(){
		return blockList;
	}

	public boolean colidesWithSides(BallAnimation ball) {
		return (ball.getCenterX() + ball.getRadius() == getX() || ball.getCenterX() - ball.getRadius() == getX() + getWidth());
	}
	public static boolean noBlocksRemaining() {
		int blocksDisabled = 0;
		for(Block block: blockList){
			if(!block.isVisible()){
				++blocksDisabled;
			}
		}
		return (blocksDisabled == blockList.size());
			
	}
	public static void clearAllBlocks(){
		for(Block block: blockList){
			block.setVisible(false);
		}
		blockList.clear();
	}
	
}
