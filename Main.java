// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.Test;
import java.util.Scanner;

import java.io.File;
import java.io.EOFException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
//import java.io.Serializable;


//
import java.nio.file.Files;
import java.nio.file.Paths;

//


public class Main {
  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("Hello world!");
    loop: while(true) {
      String choiceStr="", choice="", rest_of_command="";
      int i=0;

      choiceStr=sc.nextLine();
      //choiceStr="to_binarry test_txt.txt";
      //choiceStr="LZ77 test_txt.txt";
      String[] split_choice=choiceStr.split(" ");
      for (String individual_input : split_choice) {
        if (i==0) {
          choice=individual_input;
        } else {
          rest_of_command=individual_input;
        }
        i++;
      }

      //System.out.println("Choice:-"+choice+"-, but resrt of command:-"+rest_of_command);
      switch (choice) {
        case "to_binarry":
          to_binarry(rest_of_command);
          break;
        case "LZ77":
          LZ77(rest_of_command);
          break;
        case "exit":
          exit();
          break loop;
          //break;
      }

    }

  }

  /////////////////////////////////////////////////////////////////////
  public static void to_binarry(String convert_file) {
  }
  /////////////////////////////////////////////////////////////////////
  public static void LZ77 (String filename) {
    System.out.println("Filename is "+filename);

    File f = new File (filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }

    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line;
      int amout_of_symbols=0, current_read=0;
      while ((line=br.readLine())!=null) {
        //String[] split_to_words=line.split(" ");
        //for (String word : split_to_words) {
        //  String[] word_split_in_letters=word.split("");
        //  for (String symbol : word_split_in_letters) {
        //    amout_of_symbols++;
        //  }
        //}
        String[] split_to_symbols=line.split("");
        for (String symbol : split_to_symbols) {
          amout_of_symbols++;
        }
      }
      System.out.println("Amout of symbols is "+amout_of_symbols);
      br.close();

      FileInputStream fin = new FileInputStream(filename);
      FileOutputStream fout = new FileOutputStream("LZ77_"+filename);
      System.out.println(" File context ///////////////////////////////////////////////");
      System.out.println(fin);
      String LINE="";
      int wrote_to_line=0, line_element_count=0;
      System.out.println("end of///////////////////////////////////////////////");
      int i, k;
      //byte mas[] = new byte[20];
      byte mas[] = new byte[1];
      do {
        //System.out.println("[");
        k = fin.read(mas);
        String linee="";
        //for (i=0; i<k; i++) {
        i=0;
        //fout.write(mas[i]);
        //  if (i==0) current_read+=1;
        current_read+=1;
        //if (i==(k-1) && current_read>=amout_of_symbols) {
        if (current_read>=amout_of_symbols) {
          k=0;
        }
        //System.out.print("k="+k+" i="+i+"  and="+mas[i]);
        if (wrote_to_line==0) {
          wrote_to_line++;
          LINE+=String.valueOf(mas[i]);
        }
        else {
          LINE+=","+String.valueOf(mas[i]);
        }
          //System.out.print(mas[i]+" ");
        //}
        System.out.println("["+mas[i]+"]");
        
        if (line_element_count==20 || current_read==amout_of_symbols) {
          System.out.println("LINE IS "+LINE);
          /////////////////////////////////////////////////////////////////////////////////////////
          String neww="", symbol_updated;
          String[] split_to_symbols1=LINE.split(",");
          for (String symbol : split_to_symbols1) {
            String binarry=Integer.toBinaryString(Integer.valueOf(symbol));
            while (binarry.length()<8) {
              binarry="0"+binarry;
            }
            int ascii = Integer.parseInt(binarry, 2);
            char character = (char)ascii;
            //System.out.println("int="+symbol+" as binarry="+binarry+" as symbol="+character);
            neww+=(String.valueOf(character));
          }

          String[] split_to_symbols2=neww.split(",");
          int for_print_index=0;
          String print_search_buffer="", print_look_ahead_buffer="";
          for (String symbols : split_to_symbols2) {
            for_print_index++;
            if (for_print_index<13) {
              print_search_buffer+=symbols+",";
            } else {
              print_look_ahead_buffer+=symbols+",";
            }
          }
          System.out.println("print_search_buffer: "+print_search_buffer);
          System.out.println("print_look_ahead_buffer: "+print_look_ahead_buffer);
          /////////////////////////////////////////////////////////////////////////////////////////

          String[] Split_to_symbols=LINE.split(",");
          int Index=0, search_buffer_index=0, look_ahead_buffer_index=0;
          String Search_buffer="", Look_ahead_buffer="";
          for (String Symbols : Split_to_symbols) {
            Index++;
            if (Index<13) {
              if (search_buffer_index==0) {
                search_buffer_index++;
                Search_buffer+=Symbols;
              } else {
                 Search_buffer+=","+Symbols;
              }
            } else {
              if (look_ahead_buffer_index==0) {
                look_ahead_buffer_index++;
                Look_ahead_buffer+=Symbols;
              } else {
                 Look_ahead_buffer+=","+Symbols;
              }
            }
          }
          System.out.println("search_buffer: "+Search_buffer);
          System.out.println("look_ahead_buffer: "+Look_ahead_buffer);


          String done="";
          String[] string_done_split=new String[Split_to_symbols.length];
          int index_for_string_done_split=0;
          for (String Symbols : Split_to_symbols) {
            string_done_split[index_for_string_done_split]=Symbols;
            index_for_string_done_split++;
          }
          System.out.println("String[] done:");
          for (int l=0; l<Split_to_symbols.length; l++) {
            System.out.print(string_done_split[l]+" ");
          }

          
          int[] int_done_split=new int[Split_to_symbols.length];
          for (int j=0; j<Split_to_symbols.length; j++) {
            int_done_split[j]=Integer.valueOf(string_done_split[j]);
          }
          System.out.println("Int[] done:");
          for (int l=0; l<Split_to_symbols.length; l++) {
            System.out.print(int_done_split[l]+" ");
          }
             System.out.println("");
          for (int element_of_done_split : int_done_split) {
            fout.write(element_of_done_split);
          }

          
          // deleting 1 sybmol . moving the window
          line_element_count--;
          String[] LINE_split=LINE.split(",");
          int Changing_line=0;
          LINE="";
          for (String element : LINE_split) {
            if (Changing_line==0) {
              Changing_line++;
            } else {
              LINE+=element+",";
            }
          }
        } else if(line_element_count<20) {
          LINE+=linee;
          line_element_count++;
        }
      } while (k != 0);
      System.out.println("BROKE");
      
      fin.close();
      fout.close();


    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }


  }

  /////////////////////////////////////////////////////////////////////

  public static void exit() {
    System.out.println("Exiting...");
    System.exit(0);
  }

}