package com.example.customannotationjava;

import junit.framework.TestCase;

public class JsonParserTest extends TestCase {

    public void testParseToJson() throws Exception {
        Student student = new Student("Wong","ban","GZ",18,"110Mid");
        JsonParser jsonParser = new JsonParser();
        String actual = jsonParser.parseToJson(student);
        String expected = "{\"lastName\":\"Wong\",\"firstName\":\"ban\",\"fullName\":\"Wong ban@\",\"age\":18,\"home\":\"GZ\"}";
        assertEquals(expected,actual);
    }
}