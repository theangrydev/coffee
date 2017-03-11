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
package io.github.theangrydev.coffee.infrastructure.classfile.programs;

import io.github.theangrydev.coffee.infrastructure.classfile.*;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.*;
import io.github.theangrydev.coffee.infrastructure.classfile.instructions.*;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryWriter;

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
        ConstantIndex<CONSTANT_Utf8_info> javaLangObject = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/Object"));
        ConstantIndex<CONSTANT_Class_info> objectClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangObject));
        ConstantIndex<CONSTANT_Utf8_info> init = constantPool.addConstant(new CONSTANT_Utf8_info("<init>"));
        ConstantIndex<CONSTANT_Utf8_info> noArgumentVoid = constantPool.addConstant(new CONSTANT_Utf8_info("()V"));
        ConstantIndex<CONSTANT_NameAndType_info> voidConstructor = constantPool.addConstant(new CONSTANT_NameAndType_info(init, noArgumentVoid));
        ConstantIndex<CONSTANT_Methodref_info> objectConstructor = constantPool.addConstant(new CONSTANT_Methodref_info(objectClass, voidConstructor));

        ConstantIndex<CONSTANT_Utf8_info> javaLangSystem = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/System"));
        ConstantIndex<CONSTANT_Class_info> systemClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangSystem));
        ConstantIndex<CONSTANT_Utf8_info> out = constantPool.addConstant(new CONSTANT_Utf8_info("out"));
        ConstantIndex<CONSTANT_Utf8_info> printStreamType = constantPool.addConstant(new CONSTANT_Utf8_info("Ljava/io/PrintStream;"));
        ConstantIndex<CONSTANT_NameAndType_info> outPrintStream = constantPool.addConstant(new CONSTANT_NameAndType_info(out, printStreamType));
        ConstantIndex<CONSTANT_Fieldref_info> systemOutField = constantPool.addConstant(new CONSTANT_Fieldref_info(systemClass, outPrintStream));

        ConstantIndex<CONSTANT_Utf8_info> javaIoPrintStream = constantPool.addConstant(new CONSTANT_Utf8_info("java/io/PrintStream"));
        ConstantIndex<CONSTANT_Class_info> printStreamClass = constantPool.addConstant(new CONSTANT_Class_info(javaIoPrintStream));
        ConstantIndex<CONSTANT_Utf8_info> println = constantPool.addConstant(new CONSTANT_Utf8_info("println"));
        ConstantIndex<CONSTANT_Utf8_info> stringToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("(Ljava/lang/String;)V"));
        ConstantIndex<CONSTANT_NameAndType_info> printlnStringToVoid = constantPool.addConstant(new CONSTANT_NameAndType_info(println, stringToVoid));
        ConstantIndex<CONSTANT_Methodref_info> printStreamPrintln = constantPool.addConstant(new CONSTANT_Methodref_info(printStreamClass, printlnStringToVoid));

        ByteConstantIndex<CONSTANT_String_info> helloWorldString = constantPool.addByteConstant(new CONSTANT_String_info(constantPool.addConstant(new CONSTANT_Utf8_info("Hello World!"))));
        ConstantIndex<CONSTANT_Class_info> helloWorldClass = constantPool.addConstant(new CONSTANT_Class_info(constantPool.addConstant(new CONSTANT_Utf8_info("HelloWorld"))));

        ConstantIndex<CONSTANT_Utf8_info> main = constantPool.addConstant(new CONSTANT_Utf8_info("main"));
        ConstantIndex<CONSTANT_Utf8_info> stringArrayToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("([Ljava/lang/String;)V"));

        ConstantIndex<CONSTANT_Utf8_info> code = constantPool.addConstant(new CONSTANT_Utf8_info("Code"));

        List<MethodInfo> methods = new ArrayList<>();
        methods.add(mainMethod(systemOutField, printStreamPrintln, helloWorldString, main, stringArrayToVoid, code));
        methods.add(constructor(init, noArgumentVoid, objectConstructor, code));

        return new HelloWorld(ClassFile.classFile(constantPool, newHashSet(ClassAccessFlag.ACC_PUBLIC, ClassAccessFlag.ACC_SUPER), helloWorldClass, objectClass, methods));
    }

    public static MethodInfo constructor(ConstantIndex<CONSTANT_Utf8_info> init, ConstantIndex<CONSTANT_Utf8_info> noArgumentVoid, ConstantIndex<CONSTANT_Methodref_info> objectConstructor, ConstantIndex<CONSTANT_Utf8_info> code) {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new aload0());
        instructions.add(new invokespecial(objectConstructor, 1));
        instructions.add(new returnvoid());
        Code_attribute codeAttribute = code(code, instructions);
        return methodInfo(newHashSet(MethodAccessFlag.ACC_PUBLIC), init, noArgumentVoid, codeAttribute);
    }

    public static MethodInfo mainMethod(ConstantIndex<CONSTANT_Fieldref_info> systemOutField, ConstantIndex<CONSTANT_Methodref_info> printStreamPrintln, ByteConstantIndex<CONSTANT_String_info> helloWorldString, ConstantIndex<CONSTANT_Utf8_info> main, ConstantIndex<CONSTANT_Utf8_info> stringArrayToVoid, ConstantIndex<CONSTANT_Utf8_info> code) {
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
