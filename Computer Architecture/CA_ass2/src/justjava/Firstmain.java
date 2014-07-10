/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package justjava;

/**
 *
 * @author mcs10kai
 */

import java.io.*;
import java.util.Random;


public class Firstmain {

    static String path = null;
    String tempInst = null;
    String mnemonicInst = null;
    String numInst = null;
    String opfield = null;
    String rs = null;
    String rt = null;
    String rd = null;
    String offset = null;
    private int PC = 0;
    private int pcc = 0;
    int temp = 0;
    String rbinaryNumber = null;
    String ibinaryNumber = null;
    String reg[] = {"$zero", "$at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", "$t0", "$t1",
        "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6",
        "$s7", "$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra"
    };
    String im[] = new String[256];
    String instructions[] = new String[256];
    Current current = new Current();
    boolean fly = false;
    String s = null;
    String b = "test";
    String tpp = "test";
    private int size = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        Firstmain fp = new Firstmain();

        System.out.print("Enter the file name: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


       try {
            path = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read the file name!");
            System.exit(1);
        }

        System.out.println("The path name you entered is : " + path + "\n" + "\t");
         fp.Firstread();
         fp.Thenproceed();


    }
   

    public void Firstread() throws FileNotFoundException, IOException {
       
        File file = new File(path);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        fis = new FileInputStream(file);
        bis = new BufferedInputStream(fis);
        dis = new DataInputStream(bis);
        System.out.println("Instructions in the given file:");
        System.out.println("-------------------------------" + "\n" + "\t");


        while(dis.available() != 0) {
            
            tempInst = dis.readLine();
            instructions[size]= tempInst;
            System.out.println(instructions[size]);
           
            size++;
        } 
        

        System.out.println("\n"  + "Instruction Memory(Initial value)");
        System.out.println("----------------------------------"+ "\n");
        for (int i = 0; i < im.length; i++) {
            im[i] = "00";
            System.out.println("@inst" + i + ": " + "[" + im[i] + "]");

        }

        System.out.println("");
        System.out.println("Enter your option");
        System.out.println("-----------------" + "\n");
        System.out.println("# Type 'step' to enter into the step mode" + ": (or)" + "\n" + "# Type 'run' to execute all instructions once" + ":");
        BufferedReader bff = new BufferedReader(new InputStreamReader(System.in));

        s = bff.readLine();
         System.out.println("\n" + "Choose display format:" );
         System.out.println("----------------------" + "\n");
         System.out.println("Type 'hex' for Hexadecimal (or) Type 'dec' for Decimal: ");
                BufferedReader tp = new BufferedReader(new InputStreamReader(System.in));
                tpp = tp.readLine();
    }
    
      public void Thenproceed() throws IOException{
          
        while (getPC() <= size) {

            System.out.println("");
            if (s.equals("step")) {
                System.out.println("Type 'step' to go to the next Instruction (or) Type 'reset' to Reintialize: ");
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                s = bf.readLine();
                
            }
            if (s.equals("step")) {

                fly = true;

                if(b.equals("beq")){
                   
                    tempInst = instructions[PC];
                    PC = temp;
                   
                }
                else
                    
                for (int i = getPC(); i <= getPC(); i++) {
                    tempInst = instructions[i];
                }

            } else if (s.equals("run")) {
                                 
                if(b.equals("beq")){
                    System.out.println("Beq :value of b" + getPC());
                    tempInst = instructions[PC];
                    PC = temp;
                    System.out.println("Temp"+ temp+ "pc" + PC);
                }
                else
                    tempInst = instructions[getPC()];
                        
            } 
            else
            {
            Firstread();
              if (s.equals("step")) {

                fly = true;

                if(b.equals("beq")){
                    System.out.println("Beq :value of b" + getPC());
                    tempInst = instructions[PC];
                    PC = temp;
                    System.out.println("Temp"+ temp+ "pc" + PC);
                }
                else
                    
                for (int i = getPC(); i <= getPC(); i++) {
                    tempInst = instructions[i];
                }

            }
            }

           
            int n = tempInst.length();
            mnemonicInst = tempInst;
            System.out.println("\n" + "First Phase:");
            System.out.println("------------");
            System.out.print("\n" + "Instruction" + "(" + getPC() + ")" + "    : " + mnemonicInst + "\n");


            if (!(mnemonicInst.substring(0, 1).equals("l") || mnemonicInst.substring(0, 1).equals("b") ||
                    mnemonicInst.substring(0, 2).equals("sw")|| mnemonicInst.equals("exit"))) {

                rs = mnemonicInst.substring(8, 11);
                rt = mnemonicInst.substring(12, 15);
                rd = mnemonicInst.substring(4, 7);

                if (mnemonicInst.substring(0, 3).equals("add")) {

                    for (int i = 0; i < reg.length; i++) {
                        if (rs.equals(reg[i])) {

                            rs = Integer.toString(i);
                        } else if (rt.equals(reg[i])) {
                            rt = Integer.toString(i);
                        } else if (rd.equals(reg[i])) {
                            rd = Integer.toString(i);
                        }
                    }
                    numInst = "0" + "  " + rs + " " + rt + " " + rd + " " + "0" + " " + "32";
                    
                    
                    if (Integer.toBinaryString(Integer.parseInt(rd)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rd)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rd = left.concat(Integer.toBinaryString(Integer.parseInt(rd)));

                    } else {
                        rd = Integer.toBinaryString(Integer.parseInt(rd));
                    }
                    if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                    } else {
                        rs = Integer.toBinaryString(Integer.parseInt(rs));
                    }
                    if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                    } else {
                        rs = Integer.toBinaryString(Integer.parseInt(rs));
                    }
                    opfield = "000000";
                    rbinaryNumber = opfield +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("32"));
                   if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32);
                       current.setTpp(tpp);
                    }
                    
                    System.out.println("Binary Form       : " + opfield + " " +
                            rs + " " + rt + " " + rd + " " + "00000" +
                            Integer.toBinaryString(Integer.parseInt("32")) + "\n");
                 
                
                   
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                        }

                    }

                                        
                    current.setOpCode(opfield);
                    current.setRs(rs);
                    current.setRt(rt);
                    current.setRd(rd);
                    current.setFunc("100000");
                    current.ManageData();
                    current.ETlecture();
                    
                    if (fly) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                        
                    }



                } else if (mnemonicInst.substring(0, 3).equals("sub")) {

                    for (int i = 0; i < reg.length; i++) {
                        if (rs.equals(reg[i])) {

                            rs = Integer.toString(i);
                        } else if (rt.equals(reg[i])) {
                            rt = Integer.toString(i);
                        } else if (rd.equals(reg[i])) {
                            rd = Integer.toString(i);
                        }
                    }
                    numInst = "0" + "  " + rs + " " + rt + " " + rd + " " + "0" + " " + "34";
                    
                    if (Integer.toBinaryString(Integer.parseInt(rd)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rd)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }
                        rd = left.concat(Integer.toBinaryString(Integer.parseInt(rd)));
                    } else {
                        rd = Integer.toBinaryString(Integer.parseInt(rd));
                    }
                    if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                    } else {
                        rs = Integer.toBinaryString(Integer.parseInt(rs));
                    }
                    if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                    } else {
                        rt = Integer.toBinaryString(Integer.parseInt(rt));
                    }
                    opfield = "000000";
                    rbinaryNumber = opfield +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("32"));

                    if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                    System.out.println("Binary Form       : " + opfield + " " +
                            rs + " " + rt + " " + rd + " " + "00000" +
                            Integer.toBinaryString(Integer.parseInt("34")) + "\n");
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    
                    current.setOpCode(opfield);
                    current.setRs(rs);
                    current.setRt(rt);
                    current.setRd(rd);
                    current.setFunc("100010");
                    current.ManageData();
                    current.ETlecture();
                    
                    if (fly) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                } else if (mnemonicInst.substring(0, 3).equals("and")) {
                    for (int i = 0; i < reg.length; i++) {
                        if (rs.equals(reg[i])) {

                            rs = Integer.toString(i);
                        } else if (rt.equals(reg[i])) {
                            rt = Integer.toString(i);
                        } else if (rd.equals(reg[i])) {
                            rd = Integer.toString(i);
                        }
                    }
                    numInst = "0" + "  " + rs + " " + rt + " " + rd + " " + "0" + " " + "36";
                    
                    if (Integer.toBinaryString(Integer.parseInt(rd)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rd)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rd = left.concat(Integer.toBinaryString(Integer.parseInt(rd)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                    }
                    opfield = "000000";
                    rbinaryNumber = opfield +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("100100"));
                     if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                    System.out.println("Binary Form       : " + opfield + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("100100")) + "\n");
                    im[PC] = numInst;
                    
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                   
                    current.setOpCode(opfield);
                    current.setRs(rs);
                    current.setRt(rt);
                    current.setRd(rd);
                    current.setFunc("100100");
                    current.ManageData();
                    current.ETlecture();
                    
                    if (fly) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                } else if (mnemonicInst.substring(0, 3).equals("nor")) {

                    for (int i = 0; i < reg.length; i++) {
                        if (rs.equals(reg[i])) {

                            rs = Integer.toString(i);
                        } else if (rt.equals(reg[i])) {
                            rt = Integer.toString(i);
                        } else if (rd.equals(reg[i])) {
                            rd = Integer.toString(i);
                        }
                    }
                    numInst = "0" + "  " + rs + " " + rt + " " + rd + " " + "0" + " " + "39";
                    
                    
                    if (Integer.toBinaryString(Integer.parseInt(rd)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rd)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rd = left.concat(Integer.toBinaryString(Integer.parseInt(rd)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                    }
                    opfield = "000000";
                    rbinaryNumber = opfield +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("100111"));
                    System.out.println("Binary Form       : " + opfield + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("100111")) + "\n");
                    
                     if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                    
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                   
                    current.setOpCode(opfield);
                    current.setRs(rs);
                    current.setRt(rt);
                    current.setRd(rd);
                    current.setFunc("100111");
                    current.ManageData();
                    current.ETlecture();
                    
                    if (fly) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                } else if (mnemonicInst.substring(0, 3).equals("or")) {

                    for (int i = 0; i < reg.length; i++) {
                        if (rs.equals(reg[i])) {

                            rs = Integer.toString(i);
                        } else if (rt.equals(reg[i])) {
                            rt = Integer.toString(i);
                        } else if (rd.equals(reg[i])) {
                            rd = Integer.toString(i);
                        }
                    }
                    numInst = "0" + "  " + rs + " " + rt + " " + rd + " " + "0" + " " + "37";
                       if (Integer.toBinaryString(Integer.parseInt(rd)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rd)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rd = left.concat(Integer.toBinaryString(Integer.parseInt(rd)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                    }
                    opfield = "000000";
                    rbinaryNumber = opfield +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("100101"));
                    if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                    System.out.println("Binary form       : " + opfield + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("100101")) + "\n");
                    
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                   
                    current.setOpCode(opfield);
                    current.setRs(rs);
                    current.setRt(rt);
                    current.setRd(rd);
                    current.setFunc("100101");
                    current.ManageData();
                    current.ETlecture();
                    
                    if (fly) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }
                } else {

                    for (int i = 0; i < reg.length; i++) {
                        if (rs.equals(reg[i])) {

                            rs = Integer.toString(i);
                        } else if (rt.equals(reg[i])) {
                            rt = Integer.toString(i);
                        } else if (rd.equals(reg[i])) {
                            rd = Integer.toString(i);
                        }
                    }
                    numInst = "0" + "  " + rs + " " + rt + " " + rd + " " + "0" + " " + "42";
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    if (Integer.toBinaryString(Integer.parseInt(rd)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rd)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rd = left.concat(Integer.toBinaryString(Integer.parseInt(rd)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                    }
                    if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                        int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                        String left = "";
                        for (int i = 1; i <= difference; i++) {
                            left = left + "0";
                        }

                        rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                    }
                    opfield = "000000";
                    rbinaryNumber = opfield +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("101010"));
                    System.out.println("Binary form       : " + opfield + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("101010")) + "\n");
                    if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal Form      : " + numInst + "\t");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    
                    current.setOpCode(opfield);
                    current.setRs(rs);
                    current.setRt(rt);
                    current.setRd(rd);
                    current.setFunc("101010");
                    current.ManageData();
                    current.ETlecture();
                    
                    if (fly) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                }

            } else if (mnemonicInst.substring(0, 2).equals("lw")) {

                opfield = Integer.toBinaryString(Integer.parseInt("35"));
                rs = mnemonicInst.substring(9, 12);
                rt = mnemonicInst.substring(3, 6);
                offset = mnemonicInst.substring(7, 8);


                for (int i = 0; i < reg.length; i++) {
                    if (rs.equals(reg[i])) {

                        rs = Integer.toString(i);
                    } else if (rt.equals(reg[i])) {
                        rt = Integer.toString(i);
                    }
                }

                numInst = "35" + "  " + rs + " " + rt + " " + offset;
                
                System.out.print("Decimal Form      : " + numInst + "\n");


                if (Integer.toBinaryString(Integer.parseInt(offset)).length() < 16) {

                    int difference = 16 - Integer.toBinaryString(Integer.parseInt(offset)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    offset = left.concat(Integer.toBinaryString(Integer.parseInt(offset)));


                } else {

                    offset = (Integer.toBinaryString(Integer.parseInt(offset)));

                }
                if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                    int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                } else {

                    rs = (Integer.toBinaryString(Integer.parseInt(rs)));

                }
                if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                    int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                } else {

                    rt = (Integer.toBinaryString(Integer.parseInt(rt)));

                }


                if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                System.out.println("Binary Form       : " + opfield + " " +
                        rs + " " + rt + " " + offset + "\n");

                for (int i = 0; i < im.length; i++) {
                    if (getPC() == i) {
                        im[i] = im[i];
                        System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                    } else {
                        System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                    }
                }
                
                current.setOpCode(opfield);
                current.setRs(rs);
                current.setRt(rt);
                current.setOffset(offset);
                current.ManageData();
                current.ETlecture();
                
                if (fly) {
                    setPC(getPC() + 1);
                    setPcc(getPcc() + 4);
                    b = "test";
                    continue;

                } else {
                }

            } else if (mnemonicInst.substring(0, 2).equals("sw")) {


                opfield = Integer.toBinaryString(Integer.parseInt("43"));
                rs = mnemonicInst.substring(9, 12);
                rt = mnemonicInst.substring(3, 6);
                offset = mnemonicInst.substring(7, 8);

                for (int i = 0; i < reg.length; i++) {
                    if (rs.equals(reg[i])) {

                        rs = Integer.toString(i);
                    } else if (rt.equals(reg[i])) {
                        rt = Integer.toString(i);
                    }
                }

                numInst = "43" + "  " + rs + " " + rt + " " + offset;
                
               


                if (Integer.toBinaryString(Integer.parseInt(offset)).length() < 16) {

                    int difference = 16 - Integer.toBinaryString(Integer.parseInt(offset)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    offset = left.concat(Integer.toBinaryString(Integer.parseInt(offset)));


                } else {

                    offset = (Integer.toBinaryString(Integer.parseInt(offset)));

                }
                if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                    int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                } else {

                    rs = (Integer.toBinaryString(Integer.parseInt(rs)));

                }
                if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                    int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                } else {

                    rt = (Integer.toBinaryString(Integer.parseInt(rt)));

                }


                if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                      current.setTpp(tpp);
                    }
                System.out.println("Binary Form       : " + opfield + " " +
                        rs + "" + rt + " " + offset + "\n");

                for (int i = 0; i < im.length; i++) {
                    if (getPC() == i) {
                        im[i] = im[i];
                        System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                    } else {
                        System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                    }
                }
               
                current.setOpCode(opfield);
                current.setRs(rs);
                current.setRt(rt);
                current.setIm(im);
                current.setOffset(offset);
                current.ManageData();
                current.ETlecture();
                
                if (fly) {
                    setPC(getPC() + 1);
                    setPcc(getPcc() + 4);
                    b = "test";
                    continue;

                } else {
                }

            } else if (mnemonicInst.substring(0, 3).equals("beq")) {


                opfield = "000100";
                rs = mnemonicInst.substring(8, 11);
                rt = mnemonicInst.substring(4, 7);
                offset = mnemonicInst.substring(12);

                for (int i = 0; i < reg.length; i++) {
                    if (rs.equals(reg[i])) {

                        rs = Integer.toString(i);
                    } else if (rt.equals(reg[i])) {
                        rt = Integer.toString(i);
                    }
                }

                numInst = "4" + "  " + rs + " " + rt + " " + offset;
                


                if (Integer.toBinaryString(Integer.parseInt(offset)).length() < 16) {

                    int difference = 16 - Integer.toBinaryString(Integer.parseInt(offset)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    offset = left.concat(Integer.toBinaryString(Integer.parseInt(offset)));


                } else {

                    offset = (Integer.toBinaryString(Integer.parseInt(offset)));

                }
                if (Integer.toBinaryString(Integer.parseInt(rs)).length() < 5) {

                    int difference = 5 - Integer.toBinaryString(Integer.parseInt(rs)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    rs = left.concat(Integer.toBinaryString(Integer.parseInt(rs)));

                } else {

                    rs = (Integer.toBinaryString(Integer.parseInt(rs)));

                }
                if (Integer.toBinaryString(Integer.parseInt(rt)).length() < 5) {

                    int difference = 5 - Integer.toBinaryString(Integer.parseInt(rt)).length();
                    String left = "";
                    for (int i = 1; i <= difference; i++) {
                        left = left + "0";
                    }

                    rt = left.concat(Integer.toBinaryString(Integer.parseInt(rt)));

                } else {

                    rt = (Integer.toBinaryString(Integer.parseInt(rt)));

                }


                if(tpp.equals("dec"))
                    {    
                    System.out.print("Decimal Form      : " + numInst + "\n");
                    current.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("Hexadecimal Form  : " + "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\n");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opfield)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                       current.setTpp(tpp);
                    }
                
                System.out.println("Binary Form       : " + opfield + " " +
                        rs + "" + rt + " " + offset + "\n");

                for (int i = 0; i < im.length; i++) {
                    if (getPC() == i) {
                        im[i] = im[i];
                        System.out.println("@PC" + getPcc() + "" + "[" + im[i] + "]");
                    } else {
                        System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
                    }
                }
                temp = getPC();
                
                current.setOpCode(opfield);
                current.setRs(rs);
                current.setRt(rt);
                current.setOffset(offset);
                current.ManageData();
                current.ETlecture();
                
                if (fly) {
                    setPcc(getPcc() + 4);
                    b = "beq";
                    PC = current.getA();
                    continue;

                } else {
                    b = "beq";
                    PC = current.getA();
                    continue;
                   
                }

            }
            else if(mnemonicInst.equals("exit"))
            {
               System.out.println("\n" + "********************** The Simulator Terminates **********************");
               System.exit(0);
            }
            b = "test";
            setPC(getPC() + 1);
            
            setPcc(getPcc() + 4);

        }

    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getPcc() {
        return pcc;
    }

    public void setPcc(int pcc) {
        this.pcc = pcc;
    }
}



 class Current  {
 
    private String opfield;
    private boolean regDst;
    private boolean branch;
    private boolean memread;
    private boolean memToreg;
    private boolean memWrite;
    private boolean aluSrc;
    private boolean regWrite;
    private boolean aluOp0;
    private boolean aluOp1;
    private String rs;
    private String rt;
    private String rd;
    private String readvalue1;
    private String readvalue2;
    private String readvalue3;
    private String writevalue;
    private String offset;
    private String func;
    private int memAddress;
    private int a= 0;
    private String tpp= null;
    String data[] = {"0", "6", "4", "9", "5", "7", "3", "1", "2", "8",
        "11", "9", "10", "14", "13", "17", "10", "20", "20", "20", "25", "25", "16",
        "8", "10", "12", "12", "8", "47", "2", "5", "4"
    };
    private String im[] = new String[256];
    Operation opt = new Operation();
    Storage str = new Storage();
    Regis res = new Regis();
    

    public void ManageData() {
        regDst = false;
        branch = false;
        memread = false;
        memToreg = false;
        memWrite = false;
        aluSrc = false;
        regWrite = false;
        aluOp0 = false;
        aluOp1 = false;

        if (opfield.equals("000000")) {
            regDst = true;
            regWrite = true;
            aluOp1 = true;


        } else if (opfield.equals("100011")) {
            aluSrc = true;
            memToreg = true;
            regWrite = true;

        } else if (opfield.equals("101011")) {
            aluSrc = true;
            memWrite = true;

        } else if (opfield.equals("000100")) {
           
            branch = true;
            aluOp0 = true;

        }


    }

    public void ETlecture() {
        if (memToreg == true || aluSrc == true) {

            setMemAddress();

        } else if (branch == true) {
            System.out.println("");
            setBeq();
        } else {
            Registerindex();
        }
    }

    public void Registerindex() {

        for (int i = 0; i < 32; i++) {
            if (i == Integer.parseInt(rs, 2)) {
                readvalue1 = data[i];

            } else if (i == Integer.parseInt(rt, 2)) {
                readvalue2 = data[i];

            } else if (i == Integer.parseInt(rd, 2)) {
                setReadvalue3(data[i]);

            }

        }
        System.out.println("");
        System.out.println("Second Phase:");
        System.out.println("-------------");
        System.out.println("The Latest value of Registers are:");
        if(tpp.equals("dec")){
           
        System.out.println("Register file at index" + " " + Integer.parseInt(rs, 2) + "[ " + readvalue1 + "]" + "\n" + "Register file at index" + " " +
                Integer.parseInt(rt, 2) + "[ " + readvalue2 + "]" + "\n" +
                "Register file at index" + " " + Integer.parseInt(rd, 2) + "[ " + readvalue3 + "]" + "\n");
        }
        else if(tpp.equals("hex"))
        {
         
           System.out.println("Register file at index" + " 0x" + Integer.toHexString(Integer.parseInt(rs, 2)) + "[ " + readvalue1 + "]" + "\n" + "Register file at index" + " 0x" +
                Integer.toHexString(Integer.parseInt(rt, 2)) + "[ " + readvalue2 + "]" + "\n" +
                "Register file at index" + "0x" + Integer.toHexString(Integer.parseInt(rd, 2)) + "[ " + readvalue3 + "]" + "\n");
        }
        opt = new Operation(readvalue1, readvalue2, offset, func, rd,tpp);
        opt.MathOP();
        
    }

    public void setBeq() {
        
        System.out.println("");
        System.out.println("Second Phase:");
        System.out.println("-------------");
        
        for (int i = 0; i < 32; i++) {
            if (i == Integer.parseInt(rs, 2)) {
                readvalue1 = data[i];
            } else if (i == Integer.parseInt(rt, 2)) {
                readvalue2 = data[i];

            }

        }


        System.out.println("The Latest value of Registers and the offset value is:");
         if(tpp.equals("dec")){
        System.out.println("Register file(rs value) at index" + " " + Integer.parseInt(rs, 2) + "[ " + readvalue1 + "]" + "\n" + "Register file(rt value) at index" + " " +
                Integer.parseInt(rt, 2) + "[ " + readvalue2 + "]" + "\n" +
                "offset" + "[ " + offset + "]" + "\t");
         }
         else if(tpp.equals("hex"))
         {
            System.out.println("Register file(rs value) at index" + " 0x" + Integer.toHexString(Integer.parseInt(rs, 2))
                    + "[ " + readvalue1 + "]" + "\n" + "Register file(rt value) at index" + "0x " +
                Integer.toHexString(Integer.parseInt(rt, 2)) + "[ " + readvalue2 + "]" + "\n" +
                "offset" + "[ " + Integer.toHexString(Integer.parseInt(offset)) + "]" + "\t");
         }
             
        System.out.println("");
        System.out.println("Third Phase:");
        System.out.println("------------");
        
        if (Integer.parseInt(readvalue1) == Integer.parseInt(readvalue2)) {
            System.out.println(readvalue1 + " " + "is equal to" +" "+ readvalue2);
           
         
        } else {
            System.out.println(readvalue1 + " " + "not equal to" + " "+ readvalue2);
        }
         Firstmain fm = new Firstmain();
        fm.setPcc(4 * (Integer.parseInt(offset, 2)));
        fm.setPC(fm.getPcc() / 4);
        a = fm.getPC();
         

        for (int i = 0; i < im.length; i++) {
            if (fm.getPC() == i) {
                im[i] = im[i];
                System.out.println("@PC" + fm.getPcc() + "" + "[" + im[i] + "]");
            } else {
                System.out.println("Latest value @inst" + i + "" + "[" + im[i] + "]");
            }
        }
        
    }

    public void setMemAddress() {
        System.out.println("");
        System.out.println("Second Phase:");
        System.out.println("-------------");
        for (int i = 0; i < 32; i++) {
            if (i == Integer.parseInt(rs, 2)) {
                readvalue1 = data[i];
            } else if (i == Integer.parseInt(rt, 2)) {
                readvalue2 = data[i];

            }

        }
        memAddress = Integer.parseInt(offset, 2) + Integer.parseInt(readvalue1);
        opt.setMemAddress(Integer.toString(memAddress));
        str.setMemAddress(Integer.toString(memAddress));

        opt.setMemValue(readvalue2);
        str.setMemValue(readvalue2);
        
        if(tpp.equals("dec")){
        System.out.println("The Latest value of Registers and the offset values are:");
        System.out.println("Register file(rs value) at index" + " " + Integer.parseInt(rs, 2) + "[ " + readvalue1 + "]" + "\n" + "Register file(rt value) at index" + " " +
                Integer.parseInt(rt, 2) + "[ " + readvalue2 + "]" + "\n" +
                "offset" + "[ " + offset + "]" + "\t");
        }
        else if(tpp.equals("hex")){
            
            System.out.println("The Latest value of Registers and the offset values are:");
        System.out.println("Register file(rs value) at index" + " 0x" + Integer.toHexString(Integer.parseInt(rs, 2))
                + "[ " + readvalue1 + "]" + "\n" + "Register file(rt value) at index" + " 0x" +
                Integer.toHexString(Integer.parseInt(rt, 2)) + "[ " + readvalue2 + "]" + "\n" +
                "offset" + "[ " + Integer.toHexString(Integer.parseInt(offset,2)) + "]" + "\t");
        }
        System.out.println("");
        System.out.println("Third Phase: ");
        System.out.println("------------");
        System.out.println("The result after arithmetic operation is:" + " " + memAddress + " " + "(Address in the memory)");
        str.InitialMemory();
        str.setTpp(tpp);
        if (opfield.equals("100011")) {
            str.setRt(rt);
            res.setData(data);
            str.ProcessMEM();
        } else {
            str.storeToMem();
        }
    
     
    }

    public String getOpCode() {
        return opfield;
    }

    public void setOpCode(String opCode) {
        this.opfield = opCode;
    }

    public boolean isRegDst() {
        return regDst;
    }

    public void setRegDst(boolean regDst) {
        this.regDst = regDst;
    }

    public boolean isBranch() {
        return branch;
    }

    public void setBranch(boolean branch) {
        this.branch = branch;
    }

    public boolean isMemread() {
        return memread;
    }

    public void setMemread(boolean memread) {
        this.memread = memread;
    }

    public boolean isMemToreg() {
        return memToreg;
    }

    public void setMemToreg(boolean memToreg) {
        this.memToreg = memToreg;
    }

    public boolean isMemWrite() {
        return memWrite;
    }

    public void setMemWrite(boolean memWrite) {
        this.memWrite = memWrite;
    }

    public boolean isAluSrc() {
        return aluSrc;
    }

    public void setAluSrc(boolean aluSrc) {
        this.aluSrc = aluSrc;
    }

    public String isRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getReadvalue1() {
        return readvalue1;
    }

    public void setReadvalue1(String readvalue1) {
        this.readvalue1 = readvalue1;
    }

    public String getReadvalue2() {
        return readvalue2;
    }

    public void setReadvalue2(String readvalue2) {
        this.readvalue2 = readvalue2;
    }

    public String getWritevalue() {
        return writevalue;
    }

    public void setWritevalue(String writevalue) {
        this.writevalue = writevalue;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = "0000000000000000" + offset;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public boolean isRegWrite() {
        return regWrite;
    }

    public void setRegWrite(boolean regWrite) {
        this.regWrite = regWrite;
    }

    public boolean isAluOp0() {
        return aluOp0;
    }

    public void setAluOp0(boolean aluOp0) {
        this.aluOp0 = aluOp0;
    }

    public boolean isAluOp1() {
        return aluOp1;
    }

    public void setAluOp1(boolean aluOp1) {
        this.aluOp1 = aluOp1;
    }

    public int getMemAddress() {
        return memAddress;
    }

    public void setMemAddress(int memAddress) {
        this.memAddress = memAddress;
    }

    public String getReadvalue3() {
        return readvalue3;
    }

    public void setReadvalue3(String readvalue3) {
        this.readvalue3 = readvalue3;
    }

    public String[] getIm() {
        return im;
    }

    public void setIm(String[] im) {
        this.im = im;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getTpp() {
        return tpp;
    }

    public void setTpp(String tpp) {
        this.tpp = tpp;
    }
}



class Operation {
    
    private String readvalue1;
    private String readvalue2;
    private String offset;
    private String func;
    private String rd;
    private String tpp;
    private int result;
    private String logicResult;
    private String memAddress;
    private String memValue;
    Regis res = new Regis(); 
    Operation() {
     
    }
    Operation(String rd1, String rd2, String offst, String fun,String rdAdd,String tp) {

        readvalue1 = rd1;
        readvalue2 = rd2;
        offset = offst;
        func = fun;
        rd = rdAdd;
        tpp = tp;
        
    }

    public String MathOP() {
        
        System.out.println("Third Phase :");
        System.out.println("-------------");
        if (func.equals("100000")) {
            
            result = Integer.parseInt(readvalue1) + Integer.parseInt(readvalue2);
           
            System.out.println("The result after arithmetic operation is:"+ " "+result);
            res.setRdValue(result);
            res.setRd(rd);
            res.setTpp(tpp);
            res.setRtypeNew();
            return Integer.toBinaryString(result);
        } else if (func.equals("100010")) {
            result = Integer.parseInt(readvalue1) - Integer.parseInt(readvalue2);
            System.out.println("The result after arithmetic operation is:"+ " "+result);
            res.setRdValue(result);
            res.setRd(rd);
            res.setTpp(tpp);
            res.setRtypeNew();
            return Integer.toBinaryString(result);
        } else if (func.equals("100100")) {
            result = Integer.parseInt(readvalue1) & Integer.parseInt(readvalue2);
            System.out.println("The result after arithmetic operation is:"+ " "+result);
            res.setRdValue(result);
            res.setRd(rd);
            res.setTpp(tpp);
            res.setRtypeNew();
            return Integer.toBinaryString(result);
        } else if (func.equals("100101")) {
            result = Integer.parseInt(readvalue1) | Integer.parseInt(readvalue2);
            System.out.println("The result after arithmetic operation is:"+ " "+result);
            res.setRdValue(result);
            res.setRd(rd);
            res.setTpp(tpp);
            res.setRtypeNew();
            return Integer.toBinaryString(result);
        } else if (func.equals("101010")) {
            if (Integer.parseInt(readvalue1) < Integer.parseInt(readvalue2)) {
                result = 1;
               
                
            } else {
                result = 0;
            }
            res.setRdValue(result);
            res.setRd(rd);
            res.setTpp(tpp);
            res.setRtypeNew();
            return Integer.toBinaryString(result);
        }
        else 
          System.out.println("Error"+ " "+func);  
        
         
           
        return null;
    }
     

    public String getReadvalue1() {
        return readvalue1;
    }

    public void setReadvalue1(String readvalue1) {
        this.readvalue1 = readvalue1;
    }

    public String getReadvalue2() {
        return readvalue2;
    }

    public void setReadvalue2(String readvalue2) {
        this.readvalue2 = readvalue2;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public int getSum() {
        return result;
    }

    public void setSum(int sum) {
        this.result = sum;
    }

    public String getLogicResult() {
        return logicResult;
    }

    public void setLogicResult(String logicResult) {
        this.logicResult = logicResult;
    }

    public String getMemAddress() {
        return memAddress;
    }

    public void setMemAddress(String memAddress) {
        this.memAddress = memAddress;
        
    }

    public String getMemValue() {
        return memValue;
    }

    public void setMemValue(String memValue) {
        this.memValue = memValue;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getTpp() {
        return tpp;
    }

    public void setTpp(String tpp) {
        this.tpp = tpp;
    }

   
}


class Regis {
    
private String result;
    private int rdValue;
    private String rd;
    private String rt;
    private String memresult;
    private String tpp;
    private String data[] = {"0", "6", "4", "9", "5", "7", "3", "1", "2", "8",
        "11", "9", "10", "14", "13", "17", "10", "11", "12", "20", "25", "25", "16",
        "8", "10", "12", "12", "8", "47", "2", "5", "4"
    };
    
    
    public void setNew(){
        System.out.println("Fifth Phase:");
        System.out.println("------------");
        
      if(tpp.equals("dec")){  
       for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rt,2) == i){
                getData()[i] = memresult;
              System.out.println(" -------> New" + i + "" + "["+ "0x" + getData()[i] + "]");
           }
           else
            System.out.println("@Register" + i + "" + "["+ "0x" + getData()[i] + "]");
           
       }
      }
      else if(tpp.equals("hex"))
      {
         for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rt,2) == i){
                getData()[i] = Integer.toHexString(Integer.parseInt(memresult));
              System.out.println(" -------> New" + i + "" + "["+ "0x" + getData()[i] + "]");
           }
           else
            System.out.println("@Register" + i + "" + "["+ "0x" + getData()[i] + "]");
           
       }
      }
         System.out.println("<--------------------- End of one instruction ---------------------->");
    }
      public void setRtypeNew(){
       System.out.println("");   
       System.out.println("Fifth Phase:");
       System.out.println("------------");
       System.out.println("The Latest value of Registers which has been changed during the execution:" + "\n");
      
       if(tpp.equals("dec")){  
           
        for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rd,2) == i){
              getData()[i] = Integer.toString(getRdValue());
              System.out.println(" -------> New" + i + "" + "[" + getData()[i] + "]");
           }
           else
            System.out.println("@Register" + i + "" + "[" + getData()[i] + "]");
           
       }
        
       }
       else
       {
          for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rd,2) == i){
              getData()[i] = Integer.toHexString(getRdValue());
              System.out.println(" -------> New" + "0x"+i  + "[" + "0x"+ getData()[i] + "]");
           }
           else
            System.out.println("@Register" + "0x" + i +  "[" + "0x"+ getData()[i] + "]");
          
               
       }
       }
       
       System.out.println("<--------------------- End of one instruction ---------------------->");    
    }
    
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getMemresult() {
        return memresult;
    }

    public void setMemresult(String memresult) {
        this.memresult = memresult;
    }

    public int getRdValue() {
        return rdValue;
    }

    public void setRdValue(int rdValue) {
        this.rdValue = rdValue;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getTpp() {
        return tpp;
    }

    public void setTpp(String tpp) {
        this.tpp = tpp;
    }

} 


class Storage {
    
 private String memValue;
    private String memAddress;
    private String[] memory = new String[256];
    private String writeBack;
    private String tpp;
    private String rt;
    Regis res = new Regis();

    public void InitialMemory() {
        System.out.println("");
        System.out.println("Fourth Phase:");
        System.out.println("-------------");
        System.out.println("Initial values of Memory: " + "\n");
        Random r = new Random();
        for (int i = 0; i < memory.length; i++) {
            int x = r.nextInt(10);
            memory[i] = Integer.toString(x);

            System.out.println("@memory" + i + "" + "[" + memory[i] + "]");
        }
    }

    public void ProcessMEM() {

        res.setTpp(tpp);
        res.setRt(rt);
         System.out.println("\n" + "The Latest value of Memory location" + "\n");
        if(tpp.equals("dec")){  
        for (int i = 0; i < memory.length; i++) {
            if (Integer.parseInt(memAddress) == i) {
                writeBack = memory[i];
                System.out.println(" --->MemRead" + i + "" + "[" + memory[i] + "]");
                res.setResult(memAddress);
                res.setMemresult(writeBack);
            } else {
                System.out.println("@memory" + i + "" + "[" + memory[i] + "]");
            }
        }
        }
        else
        {
         for (int i = 0; i < memory.length; i++) {
            if (Integer.parseInt(memAddress) == i) {
                writeBack = memory[i];
                System.out.println(" --->MemRead" + "0x"+Integer.toHexString(i)  + "[" + "0x"+ Integer.toHexString(Integer.parseInt(memory[i])) + "]");
                res.setResult(memAddress);
                res.setMemresult(writeBack);
            } else {
                System.out.println("@memory" + "0x"+ Integer.toHexString(i)  + "["+ "0x" + Integer.toHexString(Integer.parseInt(memory[i])) + "]");
            }
        }
        res.setNew();
    }
    }

    public void storeToMem() {

      
        System.out.println("\n" + "The Latest value of Memory after location changed" + "\n");
        if(tpp.equals("hex")){  
        for (int i = 0; i < memory.length; i++) {
            
            if (Integer.parseInt(memAddress) == i) {
                memory[i] = memValue;
                System.out.println(" --->NewMem" + "0x"+Integer.toHexString(i) + "["+ "0x"  + Integer.toHexString(Integer.parseInt(memory[i])) + "]");
            } else {
                System.out.println("@memory" + "0x"+ Integer.toHexString(i)  + "[" + "0x"+ Integer.toHexString(Integer.parseInt(memory[i])) + "]");
            }
        }
         res.setTpp(tpp);
        }
        else
        {
           for (int i = 0; i < memory.length; i++) {
            
            if (Integer.parseInt(memAddress) == i) {
                memory[i] = memValue;
                System.out.println(" --->NewMem" + i + "" + "[" + memory[i] + "]");
            } else {
                System.out.println("@memory" + i + "" + "[" + memory[i] + "]");
            }
        }
            res.setTpp(tpp);
        }
     System.out.println("<--------------------- End of one instruction ---------------------->");
    }

    public String getMemValue() {
        return memValue;
    }

    public void setMemValue(String memValue) {
        this.memValue = memValue;
    }

    public String getMemAddress() {
        return memAddress;
    }

    public void setMemAddress(String memAddress) {
        this.memAddress = memAddress;
    }

    public String[] getMemory() {
        return memory;
    }

    public void setMemory(String[] memory) {
        this.memory = memory;
    }

    public String getWriteBack() {
        return writeBack;
    }

    public void setWriteBack(String writeBack) {
        this.writeBack = writeBack;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getTpp() {
        return tpp;
    }

    public void setTpp(String tpp) {
        this.tpp = tpp;
    }
}
