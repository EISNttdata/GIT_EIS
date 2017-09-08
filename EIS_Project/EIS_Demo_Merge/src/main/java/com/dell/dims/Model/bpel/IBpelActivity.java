package com.dell.dims.Model.bpel;

/**
 * Created by Pramod_Kumar_Tyagi on 8/1/2017.
 */
public interface IBpelActivity {

  default BpelActivityContainerType getBpelActivityContainerType() {
    return BpelActivityContainerType.BASIC;
  }

  default Sequence getActivityContainer(){
    {
      return null;
    }
  }

}
