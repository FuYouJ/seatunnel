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

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.common.constants.PluginType;

import org.neo4j.driver.AuthTokens;

import lombok.Data;

import java.io.Serializable;
import java.net.URI;

import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_BEARER_TOKEN;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_DATABASE;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_KERBEROS_TICKET;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_MAX_CONNECTION_TIMEOUT;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_MAX_TRANSACTION_RETRY_TIME;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_NEO4J_URI;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_PASSWORD;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_QUERY;
import static org.apache.seatunnel.connectors.seatunnel.neo4j.config.Neo4jCommonConfig.KEY_USERNAME;

/**
 * Because Neo4jQueryInfo is one of the Neo4jSink's member variable, So Neo4jQueryInfo need
 * implements Serializable interface
 */
@Data
public abstract class Neo4jQueryInfo implements Serializable {
    protected DriverBuilder driverBuilder;
    protected String query;

    protected PluginType pluginType;

    public Neo4jQueryInfo(ReadonlyConfig config, PluginType pluginType) {
        this.pluginType = pluginType;
        this.driverBuilder = prepareDriver(config, pluginType);
        this.query = config.get(KEY_QUERY);
    }

    // which is identical to the prepareDriver methods of the source and sink.
    // the only difference is the pluginType mentioned in the error messages.
    // so move code to here
    protected DriverBuilder prepareDriver(ReadonlyConfig config, PluginType pluginType) {
        URI uri = URI.create(config.get(KEY_NEO4J_URI));
        DriverBuilder driverBuilder = DriverBuilder.create(uri);

        String database = config.get(KEY_DATABASE);
        driverBuilder.setDatabase(database);

        String username = config.get(KEY_USERNAME);
        String password = config.get(KEY_PASSWORD);
        if (username != null && password != null) {
            driverBuilder.setUsername(username);
            driverBuilder.setPassword(password);
        }

        String bearerToken = config.get(KEY_BEARER_TOKEN);
        if (bearerToken != null) {
            AuthTokens.bearer(bearerToken);
            driverBuilder.setBearerToken(bearerToken);
        }

        String kerberosTicket = config.get(KEY_KERBEROS_TICKET);
        if (kerberosTicket != null) {
            AuthTokens.kerberos(kerberosTicket);
            driverBuilder.setBearerToken(kerberosTicket);
        }

        Long maxConnTimeoutSeconds = config.get(KEY_MAX_CONNECTION_TIMEOUT);
        driverBuilder.setMaxConnectionTimeoutSeconds(maxConnTimeoutSeconds);

        Long maxTxRetryTimeSeconds = config.get(KEY_MAX_TRANSACTION_RETRY_TIME);
        driverBuilder.setMaxTransactionRetryTimeSeconds(maxTxRetryTimeSeconds);

        return driverBuilder;
    }
}
