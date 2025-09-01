package org.martinmeer;

import junit.framework.TestCase;
import org.junit.Test;
import org.martinmeer.repo.MainReference;

public class InputDataProcessorTest extends TestCase {

    @Test
    public void testInputTest() {
        InputDataProcessor inputDataProcessor = new InputDataProcessor("\"0-3\",\"A\",\"{\"\"9\"\":[295,270],\"\"10\"\":[310,270],\"\"11\"\":[330,270],\"\"12\"\":[370,270],\"\"13\"\":[410,270]}\"", "hole");
        System.out.println(inputDataProcessor.getRange());
        System.out.println(inputDataProcessor.getRangeByToleranceByType().toString());
        MainReference mainReference = inputDataProcessor.getMainReferencesList().getFirst();
        System.out.println(mainReference.toString());

    }
  
}