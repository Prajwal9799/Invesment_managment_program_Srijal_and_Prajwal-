import java.util.ArrayList;


class Profile{
    
  ArrayList<Stock> stocks = new ArrayList<>();
  double invest = 0.0;
  double value = 0.0;

  public Profile(ArrayList<Stock> stocks, double invest){
    this.stocks = stocks;
    this.invest = invest;
    this.value = findValue(stocks);
  }

  public static double findValue(ArrayList<Stock> stockList){
    double total = 0;
    //for (Iterator<E> iter = myStocks.iterator(); iter.hasNext();){
    for (int i = 0; i < stockList.size(); i++){               total += stockList.get(i).getValue();
    }

    return total;
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
    return ("The user has " + Integer.toString(stocks.size()) + " number of stocks with a combined profile value of " + Double.toString(value));
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
  /*
  public void editStockValuebyMarket(ArrayList<Stock> market){
    for(Stock astock: stocks){
      Main.changeprofileStockValueFromMarket(market, astock);      
    }
    value = findValue(stocks);
  }*/
}

//Profile myProfile = new Profile();