package com.fm.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.fm.R;
import com.fm.qtcode.encoding.EncodingHandler;
import com.fm.util.App;
import com.fm.util.Client;
import com.fm.util.SwitchButton;


/**显示二维码的窗口*/
public class QTCodeActivity extends Activity
{
	//控件
	private Button			btnBack;
	private ImageView		img;
		
		
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtcode);   
        
        //初始化控件
        btnBack = (Button) findViewById(R.id.qtcode_btnBack);
        img = (ImageView) findViewById(R.id.qtcode_img);
        
        
        btnBack.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		finish();
        	}
        }); 
        
        
        showQRCode();
        
        
      
    }
	
	
	private void showQRCode()
	{
		try
		{
			final Resources r = getResources();
			
			String contentString = "ip<" + Client.getServerIp() + ">port<" 
					+ Client.getServerPort() + ">";
	        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（600*600）
			Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 600);
			
			//------------------添加logo部分------------------//
			Bitmap logoBmp = BitmapFactory.decodeResource(r, R.drawable.icon);
			
			//二维码和logo合并
			Bitmap bitmap = Bitmap.createBitmap(qrCodeBitmap.getWidth(), qrCodeBitmap
	                .getHeight(), qrCodeBitmap.getConfig());
	        Canvas canvas = new Canvas(bitmap);
	        //二维码
	        canvas.drawBitmap(qrCodeBitmap, 0,0, null);
	        //logo绘制在二维码中央
			canvas.drawBitmap(logoBmp, qrCodeBitmap.getWidth() / 2
					- logoBmp.getWidth() / 2, qrCodeBitmap.getHeight()
					/ 2 - logoBmp.getHeight() / 2, null);
			//------------------添加logo部分------------------//
			
			img.setImageBitmap(bitmap);
		}
		catch (Exception e)
		{
			App.log(e);
		}
	}

}
