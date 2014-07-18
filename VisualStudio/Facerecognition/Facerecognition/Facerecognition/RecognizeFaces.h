#include "facerec_eigenfaces.h"

using namespace cv;
using namespace std;

class RecognizeFaces
{
public:
	RecognizeFaces(void);
	~RecognizeFaces(void);

	void static recognizeFace(std::vector<cv::Mat> images);
	void static control(float** images);
	Mat array2Mat();

private:
	

};