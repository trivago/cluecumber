package com.trivago.cluecumber.json.pojo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepTest {

    private Step step;

    @Before
    public void setup() {
        step = mock(Step.class);
        when(step.returnNameWithArguments()).thenCallRealMethod();
    }

    @Test
    public void returnNameWithArgumentsEmptyTest() {
        List<Argument> arguments = new ArrayList<>();
        when(step.getArguments()).thenReturn(arguments);
        when(step.getName()).thenReturn("");
        assertThat(step.returnNameWithArguments(), is(""));
    }

    @Test
    public void returnNameWithArgumentsNoArgumentsTest() {
        when(step.getName()).thenReturn("some name");
        assertThat(step.returnNameWithArguments(), is("some name"));
    }

    @Test
    public void returnNameWithArguments() {
        when(step.getName()).thenReturn("This is a name with an argument inside.");
        Result result = mock(Result.class);
        List<Argument> arguments = new ArrayList<>();
        Argument argument = new Argument();
        argument.setVal("argument");
        arguments.add(argument);
        when(step.getArguments()).thenReturn(arguments);
        step.setResult(result);
        assertThat(step.returnNameWithArguments(), is("This is a name with an <strong>argument</strong> inside."));
    }
}