package org.martinmeer.repo;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MainReference(UUID rtt_id, int dev_code, int es, int ei) {
}
