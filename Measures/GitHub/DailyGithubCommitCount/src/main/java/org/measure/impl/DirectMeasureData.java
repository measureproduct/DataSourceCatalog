package org.measure.impl;

import java.util.Date;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class DirectMeasureData extends DefaultMeasurement {
    public DirectMeasureData(Integer value, Date day) {
        getValues().put("commits", value);
        getValues().put("postDate", day);
    }

}
