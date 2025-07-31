package com.skillbox.eventify.schema;

import java.time.Instant;
import java.time.ZoneId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.skillbox.eventify.schema.converter.ZoneIdConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @ToString.Include
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", updatable = false)
    @ToString.Include
    private Instant createdAt;

    @Column(name = "telegram_chat_id")
    @ToString.Include
    private Long telegramChatId;

    @Column(name = "timezone_id")
    @Convert(converter = ZoneIdConverter.class)
    @ToString.Include
    private ZoneId timezone;

    @OneToOne(fetch = FetchType.LAZY)
    private NotificationPreference notificationPreference;

    public enum Role {
        USER, ADMIN
    }
}

