import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

class Files{

    String name;
    List<Files> files;
    
    Files(String name){
        this.name=name;
        this.files=new ArrayList<>();
    }
}

class Pair{

    Files f;
    Files f1;
    String name;

    Pair(Files f,String name){
        this.f=f;
        this.name=name;
    }

    Pair(Files f,Files f1){
        this.f=f;
        this.f1=f1;
    }
}

public class FileSystem {

    private static Files search(Files root,int opt){

        Queue<Files> q=new LinkedList<>();
        q.offer(root);
        int c=0;
        while(!q.isEmpty()){
            c++;
            Files cur=q.poll();
            if(c==opt){
                return cur;
            }
            for(int i=0;i<cur.files.size();i++){
                q.offer(cur.files.get(i));
            }

        }

        return null;
    }

    private static boolean edit(Files root,int opt,String newName){

        int cnt=0;
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(root,root));

        while(!q.isEmpty()){

            cnt++;
            Pair cur=q.poll();
            if(cnt==opt){
                for(int i=0;i<cur.f.files.size();i++){
                    if(cur.f.files.get(i).name.equals(newName)){
                        return false;
                    }
                }
                return true;
            }
            for(int i=0;i<cur.f1.files.size();i++){
                q.offer(new Pair(cur.f1,cur.f1.files.get(i)));
            }
        }
        return false;

    }

    private static boolean delete(Files root,int opt){
        int cnt=0;
        Queue<Pair> q=new LinkedList<>();
        q.offer(new Pair(root,root));

        while(!q.isEmpty()){

            cnt++;
            Pair cur=q.poll();
            if(cnt==opt){
                cur.f.files.remove(cur.f1);
                return true;
            }
            for(int i=0;i<cur.f1.files.size();i++){
                q.offer(new Pair(cur.f1,cur.f1.files.get(i)));
            }
        }
        return false;
    }

    private static void displayFiles(Files root){

        Queue<Pair> q=new LinkedList<>();
        int c=1;
        q.offer(new Pair(root,new String("")));
        while(!q.isEmpty()){

            Pair cur=q.poll();
            if(cur.f==root){
                System.out.println(c+"."+cur.f.name);
            }
            else{
                System.out.println(c+"."+cur.name+cur.f.name);
            }
            for(int i=0;i<cur.f.files.size();i++){
                q.offer(new Pair(cur.f.files.get(i),cur.name+cur.f.name+">"));
            }
            c++;
        }

    }
    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);

        Files rootFile=new Files("root");

        System.out.println("Welcome to FileSystem!!");

        String[] op={"1.Create a File","2.Edit a File","3.Delete a file","4.Exit"};
        boolean exit=false;
        while(!exit){

            System.out.println("Choose a option");
            for(String s : op){
                System.out.println(s);
            }
            int choice=sc.nextInt();
            switch(choice){

                case 1:

                System.out.println("Choose a location where you want to create a file");

                displayFiles(rootFile);
                int opt=sc.nextInt();
                sc.nextLine();

                System.out.println("Enter the file name to be created");
                String fname=sc.nextLine();
                Files loc=search(rootFile,opt);

                if(loc==null){
                    System.out.println("You've selected an ivalid option");
                }
                else{
                    boolean isExist=false;
                    for(int i=0;i<loc.files.size();i++){
                        if(loc.files.get((i)).name.equals(fname)){
                            System.out.println("File Already Exits!!");
                            isExist=true;
                            break;
                        }
                    }
                    if(!isExist){
                        loc.files.add(new Files(fname));
                        System.out.println("File Created Successfully");
                        System.out.println();
                        System.out.println("File Structure after adding a new file:");
                        displayFiles(rootFile);
                        System.out.println();
                    }
                }

                break;

                case 2:

                System.out.println("Choose a File which you want to edit");

                displayFiles(rootFile);
                System.out.println();

                int opt1=sc.nextInt();
                sc.nextLine();

                System.out.println("Enter the new name for the file");
                String fname1=sc.nextLine();
                Files loc1=search(rootFile,opt1);

                if(loc1==null){
                    System.out.println("You've selected an ivalid option");
                }
                else{
                    boolean isEdit=edit(rootFile,opt1,fname1);
                    if(!isEdit){
                        System.out.println("A File with new Name "+fname1+" already exists");
                        break;
                    }
                    loc1.name=fname1;
                    System.out.println();

                    System.out.println("File Structure after editing the given file:");
                    displayFiles(rootFile);

                    System.out.println();
                }

                break;

                case 3:

                System.out.println("Choose a file which you want to delete");

                displayFiles(rootFile);
                int opt2=sc.nextInt();

                if(opt2==1){

                    System.out.println("You cannot delete the root file");
                    break;

                }
                boolean flag=delete(rootFile,opt2);

                if(flag){
                    System.out.println("File is deleted successfully");
                    System.out.println();

                    System.out.println("File Structure after deleting the file:");
                    displayFiles(rootFile);
                    System.out.println();
                }

                break; 

                case 4:

                exit=true;

                break;

                default:

                System.out.println("Invalid Option , Please Try Again");
            }
        }

        sc.close();

    }

}
