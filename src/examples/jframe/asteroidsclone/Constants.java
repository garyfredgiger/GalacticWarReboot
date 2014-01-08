package examples.jframe.asteroidsclone;

import java.awt.Font;

public class Constants
{
  public static enum PowerUpType
  {
    POWERUP_SHIELD, POWERUP_HEALTH, POWERUP_GUN, POWERUP_250, POWERUP_500, POWERUP_1000, UNDEFINED
  }

  public static final String GAME_NAME                       = "Galactic War Reboot";

  // Fonts used in the Introduction screen
  public static final Font   FONT_INTRO_SCREEN_MAIN_TITLE    = new Font("Verdana", Font.BOLD, 36);
  public static final Font   FONT_INTRO_SCREEN_MAIN_CONTROLS = new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20);
  public static final Font   FONT_INTRO_SCREEN_MAIN_START    = new Font("Ariel", Font.BOLD, 24);

  // Fonts used during game play
  public static final Font   FONT_GAME_PLAYING_HUD_SMALL     = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_GAME_PLAYING_HUD_MEDIUM    = new Font("Verdana", Font.BOLD, 18);
  public static final Font   FONT_GAME_PLAYING_HUD_LARGE     = new Font("Verdana", Font.PLAIN, 32);
  
  // Fonts used in the Game Start screen
  public static final Font   FONT_GAME_START_SCREEN          = new Font("Comic Sans MS", Font.BOLD, 48);

  // Fonts used in the Player Dead screen
  public static final Font   FONT_PLAYER_DEAD_SCREEN         = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in the Game Over screen
  public static final Font   FONT_GAME_OVER_SCREEN           = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in Next Level Screen
  public static final Font   FONT_NEXT_LEVEL_SCREEN          = new Font("Verdana", Font.BOLD, 40);

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
  public static final String MSG_GAME_PLAYING_SHIELD         = "Shield: ";
  public static final String MSG_GAME_PLAYING_FIREPOWER      = "Firepower: ";
  public static final String MSG_GAME_PLAYING_SCORE          = "Score: ";
  public static final String MSG_GAME_PLAYING_WAVE           = "Wave: ";

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

  // Constants used for player entity 
  public static final int    INVULNERABILITY_INTERVAL        = 3000;
  public static final int    SHIP_STARTING_HEALTH            = 40;
  public static final int    SHIP_STARTING_SHIELD            = 40;
  public static final double SHIP_ACCELERATION               = 1;                                                       // Was originally 0.05
  public static final double SHIP_MAX_VELOCITY               = 200;                                                     // Was originally 5
  public static final double SHIP_MIN_VELOCITY               = -SHIP_MAX_VELOCITY;
  public static final int    PLAYER_ROTATION_RATE            = 200;
  public static final int    PLAYER_BULLET_SPEED             = 200;
  public static final double PLAYER_BULLET_LIFE_SPAN_IN_SECS = 2.5;

  // Powerup constants
  public static final int    POWERUP_ROTAITON_RATE           = 50;
  public static final int    POWERUP_SPEED                   = 40;
  public static final int    POWERUP_SHIELD_VALUE            = 5;
  public static final int    POWERUP_HEALTH_VALUE            = 5;
  public static final int    POWERUP_SPAWN_PROBABILITY       = 5;                                                       // This is a 5 in 100 chance or 1/20th chance.
  public static final int    POWERUP_TOTAL_EVENTS_TO_SPAWN   = 100;
  public static final int    POWERUP_LIFE_SPAN_IN_SECS       = 15;
  public static final int    POWERUP_250_VALUE               = 250;
  public static final int    POWERUP_500_VALUE               = 5000;
  public static final int    POWERUP_1000_VALUE               = 1000;

  public static final int    SHIP_STARTING_FIREPOWER         = 5;
  public static final int    SHIP_MAX_FIREPOWER              = 5;
  public static final int    SHIP_MIN_FIREPOWER              = 1;

  // Asteroid Point Values
  public static final int    SCORE_BIG_ASTEROIDS             = 50;
  public static final int    SCORE_MEDIUM_ASTEROIDS          = 30;
  public static final int    SCORE_SMALL_ASTEROIDS           = 20;
  public static final int    SCORE_TINY_ASTEROIDS            = 10;

  // Debug constants
  public static final Font   FONT_DEBUG                      = new Font("Dialog", Font.PLAIN, 14);
  public static final String DEBUG_MSG_THRUST                = "Thrust: ";
  public static final String DEBUG_MSG_FIRE                  = "Fire: ";
  public static final String DEBUG_MSG_SHIELD                = "Shield: ";
  public static final String DEBUG_MSG_PREV_SHIELD           = "Prev Shield: ";
}