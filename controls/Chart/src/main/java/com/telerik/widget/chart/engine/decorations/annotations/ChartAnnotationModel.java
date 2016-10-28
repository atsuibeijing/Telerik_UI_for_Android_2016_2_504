package com.telerik.widget.chart.engine.decorations.annotations;

import com.telerik.android.common.math.RadRect;
import com.telerik.widget.chart.engine.axes.AxisModel;
import com.telerik.widget.chart.engine.axes.common.AxisPlotInfo;
import com.telerik.widget.chart.engine.elementTree.ChartElement;
import com.telerik.widget.chart.engine.propertyStore.ValueExtractor;

/**
 * Base class for all classes that use annotations representing an
 * instance of the {@link ChartElement} class.
 */
public abstract class ChartAnnotationModel extends ChartElement {

    /**
     * Initializes a new instance of the {@link ChartAnnotationModel} class.
     */
    public ChartAnnotationModel() {
        this.trackPropertyChanged = true;
    }

    /**
     * Tries to assign the value generated by an axis to a passed plot info instance wrapped in a
     * value extractor.
     *
     * @param axis     axis instance used to generate the new plot info using the passed value.
     * @param value    instance holding the value for creating the new plot info.
     * @param plotInfo plot info instance wrapped in a {@link ValueExtractor} that will be assigned
     *                 with the newly created plot info
     * @return <code>true</code> if the plot info was successfully created and stored in the passed
     * plot info and <code>false</code> if the creation failed.
     * @see ValueExtractor
     */
    protected static boolean tryCreatePlotInfo(AxisModel axis, Object value, ValueExtractor<AxisPlotInfo> plotInfo) {
        if (axis == null || value == null || !axis.isUpdated()) {
            plotInfo.value = null;
            return false;
        }

        plotInfo.value = axis.createPlotInfo(value);

        return plotInfo.value != null;
    }

    /**
     * Updates the current core if not already updated.
     */
    protected void update() {
        if (!this.isUpdated()) {
            this.updateCore();
        }
    }

    /**
     * States whether the current annotation has been updated.
     *
     * @return <code>true</code> if the current annotation was updated and <code>false</code> otherwise.
     */
    public abstract boolean isUpdated();

    /**
     * Used to reset the update state.
     */
    public abstract void resetState();

    /**
     * Used to update the core plots.
     */
    protected abstract void updateCore();

    /**
     * Used to arrange the core corresponding to the annotation implementation type.
     *
     * @param rect rectangle holding the info values needed for the core arrange.
     * @return the arranged core.
     */
    protected abstract RadRect arrangeCore(final RadRect rect);

    @Override
    protected RadRect arrangeOverride(final RadRect rect) {
        RadRect finalRect = super.arrangeOverride(rect);

        this.update();

        if (this.isUpdated()) {
            finalRect = this.arrangeCore(rect);
        }

        return finalRect;
    }
}