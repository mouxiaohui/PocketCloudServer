package com.xiaohui.pocket.storage.engine.local;


import com.xiaohui.pocket.config.property.LocalStorageEngineProperties;
import com.xiaohui.pocket.storage.engine.dto.StoreFileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LocalStorageEngineTest {

    @TempDir
    Path tempDir;

    @Mock
    private LocalStorageEngineProperties properties;

    @InjectMocks
    private LocalStorageEngine localStorageEngine;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getRootFilePath()).thenReturn(tempDir.toString());
    }

    @Test
    public void store_ValidContext_FileStoredSuccessfully() throws IOException {
        String filename = "test.txt";
        String content = "Hello, World!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        StoreFileDto context = StoreFileDto.builder()
                .filename(filename)
                .inputStream(inputStream)
                .totalSize((long) content.length()).build();

        String path = localStorageEngine.store(context);

        // 读取文件并验证内容
        String storedContent = Files.readString(Path.of(path));
        assertEquals(content, storedContent);
    }

}
