package org.eclipse.viatra.cep.tests.integration.model.patterns.complex;

import com.google.common.collect.Maps;
import java.util.Map;
import org.eclipse.viatra.cep.core.api.events.ParameterizableEventInstance;
import org.eclipse.viatra.cep.core.api.patterns.ParameterizableComplexEventPattern;
import org.eclipse.viatra.cep.core.metamodels.events.Event;
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory;
import org.eclipse.viatra.cep.tests.integration.model.patterns.atomic.A2_Pattern;
import org.eclipse.viatra.cep.tests.integration.model.patterns.complex.anonymous._AnonymousPattern_2;

@SuppressWarnings("all")
public class MultiplicityAtLeast_Pattern extends ParameterizableComplexEventPattern {
  public MultiplicityAtLeast_Pattern() {
    super();
    setOperator(EventsFactory.eINSTANCE.createFOLLOWS());
    
    // contained event patterns
    addEventPatternRefrence(new _AnonymousPattern_2(), 1);
    addEventPatternRefrence(new A2_Pattern(), 1);
    setId("org.eclipse.viatra.cep.tests.integration.model.patterns.complex.multiplicityatleast_pattern");
  }
  
  @Override
  public boolean evaluateParameterBindings(final Event event) {
    if(event instanceof ParameterizableEventInstance){
    	return evaluateParameterBindings((ParameterizableEventInstance) event);
    }
    return true;
  }
  
  public boolean evaluateParameterBindings(final ParameterizableEventInstance event) {
    Map<String, Object> params = Maps.newHashMap();
    return evaluateParamBinding(params, event);
    
  }
}