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
package staticanalysis;

import assertions.WithAssertions;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static infrastructure.FileHelpers.listDirectory;
import static java.util.stream.Collectors.toList;

public class UnitTestStaticAnalysisTest implements WithAssertions {

    @Test
    public void checkThatUnitTestsAreInTheSamePackageAsTheProductionClasses() throws IOException {
        List<Path> violations = listDirectory(Paths.get("src/test/java/io/github/theangrydev/coffee"))
                .stream()
                .filter(this::isJavaFile)
                .filter(this::productionFileIsMissing)
                .collect(toList());

        assertThat(violations).describedAs("The following unit tests were not in the same package as the production code").isEmpty();
    }

    private boolean isJavaFile(Path file) {
        return file.toString().endsWith("java");
    }

    private boolean productionFileIsMissing(Path unitTest) {
        String productionFileName = unitTest.getFileName().toString().replace("Test", "");
        int pathLength = unitTest.getNameCount();
        Path commonPath = unitTest.subpath(2, pathLength - 1);
        Path productionFile = Paths.get("src/main").resolve(commonPath).resolve(productionFileName);
        return !productionFile.toFile().exists();
    }
}
