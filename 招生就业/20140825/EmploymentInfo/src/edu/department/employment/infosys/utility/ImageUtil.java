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
		// 获得图片的宽高
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		// 设置想要的大小
		int newWidth = GlobalDataInstance.GetInstance().getScreenWidth();
		int newHeight = newWidth/16*9;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();

		matrix.postScale(scaleWidth, scaleHeight);

		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
		imgview.setImageBitmap(newbm);
		imgview.setScaleType(ImageView.ScaleType.FIT_XY);			// 设置图片缩放类型 
		
		return imgview;
	}
}
