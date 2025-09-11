package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.model.Keys;
import java.util.ArrayList;
import java.util.List;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private List<GameCharacter> gameCharacterList;
    private Keys keys;
    private List<Score> scoreList;

    public GameStage() {
        keys = new Keys();
        scoreList = new ArrayList<>();
        gameCharacterList = new ArrayList<>();
        Image gameStageImg = new Image(getClass().getResourceAsStream("/se233/chapter4/assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);

        gameCharacterList.add(new GameCharacter(
                1,
                30, 30, KeyCode.A, KeyCode.D, KeyCode.W,
                "/se233/chapter4/assets/MarioSheet.png",
                4, 4, 1, 16, 32, 32, 64, 7, 17,
                0
        ));


        gameCharacterList.add(new GameCharacter(
                2,
                WIDTH - 100, 30, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP,
                "/se233/chapter4/assets/rockman.png",
                10, 5, 2, 48, 48, 80, 80, 8, 16,
                15
        ));


        scoreList.add(new Score(30, GROUND + 30));
        scoreList.add(new Score(WIDTH - 100, GROUND + 30));

        getChildren().add(backgroundImg);
        getChildren().addAll(gameCharacterList);
        getChildren().addAll(scoreList);
    }

    public List<GameCharacter> getGameCharacterList() { return gameCharacterList; }
    public Keys getKeys() { return keys; }
    public List<Score> getScoreList() { return scoreList; }
}