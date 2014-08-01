public class TSP
{
    public static void main(String args[]){
        int townNum =80;
        Town[] array= new Town[townNum];
       
        
      double [] latArray = new double[townNum];
      double [] longArray = new double[townNum];
         
     
     
     FileIO reader2 = new FileIO(); 
     String[] tLat = reader2.load("lat.txt"); 
     for(int i=0;i<townNum;i++){
            latArray[i]=Double.parseDouble(tLat[i]);
        }
     
     FileIO reader3 = new FileIO(); 
     String[] tLong = reader3.load("Long.txt");    
     for(int i=0;i<townNum;i++){
            longArray[i]=Double.parseDouble(tLong[i]);
        }  
      
      for(int i=0;i<townNum;i++){
        Town newTown = new Town(i+1,latArray[i],longArray[i],false);
        array[i]=newTown;
        }
      
       double[][] shortDist = new double[array.length][array.length];
        
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length;j++){
                shortDist[i][j]=GetDistance(array[i],array[j]);
            }
        }
         
     nextTown(array,shortDist);
      
    }

    public static void nextTown(Town[] array,double[][] shortDist){
         int[] bestPath = new int[array.length];
         double bestDist=10000000;
         //timer start;
         long timer = System.currentTimeMillis();
        while(true){
        for(int i=0;i<array.length;i++){
            boolean [] visited = new boolean[array.length];
            visited[i]=true;
            int [] numArray = new int[array.length];
            numArray[0]=i+1;
            double totalDist=0.0;
             int index=i;
             int position=i;
            
               for(int j=1;j<array.length;j++){
                     double shortDistance=10000000;
                     //int index=0;
                    for(int k=0;k<array.length;k++){
                        double add= Math.random()*15;
                        if((shortDist[position][k]+add)<shortDistance && shortDist[position][k]!=0 && visited[k]==false){
                            shortDistance=shortDist[position][k];
                            index=k;
                        }
                    }
                    position=index;
                    visited[index]=true;
                    numArray[j]=index+1;
                    totalDist=totalDist+shortDistance;
                    
                    if(totalDist>bestDist){
                     break;
                    }
               }

            totalDist=totalDist+GetDistance(array[i],array[position]);
            
            //Path newBest2=TwoOpt(numArray,totalDist,shortDist);

            if(totalDist<bestDist){
                bestDist=totalDist;
                bestPath=numArray;
                System.out.println(bestDist);
                for(int b=0;b<bestPath.length;b++){
                    System.out.print(bestPath[b]+".");
                 }
                System.out.println();
              
            }
                                                
            if(System.currentTimeMillis()-timer>30000){
                     Path newBest=TwoOpt(bestPath,bestDist,shortDist);
                     System.out.println("Exited Path");
                     bestPath=newBest.path;
                     bestDist=newBest.dist;
                     System.out.println(bestDist);
                for(int b=0;b<bestPath.length;b++){
                    System.out.print(bestPath[b]+".");
                 }
                System.out.println();
                     timer = System.currentTimeMillis();
                 }
                 
        }
        
      }
  }
  
  public static Path TwoOpt(int[] bestPath, double bestDist, double[][] shortDist){
      System.out.println("In Path Class");
      Path best = new Path(bestPath,bestDist);
      System.out.println(best.dist);
       for(int b=0;b<best.path.length;b++){
                    System.out.print(best.path[b]+".");
                  }
       System.out.println();
         for(int g=0;g<1000;g++){       
              for(int i=0;i<bestPath.length-30;i++){
                    for(int j=i+1;j<i+30;j++){
                        int[] newPath =new int[bestPath.length];
                            for(int k=0;k<bestPath.length;k++){
                                newPath[k]=bestPath[k];
                            }
                        int temp=newPath[i];
                        newPath[i]=newPath[j];
                        newPath[j]=temp;
                        Path p1=shorter(newPath,shortDist);
                        if(p1.dist<best.dist){
                            best.path=p1.path;
                            best.dist=p1.dist;
                            System.out.println(best.dist);
                            for(int b=0;b<best.path.length;b++){
                            System.out.print(best.path[b]+".");
                          }
                           System.out.println();
                        }
                        
                        /*int[] newPath2 =new int[bestPath.length];
                        int limit=i+(int)Math.random()*30;
                        for(int k=0;k<limit;k++){
                            int n1 =i+(int)Math.random()*30;
                            int n2=i+(int)Math.random()*30;
                            int temp2=newPath[n1];
                            newPath2[n1]=newPath[n2];
                            newPath2[n2]=temp2;
                            Path p2=shorter(newPath,shortDist);
                            if(p2.dist<best.dist){
                            best.path=p2.path;
                            best.dist=p2.dist;
                            System.out.println(best.dist);
                            for(int b=0;b<best.path.length;b++){
                            System.out.print(best.path[b]+".");
                          }
                           System.out.println();
                        }
                       }
                        */
                       
                        
                    }
                }
            }
            
        return best;
    }

    public static double GetDistance(Town t1, Town t2){
        
        final int R = 6371; // Radious of the earth
        double r = 6372.8; //In Kilometers  
        double lat1=t1.lat;
        double lon1=t1.lon;
        double lat2=t2.lat;
        double lon2=t2.lon;
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2); //haversin(t)=sin(t)^2  haversin(d/r) 
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        Double distance = R * c;
         
        return distance;
        
    }
    
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
    
    public static Path shorter(int[] path,double[][] shortDist){
        
        double tDist=0.0;
        for(int i=0;i<path.length-1;i++){
            tDist=tDist+shortDist[path[i]-1][path[i+1]-1];
        }
        tDist=tDist+shortDist[path[(path.length-1)]-1][path[0]-1];
        Path temp = new Path(path,tDist); 
        return temp;
    }
}
