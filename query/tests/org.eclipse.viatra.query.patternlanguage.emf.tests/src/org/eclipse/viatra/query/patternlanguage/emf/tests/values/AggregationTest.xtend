/*******************************************************************************
 * Copyright (c) 2010-2012, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/

package org.eclipse.viatra.query.patternlanguage.emf.tests.values

import org.eclipse.viatra.query.patternlanguage.emf.tests.EMFPatternLanguageInjectorProvider
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.InjectWith
import com.google.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.viatra.query.patternlanguage.patternLanguage.PatternLanguagePackage
import org.eclipse.xtext.diagnostics.Diagnostic
import org.junit.Test
import org.eclipse.viatra.query.patternlanguage.emf.eMFPatternLanguage.PatternModel
import org.eclipse.viatra.query.patternlanguage.validation.IssueCodes
import org.eclipse.viatra.query.patternlanguage.typing.ITypeInferrer
import org.junit.Assert
import org.eclipse.viatra.query.runtime.matchers.context.common.JavaTransitiveInstancesKey
import org.eclipse.viatra.query.patternlanguage.emf.validation.EMFPatternLanguageJavaValidator
import org.junit.Before
import org.eclipse.xtext.junit4.validation.ValidatorTester
import com.google.inject.Injector
import org.eclipse.viatra.query.patternlanguage.emf.tests.util.AbstractValidatorTest
import org.eclipse.viatra.query.patternlanguage.emf.validation.EMFIssueCodes

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(EMFPatternLanguageInjectorProvider))
class AggregationTest extends AbstractValidatorTest {
    @Inject
    ParseHelper<PatternModel> parseHelper

    @Inject extension ValidationTestHelper
    @Inject
    Injector injector
    @Inject
    ITypeInferrer typeInferrer
    @Inject
    EMFPatternLanguageJavaValidator validator

    ValidatorTester<EMFPatternLanguageJavaValidator> tester
    
    @Before
    def void initialize() {
        tester = new ValidatorTester(validator, injector)
    }

    @Test
    def void testCountNothingPassed() {
        parseHelper.parse(
            '''package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/viatra/query/patternlanguage/emf/test"

            pattern calledPattern(p : Pattern, v: Variable) = {
                Pattern(p);
                Variable(v);
            }

            pattern callerPattern(output) = {
                output == count find calledPattern(_anyp, _anyv);
                Red.redness(_h, output);
            }'''
        ).assertNoErrors
    }

    @Test
    def void testCountSomeStuffPassed() {
        parseHelper.parse(
            '''package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/viatra/query/patternlanguage/emf/test"

            pattern calledPattern(p : Pattern, v: Variable) = {
                Pattern(p);
                Variable(v);
            }

            pattern callerPattern(p : Pattern, output) = {
                Pattern(p);
                output == count find calledPattern(p, _anyv);
                Red.redness(_h, output);
            }'''
        ).assertNoErrors
    }

    @Test
    def void testCountSomeStuffPassedNoReturn() {
        parseHelper.parse(
            '''package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"

            pattern calledPattern(p : Pattern, v: Variable) = {
                Pattern(p);
                Variable(v);
            }

            pattern callerPattern(p : Pattern) = {
                Pattern(p);
                3 == count find calledPattern(p, _anyv);
            }'''
        ).assertNoErrors
    }

    @Test
    def void testCountAllPassed() {
        parseHelper.parse(
            '''package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/viatra/query/patternlanguage/emf/test"

            pattern calledPattern(c : Circle, v: Red) = {
                Circle.red(c,v);
            }

            pattern callerPattern(c : Circle, output) = {
                Circle(c);
                Red(v);
                output == count find calledPattern(c, v);
                Red.redness(_h, output);
            }'''
        ).assertNoErrors
    }

    @Test
    def void testCountTypeChecking() {
        val parsed = parseHelper.parse('''
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            
            pattern parameterCount(call : PatternCall, c) {
                c == count find parameter(call, _);
            }
            
            pattern parameter(call : PatternCall, parameter : ValueReference) {
                PatternCall.parameters(call, parameter);
            }
        ''')
        parsed.assertNoErrors
        val param_c = parsed.patterns.get(0).parameters.get(1)
        val inferredType = typeInferrer.getType(param_c)
        Assert.assertEquals("Parameter c is expected to have a type of Integers", Integer, (inferredType as JavaTransitiveInstancesKey).instanceClass)
    }
    
    @Test
    def void testCountWithAggregate() {
        val parsed = parseHelper.parse('''
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            
            pattern parameter(call : PatternCall, parameter : ValueReference) {
                PatternCall.parameters(call, parameter);
            }
            
            pattern parameterCount(c) {
                c == count find parameter(_, #param);   
            }
        ''')
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes::INVALID_AGGREGATOR_PARAMETER)
        )
    }
    
    @Test
    def void testCountWithAggregate2() {
        val parsed = parseHelper.parse('''
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            
            pattern parameter(call : PatternCall, parameter : ValueReference) {
                PatternCall.parameters(call, parameter);
            }
            
            pattern parameterCount(c) {
                c == count find parameter(#param1, #param2);   
            }
        ''')
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes::INVALID_AGGREGATOR_PARAMETER),
            getErrorCode(IssueCodes::INVALID_AGGREGATOR_PARAMETER)
        )
    }

    @Test
    def void testMissingComposition() {
        var parsed = parseHelper.parse(
            '''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"

            pattern callerPattern(p : Pattern, output) = {
                Pattern(p);
                output == count find calledPatternMissing(p, anyv);	// anyv should be a single variable, e.g. _anyv
            }'''
        );
        parsed.assertError(
            PatternLanguagePackage::eINSTANCE.patternCall,
            Diagnostic::LINKING_DIAGNOSTIC,
            "calledPatternMissing"
        )

    }

    @Test
    def void testSumAggregator() {
        var parsed = parseHelper.parse(
            '''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            import "http://www.eclipse.org/viatra/query/patternlanguage/emf/test"
                    
            pattern belowAverage(c : Circle, r : Red) {
                Circle.red(c, r);
                Red.redness(r, value);

                C == count find extractRed(c, _);
                S == sum find extractValue(r, #v);
                A == eval(S / C);
                check(value < A as Integer);
            }		
            
            // helper patterns
            pattern extractValue(r : Red, value : EInt) {
                Red.redness(r, value);
            }
            
            pattern extractRed(c : Circle, r : Red) {
                Circle.red(c, r);
            }
            '''
        )
        
        val body = parsed.patterns.get(0).bodies.get(0)
        val variable_S = body.variables.findFirst[name == "S"]
        Assert.assertEquals(
            "Variable S is expected to have a type of Integer", 
            Integer,
            (typeInferrer.getType(variable_S) as JavaTransitiveInstancesKey).instanceClass
        )
        
        val variable_C = body.variables.findFirst[name == "C"]
        Assert.assertEquals(
            "Variable C is expected to have a type of Integer", 
            Integer,
            (typeInferrer.getType(variable_C) as JavaTransitiveInstancesKey).instanceClass
        )
        
        val variable_A = body.variables.findFirst[name == "C"]
        Assert.assertEquals(
            "Variable A is expected to have a type of Integer", 
            Integer,
            (typeInferrer.getType(variable_A) as JavaTransitiveInstancesKey).instanceClass
        )
        
        parsed.assertNoErrors
    }

    @Test
    def void testMinAggregator() {
        var parsed = parseHelper.parse(
            '''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/emf/test"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(c : Circle, redness : EInt) {
                Circle.red(c, parameter);
                redness == min find extractRedness(parameter, #_);
            }
                        
            // helper patterns
            pattern extractRedness(reference : Red, value : EInt) {
                Red(reference);
                Red.redness(reference, value);
            }
            '''
        )
        parsed.assertNoErrors
    }
    @Test
    def void testIntMinAggregator() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern parameterCount(c) {
                 c == min find helper(_call, #number);   
            }

            pattern helper(call : PatternCall, number) {
                 PatternCall.patternRef.name(call, name);
                 number == eval(name.length);
            }
            ''')
        tester.validate(parsed).assertAll(
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
        val parameter_c = parsed.patterns.get(0).parameters.get(0)
        val inferredType = typeInferrer.getType(parameter_c)
        Assert.assertEquals("Parameter c is expected to have a type of Integers", Integer, (inferredType as JavaTransitiveInstancesKey).instanceClass)
    }
    @Test
    def void testIntMinAggregatorMistyped() {
        // Before the fix for bug 521361 is merged, this test fails with a stack overflow in the type inferrer
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern parameterCount(c) {
                 c == min find helper(_call, #n);  // No way to calculate min aggregator type
            }

            pattern helper(call : PatternCall, number) { // Type declaration missing and cannot be calculated
                 PatternCall.name(call, name);  // Incorrect attribute access
                 number == eval(name.length); // Error: Cannot calculate type of eval
            }
            ''')
        tester.validate(parsed).assertAll(
            getErrorCode(EMFIssueCodes::CHECK_CONSTRAINT_SCALAR_VARIABLE_ERROR),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
        val parameter_c = parsed.patterns.get(0).parameters.get(0)
        val inferredType = typeInferrer.getType(parameter_c)
        Assert.assertEquals("Parameter c is expected to have a calculated type of Object", Object, (inferredType as JavaTransitiveInstancesKey).instanceClass)
    }    
    @Test
    def void testIntMinAggregator2() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern parameterCount(c) {
                 c == min find helper(_call, #);   
            }

            pattern helper(call : PatternCall, number) {
                 PatternCall.patternRef.name(call, _name);
                 EInt(number);
            }
            ''')
        tester.validate(parsed).assertAll(
            getWarningCode(EMFIssueCodes::CARTESIAN_STRICT_WARNING),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
        val parameter_c = parsed.patterns.get(0).parameters.get(0)
        val inferredType = typeInferrer.getType(parameter_c)
        Assert.assertEquals("Parameter c is expected to have a type of Integers", Integer, (inferredType as JavaTransitiveInstancesKey).instanceClass)
    }
    
    @Test
    def void testDoubleMinAggregator() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern parameterCount(c) {
                 c == min find helper(_call, #number);   
            }

            pattern helper(call : PatternCall, number) {
                 PatternCall.patternRef.name(call, name);
                 number == eval(name.length.doubleValue);
            }
            ''')
        tester.validate(parsed).assertAll(
            getWarningCode(IssueCodes::CHECK_WITH_IMPURE_JAVA_CALLS),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
        val parameter_c = parsed.patterns.get(0).parameters.get(0)
        val inferredType = typeInferrer.getType(parameter_c)
        Assert.assertEquals("Parameter c is expected to have a type of Doubles", Double, (inferredType as JavaTransitiveInstancesKey).instanceClass)
    }
    
    @Test
    def void testDoubleMinAggregator2() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern parameterCount(c) {
                 c == min find helper(_call, #number);   
            }

            pattern helper(call : PatternCall, number) {
                 PatternCall.patternRef.name(call, _name);
                 EDouble(number);
            }
            ''')
        tester.validate(parsed).assertAll(
            getWarningCode(EMFIssueCodes::CARTESIAN_STRICT_WARNING),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
        val parameter_c = parsed.patterns.get(0).parameters.get(0)
        val inferredType = typeInferrer.getType(parameter_c)
        Assert.assertEquals("Parameter c is expected to have a type of Doubles", Double, (inferredType as JavaTransitiveInstancesKey).instanceClass)
    }
    
    

    @Test
    def void testAggregatorMissingMarker() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(call : PatternCall, value : EInt) {
                PatternCall.parameters(call, parameter);
                value == min find extractValue(parameter, _);
            }
                        
            // helper patterns
            pattern extractValue(reference : IntValue, value : EInt) {
                IntValue(reference);
                IntValue.value(reference, value);
            }
            '''
        )
        parsed.assertError(
            PatternLanguagePackage::eINSTANCE.aggregatedValue,
            IssueCodes.INVALID_AGGREGATOR_PARAMETER
        )
    }
    
    @Test
    def void testAggregatorDuplicateMarkers() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(value) {
                value == min find extractValue(#_, #_);
            }
                        
            // helper patterns
            pattern extractValue(reference : IntValue, value : EInt) {
                IntValue(reference);
                IntValue.value(reference, value);
            }
            '''
        )
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes.INVALID_AGGREGATOR_PARAMETER),
            getErrorCode(IssueCodes.INVALID_AGGREGATOR_PARAMETER)
        )
    }
    
    @Test
    def void testAggregatorDubiusVariableReuse() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(value) {
                value == min find extractValue(_, #value);
            }
                        
            // helper patterns
            pattern extractValue(reference : IntValue, value : EInt) {
                IntValue(reference);
                IntValue.value(reference, value);
            }
            '''
        )
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes.DUBIUS_VARIABLE_NAME),
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
    }
    
    @Test
    def void testInvalidAggregator() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(value) {
                value == Integer find extractValue(_, #v);
            }
                        
            // helper patterns
            pattern extractValue(reference : IntValue, value : EInt) {
                IntValue(reference);
                IntValue.value(reference, value);
            }
            '''
        )
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes.INVALID_AGGREGATOR)
        )
    }
    
    @Test
    def void testInvalidAggregatorContext() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(value : IntValue) {
                find extractValue(value, #v);
            }
                        
            // helper patterns
            pattern extractValue(reference : IntValue, value : EInt) {
                IntValue(reference);
                IntValue.value(reference, value);
            }
            '''
        )
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes.INVALID_AGGREGATE_CONTEXT)
        )
    }
    
    @Test
    def void testInvalidAggregatorContext2() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            import "http://www.eclipse.org/emf/2002/Ecore"
            
            pattern smallestValue(value : IntValue) {
                find extractValue(value, #);
            }
                        
            // helper patterns
            pattern extractValue(reference : IntValue, value : EInt) {
                IntValue(reference);
                IntValue.value(reference, value);
            }
            '''
        )
        tester.validate(parsed).assertAll(
            getErrorCode(IssueCodes.INVALID_AGGREGATE_CONTEXT)
        )
    }
    
    @Test
    def void mistypedAggregator() {
        var parsed = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            
            private pattern bodiesOfPattern(p : Pattern, b : PatternBody) {
                Pattern.bodies(p, b);
            }
            
            pattern mistypedAggregator(number : java Integer) {
                number == max find bodiesOfPattern(_, #count);
            }
            '''
        )
        tester.validate(parsed).assertAll(
            getErrorCode(EMFIssueCodes.VARIABLE_TYPE_INVALID_ERROR)
        )
    }

}
