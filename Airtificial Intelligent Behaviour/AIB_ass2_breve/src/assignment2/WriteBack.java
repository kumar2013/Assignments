/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2;

/**
 *
 * @author mcs10aee
 */
public class WriteBack {
    
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
    
    
    public void setWB(){
        System.out.println("Stage Five: ");
        
      if(tpp.equals("dec")){  
       for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rt,2) == i){
                getData()[i] = memresult;
              System.out.println(" --------> WB" + i + "" + "["+ "0x" + getData()[i] + "]");
           }
           else
            System.out.println(" Register file" + i + "" + "["+ "0x" + getData()[i] + "]");
           
       }
      }
      else if(tpp.equals("hex"))
      {
         for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rt,2) == i){
                getData()[i] = Integer.toHexString(Integer.parseInt(memresult));
              System.out.println(" --------> WB" + i + "" + "["+ "0x" + getData()[i] + "]");
           }
           else
            System.out.println(" Register file" + i + "" + "["+ "0x" + getData()[i] + "]");
           
       }
      }
         System.out.println("<--------------- End of one instrucction cycle ---------------->");
    }
      public void setRtypeWB(){
       System.out.println("");   
       System.out.println("Stage Five :");
       System.out.println("The current value of Registers which has been changed during execution:");
      
       if(tpp.equals("dec")){  
           
        for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rd,2) == i){
              getData()[i] = Integer.toString(getRdValue());
              System.out.println(" --------> WB" + i + "" + "[" + getData()[i] + "]");
           }
           else
            System.out.println(" Register file" + i + "" + "[" + getData()[i] + "]");
           
       }
        
       }
       else
       {
          for(int i = 0; i< 32; i++){
           if(Integer.parseInt(rd,2) == i){
              getData()[i] = Integer.toHexString(getRdValue());
              System.out.println(" --------> WB" + "0x"+i  + "[" + "0x"+ getData()[i] + "]");
           }
           else
            System.out.println(" Register file" + "0x" + i +  "[" + "0x"+ getData()[i] + "]");
          
               
       }
       }
       
       System.out.println("<--------------- End of one instrucction cycle ---------------->");    
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
