package com.android.touch.gestures;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;
import com.android.settingslib.widget.R;

public class ScreenOffLaunchGestureActivity extends CollapsingToolbarBaseActivity {

    static final String ACTION_KEY = "action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int action = 0;
        try{
            action = getIntent().getExtras().getInt(ACTION_KEY);
        }catch(Exception ignored){
        }
        final Intent intent = ActionUtils.getIntentByAction(this, action);
        if (intent == null){
            finish();
        }
        new Handler().post(() -> {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(ScreenOffLaunchGestureActivity.this, new KeyguardManager.KeyguardDismissCallback() {
                @Override
                public void onDismissSucceeded() {
                    ActionUtils.startActivitySafely(ScreenOffLaunchGestureActivity.this, intent);
                    finish();
                }

                @Override
                public void onDismissError() {
                    finish();
                }

                @Override
                public void onDismissCancelled() {
                    finish();
                }
            });
        });
    }
}
