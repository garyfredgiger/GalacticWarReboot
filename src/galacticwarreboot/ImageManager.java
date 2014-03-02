package galacticwarreboot;

import game.framework.utilities.GameUtility;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;

public class ImageManager
{
  private static ImageObserver imageObserver;
  private static HashMap<String, Image> imageStore = new HashMap<String, Image>(75);

  public static void loadAllImages(ImageObserver io)
  {
    imageObserver = io;
    ImageManager.loadImage(Constants.FILENAME_BIG_ASTEROID_1);
    ImageManager.loadImage(Constants.FILENAME_BIG_ASTEROID_2);
    ImageManager.loadImage(Constants.FILENAME_BIG_ASTEROID_3);
    ImageManager.loadImage(Constants.FILENAME_BIG_ASTEROID_4);
    ImageManager.loadImage(Constants.FILENAME_BIG_ASTEROID_5);
    ImageManager.loadImage(Constants.FILENAME_MEDIUM_ASTEROID_1);
    ImageManager.loadImage(Constants.FILENAME_MEDIUM_ASTEROID_2);
    ImageManager.loadImage(Constants.FILENAME_SMALL_ASTEROID_1);
    ImageManager.loadImage(Constants.FILENAME_SMALL_ASTEROID_2);
    ImageManager.loadImage(Constants.FILENAME_SMALL_ASTEROID_3);
    ImageManager.loadImage(Constants.FILENAME_TINY_ASTEROID_1);
    ImageManager.loadImage(Constants.FILENAME_TINY_ASTEROID_2);
    ImageManager.loadImage(Constants.FILENAME_TINY_ASTEROID_3);
    ImageManager.loadImage(Constants.FILENAME_TINY_ASTEROID_4);
    ImageManager.loadImage(Constants.FILENAME_BACKGROUND);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_SHIELD);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_HEALTH);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_250);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_500);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_1000);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_SUPER_SHIELD);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_FULL_HEALTH);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_THE_BOMB);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_SHIELD_FULL_RESTORE);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_20);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_INCREASE_HEALTH_CAPACITY_40);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_20);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_INCREASE_SHIELD_CAPACITY_40);
    ImageManager.loadImage(Constants.FILENAME_SUPER_SHIELD);
    ImageManager.loadImage(Constants.FILENAME_HUD_SUPERSHIELD_ICON);
    ImageManager.loadImage(Constants.FILENAME_HUD_THE_BOMB_ICON);
    ImageManager.loadImage(Constants.FILENAME_UFO);
    ImageManager.loadImage(Constants.FILENAME_UFO_SHORTY);
    ImageManager.loadImage(Constants.FILENAME_UFO_SHOT);
    ImageManager.loadImage(Constants.FILENAME_UFO_SHORTY_SHOT);
    ImageManager.loadImage(Constants.FILENAME_PLASMA_SHOT);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_GUN);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_GUN_2);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_GUN_3);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_GUN_4);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_GUN_5);
    ImageManager.loadImage(Constants.FILENAME_BAR_HEALTH);
    ImageManager.loadImage(Constants.FILENAME_BAR_SHIELD);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_ENGINE_2);
    ImageManager.loadImage(Constants.FILENAME_POWERUP_ENGINE_3);
    ImageManager.loadImage(Constants.FILENAME_UFO_SHIELD_WEAK);
    ImageManager.loadImage(Constants.FILENAME_UFO_SHIELD_OK);
    ImageManager.loadImage(Constants.FILENAME_UFO_SHIELD_STRONG);
    ImageManager.loadImage(Constants.FILENAME_SPACESHIP_THRUST1);
    ImageManager.loadImage(Constants.FILENAME_SPACESHIP_THRUST2);
    ImageManager.loadImage(Constants.FILENAME_SPACESHIP_THRUST3);
    ImageManager.loadImage(Constants.FILENAME_SPACESHIP_SHIELD);
    ImageManager.loadImage(Constants.FILENAME_BAR_FRAME_10);
    ImageManager.loadImage(Constants.FILENAME_BAR_FRAME_20);
    ImageManager.loadImage(Constants.FILENAME_BAR_FRAME_40);
  }
  
  /**
   * 
   * Used to retrieve a specific image given the images filename.
   * 
   * If the image is already stored in the imageStore, it will be retrieved from there. If it does not already 
   * exist in the imageStore, it will first be retrieved from its location (i.e., resource folder, disk, etc...),
   * placed in the image store and then returned to the caller. 
   * 
   * @param filename - The filename of the image to retrieve
   *  
   * @return The requested image 
   */
  public static Image getImage(String filename)
  {
    try
    {
      // Check if the image already exists in the image store. If it does then return the image

      // If it does not then attempt to fetch the image. If the image is able to be retrieved, store it in 
      // the image store and return it to the caller. If the image cannot be founf throw an error.
      if (!imageStore.containsKey(filename))
      {
        loadImage(filename);
      }
    }
    catch (NullPointerException e)
    {
      System.out.println(e.getMessage());
    }
    catch (Exception e)
    {
      System.out.println("Generic Exception Occured.");
      e.printStackTrace();
    }

    return imageStore.get(filename);
  }

  public static int getWidth(String filename)
  {
    Image image = getImage(filename);

    return (image != null ? image.getWidth(imageObserver) : 0);
  }

  public static int getHeight(String filename)
  {
    Image image = getImage(filename);

    return (image != null ? image.getHeight(imageObserver) : 0);
  }
  
  public static Image getRandomBigAsteroidImage()
  {
    Image asteroidImage = null;
    int whichAsteroid = GameUtility.random.nextInt(Constants.NUMBER_OF_BIG_ASTEROID_IMAGES);
    
    switch(whichAsteroid)
    {
      case 0:
        asteroidImage = getImage(Constants.FILENAME_BIG_ASTEROID_1);
        break;
      case 1:
        asteroidImage = getImage(Constants.FILENAME_BIG_ASTEROID_2);
        break;
      case 2:
        asteroidImage = getImage(Constants.FILENAME_BIG_ASTEROID_3);
        break;  
      case 3:
        asteroidImage = getImage(Constants.FILENAME_BIG_ASTEROID_4);
        break;
      default:
        asteroidImage = getImage(Constants.FILENAME_BIG_ASTEROID_5);
    }

    return asteroidImage;
  }
  
  public static Image getRandomMediumAsteroidImage()
  {
    Image asteroidImage = null;
    int whichAsteroid = GameUtility.random.nextInt(Constants.NUMBER_OF_MEDIUM_ASTEROID_IMAGES);
    
    switch(whichAsteroid)
    {
      case 0:
        asteroidImage = getImage(Constants.FILENAME_MEDIUM_ASTEROID_1);
        break;
      default:
        asteroidImage = getImage(Constants.FILENAME_MEDIUM_ASTEROID_2);
    }

    return asteroidImage;
  }
  
  public static Image getRandomSmallAsteroidImage()
  {
    Image asteroidImage = null;
    int whichAsteroid = GameUtility.random.nextInt(Constants.NUMBER_OF_SMALL_ASTEROID_IMAGES);
    
    switch(whichAsteroid)
    {
      case 0:
        asteroidImage = getImage(Constants.FILENAME_SMALL_ASTEROID_1);
        break;
      case 1:
        asteroidImage = getImage(Constants.FILENAME_SMALL_ASTEROID_2);
        break;
      default:
        asteroidImage = getImage(Constants.FILENAME_SMALL_ASTEROID_3);
    }

    return asteroidImage;
  }
  
  public static Image getRandomTinyAsteroidImage()
  {
    Image asteroidImage = null;
    int whichAsteroid = GameUtility.random.nextInt(Constants.NUMBER_OF_TINY_ASTEROID_IMAGES);
    
    switch(whichAsteroid)
    {
      case 0:
        asteroidImage = getImage(Constants.FILENAME_TINY_ASTEROID_1);
        break;
      case 1:
        asteroidImage = getImage(Constants.FILENAME_TINY_ASTEROID_2);
        break;
      case 2:
        asteroidImage = getImage(Constants.FILENAME_TINY_ASTEROID_3);
        break;
      default:
        asteroidImage = getImage(Constants.FILENAME_TINY_ASTEROID_4);
    }

    return asteroidImage;
  }
  
  private static void loadImage(String filename)
  {
    Toolkit tk = Toolkit.getDefaultToolkit();

    Image image = tk.getImage(getURL(filename));
    
    if (image == null)
    {
      throw new NullPointerException("Image with filename ' + filename + ' could not be loaded.");
    }

    while (image.getWidth(imageObserver) <= 0);

    //System.out.println("ImageManager::loadImage() Image '" + filename + "' loaded with dimensions " + image.getWidth(imageObserver) + " by " + image.getHeight(imageObserver) + ".");

    imageStore.put(filename, image);
  }
  
  private static URL getURL(String filename)
  {
    URL url = null;
    url = ImageManager.class.getClassLoader().getResource(filename);
    
    if (url == null)
    {
      System.out.println("\tImageManager::getURL url is NULL");
    }
    else
    {
      System.out.println("\tImageManager::getURL" + url.toString());
    }
    return url;
  }
}
