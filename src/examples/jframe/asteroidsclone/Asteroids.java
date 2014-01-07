package examples.jframe.asteroidsclone;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.SwingUtilities;

import game.deprecated.GameJFrame;
import game.framework.GameEngine;
import game.framework.entities.Entity;
import game.framework.entities.EntityImage;
import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;
import game.framework.utilities.input.GameInputMovement;

public class Asteroids extends GameEngine
{
  /*
   * Class member variables
   */

  // General constants
  private static final double DEGREES_IN_A_CIRCLE              = 360;

  // Constants used for enemy entities
  private static final int    NUMBER_OF_BIG_ASTEROID_IMAGES    = 5;
  private static final int    NUMBER_OF_MEDIUM_ASTEROID_IMAGES = 2;
  private static final int    NUMBER_OF_SMALL_ASTEROID_IMAGES  = 3;
  private static final int    NUMBER_OF_TINY_ASTEROID_IMAGES   = 4;

  private static final int    ASTEROID_DIMENSIONS              = 128;
  private static final int    ASTEROID_MIN_ROTATION_RATE       = 5;
  private static final int    ASTEROID_MAX_ROTATION_RATE       = 50;

  private static final int    ASTEROID_BIG                     = 10;
  private static final int    ASTEROID_MEDIUM                  = 11;
  private static final int    ASTEROID_SMALL                   = 12;
  private static final int    ASTEROID_TINY                    = 13;

  // Constants used for player entity
  private static final int    PLAYER_ROTATION_RATE             = 200;
  private static final int    PLAYER_BULLET_SPEED              = 200;

  // TODO: Given different difficulty levels, it may be a good idea to have
  // separate velocity scales
  private static final int[]  BIG_ASTEROID_VELOCITY_SCALE      = new int[] { 10, 15, 25, 35, 50 };
  private static final int[]  MEDIUM_ASTEROID_VELOCITY_SCALE   = new int[] { 10, 25, 50, 60, 75, 100 };
  private static final int[]  SMALL_ASTEROID_VELOCITY_SCALE    = new int[] { 50, 75, 100, 110, 120, 130 };
  private static final int[]  TINY_ASTEROID_VELOCITY_SCALE     = new int[] { 10, 25, 100, 125, 200 };

  // Game variables
  //private static final int    STARTING_NUMBER_OF_ASTEROIDS     = 10;

  /*
   * Class instance variables
   */

  // Variables used for player
  EntityImage                 bulletImage;
  private GameInputMovement   playerMovement;
  private boolean             fireShot;

  ImageObserver               imageObserver;

  // Variables used for enemy entities
  EntityImage[]               bigAsteroids;
  EntityImage[]               mediumAsteroids;
  EntityImage[]               smallAsteroids;
  EntityImage[]               tinyAsteroids;
  EntityImage[]               barImage                         = new EntityImage[2];
  EntityImage                 barFrame;

  // Variables used for game
  private int                 currentLevel;
  EntityImage                 background;

  /*
   *  Game state management variables
   */

  // Flags that keep track of the user requests for game state transitions
  private boolean             requestGameStart;

  // Variables used to keep track of game event state transitions
  //private boolean                      invokePlayerDeadState;

  // Timers used to control when state transitions are to occur 
  private long                timerPlayerDeadState             = 0;
  private long                timerGameStart                   = 0;
  private long                timerGameOver                    = 0;
  private long                timerNextLevel                   = 0;

  /*
   * Timers used to manage player entity states
   */
  private long                timerCollision                   = 0;

  /*
   * Bounding boxes used for messages displayed to the screen
   */
  Rectangle2D                 boundsIntroductionTitleMsg;
  Rectangle2D                 boundsIntroductionStateTitle;
  Rectangle2D                 boundsIntroductionPowerupsMsg;
  Rectangle2D                 boundsGameStartMsg;
  Rectangle2D                 boundsPlayerDeadMsg;
  Rectangle2D                 boundsGameOverMsg;
  Rectangle2D                 boundsGamePlayingHealthMsg;
  Rectangle2D                 boundsNextLevelMsg;

  public Asteroids(IRender renderer, ImageObserver imageObserver)
  {
    super(renderer);
    this.imageObserver = imageObserver;
  }

  public Asteroids(IRender renderer, ImageObserver imageObserver, int userDefinedScreenWidth, int userDefinedScreenHeight)
  {
    super(renderer, userDefinedScreenWidth, userDefinedScreenHeight);
    this.imageObserver = imageObserver;
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.engine.jframe.GameJFrame#userGameInit()
   */

  @Override
  public void userGameInit()
  {
    bigAsteroids = new EntityImage[NUMBER_OF_BIG_ASTEROID_IMAGES];
    mediumAsteroids = new EntityImage[NUMBER_OF_MEDIUM_ASTEROID_IMAGES];
    smallAsteroids = new EntityImage[NUMBER_OF_SMALL_ASTEROID_IMAGES];
    tinyAsteroids = new EntityImage[NUMBER_OF_TINY_ASTEROID_IMAGES];

    playerMovement = new GameInputMovement();

    // Load the big asteroid images (5 total)
    for (int n = 0; n < NUMBER_OF_BIG_ASTEROID_IMAGES; n++)
    {
      bigAsteroids[n] = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.ENEMY);
      String fn = "./asteroid" + (n + 1) + ".png";
      bigAsteroids[n].load(fn);
    }

    // Load the medium asteroid images 2 total)
    for (int n = 0; n < NUMBER_OF_MEDIUM_ASTEROID_IMAGES; n++)
    {
      mediumAsteroids[n] = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.ENEMY);
      String fn = "./medium" + (n + 1) + ".png";
      mediumAsteroids[n].load(fn);
    }

    // Load the small asteroid images (3 total)
    for (int n = 0; n < NUMBER_OF_SMALL_ASTEROID_IMAGES; n++)
    {
      smallAsteroids[n] = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.ENEMY);
      String fn = "./small" + (n + 1) + ".png";
      smallAsteroids[n].load(fn);
    }

    // Load the tiny asteroid images (4 total)
    for (int n = 0; n < NUMBER_OF_TINY_ASTEROID_IMAGES; n++)
    {
      tinyAsteroids[n] = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.ENEMY);
      String fn = "./tiny" + (n + 1) + ".png";
      tinyAsteroids[n].load(fn);
    }

    // Load the health/shield bars
    barFrame = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.UNDEFINED);
    barFrame.load("./barframe.png");
    barImage[0] = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.UNDEFINED);
    barImage[0].load("./bar_health.png");
    barImage[1] = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.UNDEFINED);
    barImage[1].load("./bar_shield.png");

    // Load the background image
    background = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.UNDEFINED);
    background.load("./bluespace.png");

    // Load bullet Image
    bulletImage = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.UNDEFINED);
    bulletImage.load("./plasmashot.png");

    fireShot = false;

    // Add the player entity
    PlayerEntity ship = new PlayerEntity(this.imageObserver, GameEngineConstants.EntityTypes.PLAYER);
    ship.load("./spaceship.png");
    ship.setRotationRate(0.0);
    ship.setFaceAngle(0);
    ship.setMoveAngle(ship.getFaceAngle() - 90);
    ship.defineHomePosition((screenWidth - ship.getWidth()) / 2, (screenHeight - ship.getHeight()) / 2);
    ship.setPosition((screenWidth - ship.getWidth()) / 2, (screenHeight - ship.getHeight()) / 2);
    ship.setVisible(false); // Only make ship visible when game is being played
    this.setNewPlayerEntity(ship);

    // Configure the entity lists to remove dead entities from the list
    this.removeDeadPlayerShotsFromEntityList();
    this.removeDeadEnemiesFromEntityList();

    this.state = GameEngineConstants.GameState.INTRODUCTION;
    requestGameStart = false;
    //invokePlayerDeadState = false;
  }

  @Override
  public void userGameStart()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void userGamePreUpdate()
  {
    switch (state)
    {
      case INTRODUCTION:

        if (requestGameStart)
        {
          requestGameStart = false;
          state = GameEngineConstants.GameState.GAME_START;
          timerGameStart = System.currentTimeMillis();
        }

        break;

      case GAME_START:

        // Pause the desired amount of time in milliseconds (specified in GAME_START_INTERVAL) before transitioning to the PLAYING state
        if (System.currentTimeMillis() > (Constants.GAME_START_INTERVAL + timerGameStart))
        {
          state = GameEngineConstants.GameState.PLAYING;
          this.resetGame();
        }

        break;

      case PLAYING:

        // If there is no more health remaining for the player ship, it needs to be 
        if (!((PlayerEntity) getPlayer()).hasHealthRemaining())
        {
          //invokePlayerDeadState = true;
          state = GameEngineConstants.GameState.PLAYER_DEAD;
          timerPlayerDeadState = System.currentTimeMillis();
        }

        if (getEnemies().isEmpty())
        {
          state = GameEngineConstants.GameState.LEVEL_NEXT;
          timerNextLevel = System.currentTimeMillis();
          getPlayer().setVisible(false);
        }

        break;

      case LEVEL_NEXT:
        
        if (System.currentTimeMillis() > (Constants.NEXT_LEVEL_INTERVAL + timerNextLevel))
        {
          this.nextLevel();
          state = GameEngineConstants.GameState.PLAYING;
        }

        break;
        
      case PLAYER_DEAD:

        if (System.currentTimeMillis() > (Constants.PLAYER_DEAD_INTERVAL + timerPlayerDeadState))
        {
          state = GameEngineConstants.GameState.GAMEOVER;
          timerGameOver = System.currentTimeMillis();
        }

        break;

      case GAMEOVER:

        if (System.currentTimeMillis() > (Constants.GAMEOVER_INTERVAL + timerGameOver))
        {
          resetEntityLists();
          state = GameEngineConstants.GameState.INTRODUCTION;
        }

        break;

      default:
    }
  }

  @Override
  public void userGameUpdateEntity(Entity entity)
  {
    // Only update entities that are alive
    if (!entity.isAlive())
    {
      return;
    }
    GameUtility.warp(entity, screenWidth, screenHeight);
  }

  @Override
  public void userHandleEntityCollision(Entity entity1, Entity entity2)
  {
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER)
    {
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
//        if (entity1.getEntityState() == GameEngineConstants.EntityState.NORMAL)
//        {
          //System.out.println("userHandleEntityCollision - Player is NOT Invulnerable.");
          // Check if the player has the shields engaged, if so then do not reduce health of the player
          // TODO: Add logic to handle the shields

          timerCollision = System.currentTimeMillis();
          entity1.setVelocity(0, 0);

          // TODO: Add explosions when animation frames are added to GameFramework
          //            double x = spr1.position().X() - 10;
          //            double y = spr1.position().Y() - 10;
          //            startBigExplosion(new Point2D(x, y));

//          entity1.setEntityState(GameEngineConstants.EntityState.EXPLODING);
          entity1.kill();
          entity2.kill();
          breakAsteroid((EntityImage) entity2);
          System.out.println("Player Health: " + ((PlayerEntity) entity1).getHealthAmount());
          //System.out.println(entity1.toString());
//        }
//        // Make ship temporarily invulnerable
//        else if (entity1.getEntityState() == GameEngineConstants.EntityState.EXPLODING)
//        {
//          //System.out.println("userHandleEntityCollision - Player is Invulnerable.");
//          if (System.currentTimeMillis() > (timerCollision + Constants.INVULNERABILITY_INTERVAL))
//          {
//            entity1.setEntityState(GameEngineConstants.EntityState.NORMAL);
//          }
//        }
      }
    }

    // Handle player shot collision with an invader
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER_SHOT)
    {
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
        entity1.kill();
        entity2.kill();
        breakAsteroid((EntityImage) entity2);
      }
    }
  }

  @Override
  public void userProcessInput()
  {
    if (playerMovement.keyLeft())
    {
      getPlayer().setRotationRate(-PLAYER_ROTATION_RATE);
    }
    else if (playerMovement.keyRight())
    {
      getPlayer().setRotationRate(PLAYER_ROTATION_RATE);
    }
    else
    {
      getPlayer().setRotationRate(0.0);
    }

    if (fireShot)
    {
      addPlayerShot(stockBullet());
      fireShot = false;
    }
  }

  @Override
  public void userGamePreDraw(Graphics2D g)
  {
    // Draw the background. Note that the background image will be displayed for all game states
    g.drawImage(background.getImage(), 0, 0, screenWidth - 1, screenHeight - 1, this.imageObserver);

    switch (this.state)
    {
      case INTRODUCTION:

        displayIntroductionMainScreen(g);
        break;

      case GAME_START:

        displayGameStartScreen(g);
        break;

      case PLAYING:

        break;

      default:
    }
  }

  @Override
  public void userGamePostDraw(Graphics2D g)
  {
    switch (this.state)
    {
      case INTRODUCTION:

        break;

      case GAME_START:

        break;

      case PLAYING:

        // Draw player health bar
        g.drawImage(barFrame.getImage(), screenWidth - barFrame.getWidth() - 20, 18, this.imageObserver);
        for (int n = 0; n < ((PlayerEntity) getPlayer()).getHealthAmount(); n++)
        {
          int dx = (screenWidth - barFrame.getWidth() - 18) + n * 5;
          g.drawImage(barImage[0].getImage(), dx, 20, this.imageObserver);
        }

        // Draw the label for the health bar
        g.setFont(Constants.FONT_GAME_PLAYING_HEALTH);
        boundsGamePlayingHealthMsg = g.getFontMetrics().getStringBounds(Constants.MSG_GAME_PLAYING_HEALTH, g);
        g.setColor(Color.WHITE);
        g.drawString(Constants.MSG_GAME_PLAYING_HEALTH, (int) (screenWidth - barFrame.getWidth() - 20 - boundsGamePlayingHealthMsg.getWidth()), 30);
        break;

      case PLAYER_DEAD:

        displayPlayerDeadScreen(g);
        break;

      case GAMEOVER:

        displayGameOverScreen(g);
        break;

      case LEVEL_NEXT:
        
        displayNextLevelScreen(g);        
        break;
        
      default:
    }
  }

  @Override
  public void userGameShutdown()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void gameKeyPressed(int keyCode)
  {
    switch (this.state)
    {
      case INTRODUCTION:

        break;

      case GAME_START:

        break;

      case PLAYING:

        playerMovement.pressed(keyCode);

        break;

      default:
    }
  }

  @Override
  public void gameKeyReleased(int keyCode)
  {
    switch (this.state)
    {
      case INTRODUCTION:

        if (keyCode == KeyEvent.VK_ENTER)
        {
          requestGameStart = true;
        }

        break;

      case GAME_START:

        break;

      case PLAYING:

        playerMovement.released(keyCode);

        if (keyCode == KeyEvent.VK_CONTROL)
        {
          fireShot = true;
        }

        break;

      default:
    }
  }

  @Override
  public void gameKeyTyped(int keyCode)
  {
    // TODO Auto-generated method stub

  }

  /*
   * Create a random "big" asteroid
   */
  public void createNewAsteroid()
  {
    // Create a new asteroid sprite and set the type
    EntityAsteroid asteroid = new EntityAsteroid(this.imageObserver);
    asteroid.setEnemyType(ASTEROID_BIG);

    // Pick one of the random asteroid images
    int randomAsteroidImageIndex = GameUtility.random.nextInt(NUMBER_OF_BIG_ASTEROID_IMAGES);
    asteroid.setImage(bigAsteroids[randomAsteroidImageIndex].getImage());

    // Set to a random position on the screen and prevent asteroids from
    // starting on the players position
    double x = GameUtility.random.nextInt(GameEngineConstants.DEFAULT_CANVAS_WIDTH - ASTEROID_DIMENSIONS);
    double y = GameUtility.random.nextInt(GameEngineConstants.DEFAULT_CANVAS_HEIGHT - ASTEROID_DIMENSIONS);
    asteroid.setPosition(x, y);
    nudgeAsteroid(asteroid);

    // Set rotation and direction angles of asteroid
    int faceAngle = GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE);
    int moveAngle = GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE);
    asteroid.setFaceAngle(faceAngle);
    asteroid.setMoveAngle(moveAngle);

    // Assign a random rotation rate and determine the direction (CW or CCW)
    double rotationRate = GameUtility.random.nextInt(ASTEROID_MAX_ROTATION_RATE) + ASTEROID_MIN_ROTATION_RATE;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    asteroid.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = asteroid.getMoveAngle() - 90;
    double velx = calcAngleMoveX(angle);
    double vely = calcAngleMoveY(angle);
    asteroid.setVelocity(velx, vely);
    asteroid.getVelocity().scaleThisVector(BIG_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(BIG_ASTEROID_VELOCITY_SCALE.length)]);

    // Add the new asteroid to the sprite list
    getEnemies().add(asteroid);
  }

  /*
   * When spawning a new asteroid, this method ensures that a new asteroid is not spawned on top of the player.
   */
  private void nudgeAsteroid(EntityImage entity)
  {
    double x = entity.getPositionX();
    double y = entity.getPositionY();

    // Compute the buffer around the players starting position, note that
    // this space is dependent on the height and width of the entity.
    // This is to avoid an enemy entity from being placed directly on top of
    // the player entity when the player entity begins at its home position.
    double entityWidth = entity.getWidth();
    double entityHeight = entity.getHeight();
    double horizontalCenter = GameEngineConstants.DEFAULT_CANVAS_WIDTH / 2;
    double verticalCenter = GameEngineConstants.DEFAULT_CANVAS_HEIGHT / 2;
    double lowerHorizontalBufferFactor = (horizontalCenter - entityWidth) / GameEngineConstants.DEFAULT_CANVAS_WIDTH;
    double upperHorizontalBufferFactor = (horizontalCenter + entityWidth) / GameEngineConstants.DEFAULT_CANVAS_WIDTH;
    double lowerVerticalBufferFactor = (verticalCenter - entityHeight) / GameEngineConstants.DEFAULT_CANVAS_HEIGHT;
    double upperVerticalBufferFactor = (verticalCenter + entityHeight) / GameEngineConstants.DEFAULT_CANVAS_HEIGHT;

    // System.out.println("lowerHorizontalBufferFactor: " +
    // lowerHorizontalBufferFactor);
    // System.out.println("upperHorizontalBufferFactor: " +
    // upperHorizontalBufferFactor);
    // System.out.println("lowerVerticalBufferFactor: " +
    // lowerVerticalBufferFactor);
    // System.out.println("upperVerticalBufferFactor: " +
    // upperVerticalBufferFactor);

    // If the asteroid is near the center of the screen, nudge it away from
    // the players ship
    if ((x >= GameEngineConstants.DEFAULT_CANVAS_WIDTH * lowerHorizontalBufferFactor) && (x <= GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.5))
    {
      x += -entityWidth;
    }
    else if ((x <= GameEngineConstants.DEFAULT_CANVAS_WIDTH * upperHorizontalBufferFactor) && (x >= GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.5))
    {
      x += entityWidth;
    }

    if ((y >= GameEngineConstants.DEFAULT_CANVAS_HEIGHT * lowerVerticalBufferFactor) && (y <= GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.5))
    {
      y += -entityHeight;
    }
    else if ((y <= GameEngineConstants.DEFAULT_CANVAS_HEIGHT * upperVerticalBufferFactor) && (y >= GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.5))
    {
      y += entityHeight;
    }

    entity.setPosition(x, y);
  }

  private EntityImage stockBullet()
  {
    double faceAngle = getPlayer().getFaceAngle();

    EntityImage bullet = new EntityImage(this.imageObserver, GameEngineConstants.EntityTypes.PLAYER_SHOT);
    bullet.setImage(bulletImage.getImage());
    bullet.setFaceAngle(faceAngle);
    bullet.setMoveAngle(faceAngle - 90);

    // Set the bullet's velocity
    double angle = bullet.getMoveAngle();
    double svx = calcAngleMoveX(angle) * PLAYER_BULLET_SPEED;
    double svy = calcAngleMoveY(angle) * PLAYER_BULLET_SPEED;

    bullet.setVelocity(svx, svy);

    // Set the bullet's starting position
    double x = getPlayer().getCenterX() - bullet.getWidth() / 2;
    double y = getPlayer().getCenterY() - bullet.getHeight() / 2;
    bullet.setPosition(x, y);

    // Initialize the life span and life age
    bullet.setLifespan((int) (GameEngineConstants.DEFAULT_UPDATE_RATE * 2.5));
    return bullet;
  }

  /*
   * Break up an asteroid into smaller pieces
   */
  private void breakAsteroid(EntityImage entity)
  {
    switch (((EntityEnemy) entity).getEnemyType())
    {
      case ASTEROID_BIG:
        // Spawn medium asteroids over the old one
        spawnSmallerAsteroid(entity);
        spawnSmallerAsteroid(entity);
        spawnSmallerAsteroid(entity);

        // TODO: Draw big explosion
        break;

      case ASTEROID_MEDIUM:
        // Spawn small asteroids over the old one
        spawnSmallerAsteroid(entity);
        spawnSmallerAsteroid(entity);
        spawnSmallerAsteroid(entity);

        // TODO: Draw small explosion
        break;

      case ASTEROID_SMALL:
        // Spawn tiny asteroids over the old one
        spawnSmallerAsteroid(entity);
        spawnSmallerAsteroid(entity);
        spawnSmallerAsteroid(entity);

        // TODO: Draw small explosion
        break;

      case ASTEROID_TINY:
        // TODO: Spawn a random powerup

        // TODO: Draw small explosion
        break;

    }
  }

  /*
   * Spawn a smaller asteroid based on passed sprite
   */
  private void spawnSmallerAsteroid(EntityImage entity)
  {
    // Create a new asteroid Entity
    EntityAsteroid asteroid = new EntityAsteroid(this.imageObserver);

    // Set rotation and direction angles
    asteroid.setFaceAngle(GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE));
    asteroid.setMoveAngle(GameUtility.random.nextInt((int) DEGREES_IN_A_CIRCLE));
    double rotationRate = GameUtility.random.nextInt(ASTEROID_MAX_ROTATION_RATE) + ASTEROID_MIN_ROTATION_RATE;
    rotationRate = (GameUtility.random.nextBoolean() ? -rotationRate : rotationRate);
    asteroid.setRotationRate(rotationRate);
    asteroid.setRotationRate(rotationRate);

    // Set velocity based on movement direction
    double angle = asteroid.getMoveAngle() - 90;
    double velx = calcAngleMoveX(angle);
    double vely = calcAngleMoveY(angle);
    asteroid.setVelocity(velx, vely);

    // Set some size-specific properties
    switch (((EntityEnemy) entity).getEnemyType())
    {
      case ASTEROID_BIG:
        asteroid.setEnemyType(ASTEROID_MEDIUM);

        // Pick one of the random asteroid images
        int i = GameUtility.random.nextInt(NUMBER_OF_MEDIUM_ASTEROID_IMAGES);
        asteroid.setImage(mediumAsteroids[i].getImage());
        asteroid.getVelocity().scaleThisVector(MEDIUM_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(MEDIUM_ASTEROID_VELOCITY_SCALE.length)]);
        break;

      case ASTEROID_MEDIUM:
        asteroid.setEnemyType(ASTEROID_SMALL);

        // Pick one of the random asteroid images
        i = GameUtility.random.nextInt(NUMBER_OF_SMALL_ASTEROID_IMAGES);
        asteroid.setImage(smallAsteroids[i].getImage());
        asteroid.getVelocity().scaleThisVector(SMALL_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(SMALL_ASTEROID_VELOCITY_SCALE.length)]);
        break;

      case ASTEROID_SMALL:
        asteroid.setEnemyType(ASTEROID_TINY);

        // Pick one of the random asteroid images
        i = GameUtility.random.nextInt(NUMBER_OF_TINY_ASTEROID_IMAGES);
        asteroid.setImage(tinyAsteroids[i].getImage());
        asteroid.getVelocity().scaleThisVector(TINY_ASTEROID_VELOCITY_SCALE[GameUtility.random.nextInt(TINY_ASTEROID_VELOCITY_SCALE.length)]);
        break;
    }

    // Set pseudo-random position around source sprite
    int w = entity.getWidth();
    int h = entity.getHeight();
    double x = entity.getPositionX() + w / 2 + GameUtility.random.nextInt(20) - 40;
    double y = entity.getPositionY() + h / 2 + GameUtility.random.nextInt(20) - 40;
    asteroid.setPosition(x, y);

    // Add the new asteroid to the sprite list
    getEnemies().add(asteroid);
  }

  // NOTE: This method could be moved to the GameUtility class
  protected double calcAngleMoveX(double angle)
  {
    return (double) (Math.cos(angle * Math.PI / 180));
  }

  // NOTE: This method could be moved to the GameUtility class
  protected double calcAngleMoveY(double angle)
  {
    return (double) (Math.sin(angle * Math.PI / 180));
  }

  /*
   * Game Reset and Next Level methods
   */

  public void resetGame()
  {
    // Reset player variables

    // TODO: Consider creating a player manager to encapsulate these actions into a reset method
    //invokePlayerDeadState = false;
    //gamePaused = false;
    //unpauseGame();
    //requestToQuitPlayingGame = false;
    //quitPlayingGame = false;
    //doNotQuitPlayingGame = false;
    //currentShotType = SIConstants.ShotTypes.NORMAL;

    // This should make the player sprite visible, alive and center the player image on the screen     
    getPlayer().reset();

    currentLevel = Constants.STARTING_LEVEL;

    // Reset the managers
    //ScoreManager.reset();

    nextLevel();
  }

  public void nextLevel()
  {
    //shotFired = false;
    currentLevel++;

    // Clear the player shots when moving to the next level. This will clear the screen before the intro screen for the next level is displayed
    // This is more or less for eye candy to make a clean transition to the next level.
    clearPlayerShot();

    ((PlayerEntity)getPlayer()).moveToHomePosition();
    getPlayer().setVisible(true);

    // Clear the player input control flags
    playerMovement.reset();

    initializeAsteroidsForCurrentLevel(currentLevel);

    //ufoEntityManager.reset(); // Possibly needed for the UFO that will appear in this game
  }

  private void initializeAsteroidsForCurrentLevel(int currentLevel)
  {
    int numberOfAsteroids = currentLevel + Constants.STARTING_NUMBER_OF_ASTEROIDS;

    if (numberOfAsteroids > Constants.MAX_NUMBER_ASTEROIDS_ON_SCREEN)
    {
      numberOfAsteroids = Constants.MAX_NUMBER_ASTEROIDS_ON_SCREEN;
    }

    for (int i = 0; i < numberOfAsteroids; i++)
    {
      createNewAsteroid();
    }
  }

  /*
   * Display methods for different screens
   */

  // Display for the introduction screen
  private void displayIntroductionMainScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_TITLE);
    boundsIntroductionTitleMsg = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, g);
    g.setColor(Color.BLACK);
    g.drawString(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, (int) (((screenWidth - boundsIntroductionTitleMsg.getWidth()) / 2) + 2), 202);

    g.setColor(new Color(200, 30, 30));
    g.drawString(Constants.INTRO_SCREEN_MAIN_TITLE_MSG, (int) ((screenWidth - boundsIntroductionTitleMsg.getWidth()) / 2), 200);

    int x = 270, y = 15;
    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_CONTROLS);
    g.setColor(Color.YELLOW);
    g.drawString(Constants.INTRO_SCREEN_MAIN_CONTROLS_MSG, x, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_ROTATE_MSG, x + 20, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_THRUST_MSG, x + 20, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_SHIELD_MSG, x + 20, ++y * 20);
    g.drawString(Constants.INTRO_SCREEN_MAIN_FIRE_MSG, x + 20, ++y * 20);

    g.setColor(Color.WHITE);
    boundsIntroductionPowerupsMsg = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_MAIN_POWERUPS_MSG, g);
    g.drawString(Constants.INTRO_SCREEN_MAIN_POWERUPS_MSG, (int) ((screenWidth - boundsIntroductionPowerupsMsg.getWidth()) / 2), 480);

    g.setFont(Constants.FONT_INTRO_SCREEN_MAIN_START);
    boundsIntroductionStateTitle = g.getFontMetrics().getStringBounds(Constants.INTRO_SCREEN_MAIN_START_MSG, g);
    g.setColor(Color.ORANGE);
    g.drawString(Constants.INTRO_SCREEN_MAIN_START_MSG, (int) ((screenWidth - boundsIntroductionStateTitle.getWidth()) / 2), 570);
  }

  private void displayGameStartScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_GAME_START_SCREEN);
    boundsGameStartMsg = g.getFontMetrics().getStringBounds(Constants.MSG_GAME_START, g);
    g.setColor(Color.YELLOW);
    g.drawString(Constants.MSG_GAME_START, (int) ((screenWidth - boundsGameStartMsg.getWidth()) / 2), (int) ((screenHeight - boundsGameStartMsg.getHeight()) / 2));
  }

  private void displayNextLevelScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_NEXT_LEVEL_SCREEN);
    String msg = Constants.MSG_NEXT_LEVEL + GameUtility.lPadZero((currentLevel+1), 2);
    boundsNextLevelMsg = g.getFontMetrics().getStringBounds( msg, g);
    g.setColor(Color.YELLOW);
    g.drawString(msg, (int) ((screenWidth - boundsNextLevelMsg.getWidth()) / 2), (int) ((screenHeight - boundsNextLevelMsg.getHeight()) / 2));
  }
  
  private void displayPlayerDeadScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_PLAYER_DEAD_SCREEN);
    boundsPlayerDeadMsg = g.getFontMetrics().getStringBounds(Constants.MSG_PLAYER_DEAD, g);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_PLAYER_DEAD, (int) ((screenWidth - boundsPlayerDeadMsg.getWidth()) / 2), (int) ((screenHeight - boundsPlayerDeadMsg.getHeight()) / 2));
  }

  private void displayGameOverScreen(Graphics2D g)
  {
    g.setFont(Constants.FONT_GAME_OVER_SCREEN);
    boundsGameOverMsg = g.getFontMetrics().getStringBounds(Constants.MSG_GAMEOVER_SCREEN_GAMEOVER, g);
    g.setColor(Color.WHITE);
    g.drawString(Constants.MSG_GAMEOVER_SCREEN_GAMEOVER, (int) ((screenWidth - boundsGameOverMsg.getWidth()) / 2), (int) ((screenHeight - boundsGameOverMsg.getHeight()) / 2));
  }
}
