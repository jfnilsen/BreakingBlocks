package game;

import gameField.BlockField;
import gameObjects.Ball;
import gameObjects.Racquet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	public void start(Stage primaryStage) {
		
		Pane playingField = new Pane();
		BlockField blockField= new BlockField();
		playingField.getChildren().add(blockField);
		Racquet racquet = new Racquet(playingField);
		playingField.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		Ball ball = new Ball(playingField, racquet);
		playingField.getChildren().add(ball);
		playingField.getChildren().add(racquet);
		
		
		Scene scene = new Scene(playingField);
		primaryStage.setTitle("Breaking Blocks.");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	public static void main(String[] args){
		launch(args);
	}


}
