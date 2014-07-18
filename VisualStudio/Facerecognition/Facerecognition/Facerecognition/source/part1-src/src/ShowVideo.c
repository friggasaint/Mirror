// ShowVideo.c
//
// Capture video and display it in an OpenCV window

#include "cv.h"
#include "highgui.h"
#include <stdio.h>

const char * VIDEO_WINDOW = "VideoWindow";

int main( int argc, char** argv )
{
	CvCapture * pCapture    = 0;
	IplImage  * pVideoFrame = 0;
	IplImage  * pDisplayImg  = 0;

	// Initialize video capture
	pCapture = cvCaptureFromCAM( CV_CAP_ANY );
	if( !pCapture )
	{
		fprintf(stderr, "failed to initialize video capture\n");
		return -1;
	}


	// Tell user how to exit
	printf( "Press the ESC key to quit\n"  );

	// Create a window to display captured frames
	cvNamedWindow( VIDEO_WINDOW, 1 );


	// Capture video frames and display them in the video window
	while( 1 )
	{
		// Capture the next frame
		pVideoFrame = cvQueryFrame( pCapture );
		if( !pVideoFrame )
		{
			fprintf(stderr, "failed to get a video frame\n");
		}

		// Copy it to the display image
		if( !pDisplayImg )
		{
			pDisplayImg = cvCreateImage( cvGetSize(pVideoFrame), 8, 3 );
			pDisplayImg->origin = pVideoFrame->origin;
		}
		cvCopy( pVideoFrame, pDisplayImg, 0 );

		// Show the display image
		cvShowImage( VIDEO_WINDOW, pDisplayImg );

		if( (char)27 == cvWaitKey(10) )
			break;

	};

	// Close the video-display window
	cvDestroyWindow( VIDEO_WINDOW );

	// Terminate video capture and free capture resources
	cvReleaseCapture( &pCapture );

	// Free the display image
	cvReleaseImage( &pDisplayImg );

	return 0;
}

