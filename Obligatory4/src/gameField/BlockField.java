package gameField;

import java.util.ArrayList;

import gameObjects.Block;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BlockField extends Pane {

	public final static double YPADDING = 20;
	public final static double XPADDING = 15;
	public static ArrayList<Block> blockList = new ArrayList<>();
	private int numberOfBlocksPerLine = 13;
	private int numberOfLines = 10;

	public BlockField() {

		setMinHeight(550);
		setMinWidth(830);
		addFrameBlocks();
		addBlocks();
		setBackground(new Background(
				new BackgroundFill(Color.BLACK, null, null)));


	}

	private void addFrameBlocks() {
		for (int i = 0; i < numberOfBlocksPerLine+2; i++) {
			getChildren().add(new Block(((i * Block.BLOCKWIDTH) + (((i * XPADDING)))), 0));
			
		}
	}

	public void addBlocks() {
		for (int line = 1; line <= numberOfLines; line++) {
			for (int i = 1; i <= numberOfBlocksPerLine; i++) {
				double random = Math.random();
				if (random > 0.2) {
					Block block = new Block(
							line,
							((i * Block.BLOCKWIDTH) + (((i * XPADDING)))),
							((line * Block.BLOCKHEIGHT) + (((line * YPADDING)))));
					getChildren().add(block);
					blockList.add(block);
				} else {
					Block block = new Block(
							line,
							((i * Block.BLOCKWIDTH) + (((i * XPADDING)))),
							((line * Block.BLOCKHEIGHT) + (((line * YPADDING)))));
					block.setVisible(false);
					getChildren().add(block);
					blockList.add(block);

				}
			}
		}
	}

	public static ArrayList<Block> getBlocks() {
		return blockList;
	}

	public static boolean noBlocksRemaining() {
		int blocksDisabled = 0;
		for (Block block : blockList) {
			if (!block.isVisible()) {
				++blocksDisabled;
			}
		}
		return (blocksDisabled == blockList.size());

	}

	public void clearAllBlocks() {
		getChildren().removeAll(blockList);
		blockList.clear();
	}
	public int getNumberOfBlocksPerLine(){
		return numberOfBlocksPerLine;	
	}
	public int getNumberOfLines(){
		return numberOfLines;
	}
	
	

}
