package eel.kitchen.jsonschema;

import eel.kitchen.jsonschema.exception.MalformedJasonSchemaException;
import org.codehaus.jackson.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class JasonSchemaTest
{
    private JsonNode node;
    private JasonSchema schema;
    private List<String> messages;

    @Test
    public void testDynDB()
        throws MalformedJasonSchemaException, IOException
    {
        node = JasonLoader.load("fullschemas/dyndb.json");
        schema = new JasonSchema(node.get("schema"));

        assertFalse(schema.validate(node.get("ko")));
        messages = schema.getValidationErrors();
        assertEquals(messages.size(), 2);
        assertEquals(messages.get(0), "$.table1: property id is required but "
            + "was not found");
        assertEquals(messages.get(1), "$.table2.croute.column: node is of "
            + "type boolean, expected string");

        assertTrue(schema.validate(node.get("ok")));
        messages = schema.getValidationErrors();
        assertTrue(messages.isEmpty());
    }
}
