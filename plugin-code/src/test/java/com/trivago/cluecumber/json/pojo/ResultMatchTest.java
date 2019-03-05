package com.trivago.cluecumber.json.pojo;

import com.trivago.cluecumber.constants.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResultMatchTest {
    private ResultMatch resultMatch;

    @Before
    public void setup() {
        resultMatch = new ResultMatch();
    }

    @Test
    public void glueMethodNameTest() {
        Match match = new Match();
        match.setLocation("someMethod");
        resultMatch.setMatch(match);
        assertThat(resultMatch.getGlueMethodName(), is("someMethod"));
    }

    @Test
    public void argumentsTest() {
        Match match = new Match();
        List<Argument> arguments = new ArrayList<>();
        Argument argument = new Argument();
        argument.setVal("arg1");
        argument.setOffset(10);
        arguments.add(argument);
        match.setArguments(arguments);
        resultMatch.setMatch(match);
        assertThat(resultMatch.getArguments().size(), is(1));
        assertThat(resultMatch.getArguments().get(0).getVal(), is("arg1"));
        assertThat(resultMatch.getArguments().get(0).getOffset(), is(10));
    }

    @Test
    public void getStatusStringTest() {
        Result result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.getStatusString(), is("skipped"));
    }

    @Test
    public void isFailedTest() {
        Result result = new Result();
        result.setStatus(Status.FAILED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.isFailed(), is(true));
        assertThat(resultMatch.isPassed(), is(false));
        assertThat(resultMatch.isSkipped(), is(false));
    }

    @Test
    public void isPassedTest() {
        Result result = new Result();
        result.setStatus(Status.PASSED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.isFailed(), is(false));
        assertThat(resultMatch.isPassed(), is(true));
        assertThat(resultMatch.isSkipped(), is(false));
    }

    @Test
    public void isSkippedTest() {
        Result result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.isFailed(), is(false));
        assertThat(resultMatch.isPassed(), is(false));
        assertThat(resultMatch.isSkipped(), is(true));
    }

    @Test
    public void getConsolidatedStatusTest() {
        Result result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.getConsolidatedStatus(), is(Status.SKIPPED));
        assertThat(resultMatch.getConsolidatedStatusString(), is("skipped"));

        result = new Result();
        result.setStatus(Status.PENDING.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.getConsolidatedStatus(), is(Status.SKIPPED));
        assertThat(resultMatch.getConsolidatedStatusString(), is("skipped"));

        result = new Result();
        result.setStatus(Status.UNDEFINED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.getConsolidatedStatus(), is(Status.SKIPPED));
        assertThat(resultMatch.getConsolidatedStatusString(), is("skipped"));

        result = new Result();
        result.setStatus(Status.AMBIGUOUS.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.getConsolidatedStatus(), is(Status.SKIPPED));
        assertThat(resultMatch.getConsolidatedStatusString(), is("skipped"));

        result = new Result();
        result.setStatus(Status.PASSED.getStatusString());
        resultMatch.setResult(result);
        assertThat(resultMatch.getConsolidatedStatus(), is(Status.PASSED));
        assertThat(resultMatch.getConsolidatedStatusString(), is("passed"));
    }

    @Test
    public void hasOutputsTest(){
        assertThat(resultMatch.hasOutputs(), is(false));
        List<String> output = new ArrayList<>();
        output.add("Test");
        resultMatch.setOutput(output);
        assertThat(resultMatch.hasOutputs(), is(true));
    }

    @Test
    public void returnEscapedOutputsTest(){
        List<String> output = new ArrayList<>();
        output.add("Testäöüß");
        resultMatch.setOutput(output);
        assertThat(resultMatch.returnEscapedOutputs().get(0), is("Testäöüß"));
    }
}
