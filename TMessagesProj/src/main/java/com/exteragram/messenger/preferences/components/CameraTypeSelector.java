/*

 This is the source code of exteraGram for Android.

 We do not and cannot prevent the use of our code,
 but be respectful and credit the original author.

 Copyright @immat0x1, 2023

*/

package com.exteragram.messenger.preferences.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.exteragram.messenger.ExteraConfig;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.Easings;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberPicker;

public class CameraTypeSelector extends LinearLayout {
    String[] strings = new String[]{
            LocaleController.getString("Default", R.string.Default),
            "CameraX",
            LocaleController.getString("CameraTypeSystem", R.string.CameraTypeSystem),
    };
    int[] icons = new int[]{
            R.drawable.telegram_camera_icon,
            R.drawable.camerax_icon,
            R.drawable.android_camera_icon
    };
    int currentIcon = ExteraConfig.cameraType;
    private final NumberPicker numberPicker;
    private final FrameLayout preview;

    private final RectF rect = new RectF();
    private final Paint outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pickerDividersPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ValueAnimator animator;
    private float progress;

    public CameraTypeSelector(Context context) {
        super(context);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        pickerDividersPaint.setStyle(Paint.Style.STROKE);
        pickerDividersPaint.setStrokeCap(Paint.Cap.ROUND);
        pickerDividersPaint.setStrokeWidth(AndroidUtilities.dp(2));

        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 0x3F));

        preview = new FrameLayout(context) {
            @Override
            @SuppressLint("DrawAllocation")
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                int color = Theme.getColor(Theme.key_switchTrack);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);

                int left = getMeasuredWidth() / 2 - AndroidUtilities.dp(90);
                int right = getMeasuredWidth() / 2 + AndroidUtilities.dp(90);
                int top = AndroidUtilities.dp(18);
                int bottom = getMeasuredHeight() - top;

                outlinePaint.setStrokeWidth(Math.max(2, AndroidUtilities.dp(1)));

                float stroke = outlinePaint.getStrokeWidth() / 2;

                Rect rect1 = new Rect();
                int rad = AndroidUtilities.dp(25);
                ShapeDrawable phoneDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, 0, 0, 0, 0}, null, null));

                rect.set(left, top, right, bottom);
                rect.round(rect1);
                phoneDrawable.setBounds(rect1);
                phoneDrawable.getPaint().setColor(Color.argb(20, r, g, b));
                phoneDrawable.draw(canvas);

                rect.set(left + stroke, top + stroke, right - stroke, bottom - stroke);
                rect.round(rect1);
                phoneDrawable.setBounds(rect1);
                phoneDrawable.getPaint().set(outlinePaint);
                phoneDrawable.draw(canvas);

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{0x00, Theme.getColor(Theme.key_windowBackgroundWhite)}
                );
                gd.setCornerRadius(0f);
                gd.setBounds((int) (left - stroke), (int) (3 * getMeasuredHeight() / 4 - stroke), (int) (right + stroke), (int) (bottom + stroke));
                gd.draw(canvas);

                Drawable d = ContextCompat.getDrawable(context, icons[currentIcon]);
                int ICON_WIDTH = AndroidUtilities.dp(16 + 2 * progress);
                d.setBounds(getMeasuredWidth() / 2 - ICON_WIDTH, getMeasuredHeight() / 2, getMeasuredWidth() / 2 + ICON_WIDTH, getMeasuredHeight() / 2 + 2 * ICON_WIDTH);
                d.setColorFilter(new PorterDuffColorFilter(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), (int) (0x4F * progress)), PorterDuff.Mode.MULTIPLY));
                d.draw(canvas);

                gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gd.setColors(new int[]{0x00, Color.argb(30, r, g, b)});
                gd.setCornerRadius(AndroidUtilities.dp(25));

                Theme.dialogs_onlineCirclePaint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 0x3F));
                outlinePaint.setStrokeWidth(Math.max(3, AndroidUtilities.dp(1.5f)));
                for (int i = 0; i < 2; i++) {
                    int p = i == 1 ? AndroidUtilities.dp(35) : 0;
                    gd.setBounds(left + AndroidUtilities.dp(16), top + AndroidUtilities.dp(16) + p, left + AndroidUtilities.dp(44), top + AndroidUtilities.dp(44) + p);
                    gd.draw(canvas);
                    canvas.drawCircle(left + AndroidUtilities.dp(30), top + AndroidUtilities.dp(30) + p, AndroidUtilities.dp(14), outlinePaint);
                }
            }
        };
        preview.setWillNotDraw(false);
        addView(preview, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));

        numberPicker = new NumberPicker(context, 13) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                float y = AndroidUtilities.dp(31);
                pickerDividersPaint.setColor(Theme.getColor(Theme.key_radioBackgroundChecked));
                canvas.drawLine(AndroidUtilities.dp(2), y, getMeasuredWidth() - AndroidUtilities.dp(2), y, pickerDividersPaint);

                y = getMeasuredHeight() - AndroidUtilities.dp(31);
                canvas.drawLine(AndroidUtilities.dp(2), y, getMeasuredWidth() - AndroidUtilities.dp(2), y, pickerDividersPaint);
            }
        };

        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMinValue(0);
        numberPicker.setDrawDividers(false);
        numberPicker.setMaxValue(strings.length - 1);
        numberPicker.setFormatter(value -> strings[value]);
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            onSelectedCamera(newVal);
            updateIcon(true);
            picker.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        });
        int selectedButton = ExteraConfig.cameraType;
        numberPicker.setValue(selectedButton);
        addView(numberPicker, LayoutHelper.createFrame(132, 102, Gravity.RIGHT, 0, 33, 21, 33));
        updateIcon(false);
    }

    protected void onSelectedCamera(int cameraSelected) {
    }

    public void updateIcon(boolean animate) {
        if (animate) {
            animator = ValueAnimator.ofFloat(1f, 0f).setDuration(100);
            animator.setInterpolator(Easings.easeInOutQuad);
            animator.addUpdateListener(animation -> {
                progress = (Float) animation.getAnimatedValue();
                preview.invalidate();
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    currentIcon = ExteraConfig.cameraType;
                    animator.setFloatValues(0f, 1f);
                    animator.removeAllListeners();
                    animator.start();
                }
            });
            animator.start();
        } else {
            progress = 1f;
            preview.invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(168), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!ExteraConfig.disableDividers && numberPicker.getValue() == 1)
            canvas.drawLine(LocaleController.isRTL ? 0 : AndroidUtilities.dp(21), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.dp(21) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
    }
}
