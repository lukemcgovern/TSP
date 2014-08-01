

public class Town
{
    public int number; 
    public double lat;
    public double lon;
    public boolean visited;
    
    public Town(int num,double latitude,double longitude,boolean v )
    {
        number=num;
        lat=latitude;
        lon=longitude;
        visited=v;
    }
    
    public Town()
    {
        number=0;
        lat=0.0;
        lon=0.0;
        visited=false;
    }

}
