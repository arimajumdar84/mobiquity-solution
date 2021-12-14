package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;
import com.mobiquity.packer.processor.PackageProcessor;
import com.mobiquity.packer.processor.PackageResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MobiquityPacker {
    
    public static String pack(String filePath) throws APIException{

        List<String> lines;
        StringBuilder sb = new StringBuilder();
        try
        {
            lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            for ( String l : lines ) {
                sb.append(MobiquityPacker.process(l)).append("\n");
            }
        } catch (IOException | APIException e) {
            throw new APIException("Unexpected error occurred in reading the file");
        }
        return sb.toString();
    }

    public static String process(String line) throws APIException{

        /** This will convert each line of the file to package to be processed */
        Package targetPackage = PackageResolver.resolvePackage(line);

        String finalIndexString;
        /** This method will give the best possible list option for the selected package */
        List<Item> finalList = PackageProcessor.bestPackageList(targetPackage);

        if (finalList.isEmpty()){
            return "-";
        }
        /** Sort the indexes in comma-separated sequence */
        finalIndexString = finalList.stream().map(item -> item.getIndex()+"").collect(Collectors.joining(","));

        return finalIndexString;
    }
}
