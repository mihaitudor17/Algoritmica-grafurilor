import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Source
{	
	static int window_width = 800;
	static int window_height = 600;
	
	private static void initUI() 
	{
        Graph.Init(window_width, window_height);
        JFrame f = new JFrame("Algoritmica Grafurilor");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPannel());
        f.setSize(window_width, window_height);
        f.setVisible(true);
    }
	
	public static void main(String[] args)
	{
		//start the graphic execution thread
		SwingUtilities.invokeLater(new Runnable() //new Thread()
		{
            public void run() 
            {
            	initUI(); 
            }
        });
	}
}
