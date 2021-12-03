import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.util.Scanner; 

public class Main{
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
        editStock.setValue();
      } 
      else if(vol == 2){
        double value = ((generateRandom(.05,.2))*editStock.getValue());
        value = Math.ceil(value * 10000) / 10000;
        value = Math.pow(-1,random) * value;
        editStock.editValue(value);
        editStock.setValue();
      } else if(vol == 1) {
        double value = ((generateRandom(0.0,.05))*editStock.getValue());
        value = Math.ceil(value * 10000) / 10000;
        value = Math.pow(-1,random) * value;
        editStock.editValue(value);
        editStock.setValue();
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


  //METHOD OF QUICKSORT
  private static int partition(ArrayList<Stock> myList, int begin, int end) {
    Stock pivot = myList.get(end);
    int i = (begin-1);
    for (int j = begin; j < end; j++) {
        if (myList.get(j).getValue() < pivot.getValue()) {
            i++;
            Stock swapTemp = myList.get(i);
            myList.set(i, myList.get(j));
            myList.set(j,swapTemp);
        }
        else if (myList.get(j).getValue() == pivot.getValue() && myList.get(j).getVolume() < pivot.getVolume()){
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


  //HELPER FUNCTION THAT GIVES MAX BETWEEN 2 INT NUMBERS 
  public static int max(int a, int b){
    return (a > b) ? a : b;
  }


  // FUNCTION THAT RETURNS LIST OF STOCKS TO BUY BASED ON USER'S INVESTMENT VALUE
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
    ArrayList<Stock> toReturn = new ArrayList<Stock>();
    for (Integer i: temp){
      toReturn.add(myList.get((int)i));
    }
    return toReturn;
  }
  

  //KNAPSACK METHOD 
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


  //FUNCTION THAT HELPS FIND PARTICULAR STOCK IN THE MARKET
  public static Stock findStock(ArrayList<Stock> market, String name){
    for(Stock a: market){
      if (a.getName().equals(name)){
        return a;
      }
    }
    Stock empty = new Stock("", 0.0, 0,0);
    return empty;
  }


  //METHOD THAT BUYS STOCKS ACCORDING TO THE USERS REQUIREMENT 
  public static Profile toBuy(Profile user, ArrayList<Stock> market){
    String answer = "y";
    Scanner myObj = new Scanner(System.in);
    while (answer.toLowerCase().equals("y")){
      System.out.println();
      System.out.print("Name of Stock to buy: ");
      answer = myObj.next().toUpperCase();
      Stock returnStock = findStock(market, answer);
      if(returnStock.getName().equals("")){
        System.out.println("Stock name does not exit");
      }
      else{
        //if (user.getInvestAmount() - returnStock.getValue() >= 0){
          //System.out.println("Profile investment not enough to cover charge.");
        //}
        //else{
          user.buyStock(returnStock);
        //}
      }
      myObj = new Scanner(System.in);
      System.out.print("Do you want to buy a stock(y/n)? ");
      answer = myObj.nextLine();
    }
    //myObj.close();
    return user;
  }


  //METHOD THAT SELLS STOCKS ACCORDING TO THE USERS REQUIREMENT 
  public static Profile toSell(Profile user, ArrayList<Stock> market){
    String answer = "y";
    Scanner myObj = new Scanner(System.in);
    System.out.println("Profile Stocks: ");
    for (Stock a: user.getProfileStocks()){
      System.out.println(a.giveStockInfo());
    }
    System.out.println();
    while (answer.toLowerCase().equals("y")){

      System.out.print("Name of Stock to sell: ");
      answer = myObj.next().toUpperCase();
      Stock returnStock = findStock(user.getProfileStocks(), answer);
      if(answer.equals("")){
        System.out.println("Stock name does not exit");
      }
      else{
        user.sellStock(returnStock);
      }
      System.out.print("Do you want to sell a stock(y/n)? ");
      answer = myObj.next();
    }
    //myObj.close();
    return user;
  }


  //METHOD THAT DISPLAYS RECOMENDED STOCKS TO BUY
  public static void showRecommendation(ArrayList<Stock> market, Double invest){
    quickSort(market, 0 , market.size()-1);
    ArrayList<Stock> testingKnapsack = stockRecommend(market, invest);
    
    for(Stock toBuy: testingKnapsack){
      System.out.println(toBuy.giveStockInfo());
    }
  }


  //METHOD THAT DISPLAYS THE MARKET
  public static void showMarket(ArrayList<Stock> market){
    quickSort(market, 0 , market.size()-1);
    for(int i = 0; i < market.size(); i++){
      System.out.println(market.get(i).giveStockInfo());
    } 
  }


  //DRIVER CODE 
  public static void main(String[] args){
    //Creating market 
    ArrayList<Stock> market = createStocks(40);

    //obtaining user name
    Scanner myObj = new Scanner(System.in);
    System.out.print("Enter username: ");
    String userName = myObj.nextLine();

    //obtaining initial balance
    myObj = new Scanner(System.in);
    System.out.print("Transfer initial balance: ");
    String cash = myObj.nextLine();
    Double initial = Double.parseDouble(cash);

    //intial update to profile 
    Profile user = new Profile(userName, new ArrayList<Stock>(), initial);
    System.out.println(user.giveProfileInfo());
    
    //display market in console
    System.out.println();
    showMarket(market);
    System.out.println();

    //user input
    myObj = new Scanner(System.in);
    System.out.print("Do you wish to continue(y/n)? ");
    String answer = myObj.nextLine();
    System.out.println();
    
    while (answer.toLowerCase().equals("y")){
      //display recommended stock
      myObj = new Scanner(System.in);
      System.out.print("Do you want to view recommended stock based on your profile investement(y/n)? ");
      answer = myObj.nextLine();
      System.out.println();
      if (answer.toLowerCase().equals("y")){
        showRecommendation(market, user.getInvestAmount());
        System.out.println();
      }

      //buy stock
      user = toBuy(user, market);
      System.out.println();
      //sell stock
      myObj = new Scanner(System.in);
      System.out.print("Do you want to sell stocks(y/n)? ");
      answer = myObj.next();
      System.out.println();
      if (user.getProfileStocks().size() != 0){
        if (answer.toLowerCase().equals("y")){
          user = toSell(user, market);
        }
      }
      
      System.out.println();
      //update balance
      myObj = new Scanner(System.in);
      System.out.print("Would you like to transfer more cash to your account(y/n): ");
      String cashInput = myObj.nextLine();
      System.out.println();
      if (cashInput.toLowerCase().equals("y")){
        System.out.print("Transfer amount: ");
        String newCash = myObj.next();
        user.addInvestment(Double.parseDouble(newCash));
      }

      //display updated profile
      myObj = new Scanner(System.in);
      System.out.print("Show profile Info(y/n)? ");
      answer = myObj.next();
      System.out.println();
      if (answer.toLowerCase().equals("y")){
        System.out.println(user.giveProfileInfo());
      }
      
      //execute next day
      myObj = new Scanner(System.in);
      System.out.println();
      System.out.print("Go to next Day and continue(y/n)? ");
      answer = myObj.next();
      System.out.println();
      if (answer.toLowerCase().equals("y")){
        market = nextDay(market);
        int num = user.getProfileStocks().size();
        for(int i = 0; i < num; i++){
          user.changeprofileStockValueFromMarket(market, i);
        }
        showMarket(market);
        System.out.println();
      }
    }
    System.out.println("CASH IS KING BABY!");
  }
}
