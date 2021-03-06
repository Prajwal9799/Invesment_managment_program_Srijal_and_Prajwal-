import java.util.ArrayList;

public class Stock{

  String name;
  double value;
  int volatility;
  int volume;
  
  public Stock(String name, double value, int volatility, int volume){
      this.name = name;
      this.value = value;
      this.volatility = volatility;
      this.volume = volume;
    }

  public String getName(){
    return name;
  }
 
  public double getValue(){
    return value;
  }

  public int getVolatility(){
    return volatility;
  }
  
  public int getVolume(){
    return volume;
  }

  public void editValue(Double add){
    value += add;
  }
    
  public void editVolatility(int val){
    volatility = val;
  }

  public void editVolume(int add){
    volume += add;
  }

  public void setValue(){
    if (value <= 0){
    value += 1;
    }
  }

  public String giveStockInfo(){
    return (name + " has a value of " + String.format("%.4f", value) + " with " + Integer.toString(volume) + " volume");
  }

}
