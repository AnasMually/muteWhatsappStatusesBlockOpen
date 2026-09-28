# muteWhatsappStatusesBlockOpen

Small Android app that uses an `AccessibilityService` to immediately leave one specific WhatsApp screen:

`com.whatsapp.status.updates.ui.statusmuting.MutedStatusesActivity`

When that Activity becomes the active window, the service calls Android's global **Back** action immediately. There is no intentional delay.

## Privacy / scope

- Accessibility events are restricted to package `com.whatsapp`.
- Only `TYPE_WINDOW_STATE_CHANGED` is requested.
- `canRetrieveWindowContent` is disabled.
- No screen text or WhatsApp content is read or stored.

## Run on Android / AndroidIDE

1. Clone/import this repository in AndroidIDE.
2. Sync the Gradle project.
3. Build and install the `app` module.
4. Open the app and tap **Open Accessibility settings**.
5. Enable **Block WhatsApp muted statuses screen**.
6. In WhatsApp, try to open the muted statuses screen. It should immediately return to the previous screen.

## Target

- WhatsApp package: `com.whatsapp`
- Blocked Activity: `com.whatsapp.status.updates.ui.statusmuting.MutedStatusesActivity`
