package edu.department.employment.infosys.utility;

import edu.department.employment.infosys.model.GlobalDataInstance;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;

/**
 * @author dragonzhu
 *
 */
public class ImageUtil {

	private static ImageUtil instance;			// ����
	
	private ImageUtil()
	{
		
	}
	
	// ���ص���
	public static ImageUtil getInstance()
	{
		if (null==instance)
		{
			instance=new ImageUtil();
		}
		return instance;
	}
	
	public View getImageView(Context mcontext, int id)
	{
		ImageView imgview=new ImageView(mcontext);
		Bitmap bmp=BitmapFactory.decodeResource(mcontext.getResources(), id);
		// ���ͼƬ�Ŀ��
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		// ������Ҫ�Ĵ�С
		int newWidth = GlobalDataInstance.GetInstance().getScreenWidth();
		int newHeight = newWidth/16*6;
		// �������ű���
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ȡ����Ҫ���ŵ�matrix����
		Matrix matrix = new Matrix();

		matrix.postScale(scaleWidth, scaleHeight);

		// �õ��µ�ͼƬ
		Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
		imgview.setImageBitmap(newbm);
		imgview.setScaleType(ImageView.ScaleType.FIT_XY);			// ����ͼƬ�������� 
		
		bmp=null;
		
		return imgview;
	}
}
