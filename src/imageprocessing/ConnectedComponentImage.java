package imageprocessing;



import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


import edu.princeton.cs.introcs.Picture;


/*************************************************************************
 * Compilation: javac ConnectedComponentImage.java
 * 
 * The <tt>ConnectedComponentImage</tt> class
 * <p>
 * You do the rest....
 * 
 * @author 
 *************************************************************************/
public class ConnectedComponentImage {

	Picture picture;
    private int[] id;    // id[i] = component identifier of i
    private int [] size; //not sure if needed yet TODO
    private int count;   // number of components
    private int width;
    private int height;
    private int n;
    private boolean[] visited; // checks if you've visited the pixel
    private Random numberGenerator;
    private ArrayList<Integer> sorted;
    private Picture pic; //pic is a copy of the original with borders
    private Picture original;
    private Picture binary; //needed for the GUI
	/**
	 * Initialise fields
	 * 
	 * @param fileLocation
	 */
	public ConnectedComponentImage(String fileLocation) {
		// TODO
		boolean condition = false;
		//while(!condition){
			try {
		picture = new Picture(fileLocation);
		pic = new Picture(fileLocation); //copy of the original image
		condition = true;
		}
		catch (Exception e){
			System.out.println("Not a valid picture");
			condition = false;
		}
//		}
		width = picture.width();
		height = picture.height();
		n=width*height;
		numberGenerator = new Random();
        sorted = new ArrayList<>();
		weightedQuickFind(n);
		binaryComponentImage();
		binary = new Picture(fileLocation);
		quickUnion();
		setUpArrayList();
        countComponents();
    	colourComponentImage();
		identifyComponentImage();
		original = new Picture(fileLocation);
		picture = new Picture(fileLocation); //because GUI binary wont work as it's being called twice, this fixes it
		binary = getBinary(); // because GUI binary
	}

	public static void main(String [] args){
		ConnectedComponentImage connectedComponentImage = 
		new ConnectedComponentImage("images/bacteria.bmp");
	}
	
	/**
	 * Returns the number of components identified in the image.
	 * 
	 * @return the number of components (between 1 and N)
	 */
	public int countComponents() {
       return count;
    }
	
	/**
	 * Returns a binarised version of the original image
	 * 
	 * 
	 */
	public Picture binaryComponentImage() {
		width = picture.width();
		height = picture.height();
		double thresholdPixelValue = 128.0;
		
		for (int x = 0; x<width; x++){
			for (int y =0; y<height; y++){
				Color c = picture.get(x, y);
				if (Luminance.lum(c) < thresholdPixelValue){
					picture.set(x, y, Color.BLACK);
				}
				else{
					picture.set(x, y, Color.WHITE);
				}
			}
		}
	return picture;	
	}
	
	/**
	 * Returns a picture with each object updated to a random colour.
	 * 
	 * @return a picture object with all components coloured.
	 */
	public Picture colourComponentImage() {
		Color randomColors[] = new Color[sorted.size()];
		for(int a=0; a<sorted.size(); a++){
			randomColors[a] = randomColor();
		}
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				for(int b=0; b<sorted.size(); b++){
					if(find(y*width+x) == sorted.get(b)){
						picture.set(x,y,randomColors[b]);
					}
				}
			}
		}
		return picture;
	}
	
	
	/**
	 * Returns the original image with each object bounded by a red box.
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture identifyComponentImage() {
		for (int a=0; a<sorted.size(); a++){
			int maxX = 0;
			int minX = width;
			int maxY = 0;
			int minY = height;
		for (int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				if(find(y*width+x) == sorted.get(a)){
					 if (x < minX)
							minX = x;
						if (x > maxX)
							maxX = x;
						if (y < minY)
							minY = y;
						if (y > maxY)
							maxY = y;
				}
				}
			}
		for (int x = minX; x <= maxX; x++) {
			pic.set(x, minY, Color.RED);
			pic.set(x, maxY, Color.RED);
		}
		for (int y = minY; y <= maxY; y++) {
			pic.set(minX, y, Color.RED);
			pic.set(maxX, y, Color.RED);
		}
		}
		return pic;
	}
	
	/**
	 * returns the original picture you started with
	 * @return 
	 */
	public Picture getPlainImage(){
		return original;
	}
	
	/**
	 * returns the binary image
	 */
	public Picture getBinary(){
		binaryComponentImage();
		binary=getPicture();
		return binary;
	}
	
	/**
	 * sets up an arraylist with each different union in the picture
	 */
    private void setUpArrayList(){
 		for(int x=0; x<width; x++){
 			for(int y=0; y<height; y++){
 				int currentNumber = find(y*width+x);
 				if(x==0 && y==0){
 					sorted.add(currentNumber);
 				}
 				if(!sorted.contains(find(y*width+x))){
 					sorted.add(currentNumber);
 				}
 			}
 		}
 		Collections.sort(sorted);
    }
    
    /**
     * returns the picture even after it has been changed
     * @return returns the changed picture
     */
	public Picture getPicture() {
		return picture;
	}
	
	/**
	 * returns the original image with borders around each component
	 * @return the original image with borders around all of the components
	 */
	public Picture getOriginalWithBorders(){
		return pic;
	}


    
    /**
     * Initializes an empty union-find data structure with N sites
     */
    public void weightedQuickFind(int n) {
    	id = new int[n];
    	size = new int[n];
    	visited = new boolean[n];
    	count = n;
    	for (int i=0; i < n; i++){{
    		id[i] = i;
    		size[i] =1;
    		visited[i]=false; //setup every element to false
    	}
    	}
    }
    
    /**
     * checks if it's a valid location in the array
     */
    public void validate(int p){
    	int N = size.length;
    	if(p < 0 || p >= N){
    		throw new IndexOutOfBoundsException("Index isn't valid in the Array. It needs to be between 0 and " + (N-1) );
    	}

    }
    
    /*
     * finds the root of the entry checked
     */
    public int find(int p){
    	validate(p);
    	while (p != id[p]){
    		p = id[p];
    	}
    	return p;
    }
     
    /**
     * Returns true if the the two sites are in the same component.
     */
    public boolean connected(int p, int q) {
       return find(p) == find(q);
    }

    /**
     * Merges the component containing site p with the 
     * the component containing site q.
     */
    public void union(int p, int q) {

   	 
   	 int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            id[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            id[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
   }

    /**
     * Gets a random color
     * @return returns a random color
     */
    private Color randomColor(){
    	float r = numberGenerator.nextFloat();
    	float g = numberGenerator.nextFloat();
    	float b = numberGenerator.nextFloat();
    	Color randomColor = new Color(r, g, b);
    	return randomColor;
    }
 
    /**
     * Carries out all the checks when carrying out union on each pixel and then calls the union method for each
     */
    public void quickUnion(){
		width = picture.width();
		height = picture.height();
		double thresholdPixelValue = 128.0;
		for (int x = 0; x<width; x++){
			for (int y =0; y<height; y++){
				Color c = picture.get(x, y);		
					if(x == 0){ //this is for all the pixels at column 1.
						//Don't check left pixels
						//AT x=0, y=0
						if(y==0){
							//Don't check bottom pixels, need to check pixels right, top right and above
							Color right = picture.get(x+1, y);
							Color topRight = picture.get(x+1, y+1);
							Color top = picture.get(x, y+1);
							if(Luminance.lum(c) > thresholdPixelValue){
							if(Luminance.lum(right) > thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								union(id[y*width+x], id[y*width+x+1]);
							}
							if(Luminance.lum(topRight) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
								union(id[y*width+x], id[(y+1)*width+x+1]);
							}
							if(Luminance.lum(top) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
								union(id[y*width+x], id[(y+1)*width+x]);
							}
							}
							//for the background pixels
							if(Luminance.lum(c) < thresholdPixelValue){
								if(Luminance.lum(right) <thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
									union(id[y*width+x], id[y*width+x+1]);
									
								}
								if(Luminance.lum(topRight) < thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
									union(id[y*width+x], id[(y+1)*width+x+1]);
									
								}
								if(Luminance.lum(top) < thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
									union(id[y*width+x], id[(y+1)*width+x]);
									
								}
							}
							
						}
						//AT x=0 y=maxHeight
						 if(y>=height-1){
							//Don't check above pixels, need to check pixels right, bottom right 
							Color right = picture.get(x+1, y);
							Color bottomRight = picture.get(x+1, y-1);
							if(Luminance.lum(c) > thresholdPixelValue){
							if(Luminance.lum(right) > thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								union(id[y*width+x], id[y*width+x+1]);
							}
						    if(Luminance.lum(bottomRight) > thresholdPixelValue && !connected(id[y*width+x], id[(y-1)*width+x+1])){
								union(id[y*width+x], id[(y-1)*width+x+1]);
							}
							}
							if(Luminance.lum(c)<thresholdPixelValue){
								if(Luminance.lum(right)<thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
									union(id[y*width+x], id[y*width+x+1]);
									
								}
								if(Luminance.lum(bottomRight)<thresholdPixelValue && !connected(id[y*width+x], id[(y-1)*width+x+1])){
									union(id[y*width+x], id[(y-1)*width+x+1]);
									
								}
							}
							
						}
						 //AT x=0 and y in everything between
						 if(y > 0 && y <height-1){
							//Check top pixels, top right, right
							Color right = picture.get(x+1, y);
							Color topRight = picture.get(x+1, y+1);
							Color above = picture.get(x, y+1);
							if(Luminance.lum(c) > thresholdPixelValue){
							if(Luminance.lum(right) > thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								union(id[y*width+x], id[y*width+x+1]);
							}
							if(Luminance.lum(topRight) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
								union(id[y*width+x], id[(y+1)*width+x+1]);
							}
							if(Luminance.lum(above) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
								union(id[y*width+x], id[(y+1)*width+x]);
							}
						}
							if(Luminance.lum(c)<thresholdPixelValue){
								if(Luminance.lum(right)<thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
									union(id[y*width+x], id[y*width+x+1]);
									
								}
								if(Luminance.lum(topRight)<thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
									union(id[y*width+x], id[(y+1)*width+x+1]);
									
								}
								if(Luminance.lum(above)<thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
									union(id[y*width+x], id[(y+1)*width+x]);
									
									}

							}
						}
					}
				
					
					

					
					//Last row doesn't need to be checked at all
					 if(x >= width){
						//Don't check pixels to the right I don't know if I need to do anything
						continue;
					}
					
					//when y =0
					 if(y == 0){
						//don't check bottom pixels check top right and right pixels, also above
                          
						 if(x>=width){
						}
						 //when x is not 0 or maxRow
						 if(x>0 && x<width-1){ 
							// need to check topright, right and above
							Color right = picture.get(x+1, y);
							Color topRight = picture.get(x+1, y+1);
							Color above = picture.get(x, y+1);
							if(Luminance.lum(c)>thresholdPixelValue){
							if(Luminance.lum(right) > thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								union(id[y*width+x], id[y*width+x+1]);
							}
							if(Luminance.lum(topRight) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
								union(id[y*width+x], id[(y+1)*width+x+1]);
							}
							if(Luminance.lum(above) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
								union(id[y*width+x], id[(y+1)*width+x]);
							}
						}
							if(Luminance.lum(c)<thresholdPixelValue){
								if(Luminance.lum(right)<thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
									union(id[y*width+x], id[y*width+x+1]);
									
								}
								if(Luminance.lum(topRight)<thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
									union(id[y*width+x], id[(y+1)*width+x+1]);
									
								}
								if(Luminance.lum(above)<thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
									union(id[y*width+x], id[(y+1)*width+x]);
									
								}
							}
						
						}
					
					}
					
					//when x !=0 and not maxRow, when y!=0 and y is not maxCol
					 if(x>0 && x<width-1 && y>0 && y<height-1){
						//pixels not around the border? check above, right, topright
						Color right = picture.get(x+1, y);
						Color topRight = picture.get(x+1, y+1);
						Color above = picture.get(x, y+1);
						Color bottomRight = picture.get(x+1, y-1);
						if(Luminance.lum(c)>thresholdPixelValue){
						if(Luminance.lum(right) >thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
							union(id[y*width+x], id[y*width+x+1]);
						}
						if(Luminance.lum(topRight) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
							union(id[y*width+x], id[(y+1)*width+x+1]);
						}
						if(Luminance.lum(above) > thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
							union(id[y*width+x], id[(y+1)*width+x]);

						}
						if(Luminance.lum(bottomRight)>thresholdPixelValue && !connected(id[y*width+x], id[(y-1)*width+x+1])){
							union(id[y*width+x], id[(y-1)*width+x+1]);
							
						}
					}
						if(Luminance.lum(c)<thresholdPixelValue){
							if(Luminance.lum(right)<thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								union(id[y*width+x], id[y*width+x+1]);
								
							}
							if(Luminance.lum(topRight)<thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x+1])){
								union(id[y*width+x], id[(y+1)*width+x+1]);
								
							}
							if(Luminance.lum(above)<thresholdPixelValue && !connected(id[y*width+x], id[(y+1)*width+x])){
								union(id[y*width+x], id[(y+1)*width+x]);
								
							}
							if(Luminance.lum(bottomRight)<thresholdPixelValue && !connected(id[y*width+x], id[(y-1)*width+x+1])){
								union(id[y*width+x], id[(y-1)*width+x+1]);
								
							}
						}
						}
					 
					 
					 
					 //when y is at the maxHeight
					 if(y==height-1){
						 //check right when it isn't x= maxRow, check bottom right also
						 if(x==width-1){
							 continue;
						 }
						 Color right = picture.get(x+1, y);
						 Color bottomRight = picture.get(x+1, y-1);
						 if(Luminance.lum(c)>thresholdPixelValue){
							 if(Luminance.lum(right)>thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								 union(id[y*width+x], id[y*width+x+1]);
								 
							 }
							 if(Luminance.lum(bottomRight)>thresholdPixelValue && !connected(id[y*width+x], id[(y-1)*width+x+1])){
								 union(id[y*width+x], id[(y-1)*width+x+1]);
								 
							 }
						 }
						 if(Luminance.lum(c)<thresholdPixelValue){
							 if(Luminance.lum(right)<thresholdPixelValue && !connected(id[y*width+x], id[y*width+x+1])){
								 union(id[y*width+x], id[y*width+x+1]);
								 
							 }
							 if(Luminance.lum(bottomRight)<thresholdPixelValue && !connected(id[y*width+x], id[(y-1)*width+x+1])){
								 union(id[y*width+x], id[(y-1)*width+x+1]);
								 
							 }
						 }
					 }
					
				}
				}
				
				}
						
    }



