/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.serialize.json.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import org.junit.Test;

/**
 * @author bl07637
 */
public class JsonSchemaUtil {
  final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  public void test() throws JsonProcessingException {
    JsonSchema jsonSchema = genereteSchema(Student.class);
    String schemaString = jsonSchemaToString(jsonSchema);
    System.out.println(schemaString);
  }

  public JsonSchema genereteSchema(Class clazz) throws JsonMappingException {
    JsonSchemaGenerator generator = new JsonSchemaGenerator(MAPPER);
    JsonSchema jsonSchema = generator.generateSchema(clazz);
    return jsonSchema;
  }

  public String jsonSchemaToString(JsonSchema jsonSchema) throws JsonProcessingException {
    String schemaString = MAPPER.writeValueAsString(jsonSchema);
    return schemaString;
  }

}
