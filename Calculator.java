import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter input string to convert to decimal: ");

        String inputStr = input.nextLine();
        inputStr = removeUnderscore(inputStr);
        if (inputValidation(inputStr)) {
            double value = parseString(inputStr);
            System.out.println(value);
            input.close();
        }
        else 
            System.out.println("Not a valid Java floating decimal literal");
    }

    private static String removeUnderscore(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if (num != '_')
                newStr+=num;
        }
        return newStr;
    }

    private static boolean inputValidation(String str) {
        int e_count = 0;
        int dot_count = 0;
        for (int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if (num == 'e') {
                e_count++;
                if (dot_count > 1 || e_count > 1) 
                    return false;
            }
            else if (num == '.') {
                dot_count++;
                if (dot_count > 1 || e_count > 1) 
                    return false;
            }
            else if (i == str.length()-1) {
                if (num != '0' && num != '1' && num != '2' && num != '3' && num != '4' && num != '5' && num != '6' && num != '7' 
                && num != '8' && num != '9' && num != '.' && num != '+' && num != '-') {
                    if (num == 'f' || num == 'F' || num == 'd' || num == 'D') {
                        return true;
                    }
                    else
                        return false;
                }
            }
            else if (num != '0' && num != '1' && num != '2' && num != '3' && num != '4' && num != '5' && num != '6' && num != '7' && num != '8' 
                && num != '9' && num != '.' && num != '+' && num != '-') 
                return false;
        }
        return true;
    }

    private static double parseString(String str) {
        double result = 0;
        boolean negativeNumber = false;
        boolean lessThanOne = false;
        boolean powerOn = false;
        double powerOf10 = 0;
        boolean powerof10_negative = false;
        int decimalPlace = 0;
        int powerPlace = 0;
        for (int i = 0; i < str.length(); i++) {
            if (lessThanOne)
                decimalPlace++;
            if (powerOn)
                powerPlace++;

            char num = str.charAt(i);
            if (num == '-' && i == 0)
                negativeNumber = true;
            else if (num == 'E' || num == 'e') {
                powerOn = true;
                if (str.charAt(i+1) == '-') 
                    powerof10_negative = true;
            }
            else if (num == '.') {
                lessThanOne = true;
                result = reverseNum(result);
            }

            /*Multiply by 10^n if number on right side of decimal */
            else if (num == '0' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*0;
            else if (num == '1' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*1;
            else if (num == '2' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*2;
            else if (num == '3' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*3;
            else if (num == '4' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*4;
            else if (num == '5' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*5;
            else if (num == '6' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*6;
            else if (num == '7' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*7;
            else if (num == '8' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*8;
            else if (num == '9' && powerOn)
                powerOf10 += Math.pow(10, powerPlace)*9;


            /*Multply by 10^(-n) if number on right side of decimal */
            else if (num == '0' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*0;
            else if (num == '1' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*1;
            else if (num == '2' && lessThanOne) 
                result += Math.pow(10, -decimalPlace)*2;
            else if (num == '3' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*3;
            else if (num == '4' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*4;
            else if (num == '5' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*5;
            else if (num == '6' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*6;
            else if (num == '7' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*7;
            else if (num == '8' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*8;
            else if (num == '9' && lessThanOne)
                result += Math.pow(10, -decimalPlace)*9;



            else if (num == '0')
                result += Math.pow(10, i)*0;
            else if (num == '1')
                result += Math.pow(10, i)*1;
            else if (num == '2')
                result += Math.pow(10, i)*2;
            else if (num == '3')
                result += Math.pow(10, i)*3;
            else if (num == '4')
                result += Math.pow(10, i)*4;
            else if (num == '5')
                result += Math.pow(10, i)*5;
            else if (num == '6')
                result += Math.pow(10, i)*6;
            else if (num == '7')
                result += Math.pow(10, i)*7;
            else if (num == '8')
                result += Math.pow(10, i)*8;
            else if (num == '9')
                result += Math.pow(10, i)*9;
            
        }
        if (!lessThanOne) {
            result = reverseNum(result);
        }

        powerOf10 = reverseNum(powerOf10);
        if (powerof10_negative) 
            result = result * Math.pow(10, -powerOf10);
        else
            result = result * Math.pow(10, powerOf10);

        if (negativeNumber) {
            result= -1*result;
        }
        
        return result; 
    }

    private static double reverseNum(double num) {
        double reversedNumber = 0;
        while (num >= 1) {
            reversedNumber = Math.floor(reversedNumber * 10 + num % 10);
            num = num / 10;
        }
        return reversedNumber;
    }



} 