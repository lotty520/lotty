package com.github.component.poke;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.github.component.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 扑克相叠视图
 *
 * @author lotty
 */
public class PokerView extends View {

  /**
   * 一行默认刚好能排列4张扑克
   */
  private final static int DEFAULT_COUNT = 4;
  /**
   * 扑克资源引用，用来随机发牌
   */
  private final static List<Integer> POKER_LIST = new ArrayList<>();

  static {
    POKER_LIST.add(R.drawable.p1);
    POKER_LIST.add(R.drawable.p2);
    POKER_LIST.add(R.drawable.p3);
    POKER_LIST.add(R.drawable.p4);
    POKER_LIST.add(R.drawable.p5);
    POKER_LIST.add(R.drawable.p6);
    POKER_LIST.add(R.drawable.p7);
    POKER_LIST.add(R.drawable.p8);
    POKER_LIST.add(R.drawable.p9);
    POKER_LIST.add(R.drawable.p10);
    POKER_LIST.add(R.drawable.p11);
    POKER_LIST.add(R.drawable.p12);
    POKER_LIST.add(R.drawable.p13);
    POKER_LIST.add(R.drawable.p14);
    POKER_LIST.add(R.drawable.p15);
    POKER_LIST.add(R.drawable.p16);
    POKER_LIST.add(R.drawable.p17);
    POKER_LIST.add(R.drawable.p18);
    POKER_LIST.add(R.drawable.p19);
    POKER_LIST.add(R.drawable.p20);
    POKER_LIST.add(R.drawable.p21);
    POKER_LIST.add(R.drawable.p22);
    POKER_LIST.add(R.drawable.p23);
    POKER_LIST.add(R.drawable.p24);
    POKER_LIST.add(R.drawable.p25);
    POKER_LIST.add(R.drawable.p26);
    POKER_LIST.add(R.drawable.p27);
    POKER_LIST.add(R.drawable.p28);
    POKER_LIST.add(R.drawable.p29);
    POKER_LIST.add(R.drawable.p30);
    POKER_LIST.add(R.drawable.p31);
    POKER_LIST.add(R.drawable.p32);
    POKER_LIST.add(R.drawable.p33);
    POKER_LIST.add(R.drawable.p34);
    POKER_LIST.add(R.drawable.p35);
    POKER_LIST.add(R.drawable.p36);
    POKER_LIST.add(R.drawable.p37);
    POKER_LIST.add(R.drawable.p38);
    POKER_LIST.add(R.drawable.p39);
    POKER_LIST.add(R.drawable.p40);
    POKER_LIST.add(R.drawable.p41);
    POKER_LIST.add(R.drawable.p42);
    POKER_LIST.add(R.drawable.p43);
    POKER_LIST.add(R.drawable.p44);
    POKER_LIST.add(R.drawable.p45);
    POKER_LIST.add(R.drawable.p46);
    POKER_LIST.add(R.drawable.p47);
    POKER_LIST.add(R.drawable.p48);
    POKER_LIST.add(R.drawable.p49);
    POKER_LIST.add(R.drawable.p50);
    POKER_LIST.add(R.drawable.p51);
    POKER_LIST.add(R.drawable.p52);
    POKER_LIST.add(R.drawable.p53);
    POKER_LIST.add(R.drawable.p54);
  }

  private int count = 8;
  private Paint mPaint;

  private Map<Integer, Bitmap> mCurBitmaps = new HashMap<>();

  public PokerView(Context context) {
    super(context);
    init();
  }

  public PokerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PokerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void deal(int count) {
    this.count = count;
    randomPoker();
    invalidate();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setStrokeWidth(0);
    mPaint.setStyle(Paint.Style.FILL);
    setBackgroundColor(Color.LTGRAY);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    randomPoker();
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int measuredWidth = getMeasuredWidth();
    int defaultPokerWidth = measuredWidth / DEFAULT_COUNT;
    int pokerHeight = getMeasuredHeight();


    //Rect leftRect = new Rect(0,0,300,300);
    //Rect rightRect = new Rect(150,150,450,450);
    //canvas.clipOutRect(leftRect);
    //canvas.clipRect(rightRect);
    //
    //mPaint.setColor(Color.GREEN);
    //canvas.drawRect(leftRect,mPaint);
    //
    //mPaint.setColor(Color.RED);
    //canvas.drawRect(rightRect,mPaint);




    //overlayDraw(canvas, pokerHeight, defaultPokerWidth);
    clipDraw(canvas, pokerHeight, defaultPokerWidth);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mCurBitmaps.clear();
  }

  /**
   * 过度绘制
   */
  private void overlayDraw(Canvas canvas, int pokerHeight, int defaultWidth) {
    int pokerWidth = defaultWidth * 3 / (count - 1);
    Iterator<Bitmap> iterator = mCurBitmaps.values().iterator();
    for (int i = 0; i < count; i++) {
      Rect rect = new Rect();
      rect.left = pokerWidth * i;
      rect.bottom = pokerHeight;
      rect.top = 0;
      rect.right = rect.left + defaultWidth;
      if (iterator.hasNext()) {
        canvas.drawBitmap(iterator.next(), null, rect, mPaint);
      }
    }
  }

  /**
   * 非过度绘制
   */
  private void clipDraw(Canvas canvas, int pokerHeight, int defaultWidth) {
    int pokerWidth = defaultWidth * 3 / (count - 1);
    Rect lastRect = null;
    Iterator<Bitmap> iterator = mCurBitmaps.values().iterator();
    for (int i = count - 1; i >= 0; i--) {
      canvas.save();
      Rect rect = new Rect();
      rect.left = pokerWidth * i;
      rect.bottom = pokerHeight;
      rect.top = 0;
      rect.right = rect.left + defaultWidth;
      if (lastRect != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          canvas.clipOutRect(lastRect);
        }else {
          // TODO: 2020/3/14
        }
      }
      canvas.clipRect(rect);
      if (iterator.hasNext()) {
        canvas.drawBitmap(iterator.next(), null, rect, mPaint);
      }
      lastRect = rect;
      canvas.restore();
    }
  }

  /**
   * 发牌，随机N张
   */
  private void randomPoker() {
    mCurBitmaps.clear();
    while (mCurBitmaps.size() < count) {
      int random = (int) (Math.random() * 53 + 1);
      int rp = POKER_LIST.get(random);
      if (!mCurBitmaps.containsKey(rp)) {
        mCurBitmaps.put(rp, BitmapFactory.decodeResource(getResources(), rp));
      }
    }
  }
}
