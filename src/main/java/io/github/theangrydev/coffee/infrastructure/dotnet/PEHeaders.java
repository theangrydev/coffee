package io.github.theangrydev.coffee.infrastructure.dotnet;

import com.google.common.collect.Sets;
import io.github.theangrydev.coffee.infrastructure.Flag;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryWriter;

import java.nio.charset.StandardCharsets;

import static io.github.theangrydev.coffee.infrastructure.dotnet.COMImageFlag.COMIMAGE_FLAGS_ILONLY;
import static io.github.theangrydev.coffee.infrastructure.dotnet.FileCharacteristicsFlag.IMAGE_FILE_32BIT_MACHINE;
import static io.github.theangrydev.coffee.infrastructure.dotnet.FileCharacteristicsFlag.IMAGE_FILE_EXECUTABLE_IMAGE;
import static io.github.theangrydev.coffee.infrastructure.dotnet.SectionCharacteristicsFlag.*;
import static io.github.theangrydev.coffee.infrastructure.dotnet.SubSystem.IMAGE_SUBSYSTEM_WINDOWS_CUI;

/**
 * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=303
 */
public class PEHeaders implements BinaryWriter {

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=304
     */
    private static final int[] MS_DOS_HEADER = new int[]{
            0x4d, 0x5a, 0x90, 0x00, 0x03, 0x00, 0x00, 0x00,
            0x04, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00,
            0xb8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x80, 0x00, 0x00, 0x00,
            0x0e, 0x1f, 0xba, 0x0e, 0x00, 0xb4, 0x09, 0xcd,
            0x21, 0xb8, 0x01, 0x4c, 0xcd, 0x21, 0x54, 0x68,
            0x69, 0x73, 0x20, 0x70, 0x72, 0x6f, 0x67, 0x72,
            0x61, 0x6d, 0x20, 0x63, 0x61, 0x6e, 0x6e, 0x6f,
            0x74, 0x20, 0x62, 0x65, 0x20, 0x72, 0x75, 0x6e,
            0x20, 0x69, 0x6e, 0x20, 0x44, 0x4f, 0x53, 0x20,
            0x6d, 0x6f, 0x64, 0x65, 0x2e, 0x0d, 0x0d, 0x0a,
            0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=304
     */
    private static final int[] PE_SIGNATURE = {
            0x50, 0x45, 0x00, 0x00
    };

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=305
     */
    private static final int PE_OPTIONAL_HEADER_SIZE = 28 + 68 + 128;

    private static final int OS_MAJOR = 4;
    private static final int OS_MINOR = 0;

    private static final int USER_MAJOR = 0;
    private static final int USER_MINOR = 0;

    private static final int SUB_SYS_MAJOR = 4;
    private static final int SUB_SYS_MINOR = 0;

    private static final int STACK_RESERVE_SIZE = 0x100000;
    private static final int STACK_COMMIT_SIZE = 0x1000;
    private static final int HEAP_RESERVE_SIZE = 0x100000;
    private static final int HEAP_COMMIT_SIZE = 0x1000;

    private static final int LOADER_FLAGS = 0;
    private static final int NUMBER_OF_DATA_DIRECTORIES = 0x10;
    private static final long EXPORT_TABLE = 0L;
    private static final long EXCEPTION_TABLE = 0L;
    private static final long CERTIFICATE_TABLE = 0L;
    private static final long DEBUG = 0L;
    private static final long COPYRIGHT = 0L;
    private static final long GLOBAL_PTR = 0L;
    private static final long TLS_TABLE = 0L;
    private static final long LOAD_CONFIG_TABLE = 0L;
    private static final long BOUND_IMPORT = 0L;
    private static final long DELAY_IMPORT_DESCRIPTOR = 0L;
    private static final int POINTER_TO_RELOCATIONS = 0;
    private static final int POINTER_TO_LINENUMBERS = 0;
    private static final int NUMBER_OF_RELOCATIONS = 0;
    private static final int NUMBER_OF_LINENUMBERS = 0;

    private static final int RESERVED_INT = 0;
    private static final long RESERVED_LONG = 0L;

    private static final int INT_PADDING = 0;
    private static final int SHORT_PADDING = 0;

    private static final int CLI_HEADER_SIZE = 72;
    private static final int CLI_HEADER_MAJOR_RUNTIME_VERSION = 2;
    private static final int CLI_HEADER_MINOR_RUNTIME_VERSION = 5;
    private static final long CLI_HEADER_RESOURCES = 0L;
    private static final long CLI_HEADER_STRONG_NAME_SIGNATURE = 0L;
    private static final long CLI_HEADER_CODE_MANAGER_TABLE = 0L;
    private static final long CLI_HEADER_V_TABLE_FIXUPS = 0L;
    private static final long CLI_HEADER_EXPORT_ADDRESS_TABLE_JUMPS = 0L;

    private static final long CLI_HEADER_MANAGED_NATIVE_HEADER = 0L;
    private static final int IMPORT_TABLE_DATE_TIME_STAMP = 0;

    private static final int IMPORT_TABLE_FORWARDER_CHAIN = 0;

    private static final int CLI_METADATA_SIGNATURE = 0x424A5342;
    private static final int CLI_METADATA_MAJOR_VERSION = 1;
    private static final int CLI_METADATA_MINOR_VERSION = 1;
    private static final int CLI_METADATA_FLAGS = 0;

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeBytes(MS_DOS_HEADER);
        binaryOutput.writeBytes(PE_SIGNATURE);
        writePEHeader(binaryOutput);
        writePEOptionalHeader(binaryOutput);
        writeSectionHeaders(binaryOutput);
        writeTextSection(binaryOutput);
    }

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=303
     */
    private void writePEHeader(BinaryOutput binaryOutput) {
        writeMachine(binaryOutput);
        writeNumberOfSections(binaryOutput);
        writeEpochCreationTime(binaryOutput);
        writePointerToSymbolTable(binaryOutput);
        writeNumberOfSymbols(binaryOutput);
        writeOptionalHeaderSize(binaryOutput);
        writeFileCharacteristics(binaryOutput);
    }

    private void writeMachine(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(0x14c);
    }

    private void writeNumberOfSections(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(3);
    }

    private void writeEpochCreationTime(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0); // opt-out of writing the creation time by writing 0
    }

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=297
     */
    private void writePointerToSymbolTable(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0);
    }

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=297
     */
    private void writeNumberOfSymbols(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0);
    }

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=305
     */
    private void writeOptionalHeaderSize(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(PE_OPTIONAL_HEADER_SIZE);
    }

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=305
     */
    private void writeFileCharacteristics(BinaryOutput binaryOutput) {
        int flags = Flag.combine(Sets.newHashSet(IMAGE_FILE_EXECUTABLE_IMAGE, IMAGE_FILE_32BIT_MACHINE));
        binaryOutput.writeShort(flags);
    }

    /**
     * http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-335.pdf#page=305
     */
    private void writePEOptionalHeader(BinaryOutput binaryOutput) {
        writeStandardFields(binaryOutput);
        writeNTSpecificFields(binaryOutput);
        writeDataDirectories(binaryOutput);
    }

    private void writeStandardFields(BinaryOutput binaryOutput) {
        writeMagic(binaryOutput);
        writeLMajor(binaryOutput);
        writeLMinor(binaryOutput);
        writeCodeSize(binaryOutput);
        writeInitializedDataSize(binaryOutput);
        writeUninitializedDataSize(binaryOutput);
        writeEntryPointRVA(binaryOutput);
        writeBaseOfCode(binaryOutput);
        writeBaseOfData(binaryOutput);
    }

    private void writeMagic(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(0x10B);
    }

    /**
     * Even though ECMA-335 says this should be "Always 6", Mono mcs writes 8 so I am following that.
     */
    private void writeLMajor(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(8);
    }

    private void writeLMinor(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(0);
    }

    private void writeCodeSize(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(1024); //TODO: compute
    }

    private void writeInitializedDataSize(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(1536); //TODO: compute
    }

    private void writeUninitializedDataSize(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0); //TODO: compute
    }

    /**
     * http://www.sunshine2k.de/reversing/tuts/tut_ci_dotnet_msgbox/tut_ci_dotnet_msgbox.html
     */
    private void writeEntryPointRVA(BinaryOutput binaryOutput) {
        // TODO: entry point should be 0xFF 0x25 0x00 0x20 0x40 0x00 which is a jump to mscoree._CorExeMain
        binaryOutput.writeInt(8942); //TODO: compute
        //0x0A8
    }

    private void writeBaseOfCode(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(8192);//TODO: compute
    }

    private void writeBaseOfData(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(16384); //TODO: compute
    }

    private void writeNTSpecificFields(BinaryOutput binaryOutput) {
        writeImageBase(binaryOutput);
        writeSectionAlignment(binaryOutput);
        writeFileAlignment(binaryOutput);
        writeOSMajor(binaryOutput);
        writeOSMinor(binaryOutput);
        writeUserMajor(binaryOutput);
        writeUserMinor(binaryOutput);
        writeSubSysMajor(binaryOutput);
        writeSubSysMinor(binaryOutput);
        binaryOutput.writeInt(RESERVED_INT);
        writeImageSize(binaryOutput);
        writeHeaderSize(binaryOutput);
        writeFileChecksum(binaryOutput);
        writeSubSystem(binaryOutput);
        writeDLLFlags(binaryOutput);
        binaryOutput.writeInt(STACK_RESERVE_SIZE);
        binaryOutput.writeInt(STACK_COMMIT_SIZE);
        binaryOutput.writeInt(HEAP_RESERVE_SIZE);
        binaryOutput.writeInt(HEAP_COMMIT_SIZE);
        binaryOutput.writeInt(LOADER_FLAGS);
        binaryOutput.writeInt(NUMBER_OF_DATA_DIRECTORIES);
    }

    private void writeImageBase(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0x400000); //TODO: compute
    }

    private void writeSectionAlignment(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0x2000); //TODO: compute
    }

    private void writeFileAlignment(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0x200);
    }

    /**
     * Even though ECMA-335 says this "Should be 5", Mono mcs writes 4 so I am following that.
     */
    private void writeOSMajor(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(OS_MAJOR);
    }

    private void writeOSMinor(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(OS_MINOR);
    }

    private void writeUserMajor(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(USER_MAJOR);
    }

    private void writeUserMinor(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(USER_MINOR);
    }

    private void writeSubSysMajor(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(SUB_SYS_MAJOR);
    }

    /**
     * Even though ECMA-335 says this "Should be 5", Mono mcs writes 4 so I am following that.
     */
    private void writeSubSysMinor(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(SUB_SYS_MINOR);
    }

    private void writeImageSize(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0x8000); //TODO: write size of image including headers and padding
    }

    private void writeHeaderSize(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0x200);
    }

    private void writeFileChecksum(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(0);
    }

    private void writeSubSystem(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(IMAGE_SUBSYSTEM_WINDOWS_CUI.value);
    }

    /**
     * ECMA-335 says that "Bits 0x100f shall be zero".
     * Mono mcs writes 0x8540 so I am following that.
     */
    private void writeDLLFlags(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(0x8540);
    }

    private void writeDataDirectories(BinaryOutput binaryOutput) {
        binaryOutput.writeLong(EXPORT_TABLE);
        writeImportTableHeader(binaryOutput);
        writeResourceTableHeader(binaryOutput);
        binaryOutput.writeLong(EXCEPTION_TABLE);
        binaryOutput.writeLong(CERTIFICATE_TABLE);
        writeBaseRelocationTableHeader(binaryOutput);
        binaryOutput.writeLong(DEBUG);
        binaryOutput.writeLong(COPYRIGHT);
        binaryOutput.writeLong(GLOBAL_PTR);
        binaryOutput.writeLong(TLS_TABLE);
        binaryOutput.writeLong(LOAD_CONFIG_TABLE);
        binaryOutput.writeLong(BOUND_IMPORT);
        writeImportAddressTableHeader(binaryOutput);
        binaryOutput.writeLong(DELAY_IMPORT_DESCRIPTOR);
        writeCLIHeaderTableHeader(binaryOutput);
        binaryOutput.writeLong(RESERVED_LONG);
    }

    private void writeImportTableHeader(BinaryOutput binaryOutput) {
        int importTableRVA = 0x22a0;
        int importTableSize = 75;
        writeRVAAndSize(binaryOutput, importTableRVA, importTableSize);
    }

    private void writeResourceTableHeader(BinaryOutput binaryOutput) {
        int resourceTableRVA = 0x4000;
        int resourceTableSize = 752;
        writeRVAAndSize(binaryOutput, resourceTableRVA, resourceTableSize);
    }

    private void writeBaseRelocationTableHeader(BinaryOutput binaryOutput) {
        int baseRelocationTableRVA = 0x6000;
        int baseRelocationTableSize = 12;
        writeRVAAndSize(binaryOutput, baseRelocationTableRVA, baseRelocationTableSize);
    }

    private void writeImportAddressTableHeader(BinaryOutput binaryOutput) {
        int importAddressTableRVA = 0x2000;
        int importAddressTableSize = 8;
        writeRVAAndSize(binaryOutput, importAddressTableRVA, importAddressTableSize);
    }

    private void writeCLIHeaderTableHeader(BinaryOutput binaryOutput) {
        int cliHeaderTableRVA = 0x2008;
        int cliHeaderTableSize = 72;
        writeRVAAndSize(binaryOutput, cliHeaderTableRVA, cliHeaderTableSize);
    }

    private void writeRVAAndSize(BinaryOutput binaryOutput, int tableRVA, int tableSize) {
        binaryOutput.writeInt(tableRVA); //TODO: compute
        binaryOutput.writeInt(tableSize); //TODO: compute
    }

    private void writeSectionHeaders(BinaryOutput binaryOutput) {
        writeTextSectionHeader(binaryOutput);
        writeRsrcSectionHeader(binaryOutput);
        writeRelocSectionHeader(binaryOutput);
        //0x1f0
    }

    private void writeTextSectionHeader(BinaryOutput binaryOutput) {
        int virtualSize = 756; //TODO: compute
        int virtualAddress = 0x2000; //TODO: compute
        int sizeOfRawData = 0x400; //TODO: compute
        int pointerToRawData = 0x200; //TODO: compute
        int flags = Flag.combine(Sets.newHashSet(IMAGE_SCN_CNT_CODE, IMAGE_SCN_MEM_EXECUTE, IMAGE_SCN_MEM_READ));
        writeSectionHeader(binaryOutput, virtualSize, virtualAddress, sizeOfRawData, pointerToRawData, flags, ".text");
    }

    private void writeRsrcSectionHeader(BinaryOutput binaryOutput) {
        int virtualSize = 752; //TODO: compute
        int virtualAddress = 0x4000; //TODO: compute
        int sizeOfRawData = 0x400; //TODO: compute
        int pointerToRawData = 0x600; //TODO: compute
        int flags = Flag.combine(Sets.newHashSet(IMAGE_SCN_MEM_READ, IMAGE_SCN_CNT_INITIALIZED_DATA));
        writeSectionHeader(binaryOutput, virtualSize, virtualAddress, sizeOfRawData, pointerToRawData, flags, ".rsrc");
    }

    private void writeRelocSectionHeader(BinaryOutput binaryOutput) {
        int virtualSize = 12; //TODO: compute
        int virtualAddress = 0x6000; //TODO: compute
        int sizeOfRawData = 0x200; //TODO: compute
        int pointerToRawData = 0xA00; //TODO: compute
        int flags = Flag.combine(Sets.newHashSet(IMAGE_SCN_MEM_READ, IMAGE_SCN_CNT_INITIALIZED_DATA, IMAGE_SCN_MEM_DISCARDABLE));
        writeSectionHeader(binaryOutput, virtualSize, virtualAddress, sizeOfRawData, pointerToRawData, flags, ".reloc");
    }

    private void writeSectionHeader(BinaryOutput binaryOutput, int virtualSize, int virtualAddress, int sizeOfRawData, int pointerToRawData, int flags, String ascii) {
        binaryOutput.writeASCII(ascii, 8);
        binaryOutput.writeInt(virtualSize);
        binaryOutput.writeInt(virtualAddress);
        binaryOutput.writeInt(sizeOfRawData);
        binaryOutput.writeInt(pointerToRawData);
        binaryOutput.writeInt(POINTER_TO_RELOCATIONS);
        binaryOutput.writeInt(POINTER_TO_LINENUMBERS);
        binaryOutput.writeShort(NUMBER_OF_RELOCATIONS);
        binaryOutput.writeShort(NUMBER_OF_LINENUMBERS);
        binaryOutput.writeInt(flags);
    }

    private void writeTextSection(BinaryOutput binaryOutput) {
        writeImportAddressTable(binaryOutput);
        writeCLIHeader(binaryOutput);
        //TODO: what is inbetween!?
        writeCLIMetaData(binaryOutput);
        //TODO: what is inbetween!?
        writeImportTable(binaryOutput);
        //TODO: what is inbetween!?
        writeHintNameTable(binaryOutput);
        binaryOutput.writeNullTerminatedASCII("mscoree.dll");
    }

    private void writeHintNameTable(BinaryOutput binaryOutput) {
        //0x4D0
        binaryOutput.writeShort(SHORT_PADDING);
        binaryOutput.writeNullTerminatedASCII("_CorExeMain");
    }

    private void writeImportAddressTable(BinaryOutput binaryOutput) {
        int hintNameTableRVA = 0x22D0;
        binaryOutput.writeInt(hintNameTableRVA);
        binaryOutput.writeInt(INT_PADDING);
    }

    private void writeCLIHeader(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(CLI_HEADER_SIZE);
        binaryOutput.writeInt(CLI_HEADER_MAJOR_RUNTIME_VERSION);
        binaryOutput.writeInt(CLI_HEADER_MINOR_RUNTIME_VERSION);
        int metaDataRVA = 0x2064; //TODO: compute
        int metaDataSize = 572; //TODO: compute
        writeRVAAndSize(binaryOutput, metaDataRVA, metaDataSize);
        binaryOutput.writeInt(COMIMAGE_FLAGS_ILONLY.value());
        int entryPointToken = 0x06000002; //MethodDef 0x06 row 0x02 TODO: compute
        binaryOutput.writeInt(entryPointToken);
        binaryOutput.writeLong(CLI_HEADER_RESOURCES);
        binaryOutput.writeLong(CLI_HEADER_STRONG_NAME_SIGNATURE);
        binaryOutput.writeLong(CLI_HEADER_CODE_MANAGER_TABLE);
        binaryOutput.writeLong(CLI_HEADER_V_TABLE_FIXUPS);
        binaryOutput.writeLong(CLI_HEADER_EXPORT_ADDRESS_TABLE_JUMPS);
        binaryOutput.writeLong(CLI_HEADER_MANAGED_NATIVE_HEADER);
    }

    private void writeCLIMetaData(BinaryOutput binaryOutput) {
        binaryOutput.writeInt(CLI_METADATA_SIGNATURE);
        binaryOutput.writeShort(CLI_METADATA_MAJOR_VERSION);
        binaryOutput.writeShort(CLI_METADATA_MINOR_VERSION);
        binaryOutput.writeInt(RESERVED_INT);
        int versionStringLengthIncludingNullTerminatorRoundedUpToMultipleOfFour = 12;
        binaryOutput.writeInt(versionStringLengthIncludingNullTerminatorRoundedUpToMultipleOfFour);
        //TODO: where does this number come from?? how to write as UTF-8?
//        byte[] bytes = new String("v4.0.30319").getBytes(StandardCharsets.UTF_8);

        binaryOutput.writeASCII("v4.0.30319", versionStringLengthIncludingNullTerminatorRoundedUpToMultipleOfFour);
        binaryOutput.writeShort(CLI_METADATA_FLAGS);
        int streams = 5;
        binaryOutput.writeShort(streams);
        //TODO: write the 5 StreamHdr structures
    }

    private void writeImportTable(BinaryOutput binaryOutput) {
        int importLookupTableRVA = 0x22C8; //TODO: compute
        binaryOutput.writeInt(importLookupTableRVA);
        binaryOutput.writeInt(IMPORT_TABLE_DATE_TIME_STAMP);
        binaryOutput.writeInt(IMPORT_TABLE_FORWARDER_CHAIN);
        int mscoreeedllNameRVA = 0x22DE; //TODO: compute
        binaryOutput.writeInt(mscoreeedllNameRVA);
        int importAddressTableRVA = 0x2000; //TODO: compute
        binaryOutput.writeInt(importAddressTableRVA);
        binaryOutput.writeZeroPadding(20);
    }
}
