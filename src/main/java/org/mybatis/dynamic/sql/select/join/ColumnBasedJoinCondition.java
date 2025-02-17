/*
 *    Copyright 2016-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.select.join;

import java.util.Objects;

import org.mybatis.dynamic.sql.BasicColumn;

public abstract class ColumnBasedJoinCondition<T> implements JoinCondition<T> {
    private final BasicColumn rightColumn;

    protected ColumnBasedJoinCondition(BasicColumn rightColumn) {
        this.rightColumn = Objects.requireNonNull(rightColumn);
    }

    public BasicColumn rightColumn() {
        return rightColumn;
    }

    @Override
    public <R> R accept(JoinConditionVisitor<T, R> visitor) {
        return visitor.visit(this);
    }
}
