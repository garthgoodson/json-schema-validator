/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.syntax;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.report.Message;
import org.eel.kitchen.jsonschema.util.NodeType;

import java.util.List;

/**
 * Syntax validator for the {@code items} keyword
 */
public final class ExtendsSyntaxChecker
    extends SimpleSyntaxChecker
{
    private static final SyntaxChecker instance = new ExtendsSyntaxChecker();

    public static SyntaxChecker getInstance()
    {
        return instance;
    }

    private ExtendsSyntaxChecker()
    {
        super("extends", NodeType.ARRAY, NodeType.OBJECT);
    }

    @Override
    void checkValue(final Message.Builder msg, final List<Message> messages,
        final JsonNode schema)
    {
        final JsonNode extendsNode = schema.get(keyword);

        if (!extendsNode.isArray())
            return;

        /*
         * If it is an array, check that its elements are all objects
         */

        int index = 0;
        NodeType elementType;
        for (final JsonNode element: extendsNode) {
            elementType = NodeType.getNodeType(element);
            if (elementType != NodeType.OBJECT) {
                msg.setMessage("incorrect type for array element")
                    .addInfo("index", index).addInfo("found", elementType)
                    .addInfo("expected", NodeType.OBJECT);
                messages.add(msg.build());
            }
            index++;
        }
    }
}
