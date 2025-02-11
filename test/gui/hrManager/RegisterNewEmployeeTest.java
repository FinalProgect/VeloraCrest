/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package gui.hrManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kovid
 */
public class RegisterNewEmployeeTest {
    
    public RegisterNewEmployeeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMain() {
    }

    /**
     * Test of validateEmail method, of class RegisterNewEmployee.
     */
    @Test
//    public void testValidateEmail() {
//        System.out.println("validateEmail");
//        String email = "kovidha@gmail";
//        RegisterNewEmployee instance = new RegisterNewEmployee();
//        boolean expResult = true;
//        boolean result = instance.validateEmail(email);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of validateNIC method, of class RegisterNewEmployee.
     */
   
//    public void testValidateNIC() {
//        System.out.println("validateNIC");
//        String nic = "20021630300";
//        RegisterNewEmployee instance = new RegisterNewEmployee();
//        boolean expResult = true;
//        boolean result = instance.validateNIC(nic);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }

    /**
     * Test of validatePostal method, of class RegisterNewEmployee.
     */
    
    public void testValidatePostal() {
        System.out.println("validatePostal");
        String nic = "00100";
        RegisterNewEmployee instance = new RegisterNewEmployee();
        boolean expResult = false;
        boolean result = instance.validatePostal(nic);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
