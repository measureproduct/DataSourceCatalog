package org.measure.test;

import org.measure.smm.measure.api.IMeasurement;

public class Test {

    @org.junit.Test
    public void testMeasure() {
        org.measure.impl.DirectMeasureImpl measure = new org.measure.impl.DirectMeasureImpl();
        measure.getProperties().put("serverUrl", "http://localhost:8080/thrift/hawk/tuple");
        measure.getProperties().put("instanceName", "DataBioAgro");
        measure.getProperties().put("repository", "*");


        measure.getProperties().put("queryField", "return ArchimateView.latest.all.select(v|v.Represented.exists(c|c.isKindOf(Element))).collect(v|v.Represented.select(c|c.isKindOf(Element)).collect(e|e.o3()*e.o2()).sum()).o1(); operation Bag o1(){return self.sortBy(i|i).at(self.size/2);} operation Element o2(){if(self.relatedTo.isEmpty()){return 0.0;} else {return self.relatedTo.collect(a|a.o4()).sum().asDouble()/self.relatedTo.size.asDouble();}} operation Element o3(){var s=0;if(self.hawkParent.isKindOf(TechnologyFolder)){s=1;}else if(self.hawkParent.isKindOf(ApplicationFolder)){s=2;}else if(self.hawkParent.isKindOf(BusinessFolder)){s=3;}if(self.isKindOf(ActiveStructureElement)){s+3;}else if(self.isKindOf(BehaviorElement)){s+6;}return s;}operation Relationship o4(){switch (Model.getTypeOf(self).name){case 'Composition':return 8;case 'Aggregation':return 7;case 'Assignment':return 6;case 'Realization':return 5;case 'Serving':return 8;case 'Access':return 3;case 'Triggering':return 2;case 'Flow':return 1;default:return 0;}}");

        
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
