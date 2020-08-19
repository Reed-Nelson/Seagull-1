// Conway's Game of Life
// Reed Nelson

/*
 * IMPROVEMENT: Centralize randomColor?
 * IMPROVEMENT: Get cs slider functioning
 * IMPROVEMENT: Add slider to options for probability of life on generate
 * 
 * CS400: FileScene scene
 * CS400: FileHandler class
 * CS400: Make window resizable (or at least change with device running it
 * 
 * IDEA: What if there were a more aesthetic way of coloring the cells 
 * 
 * NOTE: Given up on audio addition after 5 failed implementations
 */

// ! The stage is the window, the scene is the contents of the window
// ! Pane is one type of LAYOUT in javafx, others include StackPane, HBox

// Underlying pane is a BorderPane. In the center of which is a TilePane, and in each tile is
// a Cell, which is a StackPane. The bottom border is a HBox, containing buttons

package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class SeagullApp extends Application {

  public static Stage primaryStage;

  public static final int W = 1920; // window width in px
  public static final int H = 1080; // window height in px
  public static int cellSize = 10; // approximate cell size in px, by default
  // static final int cellSize = calculateCELLSIZE(); // actual px per cell
  public static final int X_CELLS = W / cellSize; // number of cells in x dimension
  public static final int Y_CELLS = (H - 40) / cellSize; // number of cells in y dimension

  // Why are these instance?????
  public static MenuScene menuScene = new MenuScene();
  public static OptionsScene optionsScene; // = new OptionsScene();
  public static GameScene gameScene; // = new GameScene();
  public static FileScene fileScene; // = new FileScene();

  /**
   * Launches the program
   * 
   * @param args
   */
  public static void main(String[] args) {

    launch(args); //Resources/halfmax.csv

  }

  /**
   * Sets up the window
   * 
   * @param primaryStage - the stage on which the program runs
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    SeagullApp.primaryStage = primaryStage;

    primaryStage.setTitle("Seagull");
    primaryStage.setScene(MenuScene.getScene()); // open initially to menu
    primaryStage.setResizable(false);
    primaryStage.show();

    // makes the app exit when the stage is closed
    primaryStage.setOnCloseRequest(e -> {
      Platform.exit();
      System.exit(0);
    });

  }

}
