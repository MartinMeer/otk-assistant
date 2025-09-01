package org.martinmeer.repo;

import lombok.Builder;
import lombok.ToString;

import java.util.UUID;

@Builder
public record RangeByToleranceByType(UUID rtt_id, String el_type, String bas_tol, String range) {
}
