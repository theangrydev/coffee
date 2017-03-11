/*
 * Copyright 2016 Liam Williams <liam.williams@zoho.com>.
 *
 * This file is part of coffee.
 *
 * coffee is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * coffee is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with coffee.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.theangrydev.coffee.infrastructure.dotnet;

import io.github.theangrydev.coffee.infrastructure.LongFlag;

public enum TableIndexFlag implements LongFlag {
    MODULE(0x00),
    TYPE_REF(0x01),
    TYPE_DEF(0x02),
    FIELD(0x04),
    METHOD_DEF(0x06),
    PARAM(0x08),
    INTERFACE_IMPL(0x09),
    MEMBER_REF(0x0A),
    CONSTANT(0x0B),
    CUSTOM_ATTRIBUTE(0x0C),
    FIELD_MARSHAL(0x0D),
    DECL_SECURITY(0x0E),
    CLASS_LAYOUT(0x0F),
    FIELD_LAYOUT(0x10),
    STAND_ALONE_SIG(0x11),
    EVENT_MAP(0x12),
    EVENT(0x14),
    PROPERTY_MAP(0x15),
    PROPERTY(0x17),
    METHOD_SEMANTICS(0x18),
    METHOD_IMPL(0x19),
    MODULE_REF(0x1A),
    TYPE_SPEC(0x1B),
    IMPL_MAP(0x1C),
    FIELD_RVA(0x1D),
    ASSEMBLY(0x20),
    ASSEMBLY_PROCESSOR(0x21),
    ASSEMBLY_OS(0x22),
    ASSEMBLY_REF(0x23),
    ASSEMBLY_REF_PROCESSOR(0x24),
    ASSEMBLY_REF_OS(0x25),
    FILE(0x26),
    EXPORTED_TYPE(0x27),
    MANIFEST_RESOURCE(0x28),
    NESTED_CLASS(0x29),
    GENERIC_PARAM(0x2A),
    METHOD_SPEC(0x2B),
    GENERIC_PARAM_CONSTRAINT(0x2C)
    ;

    private final long value;

    TableIndexFlag(int index) {
        this.value = 1L << index;
    }

    @Override
    public long value() {
        return value;
    }
}
