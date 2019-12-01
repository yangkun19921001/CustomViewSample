package com.devyk.custom_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log

/**
 * <pre>
 *     author  : devyk on 2019-12-01 13:58
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is BitmapUtis
 * </pre>
 */
public class BitmapUtis
{

    companion object{
        public fun  changeBitmapSize(context: Context,res: Int,newWidth : Int = 500,newHeight:Int = 500) : Bitmap {
            var bitmap = BitmapFactory.decodeResource(context.getResources(), res);
            var width = bitmap.getWidth();
            var height = bitmap.getHeight();
            Log.e("width","width:"+width);
            Log.e("height","height:"+height);

            //计算压缩的比率
            var scaleWidth=(newWidth)/width .toFloat()
            var scaleHeight=(newHeight)/height.toFloat();

            //获取想要缩放的matrix
            var matrix =  Matrix();
            matrix.postScale(scaleWidth,scaleHeight.toFloat());

            //获取新的bitmap
            bitmap= Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
            bitmap.getWidth();
            bitmap.getHeight();
            Log.e("newWidth","newWidth"+bitmap.getWidth());
            Log.e("newHeight","newHeight"+bitmap.getHeight());
            return bitmap;
        }
    }


}