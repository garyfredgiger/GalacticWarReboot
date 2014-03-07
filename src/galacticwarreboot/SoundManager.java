package galacticwarreboot;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import paulscode.sound.SoundBuffer;

;

public class SoundManager
{
  // Resource names used to reference a source 
  public static final String   SOUND_RESOURCE_PLAYER_1_SHOOTING          = "player_shot_1";
  public static final String   SOUND_RESOURCE_PLAYER_2_SHOOTING          = "player_shot_2";
  public static final String   SOUND_RESOURCE_PLAYER_3_SHOOTING          = "player_shot_3";
  public static final String   SOUND_RESOURCE_PLAYER_4_SHOOTING          = "player_shot_4";
  public static final String   SOUND_RESOURCE_PLAYER_5_SHOOTING          = "player_shot_5";

  public static final String   SOUND_RESOURCE_PLAYER_THE_BOMB_EXPLOSION  = "the_bomb_explosion";
  public static final String   SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_3    = "bomb_coundown_3";
  public static final String   SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_2    = "bomb_coundown_2";

  public static final String   SOUND_RESOURCE_PLAYER_HULL_HIT            = "player_hull_hit";
  public static final String   SOUND_RESOURCE_PLAYER_SHIELDS_HIT         = "hit24.ogg";

  public static final String   SOUND_RESOURCE_ASTEROID_INITIAL_EXPLOSION = "asteroid_initial_explosion";
  public static final String   SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION = "asteroid_smaller_explosion";
  public static final String   SOUND_RESOURCE_ASTEROID_TINY_EXPLOSION    = "asteroid_tiny_explosion";

  public static final String   SOUND_RESOURCE_UFO_SHIELDS_HIT            = "ufo_shields_hit";
  public static final String   SOUND_RESOURCE_UFO_SHIELDS_FAIL           = "ufo_shields_fail";
  public static final String   SOUND_RESOURCE_UFO_SHOOTING               = "ufo_shooting";

  public static final String   SOUND_RESOURCE_POWERUP_SUPER_SHIELDS      = "powerup_super_shields";
  public static final String   SOUND_RESOURCE_POWERUP_ATTRIBUTE_PICKUP   = "powerup_attribute_pickup";
  public static final String   SOUND_RESOURCE_POWERUP_POINT_BONUS        = "powerup_point_bonus";
  public static final String   SOUND_RESOURCE_POWERUP_FULL_SHIELD        = "powerup_full_shield";
  public static final String   SOUND_RESOURCE_POWERUP_FULL_HEALH         = "powerup_full_health";

  public static final String   SOUND_RESOURCE_PLAYER_SHIP_EXPLOSION          = "player_ship_explosion";
  public static final String   SOUND_RESOURCE_UFO_EXPLOSION                  = "ufo_explosion";
  public static final String   SOUND_RESOURCE_PLAYER_SHIELDS_DEPLETED        = "player_shields_depleted";
  public static final String   SOUND_RESOURCE_PLAYER_WEAPON_ATTRIBUTE_EMPTY  = "player_weapon_attribute_empty";
  public static final String   SOUND_RESOURCE_NEXT_WAVE                      = "next_wave";
  
  /*
   * Sounds that will be created a sources  
   */
  public static final String   SOUND_FILE_PLAYER_1_SHOT                  = "shoot13.ogg";                  // Final sound added to code
  public static final String   SOUND_FILE_PLAYER_2_SHOT                  = "shoot36.ogg";                  // Final sound added to code         
  public static final String   SOUND_FILE_PLAYER_3_SHOT                  = "shoot11.ogg";                  // Final sound added to code
  public static final String   SOUND_FILE_PLAYER_4_SHOT                  = "shoot41.ogg";                  // Final sound added to code     
  public static final String   SOUND_FILE_PLAYER_5_SHOT                  = "shoot51.ogg";                  // Final sound added to code

  public static final String   SOUND_FILE_PLAYER_THE_BOMB_EXPLOSION      = "explosion59andexplosion57.ogg"; // Final sound added to code
  public static final String   SOUND_FILE_PLAYER_BOMB_COUNTDOWN_3        = "blip01.ogg";                   // Final sound added to code
  public static final String   SOUND_FILE_PLAYER_BOMB_COUNTDOWN_2        = "blip02.ogg";                   // Final sound added to code
  
  public static final String   SOUND_FILE_PLAYER_HULL_HIT                = "hit26.ogg";
  public static final String   SOUND_FILE_PLAYER_SHIELDS_HIT             = "hit24.ogg";                    // Final sound added to code // TODO: May need to be made into a source if too many asteroid hit this 
  
  public static final String   SOUND_FILE_ASTEROID_INITIAL_EXPLOSION     = "explosion53.ogg";              // Final sound added to code // Played when the initial asteroid is shot/hit and breaks apart
  public static final String   SOUND_FILE_ASTEROID_SMALLER_EXPLOSION     = "explosion61.ogg";              // Final sound added to code // Played when the smaller pieces are shot/hit and break apart  
  public static final String   SOUND_FILE_ASTEROID_TINY_EXPLOSION        = "explosion63modified3.ogg";//"explosion63.ogg";              // Final sound added to code // Played when the tiniest asteroid piece is hit

  public static final String   SOUND_FILE_UFO_SHIELDS_HIT                = "misc87.ogg";                   // Final sound added to code
  public static final String   SOUND_FILE_UFO_SHIELDS_FAIL               = "misc25.ogg";                   // Final sound added to code
  public static final String   SOUND_FILE_UFO_SHOOTING                   = "shoot30.ogg";

  public static final String   SOUND_FILE_POWERUP_SUPER_SHIELDS          = "misc6.ogg";
  public static final String   SOUND_FILE_POWERUP_ATTRIBUTE_PICKUP       = "powerup35.ogg";                // Final sound added to code // Generic sound for powerup pickups  
  public static final String   SOUND_FILE_POWERUP_POINT_BONUS            = "pickup25.ogg";                 // Final sound added to code 
  public static final String   SOUND_FILE_POWERUP_FULL_SHIELD            = "misc53.ogg";                   // Final sound added to code
  public static final String   SOUND_FILE_POWERUP_FULL_HEALH             = "powerup37.ogg";                // Final sound added to code

  public static final String   SOUND_FILE_PLAYER_SHIP_EXPLOSION          = "explosion2.ogg";               // // Final sound added to code
  public static final String   SOUND_FILE_UFO_EXPLOSION                  = "explosion12.ogg";              // Final sound added to code
  public static final String   SOUND_FILE_PLAYER_SHIELDS_DEPLETED        = "misc31.ogg";                   // Final sound added to code
  public static final String   SOUND_FILE_PLAYER_WEAPON_ATTRIBUTE_EMPTY  = "misc92.ogg";                   // Sound added to code but could have a better sound
  public static final String   SOUND_FILE_NEXT_WAVE                      = "misc17Modified.ogg";           // Sound added to code, but may need to be changed

  /*
   *  Sounds that will be played using quick play
   */
  
  //////
  // TODO: Add sound for player ship's hull getting hit
  // TODO: Add sound for player picking up health/shield increase
  // TODO: Add sound for moving to next wave

  // Sound filenames for UFOs
  //public static final String   SOUND_FILE_UFO_HULL_BEING_HIT             = "hit7.ogg";  // TODO: May not need this sound. Can play UFO explosion instead  

  public static final String   BACKGROUND_MUSIC                          = "background";
  public static final String   BACKGROUND_MUSIC_OGG_FILE                 = "beats.ogg";

  private SoundSystem          mySoundSystem;

  /*
   *  Sound events not needed anymore
   */

  // This sound is not needed b/c the sound of the asteroid breaking apart will be played, which will also work when the ships hull gets hit.
  //public static final String   SOUND_FILE_PLAYER_HULL_HIT                = "hit18.ogg";

  // Default Settings for Quick Play
  private static final boolean NO_PRIORITY                               = false;
  private static final boolean PRIORITY                                  = true;
  private static final boolean DO_NOT_LOOP                           = false;

  float previousPitch;
  
  public SoundManager()
  {
    boolean jSCompatible = SoundSystem.libraryCompatible(LibraryJavaSound.class);

    Class<?> libraryType;

    // Add libraries and codec used to play sounds in this game
    try
    {
      SoundSystemConfig.setNumberStreamingChannels(4);
      SoundSystemConfig.setNumberNormalChannels(28);

      SoundSystemConfig.addLibrary(LibraryJavaSound.class);
      SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
    }
    catch (SoundSystemException e1)
    {
      System.out.println("error linking with the pluggins");
    }

    // Bind the sound system to the respective sound library
    try
    {
      libraryType = LibraryJavaSound.class; // Java Sound

      mySoundSystem = new SoundSystem(libraryType);

      System.out.print("Initializing Sound Library...");
      while(!mySoundSystem.initialized())
      {
        
      }
      System.out.println("Done!");
      
      // TODO: Load these sounds when the game begins. Possibly make it as part of the game reset method

      // Not sure if this is needed, but i
      // TODO: Do I need to pre-load the sounds if they are to be made into a new source?
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_1_SHOT);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_2_SHOT);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_3_SHOT);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_4_SHOT);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_5_SHOT);

      mySoundSystem.loadSound(SOUND_FILE_PLAYER_THE_BOMB_EXPLOSION);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_BOMB_COUNTDOWN_3);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_BOMB_COUNTDOWN_2);

      mySoundSystem.loadSound(SOUND_FILE_PLAYER_HULL_HIT);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_SHIELDS_HIT);

      mySoundSystem.loadSound(SOUND_FILE_ASTEROID_INITIAL_EXPLOSION);
      mySoundSystem.loadSound(SOUND_FILE_ASTEROID_SMALLER_EXPLOSION);
      mySoundSystem.loadSound(SOUND_FILE_ASTEROID_TINY_EXPLOSION);

      mySoundSystem.loadSound(SOUND_FILE_UFO_SHIELDS_HIT);
      mySoundSystem.loadSound(SOUND_FILE_UFO_SHIELDS_FAIL);
      mySoundSystem.loadSound(SOUND_FILE_UFO_SHOOTING);

      mySoundSystem.loadSound(SOUND_FILE_POWERUP_SUPER_SHIELDS);
      mySoundSystem.loadSound(SOUND_FILE_POWERUP_ATTRIBUTE_PICKUP);
      mySoundSystem.loadSound(SOUND_FILE_POWERUP_POINT_BONUS);
      mySoundSystem.loadSound(SOUND_FILE_POWERUP_FULL_SHIELD);
      mySoundSystem.loadSound(SOUND_FILE_POWERUP_FULL_HEALH);
      
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_SHIP_EXPLOSION);
      mySoundSystem.loadSound(SOUND_FILE_UFO_EXPLOSION);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_SHIELDS_DEPLETED);
      mySoundSystem.loadSound(SOUND_FILE_PLAYER_WEAPON_ATTRIBUTE_EMPTY);
      mySoundSystem.loadSound(SOUND_FILE_NEXT_WAVE);

      createNewSource(NO_PRIORITY, SOUND_RESOURCE_PLAYER_1_SHOOTING, SOUND_FILE_PLAYER_1_SHOT, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_PLAYER_2_SHOOTING, SOUND_FILE_PLAYER_2_SHOT, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_PLAYER_3_SHOOTING, SOUND_FILE_PLAYER_3_SHOT, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_PLAYER_4_SHOOTING, SOUND_FILE_PLAYER_4_SHOT, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_PLAYER_5_SHOOTING, SOUND_FILE_PLAYER_5_SHOT, DO_NOT_LOOP);

      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_THE_BOMB_EXPLOSION, SOUND_FILE_PLAYER_THE_BOMB_EXPLOSION, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_3, SOUND_FILE_PLAYER_BOMB_COUNTDOWN_3, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_BOMB_COUNTDOWN_2, SOUND_FILE_PLAYER_BOMB_COUNTDOWN_2, DO_NOT_LOOP);

      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_HULL_HIT, SOUND_FILE_PLAYER_HULL_HIT, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_SHIELDS_HIT, SOUND_FILE_PLAYER_SHIELDS_HIT, DO_NOT_LOOP);

      createNewSource(NO_PRIORITY, SOUND_RESOURCE_ASTEROID_INITIAL_EXPLOSION, SOUND_FILE_ASTEROID_INITIAL_EXPLOSION, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_ASTEROID_SMALLER_EXPLOSION, SOUND_FILE_ASTEROID_SMALLER_EXPLOSION, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_ASTEROID_TINY_EXPLOSION, SOUND_FILE_ASTEROID_TINY_EXPLOSION, DO_NOT_LOOP);

      createNewSource(PRIORITY, SOUND_RESOURCE_UFO_SHIELDS_HIT, SOUND_FILE_UFO_SHIELDS_HIT, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_UFO_SHIELDS_FAIL, SOUND_FILE_UFO_SHIELDS_FAIL, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_UFO_SHOOTING, SOUND_FILE_UFO_SHOOTING, DO_NOT_LOOP);      

      createNewSource(NO_PRIORITY, SOUND_RESOURCE_POWERUP_SUPER_SHIELDS, SOUND_FILE_POWERUP_SUPER_SHIELDS, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_POWERUP_ATTRIBUTE_PICKUP, SOUND_FILE_POWERUP_ATTRIBUTE_PICKUP, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_POWERUP_POINT_BONUS, SOUND_FILE_POWERUP_POINT_BONUS, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_POWERUP_FULL_SHIELD, SOUND_FILE_POWERUP_FULL_SHIELD, DO_NOT_LOOP);
      createNewSource(NO_PRIORITY, SOUND_RESOURCE_POWERUP_FULL_HEALH, SOUND_FILE_POWERUP_FULL_HEALH, DO_NOT_LOOP);
      
      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_SHIP_EXPLOSION, SOUND_FILE_PLAYER_SHIP_EXPLOSION, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_UFO_EXPLOSION, SOUND_FILE_UFO_EXPLOSION, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_SHIELDS_DEPLETED, SOUND_FILE_PLAYER_SHIELDS_DEPLETED, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_PLAYER_WEAPON_ATTRIBUTE_EMPTY, SOUND_FILE_PLAYER_WEAPON_ATTRIBUTE_EMPTY, DO_NOT_LOOP);
      createNewSource(PRIORITY, SOUND_RESOURCE_NEXT_WAVE, SOUND_FILE_NEXT_WAVE, DO_NOT_LOOP);
      
    }
    catch (SoundSystemException e)
    {
      System.out.println("The Java Sound Library could be loaded...no sound will be played.");
      libraryType = Library.class; // "No Sound, Silent Mode"
      e.printStackTrace();
    }
  }

  //mySoundSystem.newStreamingSource(true, BACKGROUND_MUSIC, BACKGROUND_MUSIC_OGG_FILE, false, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, 0);
  public void createNewStreamingSource(String resourceName, String filename)
  {
    //System.out.println("Creating new Stream " + BACKGROUND_MUSIC);
  }

  public void playBackgroundMusic(String resourceName)
  {

  }

  public void stopBackgroundMusic(String resourceName)
  {

  }

  public void fadeBackgroundMusic(String resourceName, long timeToFadeInMs)
  {

  }

//  public void quickPlay(String soundFilename)
//  {
//    //mySoundSystem.quickPlay(NO_PRIORITY, soundFilename, DO_NOT_LOOP, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
//    
//    // TODO: Turned off for now
//    quickPlay(soundFilename, NO_PRIORITY);
//  }
//
//  public void quickPlay(String soundFilename, boolean priority)
//  {
//    // TODO: Turned off for now
//    mySoundSystem.quickPlay(priority, soundFilename, DO_NOT_LOOP, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
//  }

  public void playSound(String sourceName)
  {
    //    if (mySoundSystem.playing(sourceName))
    //    {
    //      mySoundSystem.stop(sourceName);
    //    }
    //    mySoundSystem.rewind(sourceName);
    //    mySoundSystem.play(sourceName);

    // TODO: Turned off for now
    if (!mySoundSystem.playing(sourceName))
    {
      mySoundSystem.play(sourceName);
      mySoundSystem.rewind(sourceName); // NOTE: If this is not called then the sound only plays once
    }
  }

  // Pitch does not seem to be working
//  public void playSound(String sourceName, float pitch)
//  { 
//    if (!mySoundSystem.playing(sourceName))
//    {
//      previousPitch = mySoundSystem.getPitch(sourceName);
//      mySoundSystem.setPitch(sourceName, pitch);
//      mySoundSystem.play(sourceName);
//      mySoundSystem.rewind(sourceName); // NOTE: If this is not called then the sound only plays once
//      //mySoundSystem.setPitch(sourceName, previousPitch);
//    }
//  }
  
  public void cleanup()
  {
    mySoundSystem.cleanup();
  }
  
  private void createNewSource(boolean priority, String resourceName, String sounfFilename, boolean shouldLoop)
  {
    mySoundSystem.newSource(priority, resourceName, sounfFilename, shouldLoop, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
  }
}
