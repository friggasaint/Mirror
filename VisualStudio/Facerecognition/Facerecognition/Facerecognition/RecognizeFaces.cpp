#include "RecognizeFaces.h"
#include <opencv2/imgproc/imgproc.hpp>

#define IDX(X,Y,WIDTH) ((X) + (Y) * (WIDTH))

RecognizeFaces::RecognizeFaces(void)
{
}


RecognizeFaces::~RecognizeFaces(void)
{
}

//Mat RecognizeFaces::array2Mat(){
//
//	MEAN = Mat();
//	imshow(mean  face, InputArray mat);
//}

//void RecognizeFaces::control(vector<Mat> images){
//
//}


void RecognizeFaces::recognizeFace(vector<Mat> images){
	// used Variables
	int M = 400;							// PictureCount
	int w = 92;
	int h = 112;
	int N = w * h;						// Dimensions of the Pictures
	static float facesMatr[400][92 * 112];	// Matrix containing all Faces
	static float facesMatrTransp[92 * 112][400];	// used for Transposed Version of facesMatr
	static float meanFace[92 * 112];
	static float covarianceMatr[400][400];
	static float eigenFaces[400][92 * 112];
	static float weights[400][92 * 112];

	// load Images(92x112 = 10304)
	// they have a representation of nxn pixels. only use grayscale. 
	
	// convert images to one 2darray consisting of all vectors
	int i = 0;
	for (auto it = images.begin(); it != images.end(); it++)
	{
		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				facesMatr[i][y + x*w] = it->at<uchar>(y, x);
			}	
		}
		i++;
	}


	float control[112][92];
	Mat A = Mat(112, 92, CV_8UC1);
	for (int i = 0; i < h; i++)
	{
		for (int j = 0; j < w; j++)
		{
			A.at<uchar>(i,j) = facesMatr[0][i + j*w];
		}
	}
	imshow("first face", A);
	waitKey(0);

	// calculate mean face
	for (int i = 0; i < N; i++)
	{
		float sum = 0;
		for (int j = 0; j < M; j++)
		{
			sum += facesMatr[j][i];
		}
		meanFace[i] = sum / M;
	}

	// check if mean Face
	for (int i = 0; i < h; i++)
	{
		for (int j = 0; j < w; j++)
		{
			A.at<uchar>(i, j) = meanFace[i + j*w];
		}
	}
	imshow(" mean Face", A);
	waitKey(0);

	// normalize face vectors (facevectors - average face)
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			facesMatr[i][j] -= meanFace[j];
			if (facesMatr[i][j] < 0)
			{
				facesMatr[i][j] = 0;
			}
		}
	}

	// check normalized Face
	for (int i = 0; i < h; i++)
	{
		for (int j = 0; j < w; j++)
		{
			A.at<uchar>(i,j) = facesMatr[0][i + j*w];
		}
	}
	imshow("first normalized Face", A);
	waitKey(0);


	// create training matrix using normalized vectors A
	// calculate covariance matrix C = AA^T => huge matrix => not an option
	// find eigenvector of training matrix => dimensional reduction ->>this also reduces noise
	// use covariance of subspace (use C = A^TA)

	// create A^T
	
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			facesMatrTransp[j][i] = facesMatr[i][j];
		}
	}

	// calculate covariance Matrix C = A^TA
	float max = 0;
	for (int i = 0; i<M; i++)
	{
		for (int j = 0; j<M; j++)
		{
			covarianceMatr[i][j] = 0;
			for (int k = 0; k< 92 * 112; k++)
			{
				covarianceMatr[i][j] = covarianceMatr[i][j] + (facesMatr[i][k] * facesMatrTransp[k][j]);
				if (max < covarianceMatr[i][j])
				{
					max = covarianceMatr[i][j];
				}
			}
		}
	}

	// control Covariance
	Mat B = Mat(400, 400, CV_8UC1);
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < M; j++)
		{
			B.at<uchar>(i, j) = covarianceMatr[i][j];
		}
	}
	imshow("Covariance", B);
	waitKey(0);

	// Training Recognizer
	// map back to higher space = Eigenfaces (u_i = Av_i) (C =[v_0,..., v_n])

	// instantiate with zeroes
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			eigenFaces[i][j] = 0;
		}
	}

	max = 0;
	for (int k = 0; k < M; k++)
	{
		for (int i = 0; i < M; i++){
			for (int j = 0; j < N; j++){
				eigenFaces[i][k] += (facesMatr[i][j] * covarianceMatr[k][j]);
				if (max < eigenFaces[i][k])
					max = eigenFaces[i][k];
			}
		}
	}
	// normalize it
	/*for (int i = 0; i < M; i++){
		for (int j = 0; j < N; j++){
			covarianceMatr[i][j] = (max / eigenFaces[i][j]) * 255;
		}
	}*/

	// control Eigenfaces
	for (int i = 0; i < h; i++)
	{
		for (int j = 0; j < w; j++)
		{
			A.at<uchar>(i, j) = eigenFaces[0][i + j*w];
		}
	}
	imshow("first eigenface", A);
	waitKey(0);


	// Eigenvector + average Face = weight Vector
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			weights[i][j] = eigenFaces[i][j] + meanFace[j];
		}
	}

	// regognize unknown Face
	// Facevector -> normalize (use same average average Face)

	// v * Eigenfaces = weightvector
	// compare to weightvector to each other picture in the training set

	// nearest should be the person
}



//Mat train_data; // initially empty
//Mat train_labels; // empty, too.
//
// for each img in the train set : 
//Mat img = imread("image_path");
//Mat float_data;
//img.convertTo(float_data, CV_32FC1);             // to float
//train_data.push_back(float_data.reshape(1, 1)); // add 1 row (flattened image)
//train_labels.push_back(label_for_image);       // add 1 item
//
//KNearest knn;
//knn.train(train_data, train_classes);