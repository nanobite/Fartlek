/*
public static int[] rndmize(int len,int[] nums,int zeros){
  int[] sendBack = new int[len];
  for(int i =0;i<len;i++){
    sendBack[i]=-1;
  }
  //first place zeros in random locations
  for(int i=0;i<zeros;i++){
      int zeroLoc = (int)(Math.random()*len);
      while(sendBack[zeroLoc]==0){
        zeroLoc = (int)(Math.random()*len);
      }
      sendBack[zeroLoc]=0;
  }
  //fill up rest of numbers
  for(int i =0;i<len;i++){
    int random = (int)(Math.random()*nums.length);
    if(sendBack[i]!=0){
      sendBack[i]=nums[random];
    }
  }
  return sendBack;
}
*/
