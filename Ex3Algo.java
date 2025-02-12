
// my ID 326029600
import java.awt.*;
import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo{
	private int _count;
	public Ex3Algo() {_count=0;}
	@Override
	/**
	 *  Add a short description for the algorithm as a String.
	 */
	public String getInfo() {
	return "The provided algorithm determines the optimal direction for Pacman's movement in the game." +
			" It achieves this by finding the closest pink pixel (representing a potential target or food) to Pacman's" +
			" current position. The algorithm utilizes a map representation of the game board and calculates the " +
			"shortest path from Pacman to the closest pink pixel using distance maps. It considers the available " +
			"directions (up, down, left, and right) and selects the direction that leads Pacman closer to the target. " +
			"This approach ensures that Pacman moves efficiently towards the nearest pink pixel, enabling it to consume " +
			"the food and progress in the game.";
	}

	@Override
/**
 * This ia the main method - that you should design, implement and test.
 */
	public int move(PacmanGame game) {
		// Checking the count to determine if it's the first or 300th move
		if (_count == 0 || _count == 300) {
			int code = 0;
			int[][] board = game.getGame(0);
			printBoard(board);

			// Getting integer representations of colors
			int blue = Game.getIntColor(Color.BLUE, code);
			int pink = Game.getIntColor(Color.PINK, code);
			int black = Game.getIntColor(Color.BLACK, code);
			int green = Game.getIntColor(Color.GREEN, code);
			System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);

			// Getting Pacman's coordinates
			String pos = game.getPos(code).toString();
			System.out.println("Pacman coordinate: " + pos);

			// Getting information about the ghosts
			GhostCL[] ghosts = game.getGhosts(code);
			printGhosts(ghosts);

			// Defining directions
			int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
		}

		_count++;

		// Generating a random direction
		int dir = randomDir(game);
		return dir;
	}

	private static void printBoard(int[][] b) {
		// Printing the game board
		for (int y = 0; y < b[0].length; y++) {
			for (int x = 0; x < b.length; x++) {
				int v = b[x][y];
				System.out.print(v + "\t");
			}
			System.out.println();
		}
	}

	private static void printGhosts(GhostCL[] gs) {
		// Printing information about the ghosts
		for (int i = 0; i < gs.length; i++) {
			GhostCL g = gs[i];
			System.out.println(i + ") status: " + g.getStatus() + ",  type: " + g.getType() + ",  pos: " + g.getPos(0) + ",  time: " + g.remainTimeAsEatable(0));
		}
	}
	private static int randomDir(PacmanGame game) {
		int[][] board = game.getGame(0);
		Map map = new Map(board);

		// Getting Pacman's current position
		String pacmanPosition = game.getPos(0);
		String[] positionParts = pacmanPosition.split(",");
		int x = Integer.parseInt(positionParts[0]);
		int y = Integer.parseInt(positionParts[1]);
		Pixel2D pacmanPose = new Index2D(x, y);

		// Calculating the shortest path to the closest pink pixel (ghost)
		Map2D allDistanceMap = map.allDistance(pacmanPose, 1);
		Pixel2D closestPinkPixel = findClosestPinkPixel(pacmanPose, map, allDistanceMap);
		Pixel2D[] path = map.shortestPath(pacmanPose, closestPinkPixel, 1);

		// Finding the direction to move based on the calculated path
		int direction = findDirection(pacmanPose, path[1]);
		return direction;
	}

	/*
The function findClosestPinkPixel takes the pacman pose (pacmanPose), the game map (map), and the distance map from pacman (allDistanceMap).
The function searches for the closest pink pixel (ghost) to Pacman on the map, based on the provided distance map.
The function uses nested for loops to iterate over all the pixels in the map. For each pixel with a value of 3 (pink pixel) in the map,
the function checks the corresponding value in the distance map. If the value is not -1 (indicating a valid distance from Pacman), the function
 compares the current distance to the closest distance found so far. If the current distance is small, it updates the nearest distance and the
 coordinates of the nearest pink pixel.
The function returns the closest pink pixel to the Pacman position.
	 */
	private static Pixel2D findClosestPinkPixel(Pixel2D pacmanPose, Map map, Map2D allDistanceMap) {
		int distanceToClosestPink = Integer.MAX_VALUE;
		Pixel2D closestPinkPixel = new Index2D(0, 0);
		int width = map.getWidth();
		int height = map.getHeight();

		// Finding the closest pink pixel (ghost)
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map.getPixel(i, j) == 3) {
					if (allDistanceMap.getPixel(i, j) != -1) {
						int currentDistance = allDistanceMap.getPixel(i, j);
						if (distanceToClosestPink > currentDistance) {
							distanceToClosestPink = currentDistance;
							closestPinkPixel = new Index2D(i, j);
						}
					}
				}
			}
		}
		return closestPinkPixel;
	}

	/*
The function findDirection takes the current position of the pacman (pacmanPose) and the target position (target).
The function determines the direction in which the pacman should move to reach the destination.
First it checks if the X-coordinate of the Pacman position matches the X-coordinate of the target.
If they match, it checks the Y coordinate to determine whether to move up or down. If the Y-coordinate is greater than the
target's Y-coordinate, it returns Game.UP; If it is less, it returns Game.DOWN.
If the X-coordinates of the Pacman position and the target do not match, the function checks if the Y-coordinate of the
Pacman position matches the Y-coordinate of the target. If they match, it checks the X coordinate to determine whether to move right or left.
If the x-coordinate is greater than the target's x-coordinate, it returns Game.RIGHT; If less, it returns Game.LEFT.
If no valid direction is found, the function returns -1.
	 */
	private static int findDirection(Pixel2D pacmanPose, Pixel2D destination) {
		// Finding the direction to move based on the current position and destination
		System.out.println("pacmanPose.getX(): " + pacmanPose.getX() + " pacmanPose.getY(): " + pacmanPose.getY()
				+ " destination.getX()" + destination.getX() + " destination.getY(): " + destination.getY());

		if (pacmanPose.getX() == destination.getX()) {
			if (pacmanPose.getY() + 1 == destination.getY()) {
				return Game.UP;
			}
			if (pacmanPose.getY() - 1 == destination.getY()) {
				return Game.DOWN;
			}
			if (pacmanPose.getY() > destination.getY()) {
				return Game.UP;
			}
			if (pacmanPose.getY() < destination.getY()) {
				return Game.DOWN;
			}
		} else if (pacmanPose.getY() == destination.getY()) {
			if (pacmanPose.getX() + 1 == destination.getX()) {
				return Game.RIGHT;
			}
			if (pacmanPose.getX() - 1 == destination.getX()) {
				return Game.LEFT;
			}
			if (pacmanPose.getX() > destination.getX()) {
				return Game.RIGHT;
			}
			if (pacmanPose.getX() < destination.getX()) {
				return Game.LEFT;
			}
		}
		return -1;
	}
	}