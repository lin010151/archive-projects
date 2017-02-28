package edu.department.employment.infosys.utility;

import edu.department.employment.infosys.data.GlobalDataInstance;
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

	public View getImageView(Context mcontext, int id)
	{
		ImageView imgview=new ImageView(mcontext);
		Bitmap bmp=BitmapFactory.decodeResource(mcontext.getResources(), id);
		// ���ͼƬ�Ŀ��
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		// ������Ҫ�Ĵ�С
		int newWidth = GlobalDataInstance.GetInstance().getScreenWidth();
		int newHeight = newWidth/16*9;
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
		
		return imgview;
	}
}
