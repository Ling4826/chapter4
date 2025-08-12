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

    private Image gameStageImg;
    private ImageView backgroundImg; // ทำให้เป็น instance variable
    private List<GameCharacter> gameCharacterList;
    private Keys keys;

    public GameStage() {
        keys = new Keys();

        gameStageImg = new Image(GameStage.class.getResourceAsStream("/se233/chapter4/assets/Background.png"));
        backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);

        gameCharacterList = new ArrayList<>();

        gameCharacterList.add(new GameCharacter(
                0, 30, 30, "/se233/chapter4/assets/MarioSheet.png", 4, 4, 1, 16, 32, KeyCode.A, KeyCode.D, KeyCode.W
        ));

        // เพิ่มพื้นหลังและตัวละครทั้งหมดเข้าใน Pane
        getChildren().add(backgroundImg);
        getChildren().addAll(gameCharacterList);
    }

    // เมธอดสำหรับวาดฉากใหม่ทั้งหมด
    public void redraw() {
        // ทำให้ตัวละครทั้งหมดถูกวาดทับพื้นหลังเสมอ
        // โดยการนำตัวละครทั้งหมดมาไว้ด้านหน้าสุดของ children list
        for (GameCharacter character : gameCharacterList) {
            character.toFront();
        }
    }

    public List<GameCharacter> getGameCharacterList() { return gameCharacterList; }
    public Keys getKeys() { return keys; }
}
