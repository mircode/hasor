/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.spring.beans;
import net.hasor.core.TypeSupplier;
import net.hasor.core.provider.InstanceProvider;
import org.springframework.context.ApplicationContext;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Spring 的 TypeSupplier
 * @version : 2020年02月27日
 * @author 赵永春 (zyc@hasor.net)
 */
public class SpringTypeSupplier implements TypeSupplier {
    private Supplier<ApplicationContext> applicationContext;

    public SpringTypeSupplier(ApplicationContext applicationContext) {
        this(InstanceProvider.of(Objects.requireNonNull(applicationContext)));
    }

    public SpringTypeSupplier(Supplier<ApplicationContext> applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T get(Class<? extends T> targetType) {
        return applicationContext.get().getBean(targetType);
    }

    @Override
    public <T> boolean test(Class<? extends T> targetType) {
        return applicationContext.get().getBeanNamesForType(targetType).length > 0;
    }
}
