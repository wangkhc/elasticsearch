/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.xpack.transform.transforms;

import org.elasticsearch.client.Client;
import org.elasticsearch.xpack.core.indexing.IndexerState;
import org.elasticsearch.xpack.core.transform.transforms.TransformIndexerPosition;
import org.elasticsearch.xpack.core.transform.transforms.TransformIndexerStats;
import org.elasticsearch.xpack.core.transform.transforms.TransformCheckpoint;
import org.elasticsearch.xpack.core.transform.transforms.TransformConfig;
import org.elasticsearch.xpack.core.transform.transforms.TransformProgress;
import org.elasticsearch.xpack.transform.checkpoint.CheckpointProvider;
import org.elasticsearch.xpack.transform.checkpoint.TransformCheckpointService;
import org.elasticsearch.xpack.transform.notifications.TransformAuditor;
import org.elasticsearch.xpack.transform.persistence.TransformConfigManager;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

class ClientTransformIndexerBuilder {
    private Client client;
    private TransformConfigManager transformsConfigManager;
    private TransformCheckpointService transformsCheckpointService;
    private TransformAuditor auditor;
    private Map<String, String> fieldMappings;
    private TransformConfig transformConfig;
    private TransformIndexerStats initialStats;
    private IndexerState indexerState = IndexerState.STOPPED;
    private TransformIndexerPosition initialPosition;
    private TransformProgress progress;
    private TransformCheckpoint lastCheckpoint;
    private TransformCheckpoint nextCheckpoint;

    ClientTransformIndexerBuilder() {
        this.initialStats = new TransformIndexerStats();
    }

    ClientTransformIndexer build(TransformTask parentTask) {
        CheckpointProvider checkpointProvider = transformsCheckpointService.getCheckpointProvider(transformConfig);

        return new ClientTransformIndexer(this.transformsConfigManager,
            checkpointProvider,
            new AtomicReference<>(this.indexerState),
            this.initialPosition,
            this.client,
            this.auditor,
            this.initialStats,
            this.transformConfig,
            this.fieldMappings,
            this.progress,
            this.lastCheckpoint,
            this.nextCheckpoint,
            parentTask);
    }

    ClientTransformIndexerBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    ClientTransformIndexerBuilder setTransformsConfigManager(TransformConfigManager transformsConfigManager) {
        this.transformsConfigManager = transformsConfigManager;
        return this;
    }

    ClientTransformIndexerBuilder setTransformsCheckpointService(TransformCheckpointService transformsCheckpointService) {
        this.transformsCheckpointService = transformsCheckpointService;
        return this;
    }

    ClientTransformIndexerBuilder setAuditor(TransformAuditor auditor) {
        this.auditor = auditor;
        return this;
    }

    ClientTransformIndexerBuilder setFieldMappings(Map<String, String> fieldMappings) {
        this.fieldMappings = fieldMappings;
        return this;
    }

    ClientTransformIndexerBuilder setTransformConfig(TransformConfig transformConfig) {
        this.transformConfig = transformConfig;
        return this;
    }

    TransformConfig getTransformConfig() {
        return this.transformConfig;
    }

    ClientTransformIndexerBuilder setInitialStats(TransformIndexerStats initialStats) {
        this.initialStats = initialStats;
        return this;
    }

    ClientTransformIndexerBuilder setIndexerState(IndexerState indexerState) {
        this.indexerState = indexerState;
        return this;
    }

    ClientTransformIndexerBuilder setInitialPosition(TransformIndexerPosition initialPosition) {
        this.initialPosition = initialPosition;
        return this;
    }

    ClientTransformIndexerBuilder setProgress(TransformProgress progress) {
        this.progress = progress;
        return this;
    }

    ClientTransformIndexerBuilder setLastCheckpoint(TransformCheckpoint lastCheckpoint) {
        this.lastCheckpoint = lastCheckpoint;
        return this;
    }

    ClientTransformIndexerBuilder setNextCheckpoint(TransformCheckpoint nextCheckpoint) {
        this.nextCheckpoint = nextCheckpoint;
        return this;
    }
}