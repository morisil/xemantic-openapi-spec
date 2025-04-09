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

import com.xemantic.ai.tool.schema.ObjectSchema
import com.xemantic.ai.tool.schema.StringSchema
import com.xemantic.kotlin.test.be
import com.xemantic.kotlin.test.have
import com.xemantic.kotlin.test.should
import kotlin.test.Test

class OpenApiSpecTest {

    @Test
    fun `should decode minimal OpenAPI spec`() {
        // given
        /* language=json */
        val json = $$"""
            {
              "openapi": "3.0.3",
              "info": {
                "title": "Sample API",
                "description": "A minimal sample API",
                "version": "1.0.0"
              },
              "servers": [
                {
                  "url": "https://api.example.com/v1"
                }
              ],
              "paths": {
                "/users": {
                  "get": {
                    "summary": "Returns a list of users",
                    "responses": {
                      "200": {
                        "description": "A JSON array of user objects",
                        "content": {
                          "application/json": {
                            "schema": {
                              "type": "array",
                              "items": {
                                "$ref": "#/components/schemas/User"
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                },
                "/users/{userId}": {
                  "get": {
                    "summary": "Get a user by ID",
                    "parameters": [
                      {
                        "name": "userId",
                        "in": "path",
                        "required": true,
                        "schema": {
                          "type": "string"
                        }
                      }
                    ],
                    "responses": {
                      "200": {
                        "description": "A user object",
                        "content": {
                          "application/json": {
                            "schema": {
                              "$ref": "#/components/schemas/User"
                            }
                          }
                        }
                      },
                      "404": {
                        "description": "User not found"
                      }
                    }
                  }
                }
              },
              "components": {
                "schemas": {
                  "User": {
                    "type": "object",
                    "properties": {
                      "id": {
                        "type": "string"
                      },
                      "name": {
                        "type": "string"
                      },
                      "email": {
                        "type": "string",
                        "format": "email"
                      }
                    },
                    "required": ["id", "name", "email"]
                  }
                }
              }
            }
        """.trimIndent()

        // when
        val spec = decodeOpenApiSpec(json)

        // then
        spec should {
            have(openapi == "3.0.3")
            info should {
                have(title == "Sample API")
                have(description == "A minimal sample API")
                have(version == "1.0.0")
            }
            components should {
                schemas should {
                    have(size == 1)
                    this["User"] should {
                        be<ObjectSchema>()
                        properties should {
                            have(size == 3)
                            this["id"] should {
                                be<StringSchema>()
                            }
                            this["name"] should {
                                be<StringSchema>()
                            }
                            this["email"] should {
                                be<StringSchema>()
                                have(format == "email")
                            }
                        }
                        have(required == listOf("id", "name", "email"))
                    }
                }
            }
        }
    }

}
