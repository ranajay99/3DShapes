public class Shape implements ShapeInterface
{
    LinkedList<Point> allpoints=new LinkedList<>();
    LinkedList<Edge> alledges=new LinkedList<Edge>();
    LinkedList<Triangle> alltriangles=new LinkedList<>();

    int ind=0;

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public boolean ADD_TRIANGLE(float [] triangle_coord)
    {
        if(collinear(triangle_coord))
            return false;

        float pp[]=triangle_coord;
        float x1=pp[0],y1=pp[1],z1=pp[2];
        float x2=pp[3],y2=pp[4],z2=pp[5];
        float x3=pp[6],y3=pp[7],z3=pp[8];

        Point p1=new Point(x1,y1,z1);
        Point p2=new Point(x2,y2,z2);
        Point p3=new Point(x3,y3,z3);

        Point p11=allpoints.search(p1);
        Point p22=allpoints.search(p2);
        Point p33=allpoints.search(p3);

        if(p11==null)
        {
            p11=p1;
            allpoints.add(p1);
        }
        if(p22==null)
        {
            p22=p2;
            allpoints.add(p2);
        }
        if(p33==null)
        {
            p33=p3;
            allpoints.add(p3);
        }
        p11.addpoint(p22);p11.addpoint(p33);
        p22.addpoint(p11);p22.addpoint(p33);
        p33.addpoint(p11);p33.addpoint(p22);

        Edge e1=new Edge(p11,p22);
        Edge e2=new Edge(p22,p33);
        Edge e3=new Edge(p11,p33);

        Edge e11=alledges.search(e1);
        Edge e22=alledges.search(e2);
        Edge e33=alledges.search(e3);

        if(e11==null)
        {
            e11=e1;
            alledges.add(e1);
        }
        if(e22==null)
        {
            e22=e2;
            alledges.add(e2);
        }
        if(e33==null)
        {
            e33=e3;
            alledges.add(e3);
        }

        Triangle t=new Triangle(p11,p22,p33,e11,e22,e33);
        alltriangles.add(t);
        t.setIndex(ind++);

        p11.addtri(t);
        p22.addtri(t);
        p33.addtri(t);

        Node<Triangle> start=e11.tri().head;
        while(start!=null)
        {
            t.addtri(start.data);
            start.data.addtri(t);
            start=start.next;
        }
        start=e22.tri().head;
        while(start!=null)
        {
            t.addtri(start.data);
            start.data.addtri(t);
            start=start.next;
        }
        start=e33.tri().head;
        while(start!=null)
        {
            t.addtri(start.data);
            start.data.addtri(t);
            start=start.next;
        }

        p11.adddedge(e11);p11.adddedge(e33);
        p22.adddedge(e11);p22.adddedge(e22);
        p33.adddedge(e22);p33.adddedge(e33);

        e11.addtri(t);
        e22.addtri(t);
        e33.addtri(t);

        return true;
    }
    public boolean collinear(float pp[])
    {
        float x1=pp[0],y1=pp[1],z1=pp[2];
        float x2=pp[3],y2=pp[4],z2=pp[5];
        float x3=pp[6],y3=pp[7],z3=pp[8];

        float d1=distance(x1,y1,z1,x2,y2,z2);
        float d2=distance(x1,y1,z1,x3,y3,z3);
        float d3=distance(x2,y2,z2,x3,y3,z3);

        if(abs(d1+d2-d3)<0.001 || abs(d3+d2-d1)<0.001 || abs(d1+d3-d2)<0.001)
            return true;

        return false;
    }
    public float distance(Point p1,Point p2)
    {
        return distance(p1.getX(),p1.getY(),p1.getZ(),p2.getX(),p2.getY(),p2.getZ());
    }
    public float distance(float a1,float b1,float c1,float a2,float b2,float c2)
    {
        return (float)Math.sqrt((a1-a2)*(a1-a2) + (b1-b2)*(b1-b2) + (c1-c2)*(c1-c2));
    }
    public float abs(float a)
    {
        if(a<0)
            return -a;
        return a;
    }

    //
    public int TYPE_MESH()
    {
        int t=1;
        Node<Edge> start=alledges.head;
        while(start!=null)
        {
            Edge e = start.data;
            if(e.tri().size()==1)
                t=2;
            else if(e.tri().size()>2)
                return 3;
            start=start.next;
        }
        return t;
    }

    //
    public EdgeInterface [] BOUNDARY_EDGES()
    {
        int l=alledges.size();
        Node<Edge> start=alledges.head;

        Edge arr[]=new Edge[l];
        int ll=0;

        while(start!=null)
        {
            Edge e = start.data;
            if(e.tri().size()==1)
            {
                arr[ll++] = e;
            }
            start=start.next;
        }
        if(ll==0)
            return null;
        mergesort(arr,0,ll-1);

        EdgeInterface qq[]=new EdgeInterface[ll];
        for(int i=0;i<ll;i++)
            qq[i]=arr[i];

        return qq;
    }
    public void mergesort(Edge[] arr,int a,int b)
    {
        if(a==b)
            return ;
        int mid=(a+b)/2;
        mergesort(arr,a,mid);
        mergesort(arr,mid+1,b);
        merge(arr,a,b);

    }
    public void merge(Edge[] arr,int a,int b)
    {
        Edge frr[]=new Edge[b-a+1];
        int j=a,mid=(a+b)/2,k=mid+1;
        for(int i=0;i<=b-a;i++)
        {
            if(j<=mid && (k>b || arr[j].length()<arr[k].length()) )
                frr[i]=arr[j++];
            else
                frr[i]=arr[k++];
        }
        for(int i=0;i<=b-a;i++)
            arr[i+a]=frr[i];
    }

    //
    public int COUNT_CONNECTED_COMPONENTS()
    {
        int l=alltriangles.size();
        int mark[]=new int[l];

        Node<Triangle> start=alltriangles.head;

        int d=0,i=0;
        while(start!=null)
        {
            if(mark[i++]==0)
            {
                connectedcomp(mark, start.data, d + 1);
                d++;
            }
            start=start.next;
        }
        return d;
    }
    /*public void connectedcomp(int[] mark,Triangle t,int k)
    {
        if(mark[t.getIndex()]>0)
            return;

        mark[t.getIndex()]=k;

        LinkedList<Triangle> tat=t.adjtri();
        Node<Triangle> start=tat.head;
        while(start!=null)
        {
            Triangle ty=start.data;
            connectedcomp(mark,ty,k);
            start=start.next;
        }
    }*/
    public void connectedcomp(int[] mark,Triangle t,int k)
    {
        mark[t.getIndex()]=k;

        LinkedList<Triangle> tt=new LinkedList<>();
        tt.add(t);
        Node<Triangle> start=tt.head;
        while(start!=null)
        {
            LinkedList<Triangle> yy=start.data.adjtri();
            Node<Triangle> op=yy.head;
            while(op!=null)
            {
                if(mark[op.data.getIndex()]==0)
                {
                    mark[op.data.getIndex()]=k;
                    tt.add(op.data);
                }
                op=op.next;
            }
            start=start.next;
        }
    }

    public Triangle searchtriangle(float [] pp)
    {
        float x1=pp[0],y1=pp[1],z1=pp[2];
        float x2=pp[3],y2=pp[4],z2=pp[5];
        float x3=pp[6],y3=pp[7],z3=pp[8];

        Point p1=new Point(x1,y1,z1);
        Point p2=new Point(x2,y2,z2);
        Point p3=new Point(x3,y3,z3);

        Triangle t=new Triangle(p1,p2,p3);

        t=alltriangles.search(t);

        return t;
    }
    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord)
    {
        Triangle t=searchtriangle(triangle_coord);

        if(t==null)
            return null;

        Node<Triangle> start=t.adjtri().head;
        int l=t.adjtri().size();
        if(l==0)
            return null;
        TriangleInterface ttp[]=new Triangle[l];
        int i=0;
        while(start!=null)
        {
            ttp[i++]=start.data;
            start=start.next;
        }

        mergesort2((Triangle[]) ttp,0,l-1);

        return ttp;

    }
    public void mergesort2(Triangle[] arr,int a,int b)
    {
        if(a==b)
            return ;
        int mid=(a+b)/2;
        mergesort2(arr,a,mid);
        mergesort2(arr,mid+1,b);
        merge2(arr,a,b);

    }
    public void merge2(Triangle[] arr,int a,int b)
    {
        Triangle frr[]=new Triangle[b-a+1];
        int j=a,mid=(a+b)/2,k=mid+1;
        for(int i=0;i<=b-a;i++)
        {
            if(j<=mid && (k>b || arr[j].getIndex()<arr[k].getIndex()) )
                frr[i]=arr[j++];
            else
                frr[i]=arr[k++];
        }
        for(int i=0;i<=b-a;i++)
            arr[i+a]=frr[i];
    }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord)
    {
        Triangle t=searchtriangle(triangle_coord);

        if(t==null)
            return null;

        //Point pp[]=(Point[]) t.triangle_coord();
        EdgeInterface ee[]=t.triangle_edge();

        return ee;
    }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord)
    {
        Triangle t=searchtriangle(triangle_coord);

        if(t==null)
            return null;

        PointInterface pp[]=t.triangle_coord();

        return pp;
    }

    //INPUT [x1,y1,z1,x2,y2,z2,x3,y3,z3]
    public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord)
    {
        Triangle t=searchtriangle(triangle_coord);

        if(t==null)
            return null;

        Point pp[]=(Point[]) t.triangle_coord();
        //Edge ee[]=t.triangle_edge();

        LinkedList<Triangle> ert=new LinkedList<>();

        LinkedList<Triangle> p1=pp[0].tri();
        LinkedList<Triangle> p2=pp[1].tri();
        LinkedList<Triangle> p3=pp[2].tri();

        Node<Triangle> start=p1.head;
        while(start!=null)
        {
            if(start.data!=t)
                if(ert.search(start.data)==null)
                    ert.add(start.data);
            start=start.next;
        }
        start=p2.head;
        while(start!=null)
        {
            if(start.data!=t)
                if(ert.search(start.data)==null)
                    ert.add(start.data);
            start=start.next;
        }
        start=p3.head;
        while(start!=null)
        {
            if(start.data!=t)
                if(ert.search(start.data)==null)
                    ert.add(start.data);
            start=start.next;
        }

        start=ert.head;
        int i=0,ll=ert.size();
        TriangleInterface ttr[]=new Triangle[ll];
        while(start!=null)
        {
            ttr[i++]=start.data;
            start=start.next;
        }

        mergesort2((Triangle[]) ttr,0,ll-1);

        return ttr;
    }

    public Point searchpoint(float[] pp)
    {
        Point p=new Point(pp[0],pp[1],pp[2]);
        p=allpoints.search(p);

        return p;
    }

    //INPUT [x,y,z]
    public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates)
    {
        Point p=searchpoint(point_coordinates);
        if(p==null)
            return null;

        LinkedList<Triangle> tt=p.tri();
        int l=tt.size();
        if(l==0)
            return null;
        Node<Triangle> start=tt.head;

        TriangleInterface rr[]=new Triangle[l];
        for(int i=0;i<l;i++)
        {
            rr[i]=start.data;
            start=start.next;
        }
        return rr;
    }

    // INPUT [x,y,z]
    public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates)
    {
        Point p=searchpoint(point_coordinates);
        if(p==null)
            return null;

        LinkedList<Point> tt=p.adjp();

        int l=tt.size();
        Node<Point> start=tt.head;

        PointInterface rr[]=new Point[l];
        for(int i=0;i<l;i++)
        {
            rr[i]=start.data;
            start=start.next;
        }
        return rr;
    }


    // INPUT[x,y,z]
    public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates)
    {
        Point p=searchpoint(point_coordinates);
        if(p==null)
            return null;

        LinkedList<Edge> tt=p.commedge();
        int l=tt.size();
        Node<Edge> start=tt.head;

        EdgeInterface rr[]=new Edge[l];
        for(int i=0;i<l;i++)
        {
            rr[i]=start.data;
            start=start.next;
        }
        return rr;
    }


    // INPUT[x,y,z]
    public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates)
    {
        return INCIDENT_TRIANGLES(point_coordinates);
    }



// INPUT // [xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 , xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3]   where xa1,ya1,za1,xa2,ya2,za2,xa3,ya3,za3 are 3 coordinates of first triangle and xb1,yb1,zb1,xb2,yb2,zb2,xb3,yb3,zb3 are coordinates of second triangle as given in specificaition.

    public boolean IS_CONNECTED(Triangle t1,Triangle t2)
    {
        if(t1==null || t2==null)
            return false;

        int l=alltriangles.size();
        int mark[]=new int[l];

        connectedcomp(mark,t1,1);

        if(mark[t2.getIndex()]==1)
            return true;
        return false;
    }
    public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2)
    {
        Triangle t1=searchtriangle(triangle_coord_1);
        Triangle t2=searchtriangle(triangle_coord_2);

        if(t1==null || t2==null)
            return false;

        int l=alltriangles.size();
        int mark[]=new int[l];

        connectedcomp(mark,t1,1);

        if(mark[t2.getIndex()]==1)
            return true;
        return false;
    }

    // INPUT [x1,y1,z1,x2,y2,z2] // where (x1,y1,z1) refers to first end point of edge and  (x2,y2,z2) refers to the second.
    public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates)
    {
        float pp[]=edge_coordinates;
        Point p1=new Point(pp[0],pp[1],pp[2]);
        Point p2=new Point(pp[3],pp[4],pp[5]);

        Edge e=new Edge(p1,p2);
        e=alledges.search(e);

        if(e==null)
            return null;

        LinkedList<Triangle> qw=e.tri();
        int l=qw.size();
        TriangleInterface er[]=new Triangle[l];
        Node<Triangle> start=qw.head;

        for(int i=0;i<l;i++)
        {
            er[i]=start.data;
            start=start.next;
        }

        return er;
    }

    //
    public PointInterface [] CENTROID ()
    {
        int l=alltriangles.size();
        int mark[]=new int[l];

        Node<Triangle> startt=alltriangles.head;
        Point [] plk=new Point[allpoints.size()];

        int d=0,i=0;
        while(startt!=null)
        {
            if(mark[i++]==0)
            {
                LinkedList<Triangle> lk=new LinkedList<>();
                connectedcomp2(mark, startt.data, lk, d + 1);
                double ff[]=new double[3];
                int y=0;

                LinkedList<PointInterface> pep=new LinkedList<>();
                Node<Triangle> start=lk.head;
                while(start!=null)
                {
                    PointInterface po[]=start.data.triangle_coord();
                    for(int j=0;j<3;j++)
                    {
                        if(pep.search(po[j])==null)
                        {
                            pep.add(po[j]);
                            y++;
                            ff[0]=ff[0]+po[j].getX();
                            ff[1]=ff[1]+po[j].getY();
                            ff[2]=ff[2]+po[j].getZ();
                        }
                    }
                    start=start.next;
                }
                ff[0]=ff[0]/y;ff[1]=ff[1]/y;ff[2]=ff[2]/y;
                Point ppp=new Point((float) ff[0],(float) ff[1],(float) ff[2]);

                plk[d]=ppp;
                //System.out.println(y);

                d++;
            }
            startt=startt.next;
        }

        PointInterface arr[]=new Point[d];

        mergesort3(plk,0,d-1);
        for(i=0;i<d;i++)
        {
            arr[i]=plk[i];
        }


        return arr;
    }
    public void mergesort3(Point[] arr,int a,int b)
    {
        if(a==b)
            return ;
        int mid=(a+b)/2;
        mergesort3(arr,a,mid);
        mergesort3(arr,mid+1,b);
        merge3(arr,a,b);

    }
    public void merge3(Point[] arr,int a,int b)
    {
        Point frr[]=new Point[b-a+1];
        int j=a,mid=(a+b)/2,k=mid+1;
        for(int i=0;i<=b-a;i++)
        {
            if(j<=mid && (k>b || arr[j].pol(arr[k])) )
                frr[i]=arr[j++];
            else
                frr[i]=arr[k++];
        }
        for(int i=0;i<=b-a;i++)
            arr[i+a]=frr[i];
    }


    // INPUT [x,y,z]
    public PointInterface CENTROID_OF_COMPONENT(float [] point_coordinates)
    {
        Point p=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
        p=allpoints.search(p);

        Triangle t=p.tri().head.data;
        //p=new Point(0,0,0);

        int mark[]=new int[alltriangles.size()];

        LinkedList<Triangle> lk=new LinkedList<>();
        connectedcomp2(mark, t, lk,1);
        double ff[]=new double[3];
        int y=0;

        LinkedList<PointInterface> pep=new LinkedList<>();
        Node<Triangle> start=lk.head;
        while(start!=null)
        {
            PointInterface po[]=start.data.triangle_coord();
            for(int j=0;j<3;j++)
            {
                if(pep.search(po[j])==null)
                {
                    pep.add(po[j]);
                    y++;
                    ff[0]=ff[0]+po[j].getX();
                    ff[1]=ff[1]+po[j].getY();
                    ff[2]=ff[2]+po[j].getZ();
                }
            }
            start=start.next;
        }
        ff[0]=ff[0]/y;ff[1]=ff[1]/y;ff[2]=ff[2]/y;
        Point ppp=new Point((float) ff[0],(float) ff[1],(float) ff[2]);

        return ppp;


        /*int mark[]=new int[alltriangles.size()];
        LinkedList<Triangle> lk=new LinkedList<>();

        connectedcomp2(mark,t,lk,1);
        float ff[]=new float[3];
        int y=0;

        LinkedList<PointInterface> pep=new LinkedList<>();
        Node<Triangle> start=lk.head;
        while(start!=null)
        {
            PointInterface po[]=start.data.triangle_coord();
            for(int i=0;i<3;i++)
            {
                if(pep.search(po[i])==null)
                {
                    pep.add(po[i]);
                    y++;
                    ff[0]=ff[0]+po[i].getX();
                    ff[1]=ff[1]+po[i].getY();
                    ff[2]=ff[2]+po[i].getZ();
                }
            }
            start=start.next;
        }
        ff[0]=ff[0]/y;ff[1]=ff[1]/y;ff[2]=ff[2]/y;
        Point ppp=new Point(ff[0],ff[1],ff[2]);
        //System.out.println(y);

        return ppp;
*/
    }
    /*public void connectedcomp2(int[] mark,Triangle t,LinkedList<Triangle> tt,int k)
    {
        if(mark[t.getIndex()]>0)
            return;

        mark[t.getIndex()]=k;
        tt.add(t);

        LinkedList<Triangle> tat=t.adjtri();
        Node<Triangle> start=tat.head;
        while(start!=null)
        {
            Triangle ty=start.data;
            connectedcomp2(mark,ty,tt,k);
            start=start.next;
        }
    }*/
    public void connectedcomp2(int[] mark,Triangle t,LinkedList<Triangle> tt,int k)
    {
        mark[t.getIndex()]=k;
        //LinkedList<Triangle> tt=new LinkedList<>();
        tt.add(t);
        Node<Triangle> start=tt.head;
        while(start!=null)
        {
            LinkedList<Triangle> yy=start.data.adjtri();
            Node<Triangle> op=yy.head;
            while(op!=null)
            {
                if(mark[op.data.getIndex()]==0)
                {
                    mark[op.data.getIndex()]=k;
                    tt.add(op.data);
                }
                op=op.next;
            }
            start=start.next;
        }
    }


    public int MAXIMUM_DIAMETER()
    {
        int l=alltriangles.size();
        int mark[]=new int[l];

        Node<Triangle> start=alltriangles.head;

        LinkedList<Triangle> ret=new LinkedList<>();

        int d=0,i=0;
        while(start!=null)
        {
            if(mark[i++]==0)
            {
                LinkedList<Triangle> tt=new LinkedList<>();
                connectedcomp2(mark, start.data, tt,d + 1);

                if(tt.size()>ret.size())
                    ret=tt;
                d++;
            }
            start=start.next;
        }

        start=ret.head;
        int ss=0;
        while(start!=null)
        {
            int y=maxpath(start.data);
            if(y>ss)
                ss=y;
            start=start.next;
        }
        return ss;
    }
    public int maxpath(Triangle t)
    {
        //int arr[]=new int[alltriangles.size()];
        int summ[]=new int[alltriangles.size()];
        int y=0;
        LinkedList<Triangle> trt=new LinkedList<>();
        trt.add(t);
        Node<Triangle> start=trt.head;
        while(start!=null)
        {
            //arr[start.data.getIndex()]=1;
            Node<Triangle> dfg=start.data.adjtri().head;
            while(dfg!=null)
            {
                if(summ[dfg.data.getIndex()]==0)//arr[dfg.data.getIndex()]!=0)
                {
                    //arr[dfg.data.getIndex()]=1;
                    summ[dfg.data.getIndex()]=summ[start.data.getIndex()]+1;
                    trt.add(dfg.data);
                }
                if(summ[dfg.data.getIndex()]>y)
                    y=summ[dfg.data.getIndex()];
                dfg=dfg.next;
            }
            start=start.next;
        }
        return y;
    }

    public PointInterface [] CLOSEST_COMPONENTS()
    {
        int l=alltriangles.size();
        int mark[]=new int[l];

        Node<Triangle> start=alltriangles.head;

        int d=0,i=0;
        while(start!=null)
        {
            if(mark[i++]==0)
            {
                connectedcomp(mark, start.data, d + 1);
                d++;
            }
            start=start.next;
        }


        Point p1=null,p2=null;
        float min_dist=Float.MAX_VALUE;
        Node<Point> a=allpoints.head;

        outer:
        while(a!=null)
        {
            Node<Point> b=a;
            while(b!=null)
            {
                if(connected(a.data,b.data,mark)==false)
                {
                    float ff=distance(a.data,b.data);
                    if(ff<min_dist)
                    {
                        min_dist=ff;
                        p1=a.data;
                        p2=b.data;
                        if(min_dist==0)
                            break outer;
                    }
                }
                b=b.next;
            }
            a=a.next;
        }

        if(p1==null && p2==null)
            return null;

        PointInterface re[]={p1,p2};
        return re;
    }
    public boolean connected(Point p1,Point p2,int mark[])
    {
        Node<Triangle> t1=p1.tri().head;

        while(t1!=null)
        {
            Node<Triangle> t2=p2.tri().head;
            while(t2!=null)
            {
                if(mark[t1.data.getIndex()]!=mark[t2.data.getIndex()])
                    return false;
                t2=t2.next;
            }
            t1=t1.next;
        }
        return true;
    }

}

