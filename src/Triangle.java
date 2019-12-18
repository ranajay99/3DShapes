public class Triangle implements TriangleInterface
{
    private PointInterface tri[]=new Point[3];

    private LinkedList<Triangle> adjtri=new LinkedList<>();
    private int index;

    private Edge ed[]=new Edge[3];
    //private int compno;
    private float sumX;
    private float sumY;
    private float sumZ;
    //private LinkedList<Triangle> adjtri=new LinkedList<>();
    //unordered non collinear points

    Triangle(Point p1,Point p2,Point p3,Edge e1,Edge e2,Edge e3)
    {
        tri[0]=p1;
        tri[1]=p2;
        tri[2]=p3;

        sumX=p1.getX()+p2.getX()+p3.getX();
        sumY=p1.getY()+p2.getY()+p3.getY();
        sumZ=p1.getZ()+p2.getZ()+p3.getZ();

        ed[0]=e1;
        ed[1]=e2;
        ed[2]=e3;
    }
    //false triangle
    Triangle(Point p1,Point p2,Point p3)
    {
        tri[0]=p1;
        tri[1]=p2;
        tri[2]=p3;
    }

    @Override
    public PointInterface[] triangle_coord() {
        return tri;
    }

    @Override
    public boolean equals(Object obj)
    {
        Triangle t=(Triangle) obj; 
        Point p1[]=(Point[]) this.triangle_coord();
        Point p2[]=(Point[]) t.triangle_coord();

        /*
        float Ax1=p1[0].getX(),Ay1=p1[0].getY(),Az1=p1[0].getZ();
        float Bx1=p1[1].getX(),By1=p1[1].getY(),Bz1=p1[1].getZ();
        float Cx1=p1[2].getX(),Cy1=p1[2].getY(),Cz1=p1[2].getZ();

        float Ax2=p2[0].getX(),Ay2=p2[0].getY(),Az2=p2[0].getZ();
        float Bx2=p2[1].getX(),By2=p2[1].getY(),Bz2=p2[1].getZ();
        float Cx2=p2[2].getX(),Cy2=p2[2].getY(),Cz2=p2[2].getZ();
        */

        if(p1[0].equals(p2[0]))
        {
            if(p1[1].equals(p2[1]))
                if(p1[2].equals(p2[2]))
                    return true;
            if(p1[1].equals(p2[2]))
                if(p1[2].equals(p2[1]))
                    return true;
        }
        if(p1[0].equals(p2[1]))
        {
            if(p1[1].equals(p2[0]))
                if(p1[2].equals(p2[2]))
                    return true;
            if(p1[1].equals(p2[2]))
                if(p1[2].equals(p2[0]))
                    return true;
        }
        if(p1[0].equals(p2[2]))
        {
            if(p1[1].equals(p2[1]))
                if(p1[2].equals(p2[0]))
                    return true;
            if(p1[1].equals(p2[0]))
                if(p1[2].equals(p2[1]))
                    return true;
        }
        return false;

    }
    public float getSumX()
    {
        return sumX;
    }
    public float getSumY()
    {
        return sumY;
    }
    public float getSumZ()
    {
        return sumZ;
    }


    public Edge[] triangle_edge()
    {
        return ed;
    }

    public void addtri(Triangle t)
    {
        adjtri.add(t);
    }
    public LinkedList<Triangle> adjtri()
    {
        return adjtri;
    }
    public void setIndex(int d)
    {
        index=d;
    }
    public int getIndex()
    {
        return index;
    }
    public void disp()
    {
        System.out.print("["+tri[0].getX()+","+tri[0].getY()+","+tri[0].getZ()+"]  ");
        System.out.print("["+tri[1].getX()+","+tri[1].getY()+","+tri[1].getZ()+"]  ");
        System.out.println("["+tri[2].getX()+","+tri[2].getY()+","+tri[2].getZ()+"]  ");
    }

}
