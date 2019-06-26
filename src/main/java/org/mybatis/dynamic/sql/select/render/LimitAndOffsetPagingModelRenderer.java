/**
 *    Copyright 2016-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.select.render;

import java.util.Optional;

import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.LimitAndOffsetPagingModel;
import org.mybatis.dynamic.sql.util.FragmentAndParameters;

public class LimitAndOffsetPagingModelRenderer {
    private static final String LIMIT_PARAMETER = "_limit"; //$NON-NLS-1$
    private static final String OFFSET_PARAMETER = "_offset"; //$NON-NLS-1$
    private RenderingStrategy renderingStrategy;
    private LimitAndOffsetPagingModel pagingModel;

    public LimitAndOffsetPagingModelRenderer(RenderingStrategy renderingStrategy,
            LimitAndOffsetPagingModel pagingModel) {
        this.renderingStrategy = renderingStrategy;
        this.pagingModel = pagingModel;
    }
    
    public Optional<FragmentAndParameters> render() {
        return pagingModel.limit()
                .map(this::renderWithLimit)
                .orElseGet(this::renderOffsetOnly);
    }

    private Optional<FragmentAndParameters> renderWithLimit(Long limit) {
        return pagingModel.offset()
                .map(o -> renderLimitAndOffset(limit, o))
                .orElseGet(() -> renderLimitOnly(limit));
    }
    
    private Optional<FragmentAndParameters> renderOffsetOnly() {
        return pagingModel.offset().flatMap(this::renderOffsetOnly);
    }
    
    private Optional<FragmentAndParameters> renderOffsetOnly(Long offset) {
        return FragmentAndParameters
                .withFragment("offset " + renderPlaceholder(OFFSET_PARAMETER)) //$NON-NLS-1$
                .withParameter(OFFSET_PARAMETER, offset)
                .buildOptional();
    }
    
    private Optional<FragmentAndParameters> renderLimitOnly(Long limit) {
        return FragmentAndParameters.withFragment("limit " + renderPlaceholder(LIMIT_PARAMETER)) //$NON-NLS-1$
                .withParameter(LIMIT_PARAMETER, limit)
                .buildOptional();
    }
    
    private Optional<FragmentAndParameters> renderLimitAndOffset(Long limit, Long offset) {
        return FragmentAndParameters.withFragment("limit " + renderPlaceholder(LIMIT_PARAMETER) //$NON-NLS-1$
                    + " offset " + renderPlaceholder(OFFSET_PARAMETER)) //$NON-NLS-1$
                .withParameter(LIMIT_PARAMETER, limit)
                .withParameter(OFFSET_PARAMETER, offset)
                .buildOptional();
    }
    
    private String renderPlaceholder(String parameterName) {
        return renderingStrategy.getFormattedJdbcPlaceholder(RenderingStrategy.DEFAULT_PARAMETER_PREFIX,
                parameterName); 
    }
}
