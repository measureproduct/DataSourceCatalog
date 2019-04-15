package org.measure.test;

import org.measure.smm.measure.api.IMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("serverUrl", "http://localhost:8080/thrift/hawk/tuple");
        measure.getProperties().put("instanceName", "DataBioAgro");
        measure.getProperties().put("repository", "*");


        measure.getProperties().put("queryField", "var listElements=Element.latest.all.select(e|e.Name.length>0).collect(c|Sequence{c.Name, Model.getTypeOf(c).name}); var listSize=listElements.size;var nbDuplicated=0;for (i in Sequence{1..(listSize-2)}){for(j in Sequence{(i+1)..(listSize-1)}){if(listElements.at(i).first()=listElements.at(j).first() and listElements.at(i).second()=listElements.at(j).second()){nbDuplicated++;break;}}}return nbDuplicated.asDouble()/listSize.asDouble();");

        
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
