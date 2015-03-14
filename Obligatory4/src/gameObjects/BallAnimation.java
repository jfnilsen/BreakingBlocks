package gameObjects;

import game.Main;
import gameField.BlockField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class BallAnimation extends Circle {
	double x = getCenterX(), y = getCenterY();
	static double dx = 0;
	static double dy = 0;
	static double radius = 5;
	static Timeline animation = new Timeline();
	static boolean gameStarted = false;
	static long timeOfBallStart = 0;
	static int level = 1;
	static Button nextLevelButton;

	public BallAnimation(Pane pane, BlockField blockField, Racquet racquet, Timer timer) {
		super(415, 490, radius);
		setFill(Color.WHITE);

		setNextLevelButton(blockField, racquet, timer);
		setResetButton(blockField, racquet, timer);

		animation = new Timeline(new KeyFrame(Duration.millis(60),
				e -> moveBall(pane, racquet, timer)));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setRate(15);
		animation.play();

	}

	private void setResetButton(BlockField blockField, Racquet racquet,	Timer timer) {
		Button resetButton = new Button("Reset");
		resetButton.relocate(720, 530);
		
		
		resetButton.setOnMouseClicked(e -> {
			Main.infoLabels.get(0).setText("Click to play");
			Main.infoLabels.get(0).setTextFill(Color.WHITE);;
			nextLevelButton.setStyle("-fx-background-color: gray;");
			nextLevelButton.setText("Next level locked");
			gameStarted = false;
			level = 1;
			Block.clearAllBlocks();
			blockField.addBlocks();
			setCenterY(490);
			setCenterX(racquet.getX() + (racquet.getWidth() / 2));
			dy = 0;
			dx = 0;
			animation.play();
			
			for(int i = 1; i <= 3; i++){
				if(i == 1){
					Main.infoLabels.get((2*i-1)).setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
				}else {
					Main.infoLabels.get((2*i-1)).setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
				}
				Main.infoLabels.get((2*i)).setText("0");
			}
			
		});
		blockField.getChildren().add(resetButton);
		
	}

	private void setNextLevelButton(BlockField blockField, Racquet racquet, Timer timer) {
		nextLevelButton = new Button("Next level locked");
		nextLevelButton.relocate(600, 530);
		nextLevelButton.setStyle("-fx-background-color: gray;");
		
		nextLevelButton.setOnMouseClicked(e -> {
			if (Block.noBlocksRemaining() && level != 3) {
				nextLevelButton.setStyle("-fx-background-color: grey;");
				nextLevelButton.setText("Next level locked");
				Block.blockList.clear();
				blockField.addBlocks();
				setCenterY(490);
				setCenterX(racquet.getX() + (racquet.getWidth() / 2));
				gameStarted = false;
				dy = 0;
				dx = 0;
				Main.infoLabels.get(0).setTextFill(Color.WHITE);;
				Main.infoLabels.get(0).setText("Click to play.");
				
				++level;
				
			}
			
		});
		
		blockField.getChildren().add(nextLevelButton);
	}

	public static void playBall() {
		if (!gameStarted) {
			timeOfBallStart = System.currentTimeMillis();
			dy = -1;
			dx = -1;
			Main.infoLabels.get(0).setText("");
			gameStarted = true;
		}
	}

	public void moveBall(Pane pane, Racquet racquet, Timer timer) {

		if (!gameStarted) {
			setCenterY(490);
			setCenterX(racquet.getX() + (racquet.getWidth() / 2));

		}

		timer.displayTimePassed(timeOfBallStart);
		setCenterX(getCenterX() + dx);
		setCenterY(getCenterY() + dy);
		for (Block block : Block.blockList) {
			if (block.collides(this) && block.colidesWithSides(this)) {
				dx *= -1;
				block.setVisible(false);

			} else if (block.collides(this)) {
				block.setVisible(false);
				dy *= -1;
			}
		}

		if (getCenterY() > racquet.getY() - (getRadius() + 1)) {
			if (racquet.engulfsBall(this)) {
				// best fix I could do with the issue of the ball being engulfed
				// or rolling on the racquet
				setCenterY(racquet.getY() - (getRadius() * 2));
			} else if (racquet.collidesWithSides(this)) {
				dx *= -1;
				dy *= -1;
			} else if (racquet.collides(this)) {
				dy *= -1;
			}
		}

		if (getCenterX() == 0 + getRadius()
				|| getCenterX() > pane.getWidth() - getRadius()) {
			dx *= -1;
		}
		if (getCenterY() == 0 + getRadius()
				|| getCenterY() > pane.getHeight() - getRadius()) {
			dy *= -1;
		}
		if (getCenterY() == pane.getHeight() - getRadius()) {
			animation.stop();

			Main.infoLabels.get(2*level).setText(timer.getTime());
			Main.infoLabels.get(0).setTextFill(Color.RED);;
			Main.infoLabels.get(0).setText("You lose!");
		}

		if (Block.noBlocksRemaining() && gameStarted) {
			nextLevelButton.setStyle("-fx-background-color: green;");
			nextLevelButton.setText("Go to next level!");
			Main.infoLabels.get((2*level-1)).setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
			Main.infoLabels.get((2*level)).setText(timer.getTime());
			if (level < 3) {
				Main.infoLabels.get(2*level+1).setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
				Main.infoLabels.get(0).setTextFill(Color.GREEN);;
				Main.infoLabels.get(0).setText("Level clear");
			}
			if(level == 3){
				Main.infoLabels.get(0).setText("You win!");
				Main.infoLabels.get(0).setTextFill(Color.GREEN);;
				animation.stop();
			}
			gameStarted = false;

		}

	}

}
