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
package io.github.theangrydev.coffee.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static io.github.theangrydev.coffee.infrastructure.CharacterSet.CHARACTER_SET;
import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;

public class FileHelpers {

    public static String readContent(File file) {
        try {
            return new String(readAllBytes(file.toPath()), CHARACTER_SET);
        } catch (IOException e) {
            throw new IllegalArgumentException(format("Could not fetch content of file '%s' because it does not exist", file), e);
        }
    }

    public static List<Path> listDirectory(Path directory) throws IOException {
        List<Path> files = new ArrayList<>();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes fileAttributes) throws IOException {
                if (!fileAttributes.isDirectory()){
                    files.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }
}
