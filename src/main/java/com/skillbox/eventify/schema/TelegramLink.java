package com.skillbox.eventify.schema;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
@Table(name = "telegram_links")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String verificationCode;

    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    private TelegramLinkStatus status;
}