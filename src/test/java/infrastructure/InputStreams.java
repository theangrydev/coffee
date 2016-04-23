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
package infrastructure;

import java.io.InputStream;
import java.util.Scanner;

import static io.github.theangrydev.coffee.infrastructure.CharacterSet.CHARACTER_SET;

public class InputStreams {
    public static String readInputStream(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, CHARACTER_SET.name()).useDelimiter("\\Z");
        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return "";
        }
    }
}
