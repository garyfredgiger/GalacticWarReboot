package examples.jframe.asteroidsclone;

import java.awt.Font;

public class Constants
{
  // Fonts used in the Introduction screen
  public static final Font   FONT_INTRO_SCREEN_MAIN_TITLE    = new Font("Verdana", Font.BOLD, 36);
  public static final Font   FONT_INTRO_SCREEN_MAIN_CONTROLS = new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20);
  public static final Font   FONT_INTRO_SCREEN_MAIN_START    = new Font("Ariel", Font.BOLD, 24);

  // Fonts used during game play
  public static final Font   FONT_GAME_PLAYING_HEALTH        = new Font("Verdana", Font.BOLD, 14);

  // Fonts used in the Game Start screen
  public static final Font   FONT_GAME_START_SCREEN          = new Font("Comic Sans MS", Font.BOLD, 68);

  // Fonts used in the Player Dead screen
  public static final Font   FONT_PLAYER_DEAD_SCREEN         = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in the Game Over screen
  public static final Font   FONT_GAME_OVER_SCREEN           = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in Next Level Screen
  public static final Font   FONT_NEXT_LEVEL_SCREEN          = new Font("Verdana", Font.BOLD, 28);

  // String constants for Introduction screen
  public static final String INTRO_SCREEN_MAIN_TITLE_MSG     = "GALACTIC WAR REBOOT";
  public static final String INTRO_SCREEN_MAIN_CONTROLS_MSG  = "CONTROLS:";
  public static final String INTRO_SCREEN_MAIN_ROTATE_MSG    = "ROTATE Left/Right Arrows Keys";
  public static final String INTRO_SCREEN_MAIN_THRUST_MSG    = "THRUST Up Arrow Key";
  public static final String INTRO_SCREEN_MAIN_SHIELD_MSG    = "SHIELD Shift Key";
  public static final String INTRO_SCREEN_MAIN_FIRE_MSG      = "FIRE - Ctrl Key";
  public static final String INTRO_SCREEN_MAIN_POWERUPS_MSG  = "POWERUPS INCREASE FIREPOWER!";
  public static final String INTRO_SCREEN_MAIN_START_MSG     = "Press ENTER to Start Game";

  // String Constants used while the game is starting
  public static final String MSG_GAME_START                  = "Get Ready!!!";

  // String Constants used while the game is being Played
  public static final String MSG_GAME_PLAYING_HEALTH         = "Health: ";

  // String constants used when the player is killed
  public static final String MSG_PLAYER_DEAD                 = "Your Ship Was Destroyed!!!";

  // String constants used when next level is reached
  public static final String MSG_NEXT_LEVEL                  = "Wave ";

  // String constants for Game Over screen
  public static final String MSG_GAMEOVER_SCREEN_GAMEOVER    = "GAME OVER";
  //public static final String MSG_GAMEOVER_SCREEN_RESTART     = "Press ENTER to Restart Game";

  // General Game Constants
  public static final int    STARTING_LEVEL                  = 0;
  public static final int    STARTING_NUMBER_OF_ASTEROIDS    = 1;
  public static final int    MAX_NUMBER_ASTEROIDS_ON_SCREEN  = 15;

  // Game state constants
  public static final int    GAME_START_INTERVAL             = 3000;
  public static final int    PLAYER_DEAD_INTERVAL            = 2000;
  public static final int    GAMEOVER_INTERVAL               = 2000;
  public static final int    NEXT_LEVEL_INTERVAL             = 3000;

  // Player entity constants
  public static final int    INVULNERABILITY_INTERVAL        = 3000;
  public static final int    PLAYER_STARTING_HEALTH          = 40;
}
