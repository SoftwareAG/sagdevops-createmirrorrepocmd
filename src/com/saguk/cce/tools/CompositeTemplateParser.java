package com.saguk.cce.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CompositeTemplateParser {
  private Map<String, ArrayList<String>> yamlTemplateMap;
  private List<String> productList = new ArrayList<String>();  

  /**
   * Create the CompositeTemplateParser instance. 
   * @param yamlParserMap Dynamic data structure generated by SnakeYAML
   */
  @SuppressWarnings("unchecked")
  public CompositeTemplateParser(Map<String, ArrayList<String>> yamlParserMap) {
    yamlTemplateMap = (Map<String, ArrayList<String>>) yamlParserMap.get("templates");
    if (yamlTemplateMap == null) {
      System.err.println("No YAML templates found!");
    }
  }


  /**
   * @return String array containing product names found within the template
   */
  @SuppressWarnings("unchecked")
  public List<String> getProducts() {
    Iterator<ArrayList<String>> templateIterator = yamlTemplateMap.values().iterator();
    Map<String, ArrayList<String>> template = null;
    Map<String, ArrayList<String>> products = null;
    Map<String, ArrayList<String>> integrationServerInstances = null;
    while (templateIterator != null && templateIterator.hasNext()) {
      template = (Map<String, ArrayList<String>>) templateIterator.next();
      products = getProductsFromTemplate(template);
      if (products != null) {
        /**
         * If the Integration Server product exists search if additional products within 'package.list'
         */
        integrationServerInstances = 
            (Map<String, ArrayList<String>>) products.get("integrationServer");
        if (integrationServerInstances != null) {
          addProductsFromIntegrationServerPackageList(integrationServerInstances);
        }
      } else {
        System.err.println("No products found!");
      }
    }

    // Remove duplicate products 
    if (productList != null) {
      productList = productList.stream().distinct().collect(Collectors.toList());
    }
    
    return productList;
  }

  /**
   * @return sub-section of the template array containing the products
   */
  @SuppressWarnings("unchecked")
  private Map<String, ArrayList<String>> getProductsFromTemplate(
      Map<String, ArrayList<String>> templateMap) {
    
    if (templateMap == null) { 
      System.err.println("templateMap is null");
      return null;
    }
    String productName;
    Map<String, ArrayList<String>> productMap = 
        (Map<String, ArrayList<String>>) templateMap.get("products");
    if (productMap != null) {
      Iterator<String> keysIterator = productMap.keySet().iterator();
      while (keysIterator != null && keysIterator.hasNext()) {
        productName = keysIterator.next();
        if (productName != null) {
          productList.add(productName);
        }
      }
    }
    return productMap;
  }

  /**
   * Walk the list of integration Server instances to discover 
   * if any extra products exist within the 'package.list' statement.
   * @param ArrayList of integrationServerInstances  
   */
  @SuppressWarnings("unchecked")
  private void addProductsFromIntegrationServerPackageList(
      Map<String, ArrayList<String>> integrationServerInstances) {
    
    if (integrationServerInstances == null) { 
      return;
    }
    Iterator<ArrayList<String>> instancesIterator = integrationServerInstances.values().iterator();
    Map<String, String> instance = null;
    while (instancesIterator.hasNext()) {
      instance = (Map<String, String>) instancesIterator.next();
      String packageList = instance.get("package.list");
      if (packageList != null) {
        productList.addAll(Arrays.asList(packageList.split(",")));
      }
    }
  }
}
