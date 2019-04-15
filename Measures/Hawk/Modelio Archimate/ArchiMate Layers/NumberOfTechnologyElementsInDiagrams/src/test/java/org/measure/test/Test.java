package org.measure.test;

import org.measure.smm.measure.api.IMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("serverUrl", "http://localhost:8080/thrift/hawk/tuple");
        measure.getProperties().put("instanceName", "DataBioAgro");
        measure.getProperties().put("repository", "*");


        measure.getProperties().put("queryField", "return Element.latest.all.select(e|not e.revRefNav_Represented.isEmpty and e.hawkParent.isKindOf(TechnologyFolder)).size;");

        
        measure.getProperties().put("defaultNamespaces", "modelio://Modeliosoft.Archimate/1.0.02");


        try {
            for(IMeasurement result : measure.getMeasurement()){
                System.out.println("Test Result : " + result.getLabel());
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
