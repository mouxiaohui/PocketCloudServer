package com.xiaohui.pocket.system.enums;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTypeEnumTest {

    @Test
    public void getFileTypeCode_test() {
        Integer normalCode = FileTypeEnum.getFileTypeCode("aaa");
        Integer archiveCode = FileTypeEnum.getFileTypeCode(".zip");
        Integer excelCode = FileTypeEnum.getFileTypeCode(".xlsx");
        Integer wordCode = FileTypeEnum.getFileTypeCode(".docx");
        Integer pdfCode = FileTypeEnum.getFileTypeCode(".pdf");
        Integer txtCode = FileTypeEnum.getFileTypeCode(".txt");
        Integer imageCode = FileTypeEnum.getFileTypeCode(".jpg");
        Integer audioCode = FileTypeEnum.getFileTypeCode(".mp3");
        Integer videoCode = FileTypeEnum.getFileTypeCode(".mp4");
        Integer powerPointCode = FileTypeEnum.getFileTypeCode(".pptx");
        Integer sourceCodeCode = FileTypeEnum.getFileTypeCode(".java");
        Integer csvCode = FileTypeEnum.getFileTypeCode(".csv");

        assertEquals(FileTypeEnum.NORMAL_FILE.getCode(), normalCode);
        assertEquals(FileTypeEnum.ARCHIVE_FILE.getCode(), archiveCode);
        assertEquals(FileTypeEnum.EXCEL_FILE.getCode(), excelCode);
        assertEquals(FileTypeEnum.WORD_FILE.getCode(), wordCode);
        assertEquals(FileTypeEnum.PDF_FILE.getCode(), pdfCode);
        assertEquals(FileTypeEnum.TXT_FILE.getCode(), txtCode);
        assertEquals(FileTypeEnum.IMAGE_FILE.getCode(), imageCode);
        assertEquals(FileTypeEnum.AUDIO_FILE.getCode(), audioCode);
        assertEquals(FileTypeEnum.VIDEO_FILE.getCode(), videoCode);
        assertEquals(FileTypeEnum.POWER_POINT_FILE.getCode(), powerPointCode);
        assertEquals(FileTypeEnum.SOURCE_CODE_FILE.getCode(), sourceCodeCode);
        assertEquals(FileTypeEnum.CSV_FILE.getCode(), csvCode);
    }

}
