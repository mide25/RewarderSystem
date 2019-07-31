package com.takehome.rewarder;

import com.takehome.rewarder.util.FileExtractionUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertNotNull;

public class FileExtractionUtilTest {

    @Test
    public void extractFile() throws Exception {
        String filePath = "samples/test-rewarder-upload.csv";
        File file = new File(filePath);
        List<String[]> output = FileExtractionUtil.extractCsvAsListArray(new FileInputStream(file));
        assertNotNull(output);
    }

    @Test
    public void testExtractEmptyFile() throws Exception {
        String filePath = "samples/emptyCsv.csv";
        File file = new File(filePath);
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> FileExtractionUtil.extractCsvAsListArray(new FileInputStream(file))).withMessage("Empty File!");
    }
}
