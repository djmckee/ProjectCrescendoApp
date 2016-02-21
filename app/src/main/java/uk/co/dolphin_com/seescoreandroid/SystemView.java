/**
 * SeeScore For Android Sample App
 * Dolphin Computing http://www.dolphin-com.co.uk
 */
package uk.co.dolphin_com.seescoreandroid;

import uk.co.dolphin_com.sscore.BarGroup;
import uk.co.dolphin_com.sscore.Component;
import uk.co.dolphin_com.sscore.CursorRect;
import uk.co.dolphin_com.sscore.Item;
import uk.co.dolphin_com.sscore.PartName;
import uk.co.dolphin_com.sscore.Point;
import uk.co.dolphin_com.sscore.RenderItem;
import uk.co.dolphin_com.sscore.RenderItem.Colour;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.SSystem;
import uk.co.dolphin_com.sscore.TimedItem;
import uk.co.dolphin_com.sscore.ex.ScoreException;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

/**
 * The SystemView is a {@link View} which displays a single {@link SSystem}.
 * <p>{@link SeeScoreView} manages layout and placement of these into a scrolling View to display the complete {@link SScore}
 */
public class SystemView extends View {

    /**
     * type of cursor
     */
    static enum CursorType
    {
        /** no cursor */
        none,

        /** vertical line cursor */
        line,

        /** rectangular cursor around bar */
        box
    }

	/**
	 * construct the SystemView
	 * 
	 * @param context the Context
	 * @param score the score
	 * @param sys the system
	 * @param am the AssetManager for fonts
	 */
	public SystemView(Context context, SScore score, SSystem sys, AssetManager am, SeeScoreView.TapNotification tn)
	{
		super(context);
		this.assetManager = am;
		this.system = sys;
		this.score = score;
        this.tapNotify = tn;
		backgroundPaint = new Paint();
		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setColor(0xFFFFFFFA);
		backgroundPaintRect = new RectF();
		tappedItemPaint = new Paint();
		tappedItemPaint.setStyle(Paint.Style.STROKE);
		tappedItemPaint.setColor(0xFF000080); // red
		barRectPaint = new Paint();
		barRectPaint.setStyle(Paint.Style.STROKE);
		barRectPaint.setStrokeWidth(3);
		barRectPaint.setColor(0xFF0000FF); // blue
		this.tl = new Point(0,0);// topleft
		this.drawItemRect = false;
		zoomingMag = 1;
		viewRect = new Rect();
	}

    /**
     * return the fractional position of the bar in the system for a pseudo smooth scroll
     * @param barIndex the 0-based bar index
     * @return the fractional position of the bar in the system (0 for leftmost, <1 for others)
     */
    public float fractionalScroll(int barIndex) {
        SSystem.BarRange br = system.getBarRange();
        return (float)(barIndex - br.startBarIndex) / br.numBars;
    }

    /**
     *
     * @param barIndex the 0-based bar index
     * @return true if the bar is in this system
     */
    public boolean containsBar(int barIndex)
    {
        return system.containsBar(barIndex);
    }

    /**
     * set the cursor at a given bar
     * @param barIndex the 0-based bar index
     * @param type the type of cursor (line or box)
     * @return true if the bar is in this system
     */
    public boolean setCursorAtBar(int barIndex, CursorType type)
    {
        boolean rval = system.containsBar(barIndex);
        if (rval) {
            cursorBarIndex = barIndex;
            cursorType = type;
            cursor_xpos = 0; // line cursor is drawn at left of bar
            invalidate();
        } else if (cursorType != CursorType.none) {
            cursorType = CursorType.none;
            cursor_xpos = 0;
            invalidate();
        }
        return rval;
    }

    /**
     * set the cursor at a given bar with a given x position in the system
     * @param barIndex the 0-based bar index
     * @param xpos the x coordinate from the left of the system
     * @return true if the bar is in this system
     */
    public boolean setCursorAtBar(int barIndex, float xpos)
    {
        boolean rval = system.containsBar(barIndex);
        if (rval) {
            cursorBarIndex = barIndex;
            cursor_xpos = xpos;
            cursorType = CursorType.line;
            invalidate();
        } else if (cursorType != CursorType.none) {
            cursorType = CursorType.none;
            invalidate();
        }
        return rval;
    }

	/** request a special colouring for a particular item in this System
	 * 
	 * @param item_h the unique identifier for the item
	 */
	public void colourItem(int item_h)
	{
		if (item_h != 0)
		{
			renderItems = new RenderItem[1];
			int[] coloured_render = new int[1];
			coloured_render[0] = RenderItem.ColourRenderFlags_notehead;
			renderItems[0] = new RenderItem(item_h, new Colour(1,0,0,1), coloured_render);
		}
		else
		{
			renderItems = null;
		}
		invalidate();		
	}

    /**
     * called by android to measure this view
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		float w = system.bounds().width;
		float h = system.bounds().height;
		setMeasuredDimension((int)w,(int)h);
	}
	
	/**
	 * called by android to draw the View
     * @param canvas the canvas
     */
	protected void onDraw(Canvas canvas)
	{
		backgroundPaintRect.left = 0;
		backgroundPaintRect.top = 0;
		backgroundPaintRect.right = canvas.getWidth();
		backgroundPaintRect.bottom = getHeight();
		canvas.drawRect(backgroundPaintRect, backgroundPaint);
		if (system != null)
		{
			if (renderItems != null)
			{
				// render notehead opaque red
				try {
					system.drawWithOptions(canvas, assetManager, tl, zoomingMag, renderItems);
				} catch (ScoreException e) {
					System.out.println(" error on draw:" + e);
				}
			}
			else
				system.draw(canvas, assetManager, tl, zoomingMag);
		}
		if (drawItemRect)
		{
			canvas.drawRect(tappedItemRect, tappedItemPaint);
		}
		if (cursorType != CursorType.none && cursorBarIndex >= 0)
		{
			CursorRect cr = system.getCursorRect(canvas, cursorBarIndex);
			if (cr.barInSystem)
			{
                if (cursorType == CursorType.box)
				    canvas.drawRect(cr.rect, barRectPaint);
                else if (cursorType == CursorType.line) {
                    if (cursor_xpos > 0)
                        canvas.drawLine(cursor_xpos, cr.rect.top, cursor_xpos, cr.rect.top + cr.rect.height(), barRectPaint);
                    else
                        canvas.drawLine(cr.rect.left, cr.rect.top, cr.rect.left, cr.rect.top + cr.rect.height(), barRectPaint);
                }
			}
		}
	}

	/**
	 * called during active pinch-zooming. We just draw the same system magnified
	 * @param zoom the magnification
	 */
	void zooming(final float zoom)
	{
		zoomingMag = zoom;
		if (!isZooming)
		{
			viewRect.top = getTop();
			viewRect.bottom = getBottom();
			isZooming = true;
		}
		setTop((int) (viewRect.top * zoom));
		setBottom((int) (viewRect.bottom * zoom));
		invalidate();
	}
	
	/**
	 * send touch events to the tap handler
     * NOTE: We really need to filter out the pinch zoom events and scroll events so these aren't seen as taps
	 */
	public boolean onTouchEvent (MotionEvent event)
	{
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
		{
			Point p = new Point(event.getX(), event.getY());
			int partIndex = system.getPartIndexForYPos(event.getY());
			int barIndex = system.getBarIndexForXPos(event.getX());
			Component[] components;
			try {
				components = system.hitTest(p);
                tapNotify.tap(system.index(), partIndex, barIndex, components);

  			} catch (ScoreException e) {
				java.lang.System.out.println(" exception:" + e.toString());
			}
		}
		return false;
	}

	private SScore score;
	private SSystem system;
	private AssetManager assetManager;
	private Point tl;
	private Paint backgroundPaint;
	private RectF backgroundPaintRect;
	private boolean drawItemRect;
	private RectF tappedItemRect;
	private Paint tappedItemPaint;
	private Paint barRectPaint;
	private float zoomingMag;
	private RenderItem[] renderItems;
	private Rect viewRect;
	private boolean isZooming = false;
	private int cursorBarIndex;
    private CursorType cursorType = CursorType.none;
    private float cursor_xpos = 0;
    private SeeScoreView.TapNotification tapNotify;
}
