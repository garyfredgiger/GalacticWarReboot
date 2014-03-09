package galacticwarreboot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import galacticwarreboot.Constants.AttributeType;
import galacticwarreboot.entities.PlayerEntity;
import game.framework.entities.StaticImage;
import game.framework.entities.text.StaticText;
import game.framework.utilities.GameUtility;

public class ScreenManager
{
  private ImageObserver imageObserver;
  private int           screenWidth, screenHeight;

  private StaticText    msgGameStartScreen;
  private StaticText    msgGameOverScreen;
  private StaticText    msgNextLevelScreen;
  private StaticText    msgPlayerDeadScreen;
  private StaticText    msgHUDHealthBar;
  private StaticText    msgHUDShieldBar;
  private StaticText    msgHUDFirepower;
  private StaticText    msgHUDCurrentLevel;
  private StaticText    msgHUDScore;
  private StaticText    msgHUDNumberOfSuperShields;
  private StaticText    msgHUDNumberOfTheBombs;
  private StaticText    msgPowerupScreenTitle;
  private StaticText    msgInstructionsScreenTitle;
  private StaticText    msgInstructionsPlayerControls;
  private StaticText    msgPowerupDetailsScreen;
  private StaticText    msgEnemiesTitle;
  private StaticText    msgCreditsTitle;
  private StaticText    msgEnemiesAsteroidLabel;
  private StaticText    msgEnemiesUFOLabel;
  private StaticText    msgGameTitle;
  private StaticText    msgPaused;

  // Static text for the power-ups screen
  private StaticText    msgIntroductionPowerupScreenThrusterLabel;
  private StaticText    msgIntroductionPowerupScreenLabelThurster1;
  private StaticText    msgIntroductionPowerupScreenLabelThruster2;
  private StaticText    msgIntroductionPowerupScreenLabelThruster3;

  private StaticText    msgIntroductionPowerupScreenHealthAndShieldLabel;
  private StaticText    msgIntroductionPowerupScreenLabelHealth;
  private StaticText    msgIntroductionPowerupScreenLabelShield;
  private StaticText    msgIntroductionPowerupScreenLabelFullHealth;
  private StaticText    msgIntroductionPowerupScreenLabelFullShield;
  private StaticText    msgIntroductionPowerupScreenLabelSuperShield;

  private StaticText    msgIntroductionPowerupScreenArmamentsLabel;
  private StaticText    msgIntroductionPowerupScreenLabelFirepower;
  private StaticText    msgIntroductionPowerupScreenLabelTheBomb;

  private StaticText    msgIntroductionPowerupScreenLabel250Bonus;
  private StaticText    msgIntroductionPowerupScreenLabel500Bonus;
  private StaticText    msgIntroductionPowerupScreenLabel1000Bonus;

  private StaticText    msgInstructionsRotateControl;
  private StaticText    msgInstructionsThrustControl;
  private StaticText    msgInstructionsShieldsControl;
  private StaticText    msgInstructionsFireControl;
  private StaticText    msgInstructionsSuperShieldsControl;
  private StaticText    msgInstructionsTheBombControl;

  private StaticText    msgInstructionsRotateCommand;
  private StaticText    msgInstructionsThrustCommand;
  private StaticText    msgInstructionsShieldsCommand;
  private StaticText    msgInstructionsFireCommand;
  private StaticText    msgInstructionsSuperShieldsCommand;
  private StaticText    msgInstructionsTheBombCommand;

  private StaticText    msgGameDetailsFirepowerLabel;
  private StaticText    msgGameDetailsSuperShieldLabel;
  private StaticText    msgGameDetailsTheBombLabel;
  private StaticText    msgGameDetailsThrust1Label;

  private StaticText    msgGameDetailsFirepowerUse;
  private StaticText    msgGameDetailsMaxFirepower;
  private StaticText    msgGameDetailsSuperShieldUse;
  private StaticText    msgGameDetailsTheBombUse;
  private StaticText    msgGameDetailsIonThrusterUse;
  private StaticText    msgGameDetailsThrustersUse;

  private StaticText    msgCreditSoundLibrary;
  private StaticText    msgCreditSoundLibraryURL;
  private StaticText    msgCreditSoundLibraryBreadcrumb;

  private StaticText    msgCreditSoundEffects;
  private StaticText    msgCreditSoundEffectsURL;
  private StaticText    msgCreditSoundEffectsBreadcrumb;
  private StaticText    msgCreditGoBack;

  private StaticText    msgInstructionsGameObjectiveLabel;
  private StaticText    msgInstructionsGameObjective;

  /*
   * Static Image Variables
   */
  private StaticImage   imgIntroductionEnemiesScreenAsteroidLarge;
  private StaticImage   imgIntroductionEnemiesScreenAsteroidMedium;
  private StaticImage   imgIntroductionEnemiesScreenAsteroidSmall;
  private StaticImage   imgIntroductionEnemiesScreenAsteroidTiny;

  private StaticImage   imgIntroductionPowerupScreenThruster1;
  private StaticImage   imgIntroductionPowerupScreenThruster2;
  private StaticImage   imgIntroductionPowerupScreenThruster3;

  private StaticImage   imgIntroductionPowerupScreenHealth;
  private StaticImage   imgIntroductionPowerupScreenShield;
  private StaticImage   imgIntroductionPowerupScreenFullHealth;
  private StaticImage   imgIntroductionPowerupScreenFullShield;
  private StaticImage   imgIntroductionPowerupScreenSuperShield;

  private StaticImage   imgIntroductionPowerupScreenFirepower;
  private StaticImage   imgIntroductionPowerupScreenTheBomb;

  private StaticImage   imgIntroductionEnemiesScreenUFO;
  private StaticImage   imgIntroductionEnemiesScreenUFOShorty;

  //  private StaticImage   imgIntroductionPowerupScreen250Bonus;
  //  private StaticImage   imgIntroductionPowerupScreen500Bonus;
  //  private StaticImage   imgIntroductionPowerupScreen1000Bonus;

  // Variables used for creating the enemies screen in the game introduction
  int                   startingPositionXOfAsteroidListing;
  int                   startingPositionXOfUFOListing;
  int                   bigAsteroidWidth;
  int                   bufferBetweenAsteroid = 64;                      // Predefined number

  PlayerEntity          playerReference       = null;

  public ScreenManager(ImageObserver io, int screenWidth, int screenHeight, PlayerEntity player)
  {
    this.imageObserver = io;
    this.playerReference = player;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;

    msgGameStartScreen = new StaticText(Constants.MSG_GAME_START, Color.YELLOW, Constants.FONT_GAME_START_SCREEN, screenWidth, screenHeight);
    msgGameOverScreen = new StaticText(Constants.MSG_GAMEOVER_SCREEN_GAMEOVER, Color.YELLOW, Constants.FONT_GAME_OVER_SCREEN, screenWidth, screenHeight);
    msgNextLevelScreen = new StaticText("", Color.YELLOW, Constants.FONT_GAME_OVER_SCREEN, screenWidth, screenHeight);
    msgPlayerDeadScreen = new StaticText(Constants.MSG_PLAYER_DEAD, Color.WHITE, Constants.FONT_PLAYER_DEAD_SCREEN, screenWidth, screenHeight);
    msgHUDHealthBar = new StaticText(Constants.MSG_GAME_PLAYING_HEALTH, (screenWidth - 284), 30, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_SMALL, screenWidth, screenHeight);
    msgHUDShieldBar = new StaticText(Constants.MSG_GAME_PLAYING_SHIELD, (screenWidth - 282), 47, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_SMALL, screenWidth, screenHeight);
    msgHUDFirepower = new StaticText(Constants.MSG_GAME_PLAYING_FIREPOWER, 20, 40, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDCurrentLevel = new StaticText("", 20, 65, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDNumberOfSuperShields = new StaticText("", 50, 103, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDNumberOfTheBombs = new StaticText("", 50, 133, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);

    msgPaused = new StaticText(Constants.MSG_GAME_PAUSED, Color.WHITE, Constants.FONT_PAUSED_SCREEN, screenWidth, screenHeight);

    msgGameTitle = new StaticText(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, -1, 64, Color.RED, Constants.FONT_INTRO_SCREEN_MAIN_TITLE, screenWidth, screenHeight);
    msgGameTitle.centerHorizontally();

    // NOTE: We will center the score horizontally, this is why there is a -1 for the x value
    msgHUDScore = new StaticText(Constants.MSG_GAME_PLAYING_SCORE, -1, 40, Color.WHITE, Constants.FONT_GAME_PLAYING_HUD_MEDIUM, screenWidth, screenHeight);
    msgHUDScore.setAdditionalOffsetHorizontal(-40);
    msgHUDScore.centerHorizontally();

    msgPowerupScreenTitle = new StaticText(Constants.MSG_POWERUPS_POWERUP_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_LARGE, screenWidth, screenHeight);
    msgPowerupScreenTitle.centerHorizontally();

    msgPowerupDetailsScreen = new StaticText(Constants.MSG_GAME_DETAILS_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_LARGE, screenWidth, screenHeight);
    msgPowerupDetailsScreen.centerHorizontally();

    msgEnemiesTitle = new StaticText(Constants.MSG_ENEMIES_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_ENEMIES_SCREEN_LARGE, screenWidth, screenHeight);
    msgEnemiesTitle.centerHorizontally();

    msgCreditsTitle = new StaticText(Constants.MSG_CREDITS_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_CREDITS_SCREEN_LARGE, screenWidth, screenHeight);
    msgCreditsTitle.centerHorizontally();

    msgEnemiesAsteroidLabel = new StaticText("The Asteroids", -1, 192, Color.WHITE, Constants.FONT_INTRO_ENEMIES_SCREEN_REGULAR, screenWidth, screenHeight);
    msgEnemiesAsteroidLabel.centerHorizontally();

    msgEnemiesUFOLabel = new StaticText("The UFOs", -1, 400, Color.WHITE, Constants.FONT_INTRO_ENEMIES_SCREEN_REGULAR, screenWidth, screenHeight);
    msgEnemiesUFOLabel.centerHorizontally();

    msgCreditSoundLibrary = new StaticText(Constants.MSG_CREDITS_SOUND_LIBRARY, -1, 256, Color.WHITE, Constants.FONT_INTRO_CREDITS_SCREEN_MEDIUM, screenWidth, screenHeight);
    msgCreditSoundLibraryURL = new StaticText(Constants.MSG_CREDITS_SOUND_LIBRARY_URL, -1, 288, Color.YELLOW, Constants.FONT_INTRO_CREDITS_SCREEN_REGULAR, screenWidth, screenHeight);
    msgCreditSoundLibraryBreadcrumb = new StaticText(Constants.MSG_CREDITS_SOUND_LIBRARY_URL_BREADCRUMB, -1, 320, Color.YELLOW, Constants.FONT_INTRO_CREDITS_SCREEN_REGULAR, screenWidth, screenHeight);

    msgCreditSoundEffects = new StaticText(Constants.MSG_CREDITS_SOUND_EFFECTS, -1, 400, Color.WHITE, Constants.FONT_INTRO_CREDITS_SCREEN_MEDIUM, screenWidth, screenHeight);
    msgCreditSoundEffectsURL = new StaticText(Constants.MSG_CREDITS_SOUND_EFFECTS_URL, -1, 432, Color.YELLOW, Constants.FONT_INTRO_CREDITS_SCREEN_REGULAR, screenWidth, screenHeight);
    msgCreditSoundEffectsBreadcrumb = new StaticText(Constants.MSG_CREDITS_SOUND_EFFECTS_URL_BREADCRUMB, -1, 464, Color.YELLOW, Constants.FONT_INTRO_CREDITS_SCREEN_REGULAR, screenWidth, screenHeight);
    msgCreditGoBack = new StaticText(Constants.MSG_INTRODUCTION_MSG_BACK, -1, 576, Color.YELLOW, Constants.FONT_INTRO_CREDITS_SCREEN_SMALL, screenWidth, screenHeight);

    msgCreditSoundLibrary.centerHorizontally();
    msgCreditSoundLibraryURL.centerHorizontally();
    msgCreditSoundLibraryBreadcrumb.centerHorizontally();
    msgCreditSoundEffects.centerHorizontally();
    msgCreditSoundEffectsURL.centerHorizontally();
    msgCreditSoundEffectsBreadcrumb.centerHorizontally();
    msgCreditGoBack.centerHorizontally();

    setupIntroductionEnemiesScreen();
    setupIntroductionPowerupsScreen();
    setupIntroductionInstructionsScreen();
    setupIntroductionGameDetailsScreen();
  }

  // TODO: Finish this method.
  // Display for the introduction screen
  public void displayIntroductionMainScreen(Graphics2D g)
  {
    Rectangle2D bounds;

    g.setFont(Constants.FONT_INTRO_SCREEN_MEDIUM);
    g.setColor(Color.YELLOW);

    bounds = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_BLURB_LINE_1, g);
    int startingYPosition = 144;
    g.drawString(Constants.INTRO_SCREEN_BLURB_LINE_1, (int) ((screenWidth - bounds.getWidth()) / 2), startingYPosition);
    startingYPosition += 32;

    bounds = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_BLURB_LINE_2, g);
    g.drawString(Constants.INTRO_SCREEN_BLURB_LINE_2, (int) ((screenWidth - bounds.getWidth()) / 2), startingYPosition);
    startingYPosition += 32;

    bounds = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_BLURB_LINE_3, g);
    g.drawString(Constants.INTRO_SCREEN_BLURB_LINE_3, (int) ((screenWidth - bounds.getWidth()) / 2), startingYPosition);

    g.setColor(Color.WHITE);
    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_START);

    bounds = g.getFontMetrics().getStringBounds("Press 'Space' for Game Instructions", g);
    startingYPosition = 288;
    g.drawString("Press 'Space' for Game Instructions", (int) ((screenWidth - bounds.getWidth()) / 2), startingYPosition);
    startingYPosition += 32;

    bounds = g.getFontMetrics().getStringBounds("Press 'C' for Game Credits", g);
    g.drawString("Press 'C' for Game Credits", (int) ((screenWidth - bounds.getWidth()) / 2), startingYPosition);
    startingYPosition += 32;

    bounds = g.getFontMetrics().getStringBounds("Press 'Enter' to begin game", g);
    g.drawString("Press 'Enter' to begin game", (int) ((screenWidth - bounds.getWidth()) / 2), startingYPosition);

    // Display the credits and copyright 
    g.setFont(Constants.FONT_INTRO_SCREEN1_CREDIT);
    g.setColor(Color.YELLOW);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_INTRO_SCREEN1_CREDIT + " " + Constants.MSG_INTRO_SCREEN1_COPYRIGHT, g);
    g.drawString(Constants.MSG_INTRO_SCREEN1_CREDIT + " " + Constants.MSG_INTRO_SCREEN1_COPYRIGHT, (int) ((screenWidth - bounds.getWidth()) / 2), 544);
  }

  private void setupIntroductionInstructionsScreen()
  {
    int controlUpperLeftCornerPositionX = 192;  // Was 192
    int controlUpperLeftCornerPositionY = 304;  // Was 240

    int commandUpperLeftCornerPositionX = 448;
    int commandUpperLeftCornerPositionY = 304;  // Was 240

    msgInstructionsScreenTitle = new StaticText(Constants.MSG_INSTRUCTIONS_TITLE, -1, 150, Color.WHITE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_LARGE, screenWidth, screenHeight);
    msgInstructionsScreenTitle.centerHorizontally();

    msgInstructionsGameObjectiveLabel = new StaticText(Constants.MSG_INSTRUCTIONS_GAME_OBJECTIVE_LABEL, -1, 208, Color.WHITE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_MEDIUM, screenWidth, screenHeight);
    msgInstructionsGameObjectiveLabel.centerHorizontally();

    msgInstructionsGameObjective = new StaticText(Constants.MSG_INSTRUCTIONS_GAME_OBJECTIVE, -1, 240, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    msgInstructionsGameObjective.centerHorizontally();

    msgInstructionsPlayerControls = new StaticText(Constants.MSG_INSTRUCTIONS_CONTROLS, -1, controlUpperLeftCornerPositionY, Color.WHITE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_MEDIUM, screenWidth, screenHeight);
    msgInstructionsPlayerControls.centerHorizontally();

    controlUpperLeftCornerPositionY += 32;
    msgInstructionsRotateControl = new StaticText(Constants.MSG_INSTRUCTIONS_ROTATE_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    controlUpperLeftCornerPositionY += 24;
    msgInstructionsThrustControl = new StaticText(Constants.MSG_INSTRUCTIONS_THRUST_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    controlUpperLeftCornerPositionY += 24;
    msgInstructionsShieldsControl = new StaticText(Constants.MSG_INSTRUCTIONS_SHIELDS_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    controlUpperLeftCornerPositionY += 24;
    msgInstructionsFireControl = new StaticText(Constants.MSG_INSTRUCTIONS_FIRE_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    controlUpperLeftCornerPositionY += 24;
    msgInstructionsSuperShieldsControl = new StaticText(Constants.MSG_INSTRUCTIONS_SUPER_SHIELDS_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    controlUpperLeftCornerPositionY += 24;
    msgInstructionsTheBombControl = new StaticText(Constants.MSG_INSTRUCTIONS_THE_BOMB_CONTROL, controlUpperLeftCornerPositionX, controlUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);

    commandUpperLeftCornerPositionY += 32;
    msgInstructionsRotateCommand = new StaticText(Constants.MSG_INSTRUCTIONS_ROTATE_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    commandUpperLeftCornerPositionY += 24;
    msgInstructionsThrustCommand = new StaticText(Constants.MSG_INSTRUCTIONS_THRUST_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    commandUpperLeftCornerPositionY += 24;
    msgInstructionsShieldsCommand = new StaticText(Constants.MSG_INSTRUCTIONS_SHIELDS_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    commandUpperLeftCornerPositionY += 24;
    msgInstructionsFireCommand = new StaticText(Constants.MSG_INSTRUCTIONS_FIRE_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    commandUpperLeftCornerPositionY += 24;
    msgInstructionsSuperShieldsCommand = new StaticText(Constants.MSG_INSTRUCTIONS_SUPER_SHIELDS_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
    commandUpperLeftCornerPositionY += 24;
    msgInstructionsTheBombCommand = new StaticText(Constants.MSG_INSTRUCTIONS_THE_BOMB_COMMAND, commandUpperLeftCornerPositionX, commandUpperLeftCornerPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_REGULAR, screenWidth, screenHeight);
  }

  public void displayInstructions(Graphics2D g)
  {
    msgInstructionsScreenTitle.draw(g);

    msgInstructionsGameObjectiveLabel.draw(g);
    msgInstructionsGameObjective.draw(g);

    msgInstructionsPlayerControls.draw(g);

    msgInstructionsRotateControl.draw(g);
    msgInstructionsThrustControl.draw(g);
    msgInstructionsShieldsControl.draw(g);
    msgInstructionsFireControl.draw(g);
    msgInstructionsSuperShieldsControl.draw(g);
    msgInstructionsTheBombControl.draw(g);

    msgInstructionsRotateCommand.draw(g);
    msgInstructionsThrustCommand.draw(g);
    msgInstructionsShieldsCommand.draw(g);
    msgInstructionsFireCommand.draw(g);
    msgInstructionsSuperShieldsCommand.draw(g);
    msgInstructionsTheBombCommand.draw(g);
  }

  private void setupIntroductionPowerupsScreen()
  {
    int bufferVerticalBetweenText = 48;
    int imageOffset = -24;
    int startingYPositionY = 192;

    int thrusterPowerupsUpperLeftCornerPositionX = 64;
    int healthShieldPowerupsUpperLeftCornerPositionX = 336;
    int weaponryPowerupsUpperLeftCornerPositionX = 556;
    int imageWidth = ImageManager.getImage(Constants.FILENAME_SPACESHIP_THRUST1).getWidth(imageObserver); // Most images are 32 wide

    /*
     * Initialize the static text and images for the thruster upgrades    
     */
    msgIntroductionPowerupScreenThrusterLabel = new StaticText(Constants.MSG_POWERUPS_THRUSTERS, thrusterPowerupsUpperLeftCornerPositionX, startingYPositionY, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_REGULAR, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenThruster1 = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_1), thrusterPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelThurster1 = new StaticText(Constants.MSG_POWERUPS_THURST1, thrusterPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenThruster2 = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_2), thrusterPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelThruster2 = new StaticText(Constants.MSG_POWERUPS_THURST2, thrusterPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);;
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenThruster3 = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_3), thrusterPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelThruster3 = new StaticText(Constants.MSG_POWERUPS_THURST3, thrusterPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);

    /*
     *  Initialize the static text and images for the health and shield power-ups 
     */
    startingYPositionY = 192;
    msgIntroductionPowerupScreenHealthAndShieldLabel = new StaticText(Constants.MSG_POWERUPS_HEALTH_SHIELD, healthShieldPowerupsUpperLeftCornerPositionX - 32, startingYPositionY, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_REGULAR, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenHealth = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_HEALTH), healthShieldPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelHealth = new StaticText(Constants.MSG_POWERUPS_HEALTH, healthShieldPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenShield = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SHIELD), healthShieldPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelShield = new StaticText(Constants.MSG_POWERUPS_SHIELDS, healthShieldPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenFullHealth = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_FULL_HEALTH), healthShieldPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelFullHealth = new StaticText(Constants.MSG_POWERUPS_FULL_HEALTH, healthShieldPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenFullShield = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SHIELD_FULL_RESTORE), healthShieldPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelFullShield = new StaticText(Constants.MSG_POWERUPS_FULL_SHIELD, healthShieldPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenSuperShield = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SUPER_SHIELD), healthShieldPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelSuperShield = new StaticText(Constants.MSG_POWERUPS_SUPER_SHIELD, healthShieldPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    /*
     * Initialize the static text and images for the armament power-ups 
     */
    startingYPositionY = 192;
    msgIntroductionPowerupScreenArmamentsLabel = new StaticText(Constants.MSG_POWERUPS_WEAPONS, weaponryPowerupsUpperLeftCornerPositionX, startingYPositionY, Color.WHITE, Constants.FONT_INTRO_POWERUPS_SCREEN_REGULAR, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenFirepower = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_GUN), weaponryPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelFirepower = new StaticText(Constants.MSG_POWERUPS_FIREPOWER, weaponryPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
    startingYPositionY += bufferVerticalBetweenText;

    imgIntroductionPowerupScreenTheBomb = new StaticImage(ImageManager.getImage(Constants.FILENAME_POWERUP_THE_BOMB), weaponryPowerupsUpperLeftCornerPositionX, startingYPositionY + imageOffset, false, imageObserver);
    msgIntroductionPowerupScreenLabelTheBomb = new StaticText(Constants.MSG_POWERUPS_THE_BOMB, weaponryPowerupsUpperLeftCornerPositionX + imageWidth, startingYPositionY, Color.ORANGE, Constants.FONT_INTRO_POWERUPS_SCREEN_SMALL, screenWidth, screenHeight);
  }

  /**
   * Display the power-ups screen
   * 
   * @param g
   */
  public void displayIntroductionPowerupsScreen(Graphics2D g)
  {
    msgPowerupScreenTitle.draw(g);

    int regularBufferBetweenImages = 16;

    // Vars for the point bonuses
    int pointBonusesPositionY = 496;
    int pointBonusesPositionX;
    int powerupImageWidth = ImageManager.getImage(Constants.FILENAME_POWERUP_250).getWidth(imageObserver);
    int powerupImageHeight = ImageManager.getImage(Constants.FILENAME_POWERUP_250).getHeight(imageObserver);
    int pointBonusTextLength, pointBonusTextHeight;

    /*
     * Display the ship thruster power-ups
     */
    msgIntroductionPowerupScreenThrusterLabel.draw(g);
    imgIntroductionPowerupScreenThruster1.draw(g);
    msgIntroductionPowerupScreenLabelThurster1.draw(g);
    imgIntroductionPowerupScreenThruster2.draw(g);
    msgIntroductionPowerupScreenLabelThruster2.draw(g);
    imgIntroductionPowerupScreenThruster3.draw(g);
    msgIntroductionPowerupScreenLabelThruster3.draw(g);

    /*
     * Display health and shield power-ups
     */
    msgIntroductionPowerupScreenHealthAndShieldLabel.draw(g);
    imgIntroductionPowerupScreenHealth.draw(g);
    imgIntroductionPowerupScreenHealth.draw(g);
    msgIntroductionPowerupScreenLabelHealth.draw(g);
    imgIntroductionPowerupScreenShield.draw(g);
    msgIntroductionPowerupScreenLabelShield.draw(g);
    imgIntroductionPowerupScreenFullHealth.draw(g);
    msgIntroductionPowerupScreenLabelFullHealth.draw(g);
    imgIntroductionPowerupScreenFullShield.draw(g);
    msgIntroductionPowerupScreenLabelFullShield.draw(g);
    imgIntroductionPowerupScreenSuperShield.draw(g);
    msgIntroductionPowerupScreenLabelSuperShield.draw(g);

    /*
     * Display weapons
     */
    msgIntroductionPowerupScreenArmamentsLabel.draw(g);
    imgIntroductionPowerupScreenFirepower.draw(g);
    msgIntroductionPowerupScreenLabelFirepower.draw(g);
    imgIntroductionPowerupScreenTheBomb.draw(g);
    msgIntroductionPowerupScreenLabelTheBomb.draw(g);

    /*
     * Display point bonus power-ups at the bottom of the screen        
     */
    // NOTE: Since the string bounds is used in these computations, the power-up images and text will not be replaced with static images and text since
    //       the bounds cannot be known without the g object. Also note that there are other ways to compute the length of the text, but these approaches
    //       do not produce accurate results according to some of the posts on various blogs. The bottom line, until this becomes a bottle neck, this code
    //       below will probably remain this way.
    g.setFont(Constants.FONT_INTRO_POWERUPS_SCREEN_MEDIUM);
    Rectangle2D bounds = g.getFontMetrics().getStringBounds(Constants.MSG_POWERUPS_SCREEN_POINT_BONUSES, g);
    pointBonusTextLength = (int) bounds.getWidth();
    pointBonusTextHeight = (int) bounds.getHeight();

    // Compute the starting x position of the text 
    pointBonusesPositionX = (screenWidth - pointBonusTextLength - 3 * regularBufferBetweenImages - 3 * powerupImageWidth) / 2;
    g.drawString(Constants.MSG_POWERUPS_SCREEN_POINT_BONUSES, pointBonusesPositionX, (int) (pointBonusesPositionY + pointBonusTextHeight / 4));

    // Draw point bonus icons after the point bonus text
    pointBonusesPositionX += pointBonusTextLength + regularBufferBetweenImages;  // Compute the x position of the first power image
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_250), pointBonusesPositionX, pointBonusesPositionY - powerupImageHeight / 2, this.imageObserver);

    pointBonusesPositionX += powerupImageWidth + regularBufferBetweenImages;     // Compute the x position of the second power image
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_500), pointBonusesPositionX, pointBonusesPositionY - powerupImageHeight / 2, this.imageObserver);

    pointBonusesPositionX += powerupImageWidth + regularBufferBetweenImages;     // Compute the x position of the third power image
    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_1000), pointBonusesPositionX, pointBonusesPositionY - powerupImageHeight / 2, this.imageObserver);
  }

  private void setupIntroductionGameDetailsScreen()
  {
    int upgradeMsgUpperLeftPositionX = 112;
    int upgradeMsgUpperLeftPositionY = 208;

    int spacingBetweenLines = 16;
    int spacingBetweenSections = 40;

    int usageUpperLeftCornerX = 320;
    int usageUpperLeftCornerY = 208;

    msgGameDetailsFirepowerLabel = new StaticText(Constants.MSG_POWERUPS_FIREPOWER, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections + spacingBetweenLines;
    msgGameDetailsSuperShieldLabel = new StaticText(Constants.MSG_POWERUPS_SUPER_SHIELD, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections;
    msgGameDetailsTheBombLabel = new StaticText(Constants.MSG_POWERUPS_THE_BOMB, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections;
    msgGameDetailsThrust1Label = new StaticText(Constants.MSG_POWERUPS_THURST1, upgradeMsgUpperLeftPositionX, upgradeMsgUpperLeftPositionY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    upgradeMsgUpperLeftPositionY += spacingBetweenSections;

    msgGameDetailsFirepowerUse = new StaticText(Constants.MSG_GAME_DETAILS_FIREPOWER_UPGRADE_USE, usageUpperLeftCornerX, usageUpperLeftCornerY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    usageUpperLeftCornerY += spacingBetweenLines;
    msgGameDetailsMaxFirepower = new StaticText(Constants.MSG_GAME_DETAILS_MAX_FIREPOWER, usageUpperLeftCornerX, usageUpperLeftCornerY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    usageUpperLeftCornerY += spacingBetweenSections;
    msgGameDetailsSuperShieldUse = new StaticText(Constants.MSG_GAME_DETAILS_SUPER_SHIELDS_USE, usageUpperLeftCornerX, usageUpperLeftCornerY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    usageUpperLeftCornerY += spacingBetweenSections;
    msgGameDetailsTheBombUse = new StaticText(Constants.MSG_GAME_DETAILS_THE_BOMB_USE, usageUpperLeftCornerX, usageUpperLeftCornerY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    usageUpperLeftCornerY += spacingBetweenSections;
    msgGameDetailsIonThrusterUse = new StaticText(Constants.MSG_GAME_DETAILS_ION_THRUSTER, usageUpperLeftCornerX, usageUpperLeftCornerY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
    usageUpperLeftCornerY += spacingBetweenLines;
    msgGameDetailsThrustersUse = new StaticText(Constants.MSG_GAME_DETAILS_THRUSTERS, usageUpperLeftCornerX, usageUpperLeftCornerY, Color.ORANGE, Constants.FONT_INTRO_INSTRUCTIONS_SCREEN_SMALL, screenWidth, screenHeight);
  }

  /**
   * Displays the details of specific power-ups to better inform the player.
   * 
   * @param g - The graphics object passed from the draw method.
   */
  public void displayIntroductionGameDetailsScreen(Graphics2D g)
  {
    msgPowerupDetailsScreen.draw(g);

    // Display some more details about player controls and power-ups
    int imageStartingPositionX = 64;  // Was 32
    int imageStartingPositionY = 188;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_GUN), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40 + 16;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_SUPER_SHIELD), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_THE_BOMB), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40;

    g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_1), imageStartingPositionX, imageStartingPositionY, this.imageObserver);
    imageStartingPositionY += 40;

    msgGameDetailsFirepowerLabel.draw(g);
    msgGameDetailsSuperShieldLabel.draw(g);
    msgGameDetailsTheBombLabel.draw(g);
    msgGameDetailsThrust1Label.draw(g);

    msgGameDetailsFirepowerUse.draw(g);
    msgGameDetailsMaxFirepower.draw(g);
    msgGameDetailsSuperShieldUse.draw(g);
    msgGameDetailsTheBombUse.draw(g);
    msgGameDetailsIonThrusterUse.draw(g);
    msgGameDetailsThrustersUse.draw(g);
  }

  // TODO: Finish this method
  public void displayCreditsScreen(Graphics2D g)
  {
    msgCreditsTitle.draw(g);

    msgCreditSoundLibrary.draw(g);
    msgCreditSoundLibraryURL.draw(g);
    msgCreditSoundLibraryBreadcrumb.draw(g);
    msgCreditSoundEffects.draw(g);
    msgCreditSoundEffectsURL.draw(g);
    msgCreditSoundEffectsBreadcrumb.draw(g);

    msgCreditGoBack.draw(g);
  }

  // TODO: Possibly place all methods responsible for drawing the screen content into some type of screen/display manager
  /**
   * Initialize the images used in the enemies screen from the introduction. Since all of this can be computed
   * before hand, it is computed here once and the content is used to create instances of the static image class
   * one for each asteroid type.
   */
  private void setupIntroductionEnemiesScreen()
  {
    int startingPositionYOfAsteroidListing = 208;

    bigAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_BIG_ASTEROID_1);  // Non local variable since it is needed in other methods for the enemies screen in the introduction
    int mediumAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_MEDIUM_ASTEROID_1);
    int smallAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_SMALL_ASTEROID_1);
    int tinyAsteroidWidth = ImageManager.getWidth(Constants.FILENAME_TINY_ASTEROID_1);

    int bigAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_BIG_ASTEROID_1);
    int mediumAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_MEDIUM_ASTEROID_1);
    int smallAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_SMALL_ASTEROID_1);
    int tinyAsteroidHeight = ImageManager.getHeight(Constants.FILENAME_TINY_ASTEROID_1);

    // (Number of Asteroid Types) x (Width of Largest Asteroids) + ((Number of Asteroid Types) - 1) x (Buffer Between Each Asteroid)
    int widthOfAsteroidListing = 4 * bigAsteroidWidth + (3) * bufferBetweenAsteroid;
    startingPositionXOfAsteroidListing = (screenWidth - widthOfAsteroidListing) / 2;

    int largeAsteroidStartingPositionX = startingPositionXOfAsteroidListing;
    int mediumAsteroidStartingPositionX = startingPositionXOfAsteroidListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - mediumAsteroidWidth) / 2;
    int smallAsteroidStartingPositionX = startingPositionXOfAsteroidListing + 2 * (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - smallAsteroidWidth) / 2;
    int tinyAsteroidStartingPositionX = startingPositionXOfAsteroidListing + 3 * (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - tinyAsteroidWidth) / 2;

    int largeAsteroidStartingPositionY = startingPositionYOfAsteroidListing;
    int mediumAsteroidStartingPositionY = startingPositionYOfAsteroidListing + (bigAsteroidHeight - mediumAsteroidHeight) / 2;
    int smallAsteroidStartingPositionY = startingPositionYOfAsteroidListing + (bigAsteroidHeight - smallAsteroidHeight) / 2;
    int tinyAsteroidStartingPositionY = startingPositionYOfAsteroidListing + (bigAsteroidHeight - tinyAsteroidHeight) / 2;

    imgIntroductionEnemiesScreenAsteroidLarge = new StaticImage(ImageManager.getImage(Constants.FILENAME_BIG_ASTEROID_1), largeAsteroidStartingPositionX, largeAsteroidStartingPositionY, false, imageObserver);
    imgIntroductionEnemiesScreenAsteroidMedium = new StaticImage(ImageManager.getImage(Constants.FILENAME_MEDIUM_ASTEROID_1), mediumAsteroidStartingPositionX, mediumAsteroidStartingPositionY, false, imageObserver);
    imgIntroductionEnemiesScreenAsteroidSmall = new StaticImage(ImageManager.getImage(Constants.FILENAME_SMALL_ASTEROID_1), smallAsteroidStartingPositionX, smallAsteroidStartingPositionY, false, imageObserver);
    imgIntroductionEnemiesScreenAsteroidTiny = new StaticImage(ImageManager.getImage(Constants.FILENAME_TINY_ASTEROID_1), tinyAsteroidStartingPositionX, tinyAsteroidStartingPositionY, false, imageObserver);

    int widthOfUFOListing = 2 * bigAsteroidWidth + bufferBetweenAsteroid;
    startingPositionXOfUFOListing = (screenWidth - widthOfUFOListing) / 2;

    int ufoWidth = ImageManager.getWidth(Constants.FILENAME_UFO);
    int ufoHeight = ImageManager.getHeight(Constants.FILENAME_UFO);
    int ufoShortyWidth = ImageManager.getWidth(Constants.FILENAME_UFO_SHORTY);
    int ufoShortyHeight = ImageManager.getHeight(Constants.FILENAME_UFO_SHORTY);

    int ufoPositionX = startingPositionXOfUFOListing + (bigAsteroidWidth - ufoWidth) / 2;
    int ufoPositionY = 432;
    int ufoShortyPositionX = startingPositionXOfUFOListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - ufoShortyWidth) / 2;
    int ufoShortyPositionY = 432 + (ufoHeight - ufoShortyHeight) / 2;

    imgIntroductionEnemiesScreenUFO = new StaticImage(ImageManager.getImage(Constants.FILENAME_UFO), ufoPositionX, ufoPositionY, false, imageObserver);
    imgIntroductionEnemiesScreenUFOShorty = new StaticImage(ImageManager.getImage(Constants.FILENAME_UFO_SHORTY), ufoShortyPositionX, ufoShortyPositionY, false, imageObserver);
  }

  /**
   * Displays the enemies (both Asteroids and UFOs) from the game.
   * 
   * @param g
   */
  public void displayIntroductionEnemiesScreen(Graphics2D g)
  {
    int startingYPositionLabel = 328;
    int startingYPositionScore = startingYPositionLabel + 16;
    Rectangle2D bounds;

    // Draw screen titles
    msgEnemiesTitle.draw(g);
    msgEnemiesAsteroidLabel.draw(g);

    // Draw asteroid images
    imgIntroductionEnemiesScreenAsteroidLarge.draw(g);
    imgIntroductionEnemiesScreenAsteroidMedium.draw(g);
    imgIntroductionEnemiesScreenAsteroidSmall.draw(g);
    imgIntroductionEnemiesScreenAsteroidTiny.draw(g);

    // Draw the labels below each asteroid, both point value and 

    g.setColor(Color.WHITE);

    // Display the respective asteroid labels
    // NOTE: Since the string bounds is used in these computations, the power-up images and text will not be replaced with static images and text since
    //       the bounds cannot be known without the g object. Also note that there are other ways to compute the length of the text, but these approaches
    //       do not produce accurate results according to some of the posts on various blogs. The bottom line, until this becomes a bottle neck, this code
    //       below will probably remain this way.
    g.setFont(Constants.FONT_INTRO_ENEMIES_SCREEN_SMALL);
    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_LARGE_LABEL, g);
    int largeAsteroidTextPosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_LARGE_LABEL, largeAsteroidTextPosition, startingYPositionLabel);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_MEDIUM_LABEL, g);
    int mediumAsteroidTextPosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_MEDIUM_LABEL, mediumAsteroidTextPosition, startingYPositionLabel);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_SMALL_LABEL, g);
    int smallAsteroidTextPosition = startingPositionXOfAsteroidListing + 2 * (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_SMALL_LABEL, smallAsteroidTextPosition, startingYPositionLabel);

    bounds = g.getFontMetrics().getStringBounds(Constants.MSG_ENEMIES_ASTEROID_TINY_LABEL, g);
    int tinyAsteroidTextPosition = startingPositionXOfAsteroidListing + 3 * (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString(Constants.MSG_ENEMIES_ASTEROID_TINY_LABEL, tinyAsteroidTextPosition, startingYPositionLabel);

    // Display the respective asteroid point values
    // NOTE: Since the string bounds is used in these computations, the power-up images and text will not be replaced with static images and text since
    //       the bounds cannot be known without the g object. Also note that there are other ways to compute the length of the text, but these approaches
    //       do not produce accurate results according to some of the posts on various blogs. The bottom line, until this becomes a bottle neck, this code
    //       below will probably remain this way.
    g.setFont(Constants.FONT_INTRO_ENEMIES_SCREEN_TINY);
    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_BIG_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int largeAsteroidTextScorePosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString("(" + Constants.SCORE_BIG_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", largeAsteroidTextScorePosition, startingYPositionScore);

    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_MEDIUM_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int mediumAsteroidTextScorePosition = startingPositionXOfAsteroidListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString("(" + Constants.SCORE_MEDIUM_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", mediumAsteroidTextScorePosition, startingYPositionScore);

    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_SMALL_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int smallAsteroidTextScorePosition = startingPositionXOfAsteroidListing + 2 * (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString("(" + Constants.SCORE_SMALL_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", smallAsteroidTextScorePosition, startingYPositionScore);

    bounds = g.getFontMetrics().getStringBounds("(" + Constants.SCORE_TINY_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", g);
    int tinyAsteroidTextScorePosition = startingPositionXOfAsteroidListing + 3 * (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString("(" + Constants.SCORE_TINY_ASTEROIDS + Constants.MSG_ENEMIES_POINTS + ")", tinyAsteroidTextScorePosition, startingYPositionScore);

    // Draw the UFOs
    msgEnemiesUFOLabel.draw(g);
    imgIntroductionEnemiesScreenUFO.draw(g);
    imgIntroductionEnemiesScreenUFOShorty.draw(g);

    g.setFont(Constants.FONT_INTRO_ENEMIES_SCREEN_SMALL);
    g.setColor(Color.WHITE);
    bounds = g.getFontMetrics().getStringBounds("Regular", g);
    int UFOTextPosition = startingPositionXOfUFOListing + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString(Constants.MSG_ENEMIES_UFO_LABEL, UFOTextPosition, 500);

    bounds = g.getFontMetrics().getStringBounds("Regular", g);
    int UFOShortyTextPosition = startingPositionXOfUFOListing + (bigAsteroidWidth + bufferBetweenAsteroid) + (bigAsteroidWidth - (int) bounds.getWidth()) / 2;
    g.drawString(Constants.MSG_ENEMIES_UFO_SHORTY_LABEL, UFOShortyTextPosition, 500);
  }

  public void displayIntroductionFooter(Graphics2D g)
  {
    // NOTE: These variables can be computed once and moved outside of this method after testing to reduce unnecessary computation
    int backMsgPositionX = 32;
    int backMsgPositionY = 576;
    int nextMsgPositionX = 544;
    int nextMsgPositionY = 576;

    g.setFont(Constants.FONT_INTRO_SCREEN_SMALL);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_INTRODUCTION_MSG_NEXT, nextMsgPositionX, nextMsgPositionY);
    g.drawString(Constants.MSG_INTRODUCTION_MSG_BACK, backMsgPositionX, backMsgPositionY);
  }

  /**
   * 
   * 
   * @param g
   */
  public void displayHUD(Graphics2D g, int currentLevel)
  {
    // Draw player health bar
    String barFrameImageName = "";
    switch (playerReference.getLimit(Constants.AttributeType.ATTRIBUTE_HEALTH))
    {
      case Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_20:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_20;
        break;
      case Constants.SHIP_HEALTH_CAPACITY_INCREASE_TO_40:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_40;
        break;
      default:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_10;
    }
    g.drawImage(ImageManager.getImage(barFrameImageName), screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 20, 18, this.imageObserver);

    // Draw player shield bar
    switch (playerReference.getLimit(Constants.AttributeType.ATTRIBUTE_SHIELD))
    {
      case Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_20:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_20;
        break;
      case Constants.SHIP_SHIELD_CAPACITY_INCREASE_TO_40:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_40;
        break;
      default:
        barFrameImageName = Constants.FILENAME_BAR_FRAME_10;
    }
    g.drawImage(ImageManager.getImage(barFrameImageName), screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 20, 33, this.imageObserver);

    // Draw the health level
    for (int n = 0; n < playerReference.getValue(Constants.AttributeType.ATTRIBUTE_HEALTH); n++)
    {
      int dx = (screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 18) + n * 5;
      g.drawImage(ImageManager.getImage(Constants.FILENAME_BAR_HEALTH), dx, 20, this.imageObserver);
    }

    // Draw the shield level
    for (int n = 0; n < playerReference.getValue(Constants.AttributeType.ATTRIBUTE_SHIELD); n++)
    {
      int dx = (screenWidth - ImageManager.getWidth(Constants.FILENAME_BAR_FRAME_40) - 18) + n * 5;
      g.drawImage(ImageManager.getImage(Constants.FILENAME_BAR_SHIELD), dx, 35, this.imageObserver);
    }

    msgHUDHealthBar.draw(g);  // Draw the label for the health bar    
    msgHUDShieldBar.draw(g);  // Draw the label for the shield bar
    msgHUDFirepower.draw(g);  // Draw label next to bullet upgrades

    // Draw the current level
    msgHUDCurrentLevel.setText(Constants.MSG_GAME_PLAYING_WAVE + GameUtility.lPadZero(currentLevel, 2));
    msgHUDCurrentLevel.draw(g);

    // Draw the score
    msgHUDScore.setText(Constants.MSG_GAME_PLAYING_SCORE + GameUtility.lPadZero(ScoreManager.getScore(), 6));
    msgHUDScore.draw(g);

    // Draw the amounts for Super Shields and for The Bomb 
    msgHUDNumberOfSuperShields.setText(playerReference.getValue(Constants.AttributeType.ATTRIBUTE_SUPER_SHIELD) + "");
    msgHUDNumberOfTheBombs.setText(playerReference.getValue(Constants.AttributeType.ATTRIBUTE_THE_BOMB) + "");
    msgHUDNumberOfSuperShields.draw(g);
    msgHUDNumberOfTheBombs.draw(g);

    // Draw the bullet upgrades
    String playerFirepowerImageName = "";
    switch (playerReference.getValue(AttributeType.ATTRIBUTE_FIREPOWER))
    {
      case 2:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_2;
        break;

      case 3:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_3;
        break;

      case 4:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_4;
        break;

      case 5:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN_5;
        break;

      default:
        playerFirepowerImageName = Constants.FILENAME_POWERUP_GUN;
    }
    g.drawImage(ImageManager.getImage(playerFirepowerImageName), 130, 17, this.imageObserver);

    // Draw the super shield icon
    g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_SUPERSHIELD_ICON), 20, 85, this.imageObserver);

    // Draw the bomb icon
    g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_THE_BOMB_ICON), 20, 115, this.imageObserver);

    // Draw the auto shield icon along with amount
    //g.drawImage(powerupAutoShield.getImage(), 20, 145, this.imageObserver);

    // Draw the respective thrust icon given the players current thrust level
    switch (playerReference.getValue(AttributeType.ATTRIBUTE_THRUST))
    {
      case 2:
        //g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_2), 20, 153, this.imageObserver); // Y coordinate Was 185
        g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_ENGINE_2), 20, 145, this.imageObserver); // Y coordinate Was 185
        break;

      case 3:
        //g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_3), 20, 153, this.imageObserver);
        g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_ENGINE_3), 20, 145, this.imageObserver); // Y coordinate Was 185
        break;

      default:
        //g.drawImage(ImageManager.getImage(Constants.FILENAME_POWERUP_ENGINE_1), 20, 153, this.imageObserver);
        g.drawImage(ImageManager.getImage(Constants.FILENAME_HUD_ENGINE_1), 20, 145, this.imageObserver); // Y coordinate Was 185
    }

    //    // TODO: This might go into a separate method since it might not be part of the HUD 
    //    if (prepareTheBomb)
    //    {
    //      if (playCountdownTimerSound)
    //      {
    //        switch ((int) prepareBombTimerSecondCount)
    //        {
    //          case 3:
    //          case 2:
    //          case 1:
    //            soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_3);
    //            this.playCountdownTimerSound = false;
    //            break;
    //          default:
    //            soundManager.playSound(SoundManager.SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_2);
    //            this.playCountdownTimerSound = false;
    //        }
    //      }
    //
    //      msgHUDTheBombCoundown.setText(Constants.DOT_DOT_DOT + prepareBombTimerSecondCount + Constants.DOT_DOT_DOT);
    //      msgHUDTheBombCoundown.draw(g);
    //    }
  }

  public void displayGameStartScreen(Graphics2D g)
  {
    msgGameStartScreen.draw(g);
  }

  public void displayNextLevelScreen(Graphics2D g, int currentLevel)
  {
    msgNextLevelScreen.setText(Constants.MSG_NEXT_LEVEL + GameUtility.lPadZero((currentLevel + 1), 2));
    msgNextLevelScreen.draw(g);
  }

  public void drawGameTitle(Graphics2D g)
  {
    msgGameTitle.draw(g);
  }

  public void displayPausedScreen(Graphics2D g)
  {
    msgPaused.draw(g);
  }

  public void displayPlayerDeadScreen(Graphics2D g)
  {
    msgPlayerDeadScreen.draw(g);
  }

  public void displayGameOverScreen(Graphics2D g)
  {
    msgGameOverScreen.draw(g);
  }
}
