package org.measure.test;

import org.measure.smm.measure.api.IMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("serverUrl", "http://localhost:8080/thrift/hawk/tuple");
        measure.getProperties().put("instanceName", "DataBioAgro");
        measure.getProperties().put("repository", "*");


        measure.getProperties().put("queryField", "var lst=ArchimateView.latest.all.select(v|v.Represented.size>0); return lst.collect(v|v.Descriptor.collect(d|d.Content.split('\\\\W+').size).sum()+v.Represented.select(r|r.isKindOf(Element)).collect(c| c.Descriptor.collect(n|n.Content.split('\\\\W+').size).sum()).sum()).sum()/lst.collect(v|1+v.Represented.select(r|r.isKindOf(Element)).size).sum();");

        
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
