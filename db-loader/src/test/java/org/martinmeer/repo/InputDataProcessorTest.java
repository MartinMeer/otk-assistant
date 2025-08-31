package org.martinmeer.repo;

import junit.framework.TestCase;
import org.junit.Test;

public class InputDataProcessorTest extends TestCase {

    public void testInputTest() {
        InputDataProcessor inputDataProcessor = new InputDataProcessor("\"0-3\",\"AC\",\"{\"\"9\"\":[295,270],\"\"10\"\":[310,270],\"\"11\"\":[330,270],\"\"12\"\":[370,270],\"\"13\"\":[410,270]}\"", "hole");
        System.out.println(inputDataProcessor.getRangeByToleranceByType().toString());
        MainReference mainReference = inputDataProcessor.getMainReferencesList().getFirst();
        System.out.println(mainReference.toString());

    }
  
}