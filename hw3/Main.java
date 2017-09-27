package edu.dt;

import java.awt.font.GraphicAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tphadke on 8/29/17.
 */
public class Main {
    Map <Processor, List<Processor> > graph ;
    Processor root ;
    public  Main(){

        init();

    }

    public static void main ( String args[]){
        Main m = new Main();
        m.graph.keySet().iterator().next().sendMessgeToMyBuffer(null, null);
        printResult(m);
        
        //TODO: Choose a processor as a Root
        //TODO: Send an initial message Message.M to this processor.
    }

    private static void printResult(Main m) {
		List<Processor> nodes = m.graph.get(m.root);
		StringBuffer parentList = new StringBuffer("");
		StringBuffer childrenList = new StringBuffer("");
		String children = "";
		for(int i =0;i< nodes.size();i++){
			children = "";
			parentList.append("P"+nodes.get(i).id+" parent = P"+nodes.get(i).parent.id+"\n");
			for(int j =0;j< nodes.get(i).children.size();j++){
				children = children+ " "+ nodes.get(i).children.get(j).id;
			}
			childrenList.append("P"+nodes.get(i).id+" children = ["+children +" ]\n");
		}
		System.out.println(parentList);
		System.out.println(childrenList);
	}

	public void init(){
    	Processor p0 =new Processor();
        p0.id = 0;
        Processor p1 =new Processor();
        p1.id = 1;
        Processor p2 =new Processor();
        p2.id = 2;
        Processor p3 =new Processor();
        p3.id = 3;
        Processor p4 =new Processor();
        p4.id = 4;
        Processor p5 =new Processor();
        p5.id = 5;
        p0.unexplored.add(p1);
        p0.unexplored.add(p2);
        p0.unexplored.add(p3);
        p1.unexplored.add(p0);
        p1.unexplored.add(p2);
        p1.unexplored.add(p4);
        p2.unexplored.add(p0);
        p2.unexplored.add(p1);
        p2.unexplored.add(p5);
        p3.unexplored.add(p0);
        p4.unexplored.add(p1);
        p4.unexplored.add(p5);
        p5.unexplored.add(p2);
        p5.unexplored.add(p4);
        
        List<Processor> nodes= new ArrayList<>();
        nodes.add(p0);
        nodes.add(p1);
        nodes.add(p2);
        nodes.add(p3);
        nodes.add(p4);
        nodes.add(p5);
        root = p0;
        graph = new HashMap<>();
        graph.put(root, nodes);
    }
}
