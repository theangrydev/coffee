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
package io.github.theangrydev.coffee.infrastructure.classfile;

import io.github.theangrydev.coffee.infrastructure.classfile.constants.*;
import io.github.theangrydev.coffee.infrastructure.classfile.instructions.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static io.github.theangrydev.coffee.infrastructure.classfile.Code_attribute.code;
import static io.github.theangrydev.coffee.infrastructure.classfile.MethodInfo.methodInfo;

public class HelloWorld implements BinaryWriter {

    private final ClassFile classFile;

    private HelloWorld(ClassFile classFile) {
        this.classFile = classFile;
    }

    public static HelloWorld helloWorld() {
        ConstantPool constantPool = new ConstantPool();
        int javaLangObject = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/Object"));
        int objectClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangObject));
        int init = constantPool.addConstant(new CONSTANT_Utf8_info("<init>"));
        int noArgumentVoid = constantPool.addConstant(new CONSTANT_Utf8_info("()V"));
        int voidConstructor = constantPool.addConstant(new CONSTANT_NameAndType_info(init, noArgumentVoid));
        int objectConstructor = constantPool.addConstant(new CONSTANT_Methodref_info(objectClass, voidConstructor));

        int javaLangSystem = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/System"));
        int systemClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangSystem));
        int out = constantPool.addConstant(new CONSTANT_Utf8_info("out"));
        int printStreamType = constantPool.addConstant(new CONSTANT_Utf8_info("Ljava/io/PrintStream;"));
        int outPrintStream = constantPool.addConstant(new CONSTANT_NameAndType_info(out, printStreamType));
        int systemOutField = constantPool.addConstant(new CONSTANT_Fieldref_info(systemClass, outPrintStream));

        int javaIoPrintStream = constantPool.addConstant(new CONSTANT_Utf8_info("java/io/PrintStream"));
        int printStreamClass = constantPool.addConstant(new CONSTANT_Class_info(javaIoPrintStream));
        int println = constantPool.addConstant(new CONSTANT_Utf8_info("println"));
        int stringToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("(Ljava/lang/String;)V"));
        int printlnStringToVoid = constantPool.addConstant(new CONSTANT_NameAndType_info(println, stringToVoid));
        int printStreamPrintln = constantPool.addConstant(new CONSTANT_Methodref_info(printStreamClass, printlnStringToVoid));

        int helloWorldString = constantPool.addConstant(new CONSTANT_String_info(constantPool.addConstant(new CONSTANT_Utf8_info("Hello World!"))));
        int helloWorldClass = constantPool.addConstant(new CONSTANT_Class_info(constantPool.addConstant(new CONSTANT_Utf8_info("HelloWorld"))));

        int main = constantPool.addConstant(new CONSTANT_Utf8_info("main"));
        int stringArrayToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("([Ljava/lang/String;)V"));

        int code = constantPool.addConstant(new CONSTANT_Utf8_info("Code"));

        List<MethodInfo> methods = new ArrayList<>();
        methods.add(mainMethod(systemOutField, printStreamPrintln, helloWorldString, main, stringArrayToVoid, code));
        methods.add(constructor(init, noArgumentVoid, objectConstructor, code));

        return new HelloWorld(ClassFile.classFile(constantPool, newHashSet(ClassAccessFlag.ACC_PUBLIC, ClassAccessFlag.ACC_SUPER), helloWorldClass, objectClass, methods));
    }

    public static MethodInfo constructor(int init, int noArgumentVoid, int objectConstructor, int code) {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new aload0());
        instructions.add(new invokespecial(objectConstructor, 1));
        instructions.add(new returnvoid());
        Code_attribute codeAttribute = code(code, instructions);
        return methodInfo(newHashSet(MethodAccessFlag.ACC_PUBLIC), init, noArgumentVoid, codeAttribute);
    }

    public static MethodInfo mainMethod(int systemOutField, int printStreamPrintln, int helloWorldString, int main, int stringArrayToVoid, int code) {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new getstatic(systemOutField));
        instructions.add(new ldc(helloWorldString));
        instructions.add(new invokevirtual(printStreamPrintln, 1, false));
        instructions.add(new returnvoid());
        Code_attribute codeAttribute = code(code, instructions);
        return methodInfo(newHashSet(MethodAccessFlag.ACC_PUBLIC, MethodAccessFlag.ACC_STATIC), main, stringArrayToVoid, codeAttribute);
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        classFile.writeTo(binaryOutput);
    }
}
