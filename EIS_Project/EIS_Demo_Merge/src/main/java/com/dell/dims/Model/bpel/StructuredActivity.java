package com.dell.dims.Model.bpel;

/**
 * Created by Pramod_Kumar_Tyagi on 8/1/2017.
 */
public class StructuredActivity  extends Activity{

  public Sequence sequence;
@Override
public BpelActivityContainerType getBpelActivityContainerType() {
    return BpelActivityContainerType.STRUCTURED;
  }
  @Override
  public Sequence getActivityContainer()
  {
    return sequence;
  }

  public void setSequence(Sequence sequence) {
    this.sequence = sequence;
  }
}
