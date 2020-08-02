package com.teamS4.n2w;

public class logic {
    private MainActivity mout;

    //static data for storing constant parts
    final static private String[] suffix = {"hundred", "", "thousand", "lakh", "crore"};
    final static private String[] ones = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    final static private String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    public logic(MainActivity out) {
        this.mout = out;
    }

    //function to calculate result
    public String process(int n) {
        if (n < 0)
//            mout.print("Please provide valid number");
            return "Please provide valid number";
        else if (n == 0) {

//            mout.print("0");
            return "zero";

        } else {
            String w = change(String.valueOf(n));
            String s = w.substring(0, 1).toUpperCase() + w.substring(1);
//            mout.print(w);
            return s;
        }
    }


    //parent function for calling other sub-functions
    private static String change(String str) {
        //check for invalid numbers
        if (str.length() == 0 || str.length() > 9) {
//            System.out.println("Please provide valid numbers !");
            return "Not valid number";
        }


        //if 2 digit or less, change_ten( ) called
        if (str.length() <= 2 && str.length() > 0) {
            return change_ten(str);
        }

        //if 3 digit, change_hundred( ) called
        if (str.length() == 3) {
            return change_hundred(str);
        }


        //if 4 digit or more, change_all( ) called
        return change_all(str);

    }

    //function to change 2-digit no. to words, recursive call
    private static String change_ten(String str) {
        if (Integer.valueOf(str) < 20)
            return ones[Integer.valueOf(str)].trim();

        if (Integer.valueOf(str) >= 20) {
            if (str.charAt(1) == '0')
                return tens[Integer.parseInt(str) / 10].trim();
            else {
                return String.format("%s %s", tens[Integer.parseInt(str) / 10], ones[Integer.parseInt(str) % 10]).trim();
            }
        }

        return null;
    }

    //function to change 3-digit no. to words, recursive call
    private static String change_hundred(String str) {
        int p = Integer.parseInt(str) % 100;
        String g = change(String.valueOf(p));
        p = Integer.parseInt(str) / 100;
        String h = change(String.valueOf(p));
        return String.format("%s %s %s", h, suffix[0], g);

    }


    //function to change 3-digit or more digits no. to words, recursive call
    private static String change_all(String str) {
        double n = 0.0;

        //n is calculated to dynamically get the 10 powered no. to divide or to modulus
        if (str.length() % 2 != 0)
            n = str.length() - 1;
        else
            n = str.length();

        int power = (int) Math.pow(10, n - 1);
        int p = Integer.parseInt(str) % power;
        String g = change(String.valueOf(p));
        p = Integer.parseInt(str) / power;
        String h = change(String.valueOf(p));
        //suffix[n/2] maps length of the string to the suffix[ ] array
        return String.format("%s %s %s", h, suffix[(int) n / 2], g);

    }

}
