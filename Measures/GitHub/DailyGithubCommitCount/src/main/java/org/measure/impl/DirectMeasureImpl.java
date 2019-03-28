package org.measure.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;

public class DirectMeasureImpl extends DirectMeasure {
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<>();
        
        String login = getProperty("Login");
        String githubAuthKey = getProperty("GitHubAuth");
        String domain = getProperty("Domain");
        String repository = getProperty("Repository");
        String since = getProperty("Since");
        
        Map<String,Integer> commitCounts = new HashMap<>();
        
        GitHub gh = GitHub.connect(login, githubAuthKey);
        for (PagedIterator localPagedIterator = gh.getRepository(domain + "/" + repository).listCommits().iterator(); localPagedIterator.hasNext();) {
            GHCommit c = (GHCommit) localPagedIterator.next();
            Date date = c.getCommitDate();
            if (date.compareTo(Date.from(Instant.parse(since))) > 0) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String day = df.format(date);
                
                Integer current = commitCounts.get(day);               
                if(current == null){
                    current = 0;
                }              
                commitCounts.put(day, current + 1);
            }
        }
        
        for(Entry<String,Integer> dailyCommit :  commitCounts.entrySet()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(dailyCommit.getKey());
            result.add(new DirectMeasureData(dailyCommit.getValue(),date));
        }
        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'z'");
        getProperties().put("Since", DATE_FORMAT.format(new Date()));
        return result;
    }

}
