package application;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The Scene representing the options menu
 * 
 * Structure: The root is a BorderPane, whose center is all the this scene's content but the
 * background
 */
public class OptionsScene {

  private static Scene optionsScene;
  
  private static BorderPane root;
  private static VBox center;
  private static Background background;

  private static int cellSize;

  /**
   * Calls the initialization of the background and center and puts them together in the root
   */
  OptionsScene() {

    root = new BorderPane();
    center = new VBox();

    initCenter();
    initBackground();

    root.setPrefSize(SeagullApp.W, SeagullApp.H);
    root.setBackground(background);
    root.setCenter(center);

    optionsScene = new Scene(root);

  }

  /**
   * Initializes the central functional components to be added to the center of the root
   */
  private static void initCenter() {

    Label header = new Label("OPTIONS");
    Label gpsOption = new Label("Generations per second");
    Label csOption = new Label("Cell size");

    Button backButton = new Button("BACK");

    Slider gps = new Slider(0, 25, GameScene.gensPerSec);
    Slider cs = new Slider(10, 120, 30);

    Font headerFont = new Font("Century Gothic", 96);
    Font optionFont = new Font("Century Gothic", 36);
    Font buttonFont = new Font("Century Gothic", 72);

    // Header label
    header.setFont(headerFont);
    header.setTranslateY(-180);
    header.setTextFill(Color.grayRgb(50));

    // Generations per second label
    gpsOption.setFont(optionFont);
    gpsOption.setTranslateY(-30);
    gpsOption.setTextFill(Color.grayRgb(50));

    // Cell size label
    csOption.setFont(optionFont);
    csOption.setTranslateY(75);
    csOption.setTextFill(Color.grayRgb(50));

    // Generations per second slider
    gps.setBlockIncrement(1);
    gps.setMajorTickUnit(1);
    gps.setMinorTickCount(0);
    gps.setMaxSize(860, 10);
    gps.setShowTickLabels(true);
    gps.setShowTickMarks(true);
    gps.setSnapToTicks(true);
    gps.setTranslateY(0);
    gps.valueProperty().addListener((arg, oldVal, newVal) -> {
      GameScene.gensPerSec = (newVal.intValue());
    });

    // Cell size slider
    cs.setBlockIncrement(1);
    cs.setMajorTickUnit(10);
    cs.setMinorTickCount(0);
    cs.setMaxSize(860, 10);
    cs.setShowTickLabels(true);
    cs.setShowTickMarks(true);
    cs.setSnapToTicks(true);
    cs.setTranslateY(100);
    cs.valueProperty().addListener((arg, oldVal, newVal) -> {
      cellSize = newVal.intValue();
      roundCellSize();
      SeagullApp.cellSize = cellSize;
    });

    // Back button
    backButton.setTranslateY(210);
    backButton.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
    backButton.setFont(buttonFont);
    backButton.setTextFill(Color.grayRgb(50));
    backButton.setOnMouseEntered(e -> backButton.setTextFill(randomColor()));
    backButton.setOnMouseExited(e -> backButton.setTextFill(Color.grayRgb(50)));
    backButton.setOnAction(e -> SeagullApp.primaryStage.setScene(MenuScene.getScene()));

    center.setAlignment(Pos.CENTER);
    center.getChildren().addAll(header, gpsOption, gps, csOption, cs, backButton);

  }

  /**
   * Initializes the background
   */
  private static void initBackground() {

    Image bgImage = new Image("file:Resources/optBG.png");
    BackgroundSize bgSize =
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
    BackgroundImage bgi = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);

    background = new Background(bgi);

  }

  /**
   * Returns this scene
   * 
   * @return optionsScene
   */
  public static Scene getScene() {

    return optionsScene;

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

    Color randomColor = new Color(r, g, b, 0.8);

    return randomColor.brighter();

  }

  private static void roundCellSize() {

    while (120 % cellSize != 0) // GCF(W, H) % cellSize != 0 // 120 is the GCF of 1920x1080
      cellSize++;

  }

}
