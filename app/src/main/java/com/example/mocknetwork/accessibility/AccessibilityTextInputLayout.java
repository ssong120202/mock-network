package com.example.mocknetwork.accessibility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputLayout;

public class AccessibilityTextInputLayout extends TextInputLayout {

    public AccessibilityTextInputLayout(Context context) {
        super(context);
    }

    public AccessibilityTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccessibilityTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This function sets the content description for the layout's helper text TextView or error text TextView.
     *
     * @param contentDescription The content description string to be set.
     * @param isForHelperText    Boolean value to determine whether the helper or error text content description should be set.
     */
    public void setHelperOrErrorTextContentDescription(String contentDescription, boolean isForHelperText) {
        int textViewIndex;
        if (!isForHelperText && isHelperTextEnabled()) {
            textViewIndex = 1;
        } else {
            textViewIndex = 0;
        }
        FrameLayout frameLayout = (FrameLayout) ((LinearLayout) getChildAt(1)).getChildAt(0);
        TextView textView = ((AppCompatTextView) frameLayout.getChildAt(textViewIndex));
        textView.setContentDescription(contentDescription);
    }
}