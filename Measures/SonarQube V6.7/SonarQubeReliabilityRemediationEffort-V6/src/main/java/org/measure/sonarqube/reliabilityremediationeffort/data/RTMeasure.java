package org.measure.sonarqube.reliabilityremediationeffort.data;

import java.util.List;

public class RTMeasure {
    private List<RTHistory> history;

    public List<RTHistory> getHistory() {
        
        return history;
    }

    public void setHistory(List<RTHistory> history) {
        this.history = history;
    }

}