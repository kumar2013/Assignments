/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mcs10aee
 */
public class Fetch {

    static String path = null;
    String tempInst = null;
    String minomicInst = null;
    String numInst = null;
    String opcode = null;
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
    Decode decode = new Decode();
    boolean flag = false;
    String s = null;
    String b = "test";
    String tpp = "test";
    private int size = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        Fetch fp = new Fetch();

        System.out.print("Enter file name: ");

        //  open up standard input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));



        //  read the path name from the command-line; need to use try/catch with the
        //  readLine() method
        try {
            path = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your name!");
            System.exit(1);
        }

        System.out.println("Thanks for the path name, " + path);
         fp.FetchModule();
         fp.mainFucnction();


    }
    /*
     * This module reads flile path form a user ,Reads the file line by line,
     * Generate MIPS instruction whichasscoiates with a given number from the file
     * Decompose the instruction to R-format,I-format and J-format
     * display the result to user based on those format
     */

    public void FetchModule() throws FileNotFoundException, IOException {
       
        File file = new File(path);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        
        fis = new FileInputStream(file);

        // Here BufferedInputStream is added for fast reading.
        bis = new BufferedInputStream(fis);
        dis = new DataInputStream(bis);
        System.out.println("Instructions :" + "\n" + "\t");


        while(dis.available() != 0) {
            
            tempInst = dis.readLine();
            instructions[size]= tempInst;
            System.out.println(instructions[size]);
           
            size++;
        } 
        

        // dis.available() returns 0 if the file does not have more lines.
        System.out.println("Instruction Memory(Intitial value)");
        for (int i = 0; i < im.length; i++) {
            im[i] = "00";
            System.out.println("@inst" + i + ": " + "[" + im[i] + "]");

        }

        System.out.println("");
        System.out.println("Type <<step>> to enter to the step mode" + ":or" + "\n" + "Type <<run>> to excute all instructions once" + ":");
        BufferedReader bff = new BufferedReader(new InputStreamReader(System.in));

        s = bff.readLine();
         System.out.println("Choose display format: Type <hex> for Hexadecimal or Type <dec> for Decimal ");
                BufferedReader tp = new BufferedReader(new InputStreamReader(System.in));
                tpp = tp.readLine();
    }
    
      public void mainFucnction() throws IOException{
          
        while (getPC() <= size) {
//            System.out.println("pc value"+ PC);
//            System.out.println("value of instruction"+ instructions[PC]);
            // this statement reads the line from the file and print it to
            // the console.
            System.out.println("");
            if (s.equals("step")) {
                System.out.println("Type step to go to the next instruction : or type reset to reintialize: ");
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                s = bf.readLine();
                
            }
            if (s.equals("step")) {

                flag = true;

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
            FetchModule();
              if (s.equals("step")) {

                flag = true;

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
            minomicInst = tempInst;
            // System.out.print("Input number :" + tempInst);
            System.out.println("Stage one: ");
            System.out.print("\t" + "Inst" + getPC() + ":" + minomicInst + "\t");


            if (!(minomicInst.substring(0, 1).equals("l") || minomicInst.substring(0, 1).equals("b") ||
                    minomicInst.substring(0, 2).equals("sw")|| minomicInst.equals("exit"))) {

                rs = minomicInst.substring(8, 11);
                rt = minomicInst.substring(12, 15);
                rd = minomicInst.substring(4, 7);

                if (minomicInst.substring(0, 3).equals("add")) {

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
                    opcode = "000000";
                    rbinaryNumber = opcode +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("32"));
                   if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                    decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32);
                       decode.setTpp(tpp);
                    }
                    
                    System.out.println("\t" + "Binary representatin :" + opcode + " " +
                            rs + " " + rt + " " + rd + " " + "00000" +
                            Integer.toBinaryString(Integer.parseInt("32")) + "\t");
                 
                
                   
               
                
             
                   
                   
                   
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("---------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                        }

                    }

                    decode.setOpCode(opcode);
                    decode.setRs(rs);
                    decode.setRt(rt);
                    decode.setRd(rd);
                    decode.setFunc("100000");

                    decode.ControlValue();
                    decode.eventSelection();
                    if (flag) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                        
                    }



                } else if (minomicInst.substring(0, 3).equals("sub")) {

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
                    opcode = "000000";
                    rbinaryNumber = opcode +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("32"));

                    if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                    decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                    System.out.println("\t" + "Binary representatin :" + opcode + " " +
                            rs + " " + rt + " " + rd + " " + "00000" +
                            Integer.toBinaryString(Integer.parseInt("34")) + "\t");
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("------------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    decode.setOpCode(opcode);
                    decode.setRs(rs);
                    decode.setRt(rt);
                    decode.setRd(rd);
                    decode.setFunc("100010");
                    decode.ControlValue();
                    decode.eventSelection();
                    if (flag) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                } else if (minomicInst.substring(0, 3).equals("and")) {
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
                    opcode = "000000";
                    rbinaryNumber = opcode +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("100100"));
                     if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                    System.out.println("\t" + "Binary representatin :" + opcode + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("100100")) + "\t");
                    im[PC] = numInst;
                    
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    decode.setOpCode(opcode);
                    decode.setRs(rs);
                    decode.setRt(rt);
                    decode.setRd(rd);
                    decode.setFunc("100100");
                    decode.ControlValue();
                    decode.eventSelection();
                    if (flag) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                } else if (minomicInst.substring(0, 3).equals("nor")) {

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
                    opcode = "000000";
                    rbinaryNumber = opcode +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("100111"));
                    System.out.println("\t" + "Binary representatin :" + opcode + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("100111")) + "\t");
                    
                     if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                    
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    decode.setOpCode(opcode);
                    decode.setRs(rs);
                    decode.setRt(rt);
                    decode.setRd(rd);
                    decode.setFunc("100111");
                    decode.ControlValue();
                    decode.eventSelection();
                    if (flag) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                } else if (minomicInst.substring(0, 3).equals("or")) {

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
                    opcode = "000000";
                    rbinaryNumber = opcode +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("100101"));
                    if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                    System.out.println("\t" + "Binary representatin :" + opcode + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("100101")) + "\t");
                    
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    decode.setOpCode(opcode);
                    decode.setRs(rs);
                    decode.setRt(rt);
                    decode.setRd(rd);
                    decode.setFunc("100101");
                    decode.ControlValue();
                    decode.eventSelection();
                    if (flag) {
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
                    System.out.print("\t" + "Decimal representatin :" + numInst + "\t");
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
                    opcode = "000000";
                    rbinaryNumber = opcode +
                            rs + rt + rd + "00000" +
                            Integer.toBinaryString(Integer.parseInt("101010"));
                    System.out.println("\t" + "Binary representatin :" + opcode + "" +
                            rs + "" + rt + "" + rd + "" + "00000" + "" +
                            Integer.toBinaryString(Integer.parseInt("101010")) + "\t");
                    if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                    for (int i = 0; i < im.length; i++) {
                        if (getPC() == i) {
                            im[i] = im[i];
                            System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                        } else {
                            System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                        }
                    }
                    decode.setOpCode(opcode);
                    decode.setRs(rs);
                    decode.setRt(rt);
                    decode.setRd(rd);
                    decode.setFunc("101010");
                    decode.ControlValue();
                    decode.eventSelection();
                    if (flag) {
                        setPC(getPC() + 1);
                        setPcc(getPcc() + 4);
                        b = "test";
                        continue;

                    } else {
                    }

                }

            } else if (minomicInst.substring(0, 2).equals("lw")) {

                opcode = Integer.toBinaryString(Integer.parseInt("35"));
                rs = minomicInst.substring(9, 12);
                rt = minomicInst.substring(3, 6);
                offset = minomicInst.substring(7, 8);


                for (int i = 0; i < reg.length; i++) {
                    if (rs.equals(reg[i])) {

                        rs = Integer.toString(i);
                    } else if (rt.equals(reg[i])) {
                        rt = Integer.toString(i);
                    }
                }

                numInst = "35" + "  " + rs + " " + rt + " " + offset;
                
                System.out.print("\t" + "Decimal representation :" + numInst + "\t");


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

//                 rbinaryNumber =  Integer.toBinaryString(Integer.parseInt("35")) +
//                      rs + rt + offset;
                if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                System.out.println("\t" + "Binary representatin :" + opcode + " " +
                        rs + " " + rt + " " + offset + "\n");

                for (int i = 0; i < im.length; i++) {
                    if (getPC() == i) {
                        im[i] = im[i];
                        System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                    } else {
                        System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                    }
                }
                decode.setOpCode(opcode);
                decode.setRs(rs);
                decode.setRt(rt);
                decode.setOffset(offset);
                decode.ControlValue();
                decode.eventSelection();
                if (flag) {
                    setPC(getPC() + 1);
                    setPcc(getPcc() + 4);
                    b = "test";
                    continue;

                } else {
                }

            } else if (minomicInst.substring(0, 2).equals("sw")) {


                opcode = Integer.toBinaryString(Integer.parseInt("43"));
                rs = minomicInst.substring(9, 12);
                rt = minomicInst.substring(3, 6);
                offset = minomicInst.substring(7, 8);

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

//                 rbinaryNumber =  Integer.toBinaryString(Integer.parseInt("35")) +
//                      rs + rt + offset;
                if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                System.out.println("\t" + "Binary representatin :" + opcode + " " +
                        rs + "" + rt + " " + offset + "\n");

                for (int i = 0; i < im.length; i++) {
                    if (getPC() == i) {
                        im[i] = im[i];
                        System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                    } else {
                        System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                    }
                }
                decode.setOpCode(opcode);
                decode.setRs(rs);
                decode.setRt(rt);
                decode.setIm(im);
                decode.setOffset(offset);
                decode.ControlValue();
                decode.eventSelection();
                if (flag) {
                    setPC(getPC() + 1);
                    setPcc(getPcc() + 4);
                    b = "test";
                    continue;

                } else {
                }

            } else if (minomicInst.substring(0, 3).equals("beq")) {


                opcode = "000100";
                rs = minomicInst.substring(8, 11);
                rt = minomicInst.substring(4, 7);
                offset = minomicInst.substring(12);

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

//                 rbinaryNumber =  Integer.toBinaryString(Integer.parseInt("35")) +
//                      rs + rt + offset;
                if(tpp.equals("dec"))
                    {    
                    System.out.print("\t" + "Decimal :" + numInst + "\t");
                     decode.setTpp(tpp);
                    im[PC] = numInst;
                    }
                    else
                    {
                       System.out.print("\t" + "Hexadecimal :" + "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(32) + "\t");
                 
                       im[PC] = "0x"+Integer.toHexString(Integer.parseInt(opcode)) + " " +
                            Integer.toHexString(Integer.parseInt(rs)) + " " + Integer.toHexString(Integer.parseInt(rt))
                            + " " + Integer.toHexString(Integer.parseInt(rd)) + " " + Integer.toHexString(Integer.parseInt("00000")) +
                            Integer.toHexString(34);
                        decode.setTpp(tpp);
                    }
                
                System.out.println("\t" + "Binary representatin :" + opcode + " " +
                        rs + "" + rt + " " + offset + "\n");

                for (int i = 0; i < im.length; i++) {
                    if (getPC() == i) {
                        im[i] = im[i];
                        System.out.println("----------> @PC" + getPcc() + "" + "[" + im[i] + "]");
                    } else {
                        System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
                    }
                }
                temp = getPC();
                decode.setOpCode(opcode);
                decode.setRs(rs);
                decode.setRt(rt);
                decode.setOffset(offset);
                decode.ControlValue();
                decode.eventSelection();
                if (flag) {
                    //setPC(getPC() + 1);
                    setPcc(getPcc() + 4);
                    b = "beq";
                    PC = decode.getA();
                    continue;

                } else {
                    b = "beq";
                    PC = decode.getA();
                    continue;
                   
                }

            }
            else if(minomicInst.equals("exit"))
            {
               System.out.println("<-- End of Simulation Thank you!-->");
               System.out.println("<-- Developed by Emnetu and Rahel-->"); 
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


    
   