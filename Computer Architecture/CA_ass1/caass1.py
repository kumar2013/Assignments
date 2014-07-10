from collections import deque
import sys, string, random, array, Numeric, pprint, os
from converter import *

class MakeOp(Converter):
	def __init__(self):
		Converter.__init__(self)
		self.op={}
		self.hexrs=""
		self.hexrt=""
		self.hexrd=""
		self.hexshamt=""
		self.hexid=""
		self.heximm=""
		self.hextarget=""
		self.decrs=0
		self.decrt=0
		self.decrd=0
		self.decshamt=0
		self.decid=0
		self.decimm=0
		self.dectarget=0		
		self.rt={}
		self.funct={}
		self.funct1={}
		self.funct2={}			
		self.instruction=""
		self.ins=""
		self.format=""
		self.input=""
		self.reg={}
		self.printFormat=""

		#self.MIPSFormat()

	def InitDict(self):		

		self.op={0:"n",1:"n",2:"j",3:"jal",4:"beq",5:"bne",6:"blez",7:"bgtz",8:"addi",9:"addiu",10:"slti",11:"sltiu",12:"andi",13:"ori",14:"xori",15:"lui",20:"beql",21:"bnel",22:"blezl",23:"bgtzl",28:"n",32:"lb",33:"lh",34:"lwl",35:"lw",36:"lbu",37:"lhu",38:"lwr",40:"sb",41:"sh",42:"swl",43:"sw",46:"swr",47:"cache",48:"ll",49:"lwc1",50:"lwc2",51:"pref",53:"ldc1",54:"ldc2",56:"sc",57:"swc1",58:"swc2",61:"sdc1",62:"sdc2"}

		self.rt={0:"bltz",1:"bgez",2:"bltzl",3:"bgezl",8:"tgei",9:"tgeiu",10:"tlti",11:"tltiu",12:"tegi",14:"tnei",16:"bltzal",17:"bgezal",18:"bltzall",19:"bgczall"}

		self.funct={0:"sll",1:"n",2:"srl",3:"sra",4:"sllv",6:"srlv",7:"srav",8:"jr",9:"jalr",10:"movz",11:"movn",12:"syscall",13:"break",15:"sync",16:"mfhi",17:"mthi",18:"mflo",19:"mtlo",24:"mult",25:"multu",26:"div",27:"divu",32:"add",33:"addu",34:"sub",35:"subu",36:"and",37:"or",38:"xor",39:"nor",42:"slt",43:"sltu",48:"tge",49:"tgeu",50:"tlt",51:"tltu",52:"teq",54:"tne"}

		self.funct1={0:"movf",1:"movt"}

		self.funct2={0:"madd",1:"maddu",2:"mul",4:"msub",5:"msubu",33:"clz",34:"clo"}

		self.RFormat=["add","addu" ,"and","break","clo","clz","mul","mulo","mulou","or","nor","sll","sllv","sra","srav","srl","srlv","sub","subu","xor","slt","sltu","sl","jalr","movn","movz","movf","movt","tne"]

		self.RFormat1=["div","divu","mult","multu","madd","maddu","msub","teq","tge","tgeu","tltu","tlt"]

		self.IFormat=["addi","addiu","andi","ori","xori","lui","slti","sltiu","bclf","bclt","beq","bgez","bgezal","bqtz","blez","bltzal","bltz","bne","teqi","tgei","tgeiu","tlti","tltiu","lb","lbu","lh","lhu","lw","lwcl","lwl","lwr","ll","sb","sh","sw","swcl","sdcl","swl","sd","swr","sc","bgezl","tnei","bltzall","bgczall","beql","bnel","blezl","bgt","bgtz","bgtzl","sdc1","sdc2","teqi"]

		self.JFormat=["j","jal"]

		self.reg={0:"$zero",1:"$at",2:"$v0",3:"$v1",4:"$a0",5:"$a1",6:"$a2",7:"$a3",8:"$t0",9:"$t1",10:"$t2",11:"$t3",12:"$t4",13:"$t5",14:"$t6",15:"$t7",16:"$s0",17:"$s1",18:"$s2",19:"$s3",20:"$s4",21:"$s5",22:"$s6",23:"$s7",24:"$t8",25:"$t9",28:"$gp",29:"$sp",30:"$fp",31:"$ra"}
	



	def FindFormat(self):			


		if(self.op[self.decop]!="n"):
			
			self.ins=self.op[self.decop]

			if((self.ins in self.RFormat)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decrd in self.reg)or(self.decshamt in self.reg)or(self.decid in self.reg))):
				self.format="R"

			elif((self.ins in self.RFormat1)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decimm in self.reg)or(self.decid in self.reg))):
				self.format="R"


			elif((self.ins in self.IFormat)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decimm in self.reg))):
				self.format="I"
			
			elif((self.ins in self.JFormat)and((self.dectarget in self.reg))):
				
				self.format="J"
			else:
				self.printFormat="x"	
		

		elif((self.decop==0)and(self.decimm in self.funct)and(self.funct[self.decimm] in self.RFormat)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decrd in self.reg)or(self.decshamt in self.reg)or(self.decid in self.reg))):
			
			self.format="R"

		elif((self.decop==0)and(self.decimm in self.funct)and(self.funct[self.decimm] in self.RFormat1)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decimm in self.reg)or(self.decid in self.reg))):
			self.format="R"
	

		elif((self.decop==1)and(self.decrs in self.reg)and(self.decrt in self.reg)and(self.decimm in self.reg)and(self.rt[self.decrt] in self.IFormat)):
			
			self.format="I"
		

		elif((self.decop==28 or self.hexop==0x1c)and(self.decid in self.funct2)and(self.funct2[self.decid] in self.RFormat)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decrd in self.reg)or(self.decshamt in self.reg)or(self.decid in self.reg))):			

			self.format="R"

		elif((self.decop==28 or self.hexop==0x1c)and(self.decid in self.funct2)and(self.funct2[self.decid] in self.RFormat1)and((self.decrs in self.reg)or(self.decrt in self.reg)or(self.decimm in self.reg)or(self.decid in self.reg))):			

			self.format="R"

		else:
			self.printFormat="x"




	def Decompose(self):		
		
		rs=self.bin[6:11]
		self.hexrs=self.BintoHex(rs)
		self.decrs=self.BintoDec(rs)
		Rt=self.bin[11:16]			
		self.hexrt=self.BintoHex(Rt)
		self.decrt=self.BintoDec(Rt)			
		rd=self.bin[16:21]			
		self.hexrd=self.BintoHex(rd)
		self.decrd=self.BintoDec(rd)
		shamt=self.bin[21:26]					
		self.hexshamt=self.BintoHex(shamt)
		self.decshamt=self.BintoDec(shamt)			
		id=self.bin[26:32]			
		self.hexid=self.BintoHex(id)
		self.decid=self.BintoDec(id)	
		imm=self.bin[16:26]					
		self.heximm=self.BintoHex(imm)
		self.decimm=self.BintoDec(imm)		
		target=self.bin[6:32]			
		self.hextarget=self.BintoHex(target)
		self.dectarget=self.BintoDec(target)
		
			




	def PrintResult(self):
		
		if(self.printFormat=="R"):
			print self.input,"	",self.printFormat,"		",self.decop," ",self.decrs," ",self.decrt," ",self.decrd," ",self.decshamt," ",self.decid,"		",self.hexop," ",self.hexrs," ",self.hexrt," ",self.hexrd," ",self.hexshamt," ",self.hexid,"	",self.instruction," ",self.reg[self.decrs],",",self.reg[self.decrd],",",self.reg[self.decrt],"\n"

		elif(self.printFormat=="R1"):

			print self.input,"	","R","		",self.decop," ",self.decrs," ",self.decrt," ",self.decimm," ",self.decid,"		",self.hexop," ",self.hexrs," ",self.hexrt," ",self.heximm," ",self.hexid,"			",self.instruction," ",self.reg[self.decrs]," ",self.reg[self.decrt],"\n"

		elif(self.printFormat=="I"):

			print self.input,"	",self.printFormat,"		",self.decop," ",self.decrs," ",self.decrt," ",self.decimm,"		",self.hexop," ",self.hexrs," ",self.hexrt," ",self.heximm," 			",self.instruction," ",self.reg[self.decrt],",",self.reg[self.decrs],",",self.reg[self.decimm],"\n"

		elif(self.printFormat=="J"):

			print self.input,"	",self.printFormat,"		",self.decop," ",self.dectarget,"				",self.hexop," ",self.hextarget,"				",self.instruction," ",self.reg[self.dectarget],"\n"


		elif(self.printFormat=="x"):
			print self.input,"		INSTRUCTION NOT FOUND		\n"
			




		
		

	def FindOp(self):
		
		self.InitDict()		
		self.Decompose()
		self.FindFormat()
		
		if((self.format=="R") and (self.decop in self.op) and (self.op[self.decop] in self.RFormat)):
			
			self.printFormat="R"
			self.instruction=self.op[self.decop]		

		elif((self.decop==00)and(self.format=="R") and (self.decrd in self.funct) and (self.funct[self.decrd] in self.RFormat)):
			
			self.printFormat="R"
			self.instruction=self.op[self.decrd]
		
		elif((self.decop==00) and (self.format=="R") and (self.decimm in self.funct) and (self.funct[self.decimm] in self.RFormat1)):
		
			self.printFormat="R1"
			self.instruction=self.funct[self.decimm]

		elif((self.decop==00)and(self.format=="R") and(self.decrd in self.funct1) and(self.funct1[self.decrd] in self.funct1)):
			
			self.printFormat="R"
			self.instruction=self.funct1[self.decimm]

		elif((self.decop==28)and(self.format=="R") and (self.decid in self.funct2) and (self.funct2[self.decid] in self.RFormat)):
			
			self.printFormat="R"
			self.instruction=self.funct2[self.decid]

		elif((self.decop==28)and(self.format=="R") and (self.decid in self.funct2) and (self.funct2[self.decid] in self.RFormat1)):
			
			self.printFormat="R1"
			self.instruction=self.funct2[self.decid]

		elif((self.format=="I") and (self.decop in self.op) and (self.op[self.decop] in self.IFormat)):
			
			self.printFormat="I"
			self.instruction=self.op[self.decop]

		elif((self.format=="I") and (self.decrt in self.rt) and (self.rt[self.decrt] in self.IFormat)):
			
			self.printFormat="I"
			self.instruction=self.rt[self.decrt]

		elif((self.format=="J") and (self.decop in self.op) and (self.op[self.decop] in self.JFormat)):
			
			self.printFormat="J"
			self.instruction=self.op[self.decop]

		
			




	def MIPSFormat(self):
		
		self.ReadIp()
		print"\n"

		print "Input		Format		Decomposed(Dec)			Decomposed(Hex)					Instruction\n"
		
		for i in (self.ip):
			
			if(i[0]=='0' and i[1]=='x'):
				self.HextoBin(i)
				
			else:
				n=int(i)
				self.bin=self.DectoBin(n, 32)
				
			self.input=i
			self.lbits=self.bin[:6]
			
			self.hexop=self.BintoHex(self.lbits)
			self.decop=self.BintoDec(self.lbits)
			
		
			self.FindOp()
			self.PrintResult()	

	pass

m=MakeOp()
m.MIPSFormat()


	
	

		


		
at(self):
		
		self.ReadIp()
		print"\n"

		print "Input		Format		Decomposed(Dec)			Decomposed(Hex)					Instruction\n"
		
		for i in (self