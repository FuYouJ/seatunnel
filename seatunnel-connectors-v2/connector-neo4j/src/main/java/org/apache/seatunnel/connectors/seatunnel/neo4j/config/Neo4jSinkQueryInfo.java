/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.connectors.seatunnel.neo4j.config;

import org.apache.seatunnel.api.common.SeaTunnelAPIErrorCode;
import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.common.constants.PluginType;
import org.apache.seatunnel.connectors.seatunnel.neo4j.constants.SinkWriteMode;
import org.apache.seatunnel.connectors.seatunnel.neo4j.exception.Neo4jConnectorException;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jSinkConfig.MAX_BATCH_SIZE;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jSinkConfig.QUERY_PARAM_POSITION;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jSinkConfig.WRITE_MODE;

@Getter
@Setter
public class Neo4jSinkQueryInfo extends Neo4jQueryInfo {

    private Map<String, String> queryParamPosition;
    private Integer maxBatchSize;

    private SinkWriteMode writeMode;

    public boolean batchMode() {
        return SinkWriteMode.BATCH.equals(writeMode);
    }

    public Neo4jSinkQueryInfo(ReadonlyConfig config) {
        super(config, PluginType.SINK);

        this.writeMode = config.get(WRITE_MODE);

        if (SinkWriteMode.BATCH.equals(writeMode)) {
            prepareBatchWriteConfig(config);
        } else {
            prepareOneByOneConfig(config);
        }
    }

    private void prepareOneByOneConfig(ReadonlyConfig config) {
        // set queryParamPosition
        this.queryParamPosition =
                config.getOptional(QUERY_PARAM_POSITION)
                        .orElseThrow(
                                () ->
                                        new Neo4jConnectorException(
                                                SeaTunnelAPIErrorCode.CONFIG_VALIDATION_FAILED,
                                                "queryParamPosition must be configured in ONE BY ONE mode"));
    }

    private void prepareBatchWriteConfig(ReadonlyConfig config) {

        // batch size
        this.maxBatchSize = config.get(MAX_BATCH_SIZE);
    }
}
