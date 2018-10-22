/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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

package com.alibaba.csp.sentinel.adapter.zuul.fallback;

import java.util.HashMap;
import java.util.Map;

/**
 * This provide fall back class manager.
 *
 * @author tiger
 */
public class SentinelFallbackManager {

    private static Map<String, SentinelFallbackProvider> fallbackProviderCache = new HashMap<String, SentinelFallbackProvider>();

    private static SentinelFallbackProvider defaultFallbackProvider = new DefaultBlockFallbackProvider();

    /**
     * Register special provider for different route.
     */
    public static synchronized void registerProvider(SentinelFallbackProvider provider) {
        String route = provider.getRoute();
        if ("*".equals(route) || route == null) {
            defaultFallbackProvider = provider;
        } else {
            fallbackProviderCache.put(route, provider);
        }
    }

    public static SentinelFallbackProvider getFallbackProvider(String route) {
        SentinelFallbackProvider provider = fallbackProviderCache.get(route);
        if (provider == null) {
            provider = defaultFallbackProvider;
        }
        return provider;
    }

    public synchronized static void clear(){
        fallbackProviderCache.clear();
    }

}
