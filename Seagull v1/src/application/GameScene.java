package application;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

/**
 * The Scene representing the actual game of life
 * 
 * Structure: The root is a BorderPane, contained in its center is a TilePane (Ecosystem), in each
 * tile is a rectangle (Cell). The bottom border of the root is an HBox, containing various
 * controls.
 */
public class GameScene {

  private static final int W = SeagullApp.W;
  private static final int H = SeagullApp.H;
  private static final int X_CELLS = SeagullApp.X_CELLS;
  private static final int Y_CELLS = SeagullApp.Y_CELLS;
  private static int cellSize = SeagullApp.cellSize;

  static int gensPerSec = 10; // generations per second, default value
  private static int gen;
  private IntegerProperty genProp = new SimpleIntegerProperty(0); // Allows live generation counter
  private static boolean running;

  private Ecosystem eco;

  private static Scene gameScene;

  private static BorderPane root;
  private static TilePane ecosystem;
  private static HBox optionBar;

  /**
   * Calls the initialization of the center, and bottom, and puts them together in the root
   */
  GameScene() {

    root = new BorderPane();
    ecosystem = new TilePane();
    optionBar = new HBox();

    initEcosystem();
    initOptionBar();

    root.setCenter(ecosystem);
    root.setBottom(optionBar);

    gameScene = new Scene(root);

  }

  ////////////////////////////// This solution is a real dickbiter
  AnimationTimer genTick = new AnimationTimer() {

    private long lastUpdate = 0;

    @Override
    public void handle(long now) { // now IS sys.nanoTime

      if ((now - lastUpdate) >= 1000000000 / gensPerSec) { // RHS rep's period, in ns
        eco.generate();
        genProp.setValue(++gen);
        lastUpdate = now;
      }

    }

  };
  //////////////////////////////

  /**
   * Initializes the main body of the GameScene
   */
  private void initEcosystem() {

    gen = 0;
    genProp.setValue(gen);

    eco = new Ecosystem(X_CELLS, Y_CELLS, FileScene.getFileIn(), FileScene.getFileOut());

    ecosystem.getChildren().clear();
    ecosystem.setPrefColumns(X_CELLS);
    ecosystem.setPrefRows(Y_CELLS);
    ecosystem.setPrefTileHeight(cellSize);
    ecosystem.setPrefTileWidth(cellSize);

    // create each cell, assign to location
    for (int y = 0; y < Y_CELLS; y++)
      for (int x = 0; x < X_CELLS; x++)
        ecosystem.getChildren().add(eco.getCell(x, y));

    // stops genTick automatically when drag starts
    ecosystem.setOnDragDetected(e -> {
      e.consume();
      genTick.stop();
    });

    // resumes genTick automatically when drag stops
    ecosystem.setOnMouseDragReleased(e -> {
      e.consume();
      if (running) // works because start drag doesn't set running false
        genTick.start();
    });

  }

  /**
   * Initializes the option bar along the bottom edge of the window
   */
  private void initOptionBar() {

    Button menu = new Button("Menu");
    Button repop = new Button("Repopulate");
    Button depop = new Button("Depopulate");
    Button step = new Button("Step");
    Button run = new Button("Run");
    Button stop = new Button("Stop");

    Label genLabel = new Label("Generation: ");
    Label genDisplayed = new Label();

    // Menu button
    menu.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-font-weight: bold;");
    menu.setPrefSize(50, 40);
    // Interaction
    menu.setOnMouseEntered(e -> menu.setTextFill(randomColor()));
    menu.setOnMouseExited(e -> menu.setTextFill(Color.grayRgb(50)));
    menu.setOnAction(e -> {
      SeagullApp.primaryStage.setScene(MenuScene.getScene());
      genTick.stop();
      running = false;
    });

    // Repopulate button
    repop.setOnMouseEntered(e -> repop.setTextFill(randomColor()));
    repop.setOnMouseExited(e -> repop.setTextFill(Color.grayRgb(50)));
    repop.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-font-weight: bold;");
    repop.setPrefSize(80, 40);
    repop.setOnAction(e -> {
      initEcosystem();
    });

    // Depopulate button
    depop.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-font-weight: bold;");
    depop.setPrefSize(80, 40);
    // Interaction
    depop.setOnMouseEntered(e -> depop.setTextFill(randomColor()));
    depop.setOnMouseExited(e -> depop.setTextFill(Color.grayRgb(50)));
    depop.setOnAction(e -> {
      genTick.stop();
      running = false;
      eco.killAll();
      gen = 0;
      genProp.setValue(gen);
    });

    // Step button
    step.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-font-weight: bold;");
    step.setPrefSize(50, 40);
    // Interaction
    step.setOnMouseEntered(e -> step.setTextFill(randomColor()));
    step.setOnMouseExited(e -> step.setTextFill(Color.grayRgb(50)));
    step.setOnAction(e -> {
      eco.generate();
      genProp.setValue(++gen);
    });

    // Run button
    run.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-font-weight: bold;");
    run.setPrefSize(50, 40);
    // Interaction
    run.setOnMouseEntered(e -> run.setTextFill(randomColor()));
    run.setOnMouseExited(e -> run.setTextFill(Color.grayRgb(50)));
    run.setOnAction(e -> {
      genTick.start();
      running = true;
    });

    // Stop button
    stop.setStyle("-fx-padding: 0; -fx-background-color: transparent; -fx-font-weight: bold;");
    stop.setPrefSize(50, 40);
    // Interaction
    stop.setOnMouseEntered(e -> stop.setTextFill(randomColor()));
    stop.setOnMouseExited(e -> stop.setTextFill(Color.grayRgb(50)));
    stop.setOnAction(e -> {
      genTick.stop();
      running = false;
    });

    // Generation label
    genLabel.setMinSize(60, 40);
    genLabel.setTranslateX(W - 480);
    genLabel.setStyle("-fx-font-weight: bold;");

    // Generation Number label
    genDisplayed.textProperty().bind(genProp.asString()); // ! allows live updating gen counter
    genDisplayed.setStyle("-fx-font-weight: bold;");
    genDisplayed.setTranslateX(W - 480);
    genDisplayed.setTranslateY(12);

    optionBar.getChildren().addAll(menu, repop, depop, step, run, stop, genLabel, genDisplayed);
    optionBar.setMinSize(W, 40);
    optionBar.setStyle("-fx-border-color: #404040");
    optionBar.setBackground(
        new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

  }

  /**
   * Returns this scene
   * 
   * @return gameScene
   */
  public static Scene getScene() {

    return gameScene;

  }

  /**
   * Generates a random color, modified to be brighter
   * 
   * @return a random color
   */
  private static Color randomColor() {

    Random rand = new Random();

    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();

    Color randomColor = new Color(r, g, b, 1);

    return randomColor.brighter();

  }

}
