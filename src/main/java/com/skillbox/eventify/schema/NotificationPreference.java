package com.skillbox.eventify.schema;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class NotificationPreference {
    @Id
    @Column(name = "user_id")
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "notify_new_events", nullable = false)
    @ToString.Include
    private Boolean notifyNewEvents = false;

    @Column(name = "notify_upcoming", nullable = false)
    @ToString.Include
    private Boolean notifyUpcoming = true;

    @Column(name = "notify_before_hours", nullable = false)
    @ToString.Include
    private Integer notifyBeforeHours = 24;
}
