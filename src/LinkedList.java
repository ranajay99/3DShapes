public class LinkedList<T>
{
    Node<T> head;
    Node<T> tail;
    int elements;

    LinkedList()
    {
        head=null;
        tail=null;
        elements=0;
    }
    public void add(T data)
    {
        Node<T> n=new Node<>(data);
        if(head==null)
        {
            head=n;
            tail=n;
            elements++;
            return;
        }

        tail.next=n;
        tail=tail.next;

        elements++;
    }

    //return original object with same characteristic features
    public T search(T item)
    {
        Node<T> start=head;
        while(start!=null)
        {
            if(start.data.equals(item))
                return start.data;
            start=start.next;
        }
        return null;
    }
    public int size()
    {
        return elements;
    }
}

class Node<T>
{
    T data;
    Node<T> next;

    Node(T data)
    {
        this.data=data;
        next=null;
    }

}
