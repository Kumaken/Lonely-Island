//Preprocessing libraries:
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.ArrayList; //Digunakan ArrayList yang bersifat dinamik agar manajemen memori lebih akurat dan mudah.
import java.util.Arrays;
import java.util.List;
import java.lang.Thread;

public class LonelyIsland implements Runnable {
// Global Variables--------------------------------------------------------------:
  //Global variable mencatat deadends yang ditemukan:
  public static ArrayList<Integer> deadEnds = new ArrayList<Integer>(); 
  // Mempersiapkan Matriks adjacency sebagai representasi graf.
  public static ArrayList<ArrayList<Integer>> AdjcMat = new ArrayList<ArrayList<Integer>>();
// Helper Functions---------------------------------------------------------------:
  //H.F. melakukan print enumerasi
  public static void printArray(ArrayList<Integer> arr){
    int neff = arr.size();
    for(int i=0; i<neff;i++){
      if (i== neff-1)
        System.out.print(arr.get(i));
      else
        System.out.print(arr.get(i)+">>");
    }
    System.out.println();
  }
  //H.F. melakukan deepcopy pada array solusi:
  public static void copySols(ArrayList<Integer> arr1,ArrayList<Integer> arr2){
    arr2.clear();
    for(int i=0; i<arr1.size();i++){
      arr2.add(arr1.get(i));
    }
  }
  //H.F. melakukan pengecekan apakah array pilihan mengandung nilai integer x
  public static boolean arrayContains(ArrayList<Integer> arr, int x){
    for(int i=0; i<arr.size();i++){
      if (arr.get(i)==x)
        return true;
    }
    return false;
  }
  //H.F. Scanner function untuk melakukan pembacaan file eksternal
  private static Scanner extracted(File file) throws FileNotFoundException {
    Scanner sc = new Scanner(file);
    return sc;
  }
// Decrease and Conquer Algorithm---------------------------------------------------------------:
  public static void DnCrecursion(int vtx, ArrayList<Integer> Adjclist, ArrayList<Integer> pathChosen ){
  // Jika ditemukan deadend:
    boolean foundonce=false; //dummmy variable untuk mengecek apakah ditemukan minimal satu simpul yang cocok dan bertetangga.
    pathChosen.add(vtx);
    // Simpul bukan deadend:
    if (!(Adjclist.size()==0)){      
      //Rekursif untuk semua simpul yang bertetangga dengan simpul vtx.
      for(int i=0;i<Adjclist.size();i++){
        //Jika belum traversed, maka akan dipilih:
        if (!arrayContains(pathChosen, Adjclist.get(i))){
            //Dilempar ke rekursif DnC lagi.
            foundonce=true; // Ditemukan simpul bertetangga yang cocok!
            DnCrecursion(Adjclist.get(i),AdjcMat.get(Adjclist.get(i)-1), pathChosen);
        }
      }
    }
      
    if(!foundonce){
      //Menulis hasil ke layar output:
      printArray(pathChosen);
      //Mencatat deadends yang ditemukan:
      //KONDISI: simpul deadends tidak boleh duplikat!
      if (!arrayContains(deadEnds, vtx)){
        deadEnds.add(vtx);
      }
    }
    //Reset array traversed agar tidak terjadi pengulangan pencetakan. 
    pathChosen.remove((Integer)vtx);
  }
  public static void CallMainAlgorithm(int vtx, int totalvtx){
    //Fungsi pemanggil rekursif agar pemanggilan di mainprogram lebih sederhana.
    ArrayList<Integer> pathChosen = new ArrayList<Integer>();
    //Penampilan hasil algoritma:
    System.out.println("-----------------------------");
    System.out.println("Enumerations: ");
    long startTime = System.nanoTime(); // Menghitung waktu eksekusi algorithm
    DnCrecursion(vtx, AdjcMat.get(vtx-1),pathChosen); // MAIN ALGORITHM
    long stopTime = System.nanoTime(); // Stop hitung.
    System.out.println("-----------------------------");
    System.out.println("Deadends adalah:");
    Collections.sort(deadEnds);//Sort deadEnds list.
    System.out.println(Arrays.deepToString(deadEnds.toArray()));
    System.out.println("-----------------------------");
    System.out.print("Execution Time is ...");
    System.out.print( (double)(stopTime - startTime) / 1000000000);System.out.println(" seconds!"); 
  }

  // Threading---------------------------------------------------------------:
  public static void main(String[] args) throws Exception {
    new Thread (null, new LonelyIsland(), "Threading!", 1 << 26).start();
  }
  // Main Program---------------------------------------------------------------:
  public void run(){
    // Mempersiapkan file input dan scanner untuk proses pembacaan dari file.
    //Meminta input filename dari user:
    System.out.println("LONELY ISLANDS");
    Scanner uinput = new Scanner(System.in);
    System.out.println("Please enter your desired filename: (Without \" \", but include extension!)");
    String filename= uinput.nextLine();
    uinput.close();
    //ERROR CATCHING (FILE EXTERNAL):
    try{
      //Memanggil filename:
      File file = new File(filename);
      Scanner sc = extracted(file);
      String[] temp = sc.nextLine().split("\\s+"); // memisahkan whitespaces.
      int vtxnum = Integer.parseInt(temp[0]); //vtxnum -> banyaknya simpul
      //int edgnum = Integer.parseInt(temp[1]); //edgnum -> banyaknya busur
      int vstart =  Integer.parseInt(temp[2]); //vstart -> starting point


      //Pembacaan file dilakukan & Mengisi adjacency Matrix graf:
      // Mempersiapkan Matriks adjacency sebagai representasi graf.
      for(int i=0;i<vtxnum;i++){
        AdjcMat.add(new ArrayList<Integer>());
      }
      while (sc.hasNextLine()) {
        temp = sc.nextLine().split("\\s+"); // memisahkan whitespaces.
        AdjcMat.get(Integer.parseInt(temp[0])-1).add(Integer.parseInt(temp[1]));
      }
      sc.close();

      //Penampilan Adjacency Matriks: (JIKA DIBUTUHKAN)
      /*
      System.out.println("Adjacency matriks: ");
      for(int i=0;i<vtxnum;i++){
        System.out.println(Arrays.deepToString(AdjcMat[i].toArray()));
      }*/
      CallMainAlgorithm(vstart, vtxnum); // MAIN ALGORITHM!
    }
    catch (FileNotFoundException notfound){
      System.out.println("Invalid File Name. Please stop playing around...");  
    }
  }
}