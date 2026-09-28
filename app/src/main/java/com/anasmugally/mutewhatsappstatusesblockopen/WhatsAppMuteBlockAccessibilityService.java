package com.anasmugally.mutewhatsappstatusesblockopen;

import android.accessibilityservice.AccessibilityService;
import android.os.SystemClock;
import android.view.accessibility.AccessibilityEvent;

/**
 * Blocks WhatsApp's "Muted statuses" screen by immediately issuing the global
 * Back action as soon as the target Activity becomes the active window.
 */
public final class WhatsAppMuteBlockAccessibilityService extends AccessibilityService {

    private static final String WHATSAPP_PACKAGE = "com.whatsapp";
    private static final String BLOCKED_ACTIVITY =
            "com.whatsapp.status.updates.ui.statusmuting.MutedStatusesActivity";

    // TYPE_WINDOW_STATE_CHANGED can occasionally be emitted more than once for
    // the same transition. This suppresses only near-simultaneous duplicates;
    // the first matching event is handled immediately with no intentional delay.
    private static final long DUPLICATE_EVENT_WINDOW_MS = 150L;
    private long lastBackAtMs = Long.MIN_VALUE;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null || event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return;
        }

        CharSequence packageName = event.getPackageName();
        CharSequence className = event.getClassName();

        if (packageName == null || className == null) {
            return;
        }

        if (!WHATSAPP_PACKAGE.contentEquals(packageName)) {
            return;
        }

        if (!BLOCKED_ACTIVITY.contentEquals(className)) {
            return;
        }

        long now = SystemClock.elapsedRealtime();
        if (lastBackAtMs != Long.MIN_VALUE && now - lastBackAtMs < DUPLICATE_EVENT_WINDOW_MS) {
            return;
        }

        // Execute Back immediately. No Handler/postDelayed/sleep is used.
        if (performGlobalAction(GLOBAL_ACTION_BACK)) {
            lastBackAtMs = now;
        }
    }

    @Override
    public void onInterrupt() {
        // No state to recover.
    }
}
