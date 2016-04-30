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
public class HelloWorldWriter implements BinaryWriter {

    private static final int CLASS_PUBLIC_FLAG = 0x0001;
    private static final int CLASS_TREAT_SUPER_METHODS_SPECIALLY_FLAG = 0x0020;

    private static final int METHOD_PUBLIC_FLAG = 0x0001;
    private static final int METHOD_STATIC_FLAG = 0x0008;

    private BinaryOutput binaryOutput;
    private int constantCount;
    private int objectClass;
    private int init;
    private int noArgumentVoid;
    private int objectConstructor;
    private int code;
    private int helloWorldClass;
    private int main;
    private int stringArrayToVoid;
    private int printStreamPrintln;
    private int systemOutField;
    private int helloWorldString;

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

    private void writeConstantPool() {
        writeConstantPoolCount(25);
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

        int javaIoPrintStream = writeUTF8("java/io/PrintStream");
        int printStreamClass = writeClass(javaIoPrintStream);
        int println = writeUTF8("println");
        int stringToVoid = writeUTF8("(Ljava/lang/String;)V");
        int printlnStringToVoid = writeNameAndType(println, stringToVoid);
        printStreamPrintln = writeMethodReference(printStreamClass, printlnStringToVoid);

        helloWorldString = writeString(writeUTF8("Hello World!"));
        helloWorldClass = writeClass(writeUTF8("HelloWorld"));

        main = writeUTF8("main");
        stringArrayToVoid = writeUTF8("([Ljava/lang/String;)V");

        code = writeUTF8("Code");
    }

    private void writeThisIndex() {
        writeConstantPoolIndex(helloWorldClass);
    }

    private void writeSuperIndex() {
        writeConstantPoolIndex(objectClass);
    }

    private void writeClassMethods() {
        writeNumberOfMethods(2);
        writeConstructorMethod();
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
        int instructionSizeInBytes = 3 + 2 + 3 + 1;
        writeAttributeSizeInBytes(2 + 2 + 4 + instructionSizeInBytes + 2 + 2);
        writeMaxStackDepth(2);
        writeMaxLocalVariables(1);
        writeMainMethodInstructions(instructionSizeInBytes);
        writeEmptyExceptionTable();
        writeEmptyAttributes();
    }

    private void writeMainMethodInstructions(int sizeInBytes) {
        writeSizeOfInstructions(sizeInBytes);
        writeGetstatic(systemOutField);
        writeLdc(helloWorldString);
        writeInvokevirtual(printStreamPrintln);
        writeVoidReturn();
    }

    private void writeLdc(int constantIndex) {
        binaryOutput.writeByte(0x12);
        binaryOutput.writeByte(constantIndex);
    }

    private void writeInvokevirtual(int methodIndex) {
        binaryOutput.writeByte(0xb6);
        writeConstantPoolIndex(methodIndex);
    }

    private void writeGetstatic(int fieldIndex) {
        binaryOutput.writeByte(0xb2);
        writeConstantPoolIndex(fieldIndex);
    }

    private void writeConstructorMethod() {
        writeMethodFlags(METHOD_PUBLIC_FLAG);
        writeMethodName(init);
        writeMethodTypeSignature(noArgumentVoid);
        writeMethodAttributeSize(1);
        writeConstructorCodeAttribute();
    }

    private void writeConstructorCodeAttribute() {
        writeAttributeName(code);
        int instructionSizeInBytes = 1 + 3 + 1;
        writeAttributeSizeInBytes(2 + 2 + 4 + instructionSizeInBytes + 2 + 2);
        writeMaxStackDepth(1);
        writeMaxLocalVariables(1);
        writeAdditionProgramConstructorInstructions(instructionSizeInBytes);
        writeEmptyExceptionTable();
        writeEmptyAttributes();
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

    private void writeAttributeSizeInBytes(int sizeInBytes) {
        binaryOutput.writeInt(sizeInBytes);
    }

    private void writeAttributeName(int attributeNameIndex) {
        writeConstantPoolIndex(attributeNameIndex);
    }

    private void writeEmptyAttributes() {
        binaryOutput.writeShort(0);
    }

    private void writeEmptyExceptionTable() {
        binaryOutput.writeShort(0);
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

    private void writeMaxStackDepth(int maxStackDepth) {
        binaryOutput.writeShort(maxStackDepth);
    }

    private void writeNumberOfMethods(int numberOfMethods) {
        binaryOutput.writeShort(numberOfMethods);
    }

    private void writeConstantPoolCount(int numberOfEntries) {
        binaryOutput.writeShort(numberOfEntries + 1);
    }

    private int writeFieldReference(int classIndex, int fieldNameAndTypIndex) {
        new CONSTANT_Fieldref_info(classIndex, fieldNameAndTypIndex).writeTo(binaryOutput);
        return registerConstant();
    }

    private int writeNameAndType(int nameIndex, int descriptorIndex) {
        new CONSTANT_NameAndType_info(nameIndex, descriptorIndex).writeTo(binaryOutput);
        return registerConstant();
    }

    private int writeMethodReference(int classIndex, int methodNameAndTypeIndex) {
        new CONSTANT_Methodref_info(classIndex, methodNameAndTypeIndex).writeTo(binaryOutput);
        return registerConstant();
    }

    private int writeClass(int nameIndex) {
        new CONSTANT_Class_info(nameIndex).writeTo(binaryOutput);
        return registerConstant();
    }

    private int writeString(int constantIndex) {
        new CONSTANT_String_info(constantIndex).writeTo(binaryOutput);
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

    private void writeClassFields() {
        binaryOutput.writeShort(0);
    }

    private void writeClassInterfaces() {
        binaryOutput.writeShort(0);
    }

    private void writeAccessFlags() {
        binaryOutput.writeShort(CLASS_PUBLIC_FLAG | CLASS_TREAT_SUPER_METHODS_SPECIALLY_FLAG);
    }

    private void writeVersion() {
        binaryOutput.writeShort(0);
        binaryOutput.writeShort(52);
    }

    private void writeMagicHeader() {
        new Magic().writeTo(binaryOutput);
    }

    private void writeClassAttributes() {
        binaryOutput.writeShort(0);
    }
}
