package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class MobiquityPackerTest {

    private List<String> validInputLines;
    private List<String> inputLinesWeightExceeded;
    private List<String> inputLinesItemsExceeded;


    @Before
    public void setup() {
        validInputLines = readLinesFromResource("test_sample.txt");
        inputLinesWeightExceeded = readLinesFromResource("test_sample_maxweight.txt");
        inputLinesItemsExceeded = readLinesFromResource("test_sample_maxitems.txt");
    }

    @Test
    public void packTest_ok() throws APIException {

        StringBuilder selectedPackage = new StringBuilder();
        for (String line : validInputLines){
            final String output = MobiquityPacker.process(line);
            selectedPackage.append(output).append("\n");
        }
        System.out.println(selectedPackage.toString());
        Assert.assertNotNull(selectedPackage.toString());
    }

    @Test
    public void packTest_maxWeightExceeded_ThrowAPIException(){

        for (String line : inputLinesWeightExceeded){
            APIException exception = assertThrows(APIException.class, () -> {
                MobiquityPacker.process(line);
            });
            assertEquals("Max weight exceeded the permissible limit of 100.0", exception.getMessage());
        }
    }

    @Test
    public void packTest_maxItemsExceeded_ThrowAPIException(){

        for (String line : inputLinesItemsExceeded){
            APIException exception = assertThrows(APIException.class, () -> {
                MobiquityPacker.process(line);
            });
            assertEquals("Package cannot contain items more than 15", exception.getMessage());
        }
    }

    private List<String> readLinesFromResource(String filename) {
        ClassLoader  loader = MobiquityPackerTest.class.getClassLoader();
        List<String> lines;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    loader.getResourceAsStream(filename), "UTF-8"
            ));
            lines = br.lines().parallel().map(Object::toString).collect(Collectors.toList());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lines = null;
        }
        return lines;
    }
}
