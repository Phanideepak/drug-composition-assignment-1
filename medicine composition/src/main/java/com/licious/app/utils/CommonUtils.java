package com.licious.app.utils;

import com.licious.app.dto.IngredientDetails;

import java.util.List;

/*
  This CommonUtils class provides the methods for generating molecule name and composition name
  from the ingredient details.
 */
public class CommonUtils {
    public static String createMoleculeName(List<String> ingredientName){
        String moleculeName="";
        for(int i=0;i<ingredientName.size();i++){
            if(i!=ingredientName.size()-1)
                moleculeName=moleculeName+ingredientName.get(i)+" + ";
            else
                moleculeName=moleculeName+ingredientName.get(i);
        }
        return  moleculeName;
    }
    public static String createCompositionName(List<IngredientDetails> ingredientDetailsList){
         String compositionName="";
         for(int i=0;i<ingredientDetailsList.size();i++){
             if(i!=ingredientDetailsList.size()-1){
                 compositionName=compositionName+ingredientDetailsList.get(i).getName()
                         +" ("+ingredientDetailsList.get(i).getStrength()
                         +ingredientDetailsList.get(i).getUnit()+")"+" + ";
             }
             else{
                 compositionName=compositionName+ingredientDetailsList.get(i).getName()
                         +" ("+ingredientDetailsList.get(i).getStrength()
                         +ingredientDetailsList.get(i).getUnit()+")";
             }
         }
        return compositionName;
    }
}
