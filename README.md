# algorithms-assign1
In this assignment, the aim of the application was to devise an application that can read in image file, 
count the number of components it has, binarize the image, colour the image, and also draw a border around each component. 

The breakdown of how I done each of these is as follows:
Read in an image file: This is setup by default in the constructor to be one of the images provided to us in the assignment, 
"bacteria.bmp". From the GUI, there's a navigation at the top left corner called file, where you are then able to navigate
to the other pictures in the images folder.

Count the Number of Components: 
Count the number of components simply just returns the a variable which is decremented every time a union operation is carried
out. The union is used to see which colours are the same, and then connects them. By doing this, you can find out the number
of components in the image file. 

Binarize the image:
This method checks if the pixel is closer to white or black by using a class called Luminance. It can then just set the pixel 
depending on what the threshold pixel value is to either black or white

Colour the image:
This adds a random colour to each component in the image. It uses a private method setUpArrayList() which sets up the root of
each component and then it if they are equal it adds a random colour which is set up at a randomColor()

Drawing a border around an image:
This works in more or less the same way as colouring the image, but instead of adding the colour it changes the min, and max x,y
co ordinates then draws a red line for each of them.

Junit Test Cases:
I provided tests for the code that I created, but found test driven development kind of hard because I found that knowing how to test the code was hard so I'm not sure if they're comprehensive. The coverage for the class finished at about 80%.

Extras:
I added in a GUI, but since I had never done a GUI before it was difficult to get everything to work. I had to add in 
additonal methods to get the binary image to work with the GUI because it was carrying it out twice. The buttons only work
once, and you can't close down the java application. I found the assignment quite difficult at first, and ended up doing
a lot of additional research that in the end was irrelevent. This involved stuff like two-pass algorithms that I found 
extremely hard to understand how they worked in the long term, but I think now I have a grasp of it for future projects.
I also learned about two dimensional arrays which might also be valuable in the long term. 
