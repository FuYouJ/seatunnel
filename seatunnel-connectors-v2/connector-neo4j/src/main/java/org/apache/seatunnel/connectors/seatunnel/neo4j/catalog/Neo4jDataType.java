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

package org.apache.seatunnel.connectors.seatunnel.neo4j.catalog;

import lombok.Getter;

import java.util.Map;

/**
 * reference https://neo4j.com/docs/cypher-manual/current/values-and-types/property-structural-constructed/
 */
@Getter
public class Neo4jDataType {
    public static final String BOOELAN = "BOOLEAN";

    public static final String DATE = "DATE";

    @Deprecated
    public static final String DURATION = "DURATION";
    public static final String FLOAT = "FLOAT";
    public static final String INTEGER = "INTEGER";

    public static final String LIST = "LIST";
    public static final String LOCAL_DATETIME = "LOCAL_DATETIME";

    public static final String LOCAL_TIME = "LOCAL_TIME";

    public static final String POINT = "POINT";

    public static final String STRING = "STRING";

    public static final String ZONED_DATETIME = "ZONED_DATETIME";

    public static final String ZONED_TIME = "ZONED_TIME";

    public static final String MAP = "MAP";

    private String type;
    private Map<String, Object> options;

    public Neo4jDataType(String type,Map<String, Object> options){
     this.type = type;
     this.options = options;
    }
}
