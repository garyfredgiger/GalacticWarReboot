package galacticwarreboot;

import game.framework.entities.EntityImage;
import game.framework.utilities.GameUtility;

import java.awt.Font;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants
{
  // Possibly have two new power-ups increase health capacity and increase shield capacity
  public static enum PowerUpType
  {
    POWERUP_SHIELD("POWERUP_SHIELD", 0), POWERUP_HEALTH("POWERUP_HEALTH", 1), POWERUP_FIREPOWER("POWERUP_FIREPOWER", 2), POWERUP_SUPER_SHIELD("POWERUP_SUPER_SHIELD", 3), POWERUP_FULL_HEALTH("POWERUP_FULL_HEALTH", 4), POWERUP_FULL_SHIELD("POWERUP_FULL_SHIELD", 5), POWERUP_THE_BOMB("POWERUP_THE_BOMB", 6), POWERUP_THRUST("POWERUP_THRUST", 7), POWERUP_INCREASE_HEALTH_CAPACITY("POWERUP_INCREASE_HEALTH_CAPACITY", 8), POWERUP_INCREASE_SHIELD_CAPACITY("POWERUP_INCREASE_SHIELD_CAPACITY", 9), POWERUP_250("POWERUP_250", 10), POWERUP_500("POWERUP_500", 11), POWERUP_1000("POWERUP_1000", 12), UNDEFINED("UNDEFINED", 13);

    private String     type;
    private int        index;
    private static int randomIndex;

    PowerUpType(String str, int index)
    {
      this.type = str;
      this.index = index;
    }

    public String toString()
    {
      return type;
    }

    private static final List<PowerUpType> VALUES                  = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int               NUMBER_OF_POWERUP_TYPES = VALUES.size();

    public static PowerUpType randomPowerupType()
    {
      randomIndex = GameUtility.random.nextInt(NUMBER_OF_POWERUP_TYPES - 1);

      return VALUES.get(randomIndex);
    }
  }

  // TODO: I will need a power-up to attribute type mapping
  public static enum AttributeType
  {
    ATTRIBUTE_SHIELD("ATTRIBUTE_SHIELD"), ATTRIBUTE_HEALTH("ATTRIBUTE_HEALTH"), ATTRIBUTE_FIREPOWER("ATTRIBUTE_FIREPOWER"), ATTRIBUTE_SUPER_SHIELD("ATTRIBUTE_SUPER_SHIELD"), ATTRIBUTE_THE_BOMB("ATTRIBUTE_THE_BOMB"), ATTRIBUTE_THRUST("ATTRIBUTE_THRUST"), UNDEFINED("UNDEFINED");

    private String type;

    AttributeType(String str)
    {
      type = str;
    }

    public String toString()
    {
      return type;
    }
  }

  public static enum PlayerShotType
  {
    PLASMA, SUPER_SHIELD, UNDEFINED
  }

  public static enum EnemyTypes
  {
    ASTEROID_BIG("ASTEROID_BIG"), ASTEROID_MEDIUM("ASTEROID_MEDIUM"), ASTEROID_SMALL("ASTEROID_SMALL"), ASTEROID_TINY("ASTEROID_TINY"), UFO("UFO"), UFO_STRONG("UFO_STRONG"), UFO_SHORTY("UFO_SHORTY");

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

  public static enum IntroductionScreens
  {
    MAIN("MAIN"), POWERUPS("POWERUPS"), POWERUP_DETAILS("POWERUP_DETAILS"), INSTRUCTIONS("INSTRUCTIONS"), ENEMIES("ENEMIES"), CREDITS("CREDITS");

    private String label;

    IntroductionScreens(String label)
    {
      this.label = label;
    }

    public String toString()
    {
      return label;
    }
  }

  // Game name
  public static final String GAME_NAME                                        = "Galactic War Reboot";

  public static final int    NUMBER_OF_BIG_ASTEROID_IMAGES                    = 5;
  public static final int    NUMBER_OF_MEDIUM_ASTEROID_IMAGES                 = 2;
  public static final int    NUMBER_OF_SMALL_ASTEROID_IMAGES                  = 3;
  public static final int    NUMBER_OF_TINY_ASTEROID_IMAGES                   = 4;

  // Filenames of images used in the game
  public static final String FILENAME_BACKGROUND                              = "bluespace.png";

  public static final String FILENAME_BIG_ASTEROID_1                          = "asteroid1_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_2                          = "asteroid2_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_3                          = "asteroid3_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_4                          = "asteroid4_smaller.png";
  public static final String FILENAME_BIG_ASTEROID_5                          = "asteroid5_smaller.png";

  public static final String FILENAME_MEDIUM_ASTEROID_1                       = "medium1.png";
  public static final String FILENAME_MEDIUM_ASTEROID_2                       = "medium2.png";
  public static final String FILENAME_SMALL_ASTEROID_1                        = "small1.png";
  public static final String FILENAME_SMALL_ASTEROID_2                        = "small2.png";
  public static final String FILENAME_SMALL_ASTEROID_3                        = "small3.png";
  public static final String FILENAME_TINY_ASTEROID_1                         = "tiny1.png";
  public static final String FILENAME_TINY_ASTEROID_2                         = "tiny2.png";
  public static final String FILENAME_TINY_ASTEROID_3                         = "tiny3.png";
  public static final String FILENAME_TINY_ASTEROID_4                         = "tiny4.png";
  public static final String FILENAME_BAR_HEALTH                              = "bar_health.png";
  public static final String FILENAME_BAR_SHIELD                              = "bar_shield.png";

  public static final String FILENAME_BAR_FRAME_10                            = "barframe_10.png";
  public static final String FILENAME_BAR_FRAME_20                            = "barframe_20.png";
  public static final String FILENAME_BAR_FRAME_40                            = "barframe_40.png";

  public static final String FILENAME_HUD_THE_BOMB_ICON                       = "hud_radioactive.png";
  public static final String FILENAME_HUD_SUPERSHIELD_ICON                    = "hud_supershield.png";
  public static final String FILENAME_PLASMA_SHOT                             = "plasmashot.png";
  public static final String FILENAME_POWERUP_1000                            = "powerup_1000_new.png";
  public static final String FILENAME_POWERUP_500                             = "powerup_500_new.png";
  public static final String FILENAME_POWERUP_250                             = "powerup_250_new.png";
  //public static final String FILENAME_POWERUP_AUTO_SHIELD                   = "powerup_auto_shield.png";
  public static final String FILENAME_POWERUP_FULL_HEALTH                     = "powerup_cola_full_health_32x28.png";                                 // "powerup_cola_full_health_40x35.png";
  public static final String FILENAME_POWERUP_HEALTH                          = "powerup_cola.png";
  public static final String FILENAME_POWERUP_GUN                             = "powerup_gun.png";

  public static final String FILENAME_POWERUP_GUN_2                           = "powerup_gun_2.png";
  public static final String FILENAME_POWERUP_GUN_3                           = "powerup_gun_3.png";
  public static final String FILENAME_POWERUP_GUN_4                           = "powerup_gun_4.png";
  public static final String FILENAME_POWERUP_GUN_5                           = "powerup_gun_5.png";

  public static final String FILENAME_POWERUP_THE_BOMB                        = "powerup_radioactive.png";
  public static final String FILENAME_POWERUP_SHIELD_FULL_RESTORE             = "powerup_shield2_full_restore.png";
  public static final String FILENAME_POWERUP_SHIELD                          = "powerup_shield2.png";
  public static final String FILENAME_POWERUP_SUPER_SHIELD                    = "powerup_supershield.png";

  public static final String FILENAME_POWERUP_ENGINE_1                        = "thrust1.png";
  public static final String FILENAME_POWERUP_ENGINE_2                        = "thrust2.png";
  public static final String FILENAME_POWERUP_ENGINE_3                        = "thrust3.png";

  public static final String FILENAME_HUD_ENGINE_1                            = "hud_thrust1.png";
  public static final String FILENAME_HUD_ENGINE_2                            = "hud_thrust2.png";
  public static final String FILENAME_HUD_ENGINE_3                            = "hud_thrust3.png";

  public static final String FILENAME_SUPER_SHIELD                            = "supershield.png";

  public static final String FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_20     = "powerup_health_capacity_20.png";
  public static final String FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_40     = "powerup_health_capacity_40.png";
  public static final String FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_20     = "powerup_shield_capacity_20.png";
  public static final String FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_40     = "powerup_shield_capacity_40.png";

  public static final String FILENAME_SPACESHIP                               = "spaceship.png";
  public static final String FILENAME_SPACESHIP_SHIELD                        = "spaceship_shields.png";

  public static final String FILENAME_SPACESHIP_PART_COCKPIT                  = "ship_piece_cockpit.png";
  public static final String FILENAME_SPACESHIP_PART_LEFT_SIDE                = "ship_piece_left_side.png";
  public static final String FILENAME_SPACESHIP_PART_RIGHT_SIDE               = "ship_piece_right_side.png";
  //public static final String FILENAME_SPACESHIP_PART_HULL                     = "ship_piece_hull.png";
  public static final String FILENAME_SPACESHIP_PART_LEFT_HULL                = "ship_piece_left_hull.png";
  public static final String FILENAME_SPACESHIP_PART_RIGHT_HULL               = "ship_piece_right_hull.png";
  public static final String FILENAME_SPACESHIP_PART_CENTER_HULL              = "ship_piece_center_hull.png";
  public static final String FILENAME_SPACESHIP_PART_LEFT_ENGINE              = "ship_piece_left_engine.png";
  public static final String FILENAME_SPACESHIP_PART_RIGHT_ENGINE             = "ship_piece_right_engine.png";

  public static final String FILENAME_UFO_PART_BOTTOM_LEFT_HULL               = "ufo_piece_bottom_left_hull.png";
  public static final String FILENAME_UFO_PART_BOTTOM_LEFT                    = "ufo_piece_bottom_left.png";
  public static final String FILENAME_UFO_PART_BOTTOM                         = "ufo_piece_bottom.png";
  public static final String FILENAME_UFO_PART_BOTTOM_RIGHT_HULL              = "ufo_piece_bottom_right_hull.png";
  public static final String FILENAME_UFO_PART_BOTTOM_RIGHT                   = "ufo_piece_bottom_right.png";
  public static final String FILENAME_UFO_PART_CENTER_HULL                    = "ufo_piece_center_hull.png";
  public static final String FILENAME_UFO_PART_LEFT_HULL                      = "ufo_piece_left_hull.png";
  public static final String FILENAME_UFO_PART_RIGHT_HULL                     = "ufo_piece_right_hull.png";
  public static final String FILENAME_UFO_PART_TOP_LEFT_HULL                  = "ufo_piece_top_left_hull.png";
  public static final String FILENAME_UFO_PART_TOP_LEFT                       = "ufo_piece_top_left.png";
  public static final String FILENAME_UFO_PART_TOP                            = "ufo_piece_top.png";
  public static final String FILENAME_UFO_PART_TOP_RIGHT_HULL                 = "ufo_piece_top_right_hull.png";
  public static final String FILENAME_UFO_PART_TOP_RIGHT                      = "ufo_piece_top_right.png";

  public static final String FILENAME_SHORTY_PART_HULL_BOTTOM_LEFT            = "shorty_hull_bottom_left.png";
  public static final String FILENAME_SHORTY_PART_HULL_BOTTOM_RIGHT           = "shorty_hull_bottom_right.png";
  public static final String FILENAME_SHORTY_PART_HULL_LEFT                   = "shorty_hull_left.png";
  public static final String FILENAME_SHORTY_PART_HULL_RIGHT                  = "shorty_hull_right.png";
  public static final String FILENAME_SHORTY_PART_HULL_TOP_LEFT               = "shorty_hull_top_left.png";
  public static final String FILENAME_SHORTY_PART_HULL_TOP_RIGHT              = "shorty_hull_top_right.png";
  public static final String FILENAME_SHORTY_PART_BOTTOM_LEFT                 = "shorty_part_bottom_left.png";
  public static final String FILENAME_SHORTY_PART_BOTTOM_RIGHT                = "shorty_part_bottom_right.png";
  public static final String FILENAME_SHORTY_PART_TOP_LEFT                    = "shorty_part_top_left.png";
  public static final String FILENAME_SHORTY_PART_TOP_RIGHT                   = "shorty_part_top_right.png";

  public static final String FILENAME_SPACESHIP_THRUST1                       = "spaceship_thrust1.png";
  public static final String FILENAME_SPACESHIP_THRUST2                       = "spaceship_thrust2.png";
  public static final String FILENAME_SPACESHIP_THRUST3                       = "spaceship_thrust3.png";

  public static final String FILENAME_UFO                                     = "ufo.png";
  public static final String FILENAME_UFO_SHORTY                              = "ufo_shorty.png";
  public static final String FILENAME_UFO_SHIELD_WEAK                         = "ufo_shield_weak.png";
  public static final String FILENAME_UFO_SHIELD_OK                           = "ufo_shield_ok.png";
  public static final String FILENAME_UFO_SHIELD_STRONG                       = "ufo_shield_strong.png";

  public static final String FILENAME_UFO_SHOT                                = "ufoshot.png";
  public static final String FILENAME_UFO_SHORTY_SHOT                         = "ufoshot2.png";

  // Fonts used in the Introduction screen
  public static final Font   FONT_INTRO_SCREEN_MAIN_TITLE                     = new Font("Verdana", Font.BOLD, 36);
  public static final Font   FONT_INTRO_SCREEN_MAIN_CONTROLS                  = new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20);
  public static final Font   FONT_INTRO_SCREEN_MAIN_START                     = new Font("Ariel", Font.BOLD, 24);
  public static final Font   FONT_INTRO_SCREEN_MEDIUM                         = new Font("Verdana", Font.ITALIC, 20);
  public static final Font   FONT_INTRO_SCREEN_SMALL                          = new Font("Verdana", Font.BOLD, 14);

  public static final Font   FONT_INTRO_SCREEN1_CREDIT                        = new Font("Courier", Font.BOLD, 18);

  // Fonts used during game play
  public static final Font   FONT_GAME_PLAYING_HUD_SMALL                      = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_GAME_PLAYING_HUD_MEDIUM                     = new Font("Verdana", Font.BOLD, 18);
  public static final Font   FONT_GAME_PLAYING_HUD_LARGE                      = new Font("Verdana", Font.PLAIN, 32);
  public static final Font   FONT_GAME_PLAYING_HUD_LARGE1                     = new Font("Comic Sans MS", Font.BOLD, 40);

  // Fonts used in the Game Start screen
  public static final Font   FONT_GAME_START_SCREEN                           = new Font("Comic Sans MS", Font.BOLD, 48);

  // Fonts used in the Player Dead screen
  public static final Font   FONT_PLAYER_DEAD_SCREEN                          = new Font("Verdana", Font.BOLD, 40);

  // Fonts used in the Game Over screen
  public static final Font   FONT_GAME_OVER_SCREEN                            = new Font("Verdana", Font.BOLD, 64);

  // Fonts used in Next Level Screen
  public static final Font   FONT_NEXT_LEVEL_SCREEN                           = new Font("Verdana", Font.BOLD, 40);

  // Fonts used for Paused Screen
  public static final Font   FONT_PAUSED_SCREEN                               = new Font("Verdana", Font.BOLD, 64);

  // Fonts used for Powerups Screen
  public static final Font   FONT_INTRO_POWERUPS_SCREEN_SMALL                 = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_INTRO_POWERUPS_SCREEN_REGULAR               = new Font("Verdana", Font.BOLD, 18);
  public static final Font   FONT_INTRO_POWERUPS_SCREEN_MEDIUM                = new Font("Verdana", Font.BOLD, 24);
  public static final Font   FONT_INTRO_POWERUPS_SCREEN_LARGE                 = new Font("Verdana", Font.BOLD, 32);

  // Fonts used for [BLANK] Screen
  public static final Font   FONT_INTRO_INSTRUCTIONS_SCREEN_TINY              = new Font("Verdana", Font.BOLD, 12);
  public static final Font   FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL             = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR           = new Font("Verdana", Font.BOLD, 18);
  public static final Font   FONT_INTRO_INSTRUCTIONS_SCREEN_MEDIUM            = new Font("Verdana", Font.BOLD, 24);
  public static final Font   FONT_INTRO_INSTRUCTIONS_SCREEN_LARGE             = new Font("Verdana", Font.BOLD, 32);

  // Fonts used for [BLANK] Screen
  public static final Font   FONT_INTRO_CREDITS_SCREEN_TINY                   = new Font("Verdana", Font.BOLD, 12);
  public static final Font   FONT_INTRO_CREDITS_SCREEN_SMALL                  = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_INTRO_CREDITS_SCREEN_REGULAR                = new Font("Verdana", Font.ITALIC, 18);
  public static final Font   FONT_INTRO_CREDITS_SCREEN_MEDIUM                 = new Font("Verdana", Font.BOLD, 24);
  public static final Font   FONT_INTRO_CREDITS_SCREEN_LARGE                  = new Font("Verdana", Font.BOLD, 32);

  // Fonts used for [BLANK] Screen
  public static final Font   FONT_INTRO_ENEMIES_SCREEN_TINY                   = new Font("Verdana", Font.BOLD, 12);
  public static final Font   FONT_INTRO_ENEMIES_SCREEN_SMALL                  = new Font("Verdana", Font.BOLD, 14);
  public static final Font   FONT_INTRO_ENEMIES_SCREEN_REGULAR                = new Font("Verdana", Font.BOLD, 18);
  public static final Font   FONT_INTRO_ENEMIES_SCREEN_MEDIUM                 = new Font("Verdana", Font.BOLD, 24);
  public static final Font   FONT_INTRO_ENEMIES_SCREEN_LARGE                  = new Font("Verdana", Font.BOLD, 32);

  // String constants for Introduction screen
  public static final String INTRO_SCREEN_MAIN_TITLE_MSG                      = "GALACTIC WAR REBOOT";
  public static final String INTRO_SCREEN_BLURB_LINE_1                        = "Adapted from the game Galactic War";
  public static final String INTRO_SCREEN_BLURB_LINE_2                        = "from Jonathan S. Harbour's book";
  public static final String INTRO_SCREEN_BLURB_LINE_3                        = "Beginning Java Game Programming (Second Edition)";

  public static final String MSG_INTRO_SCREEN1_CREDIT                         = "Written and Developed by Gary Giger";
  public static final String MSG_INTRO_SCREEN1_COPYRIGHT                      = "\u00a9 2014";

  // String constants for power-ups screen
  public static final String MSG_POWERUPS_POWERUP_TITLE                       = "Available Powerups";
  public static final String MSG_POWERUPS_THRUSTERS                           = "Thruster Upgrades";                                                  //"Ship Thrusters";
  public static final String MSG_POWERUPS_THURST1                             = "Dual Ion Drive";                                                     // "(Level 1) Ion Drive";
  public static final String MSG_POWERUPS_THURST2                             = "Fusion Pulse";                                                       // "(Level 2) Fusion Pulse";
  public static final String MSG_POWERUPS_THURST3                             = "Plasma Core";                                                        // "(Level 3) Plasma Core";
  public static final String MSG_POWERUPS_HEALTH_SHIELD                       = "Health and Shields";
  public static final String MSG_POWERUPS_HEALTH                              = "Health";
  public static final String MSG_POWERUPS_SHIELDS                             = "Shield";
  public static final String MSG_POWERUPS_FULL_HEALTH                         = "Full Health";
  public static final String MSG_POWERUPS_FULL_SHIELD                         = "Full Shield";
  public static final String MSG_POWERUPS_SUPER_SHIELD                        = "Super Shield";
  public static final String MSG_POWERUPS_WEAPONS                             = "Armament";
  public static final String MSG_POWERUPS_FIREPOWER                           = "Firepower Upgrade";
  public static final String MSG_POWERUPS_THE_BOMB                            = "Da Bomb";
  public static final String MSG_POWERUPS_SCREEN_POINT_BONUSES                = "Point Bonuses:";

  // String messages for Instructions Screen
  public static final String MSG_INSTRUCTIONS_TITLE                           = "Game Instructions";
  public static final String MSG_INSTRUCTIONS_CONTROLS                        = "Player Controls";

  public static final String MSG_INSTRUCTIONS_ROTATE_CONTROL                  = "Rotate Ship";
  public static final String MSG_INSTRUCTIONS_FIRE_CONTROL                    = "Fire Weapon";
  public static final String MSG_INSTRUCTIONS_THRUST_CONTROL                  = "Ship Thrust";
  public static final String MSG_INSTRUCTIONS_SHIELDS_CONTROL                 = "Enable Shields";
  public static final String MSG_INSTRUCTIONS_SUPER_SHIELDS_CONTROL           = "Super Shields";
  public static final String MSG_INSTRUCTIONS_THE_BOMB_CONTROL                = "Execute Da Bomb";
  public static final String MSG_INSTRUCTIONS_PAUSE_CONTROL                   = "Pause Game";
  public static final String MSG_INSTRUCTIONS_QUIT_PLAYING_CONTROL            = "Quit Playing";

  public static final String MSG_INSTRUCTIONS_ROTATE_COMMAND                  = "LEFT/RIGHT Arrow Keys";
  public static final String MSG_INSTRUCTIONS_FIRE_COMMAND                    = "CTRL Key";
  public static final String MSG_INSTRUCTIONS_THRUST_COMMAND                  = "UP Arrow Key";
  public static final String MSG_INSTRUCTIONS_SHIELDS_COMMAND                 = "SHIFT Key";
  public static final String MSG_INSTRUCTIONS_SUPER_SHIELDS_COMMAND           = "Z Key";
  public static final String MSG_INSTRUCTIONS_THE_BOMB_COMMAND                = "X Key";
  public static final String MSG_INSTRUCTIONS_PAUSE_COMMAND                   = "P Key";
  public static final String MSG_INSTRUCTIONS_QUIT_PLAYING_COMMAND            = "Esc Key";
  public static final String MSG_INSTRUCTIONS_GAME_OBJECTIVE_LABEL            = "Objective";
  public static final String MSG_INSTRUCTIONS_GAME_OBJECTIVE                  = "Shoot Enemies, collect powerups and survive as long as you can.";

  // These are part of the power-ups details screen.
  public static final String MSG_GAME_DETAILS_TITLE                           = "Game Details, Hints and Tips";
  public static final String MSG_GAME_DETAILS_FIREPOWER_UPGRADE_USE           = "Keep collecting in order to increase ship's firepower.";
  public static final String MSG_GAME_DETAILS_MAX_FIREPOWER                   = "A total of five firepower upgrades can be collected.";
  public static final String MSG_GAME_DETAILS_SUPER_SHIELDS_USE               = "Destroys all threats in immediate vicinity around ship.";
  public static final String MSG_GAME_DETAILS_THE_BOMB_USE                    = "Generates a shockwave that rips apart all Asteroids.";
  public static final String MSG_GAME_DETAILS_ION_THRUSTER                    = "The default thruster with which your ship is equipped.";
  public static final String MSG_GAME_DETAILS_THRUSTERS                       = "Advanced thrusters will become available in higher levels.";
  public static final String MSG_GAME_DETAILS_UFOS                            = "As the game progesses, UFOs will appear in higher levels.";

  // Not displayed yet
  //public static final String MSG_GAME_DETAILS_UFOS                            = "When a UFO is destroyed, certain ship upgrades may be spawned.";

  // Credits
  public static final String MSG_CREDITS_SOUND_LIBRARY                        = "Sound Library provided by Paul Lamb";
  public static final String MSG_CREDITS_SOUND_LIBRARY_URL                    = "http://www.paulscode.com";
  public static final String MSG_CREDITS_SOUND_LIBRARY_URL_BREADCRUMB         = "(Located under Forum > General > Paul's Projects > 3D Sound System)";
  public static final String MSG_CREDITS_SOUND_EFFECTS                        = "Sound Effects Generated using SFXR by Tomas Petterson";
  public static final String MSG_CREDITS_SOUND_EFFECTS_URL                    = "http://www.drpetter.se/";
  public static final String MSG_CREDITS_SOUND_EFFECTS_URL_BREADCRUMB         = "(Located under Projects)";
  public static final String MSG_INTRODUCTION_MSG_NEXT                        = "Press Space for Next Screen";
  public static final String MSG_INTRODUCTION_MSG_BACK                        = "Press Esc for Previous Screen";

  // TODO: Mention shield and health capacity increases too in the power-ups details

  // These are part of the credits screen
  public static final String MSG_CREDITS_TITLE                                = "Credits";

  // These are port of the enemies screen
  public static final String MSG_ENEMIES_TITLE                                = "Enemies";
  public static final String MSG_ENEMIES_ASTEROID_LARGE_LABEL                 = "Large";
  public static final String MSG_ENEMIES_ASTEROID_MEDIUM_LABEL                = "Medium";
  public static final String MSG_ENEMIES_ASTEROID_SMALL_LABEL                 = "Small";
  public static final String MSG_ENEMIES_ASTEROID_TINY_LABEL                  = "Tiny";
  public static final String MSG_ENEMIES_POINTS                               = " Points";
  public static final String MSG_ENEMIES_UFO_LABEL                            = "Regular";
  public static final String MSG_ENEMIES_UFO_SHORTY_LABEL                     = "Shorty";

  // String Constants used while the game is starting
  public static final String MSG_GAME_START                                   = "Get Ready!!!";

  // String Constants used while the game is being Played
  public static final String MSG_GAME_PLAYING_HEALTH                          = "Health: ";
  public static final String MSG_GAME_PLAYING_SHIELD                          = "Shield: ";
  public static final String MSG_GAME_PLAYING_FIREPOWER                       = "Firepower: ";
  public static final String MSG_GAME_PLAYING_SCORE                           = "Score: ";
  public static final String MSG_GAME_PLAYING_WAVE                            = "Wave: ";

  public static final String MSG_GAME_PAUSED                                  = "Paused";

  // String constants used when the player is killed
  public static final String MSG_PLAYER_DEAD                                  = "Your Ship Was Destroyed!!!";

  // String constants used when next level is reached
  public static final String MSG_NEXT_LEVEL                                   = "Wave ";

  // String constants for Game Over screen
  public static final String MSG_GAMEOVER_SCREEN_GAMEOVER                     = "GAME OVER";
  //public static final String MSG_GAMEOVER_SCREEN_RESTART     = "Press ENTER to Restart Game";

  public static final int    ASTEROID_DIMENSIONS                              = 128;
  public static final int    ASTEROID_MIN_ROTATION_RATE                       = 5;
  public static final int    ASTEROID_MAX_ROTATION_RATE                       = 50;

  // TODO: Given different difficulty levels, it may be a good idea to have
  // separate velocity scales
  public static final int[]  BIG_ASTEROID_VELOCITY_SCALE                      = new int[] { 10, 15, 25, 35, 50 };
  public static final int[]  MEDIUM_ASTEROID_VELOCITY_SCALE                   = new int[] { 10, 25, 50, 60, 75, 90 };
  public static final int[]  SMALL_ASTEROID_VELOCITY_SCALE                    = new int[] { 50, 75, 100, 110, 115, 125 };
  public static final int[]  TINY_ASTEROID_VELOCITY_SCALE                     = new int[] { 50, 75, 125, 150, 175, 200 };

  // General Game Constants
  public static final int    GAME_STARTING_LEVEL                              = 0;
  public static final int    GAME_STARTING_NUMBER_OF_ASTEROIDS                = 1;
  public static final int    GAME_MAX_NUMBER_ASTEROIDS_ON_SCREEN              = 40;
  public static final long   GAME_UFO_MIN_UFO_LAUNCH_LEVEL                    = 5;                                                                    // Was 5
  public static final int    GAME_LAUNCH_SUPER_UFO_LEVEL                      = 10;                                                                   // Was 10
  public static final int    GAME_LAUNCH_SHORTY_UFO_LEVEL                     = 15;                                                                   // Was 15

  public static final String DOT_DOT_DOT                                      = "...";

  // Game state constants
  public static final int    GAME_START_INTERVAL                              = 3000;
  public static final int    PLAYER_DEAD_INTERVAL                             = 2000;
  public static final int    GAMEOVER_INTERVAL                                = 2000;
  public static final int    NEXT_LEVEL_INTERVAL                              = 3000;
  public static final int    DEFAULT_DAMAGE_AMOUNT                            = 1;
  public static final int    UFO_DAMAGE_AMOUNT                                = 4;

  // Constants used for player entity 
  public static final int    INVULNERABILITY_INTERVAL                         = 3000;

  public static final int    SHIP_INITIAL_HEALTH                              = 10;
  public static final int    SHIP_HEALTH_CAPACITY_INCREASE_TO_20              = 20;
  public static final int    SHIP_HEALTH_CAPACITY_INCREASE_TO_40              = 40;

  public static final int    SHIP_INITIAL_SHIELD                              = 10;
  public static final int    SHIP_SHIELD_CAPACITY_INCREASE_TO_20              = 20;
  public static final int    SHIP_SHIELD_CAPACITY_INCREASE_TO_40              = 40;

  public static final int    SHIP_STARTING_SUPER_SHIELD                       = 2;                                                                    // Was 2
  public static final int    SHIP_STARTING_THE_BOMBS                          = 1;                                                                    // Was 1
  public static final int    SHIP_DEFAULT_ACCELERATION                        = 1;                                                                    // Was originally 0.05, 1 seemed good. Maybe increase for better performance in the from of a powerup. 2 gives better respones and 4 provides even better stopping and starting
  public static final int    SHIP_INCREASED_ACCELERATION_2                    = 2;
  public static final int    SHIP_INCREASED_ACCELERATION_3                    = 3;

  public static final double SHIP_MAX_VELOCITY                                = 200;                                                                  // Was originally 5
  public static final double SHIP_MIN_VELOCITY                                = -SHIP_MAX_VELOCITY;
  public static final int    PLAYER_ROTATION_RATE                             = 200;
  public static final int    PLAYER_BULLET_SPEED                              = 200;
  public static final double PLAYER_BULLET_LIFE_SPAN_IN_SECS                  = 1.25;                                                                 // Was 2
  public static final int    PLAYER_NUMBER_SUPER_SHIELD_BALLS                 = 18;
  public static final int    PLAYER_SUPER_SHIELD_SPEED                        = 100;
  public static final double PLAYER_SUPER_SHIELD_LIFESPAN                     = 1.25;
  public static final int    PLAYER_DEFAULT_AUTO_SHIELD_SETTING               = 0;
  public static final long   PLAYER_TIME_BETWEEN_REGISTERING_UFO_COLLISIONS   = 2500;                                                                 // This constant will limit the number of collision detections that cause damage to the player when colliding with the super UFO.

  // Powerup constants
  public static final int    POWERUP_ROTAITON_RATE                            = 50;
  public static final int    POWERUP_SPEED                                    = 40;

  public static final int    LEVEL_TO_USE_SPAWN_PROBABILITY_FOR_HIGHER_LEVELS = 10;
  public static final int    POWERUP_SPAWN_PROBABILITY_FOR_LOWER_LEVELS       = 3;
  public static final int    POWERUP_SPAWN_PROBABILITY_FOR_HIGHER_LEVELS      = 2;                                                                    // Was 1

  public static final int    POWERUP_TOTAL_EVENTS_TO_SPAWN                    = 100;
  public static final int    POWERUP_LIFE_SPAN_IN_SECS                        = 20;
  public static final int    POWERUP_THE_BOMB_TIMER                           = 1000;

  public static final int    POWERUP_FIREPOWER_VALUE                          = 1;
  public static final int    POWERUP_250_VALUE                                = 250;
  public static final int    POWERUP_500_VALUE                                = 500;
  public static final int    POWERUP_1000_VALUE                               = 1000;
  public static final int    POWERUP_SHIELD_VALUE                             = 5;
  public static final int    POWERUP_HEALTH_VALUE                             = 5;
  public static final int    POWERUP_SUPER_SHIELD_VALUE                       = 1;
  public static final int    POWERUP_THE_BOMB_VALUE                           = 1;

  public static final int    POWERUP_THRUST_2_MIN_LEVEL                       = 5;
  public static final int    POWERUP_THRUST_3_MIN_LEVEL                       = 15;

  public static final int    HEALTH_CAPACITY_INCREASE_TO_20_SCORE_LIMIT       = 25000;                                                                // Was 15000
  public static final int    HEALTH_CAPACITY_INCREASE_TO_40_SCORE_LIMIT       = 100000;                                                               // Was 75000

  public static final int    SHIELD_CAPACITY_INCREASE_TO_20_SCORE_LIMIT       = 15000;                                                                // Was 10000 Possibly think about increasing this to 15K or 25K                           
  public static final int    SHIELD_CAPACITY_INCREASE_TO_40_SCORE_LIMIT       = 75000;                                                                // Was 50000

  // UFO Constants
  public static final int    UFO_SPEED                                        = 150;                                                                  // Was 150
  public static final int    UFO_SHOT_INTERVAL                                = 750;
  public static final int    UFO_SUPER_SHOT_INTERVAL                          = 500;
  public static final int    UFO_SHORTY_SHOT_INTERVAL                         = 250;
  public static final int    UFO_SHOT_SPEED                                   = 175;
  public static final int    UFO_SHORTY_SHOT_SPEED                            = 300;
  public static final double UFO_BULLET_LIFE_SPAN_IN_SECS                     = 3;
  public static final double UFO_SHORTY_BULLET_LIFE_SPAN_IN_SECS              = 1.5;
  public static final int    UFO_SPAWN_PROBABILITY                            = 50;
  public static final int    UFO_TOTAL_EVENTS_TO_SPAWN                        = 10000;
  public static final long   UFO_MIN_TIME_BETWEEN_LAUNCHES                    = 5000;                                                                 // Was 10000
  public static final long   UFO_TIME_BETWEEN_SHIELD_HITS                     = 750;

  public static final long   UFO_SHORTY_TIME_BETWEEN_HITS                     = 50;
  public static final int    UFO_SHORTY_HIT_POINTS                            = 10;

  public static final int    UFO_MOVING_RIGHT_MIN_ANGLE_IN_DEGREES            = 25;
  public static final int    UFO_MOVING_RIGHT_MAX_ANGLE_IN_DEGREES            = 155;
  public static final int    UFO_MOVING_LEFT_MIN_ANGLE_IN_DEGREES             = 205;
  public static final int    UFO_MOVING_LEFT_MAX_ANGLE_IN_DEGREES             = 335;

  public static final int    SHIP_STARTING_FIREPOWER                          = 2;
  public static final int    SHIP_MAX_FIREPOWER                               = 5;
  public static final int    SHIP_MIN_FIREPOWER                               = 1;

  // Asteroid Point Values
  public static final int    SCORE_BIG_ASTEROIDS                              = 50;
  public static final int    SCORE_MEDIUM_ASTEROIDS                           = 30;
  public static final int    SCORE_SMALL_ASTEROIDS                            = 20;
  public static final int    SCORE_TINY_ASTEROIDS                             = 10;

  // Debug constants
  public static final Font   FONT_DEBUG                                       = new Font("Dialog", Font.PLAIN, 14);
  public static final String DEBUG_MSG_THRUST_ON                              = "Thrust: ";
  public static final String DEBUG_MSG_FIRE                                   = "Fire: ";
  public static final String DEBUG_MSG_SHIELD                                 = "Shield: ";
  public static final String DEBUG_MSG_PREV_SHIELD                            = "Prev Shield: ";
  public static final String DEBUG_MSG_UFO_PROB                               = "UFO Prob: ";
  public static final String DEBUG_MSG_THRUST_VALUE                           = "Thrust Value: ";
  public static final String DEBUG_MSG_SPAWN_PROBABILITY                      = "Spawn Probability: ";
  public static final String DEBUG_MSG_PLAYER_HEALTH_CAPACITY                 = "Health Capacity: ";
  public static final String DEBUG_MSG_PLAYER_SHIELD_CAPACITY                 = "Shield Capacity: ";
}
