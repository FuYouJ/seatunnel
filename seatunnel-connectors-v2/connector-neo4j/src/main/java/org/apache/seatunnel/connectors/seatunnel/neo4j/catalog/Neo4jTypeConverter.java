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


import com.google.auto.service.AutoService;
import org.apache.seatunnel.api.table.catalog.Column;
import org.apache.seatunnel.api.table.catalog.PhysicalColumn;
import org.apache.seatunnel.api.table.converter.BasicTypeConverter;
import org.apache.seatunnel.api.table.converter.BasicTypeDefine;
import org.apache.seatunnel.api.table.converter.TypeConverter;
import org.apache.seatunnel.api.table.type.*;

import java.util.Map;

@AutoService(TypeConverter.class)
public class Neo4jTypeConverter implements BasicTypeConverter<BasicTypeDefine<Neo4jDataType>> {
    @Override
    public String identifier() {
        return "Neo4j";
    }

    @Override
    public Column convert(BasicTypeDefine<Neo4jDataType> typeDefine) {
        PhysicalColumn.PhysicalColumnBuilder builder =
                PhysicalColumn.builder()
                        .name(typeDefine.getName())
                        .sourceType(typeDefine.getColumnType())
                        .nullable(typeDefine.isNullable())
                        .defaultValue(typeDefine.getDefaultValue())
                        .comment(typeDefine.getComment());
        String neo4jType = typeDefine.getDataType().toUpperCase();
        switch (neo4jType){
            case Neo4jDataType.BOOELAN:
                builder.dataType(BasicType.BOOLEAN_TYPE);
                break;
            case Neo4jDataType.DATE:
                case Neo4jDataType.LOCAL_DATETIME:
                builder.dataType(LocalTimeType.LOCAL_DATE_TYPE);
                break;
            case Neo4jDataType.FLOAT:
                builder.dataType(BasicType.FLOAT_TYPE);
                break;
            case Neo4jDataType.INTEGER:
                builder.dataType(BasicType.INT_TYPE);
                break;
            case Neo4jDataType.LIST:
                Map<String, BasicTypeDefine<Neo4jDataType>> typeInfo =
                        (Map) typeDefine.getNativeType().getOptions();
                SeaTunnelRowType object =
                        new SeaTunnelRowType(
                                typeInfo.keySet().toArray(new String[0]),
                                typeInfo.values().stream()
                                        .map(this::convert)
                                        .map(Column::getDataType)
                                        .toArray(SeaTunnelDataType<?>[]::new));
                builder.dataType(object);
                break;
            case Neo4jDataType.LOCAL_TIME:
                builder.dataType(LocalTimeType.LOCAL_TIME_TYPE);
                break;
            case Neo4jDataType.POINT:
                return null;



        }
        return null;
    }

    @Override
    public BasicTypeDefine<Neo4jDataType> reconvert(Column column) {
        return null;
    }
}
