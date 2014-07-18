// ConvertToGray.c
//
// Example showing how to convert an image from color
// to grayscale

#include "stdio.h"
#include "string.h"
#include "cv.h"
#include "highgui.h"

int main(int argc, char** argv)
{
	IplImage * pRGBImg  = 0;
	IplImage * pGrayImg = 0;

	// Load the RGB image from file
	pRGBImg = cvLoadImage("my_image.jpg", CV_LOAD_IMAGE_UNCHANGED);
	if(!pRGBImg)
	{
		fprintf(stderr, "failed to load input image\n");
		return -1;
	}

	// Allocate the grayscale image
	pGrayImg = cvCreateImage
		( cvSize(pRGBImg->width, pRGBImg->height), pRGBImg->depth, 1 );

	// Convert it to grayscale
	cvCvtColor(pRGBImg, pGrayImg, CV_RGB2GRAY);

	// Write the grayscale image to a file
	if( !cvSaveImage("my_image_gray.jpg", pGrayImg) )
	{
		fprintf(stderr, "failed to write image file\n");
	}

	// Free image memory
	cvReleaseImage(&pRGBImg);
	cvReleaseImage(&pGrayImg);

	return 0;
}





