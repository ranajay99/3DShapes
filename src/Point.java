public class Point implements PointInterface
{
    private float point[]=new float[3];
    private LinkedList<Point> adjacentpoints=new LinkedList<>();
    private LinkedList<Triangle> commontri=new LinkedList<>();
    private LinkedList<Edge> commedge=new LinkedList<>();

    //coordinates are ordered

    Point(float a,float b,float c)
    {
        point[0]=a;
        point[1]=b;
        point[2]=c;
    }


    @Override
    public float getX() {
        return point[0];
    }

    @Override
    public float getY() {
        return point[1];
    }

    @Override
    public float getZ() {
        return point[2];
    }

    @Override
    public float[] getXYZcoordinate() {
        return point;
    }

    @Override
    public boolean equals(Object obj)
    {
        Point p=(Point) obj;
        if(this.getX()==p.getX())
            if(this.getY()==p.getY())
                if(this.getZ()==p.getZ())
                    return true;

        return false;
    }

    public void addpoint(Point p)
    {
        if(adjacentpoints.search(p)==null)
            adjacentpoints.add(p);
    }
    public LinkedList<Point> adjp()
    {
        return adjacentpoints;
    }

    public void adddedge(Edge e)
    {
        if(commedge.search(e)==null)
            commedge.add(e);
    }
    public LinkedList<Edge> commedge()
    {
        return commedge;
    }

    public void addtri(Triangle t)
    {
        commontri.add(t);
    }

    public LinkedList<Triangle> tri()
    {
        return commontri;
    }

    public void setPoint(float a,float b,float c)
    {
        point[0]=a;
        point[1]=b;
        point[2]=c;
    }

    public boolean pol(Point p)
    {
        if (this.getX() < p.getX())
            return true;
        else if (this.getX() > p.getX())
            return false;
        else
        {
            if (this.getY() < p.getY())
                return true;
            else if (this.getY() > p.getY())
                return false;
            else
            {
                if (this.getZ() < p.getZ())
                    return true;
                else if (this.getZ() > p.getZ())
                    return false;
                else
                    return true;
            }
        }

    }
    public void disp()
    {
        System.out.println("["+point[0]+","+point[1]+","+point[2]+"]");
    }

}
