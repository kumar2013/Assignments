from collections import deque
import sys, string, random, array, Numeric, pprint, os

class Converter:
	def __init__(self):
		self.ip=[]
		self.tip=[]
		self.bin=""
		self.lbits=""
		self.hexop=""
		self.decop=""

	def ReadIp(self):
		if(len(sys.argv)==2):
			fname=sys.argv[1]
			f=open(fname,"r")
			self.tip.extend(f.readlines())
		else:
			self.ip.append(input('input the 32-bit hexadecimal or decimal number:\n'))
		

		for stri in self.tip:
				self.ip.append(stri.rstrip('\n'))

		
		
					
	def HextoBin(self,hexa):

		dec=int(hexa, 16)
		self.bin=self.DectoBin(dec, 32)
		


	def DectoBin(self,n,c):
		

		return "".join([str((n >> y) & 1) for y in range(c-1, -1, -1)])
		


	def BinFormat(self,s):
		str(s)
		if((len(s))%4==1):
			s='0'+s
		if((len(s))%4==2):
			s='00'+s
		if((len(s))%4==5):
			s='000'+s
		
	
		return s



	def BintoHex(self,b):
		
		c=self.BinFormat(b)

		arr=[c[i:i+4] for i in range(0, len(c), 4)]
		
		list="0x"

		for i in range(len(arr)):
			x=arr[i]

			if(arr[i]=='0000'):
				list=list+'0'
			elif(arr[i]=='0001'):
				list=list+'1'
			elif(arr[i]=='0010'):
				list=list+'2'
			elif(arr[i]=='0011'):
				list=list+'3'
			elif(arr[i]=='0100'):
				list=list+'4'
			elif(arr[i]=='0101'):
				list=list+'5'
			elif(arr[i]=='0110'):
				list=list+'6'
			elif(arr[i]=='0111'):
				list=list+'7'
			elif(arr[i]=='1000'):
				list=list+'8'
			elif(arr[i]=='1001'):
				list=list+'9'
			elif(arr[i]=='1010'):
				list=list+'a'
			elif(arr[i]=='1011'):
				list=list+'b'
			elif(arr[i]=='1100'):
				list=list+'c'
			elif(arr[i]=='1101'):
				list=list+'d'
			elif(arr[i]=='1110'):
				list=list+'e'
			elif(arr[i]=='1111'):
				list=list+'f'
		return list




	def BintoDec(self,c):
		s=self.BinFormat(c)
		dec=int(s, 2)
		
		return dec
	
'):
				list=list+'6'
			elif(arr[i]=='0111'):
				list=list+'7'
			elif(arr[i]=='1000'):
				list=list+'8'
			elif(arr[i]=='1001'):
				list=list+'9'
			elif(arr[i]=='1010'):
				list=list+'a'
			elif(arr[i]=='1011'):
				list=lis