package org.measure.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.measure.impl.DirectMeasureImpl;
import org.measure.smm.measure.api.IMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
    	DirectMeasureImpl impl = new DirectMeasureImpl();
    	impl.getProperties().put("MaxRange","100");
    	impl.getProperties().put("MinRange","0");
    	impl.getProperties().put("Delta","5");
    	impl.getProperties().put("Value","50");
    	
    	List<IMeasurement> result = null;
    	try {
    		result = impl.getMeasurement();

    	}catch (Exception e) {
    		e.printStackTrace();;
    	}
    	
    	
    	assertNotNull(result);
    	assertNotEquals(0, result.size());
    	assertEquals(50, Integer.valueOf( (Integer)result.get(0).getValues().get("value")), 5);
    }

}
