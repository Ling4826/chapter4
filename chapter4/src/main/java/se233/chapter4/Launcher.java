package se233.chapter4;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.chapter4.controller.DrawingLoop;
import se233.chapter4.controller.GameLoop;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.view.GameStage;

public class Launcher extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        GameStage gameStage = new GameStage();

        GameLoop gameLoop = new GameLoop(gameStage, gameStage.getGameCharacterList());
        DrawingLoop drawingLoop = new DrawingLoop(gameStage, gameStage.getGameCharacterList());
        Scene scene = new Scene(gameStage, GameStage.WIDTH, GameStage.HEIGHT);

        scene.setOnKeyPressed(event -> {
            for (GameCharacter character : gameStage.getGameCharacterList()) {
                if (event.getCode() == character.getLeftKey() ||
                        event.getCode() == character.getRightKey() ||
                        event.getCode() == character.getUpKey()) {
                    gameStage.getKeys().add(event.getCode());
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            for (GameCharacter character : gameStage.getGameCharacterList()) {
                if (event.getCode() == character.getLeftKey() ||
                        event.getCode() == character.getRightKey() ||
                        event.getCode() == character.getUpKey()) {
                    gameStage.getKeys().remove(event.getCode());
                }
            }
        });

        primaryStage.setTitle("Mario");
        primaryStage.setScene(scene);
        primaryStage.show();

        (new Thread(gameLoop)).start();
        (new Thread(drawingLoop)).start();
    }
}