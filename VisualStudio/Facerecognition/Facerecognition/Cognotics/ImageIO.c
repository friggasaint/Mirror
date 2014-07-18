// ImageIO.c
//
// Example showing how to read and write images

#include "cv.h"
#include "highgui.h"
#include <stdio.h>

int main(int argc, char** argv)
{
	IplImage * pInpImg = 0;

	// Load an image from file
	pInpImg = cvLoadImage("my_image.jpg", CV_LOAD_IMAGE_UNCHANGED);
	if(!pInpImg)
	{
		fprintf(stderr, "failed to load input image\n");
		return -1;
	}

	// Write the image to a file with a different name,
	// using a different image format -- .png instead of .jpg
	if( !cvSaveImage("my_image_copy.png", pInpImg) )
	{
		fprintf(stderr, "failed to write image file\n");
	}

	// Remember to free image memory after using it!
	cvReleaseImage(&pInpImg);

	return 0;
}








