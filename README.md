# CS360 Weight Tracker
CS360 Project Three, Weight Tracker App
Developer: Rex Green

This Weight Tracker mobile app was developed to help users monitor their weight progress over time and stay on track with their fitness or health goals. The primary requirement was to offer a simple, user friendly experience where users could record their daily weight, set a target weight, and view their progress in a clear, visual format. The app addresses the common user need of keeping consistent, private, and accessible health records without requiring third-party integrations or subscriptions.

The app includes a login screen for user authentication, a main dashboard, a screen to set the goal weight, and another screen to add today's weight, and a screen to view the user's weight history. These screens are designed to minimize clutter and use intuitive UI components.

The development was broken down into manageable phases, beginning with UI layout using XML. The core functionality was later implemented in Java. Reusable classes such as DatabaseHelper and WeightDatabaseHelper were used to improve maintainability. Using a modular structure also increases the ease of adding new features. 

Testing was carried out using an Android emulator to test various scenarios in order to identify logic flaws and ensure the app does not crash when encoutering a previously unexpected scenario. One notable challenge was reconciling different database helpers and ensuring data persisted consistently across screens.
