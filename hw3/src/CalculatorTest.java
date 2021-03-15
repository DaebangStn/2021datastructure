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
		String digits = "0123456789";
		String c = null;
		String last_captured = new String(" ");
		Opcode op_c;

		boolean last_is_blank = false;
		boolean first_char = true;

		for(int i=0; i<exp_infix.length(); i++){
			c = Character.toString(exp_infix.charAt(i));
			op_c = new Opcode(c);

			if(c.equals(" ")||c.equals("	")) { last_is_blank = true;
			}else if(digits.contains(c)){
				if(!digits.contains(last_captured)){exp_postfix.append(" ");}

				if((digits.contains(last_captured) || Opcode.isCloseBracket(last_captured)) && last_is_blank && !first_char){
					throw new Exception(); }

				exp_postfix.append(c);
				last_is_blank = false;

			}else if(op_c.isOpenBracket()) {
				if((digits.contains(last_captured) || Opcode.isCloseBracket(last_captured)) && !first_char){throw new Exception();}
				stack.push(op_c);
				last_is_blank = false;
			}else if(op_c.isCloseBracket()) {
				if((Opcode.isOpcode(last_captured) || Opcode.isOpenBracket(last_captured)) && !first_char){ throw new Exception(); }
				while(!stack.peek().isOpenBracket()){
					if(stack.empty() && !first_char){throw new Exception();} // only ")" remains
					exp_postfix.append(stack.pop().print());
				}
				stack.pop(); // remove remain open bracket
				last_is_blank = false;
			}else{
				if(Opcode.isOpcode(last_captured) || Opcode.isOpenBracket(last_captured) || first_char){
					if(!Opcode.isMinus(c)){ throw new Exception(); }
					op_c = new Opcode();
				}

				if(stack.empty()) { // stack is empty
				}else if(!stack.peek().succeedThan(op_c)) { // opcode in stack is preceding c, flush until succeeding one comes
					while (!stack.peek().succeedThan(op_c)) {
						if(stack.peek().isPow() && op_c.isPow()){ break;} // "^" is right associative. ignore same opcode
						if(stack.peek().isUnary() && op_c.isUnary()){ break;} // "^" is right associative. ignore same opcode
						exp_postfix.append(stack.pop().print());
						if (stack.empty()) { break; }
					}
				}

				stack.push(op_c);
				last_is_blank = false;
			}

			if(!last_is_blank){
				last_captured = c;
				first_char = false;
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
			if(!Opcode.isSymbol(token)){
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
		final String checker = "+~-*/%^";
		return checker.contains(str);
	}
	public static boolean isSymbol(String str){
		final String checker = "+~-*/%^()";
		return checker.contains(str);
	}
	public static boolean isMinus(String str){ return str.equals("-");}
	public static boolean isUnary(String str){ return str.equals("~");}
	public static boolean isBracket(String str){ return str.equals("(") || str.equals(")");}
	public static boolean isOpenBracket(String str){return str.equals("(");}
	public static boolean isCloseBracket(String str){return str.equals(")");}

	public String getItem(){ return this.op;}
	public int getPriority(){ return this.priority;}

	public boolean precedeThan(Opcode other){return this.getPriority() > other.getPriority();}
	public boolean succeedThan(Opcode other){return this.getPriority() < other.getPriority();}

	public boolean isOpenBracket(){return this.op.equals("(");}
	public boolean isCloseBracket(){return this.op.equals(")");}
	public boolean isPow(){return this.op.equals("^");}
	public boolean isUnary(){return this.op.equals("~");}
	public boolean isBracket(){ return this.isOpenBracket() || this.isCloseBracket();}

	public String print(){return " " + this.op;}
}