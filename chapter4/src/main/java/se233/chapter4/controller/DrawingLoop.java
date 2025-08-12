package se233.chapter4.controller;
import javafx.application.Platform; // Make sure this is imported
import javafx.scene.layout.Pane;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.view.GameStage;

import java.util.List;

public class DrawingLoop implements Runnable {
    private GameStage gameStage;
    private List<GameCharacter> gameCharacterList;
    private int frameRate;
    private float interval;
    private boolean running;

    public DrawingLoop(GameStage gameStage, List<GameCharacter> gameCharacterList) {
        this.gameStage = gameStage;
        this.gameCharacterList = gameCharacterList;
        frameRate = 60; // Usually 60 FPS for smooth animation
        interval = 1000.0f / frameRate;
        running = true;
    }

    private void checkDrawCollisions(List<GameCharacter> characterList) {
        // This method is for visual collision checks or effects, not game logic updates.
        // For example, updating character position based on collisions for rendering.
        for (GameCharacter gameCharacter : characterList) {
            gameCharacter.checkReachGameWall();
            gameCharacter.checkReachHighest();
            gameCharacter.checkReachFloor();
        }
        // If you have character-to-character collision visuals, add them here.
        // Example (from previous discussions):
        // if (characterList.size() > 1) {
        //     GameCharacter cA = characterList.get(0);
        //     GameCharacter cB = characterList.get(1);
        //     if (cA.getBoundsInParent().intersects(cB.getBoundsInParent())) {
        //         // Trigger visual collision effect, but actual game logic (like health reduction)
        //         // should ideally be in GameLoop.
        //     }
        // }
    }

    // ใน DrawingLoop.java
    private void paint(List<GameCharacter> characterList) {
        // ลบบรรทัดนี้ออก เพราะไม่ได้ใช้งาน
        // Pane root = (Pane) gameStage.getScene().getRoot();

        for (GameCharacter gameCharacter : characterList) {
            gameCharacter.repaint();

            if (gameCharacter.isMovingLeft() || gameCharacter.isMovingRight()
                    || gameCharacter.isJumping() || gameCharacter.isFalling()) {
                gameCharacter.getImageView().play();
            } else {
                gameCharacter.getImageView().stop();
            }
        }
    }




    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();

            // All UI updates MUST be on the JavaFX Application Thread
            Platform.runLater(() -> {
                checkDrawCollisions(gameCharacterList); // Perform visual collision checks
                paint(gameCharacterList); // Paint/render the characters and control their animations
            });

            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupt status
                }
            } else {

                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}