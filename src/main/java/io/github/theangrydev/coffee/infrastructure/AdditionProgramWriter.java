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

@SuppressWarnings("PMD.TooManyMethods") // Will refactor when green
public class AdditionProgramWriter implements BinaryWriter {

    private static final int CLASS_PUBLIC_FLAG = 0x0001;
    private static final int CLASS_TREAT_SUPER_METHODS_SPECIALLY_FLAG = 0x0020;

    private static final int METHOD_PUBLIC_FLAG = 0x0001;
    private static final int METHOD_STATIC_FLAG = 0x0008;
    private final Magic magic = new Magic();

    private BinaryOutput binaryOutput;
    private int constantCount;
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
        writeMethodFlags(METHOD_PUBLIC_FLAG | METHOD_STATIC_FLAG);
        writeMethodName(main);
        writeMethodTypeSignature(stringArrayToVoid);
        writeMethodAttributeSize(1);
        writeMainMethodAttribute();
    }

    private void writeMainMethodAttribute() {
        writeAttributeName(code);
        int instructionSizeInBytes = 3 + 1 + 1 + 1 + 3 + 1 + 1 + 1 + 3 + 1 + 3 + 1;
        writeAttributeSizeInBytes(2 + 2 + 4 + instructionSizeInBytes + 2 + 2);
        writeMaxStackDepth(4);
        writeMaxLocalVariables(1);
        writeMainMethodInstructions(instructionSizeInBytes);
        writeEmptyExceptionTable();
        writeEmptyAttributes();
    }

    private void writeMainMethodInstructions(int sizeInBytes) {
        writeSizeOfInstructions(sizeInBytes);
        writeGetstatic(systemOutField);
        writeAload0();
        writeIconst0();
        writeAaload();
        writeInvokestatic(integerParseInt);
        writeAload0();
        writeIConst1();
        writeAaload();
        writeInvokestatic(integerParseInt);
        writeIadd();
        writeInvokevirtual(printStreamPrintln);
        writeVoidReturn();
    }

    private void writeInvokevirtual(int methodIndex) {
        binaryOutput.writeByte(0xb6);
        writeConstantPoolIndex(methodIndex);
    }

    private void writeIadd() {
        binaryOutput.writeByte(0x60);
    }

    private void writeIConst1() {
        binaryOutput.writeByte(0x4);
    }

    private void writeInvokestatic(int methodIndex) {
        binaryOutput.writeByte(0xb8);
        writeConstantPoolIndex(methodIndex);
    }

    private void writeAaload() {
        binaryOutput.writeByte(0x32);
    }

    private void writeIconst0() {
        binaryOutput.writeByte(0x3);
    }

    private void writeGetstatic(int fieldIndex) {
        binaryOutput.writeByte(0xb2);
        writeConstantPoolIndex(fieldIndex);
    }

    private void writeAdditonProgramConstructorMethod() {
        writeMethodFlags(METHOD_PUBLIC_FLAG);
        writeMethodName(init);
        writeMethodTypeSignature(noArgumentVoid);
        writeMethodAttributeSize(1);
        writeAdditionProgramConstructorAttribute();
    }

    private void writeAdditionProgramConstructorAttribute() {
        writeAttributeName(code);
        int instructionSizeInBytes = 1 + 3 + 1;
        writeAttributeSizeInBytes(2 + 2 + 4 + instructionSizeInBytes + 2 + 2);
        writeMaxStackDepth(1);
        writeMaxLocalVariables(1);
        writeAdditionProgramConstructorInstructions(instructionSizeInBytes);
        writeEmptyExceptionTable();
        writeEmptyAttributes();
    }

    private void writeEmptyAttributes() {
        binaryOutput.writeShort(0);
    }

    private void writeEmptyExceptionTable() {
        binaryOutput.writeShort(0);
    }

    private void writeAttributeSizeInBytes(int sizeInBytes) {
        binaryOutput.writeInt(sizeInBytes);
    }

    private void writeAttributeName(int attributeNameIndex) {
        writeConstantPoolIndex(attributeNameIndex);
    }

    private void writeAdditionProgramConstructorInstructions(int sizeInBytes) {
        writeSizeOfInstructions(sizeInBytes);
        writeAload0();
        writeInvokespecial(objectConstructor);
        writeVoidReturn();
    }

    private void writeVoidReturn() {
        binaryOutput.writeByte(0xb1);
    }

    private void writeInvokespecial(int objectConstructor) {
        binaryOutput.writeByte(0xb7);
        writeConstantPoolIndex(objectConstructor);
    }

    private void writeAload0() {
        binaryOutput.writeByte(0x2a);
    }

    private void writeSizeOfInstructions(int sizeInBytes) {
        binaryOutput.writeInt(sizeInBytes);
    }

    private void writeMaxLocalVariables(int maxLocalVariables) {
        binaryOutput.writeShort(maxLocalVariables);
    }

    private void writeMethodAttributeSize(int numberOfAttributes) {
        binaryOutput.writeShort(numberOfAttributes);
    }

    private void writeMethodTypeSignature(int typeSignatureIndex) {
        writeConstantPoolIndex(typeSignatureIndex);
    }

    private void writeMethodName(int methodNameIndex) {
        writeConstantPoolIndex(methodNameIndex);
    }

    private void writeMethodFlags(int methodFlags) {
        binaryOutput.writeShort(methodFlags);
    }

    private void writeNumberOfMethods(int numberOfMethods) {
        binaryOutput.writeShort(numberOfMethods);
    }

    private void writeMaxStackDepth(int maxStackDepth) {
        binaryOutput.writeShort(maxStackDepth);
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
        writeConstantPoolCount(29);
        int javaLangObject = writeUTF8("java/lang/Object");
        objectClass = writeClass(javaLangObject);
        init = writeUTF8("<init>");
        noArgumentVoid = writeUTF8("()V");
        int voidConstructor = writeNameAndType(init, noArgumentVoid);
        objectConstructor = writeMethodReference(objectClass, voidConstructor);

        int javaLangSystem = writeUTF8("java/lang/System");
        int systemClass = writeClass(javaLangSystem);
        int out = writeUTF8("out");
        int printStreamType = writeUTF8("Ljava/io/PrintStream;");
        int outPrintStream = writeNameAndType(out, printStreamType);
        systemOutField = writeFieldReference(systemClass, outPrintStream);

        int javaLangInteger = writeUTF8("java/lang/Integer");
        int integerClass = writeClass(javaLangInteger);
        int parseInt = writeUTF8("parseInt");
        int stringToInt = writeUTF8("(Ljava/lang/String;)I");
        int parseIntStringToInt = writeNameAndType(parseInt, stringToInt);
        integerParseInt = writeMethodReference(integerClass, parseIntStringToInt);

        int javaIoPrintStream = writeUTF8("java/io/PrintStream");
        int printStreamClass = writeClass(javaIoPrintStream);
        int println = writeUTF8("println");
        int intToVoid = writeUTF8("(I)V");
        int printlnIntToVoid = writeNameAndType(println, intToVoid);
        printStreamPrintln = writeMethodReference(printStreamClass, printlnIntToVoid);

        int additionProgram = writeUTF8("AdditionProgram");
        additionClass = writeClass(additionProgram);

        main = writeUTF8("main");
        stringArrayToVoid = writeUTF8("([Ljava/lang/String;)V");

        code = writeUTF8("Code");
    }

    private int writeFieldReference(int classIndex, int fieldNameAndTypIndex) {
        binaryOutput.writeByte(9);
        writeConstantPoolIndex(classIndex);
        writeConstantPoolIndex(fieldNameAndTypIndex);
        return registerConstant();
    }

    private int writeNameAndType(int nameIndex, int descriptorIndex) {
        binaryOutput.writeByte(12);
        writeConstantPoolIndex(nameIndex);
        writeConstantPoolIndex(descriptorIndex);
        return registerConstant();
    }

    private int writeMethodReference(int classIndex, int methodNameAndTypeIndex) {
        binaryOutput.writeByte(10);
        writeConstantPoolIndex(classIndex);
        writeConstantPoolIndex(methodNameAndTypeIndex);
        return registerConstant();
    }

    private int writeClass(int nameIndex) {
        binaryOutput.writeByte(7);
        writeConstantPoolIndex(nameIndex);
        return registerConstant();
    }

    private int writeUTF8(String string) {
        new CONSTANT_Utf8_info(string).writeTo(binaryOutput);
        return registerConstant();
    }

    private int registerConstant() {
        constantCount = constantCount + 1;
        return constantCount;
    }

    private void writeConstantPoolIndex(int classIndex) {
        binaryOutput.writeShort(classIndex);
    }

    private void writeConstantPoolCount(int numberOfEntries) {
        binaryOutput.writeShort(numberOfEntries + 1);
    }

    private void writeVersion() {
        binaryOutput.writeShort(0);
        binaryOutput.writeShort(52);
    }

    private void writeMagicHeader() {
        magic.writeTo(binaryOutput);
    }
}
