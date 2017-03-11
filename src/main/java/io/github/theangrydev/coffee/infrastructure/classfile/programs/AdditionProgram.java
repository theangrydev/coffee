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
import static io.github.theangrydev.coffee.infrastructure.classfile.MethodInfo.methodInfo;

public class AdditionProgram implements BinaryWriter {

    private final ClassFile classFile;

    private AdditionProgram(ClassFile classFile) {
        this.classFile = classFile;
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        classFile.writeTo(binaryOutput);
    }

    public static AdditionProgram additionProgramWriter() {
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

        ConstantIndex<CONSTANT_Utf8_info> javaLangInteger = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/Integer"));
        ConstantIndex<CONSTANT_Class_info> integerClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangInteger));
        ConstantIndex<CONSTANT_Utf8_info> parseInt = constantPool.addConstant(new CONSTANT_Utf8_info("parseInt"));
        ConstantIndex<CONSTANT_Utf8_info> stringToInt = constantPool.addConstant(new CONSTANT_Utf8_info("(Ljava/lang/String;)I"));
        ConstantIndex<CONSTANT_NameAndType_info> parseIntStringToInt = constantPool.addConstant(new CONSTANT_NameAndType_info(parseInt, stringToInt));
        ConstantIndex<CONSTANT_Methodref_info> integerParseInt = constantPool.addConstant(new CONSTANT_Methodref_info(integerClass, parseIntStringToInt));

        ConstantIndex<CONSTANT_Utf8_info> javaIoPrintStream = constantPool.addConstant(new CONSTANT_Utf8_info("java/io/PrintStream"));
        ConstantIndex<CONSTANT_Class_info> printStreamClass = constantPool.addConstant(new CONSTANT_Class_info(javaIoPrintStream));
        ConstantIndex<CONSTANT_Utf8_info> println = constantPool.addConstant(new CONSTANT_Utf8_info("println"));
        ConstantIndex<CONSTANT_Utf8_info> intToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("(I)V"));
        ConstantIndex<CONSTANT_NameAndType_info> printlnIntToVoid = constantPool.addConstant(new CONSTANT_NameAndType_info(println, intToVoid));
        ConstantIndex<CONSTANT_Methodref_info> printStreamPrintln = constantPool.addConstant(new CONSTANT_Methodref_info(printStreamClass, printlnIntToVoid));

        ConstantIndex<CONSTANT_Utf8_info> additionProgram = constantPool.addConstant(new CONSTANT_Utf8_info("AdditionProgram"));
        ConstantIndex<CONSTANT_Class_info> additionClass = constantPool.addConstant(new CONSTANT_Class_info(additionProgram));

        ConstantIndex<CONSTANT_Utf8_info> main = constantPool.addConstant(new CONSTANT_Utf8_info("main"));
        ConstantIndex<CONSTANT_Utf8_info> stringArrayToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("([Ljava/lang/String;)V"));

        ConstantIndex<CONSTANT_Utf8_info> code = constantPool.addConstant(new CONSTANT_Utf8_info("Code"));

        List<MethodInfo> methods = new ArrayList<>();
        methods.add(mainMethod(systemOutField, integerParseInt, printStreamPrintln, main, stringArrayToVoid, code));
        methods.add(constructor(init, noArgumentVoid, objectConstructor, code));

        ClassFile classFile = ClassFile.classFile(constantPool, newHashSet(ClassAccessFlag.ACC_PUBLIC, ClassAccessFlag.ACC_SUPER), additionClass, objectClass, methods);

        return new AdditionProgram(classFile);
    }

    public static MethodInfo constructor(ConstantIndex<CONSTANT_Utf8_info> init, ConstantIndex<CONSTANT_Utf8_info> noArgumentVoid, ConstantIndex<CONSTANT_Methodref_info> objectConstructor, ConstantIndex<CONSTANT_Utf8_info> code) {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new aload0());
        instructions.add(new invokespecial(objectConstructor, 1));
        instructions.add(new returnvoid());
        Code_attribute codeAttribute = Code_attribute.code(code, instructions);
        return methodInfo(newHashSet(MethodAccessFlag.ACC_PUBLIC), init, noArgumentVoid, codeAttribute);
    }

    public static MethodInfo mainMethod(ConstantIndex<CONSTANT_Fieldref_info> systemOutField, ConstantIndex<CONSTANT_Methodref_info> integerParseInt, ConstantIndex<CONSTANT_Methodref_info> printStreamPrintln, ConstantIndex<CONSTANT_Utf8_info> main, ConstantIndex<CONSTANT_Utf8_info> stringArrayToVoid, ConstantIndex<CONSTANT_Utf8_info> code) {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new getstatic(systemOutField));
        instructions.add(new aload0());
        instructions.add(new iconst0());
        instructions.add(new aaload());
        instructions.add(new invokestatic(integerParseInt, 1, true));
        instructions.add(new aload0());
        instructions.add(new iconst1());
        instructions.add(new aaload());
        instructions.add(new invokestatic(integerParseInt, 1, true));
        instructions.add(new iadd());
        instructions.add(new invokevirtual(printStreamPrintln, 1, false));
        instructions.add(new returnvoid());
        Code_attribute codeAttribute = Code_attribute.code(code, instructions);
        return methodInfo(newHashSet(MethodAccessFlag.ACC_PUBLIC, MethodAccessFlag.ACC_STATIC), main, stringArrayToVoid, codeAttribute);
    }
}
