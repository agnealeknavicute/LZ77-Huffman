
import java.util.Scanner;

import java.io.File;
import java.io.EOFException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    Huffman_coding_Tree huffmanTree = new Huffman_coding_Tree();

    byte[] lz77Data = readLZ77File("/Users/agnealeknavicute/university/java/LZ77_text");

    huffmanTree.Create_Huffman_Tree(lz77Data);

    huffmanTree.printHuffmanTree();
    loop: while (true) {
      System.out.println("\n");
      String[] commands={"LZ77_comp","LZ77_decomp",  "Huffman_coding_comp","Huffman_coding_decomp",  "Deflate_comp","Deflate_decomp",  "size",  "equal",  "about"};
      
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
        String linee = "";
        current_read += 1;
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

        if (line_element_count == 20 || current_read == amout_of_symbols) {
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

          String[] split_to_symbols2 = neww.split(";");
          int for_print_index = 0;
          String print_search_buffer = "", print_look_ahead_buffer = "";
          for (String symbols : split_to_symbols2) {
            for_print_index++;
            if (for_print_index < 13) {
              print_search_buffer += symbols + ";";
            } else {
              print_look_ahead_buffer += symbols + ";";
            }
          }
          System.out.println("print_search_buffer: " + print_search_buffer);
          System.out.println("print_look_ahead_buffer: " + print_look_ahead_buffer);
          /////////////////////////////////////////////////////////////////////////////////////////

          String[] Split_to_symbols = LINE.split(";");
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
          System.out.println("String[] done:");
          for (int l = 0; l < Split_to_symbols.length; l++) {
            System.out.print(string_done_split[l] + " ");
          }
          
          

          int[] int_done_split = new int[Split_to_symbols.length];
          for (int j = 0; j < Split_to_symbols.length; j++) {
            int_done_split[j] = Integer.valueOf(string_done_split[j]);
          }
          System.out.println("Int[] done:");
          for (int l = 0; l < Split_to_symbols.length; l++) {
            System.out.print(int_done_split[l] + " ");
          }
          System.out.println("");
          for (int element_of_done_split : int_done_split) {
            fout.write(element_of_done_split);
          }

          // deleting 1 sybmol . moving the window
          line_element_count--;
          String[] LINE_split = LINE.split(";");
          int Changing_line = 0;
          LINE = "";
          for (String element : LINE_split) {
            if (Changing_line == 0) {
              Changing_line++;
            } else {
              LINE += element + ";";
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