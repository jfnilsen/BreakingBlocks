package gameController;

import gameField.BlockField;
import gameObjects.Ball;
import gameObjects.Block;
import gameObjects.Racquet;
import gameObjects.Timer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class BallAnimation {
	Timeline animation;
	Button nextLevelButton;
	private boolean gameStarted;
	private boolean gamePaused;
	private int level = 1;
	private long timeOfBallStart = 0;
	private double dx;
	private double dy;
	public BallAnimation(Pane pane, BlockField blockField, Racquet racquet,
			Timer timer, Ball ball) {
		dx = 0;
		dy = 0;

		setNextLevelButton(blockField, racquet, timer, ball);
		setResetButton(blockField, racquet, timer, ball);

		animation = new Timeline(new KeyFrame(Duration.millis(60),
				e -> moveBall(pane, racquet, timer, blockField, ball)));
		
		pane.setOnMouseClicked(e -> {
			playBall();
		});

		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setRate(15);
		animation.play();

	}

	private void setResetButton(BlockField blockField, Racquet racquet,
			Timer timer, Ball ball) {
		Button resetButton = new Button("Reset");
		resetButton.relocate(720, 530);

		resetButton.setOnMouseClicked(e -> {
			timer.setText("Time: 0");
			Main.infoLabels.get(0).setText("Click to play");
			Main.infoLabels.get(0).setTextFill(Color.WHITE);
			nextLevelButton.setStyle("-fx-background-color: gray;");
			nextLevelButton.setText("Next level locked");
			gameStarted = false;
			gamePaused = false;
			level = 1;
			blockField.clearAllBlocks();
			blockField.addBlocks();
			ball.setCenterY(490);
			ball.setCenterX(racquet.getX() + (racquet.getWidth() / 2));
			dy = 0;
			dx = 0;
			if(animation.getStatus() == Animation.Status.STOPPED){
				animation.play();
				
			}

			for (int i = 1; i <= 3; i++) {
				if (i == 1) {
					Main.infoLabels.get((2 * i - 1)).setBackground(
							new Background(new BackgroundFill(Color.BLUEVIOLET,
									null, null)));
				} else {
					Main.infoLabels.get((2 * i - 1)).setBackground(
							new Background(new BackgroundFill(Color.DARKRED, null,
									null)));
				}
				Main.infoLabels.get((2 * i)).setText("0");
			}

		});
		blockField.getChildren().add(resetButton);

	}

	private void setNextLevelButton(BlockField blockField, Racquet racquet,
			Timer timer, Ball ball) {
		nextLevelButton = new Button("Next level locked");
		nextLevelButton.relocate(600, 530);
		nextLevelButton.setStyle("-fx-background-color: gray;");

		nextLevelButton.setOnMouseClicked(e -> {
			if (BlockField.noBlocksRemaining() && level != 3) {
				nextLevelButton.setStyle("-fx-background-color: grey;");
				nextLevelButton.setText("Next level locked");
				BlockField.blockList.clear();
				blockField.addBlocks();
				ball.setCenterY(490);
				ball.setCenterX(racquet.getX() + (racquet.getWidth() / 2));
				gameStarted = false;
				gamePaused = false;
				dy = 0;
				dx = 0;
				Main.infoLabels.get(0).setTextFill(Color.WHITE);
				Main.infoLabels.get(0).setText("Click to play.");

				++level;

			}

		});

		blockField.getChildren().add(nextLevelButton);
	}

	public void playBall() {
		if (!gameStarted && !gamePaused) {
			dy = -1;
			dx = -1;
			Main.infoLabels.get(0).setText("");
			gameStarted = true;
		}
	}

	public void moveBall(Pane pane, Racquet racquet, Timer timer,
			BlockField blockField, Ball ball) {

		if (!gameStarted) {
			ball.setCenterY(490);
			ball.setCenterX(racquet.getX() + (racquet.getWidth() / 2));
			timeOfBallStart = System.currentTimeMillis();
		}

		timer.displayTimePassed(timeOfBallStart);
		ball.setCenterX(ball.getCenterX() + dx);
		ball.setCenterY(ball.getCenterY() + dy);
		
		for (int row = 1; row <= (BlockField.blockList.size() / blockField.getNumberOfBlocksPerLine()); row++) {
			if (isInGridYWithBlock(row, ball)) {
				// ball hits block check
				for (int column = 0; column < (BlockField.blockList.size() / blockField.getNumberOfLines()); column++) {
					 if (isInGridXWithBlock(column, ball)) {
					if (ballCollidesWithBlockInThisGridsSides(column, row, ball)) {
						dx *= -1;
						BlockField.blockList.get(column + 13 * (row - 1))
								.setVisible(false);
					} else if (ballCollidesWithBlockInThisGrid(column, row, ball)) {
						BlockField.blockList.get(column + 13 * (row - 1))
								.setVisible(false);
						dy *= -1;
						}

					}
				}

			}
		}
		if (isAtHeightWithRacquet(ball, racquet)) {
			if (racquet.engulfsBall(ball)) {
				// best fix I could do with the issue of the ball being
				// engulfed
				// or rolling on the racquet
				ball.setCenterY(racquet.getY() - (ball.getRadius() * 2));
			} else if (racquet.collidesWithSides(ball)) {
				dx *= -1;
				dy *= -1;
			} else if (racquet.collides(ball)) {
				dy *= -1;
			}
		}

		if (hitsWall(ball, pane)) {
			dx *= -1;
		}
		if (hitsTop(ball, pane)) {
			dy *= -1;
		}
		if(hitsBottom(ball, pane)) {
			animation.stop();

			Main.infoLabels.get(2 * level).setText(timer.getTime());
			Main.infoLabels.get(0).setTextFill(Color.RED);
			Main.infoLabels.get(0).setText("You lose!");
			gamePaused = true;
		}

		if (BlockField.noBlocksRemaining() && gameStarted) {
			Main.infoLabels.get((2 * level - 1)).setBackground(
							new Background(new BackgroundFill(Color.GREEN, null, null)));
			Main.infoLabels.get((2 * level)).setText(timer.getTime());
			if (level < 3) {
				nextLevelButton.setStyle("");
				nextLevelButton.setText("Go to next level!");
				Main.infoLabels.get(2 * level + 1).setBackground(
						new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
				Main.infoLabels.get(0).setTextFill(Color.GREEN);
				Main.infoLabels.get(0).setText("Level clear");
			}else if (level == 3) {
				nextLevelButton.setText("All levels complete.");
				Main.infoLabels.get(0).setText("You win!");
				Main.infoLabels.get(0).setTextFill(Color.GREEN);
			}
			gameStarted = false;
			gamePaused = true;

		}

	}

	private boolean isAtHeightWithRacquet(Ball ball, Racquet racquet) {
		return ball.getCenterY() >= racquet.getY() - (ball.getRadius());
	}

	private boolean hitsWall(Ball ball, Pane pane) {
		return ball.getCenterX() == 0 + ball.getRadius()
				|| ball.getCenterX() > pane.getWidth() - ball.getRadius();
	}

	private boolean hitsTop(Ball ball, Pane pane) {
		return ball.getCenterY() == 0 + ball.getRadius()
				|| ball.getCenterY() > pane.getHeight() - ball.getRadius();
	}

	private boolean hitsBottom(Ball ball, Pane pane) {
		return ball.getCenterY() == pane.getHeight() - ball.getRadius();
	}

	private boolean ballCollidesWithBlockInThisGrid(int column, int row,Ball ball) {
		return BlockField.blockList.get(column + 13 * (row - 1)).collides(ball);
	}

	private boolean ballCollidesWithBlockInThisGridsSides(int column, int row, Ball ball) {
		return BlockField.blockList.get(column + 13 * (row - 1)).collides(	ball)
				&& BlockField.blockList.get(column + 13 * (row - 1)).colidesWithSides(ball);
	}

	private boolean isInGridXWithBlock(int column, Ball ball) {
		return ball.getCenterX() - ball.getRadius() <= (column + 2)
				* Block.BLOCKWIDTH + (column+1) * BlockField.XPADDING
				&& ball.getCenterX() + ball.getRadius() >= (column+1)
						* Block.BLOCKWIDTH + (column+1) * BlockField.XPADDING;
	}

	private boolean isInGridYWithBlock(int row, Ball ball) {
		return ball.getCenterY() - ball.getRadius() <= 
				(row * Block.BLOCKHEIGHT + row * BlockField.YPADDING)
				+ Block.BLOCKHEIGHT
				&& ball.getCenterY() + ball.getRadius() >= (row
						* Block.BLOCKHEIGHT + row * BlockField.YPADDING);
	}
	
	

}
