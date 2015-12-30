package anca.cameraexample3;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
  private Camera mCamera;
  private CameraPreview mPreview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//     Create an instance of Camera
    mCamera = getCameraInstance();

    // Create our Preview view and set it as the content of our activity.
    mPreview = new CameraPreview(this, mCamera);
    FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
    preview.addView(mPreview);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    releaseCamera();
  }

  private static int findBackFacingCamera() {
    int cameraId = -1;
    // Search for the front facing camera
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      Camera.CameraInfo info = new Camera.CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
        cameraId = i;
        break;
      }
    }
    return cameraId;
  }

  /** A safe way to get an instance of the Camera object. */
  public static Camera getCameraInstance(){
    Camera c = null;
    try {
      c = Camera.open(findBackFacingCamera()); // attempt to get a Camera instance
    }
    catch (Exception e){
      // Camera is not available (in use or does not exist)
      e.printStackTrace();
    }
    return c; // returns null if camera is unavailable
  }

  private void releaseCamera(){
    if (mCamera != null){
      mCamera.release();        // release the camera for other applications
      mCamera = null;
    }
  }
}
