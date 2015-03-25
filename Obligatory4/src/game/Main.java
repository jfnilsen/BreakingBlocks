package game;






import java.util.ArrayList;

import gameField.BlockField;
import gameObjects.BallAnimation;
import gameObjects.Racquet;
import gameObjects.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static ArrayList<Label> infoLabels = new ArrayList<>();
	public void start(Stage primaryStage) {
		
		
		Pane playingField = new Pane();
		BlockField blockField= new BlockField();
		
		Racquet racquet = new Racquet(playingField);
		Timer timer = new Timer();
		addInfoPanels(blockField);
		BallAnimation ball = new BallAnimation(playingField, blockField, racquet, timer);
		
		
		playingField.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		playingField.getChildren().add(blockField);
		playingField.getChildren().add(timer);
		playingField.getChildren().add(ball);
		playingField.getChildren().add(racquet);
		
		Scene scene = new Scene(playingField);
		primaryStage.setTitle("Breaking Blocks.");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public void addInfoPanels(BlockField blockField) {
		Label informationLabel = new Label("Click to play");
		informationLabel.setTextFill(Color.WHITE);
		informationLabel.setFont(Font.font(STYLESHEET_CASPIAN, 20));
		informationLabel.relocate(390, 400);
		blockField.getChildren().add(informationLabel);
		infoLabels.add(informationLabel);
		
		Label round1Label = new Label("Round 1: ");
		round1Label.setTextFill(Color.WHITE);
		blockField.getChildren().add(round1Label);
		round1Label.relocate(30, 525);
		round1Label.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
		infoLabels.add(round1Label);
		
		Label round1Time = new Label("0");
		round1Time.setTextFill(Color.WHITE);
		blockField.getChildren().add(round1Time);
		round1Time.relocate(30, 540);
		infoLabels.add(round1Time);
		
		Label round2Label = new Label("Round 2: ");
		round2Label.setTextFill(Color.WHITE);
		blockField.getChildren().add(round2Label);
		round2Label.relocate(90, 525);
		round2Label.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		infoLabels.add(round2Label);
		
		Label round2Time = new Label("0");
		round2Time.setTextFill(Color.WHITE);
		blockField.getChildren().add(round2Time);
		round2Time.relocate(90, 540);
		infoLabels.add(round2Time);
		
		Label round3Label = new Label("Round 3: ");
		round3Label.setTextFill(Color.WHITE);
		blockField.getChildren().add(round3Label);
		round3Label.relocate(150, 525);
		round3Label.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		infoLabels.add(round3Label);
		
		Label round3Time = new Label("0");
		round3Time.setTextFill(Color.WHITE);
		blockField.getChildren().add(round3Time);
		round3Time.relocate(150, 540);
		infoLabels.add(round3Time);
	}
	


}
