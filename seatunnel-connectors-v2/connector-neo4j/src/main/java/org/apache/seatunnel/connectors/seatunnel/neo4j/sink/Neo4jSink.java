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

package org.apache.seatunnel.connectors.seatunnel.neo4j.sink;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.sink.SeaTunnelSink;
import org.apache.seatunnel.api.sink.SinkWriter;
import org.apache.seatunnel.api.table.catalog.CatalogTableUtil;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.api.table.type.SeaTunnelRowType;
import org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jSinkQueryInfo;

import com.google.auto.service.AutoService;

import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jSinkConfig.PLUGIN_NAME;

@AutoService(SeaTunnelSink.class)
public class Neo4jSink implements SeaTunnelSink<SeaTunnelRow, Void, Void, Void> {

    private final SeaTunnelRowType rowType;
    private final Neo4jSinkQueryInfo neo4JSinkQueryInfo;

    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    public Neo4jSink(ReadonlyConfig config) {
        this.neo4JSinkQueryInfo = new Neo4jSinkQueryInfo(config);
        this.rowType = CatalogTableUtil.buildWithConfig(config).getSeaTunnelRowType();
    }

    @Override
    public SinkWriter<SeaTunnelRow, Void, Void> createWriter(SinkWriter.Context context) {
        return new Neo4jSinkWriter(neo4JSinkQueryInfo, rowType);
    }
}
