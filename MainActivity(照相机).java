package com.example.ex06_10;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;

public class MainActivity extends Activity 
    implements SurfaceHolder.Callback
{
	 Camera mCamera=null;
		SurfaceView surfaceView;
		SurfaceHolder holder;
		ImageView mImageView;
		Button paizhaoBtn, exitBtn;
		String path = "/sdcard/zsm/camera.jpg";   //照片的保存路径及文件名,为了以后查看方便
		
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   
        mImageView = (ImageView) findViewById(R.id.imageView1);
        paizhaoBtn = (Button)findViewById(R.id.button1);
        exitBtn = (Button)findViewById(R.id.button2);
        
        paizhaoBtn.setOnClickListener(new mClick());
        exitBtn.setOnClickListener(new mClick());
        surfaceView =(SurfaceView)findViewById(R.id.surfaceView1);
        //创建SurfaceHolder对象
        holder = surfaceView.getHolder();
        //注册回调监听器
        holder.addCallback(this);
        //设置SurfaceHolder的类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);       
   
    }

    class mClick implements OnClickListener
    {
 	   @Override
 	  public void onClick(View v) 
 	  {
 		   if(v == paizhaoBtn)
 			   /* 拍照 */
 		       mCamera.takePicture(null, null, new jpegCallback());
 		   else if(v == exitBtn)
 			   exit();  //退出
 	  }
    }
  
    void exit()
    {
 	  mCamera.release();
 	  mCamera = null;
    }
    
    
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		/* 调用设置照相机取景参数的方法 */
		initCamera();   
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		 /* 打开相机 */
	    mCamera = Camera.open();
	    try {
	    	 /* 设置预览 */
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			System.out.println("预览错误");
		}
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		
	}

	/* 设置照相机取景参数 */
	private void initCamera()
	{
	    /* 创建Camera.Parameters对象 */
	    Camera.Parameters parameters = mCamera.getParameters();
	    /* 设置相片格式为JPEG */
	    parameters.setPictureFormat(PixelFormat.JPEG);
	    /* 指定preview的屏幕大小 */
	    parameters.setPreviewSize(320, 240);
	    /* 设置图片分辨率大小 */
	    parameters.setPictureSize(320, 240);
	    /* 将Camera.Parameters设置予Camera */
	    mCamera.setParameters(parameters);
	     /* 打开预览 */
	    mCamera.startPreview();
	 }
	
	//保存及显示拍照的图像
	class jpegCallback implements PictureCallback
	{
		@Override
		public void onPictureTaken(byte[] data, Camera camera) 
		{
			  /* onPictureTaken传入的第一个参数即为相片的byte */
		       Bitmap bm = BitmapFactory.decodeByteArray
		                   (data, 0, data.length);
		       try
		       {
		         BufferedOutputStream bos = new BufferedOutputStream
		         (new FileOutputStream(path));
		         /* 采用压缩转档方法 */
		         bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		         /* 调用flush()方法，更新BufferStream */
		         bos.flush();
		         /* 结束OutputStream */
		         bos.close();
		         /* 显示拍照的图像 */ 
		          mImageView.setImageBitmap(bm);
		       }
		       catch (Exception e)
		       {
		         Log.e("err", e.getMessage());
		       }
		   }
		}

}

/**  
 * 新建项目ex06_10后，接下来的步骤如下：
 * 1、编写用户界面程序
 * 2、编写主控程序
 * 3、修改配置文件
 * 
 * */
