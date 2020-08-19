package application;

/**
 * Handles a 2D array of cells
 */
public class Ecosystem {

  private final int X_CELLS; // The number of cells on the horizontal axis
  private final int Y_CELLS; // The number of cells on the vertical axis
  private String fileOut;

  private static Cell[][] past;

  /**
   * Generates the initial ecosystem 
   * 
   * @param X_CELLS  number of cells on the horizontal axis
   * @param Y_CELLS  number of cells on the vertical axis
   * @param filePath the path to the file to be read in
   */
  Ecosystem(int X_CELLS, int Y_CELLS, String fileIn, String fileOut) {

    this.X_CELLS = X_CELLS;
    this.Y_CELLS = Y_CELLS;
    this.fileOut = fileOut;

    past = new Cell[X_CELLS][Y_CELLS];

    // Conditional handles file input
    if (fileIn != null) {

      boolean[][] seed = FileHandler.read(fileIn);

      int seedX = (X_CELLS - seed[0].length) / 2; // x coordinate to start seeding at
      int seedY = (Y_CELLS - seed.length) / 2; // y coordinate to start seeding at

      // Initially fill ecosystem with dead cells
      for (int y = 0; y < Y_CELLS; y++)
        for (int x = 0; x < X_CELLS; x++)
          past[x][y] = new Cell(false);

      // place the seed in the center
      for (int y = seedY; y < seedY + seed[0].length; y++)
        for (int x = seedX; x < seedX + seed.length; x++)
          past[x][y].setLiving(seed[x - seedX][y - seedY]);

    } else {

      // Fill ecosystem with cells, about 25% living
      for (int y = 0; y < Y_CELLS; y++)
        for (int x = 0; x < X_CELLS; x++)
          past[x][y] = new Cell(Math.random() < 0.25);

    }
    
    // Clear the output file before writing to it
    if (fileOut != null ) {
      FileHandler.wipe(fileOut);
      FileHandler.write(past, fileOut);
    }

  }

  /**
   * Generates the next cell array based on the previous one
   */
  public void generate() {

    getNeighbors();

    // generate new present cell based on neighbors of past, add to list in new scene
    boolean[][] pres = new boolean[X_CELLS][Y_CELLS];
    int neighbors;

    for (int y = 0; y < Y_CELLS; y++) {
      for (int x = 0; x < X_CELLS; x++) {

        neighbors = past[x][y].getLivingNeighbors();

        // In case subject cell is alive:
        if (past[x][y].getLiving()) {
          if (neighbors < 2) {
            pres[x][y] = false;
          } else if (neighbors >= 2 && neighbors < 4) {
            pres[x][y] = true;
          } else if (neighbors >= 4) {
            pres[x][y] = false;
          }
        }

        // In case subject cell is dead:
        if (!past[x][y].getLiving())
          if (neighbors == 3)
            pres[x][y] = true;

      }
    }

    for (int y = 0; y < Y_CELLS; y++)
      for (int x = 0; x < X_CELLS; x++)
        past[x][y].setLiving(pres[x][y]);

    if (fileOut != null)
      FileHandler.write(past, fileOut);

  }

  /**
   * Helper method to generate(), finds the number of living neighbors each cell has
   */
  private void getNeighbors() {

    for (int y = 0; y < Y_CELLS; y++) {
      for (int x = 0; x < X_CELLS; x++) { // now at cell level

        int neighbors = 0;

        for (int j = -1; j <= 1; j++) {
          for (int i = -1; i <= 1; i++) {

            // skip all out of bounds cases
            if (x + i < 0 || x + i > X_CELLS - 1 || y + j < 0 || y + j > Y_CELLS - 1)
              continue;

            // A cell isn't its own neighbor
            if (i == 0 && j == 0)
              continue;

            if (past[x + i][y + j].getLiving())
              neighbors++;

          }
        }

        past[x][y].setLivingNeighbors(neighbors);

      }
    }

  }

  /**
   * Sets all cells in the array to living = false
   */
  public void killAll() {

    for (int y = 0; y < Y_CELLS; y++)
      for (int x = 0; x < X_CELLS; x++)
        past[x][y].setLiving(false);

  }

  /**
   * Returns the cell at the given x-y coordinate
   * 
   * @param x coordinate of cell in the array
   * @param y coordinate of cell in the array
   * @return the cell at the given position in the array
   */
  public Cell getCell(int x, int y) {

    return past[x][y];

  }

}
