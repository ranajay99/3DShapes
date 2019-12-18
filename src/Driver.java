import java.util.ArrayList;

import java.util.Arrays; 

import java.io.*;
class Driver {


  public static void main(String[] args) throws IOException
  {
	//try{
       	    BufferedReader br = null;
            br = new BufferedReader(new FileReader(args[0]));
	    ShapeInterface shape_intr = new Shape();
            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
		//System.out.println("cmd is "+ Arrays.toString(cmd));	

                if (cmd.length == 0) {
                    System.err.println("Error parsing:1 ");
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

		ArrayList<Float> inp = new ArrayList<>();
		 int firstskip=0;
		for (String val:cmd) {

			if(0==firstskip){
			firstskip++;
			continue;
			}

			inp.add(Float.parseFloat(val.trim()));
			//System.out.print(val + " ");
		}
		
		//System.out.println("arguments " +Arrays.toString(input.toArray()));


		float[] input = new float[inp.size()]; 
		int k = 0;

		for (Float f : inp) {
		    input[k++] = f; 
		}
                switch (cmd[0]) {
			

		

                    case "ADD_TRIANGLE":
			//System.out.println("Add TriangleInterface ")
						boolean bbb=shape_intr.ADD_TRIANGLE(input);
			System.out.println(bbb);
                        break;

                    case "TYPE_MESH":
			//System.out.println("Checking mesh type");
			int mesh_type = shape_intr.TYPE_MESH();
			System.out.println("Mesh type " + mesh_type);
                        break;

                    case "COUNT_CONNECTED_COMPONENTS":
			//System.out.println("Finding number of connected components");
			int count_connected = shape_intr.COUNT_CONNECTED_COMPONENTS();
			System.out.println("Number of connected components = "+ count_connected);
                        break;

                    case "BOUNDARY_EDGES":		
			System.out.println("Getting boundary edges");
			EdgeInterface [] boundary_edges= shape_intr.BOUNDARY_EDGES();
			if(boundary_edges==null)
			{
				System.out.println("null");
				break;
			}
			for(int i=0;i<boundary_edges.length;i++)
			{
				Edge e=(Edge) boundary_edges[i];
				e.disp();
			}
                        break;

                    case "IS_CONNECTED":
			//System.out.println("CHECKING IS_CONNECTED");
			float [] triangle1 = new float[9]; 
			float [] triangle2 = new float[9]; 
			for (int i =0;i<9;i++)
			{
				triangle1[i]=input[i];
			}
			for (int i =9;i<18;i++)
			{
				triangle2[i-9]=input[i];
			}
			boolean is_con = shape_intr.IS_CONNECTED(triangle1, triangle2);
			System.out.println("Is connected = " + is_con);
                        break;

                    case "NEIGHBORS_OF_POINT":
			System.out.println("FINDING NEIGHBORS_OF_POINT" );
			PointInterface [] nbrs_of_point = shape_intr.NEIGHBORS_OF_POINT(input);
			if(nbrs_of_point==null)
			{
				System.out.println("null");
				break;
			}
			for(int i=0;i<nbrs_of_point.length;i++)
			{
				Point p=(Point) nbrs_of_point[i];
				p.disp();
			}
			break;

                    case "NEIGHBORS_OF_TRIANGLE":
			System.out.println("FINDING NEIGHBORS_OF_TRIANGLE" );
			TriangleInterface[] pr=shape_intr.NEIGHBORS_OF_TRIANGLE(input);
			if(pr==null)
			{
				System.out.println("null");
				break;
			}
			for(int i=0;i<pr.length;i++)
			{
				Triangle t=(Triangle) pr[i];
				t.disp();
			}
			break;

                    case "INCIDENT_TRIANGLES":
			System.out.println("FINDING INCIDENT_TRIANGLES " );
			TriangleInterface[] prr=shape_intr.INCIDENT_TRIANGLES(input);
			if(prr==null)
			{
				System.out.println("null");
				break;
			}
			if(prr==null)
			{
				System.out.println("null");
				break;
			}
						for(int i=0;i<prr.length;i++)
						{
							Triangle t=(Triangle) prr[i];
							t.disp();
						}
			break;

                    case "VERTEX_NEIGHBOR_TRIANGLE":
			System.out.println("FINDING VERTEX_NEIGHBOR_TRIANGLE " );
			PointInterface[] pop=shape_intr.VERTEX_NEIGHBOR_TRIANGLE(input);
			if(pop==null)
			{
				System.out.println("null");
				break;
			}
						for(int i=0;i<pop.length;i++)
						{
							Point t=(Point) pop[i];
							t.disp();
						}
                       	 break;


						case "EXTENDED_NEIGHBOR_TRIANGLE":
			System.out.println(" FINDING EXTENDED_NEIGHBOR_TRIANGLE " );
			TriangleInterface [] extended_neighbor_triangle = shape_intr.EXTENDED_NEIGHBOR_TRIANGLE(input);
			if(extended_neighbor_triangle==null)
			{
				System.out.println("null");
				break;
			}
			for(int i=0;i<extended_neighbor_triangle.length;i++)
			{
				Triangle t=(Triangle) extended_neighbor_triangle[i];
				t.disp();
			}
			break;

	          case "MAXIMUM_DIAMETER":
			System.out.println(" Finding diameter " );
			int diameter = shape_intr.MAXIMUM_DIAMETER();
			System.out.println(diameter);
                        break;

                    case "EDGE_NEIGHBOR_TRIANGLE":
			System.out.println(" Finding   EDGE_NEIGHBOR_TRIANGLE ");
			 EdgeInterface [] edge_neighbors_of_triangle = shape_intr.EDGE_NEIGHBOR_TRIANGLE(input);
			 if(edge_neighbors_of_triangle==null)
			 {
				 System.out.println("null");
				 break;
			 }
			 for(int i=0;i<edge_neighbors_of_triangle.length;i++)
			 {
			 	Edge e=(Edge) edge_neighbors_of_triangle[i];
			 	e.disp();
			 }
                        break;


                   case "FACE_NEIGHBORS_OF_POINT":
			System.out.println(" Finding   FACE_NEIGHBORS_OF_POINT ");
			 TriangleInterface [] face_nbrs = shape_intr.FACE_NEIGHBORS_OF_POINT(input);
			 if(face_nbrs==null)
			 {
				 System.out.println("null");
				 break;
			 }
			 for(int i=0;i<face_nbrs.length;i++)
			 {
			 	Triangle t=(Triangle) face_nbrs[i];
			 	t.disp();
			 }
                        break;



                   case "EDGE_NEIGHBORS_OF_POINT":
			System.out.println(" Finding   EDGE_NEIGHBORS_OF_POINT ");
			 EdgeInterface [] edge_nbrs = shape_intr.EDGE_NEIGHBORS_OF_POINT(input);
			 if(edge_nbrs==null)
			 {
				 System.out.println("null");
				 break;
			 }
			 for(int i=0;i<edge_nbrs.length;i++)
			 {
			 	Edge e=(Edge) edge_nbrs[i];
			 	e.disp();
			 }
                        break;


                    case "TRIANGLE_NEIGHBOR_OF_EDGE":
			System.out.println(" Finding TRIANGLE_NEIGHBOR_OF_EDGE ");
			 TriangleInterface [] triangle_neighbors = shape_intr.TRIANGLE_NEIGHBOR_OF_EDGE(input);
			 if(triangle_neighbors==null)
			 {
				 System.out.println("null");
				 break;
			 }
			 for(int i=0;i<triangle_neighbors.length;i++)
			 {
			 	Triangle t=(Triangle) triangle_neighbors[i];
			 	t.disp();
			 }
			break;
		

	          case "CENTROID":
			System.out.println(" Finding Centroid " );
			PointInterface [] centroid_array = shape_intr.CENTROID();
			for(int i=0;i<centroid_array.length;i++)
			{
				Point p=(Point) centroid_array[i];
				p.disp();
			}

                        break;
                    case "CENTROID_OF_COMPONENT":
			System.out.println(" Finding CENTROID_OF_COMPONENT ");
			 PointInterface centroid_of_component = shape_intr.CENTROID_OF_COMPONENT(input);
						Point ppp=(Point)centroid_of_component;
						ppp.disp();
                        break;

                    case "CLOSEST_COMPONENTS":
			System.out.println(" Finding CLOSEST_COMPONENTS ");
			  PointInterface [] closest_vertices = shape_intr.CLOSEST_COMPONENTS();
			  if(closest_vertices==null)
			  	System.out.println("Null");
			  else {
				  Point p1 = (Point) closest_vertices[0];
				  Point p2 = (Point) closest_vertices[1];
				  p1.disp();
				  p2.disp();
			  }
			break;
		


	//	default :System.out.println(cmd[0] +" not found");	
	//		break;
			
		}

           // }
	}
	//catch(Exception e)
	//{
	//	System.err.println("Error parsing: 2	 " +e);
	//}
	

}

}
