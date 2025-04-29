import java.util.*;

public class MazeManager {
        int[][] MazeTile;
        int width, height;
        List<Agent> agentList;
        List<Integer> rotatingRows;
        static int WIDTH;
        static int HEIGHT;
        private static final char WALL = '#';
        private static final char PATH = ' ';
        private static final Random rand = new Random();
        private static char[][] maze;

        static Scanner scanner=new Scanner(System.in);

        static void generateMaze() {
            System.out.println("Enter the size of the maze(i.e 4x4,8x8,4x10");
            String size= scanner.nextLine();
            String[] sizeArray=size.split("x");
            WIDTH = Integer.parseInt(sizeArray[0].trim());
            HEIGHT = Integer.parseInt(sizeArray[1].trim());
            maze=new char[WIDTH][HEIGHT];
            for (int i = 0; i < HEIGHT; i++) {
                Arrays.fill(maze[i], WALL);
            }



        }

        public static void setMaze(char[][] maze) {
            MazeManager.maze = maze;
        }

        void rotateCorridor(int rowID) {

        }

        boolean isValidMove(int fromX, int fromY, String direction) {

            return true;
        }

        MazeTile getTile(int x, int y) {

            return null;
        }

        void updateAgentLocation(Agent a, int oldX, int oldY) {

        }

        private static boolean inBounds(int x, int y) {
            return x > 0 && x < WIDTH - 1 && y > 0 && y < HEIGHT - 1;
        }

        public static void printMazeSnapshot() {
            for (char[] row : maze) {
                for (char c : row) {
                    System.out.print(c);
                }
                System.out.println();
            }
        }
    }
