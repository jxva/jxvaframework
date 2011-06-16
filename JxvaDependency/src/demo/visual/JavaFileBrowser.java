package demo.visual;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * 利用FileTreeModel生成文件系统树
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-28 16:28:55 by Jxva
 */
public class JavaFileBrowser extends JFrame implements TreeModel, Serializable, Cloneable{
  JTree jTree1;
  protected EventListenerList listeners;

    private static final Object LEAF = new Serializable() { };

    private Map map;

    private static String  []args ={"D:\\"};
    private File root;

  public JavaFileBrowser(File root) throws HeadlessException {
    //super("FileTreeModelDemo");
    try {
      this.root = root;

     if (!root.isDirectory())
         map.put(root, LEAF);

     this.listeners = new EventListenerList();

     this.map = new HashMap();

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public JavaFileBrowser(){
    try{
      this.showTree();
      jbInit();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public Object getRoot()
   {
       return root;
   }

   public boolean isLeaf(Object node)
   {
       return map.get(node) == LEAF;
   }

   public int getChildCount(Object node)
   {
       List children = children(node);

       if (children == null)
           return 0;

       return children.size();
   }

   public Object getChild(Object parent, int index)
   {
       return children(parent).get(index);
   }

   public int getIndexOfChild(Object parent, Object child)
   {
       return children(parent).indexOf(child);
   }

   protected List children(Object node)
   {
       File f = (File)node;

       Object value = map.get(f);

       if (value == LEAF)
           return null;

       List children = (List)value;

       if (children == null)
       {
           File[] c = f.listFiles();

           if (c != null)
           {
               children = new ArrayList(c.length);

               for (int len = c.length, i = 0; i < len; i++)
               {
                   children.add(c[i]);
                   if (!c[i].isDirectory())
                       map.put(c[i], LEAF);
               }
           }
           else
               children = new ArrayList(0);

           map.put(f, children);
       }

       return children;
   }

   public void valueForPathChanged(TreePath path, Object value)
   {
   }

   public void addTreeModelListener(TreeModelListener l)
   {
       listeners.add(TreeModelListener.class, l);
   }

   public void removeTreeModelListener(TreeModelListener l)
   {
       listeners.remove(TreeModelListener.class, l);
   }

   public Object clone()
   {
       try
       {
           JavaFileBrowser clone = (JavaFileBrowser)super.clone();

           clone.listeners = new EventListenerList();

           clone.map = new HashMap(map);

           return clone;
       }
       catch (CloneNotSupportedException e)
       {
           throw new InternalError();
       }
   }

  class thisTreeListener implements javax.swing.event.TreeSelectionListener{
    //public void
    public void valueChanged(javax.swing.event.TreeSelectionEvent tse){
System.out.println(      tse.getNewLeadSelectionPath().toString());
    }
  }
  public static void main(String[] args) throws HeadlessException {
    JavaFileBrowser javaFileBrowser1 = new JavaFileBrowser();
   // javaFileBrowser1.showTree();
  }
  public  void showTree(){
    this.root=new File(args[0]);
    if (args.length != 1)
        {
            System.err.println("Usage: java FileTreeModel path");
            System.exit(1);
        }

     //   File root = this.root;

        if (!root.exists())
        {
            System.err.println(root+ ": No such file or directory");
            System.exit(2);
        }

        jTree1 = new JTree(new JavaFileBrowser(this.root));

      //  JFrame f = new JFrame(root.toString());

       this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

  }
  private void jbInit() throws Exception {
    //args=s;


    this.getContentPane().add(jTree1, BorderLayout.CENTER);
    jTree1.addTreeSelectionListener(new thisTreeListener());
    this.setSize(400,400);
    this.setVisible(true);
  }

}