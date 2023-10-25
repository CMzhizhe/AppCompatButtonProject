package com.gxx.buttonlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;


/**
 * drawableRight与文本一起居中显示
 */
public class DrawableLeftCenterButton extends AppCompatButton {
    private int picDistance = 0;
    private int picWidth = 0;
    private int picHeight = 0;

    public DrawableLeftCenterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.DrawableLeftCenterButton);
        picDistance = ta.getDimensionPixelOffset(R.styleable.DrawableLeftCenterButton_dl_dis,0);
        picWidth = ta.getDimensionPixelOffset(R.styleable.DrawableLeftCenterButton_dl_width,0);
        picHeight = ta.getDimensionPixelOffset(R.styleable.DrawableLeftCenterButton_dl_height,0);
        ta.recycle();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas = getTopCanvas(canvas);
        super.onDraw(canvas);
    }

    private Canvas getTopCanvas(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return canvas;
        }
        Drawable drawable = drawables[0];// 左面的drawable
        if (drawable == null) {
            drawable = drawables[2];// 右面的drawable
        }

        if (drawable == null){
            return canvas;
        }

        boolean isNeedScan = true;

        if (picWidth == ((BitmapDrawable) drawable).getBitmap().getWidth()){
            //不缩放
            isNeedScan = false;
        }

        Bitmap oldBitmap = drawableToBitmap(drawable);
        Bitmap bitmap = null;
        if (isNeedScan){
            bitmap = createScaledBitmap(oldBitmap,picWidth,picHeight);
        }else {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }

        Drawable newDrawable =  new BitmapDrawable(null, bitmap);
        newDrawable.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());

        setCompoundDrawables(newDrawable,null,null,null);

        float textSize = getPaint().measureText(getText().toString());
        int drawWidth = bitmap.getWidth();
        float contentWidth = textSize + drawWidth + picDistance;
        int paddingRight = (int) (getWidth() - contentWidth);
        setPadding(0, 0, paddingRight, 0); // 直接贴到左边
        float dx = (getWidth() - contentWidth) / 2;
        canvas.translate(dx, 0);// 往右移动
        return canvas;
    }



    private Bitmap createScaledBitmap(Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, 0, 0);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return scaledBitmap;

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }





}
