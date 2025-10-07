
package Login_config;


public class user_login {
   private  final int id;
   private  final String name;
   private  final String type;
   
   public user_login(int id, String name, String type){
       this.id = id;
       this.name = name;
       this.type = type;
       
   }
   
  public int getId(){
    return id;
  }
  
  public String getName(){
      return name;
  }
  
  public String getType(){
      return type;
  }
}
