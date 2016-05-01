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

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static io.github.theangrydev.coffee.infrastructure.Code_attribute.code;
import static io.github.theangrydev.coffee.infrastructure.MethodAccessFlag.ACC_PUBLIC;
import static io.github.theangrydev.coffee.infrastructure.MethodAccessFlag.ACC_STATIC;
import static io.github.theangrydev.coffee.infrastructure.MethodInfo.methodInfo;

@SuppressWarnings("PMD.TooManyMethods") // TODO: refactor
public class AdditionProgramWriter implements BinaryWriter {

    private static final int CLASS_PUBLIC_FLAG = 0x0001;
    private static final int CLASS_TREAT_SUPER_METHODS_SPECIALLY_FLAG = 0x0020;

    private final ConstantPool constantPool = new ConstantPool();
    private final Magic magic = new Magic();

    private BinaryOutput binaryOutput;
    private int objectConstructor;
    private int systemOutField;
    private int integerParseInt;
    private int printStreamPrintln;
    private int additionClass;
    private int main;
    private int stringArrayToVoid;
    private int code;
    private int objectClass;
    private int init;
    private int noArgumentVoid;

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        this.binaryOutput = binaryOutput;
        writeMagicHeader();
        writeVersion();
        writeConstantPool();
        writeAccessFlags();
        writeThisIndex();
        writeSuperIndex();
        writeClassInterfaces();
        writeClassFields();
        writeClassMethods();
        writeClassAttributes();
    }

    private void writeClassAttributes() {
        binaryOutput.writeShort(0);
    }

    private void writeClassMethods() {
        writeNumberOfMethods(2);
        writeAdditonProgramConstructorMethod();
        writeMainMethod();
    }

    private void writeMainMethod() {
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
        Code_attribute codeAttribute = code(code, instructions);

        MethodInfo mainMethod = methodInfo(newHashSet(ACC_PUBLIC, ACC_STATIC), main, stringArrayToVoid, codeAttribute);

        mainMethod.writeTo(binaryOutput);
    }

    private void writeAdditonProgramConstructorMethod() {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new aload0());
        instructions.add(new invokespecial(objectConstructor, 1));
        instructions.add(new returnvoid());
        Code_attribute codeAttribute = code(code, instructions);
        MethodInfo constructor = methodInfo(newHashSet(ACC_PUBLIC), init, noArgumentVoid, codeAttribute);
        constructor.writeTo(binaryOutput);
    }

    private void writeNumberOfMethods(int numberOfMethods) {
        binaryOutput.writeShort(numberOfMethods);
    }

    private void writeClassFields() {
        binaryOutput.writeShort(0);
    }

    private void writeClassInterfaces() {
        binaryOutput.writeShort(0);
    }

    private void writeSuperIndex() {
        writeConstantPoolIndex(objectClass);
    }

    private void writeThisIndex() {
        writeConstantPoolIndex(additionClass);
    }

    private void writeAccessFlags() {
        binaryOutput.writeShort(CLASS_PUBLIC_FLAG | CLASS_TREAT_SUPER_METHODS_SPECIALLY_FLAG);
    }

    private void writeConstantPool() {
        int javaLangObject = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/Object"));
        objectClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangObject));
        init = constantPool.addConstant(new CONSTANT_Utf8_info("<init>"));
        noArgumentVoid = constantPool.addConstant(new CONSTANT_Utf8_info("()V"));
        int voidConstructor = constantPool.addConstant(new CONSTANT_NameAndType_info(init, noArgumentVoid));
        objectConstructor = constantPool.addConstant(new CONSTANT_Methodref_info(objectClass, voidConstructor));

        int javaLangSystem = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/System"));
        int systemClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangSystem));
        int out = constantPool.addConstant(new CONSTANT_Utf8_info("out"));
        int printStreamType = constantPool.addConstant(new CONSTANT_Utf8_info("Ljava/io/PrintStream;"));
        int outPrintStream = constantPool.addConstant(new CONSTANT_NameAndType_info(out, printStreamType));
        systemOutField = constantPool.addConstant(new CONSTANT_Fieldref_info(systemClass, outPrintStream));

        int javaLangInteger = constantPool.addConstant(new CONSTANT_Utf8_info("java/lang/Integer"));
        int integerClass = constantPool.addConstant(new CONSTANT_Class_info(javaLangInteger));
        int parseInt = constantPool.addConstant(new CONSTANT_Utf8_info("parseInt"));
        int stringToInt = constantPool.addConstant(new CONSTANT_Utf8_info("(Ljava/lang/String;)I"));
        int parseIntStringToInt = constantPool.addConstant(new CONSTANT_NameAndType_info(parseInt, stringToInt));
        integerParseInt = constantPool.addConstant(new CONSTANT_Methodref_info(integerClass, parseIntStringToInt));

        int javaIoPrintStream = constantPool.addConstant(new CONSTANT_Utf8_info("java/io/PrintStream"));
        int printStreamClass = constantPool.addConstant(new CONSTANT_Class_info(javaIoPrintStream));
        int println = constantPool.addConstant(new CONSTANT_Utf8_info("println"));
        int intToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("(I)V"));
        int printlnIntToVoid = constantPool.addConstant(new CONSTANT_NameAndType_info(println, intToVoid));
        printStreamPrintln = constantPool.addConstant(new CONSTANT_Methodref_info(printStreamClass, printlnIntToVoid));

        int additionProgram = constantPool.addConstant(new CONSTANT_Utf8_info("AdditionProgram"));
        additionClass = constantPool.addConstant(new CONSTANT_Class_info(additionProgram));

        main = constantPool.addConstant(new CONSTANT_Utf8_info("main"));
        stringArrayToVoid = constantPool.addConstant(new CONSTANT_Utf8_info("([Ljava/lang/String;)V"));

        code = constantPool.addConstant(new CONSTANT_Utf8_info("Code"));
        constantPool.writeTo(binaryOutput);
    }

    private void writeConstantPoolIndex(int classIndex) {
        binaryOutput.writeShort(classIndex);
    }

    private void writeVersion() {
        binaryOutput.writeShort(0);
        binaryOutput.writeShort(52);
    }

    private void writeMagicHeader() {
        magic.writeTo(binaryOutput);
    }
}
