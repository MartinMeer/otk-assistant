package org.martinmeer.repo;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MainReference(UUID rtt_id, int dev_code, String es, String ei) {
}
