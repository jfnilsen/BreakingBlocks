package gameObjects;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Racquet extends Rectangle {

	double yPos = 0;
	public Racquet(Pane pane) {
		super(100, 500, 100, 20);
		setFill(Color.WHITE);
		
		pane.setOnMouseMoved(e -> {
			setX((e.getX()-(getWidth()/2)));
			
		});
	}
	
	public boolean collides(Ball ball) {
		return ((ball.getCenterY() + ball.getRadius() == getY() && collidesWithXPos(ball))|| 
				ball.getCenterY() - ball.getRadius() == getY() + getHeight() && collidesWithXPos(ball))  && 
				isVisible() && 
				!engulfsBall(ball);
		
	}

	private boolean collidesWithXPos(Ball ball) {
		return (ball.getCenterX() >= getX() && ball.getCenterX() <= getX() + getWidth());
	}
	private boolean isAtYPos(Ball ball) {
		return (ball.getCenterY() + ball.getRadius() >= getY() && 
				ball.getCenterY() - ball.getRadius() <= getY() + getHeight()); 
	}

	public boolean collidesWithSides(Ball ball) {
		return ((ball.getCenterX() + ball.getRadius() == getX() && isAtYPos(ball))|| 
				(ball.getCenterX() - ball.getRadius() == getX() + getWidth())  && isAtYPos(ball))
				&& !engulfsBall(ball);
		
	}
	
	public boolean engulfsBall(Ball ball) {
		return(ball.getCenterX() + ball.getRadius() > getX() && 
				ball.getCenterX() - ball.getRadius() < getX() + getWidth()) && 
				ball.getCenterY() + ball.getRadius() > getY() &&
				ball.getCenterY() - ball.getRadius() < getY()  + getHeight();
	}
}
