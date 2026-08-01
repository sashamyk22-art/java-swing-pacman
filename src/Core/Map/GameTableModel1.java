package Core.Map;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class GameTableModel1 extends AbstractTableModel {
    private final int height;
    private final int width;
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int POINT = 2;
    public static final int PACMAN = 3; // Nowy typ komórki
    public static final int GHOST = 4;
    public int points = 0; // Zmienna do przechowywania aktualnej liczby punktów
    public final int[][] MAP;
    public final int[][] POINT_MAP;
    private List<Ghost> ghosts;
    private int pacmanRow;
    private int pacmanColumn;

    public GameTableModel1(int height, int width) {
        this.height = height;
        this.width = width;
        MAP = new int[height][width];
        POINT_MAP = new int[height][width];
        generateMap();
        createGhosts();
    }

    public void generateMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                MAP[i][j] = WALL;
            }
        }

        int startX = 1;
        int startY = 1;
        MAP[startX][startY] = EMPTY;

        int pacmanStartX = 1;
        int pacmanStartY = 1;
        MAP[pacmanStartX][pacmanStartY] = PACMAN;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (MAP[i][j] != WALL && (i != pacmanStartX || j != pacmanStartY)) {
                    MAP[i][j] = POINT;
                    POINT_MAP[i][j] = POINT;
                }
            }
        }


        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Random random = new Random();
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point cell = stack.peek();
            int x = cell.x;
            int y = cell.y;


            int[] order = {0, 1, 2, 3};
            for (int i = 0; i < 4; i++) {
                int swapIndex = random.nextInt(4);
                int temp = order[i];
                order[i] = order[swapIndex];
                order[swapIndex] = temp;
            }

            boolean hasNeighbor = false;
            for (int i = 0; i < 4; i++) {
                int newX = x + 2 * dirs[order[i]][0];
                int newY = y + 2 * dirs[order[i]][1];

                if (newX > 0 && newX < height - 1 && newY > 0 && newY < width - 1 && MAP[newX][newY] == WALL) {
                    stack.push(new Point(newX, newY));
                    MAP[newX][newY] = EMPTY; // Mark as visited
                    MAP[x + dirs[order[i]][0]][y + dirs[order[i]][1]] = EMPTY;
                    hasNeighbor = true;
                    break;
                }
            }

            if (!hasNeighbor) {
                stack.pop();
            }
            for (int i = 0; i < MAP.length; i++) {
                for (int j = 0; j < MAP[i].length; j++) {
                    if (MAP[i][j] == EMPTY) {
                        MAP[i][j] = POINT;
                    }
                }
            }
        }
    }

    private void createGhosts() {
        ghosts = new ArrayList<>();
        int numGhosts;
        int totalCells = width * height;

        if (totalCells <= 625) {
            numGhosts = 2;
        } else if (totalCells <= 2500) {
            numGhosts = 3;
        } else if (totalCells <= 5625) {
            numGhosts = 4;
        } else {
            numGhosts = 5;
        }

        Random random = new Random();

        for (int i = 0; i < numGhosts; i++) {
            int row, column;
            do {
                row = random.nextInt(height);
                column = random.nextInt(width);
            } while (MAP[row][column] == WALL || MAP[row][column] == PACMAN);

            Ghost ghost = new Ghost(row, column);
            MAP[row][column] = GHOST;
            ghosts.add(ghost);
        }
    }

    public void moveGhosts() {
        Random random = new Random();
        for (Ghost ghost : ghosts) {
            int newRow = ghost.getRow() + (int)(Math.random() * 3) - 1;
            int newColumn = ghost.getColumn() + (int)(Math.random() * 3) - 1;


            if (newRow >= 0 && newRow < MAP.length && newColumn >= 0 && newColumn < MAP[0].length) {
                if (MAP[newRow][newColumn] != WALL) {
                    if (MAP[ghost.getRow()][ghost.getColumn()] == GHOST && MAP[ghost.getRow()][ghost.getColumn()] == EMPTY) {
                        MAP[ghost.getRow()][ghost.getColumn()] = EMPTY;
                    } else {
                        MAP[ghost.getRow()][ghost.getColumn()] = POINT;
                    }

                    ghost.setRow(newRow);
                    ghost.setColumn(newColumn);
                    MAP[newRow][newColumn] = GHOST;
                }
            }
        }
    }

    @Override
    public int getRowCount() {
        return height;
    }

    @Override
    public int getColumnCount() {
        return width;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return MAP[rowIndex][columnIndex];
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public class Ghost {
        private int row;
        private int column;
        private int previousState;

        public Ghost(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public int getPreviousState() {
            return previousState;
        }

        public void setPreviousState(int previousState) {
            this.previousState = previousState;
        }
    }
}