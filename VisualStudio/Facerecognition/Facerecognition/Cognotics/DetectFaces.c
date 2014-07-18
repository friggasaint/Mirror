// DetectFaces.c
//
// Example code showing how to detect faces using
// OpenCV's CvHaarClassifierCascade
//
// See also, facedetect.c, in the samples directory.
//
// Usage: DetectFaces <imagefilename>

#include <stdio.h>
#include "opencv/cv.h"
#include "opencv/highgui.h"


// *** Change this to your install location! ***
// *********************************************
#define OPENCV_ROOT  "C:/Program Files/OpenCV/1.0"
// *********************************************


void displayDetections(IplImage * pInpImg, CvSeq * pFaceRectSeq);

int main(int argc, char** argv)
{
	// variables
	IplImage * pInpImg = 0;
	CvHaarClassifierCascade * pCascade = 0;  // the face detector
	CvMemStorage * pStorage = 0;        // memory for detector to use
	CvSeq * pFaceRectSeq;               // memory-access interface

	// usage check
	if(argc < 2)
	{
		printf("Missing name of image file!\n"
		       "Usage: %s <imagefilename>\n", argv[0]);
		exit(-1);
	}

	// initializations
	pInpImg = (argc > 1) ? cvLoadImage(argv[1], CV_LOAD_IMAGE_COLOR) : 0;
	pStorage = cvCreateMemStorage(0);
	pCascade = (CvHaarClassifierCascade *)cvLoad
	   ((OPENCV_ROOT"/data/haarcascades/haarcascade_frontalface_default.xml"),
	   0, 0, 0 );

	// validate that everything initialized properly
	if( !pInpImg || !pStorage || !pCascade )
	{
		printf("Initialization failed: %s\n",
			(!pInpImg)?  "can't load image file" :
			(!pCascade)? "can't load haar-cascade -- "
				         "make sure path is correct" :
			"unable to allocate memory for data storage", argv[1]);
		exit(-1);
	}

	// detect faces in image
	pFaceRectSeq = cvHaarDetectObjects
		(pInpImg, pCascade, pStorage,
		1.1,                       // increase search scale by 10% each pass
		3,                         // merge groups of three detections
		CV_HAAR_DO_CANNY_PRUNING,  // skip regions unlikely to contain a face
		cvSize(40, 40),            // smallest size face to detect = 40x40
		cvSize(200,200));


	// display detected faces
	displayDetections(pInpImg, pFaceRectSeq);

	// clean up and release resources
	cvReleaseImage(&pInpImg);
	if(pCascade) cvReleaseHaarClassifierCascade(&pCascade);
	if(pStorage) cvReleaseMemStorage(&pStorage);

	return 0;
}


void displayDetections(IplImage * pInpImg, CvSeq * pFaceRectSeq)
{
	const char * DISPLAY_WINDOW = "Haar Window";
	int i;
	
	// create a window to display detected faces
	cvNamedWindow(DISPLAY_WINDOW, CV_WINDOW_AUTOSIZE);

	// draw a rectangular outline around each detection
	for(i=0;i<(pFaceRectSeq? pFaceRectSeq->total:0); i++ )
	{
		CvRect* r = (CvRect*)cvGetSeqElem(pFaceRectSeq, i);
		CvPoint pt1 = { r->x, r->y };
		CvPoint pt2 = { r->x + r->width, r->y + r->height };
		cvRectangle(pInpImg, pt1, pt2, CV_RGB(0,255,0), 3, 4, 0);
	}

	// display face detections
	cvShowImage(DISPLAY_WINDOW, pInpImg);
	cvWaitKey(0);
	cvDestroyWindow(DISPLAY_WINDOW);
}

