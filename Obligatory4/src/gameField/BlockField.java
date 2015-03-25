package gameField;



import gameObjects.BallAnimation;
import gameObjects.Block;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BlockField extends Pane {
	
	public final static double YPADDING = 15;
	public final static double XPADDING = 15;
	
	public BlockField() {
		
		setMinHeight(550);
		setMinWidth(830);
		addBlocks();
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		
		setOnMouseClicked(e -> {
			BallAnimation.playBall();
		});

	}

	public void addBlocks() {
		for (int line = 1; line < 11; line++) {
			for (int i = 1; i < 14; i++) {
				double random = Math.random();
				if (random > 0.2) {
					Block block = new Block(line,((i*Block.BLOCKWIDTH)+(((i*XPADDING)+1))) ,((line*Block.BLOCKHEIGHT)+(((line*YPADDING)))));
					getChildren().add(block);
				}else {
					Block block = new Block(line,((i*Block.BLOCKWIDTH)+(((i*XPADDING)+1))) ,((line*Block.BLOCKHEIGHT)+(((line*YPADDING)))));
					block.setVisible(false);
					getChildren().add(block);
					
				}
			}
		}
		for (int i = 0; i < 15; i++) {
				getChildren().add(new Block(((i*Block.BLOCKWIDTH)+(((i*XPADDING)))),0));

	}
	}
	
}
