package gameObjects;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Timer extends Label{
	
	public Timer() {
		setText("Time: 0");
		setTextFill(Color.WHITE);
		relocate(800, 530);
		
	}
	
	public void displayTimePassed(long timeOfBallStart){
		if(timeOfBallStart != 0){
			setText("Time: " + (System.currentTimeMillis() - timeOfBallStart)/1000 +  "");
		}else {
			setText("Time: 0");
		}
	}

	public String getTime() {
		String[] timeSpent = getText().split("Time: ");
		return timeSpent[1];
	}
}
