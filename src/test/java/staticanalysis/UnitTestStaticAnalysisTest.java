/*
 * Copyright 2016-2017 Liam Williams <liam.williams@zoho.com>.
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
package staticanalysis;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class UnitTestStaticAnalysisTest implements org.assertj.core.api.WithAssertions {

    @Test
    public void checkThatUnitTestsAreInTheSamePackageAsTheProductionClasses() throws IOException {
        List<Path> violations = listDirectory(Paths.get("src/test/java/io/github/theangrydev/coffee"))
                .stream()
                .filter(this::isUnitTest)
                .filter(this::productionFileIsMissing)
                .collect(toList());

        assertThat(violations).describedAs("The following unit tests were not in the same package as the production code").isEmpty();
    }

    private boolean isUnitTest(Path file) {
        return file.toString().endsWith("Test.java");
    }

    private boolean productionFileIsMissing(Path unitTest) {
        String productionFileName = unitTest.getFileName().toString().replace("Test", "");
        int pathLength = unitTest.getNameCount();
        Path commonPath = unitTest.subpath(2, pathLength - 1);
        Path productionFile = Paths.get("src/main").resolve(commonPath).resolve(productionFileName);
        return !productionFile.toFile().exists();
    }

    private static List<Path> listDirectory(Path directory) throws IOException {
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
