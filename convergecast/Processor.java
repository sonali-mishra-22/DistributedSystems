public class Processor {
		int value;
		Processor left;
		Processor right;
		int max; 
		String conc;
		
		public Processor(int v){
			value = v;
			left = null;
			right = null;
			int max = -1;
		}
		
		public static int FindMax(Processor Processor){			
			if(Processor == null){
				return Integer.MIN_VALUE;
			}else{
				Processor.max = Integer.max(Processor.value, Integer.max(FindMax(Processor.left), FindMax(Processor.right)));
				return Processor.max;
			}
		}
		
		public static String concat(Processor Processor){
			if(Processor == null){
				return("");
			}else{
				Processor.conc = ("" + Processor.value + concat(Processor.left) + concat(Processor.right));
				return Processor.conc;
			}
		}
		
		public static void main(String[] args){
			
			
			Processor p0 = new Processor(2); 
			Processor p1 = new Processor(7);
			Processor p2 = new Processor(5);
			Processor p3 = new Processor(2);
			Processor p4 = new Processor(6);
			Processor p5 = new Processor(9);
			Processor p6 = new Processor(5);
			Processor p7 = new Processor(11);
			Processor p8 = new Processor(4);
			
			p0.left = p1;
			p0.right = p2;
			
			p1.left = p3;
			p1.right = p4;
			
			p2.right = p5;
			
			p3.left = p6;
			p3.right = p7;
			
			p4.left = p8;
				
			int maxim = FindMax(p0);
			System.out.println("The max value obtained by root is " +maxim);
			
			String c= concat(p0);
			System.out.println("Concatenation is " +c);
			
			
			
		}
			
	}