package com.xiaohui.pocket.common.utils;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilsTest {

    @Test
    public void getContentType_test() throws IOException {
        String textCt = FileUtils.getContentType("./test.txt");
        String zipCt = FileUtils.getContentType("./test.zip");
        String pdfCt = FileUtils.getContentType("./test.pdf");
        String jpgCt = FileUtils.getContentType("./test.jpg");
        String pngCt = FileUtils.getContentType("./test.png");
        String mp4Ct = FileUtils.getContentType("./test.mp4");
        String mp3Ct = FileUtils.getContentType("./test.mp3");

        assertEquals("text/plain", textCt);
        assertEquals("application/x-zip-compressed", zipCt);
        assertEquals("application/pdf", pdfCt);
        assertEquals("image/jpeg", jpgCt);
        assertEquals("image/png", pngCt);
        assertEquals("video/mp4", mp4Ct);
        assertEquals("audio/mpeg", mp3Ct);
    }

}
