package se233.chapter4.model;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter4.view.GameStage;
import se233.chapter4.model.AnimatedSprite;

import java.io.File;

public class GameCharacter extends Pane {
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);

    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;

    private int id;
    private Image gameCharacterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private int startX;
    private int startY;

    // ตัวแปรตำแหน่งและสถานะ
    private volatile boolean isMoveLeft = false;
    private volatile boolean isMoveRight = false;
    private volatile boolean isFalling = true;
    private volatile boolean canJump = false;
    private volatile boolean isJumping = false;

    // ตัวแปรทางฟิสิกส์
    private volatile int xVelocity = 0;
    private volatile int yVelocity = 0;
    // ... และตัวแปรอื่นๆ ที่มีการใช้งานร่วมกันระหว่างเทรด

    public int highestJump = 100;
    public int xAcceleration = 1;
    public int yAcceleration = 1;
    public int xMaxVelocity = 7;
    public int yMaxVelocity = 17;

    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;

    public GameCharacter(int id, int x, int y, String imgName, int count, int column, int row, int width, int height, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;

        this.gameCharacterImg = new Image(
                getClass().getResource(imgName).toExternalForm()
        );

        this.imageView = new AnimatedSprite(
                new Image(getClass().getResource(imgName).toExternalForm()),
                count, column, row,
                0, 0, width, height
        );
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.getChildren().add(this.imageView);
    }

    public int getCharacterId() { return id; }

    public void moveX() {
        if (isMoveLeft) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x = x - xVelocity;
        }
        if (isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x = x + xVelocity;
        }

    }

    public void moveY() {
        if (isFalling) {
            yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
            y = y + yVelocity;
        } else if (isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity;
        }

    }

    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }

    public void checkReachHighest() {
        if (isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }

    public void checkReachFloor() {
        if (isFalling && y >= GameStage.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
            y = GameStage.GROUND - CHARACTER_HEIGHT; // Ensure character lands exactly on the ground
        }
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x + CHARACTER_WIDTH >= GameStage.WIDTH) { // Use CHARACTER_WIDTH here
            x = GameStage.WIDTH - CHARACTER_WIDTH;
        }
    }

    public void stop() {
        xVelocity = 0;
        isMoveLeft = false;
        isMoveRight = false;
    }

    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft = true;
        isMoveRight = false;
    }

    public void moveRight() {
        setScaleX(1); // Normal sprite orientation
        isMoveLeft = false;
        isMoveRight = true;
    }

    public void repaint() {
        setTranslateX(x);
        setTranslateY(y);
    }

    // --- Getters for GameLoop and other classes ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCharacterWidth() { return CHARACTER_WIDTH; }
    public int getCharacterHeight() { return CHARACTER_HEIGHT; }
    public KeyCode getLeftKey() { return leftKey; }
    public KeyCode getRightKey() { return rightKey; }
    public KeyCode getUpKey() { return upKey; }
    public AnimatedSprite getImageView() { return imageView; }

    public boolean isMovingLeft() { return isMoveLeft; }
    public boolean isMovingRight() { return isMoveRight; }
    public boolean isFalling() { return isFalling; }
    public boolean isJumping() { return isJumping; }
    public boolean canJump() { return canJump; }


}