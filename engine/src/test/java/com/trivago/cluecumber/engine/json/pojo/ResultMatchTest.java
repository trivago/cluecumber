package com.trivago.cluecumber.engine.json.pojo;

import com.trivago.cluecumber.engine.constants.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultMatchTest {
    private ResultMatch resultMatch;

    @BeforeEach
    public void setup() {
        resultMatch = new ResultMatch();
    }

    @Test
    public void glueMethodNameTest() {
        Match match = new Match();
        match.setLocation("someMethod");
        resultMatch.setMatch(match);
        assertEquals(resultMatch.getGlueMethodName(), "someMethod");
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
        assertEquals(resultMatch.getArguments().size(), 1);
        assertEquals(resultMatch.getArguments().get(0).getVal(), "arg1");
        assertEquals(resultMatch.getArguments().get(0).getOffset(), 10);
    }

    @Test
    public void getStatusStringTest() {
        Result result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        resultMatch.setResult(result);
        assertEquals(resultMatch.getStatusString(), "skipped");
    }

    @Test
    public void isFailedTest() {
        Result result = new Result();
        result.setStatus(Status.FAILED.getStatusString());
        resultMatch.setResult(result);
        assertTrue(resultMatch.isFailed());
        assertFalse(resultMatch.isPassed());
        assertFalse(resultMatch.isSkipped());
    }

    @Test
    public void isPassedTest() {
        Result result = new Result();
        result.setStatus(Status.PASSED.getStatusString());
        resultMatch.setResult(result);
        assertFalse(resultMatch.isFailed());
        assertTrue(resultMatch.isPassed());
        assertFalse(resultMatch.isSkipped());
    }

    @Test
    public void isSkippedTest() {
        Result result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        resultMatch.setResult(result);
        assertFalse(resultMatch.isFailed());
        assertFalse(resultMatch.isPassed());
        assertTrue(resultMatch.isSkipped());
    }

    @Test
    public void getConsolidatedStatusTest() {
        Result result = new Result();
        result.setStatus(Status.SKIPPED.getStatusString());
        resultMatch.setResult(result);
        assertEquals(resultMatch.getConsolidatedStatus(), Status.SKIPPED);
        assertEquals(resultMatch.getConsolidatedStatusString(), "skipped");

        result = new Result();
        result.setStatus(Status.PENDING.getStatusString());
        resultMatch.setResult(result);
        assertEquals(resultMatch.getConsolidatedStatus(), Status.SKIPPED);
        assertEquals(resultMatch.getConsolidatedStatusString(), "skipped");

        result = new Result();
        result.setStatus(Status.UNDEFINED.getStatusString());
        resultMatch.setResult(result);
        assertEquals(resultMatch.getConsolidatedStatus(), Status.SKIPPED);
        assertEquals(resultMatch.getConsolidatedStatusString(), "skipped");

        result = new Result();
        result.setStatus(Status.AMBIGUOUS.getStatusString());
        resultMatch.setResult(result);
        assertEquals(resultMatch.getConsolidatedStatus(), Status.SKIPPED);
        assertEquals(resultMatch.getConsolidatedStatusString(), "skipped");

        result = new Result();
        result.setStatus(Status.PASSED.getStatusString());
        resultMatch.setResult(result);
        assertEquals(resultMatch.getConsolidatedStatus(), Status.PASSED);
        assertEquals(resultMatch.getConsolidatedStatusString(), "passed");
    }

    @Test
    public void hasOutputsTest() {
        assertFalse(resultMatch.hasOutputs());
        List<String> output = new ArrayList<>();
        output.add("Test");
        resultMatch.setOutput(output);
        assertTrue(resultMatch.hasOutputs());
    }

    @Test
    public void returnEscapedOutputsTest() {
        List<String> output = new ArrayList<>();
        output.add("Testäöüß");
        resultMatch.setOutput(output);
        assertEquals(resultMatch.returnEscapedOutputs().get(0), "Testäöüß");
    }
}
