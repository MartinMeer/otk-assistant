package org.martinmeer;

import junit.framework.TestCase;
import org.junit.Test;
import org.martinmeer.repo.MainReference;

public class InputProcessorTest extends TestCase {

    @Test
    public void testInputTest() {
        InputProcessor inputProcessor = new InputProcessor("\"0-3\",\"A\",\"{\"\"9\"\":[295,270],\"\"10\"\":[310,270],\"\"11\"\":[330,270],\"\"12\"\":[370,270],\"\"13\"\":[410,270]}\"", "hole");
        System.out.println(inputProcessor.getRange());
        System.out.println(inputProcessor.getRangeByToleranceByType().toString());
        MainReference mainReference = inputProcessor.getMainReferencesList().getFirst();
        System.out.println(mainReference.toString());

    }
  
}