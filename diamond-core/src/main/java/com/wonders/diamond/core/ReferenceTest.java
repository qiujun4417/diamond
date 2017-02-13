package com.wonders.diamond.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2017/1/26.
 */
public class ReferenceTest {

    class Reference{
        private int age;

        public int getAge(){
            return age;
        }

        public void setAge(int age){
            this.age = age;
        }

        public void addAge(int age){
            this.age += age;
        }
    }

    class InvokeClassA{

        private Reference reference;

        public InvokeClassA(Reference reference){
            this.reference = reference;
        }

        public void addAge(int age){
            reference.addAge(age);
        }

        public int getAge(){
            return reference.getAge();
        }
    }

    class InvokeClassB{

        private Reference reference;

        public InvokeClassB(Reference reference){
            this.reference = reference;
        }

        public void addAge(int age){
            reference.addAge(age);
        }

        public int getAge(){
            return reference.getAge();
        }
    }

    class InvokeClassC{

        private Reference reference;

        public InvokeClassC(Reference reference){
            this.reference = reference;
        }

        public void addAge(int age){
            reference.addAge(age);
        }

        public int getAge(){
            return reference.getAge();
        }
    }

    class TestClassA{
        private TestClassB testClassB;

        public TestClassB getTestClassB() {
            return testClassB;
        }

        public void setTestClassB(TestClassB testClassB) {
            this.testClassB = testClassB;
        }
    }

    class TestClassB{
        private List<String> elements;

        public List<String> getElements() {
            return elements;
        }

        public void setElements(List<String> elements) {
            this.elements = elements;
        }
    }

    public static void main(String[] args){
        ReferenceTest referenceTest = new ReferenceTest();
        Reference reference = referenceTest.new Reference();
        reference.setAge(20);

        Reference reference1 = referenceTest.new Reference();
        reference1.setAge(21);
        Reference reference2 = reference1;
        reference2.setAge(22);

        TestClassB testClassB = referenceTest.new TestClassB();
        testClassB.setElements(new ArrayList<>());
        TestClassA testClassA = referenceTest.new TestClassA();
        testClassA.setTestClassB(testClassB);

        TestClassB testClassB1 = testClassA.getTestClassB();
        testClassB1.getElements().add("hello");

        InvokeClassA invokeClassA = referenceTest.new InvokeClassA(reference);
        InvokeClassB invokeClassB = referenceTest.new InvokeClassB(reference);
        InvokeClassC invokeClassC = referenceTest.new InvokeClassC(reference);

        invokeClassA.addAge(5);
        System.out.println("invokeClassA's age is " + invokeClassA.getAge());
        System.out.println("invokeClassB's age is " + invokeClassB.getAge());
        System.out.println("invokeClassC's age is " + invokeClassC.getAge());

        System.out.println("reference1's age " + reference1.getAge());
        System.out.println("reference2's age " + reference2.getAge());
        System.out.println("testClassA's testClassB's elements's size " + testClassA.getTestClassB().getElements().size());
    }
}
