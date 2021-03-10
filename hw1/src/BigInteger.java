import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
/*
    regex info
    \\s -> blank character, \\d -> [0-9]
    since [+-*] throws error ('-' is used as [A-Z]), '\-' is needed. so [+\\-*] is used.
    -----
    pattern info
    group1: num1 sign, g2: num1 characters, g3: operator, g4: num2 sign, g5: num2 characters
*/
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\s*([+-]?)\\s*(\\d+)\\s*([+\\-*])\\s*([+-]?)\\s*(\\d+)\\s*");

    // sign: true when >=0 false when <0, digits[0] -> lowest digit
    public boolean sign;
    // digits are filled up with '0'. ex) 00...00123
    public char[] digits;

    // to compare absolute of integers
    public static enum cmp {less, equal, greater};

    public BigInteger(int i)
    {
        this.digits = new char[202];
        this.sign = (i >= 0) ? true : false;
        i = (i < 0) ? -i: i;

        int idx = 0;
        while (i > 0){
            this.digits[idx] = (char)(i%10 + '0');
            i = i/10;
            idx++;
        }

        while (idx < this.digits.length){
            this.digits[idx] = '0';
            idx++;
        }
    }

    public BigInteger(boolean sign,char[] num)
    {
        this.sign = sign;
        this.digits = num.clone();
    }

    // s[0] means sign
    public BigInteger(String s)
    {
        this.digits = new char[202];
        this.sign = (s.charAt(0) == '+') ? true : false;

        int idx_arr = 0;
        for(int idx_str = s.length() - 1; idx_str>0; idx_str--, idx_arr++){
            this.digits[idx_arr] = s.charAt(idx_str);
        }
        while (idx_arr < this.digits.length){
            this.digits[idx_arr] = '0';
            idx_arr++;
        }
    }

    // order shifting
    public BigInteger shift(int order){
        char[] digits_temp = new char[202];
        int idx = 0;
        while (idx < order){
            digits_temp[idx] = '0';
            idx++;
        }
        for(int i=0; idx<digits_temp.length; i++, idx++){
            digits_temp[idx] = this.digits[i];
        }

        return new BigInteger(this.sign, digits_temp);
    }

    // a.compare(b) -> a is [result] than b, only compares absolute value, not sign!
    public cmp compare(BigInteger big)
    {
        for (int idx = this.digits.length - 1; idx >= 0; idx--){
            if(this.digits[idx] > big.digits[idx]){
                return cmp.greater;
            }else if(this.digits[idx] < big.digits[idx]){
                return cmp.less;
            }
        }
        return cmp.equal;
    }
  
    public BigInteger add(BigInteger big)
    {
        int top_idx = this.digits.length - 1;
        int idx = 0;
        int carry = 0;
        while (idx < top_idx){
            int a = this.digits[idx] - '0';
            int b = big.digits[idx] - '0';
            int c = a + b + carry;

            carry = 0;
            if(c > 9){
                c -= 10;
                carry = 1;
            }
            this.digits[idx] = (char) (c + '0');
            idx++;
        }

        if(carry == 1){
            this.digits[idx] = (char) carry;
        }

        return this;
    }

    // results the difference of two integer. first parameter must bigger than last.
    public BigInteger diff(BigInteger big)
    {
        int top_idx = this.digits.length - 1;
        int idx = 0;
        int carry = 0;
        while (idx < top_idx){
            int a = this.digits[idx] - '0';
            int b = big.digits[idx] - '0';
            int c = a - b - carry;

            carry = 0;
            if(c < 0){
                c += 10;
                carry = 1;
            }
            this.digits[idx] = (char) (c + '0');
            idx++;
        }

        if(carry == 1){
            throw new ArithmeticException();
        }

        return this;
    }

    public BigInteger multiply(BigInteger big)
    {
        this.sign = !(this.sign ^ big.sign);
        BigInteger sum = new BigInteger(0);

        // iterates the same order. ex) 123*456 = 3*6 + 10*(2*6+3*5)+...
        for(int i=0; i<this.digits.length; i++){
            int tmp = 0;
            for(int j=0; (j<100)&&(j<=i); j++){
                tmp += (this.digits[i-j] - '0') * (big.digits[j] - '0');
            }
            BigInteger tmp_big = new BigInteger(tmp);
            sum.add(tmp_big.shift(i));
        }

        return sum;
    }
  
    @Override
    public String toString()
    {
        int idx_nonzero = this.digits.length - 1;
        while (idx_nonzero >= 0){
            if(this.digits[idx_nonzero] != '0'){break;}
            idx_nonzero--;
        }

        if(idx_nonzero == -1){
            return "0";
        }

        String s;
        s = !this.sign ? "-" : "";
        while (idx_nonzero >= 0){
            s += Character.toString(this.digits[idx_nonzero]);
            idx_nonzero--;
        }
        return s;
    }

    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        final Matcher matcher = EXPRESSION_PATTERN.matcher(input);

        if(!matcher.matches()){ throw new IllegalArgumentException();}

        String num1_sgn = matcher.group(1);
        final String num1_digit = matcher.group(2);
        String operator = matcher.group(3);
        String num2_sgn = matcher.group(4);
        final String num2_digit = matcher.group(5);

        // add missed sign
        num1_sgn = (num1_sgn.equals("-")) ? "-" : "+";
        num2_sgn = (num2_sgn.equals("-")) ? "-" : "+";

        // remove subtraction case. (operator == "-")
        if(operator.equals("-")){
            operator = "+";
            num2_sgn = (num2_sgn.equals("+")) ? "-" : "+";
        }

        BigInteger num1 = new BigInteger(num1_sgn + num1_digit);
        BigInteger num2 = new BigInteger(num2_sgn + num2_digit);

        if(operator.equals("+")){
            if(num1_sgn.equals(num2_sgn)){
                return num1.add(num2);
            }else{
                // result have the same sign with larger absolute value
                if(num1.compare(num2) == cmp.greater){
                    return num1.diff(num2);
                }else if(num1.compare(num2) == cmp.less){
                    return num2.diff(num1);
                }else{
                    return new BigInteger("+0");
                }
            }
        }else if(operator.equals("*")){
            return num1.multiply(num2);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
