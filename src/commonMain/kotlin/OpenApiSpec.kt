/*
 * Copyright 2025 Kazimierz Pogoda / Xemantic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xemantic.openapi.spec

import com.xemantic.ai.tool.schema.JsonSchema
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val openApiJson = Json {
    ignoreUnknownKeys = true // should be removed once implementation is finished
}

public fun decodeOpenApiSpec(
    json: String
): OpenApiSpec = openApiJson.decodeFromString<OpenApiSpec>(json)

@Serializable
public class OpenApiSpec private constructor(
    public val openapi: String,  // TODO is it optional?
    public val info: Info, // TODO is it optional?
    public val components: Components? // TODO is it optional?
) {

    public class Builder {

        public var openapi: String? = null
        public var info: Info? = null
        public var components: Components? = null

        public fun build(): OpenApiSpec = OpenApiSpec(
            requireNotNull(openapi) { "openapi cannot be null" }, // TODO if it is optional, not null check can be skipped
            requireNotNull(info) { "openapi cannot be null" }, // TODO if it is optional, not null check can be skipped
            requireNotNull(components) { "components cannot be null" }, // TODO if it is optional, not null check can be skipped
        )

    }

}

public fun OpenApiSpec(
    block: OpenApiSpec.Builder.() -> Unit
): OpenApiSpec = OpenApiSpec.Builder().also(block).build()

@Serializable
public class Info private constructor(
    public val title: String, // TODO is it optional?
    public val description: String, // TODO is it optional?
    public val version: String  // TODO is it optional?
) {

    public class Builder {

        public var title: String? = null
        public var description: String? = null
        public var version: String? = null

        public fun build(): Info = Info(
            requireNotNull(title) { "title cannot be null" }, // TODO if it is optional, not null check can be skipped
            requireNotNull(description) { "description cannot be null" }, // TODO if it is optional, not null check can be skipped
            requireNotNull(version) { "version cannot be null" }, // TODO if it is optional, not null check can be skipped
        )

    }

}

public fun Info(
    block: Info.Builder.() -> Unit
): Info = Info.Builder().also(block).build()

@Serializable
public class Components private constructor(
    public val schemas: Map<String, JsonSchema>, // TODO is it optional?
) {

    public class Builder {

        public var schemas: Map<String, JsonSchema>? = null

        public fun build(): Components = Components(
            requireNotNull(schemas) { "schemas cannot be null" }, // TODO if it is optional, not null check can be skipped
        )

    }

}

public fun Components(
    block: Components.Builder.() -> Unit
): Components = Components.Builder().also(block).build()
