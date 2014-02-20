package galacticwarreboot;

import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JApplet
{
  /**
   * Used during de-serialization to verify that the sender and receiver of a
   * serialized object have loaded classes for that object that are compatible
   * with respect to serialization. If the receiver has loaded a class for the
   * object that has a different serialVersionUID than that of the
   * corresponding sender's class, then de-serialization will result in an
   * InvalidClassException.
   * 
   * See links below for more details:
   * http://stackoverflow.com/questions/285793
   * /what-is-a-serialversionuid-and-why-should-i-use-it
   * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
   */
  private static final long serialVersionUID = 1L;

  private Asteroids         galacticWarReboot;
  private GameScreen        gameScreen;
  
  @Override
  public void init()
  {
    System.out.println("init() Called!");

    // Setup the JFrame and panel used in this game
    gameScreen = new GameScreen();
    gameScreen.setPreferredSize(gameScreen.getPreferredSize());

    this.setContentPane(gameScreen);
    this.setVisible(true);

    galacticWarReboot = new Asteroids(gameScreen, this.gameScreen, GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    galacticWarReboot.gameInit();
    
    try
    {
      // Use the event dispatch thread to build the UI for thread-safety.
      SwingUtilities.invokeLater(new Runnable()
      {
        @Override
        public void run()
        {
          // This contains the thread that runds the game loop
          galacticWarReboot.gameStart();
        }
      });
    }
    catch (Exception e)
    {
      System.err.println("createGUI didn't complete successfully");
    }
  }

  @Override
  public void start()
  {
    System.out.println("start() Called!");
  }
  
  //  public static void main(String[] args)
  //  {
  //    // Use the event dispatch thread to build the UI for thread-safety.
  //    SwingUtilities.invokeLater(new Runnable()
  //    {
  //      @Override
  //      public void run()
  //      {
  //        new Main();
  //      }
  //    });
  //  }

  /////////////////////////////////////////////////////////////////////////////
  //   ___                          ____ _               
  //  |_ _|_ __  _ __   ___ _ __   / ___| | __ _ ___ ___ 
  //   | || '_ \| '_ \ / _ \ '__| | |   | |/ _` / __/ __|
  //   | || | | | | | |  __/ |    | |___| | (_| \__ \__ \
  //  |___|_| |_|_| |_|\___|_|     \____|_|\__,_|___/___/
  //                                                     
  //   ____                                     _   _             
  //  |  _ \ ___ _ __  _ __ ___  ___  ___ _ __ | |_(_)_ __   __ _ 
  //  | |_) / _ \ '_ \| '__/ _ \/ __|/ _ \ '_ \| __| | '_ \ / _` |
  //  |  _ <  __/ |_) | | |  __/\__ \  __/ | | | |_| | | | | (_| |
  //  |_| \_\___| .__/|_|  \___||___/\___|_| |_|\__|_|_| |_|\__, |
  //            |_|                                         |___/ 
  //       _ ____                  _ 
  //      | |  _ \ __ _ _ __   ___| |
  //   _  | | |_) / _` | '_ \ / _ \ |
  //  | |_| |  __/ (_| | | | |  __/ |
  //   \___/|_|   \__,_|_| |_|\___|_|
  //                                 
  /////////////////////////////////////////////////////////////////////////////

  //Custom drawing panel, written as an inner class.
  public class GameScreen extends JPanel implements KeyListener, IRender
  {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Constructor
    public GameScreen()
    {
      setFocusable(true);  // so that can receive key-events
      requestFocus();
      addKeyListener(this);
    }

    // Override paintComponent to do custom drawing.
    // Called back by repaint().
    @Override
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);   // paint background

      // Draw the game objects
      galacticWarReboot.gameDraw((Graphics2D) g);

      Toolkit.getDefaultToolkit().sync();
    }

    /////////////////////////////////////////////////////////////////////////////
    //   _  __            _____                 _   
    //  | |/ /___ _   _  | ____|_   _____ _ __ | |_ 
    //  | ' // _ \ | | | |  _| \ \ / / _ \ '_ \| __|
    //  | . \  __/ |_| | | |___ \ V /  __/ | | | |_ 
    //  |_|\_\___|\__, | |_____| \_/ \___|_| |_|\__|
    //            |___/                             
    //   _   _                 _ _               
    //  | | | | __ _ _ __   __| | | ___ _ __ ___ 
    //  | |_| |/ _` | '_ \ / _` | |/ _ \ '__/ __|
    //  |  _  | (_| | | | | (_| | |  __/ |  \__ \
    //  |_| |_|\__,_|_| |_|\__,_|_|\___|_|  |___/
    //                                             
    /////////////////////////////////////////////////////////////////////////////

    // KeyEvent handlers
    @Override
    public void keyPressed(KeyEvent e)
    {
      galacticWarReboot.gameKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
      // Process Debug key    
      if (e.getKeyCode() == KeyEvent.VK_BACK_QUOTE && e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK)
      {
        galacticWarReboot.displayDebugInfo = !galacticWarReboot.displayDebugInfo;

        if (galacticWarReboot.displayDebugInfo)
        {
          System.out.println("Debugging Enabled.");
        }
        else
        {
          System.out.println("Debugging Disabled.");
        }
      }

      galacticWarReboot.gameKeyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
      // Not Used
    }

    @Override
    public Dimension getPreferredSize()
    {
      return new Dimension(GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    }

    @Override
    public void renderScreen()
    {
      repaint();
    }
  }
}
