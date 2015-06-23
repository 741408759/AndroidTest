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
		String path = "/sdcard/zsm/camera.jpg";   //��Ƭ�ı���·�����ļ���
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
        //����SurfaceHolder����
        holder = surfaceView.getHolder();
        //ע��ص�������
        holder.addCallback(this);
        //����SurfaceHolder������
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);       
   
    }

    class mClick implements OnClickListener
    {
 	   @Override
 	  public void onClick(View v) 
 	  {
 		   if(v == paizhaoBtn)
 			   /* ���� */
 		       mCamera.takePicture(null, null, new jpegCallback());
 		   else if(v == exitBtn)
 			   exit();  //�˳�
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
		/* �������������ȡ�������ķ��� */
		initCamera();   
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		 /* ����� */
	    mCamera = Camera.open();
	    try {
	    	 /* ����Ԥ�� */
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			System.out.println("Ԥ������");
		}
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		
	}

	/* ���������ȡ������ */
	private void initCamera()
	{
	    /* ����Camera.Parameters���� */
	    Camera.Parameters parameters = mCamera.getParameters();
	    /* ������Ƭ��ʽΪJPEG */
	    parameters.setPictureFormat(PixelFormat.JPEG);
	    /* ָ��preview����Ļ��С */
	    parameters.setPreviewSize(320, 240);
	    /* ����ͼƬ�ֱ��ʴ�С */
	    parameters.setPictureSize(320, 240);
	    /* ��Camera.Parameters������Camera */
	    mCamera.setParameters(parameters);
	     /* ��Ԥ�� */
	    mCamera.startPreview();
	 }
	
	//���漰��ʾ���յ�ͼ��
	class jpegCallback implements PictureCallback
	{
		@Override
		public void onPictureTaken(byte[] data, Camera camera) 
		{
			  /* onPictureTaken����ĵ�һ��������Ϊ��Ƭ��byte */
		       Bitmap bm = BitmapFactory.decodeByteArray
		                   (data, 0, data.length);
		       try
		       {
		         BufferedOutputStream bos = new BufferedOutputStream
		         (new FileOutputStream(path));
		         /* ����ѹ��ת������ */
		         bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		         /* ����flush()����������BufferStream */
		         bos.flush();
		         /* ����OutputStream */
		         bos.close();
		         /* ��ʾ���յ�ͼ�� */ 
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
 * �½���Ŀex06_10�󣬽������Ĳ������£�
 * 1����д�û��������
 * 2����д���س���
 * 3���޸������ļ�
 * 
 * */
