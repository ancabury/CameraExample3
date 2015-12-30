package anca.cameraexample3;

import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anca9 on 12/30/2015.
 */
public class PictureCallback implements Camera.PictureCallback{
  private final String TAG = "PictureCallback";
  public static final int MEDIA_TYPE_IMAGE = 1;
  public static final int MEDIA_TYPE_VIDEO = 2;

  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
    if (pictureFile == null){
      Log.d(TAG, "Error creating media file, check storage permissions: ");
      return;
    }

    try {
      FileOutputStream fos = new FileOutputStream(pictureFile);
      fos.write(data);
      fos.close();
    } catch (FileNotFoundException e) {
      Log.d(TAG, "File not found: " + e.getMessage());
    } catch (IOException e) {
      Log.d(TAG, "Error accessing file: " + e.getMessage());
    }
  }

  /** Create a File for saving an image or video */
  private File getOutputMediaFile(int type){
    // To be safe, you should check that the SDCard is mounted
    // using Environment.getExternalStorageState() before doing this.

    String state = Environment.getExternalStorageState();
    if (!Environment.MEDIA_MOUNTED.equals(state)){
      Log.d(TAG, "SD card is not mounted.");
      return null;
    }

    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
      Environment.DIRECTORY_PICTURES), "CameraExample3");
    // This location works best if you want the created images to be shared
    // between applications and persist after your app has been uninstalled.

    // Create the storage directory if it does not exist
    if (! mediaStorageDir.exists()){
      if (! mediaStorageDir.mkdirs()){
        Log.d("CameraExample3", "failed to create directory");
        return null;
      }
    }

    // Create a media file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    File mediaFile;
    if (type == MEDIA_TYPE_IMAGE){
      mediaFile = new File(mediaStorageDir.getPath() + File.separator +
        "IMG_"+ timeStamp + ".jpg");
    } else if(type == MEDIA_TYPE_VIDEO) {
      mediaFile = new File(mediaStorageDir.getPath() + File.separator +
        "VID_"+ timeStamp + ".mp4");
    } else {
      return null;
    }

    return mediaFile;
  }
}
