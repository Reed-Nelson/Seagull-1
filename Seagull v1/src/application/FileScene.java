package application;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
 * The Scene representing the file menu
 * 
 * Structure: The root is a BorderPane, whose center is all the this scene's content but the
 * background
 */
public class FileScene {

  private static Scene fileScene;
  
  private static BorderPane root;
  private static VBox center;
  private static Background background;
  
  private static String fileInPath;
  private static String fileOutPath;

  /**
   * Calls the initialization of the background and center and puts them together in the root
   */
  FileScene() {

    root = new BorderPane();
    center = new VBox();

    initCenter();
    initBackground();

    root.setPrefSize(SeagullApp.W, SeagullApp.H);
    root.setBackground(background);
    root.setCenter(center);

    fileScene = new Scene(root);
    
    fileInPath = null;
    fileOutPath = null;

  }

  /**
   * Initializes the central functional components to be added to the center of the root
   */
  private static void initCenter() {

    Label header = new Label("IMPORT / EXPORT");
    Label imp = new Label("Import");
    Label exp = new Label("Export");
    
    TextField fileIn = new TextField();
    TextField fileOut = new TextField();
    
    Button backButton = new Button("BACK");

    Font headerFont = new Font("Century Gothic", 96);
    Font labelFont = new Font("Century Gothic", 60);
    Font textFieldFont = new Font("Century Gothic", 36);
    Font buttonFont = new Font("Century Gothic", 72);

    // Header label
    header.setFont(headerFont);
    header.setTranslateY(-165);
    header.setTextFill(Color.grayRgb(50));

    // Import label
    imp.setFont(labelFont);
    imp.setTranslateY(-15);
    imp.setTextFill(Color.grayRgb(50));
    
    // File In text field
    fileIn.setTranslateY(20);
    fileIn.setTranslateX(-3);
    fileIn.setMaxSize(905, 120);
    fileIn.setFont(textFieldFont);
    fileIn.setOpacity(0.5);
    
    // Export Label
    exp.setFont(labelFont);
    exp.setTranslateY(75);
    exp.setTextFill(Color.grayRgb(50));
    
    // File Out text field
    fileOut.setTranslateY(110);
    fileOut.setTranslateX(-3);
    fileOut.setMaxSize(905, 120);
    fileOut.setFont(textFieldFont);
    fileOut.setOpacity(0.5);

    // Back button
    backButton.setTranslateY(250);
    backButton.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
    backButton.setFont(buttonFont);
    backButton.setTextFill(Color.grayRgb(50));
    backButton.setOnMouseEntered(e -> backButton.setTextFill(randomColor()));
    backButton.setOnMouseExited(e -> backButton.setTextFill(Color.grayRgb(50)));
    backButton.setOnAction(e -> {
      fileInPath = fileIn.getText();
      if (fileInPath.equals(""))
        fileInPath = null;
      
      fileOutPath = fileOut.getText();
      if (fileOutPath.equals(""))
        fileOutPath = null;
      
      SeagullApp.primaryStage.setScene(MenuScene.getScene());
    } );

    center.setAlignment(Pos.CENTER);
    center.getChildren().addAll(header, imp, fileIn, exp, fileOut, backButton);

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
   * Returns the path of the input file
   * 
   * @return fileInPath
   */
  public static String getFileIn() {
    
    return fileInPath;
    
  }
  
  /**
   * Returns the path of the output file
   * 
   * @return fileOutPath
   */
  public static String getFileOut() {
    
    return fileOutPath;
    
  }

  /**
   * Returns this scene
   * 
   * @return optionsScene
   */
  public static Scene getScene() {

    return fileScene;

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
