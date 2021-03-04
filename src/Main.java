
public class Main {
	
	public static void main(String[] args) {
		new MainWindow();
		Runnable animation = new Animation();
		Thread animThread = new Thread(animation);
		animThread.start();
	}
    //Main
	//MainWindow
	//Background-Panel
	//Moorhuhn
    //Moorhuhn-Animation
    //Treffer-Animation
    //

}