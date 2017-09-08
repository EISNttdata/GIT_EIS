
package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/4/2017.
 */
public class GlobalVariables
{
    private String category;


  private List<GlobalVariable> globalVariables=new ArrayList<>();

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<GlobalVariable> getGlobalVariables() {
    return globalVariables;
  }


}


