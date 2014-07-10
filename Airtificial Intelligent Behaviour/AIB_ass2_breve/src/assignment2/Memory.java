/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.Random;

/**
 *
 * @author mcs10aee
 */
public class Memory {

    private String memValue;
    private String memAddress;
    private String[] memory = new String[256];
    private String writeBack;
    private String tpp;
    private String rt;
    WriteBack wb = new WriteBack();

    public void MemoryIntialization() {
        System.out.println("");
        System.out.println("Stage Four: ");
        System.out.println("Initial values of Memory: ");
        Random r = new Random();
        for (int i = 0; i < memory.length; i++) {
            int x = r.nextInt(10);
            memory[i] = Integer.toString(x);

            System.out.println("@mem" + i + "" + "[" + memory[i] + "]");
        }
    }

    public void loadfromMem() {

        wb.setTpp(tpp);
        wb.setRt(rt);
         System.out.println("The current value of Memory location");
        if(tpp.equals("dec")){  
        for (int i = 0; i < memory.length; i++) {
            if (Integer.parseInt(memAddress) == i) {
                writeBack = memory[i];
                System.out.println(" --->MemRead" + i + "" + "[" + memory[i] + "]");
                wb.setResult(memAddress);
                wb.setMemresult(writeBack);
            } else {
                System.out.println(" @mem" + i + "" + "[" + memory[i] + "]");
            }
        }
        }
        else
        {
         for (int i = 0; i < memory.length; i++) {
            if (Integer.parseInt(memAddress) == i) {
                writeBack = memory[i];
                System.out.println(" --->MemRead" + "0x"+Integer.toHexString(i)  + "[" + "0x"+ Integer.toHexString(Integer.parseInt(memory[i])) + "]");
                wb.setResult(memAddress);
                wb.setMemresult(writeBack);
            } else {
                System.out.println(" @mem" + "0x"+ Integer.toHexString(i)  + "["+ "0x" + Integer.toHexString(Integer.parseInt(memory[i])) + "]");
            }
        }
        wb.setWB();
    }
    }

    public void storeToMem() {

      
        System.out.println("The current value of Memory location changed");
        if(tpp.equals("hex")){  
        for (int i = 0; i < memory.length; i++) {
            
            if (Integer.parseInt(memAddress) == i) {
                memory[i] = memValue;
                System.out.println(" --->MemWrite" + "0x"+Integer.toHexString(i) + "["+ "0x"  + Integer.toHexString(Integer.parseInt(memory[i])) + "]");
            } else {
                System.out.println(" @mem" + "0x"+ Integer.toHexString(i)  + "[" + "0x"+ Integer.toHexString(Integer.parseInt(memory[i])) + "]");
            }
        }
         wb.setTpp(tpp);
        }
        else
        {
           for (int i = 0; i < memory.length; i++) {
            
            if (Integer.parseInt(memAddress) == i) {
                memory[i] = memValue;
                System.out.println(" --->MemWrite" + i + "" + "[" + memory[i] + "]");
            } else {
                System.out.println(" @mem" + i + "" + "[" + memory[i] + "]");
            }
        }
            wb.setTpp(tpp);
        }
     System.out.println("<--------------- End of one instrucction cycle ---------------->");
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
