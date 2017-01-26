package com.wonders.diamond.core;

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

    public static void main(String[] args){
        ReferenceTest referenceTest = new ReferenceTest();
        Reference reference = referenceTest.new Reference();
        reference.setAge(20);

        InvokeClassA invokeClassA = referenceTest.new InvokeClassA(reference);
        InvokeClassB invokeClassB = referenceTest.new InvokeClassB(reference);
        InvokeClassC invokeClassC = referenceTest.new InvokeClassC(reference);

        invokeClassA.addAge(5);
        System.out.println("invokeClassA's age is " + invokeClassA.getAge());
        System.out.println("invokeClassB's age is " + invokeClassB.getAge());
        System.out.println("invokeClassC's age is " + invokeClassC.getAge());
    }
}
