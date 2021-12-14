package com.mobiquity.packer.processor;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PackageResolver {

    public static Package resolvePackage(String line) throws APIException {

        line = line.replace(" ", ""); //Trim all the whitespaces
        String[] packageAttributes = line.split(":");

        int maxWeight = Integer.parseInt(packageAttributes[0]);

        List<String> packageItemStrLst = Arrays.asList(packageAttributes[1].split("[\\(\\)]"));
        packageItemStrLst = packageItemStrLst.stream().filter(str -> !str.isEmpty()).collect(Collectors.toList());

        if (packageItemStrLst.isEmpty()){
            throw new APIException("The package is empty");
        }
        List<Item> itemsList = new ArrayList<>();
        for ( String itemString : packageItemStrLst ){

            String[] itemAttributes = itemString.replace("(", "").replace(")", "").split(",");

            itemsList.add(Item.builder()
                    .index(Integer.parseInt(itemAttributes[0]))
                    .weight(Float.parseFloat(itemAttributes[1]))
                    .price(new BigDecimal(itemAttributes[2].replace("€", "")))
                    .build());
        }
        if (!itemsList.isEmpty()){
            return Package.builder().weight(maxWeight).items(itemsList).build();
        }
        return null;
    }
}
