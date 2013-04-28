/*
 * Copyright 2008-2009 the original author or authors.
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
package org.platform.security;
import org.platform.context.AppContext;
import com.google.inject.Key;
import com.google.inject.Provider;
/**
 * 
 * @version : 2013-4-28
 * @author ������ (zyc@byshell.org)
 */
class SecurityAccessDefinition implements Provider<ISecurityAccess> {
    private String                         authSystem   = null;
    private Key<? extends ISecurityAccess> accessKey    = null;
    private ISecurityAccess                accessObject = null;
    // 
    public SecurityAccessDefinition(String authSystem, Key<? extends ISecurityAccess> accessKey) {
        this.authSystem = authSystem;
        this.accessKey = accessKey;
    }
    public void initAccess(AppContext appContext) {
        this.accessObject = appContext.getGuice().getInstance(this.accessKey);
        this.accessObject.initAccess(appContext);
    }
    public void destroyAccess(AppContext appContext) {
        if (this.accessObject != null)
            this.accessObject.destroyAccess(appContext);
    }
    public String getAuthSystem() {
        return this.authSystem;
    }
    public Key<? extends ISecurityAccess> getSecurityAccessKey() {
        return this.accessKey;
    }
    @Override
    public ISecurityAccess get() {
        return this.accessObject;
    }
}