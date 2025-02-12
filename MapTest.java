
//// my ID 326029600
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    public static final int[][] map1 = {{-2, -2, -1, -2},
            {-2, -2, -2, -2},
            {-1, -2, -1, -2}};
    static final int[][] map4 = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0}};

    public static final int[][] pixels = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}};

    @Test
    /*
    Test case for the init() method of the Map class.
    It verifies that the init() method correctly initializes the map with the specified value.
     */
    void init() {
        Map CopyMap = new Map(3, 3, 1);
        CopyMap.init(2, 3, 1);
        int[][] result = CopyMap.getMap();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(1, result[i][j]);
            }
        }
    }

    @Test
    /*
     * Test case for the init() method of the Map class.
     * It verifies that the init() method correctly initializes the map with the provided input array.
     */
    void testInit() {
        int[][] input = {
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        };

        // Create a sample map
        Map sampleMap = new Map(0, 1, 0);
        sampleMap.init(input);

        // Get the map using getMap
        int[][] result = sampleMap.getMap();

        // Assert that the result matches the input array
        assertArrayEquals(input, result);
    }

    @Test
    /*
    Test case for the getMap() method of the Map class.
    It verifies that the getMap() method returns a copy of the map array.
     */
    void getMap() {
        int[][] map = { // Define the map array representing the desired map configuration
                {0, 0, 0},
                {1, 1, 1},
                {2, 2, 2}
        };
        Map copyMap = new Map(map);  // Create a Map object with the map array
        int[][] result = copyMap.getMap();  // Get the map using the getMap() method
        Assert.assertArrayEquals(map, result); // Assert that the result matches the map array
    }

    @Test
    /*
    Test case for the getMap() .
   It verifies that the getMap() method returns the correct 2D array representation of the map.
     */
    void testGetMap() {
        Map2D map = new Map(3, 4, 7); // Create a new Map2D object with dimensions 3x4 and a default pixel value of 7
        int[][] test = map.getMap();  // Get the 2D array representation of the map
        // Assert that the dimensions of the 2D array match the map dimensions
        Assertions.assertEquals(test.length, map.getWidth());
        Assertions.assertEquals(test[0].length, map.getHeight());
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < test[0].length; j++) {
                Assertions.assertEquals(test[i][j], map.getPixel(i, j));

            }

        }
    }
    @Test
    /*
     Test case for the shortestPath()
 * It verifies that the shortestPath() method returns the correct shortest path between two pixels in the map.
 */
    public void testShortestPath1() {
        Map2D map3 = new Map(map1);
        map3.setCyclic(false);
        Pixel2D zero = new Index2D(2, 1);
        Pixel2D step1 = new Index2D(1, 1);
        Pixel2D step2 = new Index2D(1, 2);
        Pixel2D step3 = new Index2D(1, 3);
        Pixel2D aim = new Index2D(2, 3);
        Pixel2D[] exp = {zero, step1, step2, step3, aim};
        assertArrayEquals(map3.shortestPath(zero, aim, -1), exp);
    }

    @Test
    /*
    Test case for the shortestPath().
 * It verifies that the shortestPath() method returns the correct shortest path between two pixels in the map.
     */
    public void testShortestPath2() {
        // Create a new Map2D object based on an existing map and set cyclic to true
        Map2D map3 = new Map(map1);
        map3.setCyclic(true);
        // // Define the starting pixel, intermediate steps, and the target pixel
        Pixel2D zero = new Index2D(2, 1);
        Pixel2D step1 = new Index2D(0, 1);
        Pixel2D step2 = new Index2D(0, 0);
        Pixel2D aim = new Index2D(0, 3);
        Pixel2D[] exp = {zero, step1, step2, aim}; //   // Define the expected shortest path array
        assertArrayEquals(map3.shortestPath(zero, aim, -1), exp); // Assert that the actual shortest path array matches the expected path
    }

    @Test
    /*
     Test case for the isInside() method of the Map class.
     It verifies that the isInside() method correctly determines if a given pixel is inside the map boundaries.
     */
    void isInside() {
        // Create a map with dimensions 3x3
        Map map = new Map(3, 3, 0);

        // Test for pixels within the map boundaries
        assertTrue(map.isInside(new Index2D(0, 0)));
        assertTrue(map.isInside(new Index2D(1, 1)));
        assertTrue(map.isInside(new Index2D(2, 2)));

        // Test for a pixel outside the map boundaries
        assertFalse(map.isInside(new Index2D(3, 3)));
    }

    @Test
    /*
     * test case for the isInside() method of the Map2D class.
       It verifies that the isInside() method correctly determines if a given pixel is inside the map boundaries.
     */
    void TestisInside2() {
        Map2D map = new Map(48, 76, 2);
        Pixel2D first = new Index2D(46, 24);
        Pixel2D second = new Index2D(48, 76);
        Assertions.assertTrue(map.isInside(first));
        Assertions.assertFalse(map.isInside(second));
    }

    @Test
    /*
   Test case for the isCyclic() method of the Map2D class.
   It verifies that the isCyclic() method returns the correct value based on the cyclic flag of the map.
 */
    void isCyclic() {
        Map2D map = new Map(4, 7, 13);
        Assertions.assertTrue(map.isCyclic());
        map.setCyclic(false);
        Assertions.assertFalse(map.isCyclic());
    }



    @Test
    /*
     Test case for the allDistance() method of the Map class.
  It verifies that the allDistance() method calculates the distances correctly in a map with obstacles.
     */
    void allDistance() {
        Map map = new Map(map4);
        Pixel2D start = new Index2D(2, 2);
        int obsColor = -1;
        Map2D result = map.allDistance(start, obsColor);
        Assertions.assertEquals(map.getWidth(), map.getHeight());
        Assertions.assertEquals(map.getHeight(), map.getWidth());

        Assertions.assertEquals(0, result.getPixel(start));

        Assertions.assertEquals(1, result.getPixel(new Index2D(2, 1)));
        Assertions.assertEquals(1, result.getPixel(new Index2D(3, 2)));
        Assertions.assertEquals(1, result.getPixel(new Index2D(2, 3)));

        Assertions.assertEquals(2, result.getPixel(new Index2D(1, 1)));
        Assertions.assertEquals(2, result.getPixel(new Index2D(1, 3)));
        Assertions.assertEquals(2, result.getPixel(new Index2D(3, 1)));
        Assertions.assertEquals(2, result.getPixel(new Index2D(3, 3)));


    }

    @Test
    /*
     * Test case for the allDistance() method of the Map2D class.
     * It verifies that the allDistance() method calculates the distances correctly in a map with obstacles.
     */
    void allDistance2() {
         // Create a Map2D object with dimensions 5x5 and initial pixel color 0
        Map2D map = new Map(5, 5, 0); //   // Create a Map2D object with dimensions 5x5 and initial pixel color 0
        int obsColor = -1;
        map.setPixel(1, 1, obsColor);
        map.setPixel(2, 1, obsColor);
        map.setPixel(3, 1, obsColor);
        map.setPixel(3, 1, obsColor);
        map.setPixel(3, 1, obsColor);
        map.setPixel(3, 1, obsColor);
        map.setPixel(1, 1, obsColor);
        map.setPixel(0, 1, obsColor);
        // // Perform the allDistance() operation on the map again
        Map2D testMap = map.allDistance(new Index2D(4, 0), obsColor);
        // Assert that the distances in the testMap match the expected values
        Assertions.assertEquals(testMap.getPixel(0, 2), 3);
        Assertions.assertEquals(testMap.getPixel(3, 4), 2);
        Assertions.assertEquals(testMap.getPixel(1, 2), 4);
        Assertions.assertEquals(testMap.getPixel(0, 4), 2);
        Assertions.assertEquals(testMap.getPixel(0, 0), 1);
        map.setCyclic(false);
        map.setPixel(0, 1, obsColor);
        testMap = map.allDistance(new Index2D(4, 0), obsColor);
        //
        Assertions.assertEquals(testMap.getPixel(0, 2), 6);
        Assertions.assertEquals(testMap.getPixel(1, 2), 5);
        testMap = map.allDistance(new Index2D(4, 0), obsColor);
        Assertions.assertEquals(testMap.getPixel(4, 2), 2);
        Assertions.assertEquals(testMap.getPixel(0, 4), 8);


    }


    @Test
    /*
     * Test case for the fill() method of the Map2D class.
     * It verifies that the fill() method correctly fills the specified area with the given color.
     */
    void fill() {
        Map2D map = new Map(pixels);  // Create a Map2D object with the initial pixel configuration
        int filledPixels = map.fill(new Index2D(1, 1), 2); //Perform the fill operation on the map, starting from the specified position (1, 1) with color 2

        assertEquals(9, filledPixels); // // Assert that the number of filled pixels matches the expected value
        // Assert that the pixels within the filled area have the correct color (2)
        assertEquals(2, map.getPixel(1, 1));
        assertEquals(2, map.getPixel(0, 0));
        assertEquals(2, map.getPixel(0, 1));
        assertEquals(2, map.getPixel(0, 2));
        assertEquals(2, map.getPixel(1, 0));
        assertEquals(2, map.getPixel(1, 2));
        assertEquals(2, map.getPixel(2, 0));
        assertEquals(2, map.getPixel(2, 1));
        assertEquals(2, map.getPixel(2, 2));


    }
}






