// my ID - 326029600
/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
	private int[][] _map;
	private boolean _cyclicFlag = true;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 *
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
		init(w, h, v);
	}

	/**
	 * Constructs a square map (size*size).
	 *
	 * @param size
	 */
	public Map(int size) {
		this(size, size, 0);
	}

	/**
	 * Constructs a map from a given 2D array.
	 *
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

	@Override
	/*
 Initializes the map with the specified width, height, and value.
 *
 * @param w The width of the map.
 * @param h The height of the map.
 * @param v The value to assign to each element in the map.
 */
	public void init(int w, int h, int v) {
		this._map = new int[w][h]; // Create a new 2D array with the specified width and height
		for (int i = 0; i < w; i++) { // Iterate over the rows of the map
			for (int j = 0; j < h; j++) { // Iterate over the columns of the map
				this._map[i][j] = v;  // Set the value of each element in the map to the specified value
			}
		}
	}

	@Override
	/*
	 * Initializes the map using the provided 2D array.
	 * The dimensions and values of the provided array are copied to the internal map.
	 * @param arr The 2D array to initialize the map with.
	 * @throws RuntimeException if the provided array is null.
	 */
	public void init(int[][] arr) {
		if (arr == null) { //// Check if the provided array is null
			throw new RuntimeException("RuntimeException,Invalid input arr cant be null");
		}
			int[][] copyMap = new int[arr.length][arr[0].length]; // / Create a new 2D array with the same dimensions as the provided array
			for (int i = 0; i < arr.length; i++) { // Iterate over the rows of the map
				for (int j = 0; j < arr[0].length; j++) { //Iterate over the columns of the map
					copyMap[i][j] = arr[i][j]; //// Copy the value from the provided array to the corresponding position in the new array
				}
			}
			// // Set the internal map to the copied array
		_map = copyMap; //
	}

	@Override
	/*
	* Returns a copy of the map.
	 */
	public int[][] getMap() {
		int[][] ans = null;
		ans = new int[this._map.length][this._map[0].length]; //A copy of the map as a 2D array.
		for (int i = 0; i < _map.length; i++) {//Iterate over the rows of the map
			for (int j = 0; j < _map[0].length; j++) { //Iterate over the columns of the map
				ans[i][j] = this._map[i][j]; // // Copy the value from the internal map to the corresponding position in the new array
			}
		}
		return ans;
	}

	@Override
	/*
	*Setting the width of the array
	 */
	public int getWidth() {
		return _map.length;
	}

	@Override
	/*
	*Setting the height of the array
	 */
	public int getHeight() {
		return _map[0].length;
	}

	@Override
	/*
	The getPixel method retrieves the value in the coordinates specified in the map.
	It takes the x and y coordinates as parameters and returns the corresponding value in the map at these coordinates.
	 */
	public int getPixel(int x, int y) {
		return _map[x][y];
	}

	@Override
	/*
	The getPixel method retrieves the value at the specified coordinates in the map based on the Pixel2D object.
	It takes a Pixel2D p object representing the coordinates of the pixel. It calls the getPixel method with the x and y coordinates extracted from
	 the Pixel2D object and returns the corresponding value in the map at these coordinates.
	 */
	public int getPixel(Pixel2D p) {

		return this.getPixel(p.getX(), p.getY());
	}

	@Override
/*
The setPixel method sets the value at the specified coordinates on the map. It takes the x and y coordinates of the pixel and the v value to be set.
 Internally, it assigns the v value to the appropriate location on the map at the specified coordinates.
 */
	public void setPixel(int x, int y, int v) {
		_map[x][y] = v; //
	}

	@Override
	/*
	The setPixel method sets the value at the specified coordinates on the map based on the Pixel2D object.
	 It takes a Pixel2D object p representing the coordinates of the pixel and the v value to be set.
	It assigns the v value to the appropriate location on the map at the specified coordinates obtained from the Pixel2D object.
	 */
	public void setPixel(Pixel2D p, int v) {
		_map[p.getX()][p.getY()] = v;
	}

	@Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	/*
The fill function performs a flood fill operation on connected areas within a 2D map, using a specified new value.
 It takes the starting point xy (Pixel2D object) and the new value new_v, and returns the full pixel count.
The function creates a temporary map (tempMap) based on the original map (_map) and the starting point.
 It marks the starting point as 0 and enters a loop that continues until no more regions are found.
 In each iteration of the loop it traverses all the pixels in the temporary map and checks if they have the value r.
 If the pixel has the value r, it calls the markNeighbors function to determine if there are any neighboring pixels that need to be marked.
 If there is, it sets the flag variable to true.
After the overflow fill is complete, the function fills the areas in the original map with the new value and counts the number of pixels filled.
 Finally, it returns the count.
	 */
	public int fill(Pixel2D xy, int new_v) {
		int r = 0 ;
		int [][] tempMap=createTempMap(this._map,this._map[xy.getX()][xy.getY()]);
		tempMap[xy.getX()][xy.getY()]=0;//Set the start point to zero
		boolean flag =true;
		while(flag) { //Perform flood fill until no more regions are found
			flag =false;
			for (int i = 0 ; i<tempMap.length; i++) { //Iterate over the rows of the map
				for(int j = 0 ; j<tempMap[0].length; j++) { //Iterate over the columns of the map
					if (tempMap [i][j]== r) {
						boolean neibor =  markNeighbors(tempMap, i,j);
						if(neibor==true)
							flag=true;
					}
				}
			}
			r++; // advance the initial value
		}
		int count =0;
		for(int i=0; i< tempMap.length; i++) { //Iterate over the rows of the map
			for(int j=0; j< tempMap[0].length; j++) {//iterate over the columns of the map
				if(tempMap[i][j]> -1) {
					setPixel(i,j, new_v);
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	@Override
	/*
*The function shortestPath calculates the shortest path between two points (p1 and p2) on the map while avoiding obstacles of a certain color (obsColor).
	 It returns an array of Pixel2D objects representing the shortest path.
 * @param p1        The starting point (Pixel2D object).
 * @param p2        The target point (Pixel2D object).
 * @param obsColor  The color of the obstacles to avoid (integer).

	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
		int [] [] temp = allDistance(p1, obsColor).getMap(); //Defining 2D  array
		int l= temp[p2.getX()][p2.getY()]+1; //The length of the shortest path from the starting point (p1) to the destination point (p2) on the map.
		Pixel2D[] ans = new Pixel2D[l];  // the result.
		ans[ans.length-1]=p2;//Set the target point
		ans[0]=p1;//Set the start point
		for (int i = ans.length-2; i >0 ; i--) {
			Pixel2D mainP=ans[i+1];
			int x= mainP.getX();int y= mainP.getY();
			int nx; int ny;//neighbor coordinates

			if (x==temp.length-1&&this._cyclicFlag){//Right edge
				nx=0;
				ny=y;
			}
			else {
				nx=x+1;
				ny=y;
			}
			if(nx<temp.length&&temp[nx][ny]==temp[mainP.getX()][mainP.getY()]-1) {
				ans[i]=new Index2D(nx,ny);
			}

			if (x==0&&this._cyclicFlag){//Left edge
				nx=temp.length-1;
				ny=y;
			}
			else {
				nx=x-1;
				ny=y;
			}
			if(nx>-1&&temp[nx][ny]==temp[mainP.getX()][mainP.getY()]-1) {
				ans[i]=new Index2D(nx,ny);
			}

			if (y==0&&this._cyclicFlag){//Up edge
				nx=x;
				ny=temp[0].length-1;
			}
			else {
				nx=x;
				ny=y-1;
			}
			if(ny>-1&&temp[nx][ny]==temp[mainP.getX()][mainP.getY()]-1) {
				ans[i]=new Index2D(nx,ny);
			}

			if (y==temp[0].length-1&&this._cyclicFlag) {//Down edge
				nx=x;
				ny=0;
			}
			else {
				nx=x;
				ny=y+1;
			}
			if(ny<temp[0].length&&temp[nx][ny]==temp[mainP.getX()][mainP.getY()]-1) {
				ans[i]=new Index2D(nx,ny);
			}
		}
		return ans;
	}

	@Override
	/*
	The isInside function is used to determine if a given pixel (p) is located within the map boundaries.
	 It returns true if the pixel is inside the map, and false otherwise.
	 */
	public boolean isInside(Pixel2D p) {
		if (p.getX() >= 0 && p.getX() < _map.length && p.getY() >= 0 && p.getY() < _map[0].length) { // Check if the X and Y coordinates of the pixel are within the map boundaries
			return true;
		}
		return false; // The pixel is outside the map
	}


	@Override
/*
The isCyclic function is used to determine whether the map has a cyclic property. It returns true if the map is cyclic,
meaning it wraps around and connects the opposite edges, and false otherwise.
 */
	public boolean isCyclic() {
		return this._cyclicFlag;
	}

	@Override
	/*
	The setCyclic function is used to set the cyclic property of the map. It takes a boolean parameter cy
	, which indicates whether the map should be set as cyclic or not.
	 */
	public void setCyclic(boolean cy) {
		this._cyclicFlag = cy;
	}



	@Override
	/*
The allDistance function calculates the distance map from a given starting point (start) taking into account obstacles of a certain color (obsColor).
 returns a Map2D object representing the distance map.
The function first creates a new map (newMap) using the createMapAllD function, which initializes the
 map based on the original map and sets the obstacle pixels to a special value.
The variable r represents the distance from the starting point. it starts with 0,
The function then enters a while loop that continues until no more updates are made to the map.
 Inside the loop, it iterates over each pixel in newMap and checks if its value is equal to r.
  If so, it calls the markNeighbors function to update the values of neighboring pixels.
 If updates have been made, the flag variable is set to true to indicate that additional iterations are required.
After the loop, the distance map is ready, and a new Map2D object (ans) is created using newMap as the map data.
Finally the ans map is returned, representing the distance map from the starting point, given the specified obstacle color.
	 */
	public Map2D allDistance(Pixel2D start, int obsColor) {
		int[][] newMap = createMapAllD(this.getMap(), obsColor);
		int r = 0;//Radius
		newMap[start.getX()][start.getY()] = r; //Set the start value to zero
		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i = 0; i < newMap.length; i++) { //Iterate over the rows of the map
				for (int j = 0; j < newMap[0].length; j++) {//iterate over the columns of the map
					if (newMap[i][j] == r) {
						boolean temp = markNeighbors(newMap, i, j);
						if (temp)
							flag = true;
					}
				}
			}
			r++;
		}
		Map ans = new Map(newMap);
		return ans;
	}
	/*
	The createTempMap function creates a new temporary map based on the given map (temp) and a new value (newV).
	It returns a 2D array representing the new temporary map.
	 */
	private  int [][] createTempMap (int [] [] temp,int newV){
		int [] [] ans = new int [temp.length][temp[0].length]; //Defining a 2D array
		for(int i = 0; i< temp.length; i++) { //Iterate over the rows of the map
			for(int j = 0; j< temp[0].length; j++) { //iterate over the columns of the map
				if (temp [i][j]== newV) // Check if the value matches the new value
					ans [i][j]= -1; //  Mark the pixel as -1 in the temporary map
				else
					ans[i] [j]= -2 ;  // Mark the pixel as -2 in the temporary map
			}
		}
		return ans ;
	}
	/*
The markNeighbors function marks the neighboring pixels of a given location (x, y) on the map. It returns a Boolean value indicating whether any neighbors have been checked.
The function checks everything clockwise starting from the correct neighbor. If the neighbor is within the bounds of the map and its value
 is -1 (indicating an unmarked pixel), it marks the neighbor by assigning the value of the current pixel plus one (map[x][y] + 1) to
 the neighbor's position (map) [nx] [ny]). In addition, the function updates the variable ans to true if any neighbors have been marked.
The function handles special cases for pixels located at the edges of the map. If the cyclicFlag is set to true and the current pixel is at
the right or left edge, the function wraps to the opposite edge. Similarly, if the current pixel is at the top or bottom edge, it wraps
	 */
	private boolean markNeighbors(int[][] map, int x, int y) {
		boolean ans =false;
		int nx =0; int ny=0;//neighbor coordinates
		if (x==map.length-1&&this._cyclicFlag){//Right edge
			nx=0;
			ny=y;
		}
		else {
			nx=x+1;
			ny=y;
		}
		if(nx<map.length&&map[nx][ny]==-1) {
			map[nx][ny]=map[x][y]+1;//Mark the right neighbor
			ans=true;
		}

		if (x==0&&this._cyclicFlag){//Left edge
			nx=map.length-1;
			ny=y;
		}
		else {
			nx=x-1;
			ny=y;
		}
		if(nx>-1&&map[nx][ny]==-1) {
			map[nx][ny]=map[x][y]+1;//Mark the left neighbor
			ans=true;
		}

		if (y==0&&this._cyclicFlag){//Up edge
			nx=x;
			ny=map[0].length-1;
		}
		else {
			nx=x;
			ny=y-1;
		}
		if(ny>-1&&map[nx][ny]==-1) {
			map[nx][ny]=map[x][y]+1;//Mark the up neighbor
			ans=true;
		}

		if (y==map[0].length-1&&this._cyclicFlag) {//Down edge
			nx=x;
			ny=0;
		}
		else {
			nx=x;
			ny=y+1;
		}
		if(ny<map[0].length&& map[nx][ny]==-1) {
			map[nx][ny]=map[x][y]+1;//Mark the down neighbor
			ans=true;
		}

		return ans;
	}

	/*
The createMapAllD function creates a new map for the allDistance method. It takes the original map and the obstacle color
as parameters. The function returns a new map where cells with the obstacle color are marked as 2, and all other cells are marked as 1.
The function iterates over each cell in the original map using nested loops. If the cell color matches the obstacle color, it sets the
corresponding cell in the new map to 2. Otherwise, it sets the cell in the new map to 1.
Finally, the function returns the new map with the updated markings.
	 */
	private int[][] createMapAllD(int[][] map, int obsColor){
		int [][] ans = new int[map.length][map[0].length];
		for( int i=0;i<map.length;i++){
			for(int j=0;j<map[0].length;j++){
				if(map[i][j]==obsColor)
					ans[i][j]=-2;
				else
					ans[i][j]=-1;
			}
		}
		return ans;

	}
}





















