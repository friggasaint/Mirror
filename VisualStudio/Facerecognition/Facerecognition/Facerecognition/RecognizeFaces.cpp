#include "RecognizeFaces.h"
#include <opencv2/imgproc/imgproc.hpp>

#define IDX(X,Y,WIDTH) ((X) + (Y) * (WIDTH))

RecognizeFaces::RecognizeFaces(void)
{
}


RecognizeFaces::~RecognizeFaces(void)
{
}


void RecognizeFaces::recognizeFace(vector<Mat> images){
	// used Variables
	int M = 400;							// PictureCount
	int N = 92 * 112;						// Dimensions of the Pictures
	static float facesMatr[400][92 * 112];	// Matrix containing all Faces
	static float facesMatrTransp[92 * 112][400];	// used for Transposed Version of facesMatr
	static float meanFace[92 * 112];
	static float covarianceMatr[400][400];

	// load Images(92x112 = 10304)
	// they have a representation of nxn pixels. only use grayscale. 
	
	// convert images to one 2darray consisting of all vectors
	int i = 0;
	for (auto it = images.begin(); it != images.end(); it++)
	{
		for (int y = 0; y < 112; y++)
		{
			for (int x = 0; x < 92; x++)
			{
				facesMatr[i][x+y*92] = it->at<unsigned char>(y, x);
			}	
		}
		i++;
	}

	// calculate mean face
	for (int i = 0; i < M; i++)
	{
		float sum = 0;
		for (int j = 0; j < N; j++)
		{
			sum += facesMatr[i][j];
		}
		meanFace[i] = sum / N;
	}

	// normalize face vectors (facevectors - average face)
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			facesMatrTransp[j][i] = facesMatr[i][j];
		}
	}

	// create training matrix using normalized vectors A
	// calculate covariance matrix C = AA^T => huge matrix => not an option
	// find eigenvector of training matrix => dimensional reduction ->>this also reduces noise
	// use covariance of subspace (use C = A^TA)

	// create A^T
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			facesMatr[i][j] -= meanFace[j];
		}
	}

	// Training Recognizer
	// map back to higher space (u_i = Av_i) (C =[v_0,..., v_n])

	// Eigenvector + average Face = weight Vector


	// regognize unknown Face
	// Facevector -> normalize (use same average average Face)

	// v * Eigenfaces = weightvector
	// compare to weightvector to each other picture in the training set

	// user nearest
}

