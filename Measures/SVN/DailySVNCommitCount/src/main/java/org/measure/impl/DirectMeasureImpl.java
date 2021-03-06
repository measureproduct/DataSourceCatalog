package org.measure.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnLog;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRevisionRange;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public class DirectMeasureImpl extends DirectMeasure {
    private Date from;

    Map<String, Integer> commitCounts = new HashMap<>();

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        try {
        
            List<IMeasurement> result = new ArrayList<>();
        
            String uri = getProperty("url");
            String login = getProperty("login");
            String password = getProperty("password");
            Long lastUpdate = new Long(0);
            try {
                lastUpdate = Long.valueOf(getProperty("lastExecution"));
            } catch (Exception e) {
            }
        
            final SVNURL svnurl = SVNURL.parseURIEncoded(uri.toString());
            svnOperationFactory.setAuthenticationManager(new BasicAuthenticationManager(login, password));
        
            final SvnLog log = svnOperationFactory.createLog();
        
            if (lastUpdate != 0) {
                from = new Date(lastUpdate);
                log.addRange(SvnRevisionRange.create(SVNRevision.create(from), SVNRevision.create(new Date())));
            } else {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.YEAR, -1);
                from = c.getTime();
                log.addRange(SvnRevisionRange.create(SVNRevision.create(from), SVNRevision.create(new Date())));
        
            }
            log.setDiscoverChangedPaths(true);
            log.setSingleTarget(SvnTarget.fromURL(svnurl));
        
            log.setReceiver(new ISvnObjectReceiver<SVNLogEntry>() {
                @Override
                public void receive(SvnTarget target, SVNLogEntry logEntry) throws SVNException {
                    if (logEntry.getDate().getTime() > DirectMeasureImpl.this.from.getTime()) {
        
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String day = df.format(logEntry.getDate());
        
                        Integer current = commitCounts.get(day);
                        if (current == null) {
                            current = 0;
                        }
                        commitCounts.put(day, current + 1);
        
                    }
                }
            });
            log.run();
        
            for (Entry<String, Integer> dailyCommit : commitCounts.entrySet()) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = df.parse(dailyCommit.getKey());
                result.add(new DirectMeasureData(dailyCommit.getValue(), date));
            }
        
            getProperties().put("lastExecution", new Long(new Date().getTime()).toString());
            return result;
        } catch (SVNException e) {
            throw e;
        } finally {
            svnOperationFactory.dispose();
        }
    }

}
