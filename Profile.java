import java.util.ArrayList;

class Profile{
  
  String userName = "";
  ArrayList<Stock> stocks = new ArrayList<>();
  double invest;
  double value;

  public Profile(String name, ArrayList<Stock> stocks, double invest){
    this.userName = name;
    this.stocks = stocks;
    this.invest = invest;
    this.value = findValue(stocks);
  }

  public static double findValue(ArrayList<Stock> stockList){
    double total = 0;
    //for (Iterator<E> iter = myStocks.iterator(); iter.hasNext();){
    for (int i = 0; i < stockList.size(); i++){              
       total += stockList.get(i).getValue();
    }

    return total;
  }

  public String getUserName(){
    return userName;
  }

  public double getValue(){
    return value;
  }

  public ArrayList<Stock> getProfileStocks(){
    return stocks;
  }

  public double getInvestAmount(){
    return invest;
  }

  public String giveProfileInfo(){
    return ("The user," + userName + " has " + Integer.toString(stocks.size()) + " number of stocks with a combined profile investment of " + String.format("%.4f",invest));
  }
  public void buyStock(Stock newStock){
    stocks.add(newStock);
    invest -= newStock.getValue();
    value = findValue(stocks);
  }

  public void sellStock(Stock removeStock){
    stocks.remove(removeStock);
    invest += removeStock.getValue();
    value = findValue(stocks);
  }

  public void addInvestment(double val){
    invest += val;
  }

  public void changeprofileStockValueFromMarket(ArrayList<Stock> market, int k){
    int n = market.size();
    for (int i = 0; i < n; i++)
    {
      if (market.get(i).getName().toString().equals(stocks.get(k).getName().toString())){
        stocks.get(k).editValue(market.get(i).getValue()-stocks.get(k).getValue());
      }
    }
    value = findValue(stocks);
  }
}

