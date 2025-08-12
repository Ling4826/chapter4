package se233.chapter4.controller;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.view.GameStage;

import java.util.List;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private List<GameCharacter> gameCharacterList;
    private int frameRate;
    private float interval;
    private boolean running;

    public GameLoop(GameStage gameStage, List<GameCharacter> gameCharacterList) {
        this.gameStage = gameStage;
        this.gameCharacterList = gameCharacterList;
        frameRate = 60;
        interval = 1000.0f / frameRate;
        running = true;
    }

    private void update(List<GameCharacter> characterList) {
        for (GameCharacter gameCharacter : characterList) {
            boolean leftPressed = gameStage.getKeys().isPressed(gameCharacter.getLeftKey());
            boolean rightPressed = gameStage.getKeys().isPressed(gameCharacter.getRightKey());
            boolean upPressed = gameStage.getKeys().isPressed(gameCharacter.getUpKey());

            if (leftPressed && rightPressed) {
                gameCharacter.stop();
            } else if (leftPressed) {
                gameCharacter.moveLeft();
            } else if (rightPressed) {
                gameCharacter.moveRight();
            } else {
                gameCharacter.stop();
            }

            if (upPressed) {
                gameCharacter.jump();
            }

            gameCharacter.moveX();
            gameCharacter.moveY();
        }
    }

    private void checkCollisions(List<GameCharacter> characterList) {
        for (GameCharacter gameCharacter : characterList) {
            gameCharacter.checkReachGameWall();
            gameCharacter.checkReachHighest();
            gameCharacter.checkReachFloor();
        }
    }

    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
            update(gameCharacterList);
            checkCollisions(gameCharacterList);

            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}