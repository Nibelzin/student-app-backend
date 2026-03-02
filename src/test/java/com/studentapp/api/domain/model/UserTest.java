package com.studentapp.api.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User buildUser(int currentXp, int currentLevel) {
        return User.fromState(
                UUID.randomUUID(), "Test", "test@example.com", "hash",
                null, null, currentXp, currentLevel, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
    }

    @Test
    void awardXp_doesNotLevelUp_whenBelowThreshold() {
        User user = buildUser(0, 1);

        boolean leveledUp = user.awardXp(50);

        assertThat(leveledUp).isFalse();
        assertThat(user.getCurrentXp()).isEqualTo(50);
        assertThat(user.getCurrentLevel()).isEqualTo(1);
    }

    @Test
    void awardXp_levelsUp_whenXpReachesThreshold() {
        // Level 1 threshold: 100 * 1 = 100
        User user = buildUser(0, 1);

        boolean leveledUp = user.awardXp(100);

        assertThat(leveledUp).isTrue();
        assertThat(user.getCurrentLevel()).isEqualTo(2);
        assertThat(user.getCurrentXp()).isEqualTo(0);
    }

    @Test
    void awardXp_preservesOverflowXp_afterLevelUp() {
        // Level 1 threshold: 100; award 150 → level up with 50 overflow
        User user = buildUser(0, 1);

        boolean leveledUp = user.awardXp(150);

        assertThat(leveledUp).isTrue();
        assertThat(user.getCurrentLevel()).isEqualTo(2);
        assertThat(user.getCurrentXp()).isEqualTo(50);
    }

    @Test
    void awardXp_accumulatesXpAcrossMultipleCalls() {
        User user = buildUser(0, 1);

        user.awardXp(30);
        user.awardXp(30);
        boolean leveledUp = user.awardXp(40);

        assertThat(leveledUp).isTrue();
        assertThat(user.getCurrentLevel()).isEqualTo(2);
        assertThat(user.getCurrentXp()).isEqualTo(0);
    }

    @Test
    void awardXp_usesProgressiveThreshold() {
        // Level 2 threshold: 100 * 2 = 200
        User user = buildUser(0, 2);

        boolean leveledUp = user.awardXp(150);

        assertThat(leveledUp).isFalse();
        assertThat(user.getCurrentLevel()).isEqualTo(2);
        assertThat(user.getCurrentXp()).isEqualTo(150);
    }

    @Test
    void awardXp_levelsUpAtLevel2Threshold() {
        User user = buildUser(0, 2);

        boolean leveledUp = user.awardXp(200);

        assertThat(leveledUp).isTrue();
        assertThat(user.getCurrentLevel()).isEqualTo(3);
        assertThat(user.getCurrentXp()).isEqualTo(0);
    }
}
