package galacticwarreboot;

import game.framework.entities.EntityImage;

import java.awt.Font;

public class Constants
{
  public static enum PowerUpType
  {
    POWERUP_SHIELD, POWERUP_HEALTH, POWERUP_FIREPOWER, POWERUP_SUPER_SHIELD, POWERUP_FULL_HEALTH, POWERUP_FULL_SHIELD, POWERUP_THE_BOMB, POWERUP_THRUST, POWERUP_250, POWERUP_500, POWERUP_1000, UNDEFINED
  }

  // TODO: I will need a power-up to attribute type mapping
  public static enum AttributeType
  {
    ATTRIBUTE_SHIELD, ATTRIBUTE_HEALTH, ATTRIBUTE_FIREPOWER, ATTRIBUTE_SUPER_SHIELD, ATTRIBUTE_THE_BOMB, ATTRIBUTE_THRUST, UNDEFINED
  }

  // TODO: Should there be a attribute type?

  public static enum PlayerShotType
  {
    PLASMA, SUPER_SHIELD, UNDEFINED
  }

  public static enum EnemyTypes
  {
    ASTEROID_BIG("ASTEROID_BIG"), ASTEROID_MEDIUM("ASTEROID_MEDIUM"), ASTEROID_SMALL("ASTEROID_SMALL"), ASTEROID_TINY("ASTEROID_TINY"), UFO("UFO"), UFO_SHORTY("UFO_SHORTY");

    private String label;

    EnemyTypes(String label)
    {
      this.label = label;
    }

    public String toString()
    {
      return label;
    }
  }

  // Game name
  public static final String GAME_NAME                                      = "Galactic War Reboot";

  // Filenames of images used in the game
  public static final String FILENAME_BACKGROUND                            = "bluespace.png";
  //  public static final String FILENAME_BIG_ASTEROID_1                        = "asteroid1.png";
  //  public static final String FILENAME_BIG_ASTEROID_2                        = "asteroid2.png";
  //  public static final String FILENAME_BIG_ASTEROID_3                        = "asteroid3.png";
  //  public static final String FILENAME_BIG_ASTEROID_4                        = "asteroid4.png";
  //  public static final String FILENAME_BIG_ASTEROID_5                        = "asteroid5.png";

  public static final String FILENAME_BIG_ASTEROID_1                        = "asteroid1_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_2                        = "asteroid2_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_3                        = "asteroid3_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_4                        = "asteroid4_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_5                        = "asteroid5_smaller.png";

  public static final String FILENAME_MEDIUM_ASTEROID_1                     = "medium1.png";
  public static final String FILENAME_MEDIUM_ASTEROID_2                     = "medium2.png";
  public static final String FILENAME_SMALL_ASTEROID_1                      = "small1.png";
  public static final String FILENAME_SMALL_ASTEROID_2                      = "small2.png";
  public static final String FILENAME_SMALL_ASTEROID_3                      = "small3.png";
  public static final String FILENAME_TINY_ASTEROID_1                       = "tiny1.png";
  public static final String FILENAME_TINY_ASTEROID_2                       = "tiny2.png";
  public static final String FILENAME_TINY_ASTEROID_3                       = "tiny3.png";
  public static final String FILENAME_TINY_ASTEROID_4                       = "tiny4.png";
  public static final String FILENAME_BAR_HEALTH                            = "bar_health.png";
  public static final String FILENAME_BAR_SHIELD                            = "bar_shield.png";

  public static final String FILENAME_BAR_FRAME_10                          = "barframe_10.png";
  public static final String FILENAME_BAR_FRAME_20                          = "barframe_20.png";
  public static final String FILENAME_BAR_FRAME_40                          = "barframe_40.png";

  public static final String FILENAME_HUD_THE_BOMB_ICON                     = "hud_radioactive.png";
  public static final String FILENAME_HUD_SUPERSHIELD_ICON                  = "hud_supershield.png";
  public static final String FILENAME_PLASMA_SHOT                           = "plasmashot.png";
  public static final String FILENAME_POWERUP_1000                          = "powerup_1000.png";
  public static final String FILENAME_POWERUP_500                           = "powerup_500.png";
  public static final String FILENAME_POWERUP_250                           = "powerup_250.png";
  public static final String FILENAME_POWERUP_AUTO_SHIELD                   = "powerup_auto_shield.png";
  public static final String FILENAME_POWERUP_FULL_HEALTH                   = "powerup_cola_full_health_40x35.png";
  public static final String FILENAME_POWERUP_HEALTH                        = "powerup_cola.png";
  public static final String FILENAME_POWERUP_GUN                           = "powerup_gun.png";

  public static final String FILENAME_POWERUP_GUN_2                         = "powerup_gun_2.png";
  public static final String FILENAME_POWERUP_GUN_3                         = "powerup_gun_3.png";
  public static final String FILENAME_POWERUP_GUN_4                         = "powerup_gun_4.png";
  public static final String FILENAME_POWERUP_GUN_5                         = "powerup_gun_5.png";

  public static final String FILENAME_POWERUP_THE_BOMB                      = "powerup_radioactive.png";
  public static final String FILENAME_POWERUP_SHIELD_FULL_RESTORE           = "powerup_shield2_full_restore.png";
  public static final String FILENAME_POWERUP_SHIELD                        = "powerup_shield2.png";
  public static final String FILENAME_POWERUP_SUPER_SHIELD                  = "powerup_supershield.png";
  public static final String FILENAME_POWERUP_ENGINE_2                      = "thrust2.png";
  public static final String FILENAME_SUPER_SHIELD                          = "supershield.png";

  public static final String FILENAME_SPACESHIP                             = "spaceship.png";
  public static final String FILENAME_SPACESHIP_SHIELD                      = "spaceship_shields.png";
  public static final String FILENAME_SPACESHIP_THRUST1                     = "spaceship_thrust1.png";
  public static final String FILENAME_SPACESHIP_THRUST2                     = "spaceship_thrust2.png";

  public static final String FILENAME_UFO                                   = "ufo.png";
  public static final String FILENAME_UFO_SHORTY                            = "ufo_shorty.png";
  public static final String FILENAME_UFO_SHIELD_WEAK                       = "ufo_shield_weak.png";
  public static final String FILENAME_UFO_SHIELD_OK                         = "ufo_shield_ok.png";
  public static final String FILENAME_UFO_SHIELD_STRONG                     = "ufo_shield_strong.png";

  public static final String FILENAME_UFO_SHOT                              = "ufoshot.png";
  public static final String FILENAME_UFO_SHORTY_SHOT                       = "ufoshot2.png";

  // Indices for the spaceship image array
  public static final int    IMAGE_SPACESHIP_INDEX                          = 0;
  public static final int    IMAGE_SPACESHIP_SHIELD_INDEX                   = 1;
  public static final int    IMAGE_SPACESHIP_THRUST1_INDEX                  = 2;
  public static final int    IMAGE_SPACESHIP_THRUST2_INDEX                  = 3;

  // Fonts used in the Introduction screen
  public static final Font   FONT_INTRO_SCREEN_MAIN_TITLE                   = new Font("Verdana", Font.BOLD, 36);
  public static final Font   FONT_INTRO_SCREEN_MAIN_CONTROLS                = new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20);
  public static final Font   FONT_INTRO_SCREEN_MAIN_START                   = new Font("Ariel", Font.BOLD, 24);

  // Fonts used during game play
  public static final Font   FONT_GAME_PLAYING_HUD_SMALL                    = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_GAME_PLAYING_HUD_MEDIUM                   = new Font("Verdana", Font.BOLD, 18);
  public static final Font   FONT_GAME_PLAYING_HUD_LARGE                    = new Font("Verdana", Font.PLAIN, 32);
  public static final Font   FONT_GAME_PLAYING_HUD_LARGE1                   = new Font("Comic Sans MS", Font.BOLD, 40);

  // Fonts used in the Game Start screen
  public static final Font   FONT_GAME_START_SCREEN                         = new Font("Comic Sans MS", Font.BOLD, 48);

  // Fonts used in the Player Dead screen
  public static final Font   FONT_PLAYER_DEAD_SCREEN                        = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in the Game Over screen
  public static final Font   FONT_GAME_OVER_SCREEN                          = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in Next Level Screen
  public static final Font   FONT_NEXT_LEVEL_SCREEN                         = new Font("Verdana", Font.BOLD, 40);

  // String constants for Introduction screen
  public static final String INTRO_SCREEN_MAIN_TITLE_MSG                    = "GALACTIC WAR REBOOT";
  public static final String INTRO_SCREEN_MAIN_CONTROLS_MSG                 = "CONTROLS:";
  public static final String INTRO_SCREEN_MAIN_ROTATE_MSG                   = "ROTATE Left/Right Arrows Keys";
  public static final String INTRO_SCREEN_MAIN_THRUST_MSG                   = "THRUST Up Arrow Key";
  public static final String INTRO_SCREEN_MAIN_SHIELD_MSG                   = "SHIELD Shift Key";
  public static final String INTRO_SCREEN_MAIN_FIRE_MSG                     = "FIRE - Ctrl Key";
  public static final String INTRO_SCREEN_MAIN_POWERUPS_MSG                 = "POWERUPS INCREASE FIREPOWER!";
  public static final String INTRO_SCREEN_MAIN_START_MSG                    = "Press ENTER to Start Game";

  // String Constants used while the game is starting
  public static final String MSG_GAME_START                                 = "Get Ready!!!";

  // String Constants used while the game is being Played
  public static final String MSG_GAME_PLAYING_HEALTH                        = "Health: ";
  public static final String MSG_GAME_PLAYING_SHIELD                        = "Shield: ";
  public static final String MSG_GAME_PLAYING_FIREPOWER                     = "Firepower: ";
  public static final String MSG_GAME_PLAYING_SCORE                         = "Score: ";
  public static final String MSG_GAME_PLAYING_WAVE                          = "Wave: ";

  // String constants used when the player is killed
  public static final String MSG_PLAYER_DEAD                                = "Your Ship Was Destroyed!!!";

  // String constants used when next level is reached
  public static final String MSG_NEXT_LEVEL                                 = "Wave ";

  // String constants for Game Over screen
  public static final String MSG_GAMEOVER_SCREEN_GAMEOVER                   = "GAME OVER";
  //public static final String MSG_GAMEOVER_SCREEN_RESTART     = "Press ENTER to Restart Game";

  // General Game Constants
  public static final int    STARTING_LEVEL                                 = 0;
  public static final int    STARTING_NUMBER_OF_ASTEROIDS                   = 15;                                                      // Was 1
  public static final int    MAX_NUMBER_ASTEROIDS_ON_SCREEN                 = 20;

  public static final String DOT_DOT_DOT                                    = "...";

  // Game state constants
  public static final int    GAME_START_INTERVAL                            = 3000;
  public static final int    PLAYER_DEAD_INTERVAL                           = 2000;
  public static final int    GAMEOVER_INTERVAL                              = 2000;
  public static final int    NEXT_LEVEL_INTERVAL                            = 3000;
  public static final int    DEFAULT_DAMAGE_AMOUNT                          = 1;
  public static final int    UFO_DAMAGE_AMOUNT                              = 4;
  public static final int    GAME_LAUNCH_SUPER_UFO_LEVEL                    = 10;                                                      // Was 10
  public static final int    GAME_LAUNCH_SHORTY_UFO_LEVEL                   = 15;                                                      // Was 15

  // Constants used for player entity 
  public static final int    INVULNERABILITY_INTERVAL                       = 3000;
  public static final int    SHIP_STARTING_HEALTH                           = 40;
  public static final int    SHIP_STARTING_SHIELD                           = 40;
  public static final int    SHIP_STARTING_SUPER_SHIELD                     = 2;                                                       // Was 2
  public static final int    SHIP_STARTING_THE_BOMBS                        = 0;                                                       // Was O
  public static final int    SHIP_DEFAULT_ACCELERATION                      = 1;                                                       // Was originally 0.05, 1 seemed good. Maybe increase for better performance in the from of a powerup. 2 gives better respones and 4 provides even better stopping and starting
  public static final int    SHIP_INCREASED_ACCELERATION                    = 2;
  public static final double SHIP_MAX_VELOCITY                              = 200;                                                     // Was originally 5
  public static final double SHIP_MIN_VELOCITY                              = -SHIP_MAX_VELOCITY;
  public static final int    PLAYER_ROTATION_RATE                           = 200;
  public static final int    PLAYER_BULLET_SPEED                            = 200;
  public static final double PLAYER_BULLET_LIFE_SPAN_IN_SECS                = 2;
  public static final int    PLAYER_NUMBER_SUPER_SHIELD_BALLS               = 18;
  public static final int    PLAYER_SUPER_SHIELD_SPEED                      = 100;
  public static final double PLAYER_SUPER_SHIELD_LIFESPAN                   = 1.25;
  public static final int    PLAYER_DEFAULT_AUTO_SHIELD_SETTING             = 0;
  public static final long   PLAYER_TIME_BETWEEN_REGISTERING_UFO_COLLISIONS = 1500;                                                    // This constant will limit the number of collision detections that cause damage to the player when colliding with the super UFO.

  // Powerup constants
  public static final int    POWERUP_ROTAITON_RATE                          = 50;
  public static final int    POWERUP_SPEED                                  = 40;
  public static final int    POWERUP_SPAWN_PROBABILITY                      = 3;                                                       // Was 3, the 50 is for testing                                                   // NOTE: Was 5                                                   // This is a 5 in 100 chance or 1/20th chance.
  public static final int    POWERUP_TOTAL_EVENTS_TO_SPAWN                  = 100;
  public static final int    POWERUP_LIFE_SPAN_IN_SECS                      = 20;
  public static final int    POWERUP_THE_BOMB_TIMER                         = 1000;

  public static final int    POWERUP_FIREPOWER_VALUE                        = 1;
  public static final int    POWERUP_250_VALUE                              = 250;
  public static final int    POWERUP_500_VALUE                              = 500;
  public static final int    POWERUP_1000_VALUE                             = 1000;
  public static final int    POWERUP_SHIELD_VALUE                           = 5;
  public static final int    POWERUP_HEALTH_VALUE                           = 5;
  public static final int    POWERUP_SUPER_SHIELD_VALUE                     = 1;
  public static final int    POWERUP_THE_BOMB_VALUE                         = 1;

  public static final int    POWERUP_INCREASE_HEALTH_TO_20_LIMIT            = 50000;
  public static final int    POWERUP_INCREASE_HEALTH_TO_40_LIMIT            = 200000;
  
  // UFO Constants
  public static final int    UFO_SPEED                                      = 150;
  public static final int    UFO_SHOT_INTERVAL                              = 750;
  public static final int    UFO_SUPER_SHOT_INTERVAL                        = 500;
  public static final int    UFO_SHORTY_SHOT_INTERVAL                       = 250;
  public static final int    UFO_SHOT_SPEED                                 = 175;
  public static final int    UFO_SHORTY_SHOT_SPEED                          = 300;
  public static final double UFO_BULLET_LIFE_SPAN_IN_SECS                   = 3;
  public static final double UFO_SHORTY_BULLET_LIFE_SPAN_IN_SECS            = 1.5;
  public static final int    UFO_SPAWN_PROBABILITY                          = 50;
  public static final int    UFO_TOTAL_EVENTS_TO_SPAWN                      = 10000;
  public static final long   UFO_MIN_TIME_BETWEEN_LAUNCHES                  = 10000;
  public static final long   UFO_MIN_UFO_LAUNCH_LEVEL                       = 5;                                                       // Was 5
  public static final long   UFO_TIME_BETWEEN_SHIELD_HITS                   = 750;

  public static final long   UFO_SHORTY_TIME_BETWEEN_HITS                   = 50;
  public static final long   UFO_SHORTY_HIT_POINTS                          = 10;

  public static final int    SHIP_STARTING_FIREPOWER                        = 2;
  public static final int    SHIP_MAX_FIREPOWER                             = 5;
  public static final int    SHIP_MIN_FIREPOWER                             = 1;

  // Asteroid Point Values
  public static final int    SCORE_BIG_ASTEROIDS                            = 50;
  public static final int    SCORE_MEDIUM_ASTEROIDS                         = 30;
  public static final int    SCORE_SMALL_ASTEROIDS                          = 20;
  public static final int    SCORE_TINY_ASTEROIDS                           = 10;

  // Debug constants
  public static final Font   FONT_DEBUG                                     = new Font("Dialog", Font.PLAIN, 14);
  public static final String DEBUG_MSG_THRUST_ON                            = "Thrust: ";
  public static final String DEBUG_MSG_FIRE                                 = "Fire: ";
  public static final String DEBUG_MSG_SHIELD                               = "Shield: ";
  public static final String DEBUG_MSG_PREV_SHIELD                          = "Prev Shield: ";
  public static final String DEBUG_MSG_UFO_PROB                             = "UFO Prob: ";
  public static final String DEBUG_MSG_THRUST_VALUE                         = "Thrust Value: ";
}
