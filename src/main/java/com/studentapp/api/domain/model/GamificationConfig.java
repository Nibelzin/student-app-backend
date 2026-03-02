package com.studentapp.api.domain.model;

public final class GamificationConfig {
    private GamificationConfig() {}

    /** XP awarded when an Activity is fully completed. */
    public static final int XP_ACTIVITY_COMPLETION = 50;

    /** XP awarded per minute of a completed FocusSession (minimum 1). */
    public static final int XP_FOCUS_PER_MINUTE = 1;

    /** Base XP threshold per level: threshold = XP_LEVEL_BASE * currentLevel. */
    public static final int XP_LEVEL_BASE = 100;
}
