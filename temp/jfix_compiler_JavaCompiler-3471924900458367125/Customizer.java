import jfix.functor.*;
import jease.cms.domain.*;

public class Customizer implements Procedure<Content> {

  public void execute(Content content) {
     content.replace("=\"http://www.jease.org/","=\"./~/");
  }
  
}