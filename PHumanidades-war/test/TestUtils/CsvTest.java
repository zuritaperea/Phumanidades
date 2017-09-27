/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ruben
 */
public class CsvTest {
    
    public CsvTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        try {
            String archivo = "/home/ruben/Descargas/LINKRENDICION.csv";
            
            Scanner scanner = new Scanner(new File(archivo));
            
            while(scanner.hasNextLine()){
                List<String> result = new ArrayList<>();
                String line = scanner.nextLine().trim();
                boolean doblequote = false;
                boolean doblequoteend = false;
                if(line.startsWith("\"")){
                    StringBuffer buffer = new StringBuffer();
                    char[] chars = line.toCharArray();
                    for(char c:  chars){
                        if(c == '=' && doblequote){
                            doblequote = false;
                        }
                        if(c == '\"' && doblequoteend){
                            doblequoteend = false;
                            doblequote = true;
                            continue;
                        }else{
                            if(c == '\"' && doblequote){
                                buffer.append(c);
                                doblequote = false;
                                doblequoteend = true;
                            }else{
                                if(c == '\"'){
                                    doblequote = true;
                                    continue;
                                }else if(c == ' '){
                                    continue;
                                }else if(c == '='){
                                    continue;
                                }else if(c == ','){
                                    
                                    result.add(buffer.toString());
                                    buffer = new StringBuffer();
                                    continue;
                                }else{
                                    buffer.append(c);
                                }
                            }
                        }
                    }
                    
                    result.add(buffer.toString());
                }
                System.out.println(result);
                System.out.println(doblequote);
                System.out.println(doblequoteend);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
