package com.mobiquity.packer.processor;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.Package;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PackageProcessor {

    private static final float MAX_WEIGHT = 100;
    private static final int MAX_ITEMS = 15;

    public static List<Item> bestPackageList(Package itemPackage) throws APIException {

        /** Check if the max weight of the package is less than 100 */
        if (itemPackage.getWeight() > MAX_WEIGHT){
            throw new APIException("Max weight exceeded the permissible limit of "+MAX_WEIGHT);
        }

        /** Check if the package contains items more than 15 */
        if (itemPackage.getItems().size() > MAX_ITEMS){
            throw new APIException("Package cannot contain items more than "+MAX_ITEMS);
        }

        /** This is to sort the items in a package based on price (Descending)
         *  and if the price is same then sort based on weight (Ascending) */
        itemPackage.getItems().sort(Comparator.comparing(Item::getPrice).reversed().thenComparing(Item::getWeight));

        float currentWeight = 0;
        float maxWeight = itemPackage.getWeight();
        List<Item> selectedItems = new ArrayList<>();

        for (Item packageItem : itemPackage.getItems()){
            if (packageItem.getWeight() > maxWeight){
                continue;
            }
            float itemWeight = packageItem.getWeight();
            if (currentWeight + itemWeight < maxWeight){
                currentWeight = currentWeight + itemWeight;
                selectedItems.add(packageItem);
            }
        }
        return selectedItems;
    }
}
