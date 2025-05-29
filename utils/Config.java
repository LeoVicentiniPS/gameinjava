package utils;

import java.awt.*;

public class Config {
    //Janela
    public static final int WINDOW_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int WINDOW_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final String GAME_TITLE = "Java Wizard";

    //Player
    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 50;
    public static final int PLAYER_SPEED = 5;
    public static final int PLAYER_START_X = WINDOW_WIDTH / 2 - PLAYER_WIDTH / 2;
    public static final int PLAYER_START_Y = WINDOW_HEIGHT / 2 - PLAYER_HEIGHT / 2;

    //Projetil
    public static final double PROJECTILE_COOLDOWN = 0.3;
    public static final int PROJECTILE_SPEED = 10;
    public static final int PROJECTILE_SIZE = 10;
    public static final Color PROJECTILE_COLOR = Color.RED;

    //Inimigo
    public static final int ENEMY_WIDTH = 30;
    public static final int ENEMY_HEIGHT = 30;
    public static final int ENEMY_SPEED = 4;
    public static final double ENEMY_COOLDOWN = 0.6;
}