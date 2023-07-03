package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CenteredXAxisRenderer extends XAxisRenderer {

    private float[] labelBuffer = new float[2];
    private boolean isDrawingLabel = false;
    private float labelWidth;

    public CenteredXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer, float labelWidth) {
        super(viewPortHandler, xAxis, transformer);
        this.labelWidth = labelWidth;
    }

    @Override
    public void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        if (isDrawingLabel) {
            // Function is already being executed, return to prevent infinite loop
            return;
        }
        isDrawingLabel = true;

        String label = formattedLabel;
        if (label == null) {
            return;
        }

        float width = mAxisLabelPaint.measureText(label);
        labelBuffer[0] = width / 2f + labelWidth / 2f;
        labelBuffer[1] = 0f;

        if (mTrans != null) {
            mTrans.pointValuesToPixel(labelBuffer);
        }

        float drawX = x - labelBuffer[0];
        float drawY = y + labelBuffer[1];

        drawLabel(c, label, drawX, drawY, anchor, angleDegrees);

        isDrawingLabel = false;
    }
}
