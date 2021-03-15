import java.io.*;
import java.util.Stack;

public class CalculatorTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("ERROR");
			}
		}
	}

	private static void command(String input) throws Exception
	{
		String input_postfix = interpret_infix(input);
		Long answer = calculate(input_postfix);
		System.out.println(input_postfix);
		System.out.println(answer);
	}

	private static String interpret_infix(String exp_infix) throws Exception
	{
		Stack<Opcode> stack = new Stack<>();
		StringBuilder exp_postfix = new StringBuilder();
		String c = null;
		Opcode op_c;

		boolean last_is_num = false;
		boolean num_reading = false;

		for(int i=0; i<exp_infix.length(); i++){
			c = Character.toString(exp_infix.charAt(i));

			if(c.equals(" ")||c.equals("	")) { num_reading = false;
			}else if(!Opcode.isOpcode(c)){
				if(!last_is_num){exp_postfix.append(" ");
				}else if(!num_reading){ throw new Exception(); }

				exp_postfix.append(c);
				last_is_num = true;
				num_reading = true;

			}else if(Opcode.isBracket(c)){
				op_c = new Opcode(c);
				if(op_c.isOpenBracket()){
					stack.push(op_c);
				}else{
					while(!stack.peek().isOpenBracket()){
						if(stack.empty()){throw new Exception();} // only ")" remains
						exp_postfix.append(stack.pop().print());
					}
					stack.pop(); // remove remain open bracket
				}
			}else{

				if(!last_is_num){
					if(!Opcode.isMinus(c)){ throw new Exception(); }
					op_c = new Opcode();
				}else{ op_c = new Opcode(c); }

				if(stack.empty()) { // stack is empty
				}else if(!stack.peek().succeedThan(op_c)) { // opcode in stack is preceding c, flush until succeeding one comes
					while (!stack.peek().succeedThan(op_c) && last_is_num) {
						if(stack.peek().isPow() && op_c.isPow()){ break;} // "^" is right associative. ignore same opcode
						exp_postfix.append(stack.pop().print());
						if (stack.empty()) { break; }
					}
				}

				stack.push(op_c);
				num_reading = false;
				last_is_num = false;
			}
		}

		while (!stack.empty()){
			Opcode op_temp = stack.pop();
			if(op_temp.isBracket()){throw new Exception();} // only "(" remains
			exp_postfix.append(op_temp.print());
		}

		return exp_postfix.toString().trim();
	}

	private static long calculate(String exp_postfix) throws Exception{
		Stack<Long> stack = new Stack<>();
		String [] tokens = exp_postfix.split(" ");

		for(String token : tokens){
			if(!Opcode.isOpcode(token)){
				stack.push(Long.parseLong(token));
			}else if(Opcode.isUnary(token)){
				stack.push(-1 * stack.pop());
			}else{
				Long num2 = stack.pop();
				Long num1 = stack.pop();

				switch (token){
					case "+":
						stack.push(num1+num2);
						break;
					case "-":
						stack.push(num1-num2);
						break;
					case "*":
						stack.push(num1*num2);
						break;
					case "/":
						if(num2 == 0){throw new Exception();}
						stack.push(num1/num2);
						break;
					case "%":
						if(num2 == 0){throw new Exception();}
						stack.push(num1%num2);
						break;
					case "^":
						if(num1 == 0 && num2 < 0){throw new Exception();}
						stack.push((long)Math.pow(num1, num2));
						break;
					default:
						throw new Exception();
				}
			}
		}

		return stack.pop();
	}
}

class Opcode
{
	private String op;
	private int priority;

	// unary -
	Opcode(){
		this.op = "~";
		this.priority = 2;
	}

	Opcode(String str) throws Exception{
		if(!isOpcode(str)){throw new Exception();}
		this.op = str;
		switch (str){
			case "+":
			case "-":
				this.priority = 0;
				break;
			case "*":
			case "/":
			case "%":
				this.priority = 1;
				break;
			case "^":
				this.priority = 3;
				break;
			case "(":
			case ")":
				this.priority = -1;
				break;
		}

	}

	public static boolean isOpcode(String str){
		final String checker = "+~-*/%^()";
		return checker.contains(str);
	}
	public static boolean isMinus(String str){ return str.equals("-");}
	public static boolean isUnary(String str){ return str.equals("~");}
	public static boolean isBracket(String str){ return str.equals("(") || str.equals(")");}

	public String getItem(){ return this.op;}
	public int getPriority(){ return this.priority;}

	public boolean precedeThan(Opcode other){return this.getPriority() > other.getPriority();}
	public boolean succeedThan(Opcode other){return this.getPriority() < other.getPriority();}

	public boolean isOpenBracket(){return this.op.equals("(");}
	public boolean isCloseBracket(){return this.op.equals(")");}
	public boolean isPow(){return this.op.equals("^");}
	public boolean isBracket(){ return this.isOpenBracket() || this.isCloseBracket();}

	public String print(){return " " + this.op;}
}