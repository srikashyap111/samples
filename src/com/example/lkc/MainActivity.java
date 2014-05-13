package com.example.lkc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	PaintView paintview;
	public static String name;
	public static String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		paintview = new PaintView(this);
		setContentView(paintview);
		paintview.requestFocus();
	}

	public class PaintView extends View implements OnTouchListener {
		public static final String TAG = "PaintView";
		List<Point> points = new ArrayList<Point>();
		Paint paint = new Paint();

		public PaintView(Context context) {
			super(context);
			setFocusable(true);
			setFocusableInTouchMode(true);
			this.setOnTouchListener(this);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			for (Point point : points) {
				canvas.drawCircle(point.x, point.y, 2, paint);
			}
			super.onDraw(canvas);
		}

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			Point point = new Point();
			point.x = event.getX();
			point.y = event.getY();
			points.add(point);
			invalidate();
			Log.d(TAG, "point" + point);
			return true;
		}

	}

	class Point {
		float x, y;

		public String toString() {
			return x + ", " + y;
		}
	}
}
