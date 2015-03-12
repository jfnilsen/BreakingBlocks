package gameObjects;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Racquet extends Rectangle {

	double yPos = 0;
	public Racquet(Pane pane) {
		super(100, 500, 100, 10);
		setFill(Color.WHITE);
		
		pane.setOnMouseMoved(e -> {
			setX((e.getX()-50));
			
		});
	}
	
	public boolean collides(Ball ball) {
		return ball.intersects(getX(), getY(), getWidth(),
					getHeight()) && isVisible();
		
	}
}
