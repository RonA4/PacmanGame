// my ID - 326029600
public class Index2D implements Pixel2D {
    private int _x, _y;
    public Index2D() {this(0,0);}
    public Index2D(int x, int y) {_x=x;_y=y;}
    public Index2D(Pixel2D t) {this(t.getX(), t.getY());}
    @Override
    public int getX() {
        return _x;
    }
    @Override
    public int getY() {
        return _y;
    }
    /*
    The distance2D method calculates the 2D distance between the current pixel and the provided target pixel.
    A distance calculation formula is used in a two-dimensional coordinate system. The parameter t represents the target pixel.
    The method starts by checking if the target pixel is empty. If so, it throws a RuntimeException with an error message stating that the target
     pixel cannot be null.
    Otherwise, the method proceeds to calculate the distance by calculating the squared differences between the x and y coordinates of the current pixel
    and the target pixel, then adding them together. Finally, it takes the square root of the sum and returns the calculated value.
     */
    public double distance2D(Pixel2D t) throws RuntimeException {
        double ans = 0;
        if (t == null) {
            throw new RuntimeException("Invalid input, t cannot be null");
        } else {
            double x = Math.pow(this._x - t.getX(), 2);
            double y = Math.pow(this._y - t.getY(), 2);
            ans = Math.sqrt(x + y);
        }
        return ans;
    }
    @Override
/*
The toString method overrides the default implementation of the toString method from the Object class.
It returns a string representation of the current pixel by concatenating its x coordinate and y coordinate with a comma separator.
 */
    public String toString() {
        return getX()+","+getY();
    }

    @Override
    /*
    The method checks if the current pixel is equal to the specified object. It also states that the object must be Pixel2D
     and have the same coordinates as the current pixel in order for the method to return true. The method uses the distance2D
      method to calculate the distance between the current pixel and the object and checks if the distance is equal to 0 to determine equality.
     */
    public boolean equals(Object t) {
        boolean ans = false;
        if (t != null && t instanceof Pixel2D) {
            Pixel2D p3= new Index2D(3,2);
            Pixel2D p = (Pixel2D) t;
            ans = this.distance2D(p) ==0;
        }

        return ans;
    }

    }