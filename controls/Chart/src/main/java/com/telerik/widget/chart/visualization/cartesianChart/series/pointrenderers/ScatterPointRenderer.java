package com.telerik.widget.chart.visualization.cartesianChart.series.pointrenderers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.telerik.widget.chart.engine.dataPoints.DataPoint;
import com.telerik.widget.chart.visualization.cartesianChart.series.scatter.ScatterPointSeries;
import com.telerik.widget.palettes.PaletteEntry;

import java.util.HashMap;

/**
 * Renders the data points for {@link ScatterPointSeries}.
 */
public class ScatterPointRenderer extends ChartDataPointRendererBase<ScatterPointSeries> {
    private HashMap<DataPoint, PaletteEntry> pointColors = new HashMap<DataPoint, PaletteEntry>();

    /**
     * Creates a new instance of the ScatterPointRenderer class.
     *
     * @param owner The owner series.
     */
    public ScatterPointRenderer(ScatterPointSeries owner) {
        super(owner);
    }

    /**
     * Gets the palette entry map for the owner series' data points.
     */
    public HashMap<DataPoint, PaletteEntry> pointColors() {
        return this.pointColors;
    }

    @Override
    protected void renderPointCore(Canvas canvas, DataPoint point) {
        Paint fillPaint;
        Paint strokePaint;

        ScatterPointSeries series = this.getSeries();
        if (series.isPaletteApplied() && this.pointColors.containsKey(point)) {
            PaletteEntry entry = this.pointColors.get(point);
            fillPaint = new Paint();
            fillPaint.setAntiAlias(true);
            fillPaint.setColor(entry.getFill());

            strokePaint = new Paint();
            strokePaint.setAntiAlias(true);
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(entry.getStroke());
            strokePaint.setStrokeWidth(entry.getStrokeWidth());
        } else {
            strokePaint = series.getStrokePaint();
            fillPaint = series.getFillPaint();
        }

        float pointSize = series.getPointSize();

        canvas.drawCircle((float) point.getCenterX(), (float) point.getCenterY(), pointSize, fillPaint);
        canvas.drawCircle((float) point.getCenterX(), (float) point.getCenterY(), pointSize, strokePaint);
    }
}
