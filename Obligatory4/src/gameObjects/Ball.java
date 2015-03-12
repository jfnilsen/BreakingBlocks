package gameObjects;


import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball extends Circle {
	double x = 400, y = 510;
	double dx = 1, dy = -1;
	static double radius = 5;

	public Ball(Pane pane, Racquet racquet) {
		super(0, 0, radius);
		setFill(Color.WHITE);

		Timeline animation = new Timeline(new KeyFrame(Duration.millis(60),
				e -> moveBall(pane, racquet)));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setRate(15);
		animation.play();

	}

	public void moveBall(Pane pane, Racquet racquet) {
		x += dx;
		y += dy;
		setCenterX(x);
		setCenterY(y);
		ArrayList<Block> blocks = Block.blockList;

		for (Block block : blocks) {
			if (block.collides(this)) {
				block.setVisible(false);
				dy *= -1;
			}
		}
		if (racquet.collides(this)){
			dy *= -1;
		}

		if (x == 0 || x > pane.getWidth() - getRadius()) {
			dx *= -1;
		}
		if (y == 0 || y > pane.getHeight() - getRadius()) {
			dy *= -1;
		}

	}

}
