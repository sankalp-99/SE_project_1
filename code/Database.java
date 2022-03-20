import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class Database {
    private static String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";
    
    public String search(String searchText) throws Exception
    {

        File file = new File(SCOREHISTORY_DAT);
        Scanner sc = new Scanner(file);        
        String result ="";
        int i=1;
        
        if (searchText.equals(""))
        {
            sc.close();
            return "Not a valid name";
        }
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String[] arr = line.split("\t",-1);
            String name=arr[0];
            
           
            if(name.equals(searchText))
            {

                result+=i + ".  "+line+"\n";
                i++;
            }
            
        }

        if (result.equals(""))
        {
            result+="No record for "+ searchText;
        }
        sc.close();
        return result;
    } 

    public String maxScore() throws Exception
    {
        
        File file = new File(SCOREHISTORY_DAT);
        Scanner sc = new Scanner(file);
        
        int max=0;
        String maxPlayer="";
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String arr[] = line.split("\t",-1);
            String name=arr[0];
            String scoreString=arr[2].split("\n",-1)[0];
            if(Integer.parseInt(scoreString)>max)
            {
                max=Integer.parseInt(scoreString);
                maxPlayer=name;
            }
            
        }
        
        
        sc.close();
        return maxPlayer+" "+max;
    }


    public String minScore() throws Exception
    {
        
        File file = new File(SCOREHISTORY_DAT);
        Scanner sc = new Scanner(file);
        
        int min=Integer.MAX_VALUE;
        String minPlayer="";
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String arr[] = line.split("\t",-1);
            String name=arr[0];
            String scoreString=arr[2].split("\n",-1)[0];
            if(Integer.parseInt(scoreString) < min)
            {
                min=Integer.parseInt(scoreString);
                minPlayer=name;
            }
            
        }
        
        
        sc.close();
        return minPlayer+" "+min;
    }


    public double averageScore() throws Exception
    {
        File file = new File(SCOREHISTORY_DAT);
        Scanner sc = new Scanner(file);
        
        double sum=0.0;
        int count=0;
        
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String arr[] = line.split("\t",-1);
            String scoreString=arr[2].split("\n",-1)[0];
            sum +=Double.parseDouble(scoreString); 
            count++; 
        }
        // System.out.println(sum+" "+count);
        return (1.0 *sum)/count;
    
    }

    public String topPlayer() throws Exception
    {
        
        File file = new File(SCOREHISTORY_DAT);
        Scanner sc = new Scanner(file);
       
        HashMap<String,Integer> map=new HashMap<>();

        int max=0;
        String maxPlayer="";
       
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            String[] arr = line.split("\t",-1);
            String name=arr[0];
            String scoreString=arr[2].split("\n",-1)[0];
            
            if(map.containsKey(name)){
                map.put(name,map.get(name)+Integer.parseInt(scoreString));
               
            }
            else
                map.put(name,Integer.parseInt(scoreString));

            if(max < map.get(name))
            {
                max=map.get(name);
                maxPlayer=name;
            }    
            
        }
        
        sc.close();
        
        return maxPlayer+" "+max;
    }

    
    
}
