/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eel.kitchen.jsonschema.v2.validation.keyword.format;

import eel.kitchen.jsonschema.v2.validation.ValidationReport;
import org.codehaus.jackson.JsonNode;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public final class EmailFormatValidator
    extends AbstractFormatValidator
{
    public EmailFormatValidator(final JsonNode node)
    {
        super(node);
    }

    @Override
    public ValidationReport validate()
    {
        try {
            new InternetAddress(node.getTextValue());
        } catch (AddressException ignored) {
            report.addMessage("string is not a valid email address");
        }

        return report;
    }
}