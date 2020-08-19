package application;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a single cell in the ecosystem array
 */
public class Cell extends Rectangle {

  private boolean living;
  private int livingNeighbors;
  private final int CELL_SIZE = SeagullApp.cellSize;

  /**
   * Sets up the cell object
   * 
   * @param living whether the cell is living or dead
   */
  public Cell(boolean living) {

    this.living = living;

    this.setWidth(CELL_SIZE);
    this.setHeight(CELL_SIZE);

    if (living) {
      setFill(randomColor());
    } else {
      setFill(Color.BLACK);
    }

    setStroke(Color.grayRgb(50)); // sets border to a very dark gray

    // toggle cell alive/dead with click
    setOnMousePressed(e -> setLiving());

    // set cell alive if mouse is dragged over it
    setOnDragDetected(e -> startFullDrag());
    setOnMouseDragEntered(e -> {
      e.consume();
      setLiving(true);
    });

  }

  /**
   * Updates the living value of the cell, changes the color accordingly
   * 
   * @param living whether the cell should be living or dead
   */
  public void setLiving(boolean living) {

    if (this.living == living) {

      return;

    } else if (living) {

      this.living = true;

      setFill(randomColor());

    } else if (!living) {

      this.living = false;
      setFill(Color.BLACK);

    }

  }

  /**
   * Flips the living value of the cell, changes the color accordingly
   */
  public void setLiving() {

    if (this.living == true) {

      this.living = false;
      setFill(Color.BLACK);

    } else if (this.living == false) {

      this.living = true;

      setFill(randomColor());

    }

  }

  /**
   * Sets this cell's livingNeighbors
   * 
   * @param livingNeighbors the number to set this cell's livingNeighbors to
   */
  public void setLivingNeighbors(int livingNeighbors) {

    this.livingNeighbors = livingNeighbors;

  }

  /**
   * Returns the living value of this cell
   * 
   * @return true if this cell is living, false otherwise
   */
  public boolean getLiving() {

    return living;

  }

  /**
   * Returns the number of living neighbors for this cell
   * 
   * @return the number of living neighbors this cell has
   */
  public int getLivingNeighbors() {

    return livingNeighbors;

  }

  /**
   * Generates a random color, modified to be brighter
   * 
   * @return a random color
   */
  private Color randomColor() {

    Random rand = new Random();

    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();

    Color randomColor = new Color(r, g, b, 0.8);

    return randomColor.brighter();

  }

}
