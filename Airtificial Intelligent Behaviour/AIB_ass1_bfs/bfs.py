import sys
class Node:
    def __init__(self,x,y,link):
      self.x=x
      self.y=y
      self.link=link

Xs=0
Ys=0
startNode = Node(0,0,None)
endNode = Node(0,0,None)
mapData=[]
queue =[]
Traveled = []

## Read the MAP file
if len(sys.argv)>1:
	f = open(sys.argv[1], 'r')
Xe=int(f.readline())
Ye=int(f.readline())
for i in range(Ye):
 mapData.append(f.readline())

# Find the Start and End points in the Map
for i in range(Ye):   
    for j in range(Xe):       
             if mapData[i][j] =='s':
                startNode.x = i
                startNode.y = j
   
	     if mapData[i][j] =='g':
                endNode.x = i
                endNode.y = j

def nextNode(i,j,link,queue,Traveled,mapData,Ye,Xe):
	if ( i < Ye and j < Xe and i>=0 and j>=0 and mapData[i][j]!='x' ):
		if not (i,j) in [(n.x,n.y) for n in Traveled]:
			leaf = Node(i,j,link)
		        if not (leaf.x,leaf.y) in [(n.x,n.y) for n in queue] :
				queue.append(leaf)

# Breadth first search
def BFS(queue,mapData,Traveled):
	while(len(queue)>0):
		node = queue.pop(0)

		if not (node.x,node.y) in [(n.x,n.y) for n in Traveled] :
			Traveled.append(node)

		i=node.x
		j=node.y

		# check if current node is the goal node
		if node.x == endNode.x and node.y == endNode.y :
			return node

		nextNode(i-1,j,node,queue,Traveled,mapData,Ye,Xe) #left
		nextNode(i+1,j,node,queue,Traveled,mapData,Ye,Xe) #right
		nextNode(i,j-1,node,queue,Traveled,mapData,Ye,Xe) #up
		nextNode(i,j+1,node,queue,Traveled,mapData,Ye,Xe) #down
	        
# Finding shortest path
queue.append(startNode)
rNode = BFS(queue,mapData,Traveled)
shortestPath=[]
while not rNode == None:
	shortestPath.append(rNode)
	rNode = rNode.link

# Output
print 'Given Map:' 
print  'Width',Xe,'Height',Ye
print  mapData

print "Traveled By: "
for n in Traveled:
	print (str(n.y)+ ' '+str(n.x) )   
print 'Shortest Path:'
for node in shortestPath[::-1]:
	print str(node.y) + ' ' + str(node.x)

