/**
 * 
 */
package imageprocessing;

import static org.junit.Assert.*;

import java.awt.Color;

import edu.princeton.cs.introcs.Picture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Danny
 *
 */
public class ConnectedComponentImageTest {
	ConnectedComponentImage test;
	Picture pic;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		test = new ConnectedComponentImage("images/bacteria.bmp");
		test.weightedQuickFind(10);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		test = null;
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#ConnectedComponentImage(java.lang.String)}.
	 */
	@Test
	public void testConnectedComponentImage() {
		assertNotNull(test); //tests if the picture isn't null     // TODO
	    try{
	    	Picture picture = new Picture("INVALID PIC");
	    	fail("An error should be thrown");
	    }
	    catch (Exception e){
	    	assertTrue(true);
	    }
	    Picture pic = new Picture("http://images.fastcompany.com/upload/Simple.jpg");
	    assertEquals(640, pic.width());
	    assertEquals(480, pic.height());
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#countComponents()}.
	 */
	@Test
	public void testCountComponents() {
	   assertSame(10, test.countComponents());
	   test.union(0, 1);
	   assertSame(9, test.countComponents());
	   test.union(1, 2);
	   test.union(2, 3);
	   test.union(3, 4);
	   test.union(4, 5);
	   test.union(5, 6);
	   test.union(6, 7);
	   test.union(7, 8);
	   test.union(8, 9);
	   assertSame(1, test.countComponents());
	   test.union(1, 9); //if you repeat a union
	   assertSame(1, test.countComponents());
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#identifyComponentImage()}.
	 */
	@Test
	public void testIdentifyComponentImage() {
		//pixel 0,0 should be RED I think.
		Color b = test.getOriginalWithBorders().get(0, 0);
		Color c = test.getPlainImage().get(0, 0);
		assertNotSame(b.getBlue(), c.getBlue());
		assertNotSame(b.getGreen(), c.getGreen());
		assertNotSame(b.getRed(), c.getRed());
		Color d = test.getOriginalWithBorders().get(1,1);
		Color e = test.getPlainImage().get(1, 1);
		assertSame(d.getBlue(), e.getBlue());
		assertSame(d.getGreen(), e.getGreen());
		assertSame(d.getRed(), e.getRed());
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#colourComponentImage()}.
	 */
	@Test
	public void testColourComponentImage() {
		Color a = test.getPlainImage().get(0, 0);
		Color b = test.getPicture().get(0, 0);
		Color c = test.getPicture().get(0, 0);
		assertNotSame(a.getRGB(),b.getRGB());
		assertNotSame(b.getRGB(), c.getRGB());
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#getPicture()}.
	 */
	@Test
	public void testGetPicture() {
		try{
			test.getPicture();
			assertTrue(true);
		}
		catch (Exception e){
			fail("Should not be an exception");
		}
		try{
			ConnectedComponentImage cci = new ConnectedComponentImage("");
			test.getPicture();
			fail("Should error");
		}
		catch(Exception e){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#binaryComponentImage()}.
	 */
	@Test
	public void testBinaryComponentImage() {
		Picture pic = new Picture("http://images.fastcompany.com/upload/Simple.jpg");
	    assertEquals(640, pic.width());
	    assertEquals(480, pic.height());
	    
	    //Testing boundary
	    try{
	    	pic.get(641, 400);
	    	fail("Outside the boundary");
	    }
	    catch (Exception e){
	    	assertTrue(true);       
	    }
	    try{
	    	pic.get(640, 480);
	    	fail("Outside the boundary");
	    }
	    catch (Exception e){
	    	assertTrue(true);
	    }
	    try{
	    	pic.get(-1, -1);
	    	fail("outside the boundary");
	    }
	    catch (Exception e){
	    	assertTrue(true);
	    }
	    try{
	    	pic.get(639, 479);
	    	assertTrue(true);
	    }
	    catch (Exception e){
	    	fail("Inside the boundary");
	    }
	    try{
	    	pic.get(0, 0);
	    	assertTrue(true);
	    }
	    catch (Exception e){
	    	fail("Inside the boundary");
	    }
	    
	    //testing if statement
	    pic.set(0, 0, Color.BLACK); //black pixel
	    Color c = pic.get(0, 0);
	    double thresholdPixelValue = 128.0; 
	    if(Luminance.lum(c) < thresholdPixelValue){
	    	assertTrue(true);
	    }
	    if(Luminance.lum(c) > thresholdPixelValue){
	    	fail("Should be less than the threshold");
	    }
	    else{
	    	assertTrue(true);
	    }
	    pic.set(0, 0, Color.WHITE); // white pixel
	    Color d = pic.get(0,0);
	    if(Luminance.lum(d) < thresholdPixelValue){
	    	fail("Should not be less than the threshold");
	    }
	    else{
	    	assertTrue(true);
	    }
	    if(Luminance.lum(d) > thresholdPixelValue){
	    	assertTrue(true);
	    }
	    else{
	    	fail("Should be valid");
	    }
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#weightedQuickFind(int)}.
	 */
	@Test
	public void testWeightedQuickFind() {
		for(int i=0; i<10; i++){
			assertSame(i, test.find(i));
		}
	}
	

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#validate(int)}.
	 */
	@Test
	public void testValidate() {
		try{
		test.validate(5);
		assertTrue(true);
		}
		catch (Exception e){
			fail("Should be no exception");
		}
		try{
			test.validate(10);
			fail("Should throw an exception");
		}
		catch (Exception e){
			assertTrue(true);
		}
		try{
			test.validate(-1);
			fail("Should throw an exception");
		}
		catch (Exception e){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#find(int)}.
	 */
	@Test
	public void testFind() {
		 assertSame(0, test.find(0));

	        test.union(0,9);
	        assertSame(0, test.find(9));
	        
	        assertNotSame(0, test.find(1));
	        try{
	        	test.union(0, 10);
	        	fail("Shouldn't work");
	        }
	        catch (Exception e){
	        	assertTrue(true);
	        }
	}


	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#connected(int, int)}.
	 */
	@Test
	public void testConnected() {
		 //test sites not connected
	    for (int i = 1; i < 10; i++) {
	            assertFalse(test.connected(0, i));
	        }

	    //test connections
	    test.union(0, 1);
	        test.union(9, 8);
	        assertTrue(test.connected(1, 0));
	        assertTrue(test.connected(9, 8));

	        test.union(8, 1);
	        assertTrue(test.connected(8, 1));
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#union(int, int)}.
	 */
	@Test
	public void testUnion() {
		test.union(0, 1);
        test.union(1, 2);
        test.union(2, 3);
        test.union(3, 4);
        try{
        	test.union(1, 10);
        	fail("should throw an error");
        }
        catch (Exception e){
        	assertTrue(true);
        }
        assertSame(0, test.find(1));
        assertSame(0, test.find(4));
        assertNotSame(test.find(0),test.find(5));
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#quickUnion()}.
	 */
	@Test
	public void testQuickUnion() {
		try{
			test.union(0*2+0, 0*2+0-1); //checks the bottom left pixel, first in the array therefore none to the left
			fail("Error no pos to the left");
		}
		catch (Exception e){
			assertTrue(true);
		}
		
		for (int x=0; x<2; x++){
			for(int y=0; y<2; y++){
				//checking at x=0 for left pixel, should throw an error
				test.union(test.find(y*2+x), test.find(y*2+x+1)); //pixel to the right
				assertSame(test.find(y*2+x), test.find(y*2+x+1));
			}
	}
	}

	/**
	 * Test method for {@link imageprocessing.ConnectedComponentImage#getPlainImage()}.
	 */
	@Test
	public void testGetPlainImage() {
		Color a = test.getPlainImage().get(0, 0);
		Picture pic = new Picture("images/bacteria.bmp");
		Color b = pic.get(0, 0);
		assertSame(a.getBlue(), b.getBlue());
		assertSame(a.getRed(), b.getRed());
		assertSame(a.getGreen(), b.getGreen());
	}

    /**
     * Test method for {@link imageprocessing.ConnectedComponentImage#getOriginalWithBorders}.
     */
	@Test
	public void testGetOriginalWithBorders() {
		Color b = test.getOriginalWithBorders().get(0, 0);
		Color c = test.getPlainImage().get(0, 0);
		assertNotSame(b.getBlue(), c.getBlue());
		assertNotSame(b.getGreen(), c.getGreen());
		assertNotSame(b.getRed(), c.getRed());
		Color d = test.getOriginalWithBorders().get(1,1);
		Color e = test.getPlainImage().get(1, 1);
		assertSame(d.getBlue(), e.getBlue());
		assertSame(d.getGreen(), e.getGreen());
		assertSame(d.getRed(), e.getRed());
	}
}
