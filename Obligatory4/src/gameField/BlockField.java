package gameField;


import gameObjects.Block;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BlockField extends Pane {
	
	private double yPadding = 10;
	private double xPadding = 10;
	
	public BlockField() {
		
		setMinHeight(550);
		setMinWidth(830);
		addBlocks();
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

	}

	public void addBlocks() {
		for (int line = 1; line < 11; line++) {
			for (int i = 1; i < 14; i++) {
				double random = Math.random();
				if (random > 0.2) {
					getChildren().add(new Block(line,((i*50)+(((i*xPadding)+1))) ,((line*10)+(((line*yPadding)+1)))));
				}
			}
		}
		for (int i = 0; i < 15; i++) {
				getChildren().add(new Block(((i*50)+(((i*xPadding)+1))),0));

	}
	}
}
