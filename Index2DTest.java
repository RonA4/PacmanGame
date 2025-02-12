// my ID - 326029600
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class Index2DTest {

    @Test
    /*
     * Test case for getX() and getY() methods
     * It makes sure that the getX() and getY() methods return the correct x and y coordinates of the Pixel2D objects.
     */
    void Test_getX_getY() {
        // Create Pixel2D objects with different coordinates
        Pixel2D p1 = new Index2D(3, 5);
        Pixel2D p2 = new Index2D(5, 3);
        //// Verify that the getX() and getY() methods return the correct x and y coordinates
        Assertions.assertEquals(p1.getX(), 3);
        Assertions.assertEquals(p2.getX(), 5);
        Assertions.assertEquals(p1.getY(), 5);
        Assertions.assertEquals(p2.getY(), 3);

    }

    @Test
    /*
     * Test case for the distance2D() method of the class.
     * It verifies that the distance2D() method correctly calculates the 2D Euclidean distance between two Pixel2D objects.
     */
    void distance2D() {
        Pixel2D p1 = new Index2D(9,7);
        Pixel2D p2 = new Index2D(12,3);
        double distance = p1.distance2D(p2);
        Assertions.assertEquals(5.0,distance);
    }

    @Test
    /*
     * Test case for the toString() method
     * It verifies the correctness of the toString() method by comparing the expected string representation to the actual result.

     */
    void testToString() {
        Pixel2D pixel = new Index2D(2,3);
        String expected = "2,3";
        String result = pixel.toString();// Call the toString() method and get the actual result
        Assertions.assertEquals(expected,result);  // Verify that the actual result matches the expected string representation
    }

    @Test
    /*
     * Test case for the equals method
     * It verifies the correctness of the equals() method by comparing two objects
     */
    void testEquals() {
        //// Create Pixel2D objects with the same coordinates
        Pixel2D p1 =new Index2D(2,3);
        Pixel2D p2 = new Index2D(2,3);
        Pixel2D p3 = new Index2D(4,5);
        Assertions.assertTrue(p1.equals(p2)); // Verify that p1 is equal to p2
        Assertions.assertFalse(p1.equals(p3)); //// Verify that p1 is not equal to p3


    }
}