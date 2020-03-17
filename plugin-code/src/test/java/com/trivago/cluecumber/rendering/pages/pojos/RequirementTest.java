package com.trivago.cluecumber.rendering.pages.pojos;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class RequirementTest {

    @Test
    public void reqTree()  {
        Requirement root = new Requirement("", 0, "");
        Feature f1 = new Feature("f1", "", 1);
        Requirement r1 = root.getSubRequirement("features/test/f1.feature");
        r1.addFeature(f1);
        Requirement r2 = root.getSubRequirement("features/test/f2.feature");
        r2.addFeature(f1);
        assertThat(r1 == r2, is(true));
        assertThat(r1 == root, is(false));
        assertThat(r1.getFeatures().size(),  is(equalTo(1)));
        assertThat(root.getFeatures().size(),  is(equalTo(0)));
        r2 = root.getSubRequirement("features/test2/f2.feature");
        assertThat(r1 == r2, is(false));
        assertThat(r2 == root, is(false));
        // root contains only "features"
        assertThat(root.getChildren().size() , is(equalTo(1)));
        // and "features" contains the 2 sub-reqs "test" and "test2"
        assertThat(root.getChildren().iterator().next().getChildren().size() , is(equalTo(2)));
    }

    @Test
    public void reqCount()  {
        Requirement root = new Requirement("", 0, "");
        Feature ff = new Feature("f1", "", 1);
        ResultCount rf = new ResultCount();
        ff.setResultcount(rf);
        rf.addFailed(1);
        Feature fs = new Feature("f2", "", 2);
        ResultCount rs = new ResultCount();
        fs.setResultcount(rs);
        rs.addSkipped(1);
        Feature fp = new Feature("f3", "", 3);
        ResultCount rp = new ResultCount();
        fp.setResultcount(rp);
        rp.addPassed(1);
        Requirement rqf = root.getSubRequirement("features/test/failed/f1.feature");
        rqf.addFeature(ff);
        Requirement rqp = root.getSubRequirement("features/test/passed/f3.feature");
        rqp.addFeature(fp);
        Requirement rqs = root.getSubRequirement("features/test/skipped/f2.feature");
        rqs.addFeature(fs);
        root.computeResultCount();
        assertThat(root.getCount().getPassed(), is(equalTo(1)));
        assertThat(root.getCount().getFailed(), is(equalTo(1)));
        assertThat(root.getCount().getSkipped(), is(equalTo(1)));
    }
}