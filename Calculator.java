import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        String inputStr = " ";
        Scanner input = new Scanner(System.in);
        
        while (inputStr != "q") {
            System.out.print("Enter input string to convert to decimal (press q to quit): ");
            inputStr = input.nextLine();
            if (inputStr.equals("q")) {
                break;
            }
            inputStr = underscoreValidation(inputStr);
            if (inputValidation(inputStr)) {    
                double value = parseString(inputStr);
                System.out.println(value);
                
            }
            else  
                System.out.println("Not a valid Java floating decimal literal");
        }
        input.close();
    }

    private static String underscoreValidation(String str) {   //STATE1
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if (num != '_')         
                newStr+=num;
            if (num == '_') {       
                if (i == 0) {
                    newStr+=num;
                }
                else if (i == str.length()-1) {
                    newStr+=num;
                }
                else if (i+1 != str.length()) {   //if '_' is not b/w 2 digits, reject
                    if (str.charAt(i+1) != '0' && str.charAt(i+1) != '1' && str.charAt(i+1) != '2' && str.charAt(i+1) != '3' && 
                        str.charAt(i+1) != '4' && str.charAt(i+1) != '5' && str.charAt(i+1) != '6' && str.charAt(i+1) != '7' &&
                        str.charAt(i+1) != '8' && str.charAt(i+1) != '9') {
                            newStr+=num;
                    }
                    else if (str.charAt(i-1) != '0' && str.charAt(i-1) != '1' && str.charAt(i-1) != '2' && str.charAt(i-1) != '3' && 
                            str.charAt(i-1) != '4' && str.charAt(i-1) != '5' && str.charAt(i-1) != '6' && str.charAt(i-1) != '7' &&
                            str.charAt(i-1) != '8' && str.charAt(i-1) != '9') {
                            newStr+=num;
                        }
                    
                }

            }
        }
        return newStr;
    }

    private static boolean inputValidation(String str) {     //STATE2; if return false -> bad state
                                                            //if return true -> go to STATE3
        int e_count = 0;
        int dot_count = 0;
        if (str.length() == 0) {  //reject empty string
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if (i == 0 && num == '.' && i == str.length()-1) {    //reject if str = '.'
                return false;
            }
            if (i == 0 && (num == 'E' || num == 'e'))  //reject if first char of string is e,E
                return false;
            if ((i == 0 && num == '-') || ( i==0 && num == '+') || ( i==0 && num == 'd')
            || ( i==0 && num == 'D') || ( i==0 && num == 'f') || ( i==0 && num == 'F')) { //reject if first char of string is the following chars
                return false;
            }
            if ((num == '+' || num == '-')) {            //reject if there is no 'e,E' before '+,-'
                if (i != 0) {
                    if (str.charAt(i-1) != 'e' && str.charAt(i-1) != 'E') {
                        return false;
                    }
                }
            }

            if (i != str.length()-1) {                 
                char numNext = str.charAt(i+1);      //reject if operators are consecutive (for proj2)
                if ((num  == '-' && numNext == '-') || (num  == '+' && numNext == '+') || (num  == '.' && numNext == '.')
                || (num  == '/' && numNext == '/') || (num  == '*' && numNext == '*')) {
                    return false;
                }
            }
            if (num == 'e' || num == 'E') {          
                e_count++;
                if (i == str.length()-1)           //reject if last char of str is 'E,e'
                    return false;
                if (dot_count > 1 || e_count > 1)   //reject if more than 1 'e,E' or '.' in num
                    return false;
            }
            else if (num == '.') {
                if (e_count > dot_count)            //reject if num is an integer; only accepts floats
                    return false;
                dot_count++;
                if (dot_count > 1 || e_count > 1)     //reject if more than 1 'e,E' or '.' in num
                    return false;
            }
            else if (i == str.length()-1) {         
                if (num != '0' && num != '1' && num != '2' && num != '3' && num != '4' && num != '5' && num != '6' && num != '7' 
                && num != '8' && num != '9' && num != '.' && num != '+' && num != '-') {
                    if (num == 'f' || num == 'F' || num == 'd' || num == 'D') {  //accept suffixes
                        return true;
                    }
                    else
                        return false;
                }
            }                                         
            //reject other alphabet
            else if (num != '0' && num != '1' && num != '2' && num != '3' && num != '4' && num != '5' && num != '6' && num != '7' && num != '8' 
                && num != '9' && num != '.' && num != '+' && num != '-') 
                return false;
            if (i == str.length()-1) {
                if (dot_count == 0 && e_count == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double parseString(String str) {    //STATE3 -> accept state
        double result = 0;
        boolean lessThanOne = false;
        boolean powerOn = false;
        double powerOf10 = 0;
        boolean powerof10_negative = false;
        int decimalPlace = 0;
        int placesOnLeft = 0;
        int powerCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if (num == '.' || num == 'e' || num == 'E') 
                break;
            else if (num == 'f' || num == 'F' || num == 'd' || num == 'D') 
                break;
            else
                placesOnLeft++;
        }
    
        boolean reached_E = false;
        for (int i = 0; i < str.length(); i++) {
            char num = str.charAt(i);
            if (num == 'E' || num == 'e') {
                reached_E = true;
            }
            else if (reached_E) {
                if (num == '-' || num == '+' || num == 'f' || num == 'F' || num == 'd' || num == 'D') {
                    continue;
                }
                else 
                    powerCount++;
            }
        }
        powerCount--;
        placesOnLeft--;

        for (int i = 0; i < str.length(); i++) {
            if (lessThanOne)
                decimalPlace++;

            char num = str.charAt(i);
            if (num == 'E' || num == 'e') {
                powerOn = true;
                if (str.charAt(i+1) == '-') 
                    powerof10_negative = true;
            }
            else if (num == '.') {
                lessThanOne = true;
            }

            /*Multiply by 10^n for e side of decimal */
            else if (num == '0' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*0;
            else if (num == '1' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*1;
            else if (num == '2' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*2;
            else if (num == '3' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*3;
            else if (num == '4' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*4;
            else if (num == '5' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*5;
            else if (num == '6' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*6;
            else if (num == '7' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*7;
            else if (num == '8' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*8;
            else if (num == '9' && powerOn)
                powerOf10 += Math.pow(10, powerCount--)*9;


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
                result += Math.pow(10, placesOnLeft--)*0;
            else if (num == '1')
                result += Math.pow(10, placesOnLeft--)*1;
            else if (num == '2')
                result += Math.pow(10, placesOnLeft--)*2;
            else if (num == '3')
                result += Math.pow(10, placesOnLeft--)*3;
            else if (num == '4')
                result += Math.pow(10, placesOnLeft--)*4;
            else if (num == '5')
                result += Math.pow(10, placesOnLeft--)*5;
            else if (num == '6')
                result += Math.pow(10, placesOnLeft--)*6;
            else if (num == '7')
                result += Math.pow(10, placesOnLeft--)*7;
            else if (num == '8')
                result += Math.pow(10, placesOnLeft--)*8;
            else if (num == '9')
                result += Math.pow(10, placesOnLeft--)*9;
            
        }
    
        if (powerof10_negative) 
            result = result * Math.pow(10, -powerOf10);
        else
            result = result * Math.pow(10, powerOf10);
        
        return result; 
    }

} 