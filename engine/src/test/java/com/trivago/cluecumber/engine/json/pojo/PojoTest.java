package com.trivago.cluecumber.engine.json.pojo;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.filters.FilterSyntheticClasses;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PojoTest {
    private static final int EXPECTED_CLASS_COUNT = 11;
    private static final String POJO_PACKAGE = "com.trivago.cluecumber.engine.json.pojo";

    @BeforeAll
    public static void setSystemProperty() {
        System.setProperty("sun.boot.class.path", System.getProperty("java.class.path"));
    }

    @Test
    public void ensureExpectedPojoCount() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, new FilterSyntheticClasses());
        int count = (int) pojoClasses.stream().filter(
                pojoClass -> !pojoClass.getSourcePath().contains("/test-classes/")).count();
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, count);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(POJO_PACKAGE, new FilterPackageInfo());
    }
}
