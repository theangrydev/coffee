/*
 * Copyright 2016-2020 Liam Williams <liam.williams@zoho.com>.
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
package acceptancetests.infrastructure;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static acceptancetests.infrastructure.InputStreams.readInputStream;
import static io.github.theangrydev.coffee.infrastructure.CharacterSet.CHARACTER_SET;

public class InputStreamsTest implements org.assertj.core.api.WithAssertions {

    @Test
    public void readsInputStreamThatIsNotEmpty() {
        String content = readInputStream(new ByteArrayInputStream("Hello World".getBytes(CHARACTER_SET)));

        assertThat(content).isEqualTo("Hello World");
    }

    @Test
    public void readsInputStreamWithNewLines() {
        String content = readInputStream(new ByteArrayInputStream("Hello\r\nWorld\r\n".getBytes(CHARACTER_SET)));

        assertThat(content).isEqualTo("Hello\r\nWorld\r\n");
    }

    @Test
    public void readsInputStreamThatIsEmpty() {
        String content = readInputStream(new ByteArrayInputStream("".getBytes(CHARACTER_SET)));

        assertThat(content).isEmpty();
    }
}