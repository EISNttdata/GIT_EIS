package com.dell.dims.Builder;

import com.dell.dims.Model.ActivityType;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public interface ActivityBuilderInterface
{
    Object getBuilder(ActivityType activityType) throws Exception;
}
