
import java.util.Scanner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.EOFException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
//import java.io.Serializable;


//

public class Main {
  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("Hello world!");
    Huffman_coding_Tree huffmanTree = new Huffman_coding_Tree();

    byte[] lz77Data = readLZ77File("/Users/agnealeknavicute/university/java/LZ77_text");

    huffmanTree.Create_Huffman_Tree(lz77Data);

    huffmanTree.printHuffmanTree();
    loop: while (true) {
      System.out.println("\n");
      String[] commands={"LZ77_comp","LZ77_decomp",  "Huffman_coding_comp","Huffman_coding_decomp",  "Deflate_comp","Deflate_decomp",  "size",  "equal",  "about", "exit"};
      
      //System.out.println("Commands, Command explenation");
      System.out.printf("\n%-8s%-23s%-20s", "Number", "Commands", "Command explenation");
      for (int i=0; i<commands.length; i++) {
        //System.out.println((i+1)+". "+commands[i]+" - "+commands[i].replace("_"," "));
        System.out.printf("\n%-8d%-23s%-19s", (i+1), commands[i], commands[i].replace("_"," "));
      }
      System.out.println();

      String choice=sc.next(), rest_of_command="";

      switch (choice) {
        case "to_binarry":
          to_binarry(rest_of_command);
          break;
        case "LZ77_comp":
          LZ77_comp();
          break;
        case "LZ77_decomp":
          LZ77_decomp();
          break;
        case "Huffman_coding_comp":
          Huffman_coding_comp();
          break;
        case "Huffman_coding_decomp":
          Huffman_coding_decomp();
          break;
        case "Deflate_comp":
          Deflate_comp();
          break;
        case "Deflate_decomp":
          Deflate_decomp();
          break;
        case "size":
          size();
          break;
        case "equal":
          equal();
          break;
        case "about":
          about();
          break;
        case "exit":
          exit();
          break loop;
        // break;
      }

    }

  }

  /////////////////////////////////////////////////////////////////////
  public static void to_binarry(String convert_file) {
  }

  public static byte[] readLZ77File(String filePath) {
    File file = new File(filePath);
    byte[] data = new byte[(int) file.length()];
    try (FileInputStream fis = new FileInputStream(file)) {
      fis.read(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  /////////////////////////////////////////////////////////////////////
  
  public static void LZ77_comp() {
    System.out.println("Source file name (which will be comp using LZ77):");
    String filename=sc.next();

    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }

    String the_text="";

    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line;
      int amout_of_words=0, amout_of_symbols = 0, current_read = 0;
      while ((line = br.readLine()) != null) {
        String[] split_to_words=line.split(" ");
        amout_of_words+=split_to_words.length;
        String[] split_to_symbols = line.split("");
        for (String symbol : split_to_symbols) {
          amout_of_symbols++;
        }
      }
      System.out.println("Amout of symbols is " + amout_of_symbols);
      br.close();

      FileInputStream fin = new FileInputStream(filename);
      FileOutputStream fout = new FileOutputStream("LZ77_comp_" + filename);
      System.out.println(fin);
      String LINE = "";
      int wrote_to_line = 0, line_element_count = 0;

      int i = 0, k;
      boolean continue_comp = true;
      byte mas[] = new byte[1];
      do {
        k = fin.read(mas);
        line_element_count++;
        current_read++;
        if (current_read >= amout_of_symbols) {
          // k=0;
          continue_comp = false;
        }
        if (wrote_to_line == 0) {
          wrote_to_line++;
          LINE += String.valueOf(mas[i]);
        } else {
          LINE += ";" + String.valueOf(mas[i]);
        }
        System.out.print("[" + mas[i] + "] ");

        if (line_element_count == 100 || current_read == amout_of_symbols) {
          System.out.println("LINE IS " + LINE);
          /////////////////////////////////////////////////////////////////////////////////////////
          String neww = "", symbol_updated;
          String[] split_to_symbols1 = LINE.split(";");
          for (String symbol : split_to_symbols1) {
            String binarry = Integer.toBinaryString(Integer.valueOf(symbol));
            while (binarry.length() < 8) {
              binarry = "0" + binarry;
            }
            int ascii = Integer.parseInt(binarry, 2);
            char character = (char) ascii;
            // System.out.println("int="+symbol+" as binarry="+binarry+" as
            // symbol="+character);
            neww += (String.valueOf(character));
          }
          /////////////////////////////////////////////////////////////////////////////////////////

          String[] Split_to_symbols = LINE.split(";");
          System.out.println("Split_to_symbols.length=" + Split_to_symbols.length);
          int Index = 0, search_buffer_index = 0, look_ahead_buffer_index = 0;
          String Search_buffer = "", Look_ahead_buffer = "";
          for (String Symbols : Split_to_symbols) {
            Index++;
            if (Index < 13) {
              if (search_buffer_index == 0) {
                search_buffer_index++;
                Search_buffer += Symbols;
              } else {
                Search_buffer += ";" + Symbols;
              }
            } else {
              if (look_ahead_buffer_index == 0) {
                look_ahead_buffer_index++;
                Look_ahead_buffer += Symbols;
              } else {
                Look_ahead_buffer += ";" + Symbols;
              }
            }
          }
          System.out.println("search_buffer: " + Search_buffer);
          System.out.println("look_ahead_buffer: " + Look_ahead_buffer);

          String done = "";
          String[] string_done_split = new String[Split_to_symbols.length];
          int index_for_string_done_split = 0;
          for (String Symbols : Split_to_symbols) {
            string_done_split[index_for_string_done_split] = Symbols;
            index_for_string_done_split++;
          }
          System.out.println("string_done_split.length=" + string_done_split.length);
          System.out.println("String[] done:");
          for (int l = 0; l < Split_to_symbols.length; l++) {
            System.out.print(string_done_split[l] + " ");
          }

          //
          //
          System.out.println("\nStarting another String[]");

          String[] another_string_done_split = new String[string_done_split.length];
          another_string_done_split[0]=string_done_split[0];
          another_string_done_split[1]=string_done_split[1];
          another_string_done_split[2]=string_done_split[2];
          System.out.println("\nWrote 0. , 1. , 2. space");

          int writed_index=3;
          for (int index1=3; index1<string_done_split.length; index1++) {
            int temp=index1, amount_of_matches=0;
            
            another_string_done_split[writed_index]=string_done_split[index1];
            System.out.println("looking for "+another_string_done_split[writed_index]);
            String trying_to_replace=string_done_split[index1];
            String past_elements="", current_reading="";
            String longest_matching="";
            int start_of_match=0;
            boolean first_match=true;

            for (int index2=0; index2<index1; index2++) {
              current_reading=string_done_split[index2];
              if ((past_elements+current_reading).equals(trying_to_replace)) {
                past_elements+=current_reading;
              }
              if (past_elements.equals(trying_to_replace)) {
                longest_matching=past_elements;
                amount_of_matches++;
                if (first_match) {
                  start_of_match=index1-index2;
                  first_match=false;
                }
                if (index1<=string_done_split.length-2) {
                  index1++;
                  trying_to_replace+=string_done_split[index1];
                }
              }
            }

            if (amount_of_matches>3) {
              System.out.println("longest match is "+amount_of_matches+" "+longest_matching);
              //String replacement="<("+start_of_match+","+amount_of_matches+")>";
              //String replacement=start_of_match+"126"+amount_of_matches;
              //another_string_done_split[index1]=replacement;
              another_string_done_split[writed_index]=""+start_of_match;
              another_string_done_split[writed_index+1]="126";
              another_string_done_split[writed_index+2]=""+amount_of_matches;
              writed_index+=2;
            } else {
              index1=temp;
            }
            //if (index1<=string_done_split.length-2) writed_index++;
            writed_index++;
          }

          System.out.println("\n printing another string list");
          for (int idkkk=0; idkkk<another_string_done_split.length; idkkk++) {
            if (another_string_done_split[idkkk]==null) break;
            System.out.print(another_string_done_split[idkkk]+" ");
          }
          System.out.println("\n");


          if (continue_comp) {
            fout.write(Integer.valueOf(another_string_done_split[0]));
          } else {
            for (int index=0; index<another_string_done_split.length; index++) {
              if (another_string_done_split[index]==null) break;
              fout.write(Integer.valueOf(another_string_done_split[index]));
            }
          }

          //int[] int_done_split = new int[Split_to_symbols.length];
          //for (int j = 0; j < Split_to_symbols.length; j++) {
          //  int_done_split[j] = Integer.valueOf(string_done_split[j]);
          //}
          //System.out.println("Int[] done:");
          //for (int l = 0; l < Split_to_symbols.length; l++) {
          //  System.out.print(int_done_split[l] + " ");
          //}
          //System.out.println("");
          //for (int element_of_done_split : int_done_split) {
          //  fout.write(element_of_done_split);
          //}


          // deleting 1 sybmol . moving the window
          line_element_count--;
          String[] LINE_split = LINE.split(";");
          int Changing_line = 0;
          LINE = "";
          for (String element : LINE_split) {
            if (Changing_line == 0) {
              Changing_line++;
            } else {
              if (Changing_line==1) {
                Changing_line++;
                LINE += element;
              } else {
                LINE +=";"+element;
              }
            }
          }
        }
      } while (continue_comp);
      System.out.println("BROKE");

      fin.close();
      fout.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

  }

  /////////////////////////////////////////////////////////////////////

  public static void LZ77_decomp() {
    System.out.println("Archive name (which will be decomp using LZ77):");
    String filename=sc.next();


    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }

    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line, index_of_shortened_part_middle="";
      int amout_of_symbols = 0, current_read = 0, shortened_count=0;
      while ((line = br.readLine()) != null) {
        String[] split_to_symbols = line.split("");
        for (String symbol : split_to_symbols) {
          amout_of_symbols++;
          if (symbol.equals("~")) {
            shortened_count++;
            index_of_shortened_part_middle+=(amout_of_symbols-1)+";";
          }
        }
      }
      br.close();

      System.out.println("Amout of symbols is " + amout_of_symbols+" and shortened count is "+shortened_count);
      System.out.println("index_of_shortened_part_middle="+index_of_shortened_part_middle);

      String[] split_index_of_shortened_part_middle=index_of_shortened_part_middle.split(";");
      int[] sorted_shortened_part_middle=new int[split_index_of_shortened_part_middle.length];
      int index_sorted_shortened_part_middle=0;
      for (String index : split_index_of_shortened_part_middle) {
        sorted_shortened_part_middle[index_sorted_shortened_part_middle]=Integer.valueOf(index);
        index_sorted_shortened_part_middle++;
      }
      
      FileInputStream fin = new FileInputStream(filename);
      FileOutputStream fout = new FileOutputStream("LZ77_decomped_" + filename);
      System.out.println(fin);
      String LINE = "";
      int wrote_to_line = 0, line_element_count = 0;

      int i = 0, k;
      boolean continue_comp = true;
      byte mas[] = new byte[1];
      do {
        k = fin.read(mas);
        line_element_count++;
        current_read++;
        if (current_read >= amout_of_symbols) {
          // k=0;
          continue_comp = false;
        }
        if (wrote_to_line == 0) {
          wrote_to_line++;
          LINE += String.valueOf(mas[i]);
        } else {
          LINE += ";" + String.valueOf(mas[i]);
        }
        System.out.print("[" + mas[i] + "] ");

        if (current_read == amout_of_symbols) {
          continue_comp=false;
          System.out.println("LINE IS " + LINE);
          
          String[] Split_to_symbols = LINE.split(";");

          String done = "";
          String[] string_done_split = new String[Split_to_symbols.length];
          int index_for_string_done_split = 0;
          for (String Symbols : Split_to_symbols) {
            string_done_split[index_for_string_done_split] = Symbols;
            index_for_string_done_split++;
          }
          
          int max=string_done_split.length;
          for (int elements_index=0; i<max; i++) {
            if (string_done_split[elements_index+1].equals("~")) {
              int space_back=Integer.valueOf(string_done_split[elements_index]), amount_of_symbols=Integer.valueOf(string_done_split[elements_index+2]);
              String temp="";
              for (int restoring=elements_index-space_back; restoring<amount_of_symbols; restoring++) {
                temp+=string_done_split[restoring];
              }
              
            }
          }
        }
          
      } while (continue_comp);
      System.out.println("BROKE");

      fin.close();
      fout.close();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }
    
  }

  /////////////////////////////////////////////////////////////////////

  public static void Huffman_coding_comp() {
    System.out.println("Source file name (which will be comp using Huffman coding):");
    String filename=sc.next();

    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }
  }

  /////////////////////////////////////////////////////////////////////

  public static void Huffman_coding_decomp() {
    System.out.println("Archive name (which will be decomp using Huffman coding):");
    String filename=sc.next();

    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }
  }

  /////////////////////////////////////////////////////////////////////

  public static void Deflate_comp() {
    System.out.println("Source file name (which will be comp using Deflate):");
    String filename=sc.next();

    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }
    LZ77_comp();
    Huffman_coding_comp();
  }

  /////////////////////////////////////////////////////////////////////

  public static void Deflate_decomp() {
    System.out.println("Archive name (which will be decomp using Deflate):");
    String filename=sc.next();

    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }
    Huffman_coding_decomp();
    LZ77_decomp();
  }

  /////////////////////////////////////////////////////////////////////

  public static void size() {
    System.out.println("File name:");
    String filename=sc.next();
    
    File f = new File(filename);
    if (!f.exists()) {
      System.out.println("No such a file exsists");
      return;
    }
    
    try {
      File file = new File(filename);
      System.out.println("size: "+file.length());
    } catch (Exception e) {
      System.out.println(e);
    }

  }
  /////////////////////////////////////////////////////////////////////

  public static void equal() {
    System.out.println("First file name:");
    String first_filename=sc.next();
    
    File f1 = new File(first_filename);
    if (!f1.exists()) {
      System.out.println("No such a file exsists");
      return;
    }

    System.out.println("Second file name:");
    String second_filename=sc.next();
    
    File f2 = new File(second_filename);
    if (!f2.exists()) {
      System.out.println("No such a file exsists");
      return;
    }

    try {
      Path path_1_file=Paths.get(first_filename), path_2_file=Paths.get(second_filename);
      //long mismatchPosition = Files.mismatch(path_1_file, path_2_file);
      //if (mismatchPosition == -1L) {
      //  System.out.println("true");
      //} else {
      //  System.out.println("false");
      //}
      
      // !! Izmantota daļa no https://www.baeldung.com/java-compare-files
      BufferedInputStream first_file=new BufferedInputStream(new FileInputStream(path_1_file.toFile()));
      BufferedInputStream second_file=new BufferedInputStream(new FileInputStream(path_2_file.toFile()));
      int ch=0;
      long pos=1;
      while ((ch=first_file.read())!=-1) {
        if (ch!=second_file.read()) {
          System.out.println("false");
          return;
        }
        pos++;
      }
      System.out.println("true");

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /////////////////////////////////////////////////////////////////////

  public static void about() {
    System.out.printf("\n%-19s%-9s%-14s%-8s", "Apliecības numurs", "Vārds", "Uzvārds", "Grupa");
    System.out.printf("\n%-19s%-9s%-14s%-10s", "231RDB197", "Roberts", "Jansons", "RDBIO 1.");
    System.out.printf("\n%-19s%-9s%-14s%-10s", "231RDB221", " Agne", "Aleknavičūte", "RDBIO 1.");
  }

  /////////////////////////////////////////////////////////////////////

  public static void exit() {
    System.out.println("Exiting...");
    System.exit(0);
  }

}