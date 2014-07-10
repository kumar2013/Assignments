/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author mcs10aee
 */
public class Decode {

    private String opCode;
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
    private String readData1;
    private String readData2;
    private String readData3;
    private String writeData;
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
    Execution exc = new Execution();
    Memory mem = new Memory();
    WriteBack wb = new WriteBack();
    

    public void ControlValue() {
        regDst = false;
        branch = false;
        memread = false;
        memToreg = false;
        memWrite = false;
        aluSrc = false;
        regWrite = false;
        aluOp0 = false;
        aluOp1 = false;

        if (opCode.equals("000000")) {
            regDst = true;
            regWrite = true;
            aluOp1 = true;


        } else if (opCode.equals("100011")) {
            aluSrc = true;
            memToreg = true;
            regWrite = true;

        } else if (opCode.equals("101011")) {
            aluSrc = true;
            memWrite = true;

        } else if (opCode.equals("000100")) {
           
            branch = true;
            aluOp0 = true;

        }


    }

    public void eventSelection() {
        if (memToreg == true || aluSrc == true) {

            setMemAddress();

        } else if (branch == true) {
            System.out.println("cou cou");
            setBeq();
        } else {
            setRegisterFile();
        }
    }

    public void setRegisterFile() {

        for (int i = 0; i < 32; i++) {
            if (i == Integer.parseInt(rs, 2)) {
                readData1 = data[i];

            } else if (i == Integer.parseInt(rt, 2)) {
                readData2 = data[i];

            } else if (i == Integer.parseInt(rd, 2)) {
                setReadData3(data[i]);

            }

        }
        System.out.println("");
        System.out.println("Stage two: ");
        System.out.println("The current value of Registers are:");
        if(tpp.equals("dec")){
           
        System.out.println("Register file at index" + " " + Integer.parseInt(rs, 2) + "[ " + readData1 + "]" + "\n" + "Register file at index" + " " +
                Integer.parseInt(rt, 2) + "[ " + readData2 + "]" + "\n" +
                "Register file at index" + " " + Integer.parseInt(rd, 2) + "[ " + readData3 + "]" + "\n");
        }
        else if(tpp.equals("hex"))
        {
         
           System.out.println("Register file at index" + " 0x" + Integer.toHexString(Integer.parseInt(rs, 2)) + "[ " + readData1 + "]" + "\n" + "Register file at index" + " 0x" +
                Integer.toHexString(Integer.parseInt(rt, 2)) + "[ " + readData2 + "]" + "\n" +
                "Register file at index" + "0x" + Integer.toHexString(Integer.parseInt(rd, 2)) + "[ " + readData3 + "]" + "\n");
        }
        exc = new Execution(readData1, readData2, offset, func, rd,tpp);
        exc.aluOperation();
        
    }

    public void setBeq() {
        
        System.out.println("");
        System.out.println("Stage two: ");
        
        for (int i = 0; i < 32; i++) {
            if (i == Integer.parseInt(rs, 2)) {
                readData1 = data[i];
            } else if (i == Integer.parseInt(rt, 2)) {
                readData2 = data[i];

            }

        }


        System.out.println("The current value of Registers and the offeset value is:");
         if(tpp.equals("dec")){
        System.out.println("Register file(rs value) at index" + " " + Integer.parseInt(rs, 2) + "[ " + readData1 + "]" + "\n" + "Register file(rt value) at index" + " " +
                Integer.parseInt(rt, 2) + "[ " + readData2 + "]" + "\n" +
                "offset" + "[ " + offset + "]" + "\t");
         }
         else if(tpp.equals("hex"))
         {
            System.out.println("Register file(rs value) at index" + " 0x" + Integer.toHexString(Integer.parseInt(rs, 2))
                    + "[ " + readData1 + "]" + "\n" + "Register file(rt value) at index" + "0x " +
                Integer.toHexString(Integer.parseInt(rt, 2)) + "[ " + readData2 + "]" + "\n" +
                "offset" + "[ " + Integer.toHexString(Integer.parseInt(offset)) + "]" + "\t");
         }
             
        System.out.println("");
        System.out.println("Stage three: ");
        
        if (Integer.parseInt(readData1) == Integer.parseInt(readData2)) {
            System.out.println(readData1 + " " + "is equal to" +" "+ readData2);
           
         
        } else {
            System.out.println(readData1 + " " + "not equal to" + " "+ readData2);
        }
         Fetch f = new Fetch();
        f.setPcc(4 * (Integer.parseInt(offset, 2)));
        f.setPC(f.getPcc() / 4);
        a = f.getPC();
         

        for (int i = 0; i < im.length; i++) {
            if (f.getPC() == i) {
                im[i] = im[i];
                System.out.println("----------> @PC" + f.getPcc() + "" + "[" + im[i] + "]");
            } else {
                System.out.println("current value @inst" + i + "" + "[" + im[i] + "]");
            }
        }
        
    }

    public void setMemAddress() {
        System.out.println("");
        System.out.println("Stage two: ");
        for (int i = 0; i < 32; i++) {
            if (i == Integer.parseInt(rs, 2)) {
                readData1 = data[i];
            } else if (i == Integer.parseInt(rt, 2)) {
                readData2 = data[i];

            }

        }
        memAddress = Integer.parseInt(offset, 2) + Integer.parseInt(readData1);
        exc.setMemAddress(Integer.toString(memAddress));
        mem.setMemAddress(Integer.toString(memAddress));

        exc.setMemValue(readData2);
        mem.setMemValue(readData2);
        
        if(tpp.equals("dec")){
        System.out.println("The current value of Registers and the offeset value is:");
        System.out.println("Register file(rs value) at index" + " " + Integer.parseInt(rs, 2) + "[ " + readData1 + "]" + "\n" + "Register file(rt value) at index" + " " +
                Integer.parseInt(rt, 2) + "[ " + readData2 + "]" + "\n" +
                "offset" + "[ " + offset + "]" + "\t");
        }
        else if(tpp.equals("hex")){
            
            System.out.println("The current value of Registers and the offeset value is:");
        System.out.println("Register file(rs value) at index" + " 0x" + Integer.toHexString(Integer.parseInt(rs, 2))
                + "[ " + readData1 + "]" + "\n" + "Register file(rt value) at index" + " 0x" +
                Integer.toHexString(Integer.parseInt(rt, 2)) + "[ " + readData2 + "]" + "\n" +
                "offset" + "[ " + Integer.toHexString(Integer.parseInt(offset,2)) + "]" + "\t");
        }
        System.out.println("");
        System.out.println("Stage three: ");
        System.out.println("the result after alu operation is:(Address in the Memory)" + " " + memAddress);
        mem.MemoryIntialization();
        mem.setTpp(tpp);
        if (opCode.equals("100011")) {
            mem.setRt(rt);
            wb.setData(data);
            mem.loadfromMem();
        } else {
            mem.storeToMem();
        }
    
     
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
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

    public String getReadData1() {
        return readData1;
    }

    public void setReadData1(String readData1) {
        this.readData1 = readData1;
    }

    public String getReadData2() {
        return readData2;
    }

    public void setReadData2(String readData2) {
        this.readData2 = readData2;
    }

    public String getWriteData() {
        return writeData;
    }

    public void setWriteData(String writeData) {
        this.writeData = writeData;
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

    public String getReadData3() {
        return readData3;
    }

    public void setReadData3(String readData3) {
        this.readData3 = readData3;
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
