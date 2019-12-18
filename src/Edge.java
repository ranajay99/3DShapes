public class Edge implements EdgeInterface
{
    private PointInterface edge[]=new Point[2];
    private LinkedList<Triangle> commontri=new LinkedList<>();
    private float dist;
    //points are unordered

    Edge(Point p1,Point p2)
    {
        edge[0]=p1;
        edge[1]=p2;

        float x1=p1.getX(),y1=p1.getY(),z1=p1.getZ();
        float x2=p2.getX(),y2=p2.getY(),z2=p2.getZ();

        dist = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) + (z1-z2)*(z1-z2);
    }

    @Override
    public PointInterface[] edgeEndPoints() {
        return edge;
    }

    public Point pointA()
    {
        return (Point)edge[0];
    }

    public Point pointB()
    {
        return (Point)edge[1];
    }

    @Override
    public boolean equals(Object obj)
    {
        Edge e=(Edge) obj;
        if(this.pointA().equals(e.pointA()) && this.pointB().equals(e.pointB()))
            return true;
        if(this.pointB().equals(e.pointA()) && this.pointA().equals(e.pointB()))
            return true;

        return false;
    }

    public void addtri(Triangle t)
    {
        commontri.add(t);
    }
    public LinkedList<Triangle> tri()
    {
        return commontri;
    }

    public float length()
    {
        return dist;
    }

    public void disp()
    {
        System.out.print("["+edge[0].getX()+","+edge[0].getY()+","+edge[0].getZ()+"]  ");
        System.out.println("["+edge[1].getX()+","+edge[1].getY()+","+edge[1].getZ()+"]");
    }

}
