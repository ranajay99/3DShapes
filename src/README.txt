Description of the assignment


//Time complexities are mentioned wherever applicable
//O(...)

////////Node//////////

data element
Node<T> next


/////Linkedlist///////

Node head
Node tail

add(data element)
adds element to the tail
O(1) 

search(data element)
searches for the element which share some primitive attributes
checked via equals()
O(n)  

n=number of nodes


/*******************Point*******************/

contains Linkedlists of connected Points, Edges, Triangles
and corresponding functons to insert and access the objects


public float getX() 
returns the x coordinate	
	
public float getY() 
returns the y coordinate

public float getZ() 
returns the z coordinate

public float[] getXYZcoordinate() 
returns the array of the  three coordinates

public boolean equals(Object obj)
Point object
checks equality for current and given point


/**********************Edge***********************/

contains Linkedlists of connected Triangles
and corresponding functons to insert and access the objects


public float f()
return square of length of edge

public PointInterface[] edgeEndPoints()
return the array of points

public boolean equals(Object obj)
Edge object
checks equality for currrent and given edge

public float length()
return the edge length


/********************Triangle*******************/

contains Linkedlists of edge connected Triangles
and corresponding functons to insert and access the objects


public PointInterface[] triangle_coord()
returns a point array of the three coordinates

public boolean equals(Object obj)
Triangle object
checks equality for current and given triangle

public void setIndex(int d)
sets the order in which the triangle is added


/*********************Shape***********************/


contains Linkedlists of all Points, Edges, Triangles
all arrays requiring sorting use merge sort
O(nlogn) where n is the number of elements


public boolean ADD_TRIANGLE(float [] triangle_coord)
checks if the given coordinates already exist
if not create one
O(no. of points)
each is adjacent to other and added to each adjacent point Linkedlist
check if the given edge already exists
O(no. of edges)
if not create one
a new triangle is created
adjacent triangles are added to this triangle and vice versa
O(no. of adjacent triangles)
add the new triangle to the given points' and edges' individual triangle list
O(1) each

public int TYPE_MESH()
for every edge check the corresponding number of triangles
O(no. of triangles)

public EdgeInterface [] BOUNDARY_EDGES()
check every edge for number of triangles equal to one
O(total no. of edges)

public int COUNT_CONNECTED_COMPONENTS()
performed breadth first search (bfs) to find the connected adjacent triangles
indexing of triangles is useful to mark the traversed triangles
O(no. of edges + no. of triangles) per component
n=no. of triangles
m=no. of edges
m~n^2
O(n^2)

public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord)
search the corresponding triangle and iterate over the list of adjacent triangles
O(total no. of triangles) for searching plus
O(no. of corresponding triangles) for iteration

public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord)
return all the edge forming triangle
O(1) since already calculated before

public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord)
return all the points forming triangle
O(1) since already calculated before

public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord)
returns the pointwise neighbours
O(total no. of triangles) for searching plus
O(3*no. of triangles) for the three points

public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates)
for corresponding point returns all the triangles
O(no. of points) for searching the point plus
O(no. of triangles) for adding into an array

public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates)
for corresponding point returns all the adjacent points
O(no. of points) for searching the point plus
O(no. of subset points) for adding into an array

public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates)
returns all the corresponding edges of the point
O(no. of points) for searching the point plus
O(no. of subset edges) for adding into an array

public boolean IS_CONNECTED(Triangle t1,Triangle t2)
use bfs to mark index of connected components of t1
check if t2 shares one of the indices
O(no. of edges+no. of triangles)
n=no. of triangles
m=no. of edges
m~n^2
O(n^2)

public PointInterface CENTROID_OF_COMPONENT(float [] point_coordinates)
average of all the points in the connected components
use bfs to mark all the connected components
points of triangles are obtained
O(no. of edges+no. of triangles)
n=no. of triangles
m=no. of edges
m~n^2
O(n^2)

public int MAXIMUM_DIAMETER()
for every triangle in a component use bfs to calculate the minpath to another triangle
O(n*(n+m))
n=no. of triangles
m=no. of edges
m~n^2
hence O(n^3)

public PointInterface [] CLOSEST_COMPONENTS()
for every two points first check if they lie in same component 
if not find the distance 
find the min distance among them
O(n^2*(n+m))
n=no. of triangles
m=no. of edges
m~n^2
O(n^4)
