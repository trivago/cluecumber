package com.trivago.cluecumber.json.pojo;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class PojoTest {
    private static final int EXPECTED_CLASS_COUNT = 13;
    private static final String POJO_PACKAGE = "com.trivago.cluecumber.json.pojo";

    @BeforeClass
    public static void setSystemProperty() {
        System.setProperty("sun.boot.class.path", System.getProperty("java.class.path"));
    }

    @Test
    public void ensureExpectedPojoCount() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE,
                pojoClass -> !pojoClass.getSourcePath().contains("/test-classes/"));
        Affirm.affirmEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
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
