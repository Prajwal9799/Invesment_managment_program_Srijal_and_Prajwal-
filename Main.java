import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.util.Scanner; 

class Main {
  
  //METHOD TO CREATE A LIST OF num amount of STOCKS
  public static ArrayList<Stock> createStocks(int num) {
    ArrayList<Stock> market = new ArrayList<>();

    for (int i = 0; i < num; i++) {
      String stockName = usingRandom();
      int volume = generateIntRandom(10000, 100000);
      Stock val = new Stock(stockName, generateRandom(5.0, 15.0), generateVolatility(volume), volume);
      market.add(i, val);
    }
    return market;
  }
  
  // HELPER METHOD TO GENERATE RANDOM STRING OF 3 CHARACTERS
  public static String usingRandom() {
    String allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuffer randomString = new StringBuffer();
    for (int i = 0; i < 3; i++) {
      // generate a random number between 0 and length of all characters
      Random random = new Random();
      int randomIndex = random.nextInt(allCharacters.length());
      // retrieve character at index and add it to result
      randomString.append(allCharacters.charAt(randomIndex));
    }
    return randomString.toString();
  }
  
  // HELPER METHOD TO GENERATE RANDOM DOUBLE VALUE NUMBER BETWEEN A RANGE
  public static double generateRandom(double min, double max){
    Random r = new Random();
    double rand = (min + (max - min) * r.nextDouble());
    rand = Math.ceil(rand* 10000) / 10000;
    return rand;
  }

  // HELPER METHOD TO GENERATE RANDOM INT NUMBER BETWEEN A RANGE 
  public static int generateIntRandom(int min, int max){
    int range = max - min + 1;
    int rand = (int)(Math.random() * range) + min;
    return rand;
  }
  
  // HELPER METHOD TO CALCULATE VOLATILITY FOR STOCK BASED ON VOLUME
  public static int generateVolatility(int volume){
    if(volume >= 80000){
      return 3;
    } else if(volume >= 30000){
      return 2;
    } else {
      return 1;
    }
  }
  
  //NOTE: ^ == XOR, not to the power of. So use math.pow(val, exponent)
  
  // METHOD TO GENERATE CHANGE IN VALUE, AND VOLUME FOR MARKET ARRAYLIST OF STOCKS
  public static ArrayList<Stock> nextDay(ArrayList<Stock> alist){
    int length = alist.size();
    for (int i = 0; i < length; i++){
      Stock editStock = alist.get(i);
      int volu = generateIntRandom(10, 5000);
      int random = generateIntRandom(1,2);
      if (random == 1){
        volu = -1 * volu;
      }
      editStock.editVolume(volu);

      int vol = editStock.getVolatility();
      
      random = generateIntRandom(1,2);
      if(vol == 3){
        double value = ((generateRandom(.2,.4))*editStock.getValue());
        value = Math.ceil(value * 10000) / 10000;
        value = Math.pow(-1,random) * value;
        editStock.editValue(value);
      } 
      else if(vol == 2){
        double value = ((generateRandom(.05,.2))*editStock.getValue());
        value = Math.ceil(value * 10000) / 10000;
        value = Math.pow(-1,random) * value;
        editStock.editValue(value);

      } else if(vol == 1) {
        double value = ((generateRandom(0.0,.05))*editStock.getValue());
        value = Math.ceil(value * 10000) / 10000;
        value = Math.pow(-1,random) * value;
        editStock.editValue(value);
      }
      editStock.editVolatility(vol);
      alist.set(i, editStock);
    }
    return alist;
  }

  //QUICK SORT METHOD TO SORT ARRAYLIST OF STOCKS
  public static void quickSort(ArrayList<Stock> myList, int begin, int end) {
    
    if (begin <= end) {
        int partitionIndex = partition(myList, begin, end);

        quickSort(myList, begin, partitionIndex-1);
        quickSort(myList, partitionIndex+1, end);
    }

  }
  //method of quicksort
  private static int partition(ArrayList<Stock> myList, int begin, int end) {
    Stock pivot = myList.get(end);
    int i = (begin-1);

    for (int j = begin; j < end; j++) {
        if (myList.get(j).getValue() <= pivot.getValue()) {
            i++;

            Stock swapTemp = myList.get(i);
            myList.set(i, myList.get(j));
            myList.set(j,swapTemp);
        }
    }

    Stock swapTemp = myList.get(i+1);
    myList.set(i+1, myList.get(end));
    myList.set(end, swapTemp);

    return i+1;
  }

  //Helper function that gives max between 2 int numbers
  public static int max(int a, int b){
    return (a > b) ? a : b;
  }
  // Function that returns list of stocks to buy based on user's investment value
  public static ArrayList<Stock> stockRecommend(ArrayList<Stock> myList, double investment){
    int W = (int)investment;
    int n = myList.size();
    int wt[] = new int[n];
    int val[] = new int[n];

    for (int i = 0; i< n; i++) {
      wt[i] = (int) myList.get(i).getValue();
      val[i] = myList.get(i).getVolume();
    }
    ArrayList<Integer> temp = knapSack(W, wt, val, n); 
    //ArrayList<Integer> listOfpositions = temp;
    
    ArrayList<Stock> toReturn = new ArrayList<Stock>();
    for (Integer i: temp){
      toReturn.add(myList.get((int)i));
    }
    //for val in toRetun
    return toReturn;
  }
  

  private static ArrayList<Integer> knapSack(int W, int wt[],int val[], int n){
    int i, w;
    int K[][] = new int[n + 1][W + 1];
    ArrayList<Integer> toReturn = new ArrayList<Integer>();
        
    for (i = 0; i <= n; i++) {
      for (w = 0; w <= W; w++) {
        if (i == 0 || w == 0){
          K[i][w] = 0;
        }
        else if (wt[i - 1] <= w){
          K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
        }
        else{
          K[i][w] = K[i - 1][w];
        }
      }
    }
 
    int res = K[n][W];
 
    w = W;
    for (i = n; i > 0 && res > 0; i--) {
      // either the result comes from the top
      // (K[i-1][w]) or from (val[i-1] + K[i-1]
      // [w-wt[i-1]]). If it comes from the latter 
      // one it means the item is included.
      if (res == K[i - 1][w]){
        continue;
      }
      else {
        // This item is included.
        toReturn.add(i-1);
        res = res - val[i - 1];
        w = w - wt[i - 1];
      }
    }
    
    return toReturn;
  }
  
  public static void main(String[] args){

    ArrayList<Stock> myList = createStocks(20);
    System.out.println("Print stocks:");
    int length = myList.size();
    for (int i = 0; i < length; i++){
      System.out.println(myList.get(i).giveStockInfo());
    }
    System.out.println();

    System.out.println("testing quicksort");
    quickSort(myList, 0, myList.size()-1);
    for (int i = 0; i < length; i++){
      System.out.println(myList.get(i).giveStockInfo());
    }
    System.out.println();

    System.out.println("testing knapsack to recommend");
    ArrayList<Stock> testingKnapsack = stockRecommend(myList,40);
    for (int i = 0; i < testingKnapsack.size(); i++){
      System.out.println(testingKnapsack.get(i).giveStockInfo());
    }
    System.out.println();

    System.out.println("testing next day");
    myList = nextDay(myList);
    for (int i = 0; i < length; i++){
      System.out.println(myList.get(i).giveStockInfo()); 
    }
    System.out.println();
    quickSort(myList, 0, myList.size()-1);

    System.out.println();
    System.out.println("Testing Profile");

    Profile user = new Profile(new ArrayList<Stock>(), 40.0);
    testingKnapsack = stockRecommend(myList,40);
    
    for(Stock toBuy: testingKnapsack){
      if (user.getInvestAmount() - toBuy.getValue() >= 0){
        user.buyStock(toBuy);
        System.out.println(toBuy.giveStockInfo());
      }
    }


    System.out.println(user.giveProfileInfo());

    myList = nextDay(myList);
    for (int i = 0; i < length; i++){
      System.out.println(myList.get(i).giveStockInfo());
    }
    System.out.println();
    quickSort(myList, 0, myList.size()-1);
    
    int num = user.getProfileStocks().size();
    for(int i = 0; i < num; i++){
      user.changeprofileStockValueFromMarket(myList, i);
    }

    System.out.println(user.giveProfileInfo());

    testingKnapsack = stockRecommend(myList,user.getInvestAmount());

    for(Stock toBuy: testingKnapsack){
      if (user.getInvestAmount() - toBuy.getValue() >= 0){
        user.buyStock(toBuy);
        System.out.println(toBuy.giveStockInfo());
      }
    }


  }
  
}