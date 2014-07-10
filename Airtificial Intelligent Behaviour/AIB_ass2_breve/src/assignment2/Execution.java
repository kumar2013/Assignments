/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author mcs10aee
 */
public class Execution {

    private String readData1;
    private String readData2;
    private String offset;
    private String func;
    private String rd;
    private String tpp;
    private int result;
    private String logicResult;
    private String memAddress;
    private String memValue;
    WriteBack wb = new WriteBack();
     Execution() {
     
    }
    Execution(String rd1, String rd2, String offst, String fun,String rdAdd,String tp) {

        readData1 = rd1;
        readData2 = rd2;
        offset = offst;
        func = fun;
        rd = rdAdd;
        tpp = tp;
        
    }

    public String aluOperation() {
        
        System.out.println("Stage three : ");
        if (func.equals("100000")) {
            
            result = Integer.parseInt(readData1) + Integer.parseInt(readData2);
           
            System.out.println("the result after alu operation is:"+ " "+result);
            wb.setRdValue(result);
            wb.setRd(rd);
            wb.setTpp(tpp);
            wb.setRtypeWB();
            return Integer.toBinaryString(result);
        } else if (func.equals("100010")) {
            result = Integer.parseInt(readData1) - Integer.parseInt(readData2);
            System.out.println("the result after alu operation is:"+ " "+result);
            wb.setRdValue(result);
            wb.setRd(rd);
            wb.setTpp(tpp);
            wb.setRtypeWB();
            return Integer.toBinaryString(result);
        } else if (func.equals("100100")) {
            result = Integer.parseInt(readData1) & Integer.parseInt(readData2);
            System.out.println("the result after alu operation is:"+ " "+result);
            wb.setRdValue(result);
            wb.setRd(rd);
            wb.setTpp(tpp);
            wb.setRtypeWB();
            return Integer.toBinaryString(result);
        } else if (func.equals("100101")) {
            result = Integer.parseInt(readData1) | Integer.parseInt(readData2);
            System.out.println("the result after alu operation is:"+ " "+result);
            wb.setRdValue(result);
            wb.setRd(rd);
            wb.setTpp(tpp);
            wb.setRtypeWB();
            return Integer.toBinaryString(result);
        } else if (func.equals("101010")) {
            if (Integer.parseInt(readData1) < Integer.parseInt(readData2)) {
                result = 1;
               
                
            } else {
                result = 0;
            }
            wb.setRdValue(result);
            wb.setRd(rd);
            wb.setTpp(tpp);
            wb.setRtypeWB();
            return Integer.toBinaryString(result);
        }
        else 
          System.out.println("Error"+ " "+func);  
        
         
           
        return null;
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
