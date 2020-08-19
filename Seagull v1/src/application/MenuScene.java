package application;

import java.util.Random;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The Scene representing the menu screen
 * 
 * Structure: The root is a BorderPane, in its center is a VBox (center), containing central
 * elements. The bottom border of the root is an HBox (info), containing the author's name and
 * version.
 */
public class MenuScene {

  private static Scene menuScene;

  private static BorderPane root;
  private static HBox info;
  private static VBox center;
  private static Background background;

  /**
   * Calls the initialization of the background, center, and bottom, and puts them together in the
   * root
   */
  MenuScene() {

    root = new BorderPane();
    center = new VBox();
    info = new HBox();


    initCenter();
    initInfo();
    initBackground();

    root.setPrefSize(SeagullApp.W, SeagullApp.H);
    root.setBackground(background);
    root.setCenter(center);
    root.setBottom(info);

    menuScene = new Scene(root);

  }

  /**
   * Initializes the central functional components to be added to the center of the root
   */
  private static void initCenter() {

    Label title = new Label("   Conway's    \nGame of Life");

    Button startButton = new Button("START");
    Button optionsButton = new Button("OPTIONS");
    Button fileButton = new Button("IMPORT/EXPORT");
    Button quitButton = new Button("QUIT");

    Font titleFont = new Font("Tahoma", 125);
    Font buttonFont = new Font("Century Gothic", 72);

    title.setFont(titleFont);
    title.setStyle("-fx-font-weight: bold;");
    title.setTextFill(Color.grayRgb(50));
    title.setTranslateY(-100);
    title.setOnMouseClicked(e -> title.setTextFill(randomColor()));

    // Start button
    startButton.setTranslateY(85);
    startButton.setStyle("-fx-padding: 0; -fx-background-color: transparent; ");
    startButton.setFont(buttonFont);
    startButton.setTextFill(Color.grayRgb(50));
    startButton.setOnMouseEntered(e -> startButton.setTextFill(randomColor()));
    startButton.setOnMouseExited(e -> startButton.setTextFill(Color.grayRgb(50)));
    startButton.setOnAction(e -> {
      SeagullApp.gameScene = new GameScene();
      SeagullApp.primaryStage.setScene(GameScene.getScene());
    });

    // Options button
    optionsButton.setTranslateY(110);
    optionsButton.setFont(buttonFont);
    optionsButton.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
    optionsButton.setTextFill(Color.grayRgb(50));
    optionsButton.setOnMouseEntered(e -> optionsButton.setTextFill(randomColor()));
    optionsButton.setOnMouseExited(e -> optionsButton.setTextFill(Color.grayRgb(50)));
    optionsButton.setOnAction(e -> {
      SeagullApp.optionsScene = new OptionsScene();
      SeagullApp.primaryStage.setScene(OptionsScene.getScene());
    });

    // File button
    fileButton.setTranslateY(135);
    fileButton.setFont(buttonFont);
    fileButton.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
    fileButton.setTextFill(Color.grayRgb(50));
    fileButton.setOnMouseEntered(e -> fileButton.setTextFill(randomColor()));
    fileButton.setOnMouseExited(e -> fileButton.setTextFill(Color.grayRgb(50)));
    fileButton.setOnAction(e -> {
      SeagullApp.fileScene = new FileScene();
      SeagullApp.primaryStage.setScene(FileScene.getScene());
    });
    
    // Quit button
    quitButton.setTranslateY(160);
    quitButton.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
    quitButton.setFont(buttonFont);
    quitButton.setTextFill(Color.grayRgb(50));
    quitButton.setOnMouseEntered(e -> quitButton.setTextFill(randomColor()));
    quitButton.setOnMouseExited(e -> quitButton.setTextFill(Color.grayRgb(50)));
    quitButton.setOnAction(e -> {
      Platform.exit();
      System.exit(0);
    });

    center.setAlignment(Pos.CENTER);
    center.getChildren().addAll(title, startButton, optionsButton, fileButton, quitButton);

  }

  /** 
   * Initializes the 'info' portion, to be put along the lower border of the root
   */
  private static void initInfo() {

    Label credit = new Label("Created by Reed Nelson");
    Label version = new Label("Version 1.0");
    Font infoFont = new Font("Century Gothic", 24);

    // Author text
    credit.setPrefSize(300, 50);
    credit.setTranslateX(15);
    credit.setFont(infoFont);
    credit.setTextFill(Color.grayRgb(50));
    credit.setAlignment(Pos.CENTER_LEFT);

    // Version text
    version.setPrefSize(150, 50);
    version.setFont(infoFont);
    version.setTextFill(Color.grayRgb(50));
    version.setTranslateX(SeagullApp.W - 440);

    info.setPrefSize(SeagullApp.W, 50);
    info.setAlignment(Pos.CENTER_LEFT);
    info.getChildren().addAll(credit, version);

  }

  /**
   * Initializes the background
   */
  private static void initBackground() {

    Image bgImage = new Image("file:Resources/mainBG.png");
    BackgroundSize bgSize =
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
    BackgroundImage bgi = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);

    background = new Background(bgi);

  }

  /**
   * Returns this scene
   * 
   * @return menuScene
   */
  public static Scene getScene() {

    return menuScene;

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

}
