 public class Weather{
	private double tempF;
  private String condition;
  private double tempC;
  private String imageURL;

  public Weather(double tempF, String condition, double tempC, String imageURL){
    this.tempF = tempF;
    this.tempC = tempC;
    this.condition = condition;
		this.imageURL = imageURL;
  }

  public String getCondition(){
	  return condition;
  }

  public double getTempC(){
    return tempC;
  }

  public double getTempF(){
    return tempF;
  }

   public String getImageURL(){
	  return imageURL;
  }
}