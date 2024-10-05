package org.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class TestNGGenerator implements IAlterSuiteListener {
    private static final Logger LOGGER = LogManager.getLogger(TestNGGenerator.class);

    @Override
    public void alter(List<XmlSuite> suites) {
        LOGGER.info("Altering suite configuration based on environment settings.");
        EnvConfigFileReader envConfigFileReader = new EnvConfigFileReader();
        XmlSuite suite = suites.get(0);
        XmlTest xmlTest = new XmlTest(suite);
        List<XmlClass> xmlClasses = new ArrayList<>();

        addClassWithMethods(xmlClasses, envConfigFileReader, "Contact_Tests", "org.automation.tests.contact.", envConfigFileReader.getContactVersionToBeUsed(),
                "checkContactUrl" , "contactLoginTest", "checkLogout");

        addClassWithMethods(xmlClasses, envConfigFileReader, "Para_Tests", "org.automation.tests.para.", envConfigFileReader.getParaVersionToBeUsed(),
                "registerUser", "checkParaUrl", "paraLoginTest", "checkLogout");

        xmlTest.setXmlClasses(xmlClasses);
        LOGGER.info("Suite configuration altered successfully.");
    }

    private void addClassWithMethods(List<XmlClass> xmlClasses, EnvConfigFileReader envConfigFileReader, String testClassName,
                                     String classPackage, String versionToBeUsed, String... methodNames) {
        if (versionToBeUsed != null && !versionToBeUsed.isEmpty()) {
            List<XmlInclude> methodsToInclude = constructIncludes(testClassName, methodNames);
            if (!methodsToInclude.isEmpty()) {
                XmlClass xmlClass = new XmlClass(classPackage + versionToBeUsed + "." + testClassName);
                xmlClass.setIncludedMethods(methodsToInclude);
                xmlClasses.add(xmlClass);
                LOGGER.info("Added class {} with methods {}.", xmlClass.getName(), methodsToInclude);
            }
        }
    }

    public List<XmlInclude> constructIncludes(String className, String... methodNames) {
        List<XmlInclude> includes = new ArrayList<>();
        String[] excludeMethods = getExcludeMethods(className);
        for (String methodName : methodNames) {
            boolean isExcluded = false;
            for (String excludeMethod : excludeMethods) {
                if (methodName.equalsIgnoreCase(excludeMethod)) {
                    isExcluded = true;
                    break;
                }
            }
            if (!isExcluded) {
                includes.add(new XmlInclude(methodName));
            }
        }
        return includes;
    }

    private String[] getExcludeMethods(String className) {
        EnvConfigFileReader envConfigFileReader = new EnvConfigFileReader();
        String excludeMethodsStr = "";
        switch (className) {
            case "Contact_Tests":
                excludeMethodsStr = envConfigFileReader.getContactExcludeTest();
                break;
            case "Para_Tests":
                excludeMethodsStr = envConfigFileReader.getParaExcludeTest();
                break;
        }
        return excludeMethodsStr.isEmpty() ? new String[]{} : excludeMethodsStr.split(",");
    }

}
